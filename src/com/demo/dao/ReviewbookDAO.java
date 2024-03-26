package com.demo.dao;

import com.demo.pojo.Read2;
import com.demo.pojo.Reviewbook;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReviewbookDAO {
    public ArrayList<Reviewbook> select(Reviewbook pojo) throws SQLException;//查询
    public int count(Reviewbook pojo) throws SQLException;//统计记录数
    public int insert(Reviewbook pojo) throws SQLException;//添加单个记录
    public int update(Reviewbook pojo) throws SQLException;//删除单个记录
    public int delete(Reviewbook pojo) throws SQLException;//更新单个记录
    public ArrayList<Reviewbook> selectreview(Reviewbook pojo) throws SQLException;
}
