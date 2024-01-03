package com.example.srt.dto.device.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "상태 요청 DTO")
public class StatusDTO {
	@Schema(description = "버전 update 필요하면 Y 아니면 N", allowableValues = {"Y", "N"})
    private String status;
}
