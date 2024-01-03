package com.example.srt.dto.srt.request;

import com.example.srt.dto.srtdetail.SrtDetailDTO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Schema(description = "srt 요청 DTO")
public class SrtDTO {
    @Hidden
    private int srtId;

    @Schema(description = "탑승 일자", defaultValue = "yyyyMMdd")
    @NotBlank(message = "boardingDate(탑승 일자)가 없습니다.")
    private String boardingDate;

    @Schema(description = "승차권 매수")
    @Min(value = 1, message = "ticketCnt(승차권 매수)는 최소 1 이상이어야 합니다.")
    @Max(value = 9, message = "ticketCnt(승차권 매수)는 최대 9 입니다.")
    private int ticketCnt;

    @Schema(description = "기차 번호")
    @Min(value = 1, message = "trainNo(기차 번호)는 최소 1 이상이어야 합니다.")
    private int trainNo;

    @Schema(description = "출발지 ID")
    @NotBlank(message = "depPlaceId(출발지 ID)가 없습니다.")
    private String depPlaceId;

    @Schema(description = "출발역")
    @NotBlank(message = "depPlaceName(출발역)가 없습니다.")
    private String depPlaceName;

    @Schema(description = "출발 시간", defaultValue = "HHmm")
    @NotBlank(message = "depPlandTime(출발 시간)가 없습니다.")
    private String depPlandTime;

    @Schema(description = "도착지 ID")
    @NotBlank(message = "arrPlaceId(도착지 ID)가 없습니다.")
    private String arrPlaceId;

    @Schema(description = "도착역")
    @NotBlank(message = "arrPlaceName(도착역)가 없습니다.")
    private String arrPlaceName;

    @Schema(description = "도착 시간", defaultValue = "HHmm")
    @NotBlank(message = "arrPlandTime(도착 시간)가 없습니다.")
    private String arrPlandTime;

    @Schema(description = "예매 일시")
//    @NotBlank(message = "reservationDate(예매 일시)가 없습니다.")
    @Hidden
    private String reservationDate;

    @Hidden
    @Schema(description = "발권 상태 ( 1 : 발권완료 2 : 사용완료 )")
    private String ticketingStatus;

    @Hidden
    @Schema(description = "유저 이메일")
    private String email;

    @Schema(description = "가격")
    @Min(value = 1, message = "price(가격)는 최소 1 이상이어야 합니다.")
    private int price;

    @Hidden
    @Schema(description = "srt디테일")
    private List<SrtDetailDTO> srtDetailList;

}
