package com.example.srt.dto.srtInfo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "배너 요청 DTO")
public class BannerDTO {
    @Schema(description = "배너 이미지 url , 경로 찾을 때 앞에 http://dpms.openobject.net:4132 붙여주시면 됩니다.")
    private String imgUrl;
    @Schema(description = "링크 url")
    private String linkUrl;
}
