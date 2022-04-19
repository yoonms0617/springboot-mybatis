package com.example.springbootmybatis.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    // PK
    private Long id;

    // user_name column
    private String name;

    // user_email column
    private String email;

    @Builder
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
