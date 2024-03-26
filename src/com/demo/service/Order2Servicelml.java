package com.demo.service;

import com.demo.dao.Order2DAOImpl;
import com.demo.pojo.JsonData;
import com.demo.pojo.Order2;;
import com.demo.util.DButil;
import java.sql.Connection;
import java.sql.SQLException;

public class Order2Servicelml {
    boolean success;//操作成功与否
    String msg;//返回的结果信息
    int total;//返回记录数
    JsonData jd;//将数据转换为指定JSON格式的对象，并返回给Controller层
    public JsonData insert(Order2 obj){
        Connection conn = DButil.getConnection();//数据库连接
        Order2DAOImpl bookBO = new Order2DAOImpl(conn);//业务层对象，用于调用DAO层方法
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
