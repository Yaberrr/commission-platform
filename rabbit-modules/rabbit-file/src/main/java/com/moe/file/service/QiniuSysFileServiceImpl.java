package com.moe.file.service;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.moe.common.core.exception.ServiceException;
import com.moe.common.core.utils.file.FileUtils;
import com.moe.file.config.QiniuConfig;
import com.moe.file.utils.FileUploadUtils;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author tangyabo
 * @date 2025/4/7
 */
@Service
@Primary
public class QiniuSysFileServiceImpl implements ISysFileService {

    @Autowired
    private QiniuConfig qiniuConfig;

    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = FileUploadUtils.extractFilename(file);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucketName());
        Response response = qiniuConfig.getUploadManager().put(file.getInputStream(),fileName,upToken,null, null);
        //解析结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        return putRet.key;
    }
}
