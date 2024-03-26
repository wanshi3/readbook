package com.demo.service;

import com.demo.dao.BookDAOImpl;
import com.demo.dao.EbookDAOImpl;
import com.demo.pojo.Book;
import com.demo.pojo.Ebook;
import com.demo.pojo.JsonData;
import com.demo.util.DButil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class EBookIndexServiceImpl implements EBookIndexService{
    boolean success;//操作成功与否
    String msg;//返回的结果信息
    int total;//返回记录数
    JsonData jd;//将数据转换为指定JSON格式的对象，并返回给Controller层
    //查询业务
    @Override
    public JsonData select(Ebook obj)
    {
        Connection conn = DButil.getConnection();//数据库连接
        EbookDAOImpl bo = new EbookDAOImpl(conn);//业务层对象，用于调用DAO层方法
        try{
            ArrayList<Ebook> rows = new ArrayList<Ebook>();
            //返回记录集
            rows = bo.select(obj);//返回记录集
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
    public JsonData select2(Ebook obj)
    {
        Connection conn = DButil.getConnection();//数据库连接
        EbookDAOImpl bo = new EbookDAOImpl(conn);//业务层对象，用于调用DAO层方法
        try{
            ArrayList<Ebook> rows = new ArrayList<Ebook>();
            //返回记录集
            rows = bo.select2(obj);//返回记录集
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
    //删除
    @Override
    public JsonData delete(Ebook obj)
    {
        Connection conn = DButil.getConnection();//数据库连接
        EbookDAOImpl stuBO = new EbookDAOImpl(conn);//业务层对象，用于调用DAO层方法
        try{

            //删除student表中指定id的记录
            int i = stuBO.delete(obj);
            conn.commit();
            success = true;
            msg = "成功删除["+i+"]条记录";
            jd = new JsonData(success,msg);
            return jd;
        }catch(Exception e){
            try {conn.rollback();}
            catch (SQLException e1) {e1.printStackTrace();}
            e.printStackTrace();
            success = false;
            msg = "删除失败";
            jd = new JsonData(success,msg);
            return jd;
        }finally{
            if(conn != null){DButil.closeConnection(conn);}
        }
    }
    /**
     * 更新业务，此处做2件事：
     * 1、调用DAO层的update方法，返回操作结果
     * 2、将操作结果拼接成success=true,msg="更新成功"格式
     */
    @Override
    public JsonData update(Ebook obj){
        Connection conn = DButil.getConnection();//数据库连接
        EbookDAOImpl bo = new EbookDAOImpl(conn);//业务层对象，用于调用DAO层方法
        try{
            int i = bo.update(obj);//i为影响的行数
            conn.commit();
            success = true;
            msg = "成功更新["+i+"]条记录";
            jd = new JsonData(success,msg);
            return jd;
        }catch(Exception e){
            try {conn.rollback();}
            catch (SQLException e1) {e1.printStackTrace();}
            e.printStackTrace();
            success = false;
            msg = "更新失败";
            jd = new JsonData(success,msg);
            return jd;
        }finally{
            if(conn != null){DButil.closeConnection(conn);}
        }
    }
    //插入
    @Override
    public JsonData insert(Ebook obj){
        Connection conn = DButil.getConnection();//数据库连接
        EbookDAOImpl bookBO = new EbookDAOImpl(conn);//业务层对象，用于调用DAO层方法
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
