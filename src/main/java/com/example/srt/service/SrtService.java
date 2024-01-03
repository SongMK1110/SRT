package com.example.srt.service;

import com.example.srt.common.CustomException;
import com.example.srt.dto.device.request.DeviceDTO;
import com.example.srt.dto.device.response.StatusDTO;
import com.example.srt.dto.publicapi.Item;
import com.example.srt.dto.publicapi.PublicDTO;
import com.example.srt.dto.srt.request.SrtDTO;
import com.example.srt.dto.srt.request.TrainDTO;
import com.example.srt.dto.srtdetail.SrtDetailDTO;
import com.example.srt.dto.srtdetail.response.ResponseSrtDetailDTO;
import com.example.srt.dto.srtInfo.response.SrtInfoDTO;
import com.example.srt.dto.storage.response.ResponseStorageDTO;
import com.example.srt.dto.storage.response.ResponseStorageListDTO;
import com.example.srt.mapper.SrtMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class SrtService {

    @Autowired
    SrtMapper srtMapper;

    public StatusDTO addDevice(DeviceDTO dto) {
        log.debug("deviceDTO " + dto);
        DeviceDTO deviceDTO = srtMapper.selectDevice(dto);

        if (deviceDTO.getCnt() <= 0) {
            int result = srtMapper.insertDevice(dto);
            if (result > 0) {
                StatusDTO statusDTO = new StatusDTO();
                statusDTO.setStatus("N");
                return statusDTO;
            } else {
                throw new CustomException("서버 에러.");
            }
            // 이후 진행 불가
        }

        String dbVersion = deviceDTO.getVersion();
        String version = dto.getVersion();

        int parseDbVersion = Integer.parseInt(dbVersion.replaceAll("\\.", ""));
        int parseVersion = Integer.parseInt(version.replaceAll("\\.", ""));

        if (parseDbVersion > parseVersion) {
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setStatus("Y");
            return statusDTO;
        } else {
            int result = srtMapper.insertDevice(dto);
            if (result > 0) {
                StatusDTO statusDTO = new StatusDTO();
                statusDTO.setStatus("N");
                return statusDTO;
            } else {
                throw new CustomException("서버 에러.");
            }
        }
    }

    public SrtInfoDTO srtInfo(String email) {
        SrtInfoDTO dto = new SrtInfoDTO();
        dto.setRecentReservationList(srtMapper.selectSrtInfo(email));
        dto.setBannerList(srtMapper.selectBanner());
        dto.setNoticeList(srtMapper.selectNotice());
        return dto;
    }

    public TrainDTO srtList(String depPlaceId, String arrPlaceId, String depPlandDate, String depPlandTime,
            String email) throws ParseException {
        String key = "";
        String urlBuilder = "https://apis.data.go.kr/1613000/TrainInfoService/getStrtpntAlocFndTrainInfo" + "?"
                + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + key +
                "&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "="
                + URLEncoder.encode("1", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "="
                + URLEncoder.encode("200", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "="
                + URLEncoder.encode("json", StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("depPlaceId", StandardCharsets.UTF_8) + "="
                + URLEncoder.encode(depPlaceId, StandardCharsets.UTF_8) + // NAT010000
                "&" + URLEncoder.encode("arrPlaceId", StandardCharsets.UTF_8) + "="
                + URLEncoder.encode(arrPlaceId, StandardCharsets.UTF_8) + // NAT011668
                "&" + URLEncoder.encode("depPlandTime", StandardCharsets.UTF_8) + "="
                + URLEncoder.encode(depPlandDate, StandardCharsets.UTF_8);

        StringBuilder sb;
        BufferedReader rd = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlBuilder);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            log.debug("Response code " + conn.getResponseCode());

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            throw new CustomException("공공 API 요청 오류.");
        } finally {
            if (rd != null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    log.error("오류 ");
                }
            }
            if (conn != null)
                conn.disconnect();
        }

        String requestDate = depPlandDate + depPlandTime + "00";
        Gson gson = new Gson();
        log.debug("sb 입니다 " + sb);
        PublicDTO publicDTO;
        try {
            publicDTO = gson.fromJson(sb.toString(), PublicDTO.class);
        } catch (JsonSyntaxException e) {
            throw new CustomException("기차 조회 오류");
        }
        log.debug("publicDTO 입니다 " + publicDTO);
        List<Item> body = publicDTO.getResponse().getBody().getItems().getItem();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date parseDate;
        parseDate = df.parse(requestDate);

        List<Item> dateAfter = new ArrayList<>();
        for (Item item : body) {
            long time = item.depplandtime;

            Date parseDateApi;
            parseDateApi = df.parse(String.valueOf(time));
            if (parseDateApi.after(parseDate) || parseDateApi.equals(parseDate)) {
                try {
                    int isReserved = srtMapper.reserveCheck(email, item.trainno, String.valueOf(time));
                    if (isReserved > 0) {
                        item.setReserveYN("Y");
                    }
                } catch (Exception e) {
                    throw new CustomException("예약 여부 조회 오류.");
                }
                dateAfter.add(item);
            }
        }

        TrainDTO trainDTO = new TrainDTO();
        trainDTO.setTrainInfoList(dateAfter);
        trainDTO.setTotalCount(dateAfter.size());
        return trainDTO;
    }

    public int insertReserve(SrtDTO dto) {
        Random random = new Random();
        log.debug("srtDTO " + dto);

        if (srtMapper.insertReserve(dto) <= 0) {
            throw new CustomException("예매하기 오류.");
        }

        int hocha = random.nextInt(5) + 1;
        int sNumber = random.nextInt(9) + 1;
        int srtId = dto.getSrtId();

        for (int i = 0; i < dto.getTicketCnt(); i++) {
            String ticketNo = dto.getBoardingDate().substring(2) + "-" + dto.getDepPlandTime() + "-"
                    + dto.getArrPlandTime() + "-" + dto.getTrainNo() + i;
            String seatNumber = String.valueOf(sNumber) + (char) (i + 65);

            SrtDetailDTO detailDTO = new SrtDetailDTO();
            detailDTO.setSrtId(srtId);
            detailDTO.setHocha(hocha);
            detailDTO.setSeatNumber(seatNumber);
            detailDTO.setTicketNo(ticketNo);
            log.debug("detailDTO " + detailDTO);

            if (srtMapper.insertReserveDetail(detailDTO) <= 0) {
                throw new CustomException("예매하기 오류.");
            }
        }
        return 1;
    }

    public ResponseStorageListDTO selectStorage(String email) throws ParseException {
        List<SrtDTO> srtDTO = srtMapper.selectSrt(email);

        for (SrtDTO srt : srtDTO) {
            String date = srt.getBoardingDate() + " " + srt.getDepPlandTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parseDate;
            parseDate = sdf.parse(date);
            Date currentDate = new Date();
            if (currentDate.after(parseDate)) {
                srtMapper.updateTicketStatus(srt.getSrtId());
            }
        }

        List<SrtDTO> updateSrtDTO = srtMapper.selectSrt(email);
        List<ResponseStorageDTO> returnList = new ArrayList<>();

        for (SrtDTO srt : updateSrtDTO) {
            List<ResponseSrtDetailDTO> srtDetailList = srtMapper.selectSrtDetailList(srt.getSrtId());
            ResponseStorageDTO res = new ResponseStorageDTO();
            BeanUtils.copyProperties(srt, res);
            res.setSrtDetailList(srtDetailList);
            returnList.add(res);
        }

        log.debug("srtDTO입니다 " + srtDTO);

        ResponseStorageListDTO responseStorageListDTO = new ResponseStorageListDTO();
        responseStorageListDTO.setStorageList(returnList);
        return responseStorageListDTO;
    }
}
