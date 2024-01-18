package com.sky.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Data
@AllArgsConstructor
@Slf4j
public class AwsUtil {

    private String region;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    /**
     * 文件上传
     *
     * @param file
     * @param objectName
     * @return
     */
    public String upload(MultipartFile file, String objectName) throws IOException {


        String contentType = file.getContentType();
        long fileSize = file.getSize();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        objectMetadata.setContentLength(fileSize);

        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        builder.setRegion("ap-northeast-1");
        AmazonS3 s3Client = builder.build();


        try {
            PutObjectResult request = s3Client.putObject(bucketName, objectName, file.getInputStream(),objectMetadata);
            //公共讀
            s3Client.setObjectAcl(bucketName, objectName,CannedAccessControlList.PublicRead);
        } catch (Exception e){
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".s3.")
                .append(region)
                .append(".amazonaws.com/")
                .append(objectName);
        log.info("文件上傳到:{}", stringBuilder.toString());
        return stringBuilder.toString();
    }
}
