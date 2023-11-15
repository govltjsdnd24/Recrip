package com.ssafy.enjoytrip.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Component
@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        System.out.println("asdadsadsadsasdddsaads " + credentials.getAWSAccessKeyId() + " " + credentials.getAWSSecretKey());
        AmazonS3 client= (AmazonS3) AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
        System.out.println(client.getBucketLocation("primary-sun-bucket"));
        return client;
    }
}
