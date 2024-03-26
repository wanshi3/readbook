package com.demo.service;

import com.demo.pojo.Book;
import com.demo.pojo.JsonData;

public interface BookIndexService {
    public JsonData login(Book obj);//查找
    public JsonData insert(Book obj);//添加
    public JsonData delete(Book obj);//删除
    public JsonData update(Book obj);//修改

}
