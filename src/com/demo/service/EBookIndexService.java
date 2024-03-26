package com.demo.service;
import com.demo.pojo.Ebook;
import com.demo.pojo.JsonData;

public interface EBookIndexService {
    public JsonData select(Ebook obj);//查找
    public JsonData insert(Ebook obj);//添加
    public JsonData delete(Ebook obj);//删除
    public JsonData update(Ebook obj);//修改
}
