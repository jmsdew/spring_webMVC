package com.ohgiraffers.request;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
* DispatcherServlet 은 웹 요청을 받는 즉시 @Controller 가 달린 컨트롤러 클래스에 처리를 위임한다.
* 그 과정은 컨트롤러 클래스의 핸들러 메소드에 선언된 다양한 @RequestMapping 설정을 따르게 된다.
* */
@Controller
public class MethodMappingTestController {

    // 1. 메소드 방식 미지정
    // 요청 url 설정
    @RequestMapping("/menu/regist")
    public String registMenu(Model model){
        /*
        * Model 객체에 addAttribute 메소드를 이용해
        * key, value 를 추가하면 추후 view 에서 사용할 수 있다.
        * */
        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메소드 호출");
        // 반환 하고자 하는 view 의 경로를 포함한 이름을 작성한다.
        // resource/templates 하위 부터의 경로를 작성한다.
        // chap03-view-resolver 에서 다룬다.

        return "mappingResult";
    }
    
    
    /*
    * 2. 메소드 방식 지정
    * 요청 url 을 value 속성에 method 속성에 설정
    * */
    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET)        // get 요청만 허용함  RequestMethod.GET
    public String modifyMenu(Model model){
        model.addAttribute("message", "Get 방식의 메뉴 수정 호출");

        return "mappingResult";
    }

    /*
    * 3. 요청 메소드 전용 어노테이션
    * 요청 메소드        어노테이션
    * post              @PostMapping
    * Get               @GetMapping
    * PUT               @PutMapping
    * Delete            @DeleteMapping
    * Path              @PatchMapping
    * */
    
    @GetMapping("/menu/delete")
    public String getDeleteMenu(Model model){
        model.addAttribute("message", "Get 방식의 삭제용 핸들러 메소드 호출");
        return "mappingResult";
    }

    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model model){
        model.addAttribute("message", "post 방식의 삭제용 핸들러 메소드 호출");
        return "mappingResult";
    }


}
