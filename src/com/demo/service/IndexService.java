package com.demo.service;

import com.demo.pojo.JsonData;
import com.demo.pojo.User;

/**
 * 每个Service对应前端一个页面
 * 一个页面中需要实现的业务逻辑都在这里定义
 * 所以，我们建议，将这些方法的名称命名为与业务相关的名称
 */

public interface IndexService {
//    public JsonData selectRnd(User obj);//随机查询业务
    public JsonData login(User obj);//登录业务

}