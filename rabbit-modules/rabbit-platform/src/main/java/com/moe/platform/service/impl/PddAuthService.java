package com.moe.platform.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.moe.common.core.domain.platform.PlatformAuth;
import com.moe.common.core.enums.platform.PlatformType;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.Assert;
import com.moe.platform.service.IPlatformAuthService;
import com.moe.platform.utils.PddUtils;
import com.moe.platform.vo.PlatformUrlVO;
import com.pdd.pop.sdk.http.PopClient;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkGoodsPidGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkMemberAuthorityQueryRequest;
import com.pdd.pop.sdk.http.api.pop.request.PddDdkRpPromUrlGenerateRequest;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkGoodsPidGenerateResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkMemberAuthorityQueryResponse;
import com.pdd.pop.sdk.http.api.pop.response.PddDdkRpPromUrlGenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author tangyabo
 * @date 2025/3/24
 */
@Component
public class PddAuthService implements IPlatformAuthService {

    @Autowired
    private PopClient popClient;

    @Override
    public PlatformAuth createAuth(Long userId) {
        try {
            PddDdkGoodsPidGenerateRequest request = new PddDdkGoodsPidGenerateRequest();
            //推广位数量
            request.setNumber(1L);
            //推广位名称
            request.setPIdNameList(Collections.singletonList("USER-"+userId));
            PddDdkGoodsPidGenerateResponse response = popClient.syncInvoke(request);
            PddUtils.checkResponse(response);

            List<PddDdkGoodsPidGenerateResponse.PIdGenerateResponsePIdListItem> pIdList = response.getPIdGenerateResponse().getPIdList();
            Assert.isFalse(CollUtil.isEmpty(pIdList),"拼多多生成推广位失败");

            PlatformAuth auth = new PlatformAuth();
            auth.setAuthId(pIdList.get(0).getPId());
            auth.setPlatformType(PlatformType.PDD);
            auth.setStatus(0);
            auth.setUserId(userId);
            return auth;
        } catch (Exception e) {
            throw new ServiceException(e,"获取授权信息api异常:{}",e.getMessage());
        }
    }

    @Override
    public boolean checkAuth(PlatformAuth auth) {
        try {
            PddDdkMemberAuthorityQueryRequest request = new PddDdkMemberAuthorityQueryRequest();
            request.setPid(auth.getAuthId());
            request.setCustomParameters(PddUtils.getCustomParameter(auth));
            PddDdkMemberAuthorityQueryResponse response = popClient.syncInvoke(request);
            PddUtils.checkResponse(response);
            //判断状态
            return response.getAuthorityQueryResponse().getBind() == 1;
        }catch (Exception e){
            throw new ServiceException(e,"检查授权状态api异常:{}",e.getMessage());
        }
    }

    @Override
    public PlatformUrlVO generateAuthUrl(PlatformAuth auth) {
        try {
            PddDdkRpPromUrlGenerateRequest request = new PddDdkRpPromUrlGenerateRequest();
            request.setChannelType(10);
            request.setCustomParameters(PddUtils.getCustomParameter(auth));
            request.setGenerateSchemaUrl(true);
            request.setPIdList(Collections.singletonList(auth.getAuthId()));
            PddDdkRpPromUrlGenerateResponse response = popClient.syncInvoke(request);
            PddUtils.checkResponse(response);
            //提取url
            PddDdkRpPromUrlGenerateResponse.RpPromotionUrlGenerateResponseUrlListItem item = response.getRpPromotionUrlGenerateResponse().getUrlList().get(0);
            return new PlatformUrlVO(item.getMobileUrl(),item.getSchemaUrl(),null);
        }catch (Exception e){
            throw new ServiceException(e,"生成授权链接api异常:{}",e.getMessage());
        }
    }

}
