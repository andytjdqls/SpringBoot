package com.example.myrestfulservice.repository;

import com.example.myrestfulservice.bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //기존 상속 bean의 Post
public interface PostRepository extends JpaRepository<Post, Integer> {
}
