package com.example.myrestfulservice.dao;


import com.example.myrestfulservice.bean.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDaoService {
    private static List<User> users = new ArrayList<>();


    private static int userCount = 3;

    static{
        users.add(new User(1, "Kenneth", new Date(), "test1", "111111-1111111"));
        users.add(new User(2, "Alice", new Date(), "test2", "222222-1111111"));
        users.add(new User(3, "Elena", new Date(), "test3", "333333-1111111"));
    }

    //API 생성
    //모든 값 조회
    public List<User> findAll(){
        return users;
    }

    //저장
    public User save(User user){
        if(user.getId() == null){
            user.setId(++userCount);
        }

        if(user.getJoinDate() == null){
            user.setJoinDate(new Date());
        }
        users.add(user);
        return user;
    }

    //하나 조회
    public User findOne(int id){
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    //삭제
    public User deleteById(int id){
        Iterator<User> Iterator = users.iterator();

        while (Iterator.hasNext()){
            User user = Iterator.next();
            if(user.getId() == id){
                Iterator.remove();
                return user;
            }
        }
        return null;
    }

}
