package com.book.dao;

import com.book.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: 周明源
 * @Date: 2023-03-12 12:41
 * @Description:
 **/
public interface UserMapper {
    @Select("select * from admin where username=#{username} and password=#{password}")
    User getUser(@Param("username") String username, @Param("password") String password);
}
