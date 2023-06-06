package com.book.servlet.auth;
/**
 * @Author: 周明源
 * @Date: 2023-03-12 20:00
 * @Description: ${description}
 **/

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("user");
        Cookie cookie_username = new Cookie("username", null);
        cookie_username.setMaxAge(0);
        resp.addCookie(cookie_username);
        Cookie cookie_password = new Cookie("password", null);
        cookie_password.setMaxAge(0);
        resp.addCookie(cookie_password);
        resp.sendRedirect("login");
    }
}
