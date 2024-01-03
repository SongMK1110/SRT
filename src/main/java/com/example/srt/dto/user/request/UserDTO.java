package com.example.srt.dto.user.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "User 요청 DTO")
public class UserDTO {

    @Schema(description = "이메일", defaultValue = "string")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "email이 없습니다.")
    private String email;
    @Schema(description = "비밀번호")
    @Size(min = 6, message = "비밀번호는 최소 6자리 이상 입니다.")
    @NotBlank(message = "pw가 없습니다.")
    private String pw;
    @Schema(description = "생일", defaultValue = "yyyyMMdd")
    @NotBlank(message = "birth가 없습니다.")
    private String birth;
    @Schema(description = "이름")
    @NotBlank(message = "name이 없습니다.")
    private String name;
    @Hidden
    private int check;
}
