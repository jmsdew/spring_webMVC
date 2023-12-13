package com.example.login.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String nullPointerExceptionHandler(Exception e, Model model){
        System.out.println("exception 발생함");
        model.addAttribute("message", "오류가 발생했습니다.");
        return "error/errorMessage";
    }
}
