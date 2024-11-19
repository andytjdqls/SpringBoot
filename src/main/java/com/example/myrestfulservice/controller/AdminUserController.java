package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.bean.AdminUser;
import com.example.myrestfulservice.bean.AdminUserV2;
import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.dao.UserDaoService;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin") //이 클래스는 무조건 앞에 admin이 붙여짐
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }//우클릭 -> Generate -> Constructer ->  선택 후 만들기

    /*@GetMapping("/v1/users/{id}")*/
    /*@GetMapping(value = "/users/{id}", params = "version=1")*/
    /*@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")*/
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {
        User user =  service.findOne(id);
        AdminUser adminUser = new AdminUser();
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        else {
            /*adminUser.setName(user.getName());*/ //하나만 지정
            BeanUtils.copyProperties(user,adminUser); //속성 전체 복사
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    //일반 브라우저에서 사용가능
    /*@GetMapping("/v2/users/{id}")*/ //end point 이용 버전 설정 ex) Twitter
    /*@GetMapping(value = "/users/{id}", params = "version=2")*/ //params 이용 버전 설정 ex) Amazon

    //일반 브라우저에서 사용 불가
    /*@GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")*/ //header 이용 버전 설정 ex) Microsoft
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")//mime type 이용 버전 설정 ex) gitHub
    public MappingJacksonValue retrieveUser4Adminv2(@PathVariable int id) {
        User user =  service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        else {
            /*adminUser.setName(user.getName());*/ //하나만 지정
            BeanUtils.copyProperties(user,adminUser); //속성 전체 복사
            adminUser.setGrade("VIP"); //grade라는 새로운 추가 속성
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }



    // -> /admin/users
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers4Admin() {
        List<User> users =  service.findAll();
        List<AdminUser> adminUsers = new ArrayList<>();
        AdminUser adminUser = null;
        for(User user : users){
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);

            adminUsers.add(adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }
}
