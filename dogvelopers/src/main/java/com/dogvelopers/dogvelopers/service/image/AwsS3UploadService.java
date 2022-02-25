package com.dogvelopers.dogvelopers.service.image;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface AwsS3UploadService {

    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

    String getFileUrl(String fileName);

    void deleteFile(String fileName);

}