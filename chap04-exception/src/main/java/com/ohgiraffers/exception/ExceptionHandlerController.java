package com.ohgiraffers.exception;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionHandlerController {

    @GetMapping("controller-null")
    public String nullPointerExceptionTest(){
        // exception 이 발생해 return 으로 넘어가지 못하고 에러를 던짐
        // str이 null이므로 String.charAt(int)를 호출할 수 없습니다 Cannot invoke "String.charAt(int)" because "str" is null
        String str = null;
        System.out.println(str.charAt(0));
        return "/main";   // 기본루트 - localhost:8080
    }

    @ExceptionHandler(NullPointerException.class)  // exception 처리의 우선권을 가짐 - 없으면 ControllerAdvice 를 찾아감
    public String nullPointerExceptionHandler(NullPointerException e){
        System.out.println("controller 레벨의 exception 처리");
        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class)
    public String userExceptionHandler(Model model, MemberRegistException exception){
        System.out.println("contoller 레벨의 exception 처리");
        model.addAttribute("exception", exception);
        return "error/memberRegist";
    }

    @GetMapping("controller-user")
    public String userException() throws MemberRegistException{
        boolean check = true;
        if(check){
            throw new MemberRegistException("입사가 불가능 합니다.");
        }
        return "/";
    }
}
