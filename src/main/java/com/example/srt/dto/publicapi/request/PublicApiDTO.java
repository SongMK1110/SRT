package com.example.srt.dto.publicapi.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Schema(description = "기차 조회 요청 DTO")
public class PublicApiDTO {
    @Schema(description = "출발역ID")
    @NotBlank(message = "depPlaceId가 없습니다.")
    private String depPlaceId;

    @Schema(description = "도착역ID")
    @NotBlank(message = "arrPlaceId가 없습니다.")
    private String arrPlaceId;

    @Schema(description = "출발 날짜", defaultValue = "20230505")
    @NotBlank(message = "depPlandDate가 없습니다.")
    @Pattern(regexp = "\\d+", message = "depPlandDate는 숫자 형식 입니다.")
    @Size(min = 8, max = 8, message = "depPlandDate는 8자리 입니다.")
    private String depPlandDate;

    @Schema(description = "출발 시간", defaultValue = "2000")
    @NotBlank(message = "depPlandTime가 없습니다.")
    @Pattern(regexp = "\\d+", message = "depPlandTime는 숫자 형식 입니다.")
    @Size(min = 4, max = 4, message = "depPlandTime는 4자리 입니다.")
    private String depPlandTime;

    @Hidden
    @Schema(description = "디바이스ID")
    private String deviceId;
}
