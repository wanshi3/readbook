package com.demo.dao;

import com.demo.pojo.Reviewbook;
import com.demo.pojo.Reviewebook;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReviewebookDAO {
    public ArrayList<Reviewebook> select(Reviewebook pojo) throws SQLException;//查询
    public int count(Reviewebook pojo) throws SQLException;//统计记录数
    public int insert(Reviewebook pojo) throws SQLException;//添加单个记录
    public int update(Reviewebook pojo) throws SQLException;//删除单个记录
    public int delete(Reviewebook pojo) throws SQLException;//更新单个记录
}
