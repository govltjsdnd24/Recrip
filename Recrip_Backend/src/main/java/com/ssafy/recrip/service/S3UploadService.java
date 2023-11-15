package com.ssafy.recrip.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssafy.recrip.config.S3Config;

import lombok.RequiredArgsConstructor;

@Service
public class S3UploadService {

	@Autowired
    private AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    
    @Value("${cloud.aws.credentials.access-key}")
    private String key;

    public String saveFile(@RequestParam MultipartFile multipartFile) throws IOException {
    	System.out.println(amazonS3Client.getBucketLocation("primary-sun-bucket"));
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        
       try {
    	   amazonS3Client.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
       } catch (Exception e) {
		e.printStackTrace();
       }
        
       return amazonS3Client.getUrl(bucket, originalFilename).toString();
    }

	
}