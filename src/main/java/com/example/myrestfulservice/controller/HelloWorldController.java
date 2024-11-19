package com.example.myrestfulservice.controller;


import com.example.myrestfulservice.bean.HelloWorldBean;
import org.apache.logging.log4j.message.Message;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // GET
    // URI -> /hello-world
    // @RequestMapping(method-RequestMethod.GET, path="/hello-world")
    // 위의 지정을 좀더 간단하게 변환

    @GetMapping(path = "/hello-world") //API 주소 지정
    public String helloworld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean") //API 주소 지정
    public HelloWorldBean helloworldBean(){
        return new HelloWorldBean("Hello World!!");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}") //API 주소 지정
    public HelloWorldBean helloworldBeanPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloworlInternationalized(
        @RequestHeader(name = "Accept-language", required = false) Locale locale){
            return messageSource.getMessage("greeting.message", null, locale);
        }
    }