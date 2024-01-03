package com.example.srt.dto.device.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "디바이스 요청 DTO")
public class DeviceDTO {
	@Schema(description = "디바이스 ID")
	@NotBlank(message = "deviceId가 없습니다.")
	private String deviceId;
	@Schema(description = "디바이스 버전", defaultValue = "int.int.int")
	@Pattern(regexp = "\\d+\\.\\d+\\.\\d+", message = "version 형식이 숫자.숫자.숫자 이여야만 합니다.")
	@NotBlank(message = "version이 없습니다.")
	private String version;
	@Hidden
	private int cnt;
	
	
}