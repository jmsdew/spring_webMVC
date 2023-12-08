package com.ohgiraffers.intercepter;

import org.springframework.stereotype.Service;

@Service
public class MenuService {
    public void method(){
        System.out.println("메소드 호출 확인");
    }
}
