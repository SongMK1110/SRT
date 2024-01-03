package com.example.srt.mapper;

import com.example.srt.dto.user.request.LoginDTO;
import com.example.srt.dto.user.request.UserDTO;

public interface UserMapper {

    int insertSignUp(UserDTO dto);
    int checkUserEmail(UserDTO dto);
    UserDTO login(LoginDTO dto);
}