package com.example.myrestfulservice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data //(lombok)get과 set가 자동으로 만들어짐
@AllArgsConstructor //생성자 자동 생성 어노테이션
public class HelloWorldBean {
    private String message;

//    public HelloWorldBean(String message) {
//        this.message = message;
//    } //원래 생성자 생성}
}