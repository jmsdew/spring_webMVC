package com.ohgiraffers.securitysession.auth.service;

import com.ohgiraffers.securitysession.auth.model.AuthDetails;
import com.ohgiraffers.securitysession.user.model.dto.LoginUserDTO;
import com.ohgiraffers.securitysession.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    /*
    * AuthenticationProvider 에서 호출하는 메소드로
    * login 요청시 전달된 사용자의 id를 매개변수로 db에서 사용자의 정보를 찾는다.
    * 전달된 사용자의 개체 타입은 userDetails 를 구현한 구현체가 되어야 한다.
    * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserDTO login = memberService.findByUserName(username);

        if (Objects.isNull(login)){
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
        }

        return new AuthDetails(login);
    }
}
