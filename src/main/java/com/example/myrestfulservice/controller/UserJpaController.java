package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.bean.Post;
import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.example.myrestfulservice.repository.PostRepository;
import com.example.myrestfulservice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//JPA DB 연동에 사용되는 Class
@RestController //RESTAPI 사용
@RequestMapping("/jpa") //prefix 값 지정
public class UserJpaController {

    private UserRepository userRepository;
    private PostRepository postRepository; //주입 받기

    public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }//Generate 기능으로 클래스 생성자 생성


    //전체 사용자 목록 조회
    // /jpa/users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    //사용자의 수 반환
    // /jpa/users/count
    @GetMapping("/users/count")
    public long retrieveUsersCount() {
        return userRepository.count();
    }


    //ID로 조회
    // /jpa/users/{id}
    @GetMapping("/users/{id}")
    public ResponseEntity retrieveUsersById(@PathVariable int id) {
        Optional <User> user = userRepository.findById(id);

        if (!user.isPresent()) { //존재 하지 않는 경우
            throw new UserNotFoundException("id" + id);
        }

        //entitiy모델 생성 후 반환
        EntityModel entityModel = EntityModel.of(user.get()); //Optional 데이터에서 실제 유저 값을 가져오기

        WebMvcLinkBuilder linTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        //hateoas 적용
        entityModel.add(linTo.withRel("all-users")); //all-users -> http://localhost:8080/users
        //링크 연동 RMM(리처드슨 성숙도 모델) Lv.2에 해당

        return ResponseEntity.ok(entityModel);
    }

    //삭제하기
    @DeleteMapping("/users/{id}")
    public void deleteIserById(@PathVariable int id){
        userRepository.deleteById(id);
    }

    //신규 사용자 생성하기
    // /jpa/users
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){ //JSON 타입
        User savedUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())//사용자 Id 가져오기
                .toUri(); //URL로 만들기

        return ResponseEntity.created(location).build();
    }

    //post 데이터 호출하기
    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){
        Optional <User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new UserNotFoundException("id-" + id);
        }

        return user.get().getPosts(); //user와 user의 post데이터 반환
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post){
        Optional <User> userOptional = userRepository.findById(id);
        //Optional Data 형식으로 유저 객체 값 찾기
        if(!userOptional.isPresent()){
            throw new UserNotFoundException("id=" + id);
        }
        User user = userOptional.get();

        post.setUser(user);

        //save 호출하기
        postRepository.save(post);

        //헤테오스 추가하기
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }



}
