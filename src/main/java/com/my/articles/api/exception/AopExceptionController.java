package com.my.articles.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//RestApi 사용시에 요류를 감지하는 Controller
//throw new BadRequestException("TEST"); 오류를 잡아서 처리
public class AopExceptionController {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponseMessage> badRequestError(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseMessage.builder().message(e.getMessage()).build());
    }
}
