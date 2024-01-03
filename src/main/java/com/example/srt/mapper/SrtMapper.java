package com.example.srt.mapper;

import com.example.srt.dto.device.request.DeviceDTO;
import com.example.srt.dto.srt.request.SrtDTO;
import com.example.srt.dto.srtdetail.SrtDetailDTO;
import com.example.srt.dto.srtdetail.response.ResponseSrtDetailDTO;
import com.example.srt.dto.srtInfo.response.BannerDTO;
import com.example.srt.dto.srtInfo.response.NoticeDTO;
import com.example.srt.dto.srtInfo.response.RecentReservationDTO;

import java.util.List;

public interface SrtMapper {

	int insertDevice(DeviceDTO dto);

	DeviceDTO selectDevice(DeviceDTO dto);

	List<RecentReservationDTO> selectSrtInfo(String email);

	List<BannerDTO> selectBanner();

	List<NoticeDTO> selectNotice();

	int reserveCheck(String email, int trainNo, String depPlandTime);

	int insertReserve(SrtDTO dto);

	int insertReserveDetail(SrtDetailDTO dto);

	List<SrtDTO> selectSrt(String email);

	List<ResponseSrtDetailDTO> selectSrtDetailList(int srtId);

	void updateTicketStatus(int srtId);


}
