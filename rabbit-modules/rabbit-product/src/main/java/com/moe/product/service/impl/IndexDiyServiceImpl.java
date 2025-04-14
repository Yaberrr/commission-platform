package com.moe.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.constant.LockConstants;
import com.moe.common.core.domain.IndexDiy;
import com.moe.common.core.enums.index.DiyModuleType;
import com.moe.common.core.enums.index.PublishStatus;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.Assert;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.common.core.web.page.TableSupport;
import com.moe.common.redis.service.RedisLock;
import com.moe.common.redis.service.RedisService;
import com.moe.platform.api.IPlatformProductApi;
import com.moe.platform.dto.product.PlatformProductDTO;
import com.moe.platform.vo.ProductVO;
import com.moe.product.domain.dto.ProductListDTO;
import com.moe.product.mapper.IndexDiyMapper;
import com.moe.product.service.IIndexDiyService;
import com.moe.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author tangyabo
 * @date 2025/4/14
 */
@Service
public class IndexDiyServiceImpl implements IIndexDiyService {

    @Autowired
    private IndexDiyMapper indexDiyMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisLock redisLock;
    @Autowired
    private IProductService productService;

    @Override
    public Map<String, Object> getIndexDiy(String appVersion) {
        //查询最后发布的装修
        IndexDiy indexDiy = indexDiyMapper.selectOne(new LambdaQueryWrapper<IndexDiy>()
                .select(IndexDiy::getId)
                .eq(IndexDiy::getPublishStatus, PublishStatus.PUBLISHED)
                .le(IndexDiy::getMinVersion, appVersion)
                .ge(IndexDiy::getMaxVersion, appVersion)
                .orderByDesc(IndexDiy::getLastPublishTime)
                .last(" LIMIT 1 "));
        Assert.notNull(indexDiy,  "App版本对应装修不存在");

        String diyIndexKey = CacheConstants.DIY_INDEX_KEY + indexDiy.getId();
        Map<String,Object> result = redisService.getCacheObject(diyIndexKey);
        //双检加载缓存
        if(result == null){
            if(redisLock.tryLock(LockConstants.INDEX_DIY_LOCK + indexDiy.getId(), 10, TimeUnit.SECONDS)){
                result = redisService.getCacheObject(diyIndexKey);
                if(result == null) {
                    result = this.loadIndexDiyData(indexDiy.getId());
                    redisService.setCacheObject(diyIndexKey, result, 30L, TimeUnit.MINUTES);
                }
            }else{
                throw new ServiceException("首页加载失败，请稍后再试");
            }
        }
        return result;
    }

    /**
     * 加载装修数据
     * @param indexDiyId
     * @return
     */
    public Map<String,Object> loadIndexDiyData(Long indexDiyId) {
        IndexDiy indexDiy = indexDiyMapper.selectById(indexDiyId);
        JSONArray diyContent = JSON.parseArray(indexDiy.getConfigContent());
        Iterator<Object> iterator = diyContent.iterator();
        while(iterator.hasNext()){
            JSONObject item = (JSONObject) iterator.next();
            int componentType = item.getIntValue("componentType");
            DiyModuleType diyModuleType = DiyModuleType.diyModelType(componentType);
            if(diyModuleType != null) {
                switch (diyModuleType) {
                    case EMPTY:
                    case TITLE:
                    case SIDE_BAR:
                        //不处理
                        break;
                    default:
                        //查询商品
                        long groupId = item.getLongValue("groupId");
                        int showCount = item.getIntValue("showCount");
                        if(groupId > 0 && showCount > 0){
                            ProductListDTO dto = new ProductListDTO();
                            dto.setGroupId(groupId);
                            TableDataInfo<ProductVO> data = productService.productList(new Page<>(TableSupport.DEFAULT_PAGE_NUM, showCount), dto).getData();
                            item.put("data",data.getRows());
                            item.put("totalGoodsNum",data.getTotal());
                        }
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("homeJson", diyContent);
        return result;
    }

}
