package com.example.srt.dto.srtInfo.response;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
@Data
@Schema(description = "최근 예매 요청 DTO")
public class RecentReservationDTO {
    @Schema(description = "출발지 ID")
    private String depPlaceId;
    @Schema(description = "출발역")
    private String depPlaceName;
    @Schema(description = "도착지 ID")
    private String arrPlaceId;
    @Schema(description = "도착역")
    private String arrPlaceName;
}
