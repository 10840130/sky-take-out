package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.aws")
@Data
public class AwsS3Properties {

    private String region;
    private String accessKey;
    private String secretKey;
    private String bucketName;

}
