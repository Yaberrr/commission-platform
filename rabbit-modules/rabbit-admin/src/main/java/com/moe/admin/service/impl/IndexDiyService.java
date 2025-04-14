package com.moe.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moe.admin.domain.dto.IndexDiySaveDTO;
import com.moe.admin.mapper.IndexDiyMapper;
import com.moe.admin.queue.IndexDiyDelayQueue;
import com.moe.admin.service.IIndexDiyService;
import com.moe.common.core.constant.CacheConstants;
import com.moe.common.core.domain.IndexDiy;
import com.moe.common.core.enums.index.PublishStatus;
import com.moe.common.core.utils.Assert;
import com.moe.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author tangyabo
 * @date 2025/4/11
 */
@Service
public class IndexDiyService implements IIndexDiyService {

    @Autowired
    private IndexDiyMapper indexDiyMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IndexDiyDelayQueue indexDiyDelayQueue;

    @Override
    public Page<IndexDiy> indexDiyList(IPage page) {
        return indexDiyMapper.diyIndexList(page);
    }

    @Override
    public IndexDiy indexDiyDetail(Long id) {
        return indexDiyMapper.selectById(id);
    }

    @Override
    @Transactional
    public void saveIndexDiy(IndexDiySaveDTO dto) {
        IndexDiy indexDiy = BeanUtil.copyProperties(dto, IndexDiy.class);
        if(PublishStatus.PUBLISHED.equals(indexDiy.getPublishStatus())){
            indexDiy.setLastPublishTime(new Date());
        }
        indexDiy.setLastSaveTime(new Date());
        if(indexDiy.getId() == null){
            indexDiyMapper.insert(indexDiy);
        }else{
            indexDiyMapper.updateById(indexDiy);
            //清空缓存
            redisService.deleteObject(CacheConstants.DIY_INDEX_KEY + indexDiy.getId());
        }

        //定时发布
        if(indexDiy.getSchedulePublishTime()!=null){
            Assert.isTrue(indexDiy.getSchedulePublishTime().after(new Date()),"定时发布-时间错误");
            Assert.isTrue(indexDiy.getPublishStatus().equals(PublishStatus.SCHEDULED), "定时发布-状态需为预计发布");
            indexDiyDelayQueue.addTask(indexDiy.getId().toString(),
                    indexDiy.getSchedulePublishTime().getTime()-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void cancelIndexDiy(Long id) {
        IndexDiy updateBean = new IndexDiy();
        updateBean.setId(id);
        updateBean.setPublishStatus(PublishStatus.UNPUBLISHED);
        indexDiyMapper.updateById(updateBean);
        redisService.deleteObject(CacheConstants.DIY_INDEX_KEY + id);
    }

    @Override
    public void deleteIndexDiy(Long id) {
        indexDiyMapper.deleteById(id);
        redisService.deleteObject(CacheConstants.DIY_INDEX_KEY + id);
    }
}
