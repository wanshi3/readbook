package com.demo.dao;

import com.demo.pojo.Book;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookDAO {
    public ArrayList<Book> select(Book pojo) throws SQLException;//查询
    public int count(Book pojo) throws SQLException;//统计记录数
    public int insert(Book pojo) throws SQLException;//添加单个记录
    public int update(Book pojo) throws SQLException;//更新单个记录
    public int delete(Book pojo) throws SQLException;//删除单个记录
}
