package com.demo.dao;

import com.demo.pojo.Read2;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Read2DAO {
    public ArrayList<Read2> select(Read2 pojo) throws SQLException;//查询
    public int count(Read2 pojo) throws SQLException;//统计记录数
    public int insert(Read2 pojo) throws SQLException;//添加单个记录
    public int update(Read2 pojo) throws SQLException;//删除单个记录
    public int delete(Read2 pojo) throws SQLException;//更新单个记录
}
