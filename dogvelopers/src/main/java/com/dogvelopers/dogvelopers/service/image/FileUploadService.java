package com.dogvelopers.dogvelopers.service.image;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileUploadService {

    private final AwsS3UploadService s3Service;

    // 실질적으로 image 를 aws에 올리고 url 을 반환하는 메소드
    public String uploadImage(MultipartFile file) { // createFileName 으로 확장자를 유지하고 , 새로운 랜덤하게 유니크한 파일명을 가져온다.
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata(); // image 파일의 date를 담을 객체 생성
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        try (InputStream inputStream = file.getInputStream()) { // 입력 받은 파일에서 파일의 기존 이름, metadata, inputStream을 읽어오고 이를 토대로 S3에 저장을 요청함
            s3Service.uploadFile(inputStream, objectMetadata, fileName);
        } catch (IOException e) { // 파일을 upload 하는 과정에서 , exception 발생
            throw new IllegalArgumentException(
                    String.format("파일 변환 중 에러가 발생하였습니다 (%s)", file.getOriginalFilename())); // file 원래 이름으로 에러 로그 띄워줌
        }

        return s3Service.getFileUrl(fileName); // aws 에 올린 image url 을 받아옴
    }

    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString()
                .concat(getFileExtension(originalFileName)); // 기존의 파일명을 통해서 랜덤으로 숫자와 영어로 구성된 이름을 만들어 저장함
    }

    private String getFileExtension(String fileName) { // 확장자만 때오는 메소드
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s) 입니다", fileName));
        }
    }

}