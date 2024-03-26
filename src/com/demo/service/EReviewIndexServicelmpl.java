package com.demo.service;

import com.demo.dao.ReviewbookDAOImpl;
import com.demo.dao.ReviewebookDAOImpl;
import com.demo.pojo.JsonData;
import com.demo.pojo.Reviewbook;
import com.demo.pojo.Reviewebook;
import com.demo.util.DButil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EReviewIndexServicelmpl {
    boolean success;//操作成功与否
    String msg;//返回的结果信息
    int total;//返回记录数
    JsonData jd;//将数据转换为指定JSON格式的对象，并返回给Controller层
    public JsonData select(Reviewebook obj) {
        Connection conn = DButil.getConnection();//数据库连接
        ReviewebookDAOImpl bo = new ReviewebookDAOImpl(conn);//业务层对象，用于调用DAO层方法
        try{
            ArrayList<Reviewebook> rows = new ArrayList<Reviewebook>();
            //返回记录集
            rows = bo.selectreview(obj);//返回记录集
            total = bo.count(obj);
            conn.commit();
            success = true;
            msg = "查询成功";
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
            msg = "查询失败";
            jd = new JsonData(success,msg);
            return jd;
        }finally{
            if(conn != null){DButil.closeConnection(conn);}
        }
    }
    public JsonData insert(Reviewebook obj){
        Connection conn = DButil.getConnection();//数据库连接
        ReviewebookDAOImpl bookBO = new ReviewebookDAOImpl(conn);//业务层对象，用于调用DAO层方法
        try{
            int i = bookBO.insert(obj);//i为影响的行数
            conn.commit();
            success = true;
            msg = "成功添加["+i+"]条记录";
            jd = new JsonData(success,msg);
            return jd;
        }catch(Exception e){
            try {conn.rollback();}
            catch (SQLException e1) {e1.printStackTrace();}
            e.printStackTrace();
            success = false;
            msg = "添加失败";
            JsonData jrs = new JsonData(success,msg);
            return jrs;
        }finally{
            if(conn != null){DButil.closeConnection(conn);}
        }
    }
}
