package com.book.service;

import jakarta.servlet.http.HttpSession;

/**
 * @Author: 周明源
 * @Date: 2023-03-12 18:03
 * @Description:
 **/
public interface UserService {
    boolean auth(String username, String password, HttpSession session);
}
