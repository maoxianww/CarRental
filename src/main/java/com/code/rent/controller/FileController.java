package com.code.rent.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.code.rent.common.Result;
import com.code.rent.exception.CustomException;
import com.code.rent.utils.AliYunProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 文件控制器
 *
 * @author HeXin
 * @date 2023/11/13
 */
@Tag(name = "文件管理模块")
@RestController
@RequestMapping("/file")
@SaCheckLogin
public class FileController {
    /**
     * 上传
     *
     * @param file 文件
     * @return {@link Result}<{@link String}>
     */
    @Operation(summary = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if(file.getSize() > 3145728L){
            throw new CustomException("图片大小最大为3M！");
        }
        String endPoint = AliYunProperties.END_POINT;
        String bucketName = AliYunProperties.BUCKET_NAME;
        String keyId = AliYunProperties.KEY_ID;
        String keySecret = AliYunProperties.KEY_SECRET;
        String foldername = AliYunProperties.FOLDER_NAME;
        String originalFilename = file.getOriginalFilename();
        String suffixName = originalFilename;
        if (originalFilename != null) {
            // 获取文件后缀名
            suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            if(!".jpg".equals(suffixName) && !".png".equals(suffixName) && !".jpeg".equals(suffixName)){
                throw new CustomException("图片格式错误！");
            }
        }
        String filename = foldername + "/" +new DateTime().toString("yyyy/MM/dd/") + UUID.randomUUID() + suffixName;
        String url;
//        创建oss对象
        OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);
//        传输
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, file.getInputStream());
            ossClient.putObject(putObjectRequest);
            url = "https://" + bucketName + "." + endPoint + "/" + filename;
        } catch (IOException e) {
            throw new CustomException("文件上传失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return Result.success(url);
    }
}
