package com.ohgiraffers.viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
                                // localhost:8080(도메인) == /  같은 요청 -- 기본루트가 template 하위 이기 때문에 static에 html 을 생성하면 실행 불가
                                // resource 에서
                                // static 에는 js나 css 같은 정적 자원들을 미리 업로드해 가져오는 방식으로 사용하기 위해 사용하고 ,
                                // templates 는 동적 자원들을 관리하기 위해 사용한다.
    @RequestMapping(value = {"/","/main"})
    public String main(){return "main";}



}
