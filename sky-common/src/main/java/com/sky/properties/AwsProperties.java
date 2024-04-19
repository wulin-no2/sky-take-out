package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix = "cloud.aws")
@Data
public class AwsProperties {
    private String region;
    private String bucketName;
}
