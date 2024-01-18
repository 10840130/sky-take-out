package com.sky.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.sky.properties.AwsS3Properties;
import com.sky.utils.AwsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AwsS3Configuration {

    @Bean
    @ConditionalOnMissingBean
    public AwsUtil awsUtil(AwsS3Properties awsS3Properties){
        log.info("開始創建雲文件上傳對象: {}",awsS3Properties);
        return new AwsUtil(awsS3Properties.getRegion(),
                awsS3Properties.getAccessKey(),
                awsS3Properties.getSecretKey(),
                awsS3Properties.getBucketName());
    }
}