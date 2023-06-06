package com.book.service;

import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * @Author: 周明源
 * @Date: 2023-03-13 19:14
 * @Description:
 **/
public interface BookService {
    List<Borrow> getBorrowList();

    void addBorrow(int sid, int bid);

    void deleteBorrow(int id);

    Map<Book,Boolean> getBookList();

    List<Book> getActiveBookList();

    List<Student> getStudentList();

    Book getBook(int bid);

    void deleteBook(int bid);

    void addBook(String title, String desc, double price);

    void updateBook(int bid, String title, String desc, double price);

    Student getStudent(int sid);

    void updateStudent(int sid, String name, String sex, int grade, String password);

    Map<Book,Boolean> queryBookList(String search);

    List<Student> queryStudentList(String search);

    List<Borrow> queryBorrowList(String search);
}
