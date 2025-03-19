package com.moe.admin.factory;

import com.moe.admin.api.FileApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.moe.common.core.domain.R;
import com.moe.common.core.domain.sys.SysFile;

/**
 * 文件服务降级处理
 *
 * @author ruoyi
 */
@Component
public class FileApiFallback implements FallbackFactory<FileApi>
{
    private static final Logger log = LoggerFactory.getLogger(FileApiFallback.class);

    @Override
    public FileApi create(Throwable throwable)
    {
        log.error("文件服务调用失败:{}", throwable.getMessage());
        return new FileApi()
        {
            @Override
            public R<SysFile> upload(MultipartFile file)
            {
                return R.fail("上传文件失败:" + throwable.getMessage());
            }
        };
    }
}
