package com.moe.admin.api;

import com.moe.common.core.annotation.FeignResponseCheck;
import com.moe.common.core.domain.sys.SysFile;
import com.moe.admin.factory.FileApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.moe.common.core.constant.ServiceNameConstants;
import com.moe.common.core.domain.R;

import java.util.List;

/**
 * 文件服务
 *
 * @author ruoyi
 */
@FeignResponseCheck(serviceName = "文件")
@FeignClient(contextId = "fileApi", value = ServiceNameConstants.FILE_SERVICE, fallbackFactory = FileApiFallback.class)
public interface FileApi
{
    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<SysFile> upload(@RequestPart(value = "file") MultipartFile file);


    /**
     * 同时上传多个文件
     *
     * @param files 文件信息
     * @return 结果
     */
    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<List<SysFile>> uploads(@RequestPart(value = "files") MultipartFile[] files);
}
