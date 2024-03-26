package com.demo.dao;

import com.demo.pojo.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {
    public ArrayList<User> select(User pojo) throws SQLException;//查询
    public int count(User pojo) throws SQLException;//统计记录
    public int insert(User pojo) throws SQLException;// 添加单个记录
    public int update(User pojo) throws SQLException;//更新单个记录
    public int delete(User pojo) throws SQLException;//删除单个记录
    public ArrayList<User> loginCheck(User pojo) throws SQLException;//登入查询
}
