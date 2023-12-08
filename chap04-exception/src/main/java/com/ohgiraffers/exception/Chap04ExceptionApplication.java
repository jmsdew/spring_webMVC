package com.ohgiraffers.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 
public class Chap04ExceptionApplication {             // 경로가 바뀌면 작동하지 않음 바꾸려면 @SpringBootApplication(scanBasePakages="주소")
														// 해당 패키지 안에 있는 클래스만 범위로 가질 수 있음 .  exception 안에 test 폴더를 만들어서 넣으면 test폴더 내부만 작동함

	public static void main(String[] args) {
		SpringApplication.run(Chap04ExceptionApplication.class, args);
	}

}
