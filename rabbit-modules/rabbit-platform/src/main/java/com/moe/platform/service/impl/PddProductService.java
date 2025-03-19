package com.moe.platform.service.impl;

import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.web.page.TableDataInfo;
import com.moe.platform.body.SearchBody;
import com.moe.platform.body.SearchParam;
import com.moe.platform.service.ProductService;
import com.moe.platform.vo.PlatformProductVO;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsSearchRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsSearchResponse;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 拼多多商品service
 * @author tangyabo
 * @date 2025/3/19
 */
@Service
public class PddProductService implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(PddProductService.class);

    @Autowired
    private PopClient client;

    @Override
    public TableDataInfo<PlatformProductVO> search(SearchBody body, SearchParam param) {
        try {
            PddDdkGoodsSearchRequest request = new PddDdkGoodsSearchRequest();
            //筛选条件
            if(param.getTagType() != null) {
                switch (param.getTagType()) {
                    case CHANNEL:
                        request.setActivityTags(Collections.singletonList(Integer.valueOf(param.getTagValue())));
                        break;
                    case CATEGORY:
                        request.setCatId(Long.valueOf(param.getTagValue()));
                    case LABEL:
                        request.setOptId(Long.valueOf(param.getTagValue()));
                }
            }

            request.setKeyword(body.getKeyword());
            request.setPage(body.getPageNo());
            request.setPageSize(body.getPageSize());
            PddDdkGoodsSearchResponse response = client.syncInvoke(request);
            return new TableDataInfo<>();
        }catch (Exception e){
            log.error("拼多多商品搜索api异常",e);
            return TableDataInfo.error();
        }
    }
}
