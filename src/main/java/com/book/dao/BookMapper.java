package com.book.dao;

import com.book.entity.Book;
import com.book.entity.Borrow;
import com.book.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: 周明源
 * @Date: 2023-03-17 17:45
 * @Description:
 **/
public interface BookMapper {
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "bid", property = "book_id"),
            @Result(column = "title", property = "book_name"),
            @Result(column = "time", property = "time"),
            @Result(column = "name", property = "student_name"),
            @Result(column = "sid", property = "student_id")
    })
    @Select("select * from borrow inner join student on borrow.sid = student.sid " +
            "inner join book on borrow.bid = book.bid")
    List<Borrow> getBorrowList();

    @Insert("insert into borrow(sid, bid, time) values(#{sid}, #{bid}, NOW())")
    void addBorrow(@Param("sid") int sid, @Param("bid") int bid);

    @Delete("delete from borrow where id = #{id}")
    void deleteBorrow(int id);

    @Select("select * from book")
    List<Book> getBookList();

    @Select("select * from student")
    List<Student> getStudentList();

    @Delete("delete from book where bid = #{bid}")
    void deleteBook(int bid);

    @Insert("insert into book(title, `desc`, price) values(#{title}, #{desc}, #{price})")
    void addBook(@Param("title") String title, @Param("desc") String desc, @Param("price") double price);

    @Select("select * from book where bid = #{bid}")
    Book getBook(int bid);

    @Update("update book set title = #{title},`desc` = #{desc},price = #{price} where bid = #{bid}")
    void updateBook(@Param("bid") int bid, @Param("title") String title, @Param("desc") String desc, @Param("price") double price);

    @Select("select * from student where sid = #{sid}")
    Student getStudent(int sid);

    @Update("update student set name = #{name}, `sex` = #{sex}, grade = #{grade}, password = #{password} where sid = #{sid}")
    void updateStudent(@Param("sid") int sid, @Param("name") String name, @Param("sex") String sex, @Param("grade") int grade, @Param("password") String password);

    @Select("select * from book where bid = #{search} or price = #{search} or title like CONCAT('%',#{search},'%') or `desc` like CONCAT('%',#{search},'%')")
    List<Book> queryBookList(@Param("search") String search);

    @Select("select * from student where sid = #{search} or grade = #{search} or name like CONCAT('%',#{search},'%') or sex = #{search}")
    List<Student> queryStudentList(String search);

    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "bid", property = "book_id"),
            @Result(column = "title", property = "book_name"),
            @Result(column = "time", property = "time"),
            @Result(column = "name", property = "student_name"),
            @Result(column = "sid", property = "student_id")
    })
    @Select("select * from borrow inner join student on borrow.sid = student.sid " +
            "inner join book on borrow.bid = book.bid where borrow.sid = #{search} or book.title like CONCAT('%',#{search},'%') or student.name like CONCAT('%',#{search},'%')")
    List<Borrow> queryBorrowList(String search);
}
