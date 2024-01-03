package com.example.srt.dto.srtInfo.response;

import com.example.srt.dto.srtInfo.response.BannerDTO;
import com.example.srt.dto.srtInfo.response.NoticeDTO;
import com.example.srt.dto.srtInfo.response.RecentReservationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "최근 예매, 배너, 공지사항 요청 DTO")
public class SrtInfoDTO {
    private List<RecentReservationDTO> recentReservationList;
    private List<BannerDTO> bannerList;
    private List<NoticeDTO> noticeList;
}
