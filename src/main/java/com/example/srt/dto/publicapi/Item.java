package com.example.srt.dto.publicapi;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Item {
    @Schema(description = "도착역")
    public String arrplacename;
    @Schema(description = "도착시간")
    public Long arrplandtime;
    @Schema(description = "출발역")
    public String depplacename;
    @Schema(description = "출발시간")
    public Long depplandtime;
    @Schema(description = "기차번호")
    public int trainno;
    @Schema(description = "예매여부(예매했으면 Y 아니면 N)")
    public String reserveYN = "N";
}