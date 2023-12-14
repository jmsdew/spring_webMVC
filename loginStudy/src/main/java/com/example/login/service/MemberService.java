package com.example.login.service;

import com.example.login.dto.LoginUserDTO;
import com.example.login.dto.SignupDTO;
import com.example.login.model.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class MemberService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public int regist(SignupDTO signupDTO){
        signupDTO.setUserPass(encoder.encode(signupDTO.getUserPass()));  // 패스워드를 꺼내 인코딩해서 다시 넣어줌
        int result = userMapper.regist(signupDTO);

        return result;

    }

    public LoginUserDTO findByUserName(String username) {

        LoginUserDTO login = userMapper.findByUserName(username);
        if(!Objects.isNull(login)){
            return login;
        }else {
            return null;
        }
    }
}
