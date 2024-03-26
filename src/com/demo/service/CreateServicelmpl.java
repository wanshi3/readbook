package com.demo.service;

import com.demo.dao.UserDAOImpl;
import com.demo.pojo.JsonData;
import com.demo.pojo.User;
import com.demo.util.DButil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateServicelmpl implements CreateService{
    boolean success;//??????????
    String msg;//??????????
    int total;//????????
    JsonData jd;//
    @Override
    public JsonData create(User obj){
        Connection conn = DButil.getConnection();//?????????
        UserDAOImpl bo = new UserDAOImpl(conn);//??????????????DAO????
        try{
            total = bo.insert(obj);;
            conn.commit();
            success = true;
            msg = "成功";
            jd = new JsonData(success,msg);
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
            msg = "链接失败";
            jd = new JsonData(success,msg);
            return jd;
        }finally{
            if(conn != null){DButil.closeConnection(conn);}
        }

    }
}
