package com.demo.dao;

import com.demo.pojo.Ebook;
import com.demo.pojo.Order2;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Order2DAO {
    public ArrayList<Order2> select(Order2 pojo) throws SQLException;//查询
    public int count(Order2 pojo) throws SQLException;//统计记录数
    public int insert(Order2 pojo) throws SQLException;//添加单个记录
    public int update(Order2 pojo) throws SQLException;//删除单个记录
    public int delete(Order2 pojo) throws SQLException;//更新单个记录
}
