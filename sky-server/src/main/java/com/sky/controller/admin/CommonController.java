package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 新增通用上传接口：
 */
@Slf4j
@Api(tags = "通用相关接口")
@RequestMapping("/admin/common")
@RestController
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        String originalFileName= file.getOriginalFilename();
        log.info("文件上传，原始文件名：{}",originalFileName);
        String suffix=originalFileName.substring(originalFileName.lastIndexOf("."));
        String objectName= UUID.randomUUID().toString()+suffix;

        try {
            String url=aliOssUtil.upload(file.getBytes(),objectName);
            return Result.success(url);
        } catch (IOException e) {
            log.info("文件上传失败：{}",e.getMessage());
            return Result.error(MessageConstant.UPLOAD_FAILED);
        }

    }
}
