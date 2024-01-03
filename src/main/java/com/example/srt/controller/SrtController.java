package com.example.srt.controller;

import com.example.srt.common.ApiResponse;
import com.example.srt.common.ErrorCode;
import com.example.srt.dto.device.request.DeviceDTO;
import com.example.srt.dto.device.response.StatusDTO;
import com.example.srt.dto.publicapi.request.PublicApiDTO;
import com.example.srt.dto.srt.request.SrtDTO;
import com.example.srt.dto.srt.request.TrainDTO;
import com.example.srt.dto.srtInfo.response.SrtInfoDTO;
import com.example.srt.dto.storage.response.ResponseStorageDTO;
import com.example.srt.dto.storage.response.ResponseStorageListDTO;
import com.example.srt.service.SrtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Slf4j
@Controller
@Tag(name = "SRT", description = "SRT 관련 API 입니다.")
public class SrtController {
    @Autowired
    private SrtService srtService;


    @GetMapping("/")
    public String test() {
        return "test";
    }

    @PostMapping("/device")
    @ResponseBody
    @Operation(summary = "디바이스 등록", description = "code : 10 필수 Parameter(deviceId, version)가 없습니다 <br> code : 20 version을 숫자 형식으로 해주세요")
    public ApiResponse<StatusDTO> device(@Validated @RequestBody DeviceDTO dto) {
        StatusDTO statusDTO = srtService.addDevice(dto);
//        if("N".equals(statusDTO.getStatus())){
//            session.setAttribute("deviceId", dto.getDeviceId());
//        }
        return ApiResponse.success(statusDTO);

    }
    @PostMapping("/srtInfo")
    @ResponseBody
    @Operation(summary = "최근 예매 구간, 공지사항, 배너 조회", description = "code : 10 필수 Parameter(deviceId)가 없습니다 <br> 배너 조회 할 때 이미지 경로 앞에 http://dpms.openobject.net:4132 넣어주세요")
    public ApiResponse<SrtInfoDTO> srtInfo(HttpSession session){
        String email = (String) session.getAttribute("userEmail");
        return ApiResponse.success(srtService.srtInfo(email));
    }

    @PostMapping("/srtList")
    @ResponseBody
    @Operation(summary = "기차 조회", description = "code : 10 필수 Parameter(depPlaceId, arrPlaceId, depPlandDate, depPlandTime, deviceId)가 없습니다 <br> code : 20 depPlandDate or depPlandTime이 숫자 형식이 아닙니다. <br> code : 30 depPlandDate or depPlandTime이 사이즈가 다릅니다. <br> code : 80 기차 조회 오류")
    public ApiResponse<TrainDTO> srtList(@Validated @RequestBody PublicApiDTO dto, HttpSession session) throws ParseException {
        String email = (String) session.getAttribute("userEmail");
        return ApiResponse.success(srtService.srtList(dto.getDepPlaceId(), dto.getArrPlaceId(), dto.getDepPlandDate(), dto.getDepPlandTime(), email));
    }

    @PostMapping("/reserve")
    @ResponseBody
    @Operation(summary = "예매하기", description = "code : 10 필수 Parameter가 없습니다")
    public ApiResponse<String> reserve(@Validated @RequestBody SrtDTO dto, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        dto.setEmail(email);
        if(srtService.insertReserve(dto) > 0){
            return ApiResponse.success(null);
        } else {
            return ApiResponse.error(ErrorCode.SERVER_ERROR, "서버 에러");
        }

    }

    @PostMapping("/storage")
    @ResponseBody
    @Operation(summary = "승차권 보관함 조회", description = "code : 10 필수 Parameter가 없습니다")
    public ApiResponse<ResponseStorageListDTO> storage(HttpSession session) throws ParseException {
        String email = (String) session.getAttribute("userEmail");
        return ApiResponse.success(srtService.selectStorage(email));
    }
}
