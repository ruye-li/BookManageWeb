package com.book.entity;

import lombok.Data;

/**
 * @Author: 周明源
 * @Date: 2023-03-12 12:18
 * @Description: User类
 **/
@Data
public class User {
    int id;
    String username;
    String nickname;
    String password;
}
