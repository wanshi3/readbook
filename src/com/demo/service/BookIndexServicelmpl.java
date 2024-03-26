package com.demo.service;

import com.demo.dao.BookDAOImpl;
import com.demo.dao.UserDAOImpl;
import com.demo.pojo.Book;
import com.demo.pojo.JsonData;
import com.demo.pojo.Reviewbook;
import com.demo.pojo.User;
import com.demo.util.DButil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookIndexServicelmpl implements BookIndexService{
    boolean success;//操作成功与否
    String msg;//返回的结果信息
    int total;//返回记录数
    JsonData jd;//将数据转换为指定JSON格式的对象，并返回给Controller层

    public JsonData index(Book obj) {
        Connection conn = DButil.getConnection();//数据库连接
        BookDAOImpl bo = new BookDAOImpl(conn);//业务层对象，用于调用DAO层方法
        try{
            ArrayList<Book> rows = new ArrayList<Book>();
            //返回记录集
            if ( bo.select(obj) != null) {
                total =  bo.select(obj).size();
                rows = bo.select(obj);
                // 在这里处理 ArrayList 的数据
            } else {
                // 处理 ArrayList 为空的情况
                total=0;
            }
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
    @Override
    public JsonData login(Book obj) {
        Connection conn = DButil.getConnection();//数据库连接
        BookDAOImpl bo = new BookDAOImpl(conn);//业务层对象，用于调用DAO层方法
        try{
            ArrayList<Book> rows = new ArrayList<Book>();
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
    @Override
    public JsonData insert(Book obj){
        Connection conn = DButil.getConnection();//数据库连接
        BookDAOImpl bookBO = new BookDAOImpl(conn);//业务层对象，用于调用DAO层方法
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
    /**
     * 删除业务，此处做这几件事：
     * 1、根据id查询对应记录的userId
     * 2、根据userId删除user表中记录
     * 3、根据id删除student表中记录
     * 4、将操作结果拼接成success=true,msg="删除新成功"格式
     */
    @Override
    public JsonData delete(Book obj){
        Connection conn = DButil.getConnection();//数据库连接
        BookDAOImpl stuBO = new BookDAOImpl(conn);//业务层对象，用于调用DAO层方法
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

    @Override
    public JsonData update(Book obj){
        Connection conn = DButil.getConnection();//数据库连接
        BookDAOImpl bo = new BookDAOImpl(conn);//业务层对象，用于调用DAO层方法
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


}

