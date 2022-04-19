package com.example.springbootmybatis.user.application;

import com.example.springbootmybatis.mapper.UserJavaMapper;
import com.example.springbootmybatis.mapper.UserXmlMapper;
import com.example.springbootmybatis.user.dto.UserList;
import com.example.springbootmybatis.user.dto.UserRequest;
import com.example.springbootmybatis.user.dto.UserResponse;
import com.example.springbootmybatis.user.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserJavaMapper userJavaMapper;
    private final UserXmlMapper userXmlMapper;

    public Long save(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .build();
        // userXmlMapper.save(user);
        userJavaMapper.save(user);
        return user.getId();
    }

    public UserResponse findById(Long id) {
        // User user = userXmlMapper.findById(id).orElseThrow();
        User user = userJavaMapper.findById(id).orElseThrow();
        return new UserResponse(user.getName(), user.getEmail());
    }

    public UserResponse findByEmail(String email) {
        // User user = userXmlMapper.findByEmail(email).orElseThrow();
        User user = userJavaMapper.findByEmail(email).orElseThrow();
        return new UserResponse(user.getName(), user.getEmail());
    }

    public UserList findAll() {
        // List<User> users = userXmlMapper.findAll();
        List<User> users = userJavaMapper.findAll();
        List<UserResponse> result = users.stream()
                .map(user -> new UserResponse(
                        user.getName(),
                        user.getEmail()
                )).collect(Collectors.toList());
        return new UserList(result);
    }

}
