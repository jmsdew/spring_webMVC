package com.example.login.model;

import com.example.login.dto.LoginUserDTO;
import com.example.login.dto.SignupDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int regist(SignupDTO signupDTO);

    LoginUserDTO findByUserName(String username);
}
