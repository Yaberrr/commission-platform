package com.moe.file.controller;

import com.moe.admin.api.FileApi;
import com.moe.file.mapper.SysFileMapper;
import com.moe.file.service.ISysFileService;
import com.moe.file.utils.FileUploadUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.moe.common.core.domain.R;
import com.moe.common.core.utils.file.FileUtils;
import com.moe.common.core.domain.sys.SysFile;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "文件")
@RestController
public class SysFileController implements FileApi
{
    private static final Logger log = LoggerFactory.getLogger(SysFileController.class);

    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private SysFileMapper sysFileMapper;

    private SysFile processUploadedFile(MultipartFile file) throws Exception {
        String fileName = FilenameUtils.getName(file.getOriginalFilename());
        // 上传并返回访问地址
        String url = sysFileService.uploadFile(file);
        SysFile sysFile = new SysFile();
        sysFile.setName(fileName);
        sysFile.setUrl(url);
        sysFileMapper.insert(sysFile);
        //添加前缀
        sysFile.setUrl(FileUtils.addPrefix(url));
        return sysFile;
    }

    @Operation(description = "上传文件")
    @PostMapping("upload")
    public R<SysFile> upload(MultipartFile file) {
        try {
            SysFile sysFile = processUploadedFile(file);
            return R.ok(sysFile);
        }
        catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }

    @Operation(description = "上传多个文件")
    @PostMapping("uploads")
    public R<List<SysFile>> uploads(MultipartFile[] files) {
        List<SysFile> sysFiles = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                sysFiles.add(processUploadedFile(file));
            }
            return R.ok(sysFiles);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return R.fail(e.getMessage());
        }
    }
}
