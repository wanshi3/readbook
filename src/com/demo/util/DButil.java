package com.demo.util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * ������ݿ�����Ӻ͹ر�
 */

public class DButil {
    /**
     * ����ȡ Properties����Ϊ��̬�����
     * ��JVM������ʱ�Զ�ִ����Щ������ֻ��ִ��һ��
     */
    private static Properties prop = new Properties();
    private static String driverClass;
    private static String url;
    private static String userName;
    private static String passWord;
    static {
        InputStream is = DButil.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            prop.load(is);
            driverClass = prop.getProperty("driverClass");
            url = prop.getProperty("url");
            url = url.replaceAll("%20", " ");//·���еĿո����Ϊ%20
            userName = prop.getProperty("userName");
            passWord = prop.getProperty("passWord");
            Class.forName(driverClass);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(is!=null){
                try {is.close();}
                catch (IOException e){e.printStackTrace();}
            }
        }
    }

    /**
     * ����ȡ getConnection����Ϊ��̬����
     * �Ա�֤ͬһ����ʹ�õ���ͬһ������
     */
    public static Connection getConnection(){
        Connection conn=null;
        try {
            conn = DriverManager.getConnection(url,userName,passWord);
            conn.setAutoCommit(false);//ע�⣺Ϊ��ֹ�����Զ��ύ������ϰ�������ｫ������ύ��ʽ��Ϊ�ֶ��ύ
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * �� closeConnectionҲ����Ϊ��̬����
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {//�ر�����
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
