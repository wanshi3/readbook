package com.demo.dao;

import com.demo.pojo.Ebook;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EbookDAO {
    public ArrayList<Ebook> select(Ebook pojo) throws SQLException;//查询
    public int count(Ebook pojo) throws SQLException;//统计记录数
    public int insert(Ebook pojo) throws SQLException;//添加单个记录
    public int delete(Ebook pojo) throws SQLException;//删除单个记录
    public int update(Ebook pojo) throws SQLException;//更新单个记录
}
