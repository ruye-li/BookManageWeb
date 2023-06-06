package com.book.entity;

import lombok.Data;

/**
 * @Author: 周明源
 * @Date: 2023-03-13 18:59
 * @Description: 借阅信息
 **/
@Data
public class Borrow {
    int id;
    int book_id;
    String book_name;
    String time;
    String student_name;
    String student_id;
}
