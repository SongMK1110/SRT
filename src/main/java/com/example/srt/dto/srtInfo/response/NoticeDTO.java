package com.example.srt.dto.srtInfo.response;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "공지사항 요청 DTO")
public class NoticeDTO {
    @Schema(description = "공지사항 제목")
    private String title;
    @Schema(description = "공지사항 url")
    private String linkUrl;
}
