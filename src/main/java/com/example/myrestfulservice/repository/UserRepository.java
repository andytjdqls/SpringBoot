package com.example.myrestfulservice.repository;

import com.example.myrestfulservice.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //기존 상속 bean의 User
public interface UserRepository extends JpaRepository<User, Integer> {
}
