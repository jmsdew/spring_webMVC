package com.ohgiraffers.fileupload;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {    // 디스패쳐 서블릿이 응답하지않게 만듦 -- 정적 자원 등록
        registry.addResourceHandler("/img/**")  // 정적 리소스에 대한 핸들러 추가하는 메소드 img/** 요청시 응답 한다.
                .addResourceLocations("classpath:/static/img/") // 해당 요청에 매핑될 경로   // classpath -- 서버의 절대 경로  서버가 어디에 있던 src 까지 옴
                .setCachePeriod(20);  // 클라이언트의 캐싱 유지 시작
        
    }
}
