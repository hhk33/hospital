package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select id from user where name=#{name} and password=#{password}")
    String selectID(User user);

    @Select("select name from user where id=#{id}")
    String selectName(User user);
}
