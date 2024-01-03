package com.example.srt.dto.srtdetail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "srt디테일 요청 DTO")
public class SrtDetailDTO {
    @Schema(description = "승차권 번호")
    @NotBlank(message = "ticketNo(승차권 번호)가 없습니다.")
    private String ticketNo;

    @Schema(description = "호차")
    @NotBlank(message = "hocha(호차)가 없습니다.")
    private int hocha;

    @Schema(description = "좌석번호")
    @NotBlank(message = "seatNumber(좌석번호)가 없습니다.")
    private String seatNumber;

    @Schema(hidden = true)
    private int srtId;
}
