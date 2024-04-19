package com.sky.controller.admin;

import com.sky.properties.AwsProperties;
import com.sky.result.Result;
import com.sky.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "common interfaces, upload image")
public class ImageUploadController {
    @Autowired
    private StorageService storageService;
    @Autowired
    private AwsProperties awsProperties;

    @PostMapping("/upload")
    @ApiOperation(value = "image upload")
    public Result<String> uploadImage(@RequestParam MultipartFile file) {
        // upload image:
        String fileName = storageService.uploadFile(file);
        // get url:
        String bucketName = awsProperties.getBucketName();
        String imageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
        log.info("image url: {}",imageUrl);
//        return to front end:
        return Result.success(imageUrl);
    }
}

