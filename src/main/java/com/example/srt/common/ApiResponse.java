package com.example.srt.common;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
@Schema(description = "응답 DTO")
public class ApiResponse<T> {

    @Schema(description = "상태 코드")
    private int code;
    @Schema(description = "상태 메세지")
    private String message;
    @Schema(description = "데이터")
    private T data;

    public ApiResponse(int code, String message, T data) {
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(0, "SUCCESS", data);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode, String message) {
        return new ApiResponse<>(errorCode.getCode(), message, null);
    }
}
