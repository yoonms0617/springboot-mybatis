package com.example.springbootmybatis.mapper;

import com.example.springbootmybatis.user.model.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserJavaMapper {

    @Insert("INSERT INTO USER(user_name, user_email) VALUES(#{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(User user);

    @Select("SELECT * FROM USER WHERE user_id = #{id}")
    @Results(id = "UserMap", value = {
            @Result(property = "name", column = "user_name"),
            @Result(property = "email", column = "user_email")
    })
    Optional<User> findById(Long id);

    @Select("SELECT * FROM USER WHERE user_email = #{email}")
//    @Results({
//            @Result(property = "name", column = "user_name"),
//            @Result(property = "email", column = "user_email")
//    })
    @ResultMap("UserMap")
    Optional<User> findByEmail(String email);


    @Select("SELECT * FROM USER")
    @Results({
            @Result(property = "name", column = "user_name"),
            @Result(property = "email", column = "user_email")
    })
    List<User> findAll();

}
