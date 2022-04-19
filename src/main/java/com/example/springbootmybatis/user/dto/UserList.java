package com.example.springbootmybatis.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserList {

    private List<UserResponse> users;

    public UserList(List<UserResponse> users) {
        this.users = users;
    }

}
