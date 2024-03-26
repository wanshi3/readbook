package com.demo.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.demo.dao.UserDAOImpl;
import com.demo.pojo.JsonData;
import com.demo.pojo.User;
import com.demo.util.DButil;

public class IndexServiceImpl implements IndexService{
    /**
     * 随机查询业务，此处做3件事：
     * 1、调用DAO层的select方法，返回查询到的记录集rows
     * 2、调用DAO层的count方法，返回查询到的记录数total
     * 3、利用JsonData将记录数total与记录集studentList拼接成“total=50,rows=[{},{}...{}]”格式
     */
    boolean success;//操作成功与否
    String msg;//返回的结果信息
    int total;//返回记录数
    JsonData jd;//将数据转换为指定JSON格式的对象，并返回给Controller层

//    @Override
//    public JsonData selectRnd(User obj) {
//        Connection conn = DButil.getConnection();//数据库连接
//        UserDAOImpl bo = new UserDAOImpl(conn);//业务层对象，用于调用DAO层方法
//        try{
//            ArrayList<User> rows = new ArrayList<User>();
//            rows = bo.selectRnd(obj);//返回记录集
//            conn.commit();
//            success = true;
//            msg = "查询成功";
//            jd = new JsonData(success,msg,total,rows);
//            return jd;
//        }catch(Exception e){
//            try {
//                conn.rollback();
//            }
//            catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//            success = false;
//            msg = "查询失败";
//            jd = new JsonData(success,msg);
//            return jd;
//        }finally{
//            if(conn != null){DButil.closeConnection(conn);}
//        }
//    }

    @Override
    public JsonData login(User obj) {
        Connection conn = DButil.getConnection();//数据库连接
        UserDAOImpl bo = new UserDAOImpl(conn);//业务层对象，用于调用DAO层方法
        try{
            ArrayList<User> rows = new ArrayList<User>();
           //返回记录集
            if ( bo.loginCheck(obj) != null) {
                total =  bo.loginCheck(obj).size();
                rows = bo.loginCheck(obj);
                // 在这里处理 ArrayList 的数据
            } else {
                // 处理 ArrayList 为空的情况
                total=0;
            }
            conn.commit();
            success = true;
            msg = "sucess";
            jd = new JsonData(success,msg,total,rows);
            return jd;
        }catch(Exception e){
            try {
                conn.rollback();
            }
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            success = false;
            msg = "fail";
            jd = new JsonData(success,msg);
            return jd;
        }finally{
            if(conn != null){DButil.closeConnection(conn);}
        }
    }
}