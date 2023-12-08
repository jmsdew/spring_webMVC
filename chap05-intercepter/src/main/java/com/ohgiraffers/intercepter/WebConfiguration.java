package com.ohgiraffers.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // spring 컨테이너에서 bean을 관리할 수 있게 됨
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired // 의존성 주입
    private StopWatchInterceptor stopWatchInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(stopWatchInterceptor)
                .addPathPatterns("/stopwatch")
                .excludePathPatterns("/css/**")          // 해당 역할을 제외하겠다는 명령 css 전체, 사진 전체, 스크립트 전체, 에러 전체
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/error");
    }
}
