package com.example.srt.controller;

import com.example.srt.common.ApiResponse;
import com.example.srt.common.ErrorCode;
import com.example.srt.dto.user.request.LoginDTO;
import com.example.srt.dto.user.request.UserDTO;
import com.example.srt.dto.user.request.VerifyDTO;
import com.example.srt.dto.user.response.ResponseUserDTO;
import com.example.srt.service.MailService;
import com.example.srt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@Tag(name = "User", description = "User 관련 API 입니다.")
public class UserController {

    @Autowired
    private MailService mailService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private RedisUtil redisUtil;

    @GetMapping("/code")
    @Operation(summary = "회원가입 인증 코드 보내기")
    @ResponseBody
    public ApiResponse<String> emailCode(@RequestParam String email) throws Exception {
        log.debug("email " + email);
        if (mailService.sendSimpleMessage(email) <= 0) {
            return ApiResponse.error(ErrorCode.SERVER_ERROR, "서버 에러");
        }
        return ApiResponse.success(null);
    }

    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "code : 10 필수 Parameter가 없습니다 <br> code : 20 이메일 형식으로 입력해주세요 <br> code : 30 비밀번호 최소 6자리 이상 입력해주세요 <br> code : 40 중복된 이메일 입니다.")
    @ResponseBody
    public ApiResponse<String> signUp(@Validated @RequestBody UserDTO dto) {
        if (userService.signUp(dto) <= 0) {
            return ApiResponse.error(ErrorCode.SERVER_ERROR, "서버 에러");
        }
        return ApiResponse.success(null);
    }

    @PostMapping("login")
    @Operation(summary = "로그인", description = "code : 10 필수 Parameter가 없습니다  <br> code : 20 이메일 형식으로 입력해주세요 <br> code : 30 비밀번호 최소 6자리 이상 입력해주세요 <br> code : 50 이메일 또는 비밀번호를 잘못 입력했습니다.")
    @ResponseBody
    public ApiResponse<ResponseUserDTO> logIn(@Validated @RequestBody LoginDTO dto, HttpSession session) {
        UserDTO userDTO = userService.logIn(dto);
        session.setAttribute("userEmail", userDTO.getEmail());
        ResponseUserDTO responseUserDTO = new ResponseUserDTO();
        responseUserDTO.setName(userDTO.getName());
        return ApiResponse.success(responseUserDTO);
    }

    @PostMapping("/verify")
    @Operation(summary = "코드 검증", description = "code : 10 필수 Parameter가 없습니다  <br> code : 60 코드가 일치하지 않습니다.")
    @ResponseBody
    public ApiResponse<String> verify(@Validated @RequestBody VerifyDTO dto){
        String serverCode = mailService.verify();
//        String email = redisUtil.getData(dto.getCode());
//        log.debug("email " + email);
        if(!serverCode.equals(dto.getCode())){
            return ApiResponse.error(ErrorCode.VERIFY_ERROR, "코드가 일치하지 않습니다.");
        }
        return ApiResponse.success(null);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    @ResponseBody
    public ApiResponse<String> logOut(HttpSession session){
        session.invalidate();
        return ApiResponse.success(null);
    }






}
