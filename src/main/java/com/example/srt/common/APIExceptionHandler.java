package com.example.srt.common;

import com.example.srt.common.ApiResponse;
import com.example.srt.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class APIExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleValidationException(BindException ex) {
        log.debug("오류 내용 " + ex.getAllErrors().get(0).getCode());
        switch (Objects.requireNonNull(ex.getAllErrors().get(0).getCode())) {
            case "NotBlank":
            case "Min":
            case "NotEmpty":
            case "Max":
                return ApiResponse.error(ErrorCode.BAD_REQUEST, ex.getAllErrors().get(0).getDefaultMessage());
            case "Pattern":
                return ApiResponse.error(ErrorCode.PATTERN_ERROR, ex.getAllErrors().get(0).getDefaultMessage());
            case "Size":
                return ApiResponse.error(ErrorCode.SIZE_ERROR, ex.getAllErrors().get(0).getDefaultMessage());
            default:
                return ApiResponse.error(ErrorCode.FAIL, ex.getAllErrors().get(0).getDefaultMessage());
        }
    }


    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<?> handleCustomException(CustomException ex) {
        log.error("CustomException ", ex);
        if (ex.getMessage().equals("이메일 중복")) {
            return ApiResponse.error(ErrorCode.DUPLICATION_ERROR, "중복된 이메일 입니다.");
        } else if (ex.getMessage().equals("로그인 오류")) {
            return ApiResponse.error(ErrorCode.LOGIN_ERROR, "이메일 또는 비밀번호를 잘못 입력했습니다.");
        } else if (ex.getMessage().equals("기차 조회 오류")) {
            return ApiResponse.error(ErrorCode.PUBLIC_API_ERROR, "공공 API 기차 조회 오류입니다.");
        }
        return ApiResponse.error(ErrorCode.SERVER_ERROR, ex.getMessage());

    }


}


