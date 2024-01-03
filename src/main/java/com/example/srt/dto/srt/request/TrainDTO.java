package com.example.srt.dto.srt.request;

import com.example.srt.dto.publicapi.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "기차 조회 관련 요청 DTO")
public class TrainDTO {
    @Schema(description = "기차 정보 리스트")
    private List<Item> trainInfoList;
    @Schema(description = "전체 개수")
    private int totalCount;
}
