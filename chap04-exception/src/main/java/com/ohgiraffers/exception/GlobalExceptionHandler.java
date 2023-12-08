package com.ohgiraffers.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice               // exception 이 발생했을 때 핸들링 해 주는  테이블을 만드는 어노테이션
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)  // 어떤  exception 을 처리할 지 입력 해줘야 함
    public String nullPointerExceptionHandler(NullPointerException e){
        System.out.println("global(서버에서 발생하는 모든) 레벨의 Exception 처리");
        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(Model model, MemberRegistException exception){
        System.out.println("Global 레벨의 exception 처리");
        model.addAttribute("exception", exception);
        return "error/memberRegist";
    }

    @ExceptionHandler(Exception.class)
    public String nullPointerExceptionHandler(Exception e){
        System.out.println("exception 발생함");
        return "error/default";
    }


}
