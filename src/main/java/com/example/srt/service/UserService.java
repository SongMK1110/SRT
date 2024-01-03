package com.example.srt.service;

import com.example.srt.common.CustomException;
import com.example.srt.dto.user.request.LoginDTO;
import com.example.srt.dto.user.request.UserDTO;
import com.example.srt.dto.user.response.ResponseUserDTO;
import com.example.srt.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public int signUp(UserDTO dto) {
        if (userMapper.checkUserEmail(dto) > 0) {
            throw new CustomException("이메일 중복");
        }
        int result = userMapper.insertSignUp(dto);
        if (result <= 0) {
            throw new CustomException("서버 에러");
        }
        return result;
    }

    public UserDTO logIn(LoginDTO dto){
        UserDTO result = userMapper.login(dto);
        if (result == null){
            throw  new CustomException("로그인 오류");
        }
        return result;
    }
}
