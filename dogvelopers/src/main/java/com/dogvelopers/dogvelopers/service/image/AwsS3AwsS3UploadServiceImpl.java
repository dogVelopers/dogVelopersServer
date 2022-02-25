package com.dogvelopers.dogvelopers.service.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dogvelopers.dogvelopers.dto.component.S3Component;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class AwsS3AwsS3UploadServiceImpl implements AwsS3UploadService {

    private final AmazonS3 amazonS3;
    private final S3Component component;

    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata,
                           String fileName) { // InputStream , image 파일에 대한 정보 , 새로 생성한 fileName 을 가지고 , aws 에 올려줌
        amazonS3.putObject(
                new PutObjectRequest(component.getBucket(), fileName, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public String getFileUrl(String fileName) { // 업로드한 파일의 URI를 가져오는 메소드
        return amazonS3.getUrl(component.getBucket(), fileName).toString();
    }

    @Override
    public void deleteFile(String fileName) { // file name 으로 삭제 해주는 메소드
        amazonS3.deleteObject((new DeleteObjectRequest(component.getBucket(), fileName)));
    }

}
