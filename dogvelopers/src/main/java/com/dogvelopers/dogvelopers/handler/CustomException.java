package com.dogvelopers.dogvelopers.handler;

import com.dogvelopers.dogvelopers.enumType.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
}
