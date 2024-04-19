package com.sky.config;

import com.sky.properties.AwsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


@Configuration
public class S3Config {
    @Autowired
    private AwsProperties awsProperties;

    @Bean
    public S3Client s3client() {
        return S3Client.builder()
                .region(Region.of(awsProperties.getRegion()))
                .build();
    }
}
