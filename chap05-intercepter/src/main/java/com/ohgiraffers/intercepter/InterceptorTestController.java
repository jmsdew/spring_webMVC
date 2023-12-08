package com.ohgiraffers.intercepter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/*")
public class InterceptorTestController {    // 요청이 들어왔을 떄 수행시간을 확인하는 로깅행위,조작행위 권한체크 등을 한다.
    
    @PostMapping("stopwatch")
    public String handlerMethod() throws InterruptedException {
        System.out.println("핸들러 메소드 호출함");    // 2번째 실행 true 일시 실행됨
        Thread.sleep(1000);// 1초의 대기

        return "result";  // result 로 매핑됨
    }
}
