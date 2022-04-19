package com.example.springbootmybatis.user.controller;

import com.example.springbootmybatis.user.application.UserService;
import com.example.springbootmybatis.user.dto.UserList;
import com.example.springbootmybatis.user.dto.UserRequest;
import com.example.springbootmybatis.user.dto.UserResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<Long> save(@RequestBody UserRequest userRequest) {
        Long save = userService.save(userRequest);
        return ResponseEntity.ok(save);
    }

    @GetMapping("/find-id/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Long id) {
        UserResponse userResponse = userService.findById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/find-email/{email}")
    public ResponseEntity<UserResponse> findByEmail(@PathVariable("email") String email) {
        UserResponse userResponse = userService.findByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/find/all")
    public ResponseEntity<UserList> findAll() {
        UserList userList = userService.findAll();
        return ResponseEntity.ok(userList);
    }

}
