package com.ohgiraffers.securitysession.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")  // void 로 설정 해 놓으면 login.html 을 추적해 들어감
    public void login(){

    }

    @GetMapping("/fail")
    public ModelAndView loginFail(@RequestParam String message, ModelAndView mv){
        mv.addObject("message", message);
        mv.setViewName("/auth/fail");
        return mv;
    }
}
