package com.book.entity;

import lombok.Data;

/**
 * @Author: 周明源
 * @Date: 2023-03-17 17:43
 * @Description:
 **/
@Data
public class Book {
    int bid;
    String title;
    String desc;
    double price;
}
