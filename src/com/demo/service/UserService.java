package com.demo.service;


import com.demo.pojo.JsonData;
import com.demo.pojo.User;

public interface UserService {
    public JsonData select(User obj);//查找
    public JsonData insert(User obj);//添加
    public JsonData delete(User obj);//删除
    public JsonData update(User obj);//修改
}
