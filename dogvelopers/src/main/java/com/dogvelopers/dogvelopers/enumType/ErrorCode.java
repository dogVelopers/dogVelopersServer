package com.dogvelopers.dogvelopers.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND_INFO(NOT_FOUND , "회원 정보를 조회할 수 없습니다."),
    BAD_REQUEST_INFO(BAD_REQUEST , "정보를 다시 입력해주세요."),
    DUPLICATE_INFO(CONFLICT , "이미 존재하는 회원입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
