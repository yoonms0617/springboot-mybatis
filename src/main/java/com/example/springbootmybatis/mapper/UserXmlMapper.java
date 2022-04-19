package com.example.springbootmybatis.mapper;

import com.example.springbootmybatis.user.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserXmlMapper {

    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

}
