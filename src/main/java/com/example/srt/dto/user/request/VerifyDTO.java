package com.example.srt.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "검증 요청 DTO")
public class VerifyDTO {
    @Schema(description = "코드")
    @NotBlank(message = "code가 없습니다.")
    private String code;
}
