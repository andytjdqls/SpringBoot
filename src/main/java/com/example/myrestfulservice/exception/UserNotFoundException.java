package com.example.myrestfulservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//User id가 없을 때에도 200 OK가 뜨는 것을 방지
@ResponseStatus(HttpStatus.NOT_FOUND) //상태 반환
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
