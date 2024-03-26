package test;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@WebServlet(name = "CreateDAO", value = "/test/CreateDAO")

public class CreateDAO extends HttpServlet {

    // 当前表对应的信息，主要用于在当前 DAO 类中创建对应的 Pojo 类(增删改查中均有使用)
    public String tableName;// 表名
    public String comment;// 表注释
    public String[] colNames; // 列名数组
    public String[] colTypes; // 列名类型数组
    public String[] colComment; // 列名注释数组
    public int[] colSizes; // 列名大小数组

    // 非当前表对应的信息，主要用于在当前 DAO 类中创建其他Pojo类(多表查询中使用)
    public String[] otherColNames; // 列名数组
    public String[] otherColTypes; // 列名类型数组
    public int otherColSize; // 列名大小

    //需要修改的数据
	public static final String DATABASENAME = "project";// 数据库名
    public static final String NAME = "root";// 连接用户名
    public static final String PASS = "root";// 连接密码
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/"+DATABASENAME+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Hongkong";// 需要连接的数据库
    public String parentPackage = "com";// 指定类生成所在包的父路径

    // SQL相关
    public static final String SQL = "select * from ";// 数据表操作
    public static final String COL = "show full columns from ";// 数据表的字段信息
    public static final String SCH = "SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='"+DATABASENAME+"'";// 数据中的表注释

    /**
     * 类的构造方法
     */
    public CreateDAO() {

    }

    /**
     * 判断字段类型，便于使用getInt()或getString()
     */
    public String judgeType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")
                || sqlType.equalsIgnoreCase("tinyint")
                || sqlType.equalsIgnoreCase("smallint")
                || sqlType.equalsIgnoreCase("int")
                || sqlType.equalsIgnoreCase("bigint")
                || sqlType.equalsIgnoreCase("float")
                || sqlType.equalsIgnoreCase("double")
                || sqlType.equalsIgnoreCase("decimal")
                || sqlType.equalsIgnoreCase("real")
                || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "int";
        }
        else {
            return "string";
        }
    }

    /**
     * 将传入字符串的首字母转成大写
     */
    public String initCap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z'){ ch[0] = (char) (ch[0] - 32);}
        return new String(ch);
    }

    /**
     * 将mysql中表名和字段名转换成驼峰形式
     */
    public String getTransStr(String before, boolean firstChar2Upper) {
        //不带"_"的字符串,则直接首字母大写后返回
        if (!before.contains("_")){return firstChar2Upper ? initCap(before) : before;}
        String[] strs = before.split("_");
        StringBuffer after = null;
        if (firstChar2Upper) {
            after = new StringBuffer(initCap(strs[0]));
        } else {
            after = new StringBuffer(strs[0]);
        }
        if (strs.length > 1) {
            for (int i=1; i<strs.length; i++){after.append(initCap(strs[i]));}
        }
        return after.toString();
    }

    /**
     * 根据表名，获取该表的字段信息
     * 列名数组:otherColNames
     * 列名类型数组:otherColTypes
     */
    public void getColInfo(String tableName) throws Exception{
        // 连接数据库
        Connection con;
        PreparedStatement pst = null;
        ResultSetMetaData rsmd = null;
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, NAME, PASS);
        try {
            // 查询表信息
            String sql = "show full columns from " + tableName;
            pst = con.prepareStatement(sql);
            rsmd = pst.getMetaData();
            otherColSize = rsmd.getColumnCount();
            otherColNames = new String[otherColSize];
            otherColTypes = new String[otherColSize];
            //保存所需的信息
            for (int i = 0; i < otherColSize; i++) {
                otherColNames[i] = rsmd.getColumnName(i + 1);
                otherColTypes[i] = rsmd.getColumnTypeName(i + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            con.close();
        }
    }

    /**
     * 生成DAO的所有内容
     */
    public String parseDAO() {
        StringBuffer sb = new StringBuffer();
        tableName = getTransStr(tableName, true);
        // 文件头部
        sb.append("package " + parentPackage + ".dao;\r\n");
        sb.append("\r\n");
        sb.append("import java.sql.*;\r\n");
        sb.append("import java.util.ArrayList;\r\n");
        sb.append("import " + parentPackage + ".pojo." + tableName + ";\r\n");
        sb.append("\r\n");
        // 注释部分
        sb.append("/**\r\n");
        sb.append(" * 每个DAO对应数据库中的一张表\r\n");
        sb.append(" * 包含添加、删除、修改、查询、统计等操作\r\n");
        sb.append(" */ \r\n");
        sb.append("\r\n");
        // 实体部分
        sb.append("public interface " + tableName + "DAO {\r\n");
        sb.append("\tpublic ArrayList<" + tableName + "> select(" + tableName + " obj) throws SQLException;\t// 查询\r\n");
        sb.append("\tpublic int count(" + tableName + " obj) throws SQLException;\t\t// 统计记录数\r\n");
        sb.append("\tpublic int insert(" + tableName + " obj) throws SQLException;\t// 添加\r\n");
        sb.append("\tpublic int update(" + tableName + " obj) throws SQLException;\t// 更新\r\n");
        sb.append("\tpublic int delete(" + tableName + " obj) throws SQLException;\t// 删除\r\n");
        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * 生成DAOImpl的所有内容
     */
    public String parseDAOImpl() throws Exception {
        StringBuffer sb = new StringBuffer();
        tableName = getTransStr(tableName, true);
        // 文件头部
        sb.append("package " + parentPackage + ".dao;\r\n");
        sb.append("\r\n");
        sb.append("import java.sql.*;\r\n");
        sb.append("import java.util.ArrayList;\r\n");
        sb.append("import " + parentPackage + ".pojo." + tableName + ";\r\n");
        sb.append("\r\n");
        // 实体部分
        sb.append("public class " + tableName + "DAOImpl implements " + tableName + "DAO {\r\n");
        sb.append("\r\n");
        sb.append("\tprivate Connection conn = null;\r\n");
        sb.append("\tprivate PreparedStatement pst = null;\r\n");
        sb.append("\r\n");
        // 1.无参构造函数
        sb.append("\t/**\r\n");
        sb.append("\t * 无参构造函数\r\n");
        sb.append("\t */\r\n");
        sb.append("\tpublic " + tableName + "DAOImpl () {\r\n");
        sb.append("\t\tsuper();\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        // 2.带参构造函数
        sb.append("\t/**\r\n");
        sb.append("\t * 带参构造函数\r\n");
        sb.append("\t * 参数为conn连接，实例化的时候，就直接完成连接的注入\r\n");
        sb.append("\t */\r\n");
        sb.append("\tpublic " + tableName + "DAOImpl (Connection conn) {\r\n");
        sb.append("\t\tsuper();\r\n");
        sb.append("\t\tthis.conn = conn;\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        // 3.对应接口类中的各种实现
        sb.append(selectStr());
        sb.append(countStr());
        sb.append(insertStr());
        sb.append(updateStr());
        sb.append(deleteStr());

        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * 拼接查询记录的代码
     */
    public String selectStr() throws Exception {
        StringBuffer sb = new StringBuffer();
        // 注释部分
        sb.append("\t/**\r\n");
        sb.append("\t * 查询\r\n");
        sb.append("\t */\r\n");
        // 实体部分
        sb.append("\tpublic ArrayList<" + tableName + "> select(" + tableName + " pojo) throws SQLException {\r\n");
        sb.append("\t\ttry{\r\n");
        // 1.拼接SQL
        sb.append("\t\t\t// 基本的SQL\r\n");
        sb.append("\t\t\tString sql = \"select \"\r\n");
        for (int i = 0; i < colNames.length; i++) {
            sb.append("\t\t\t\t+\"");
            if(i > 0) {sb.append(",");}
            sb.append(" " + tableName.toLowerCase() + "." + colNames[i] + " as " + colNames[i] + " \"\r\n");
        }
        sb.append("\t\t\t\t+\" from " + tableName.toLowerCase() + " \"\r\n");
        sb.append("\t\t\t\t+\" where 1=1 \";\r\n");
        sb.append("\r\n");
        // 2.拼接查询
        sb.append("\t\t\t// 拼接查询条件\r\n");
        sb.append("\t\t\tString condition = pojo.getCondition();\r\n");
        sb.append("\t\t\tif(condition != null && ! condition.equals(\"\")){\r\n");
        sb.append("\t\t\t\tsql += \" and \" + condition;\r\n");
        sb.append("\t\t\t}\r\n");
        sb.append("\r\n");
        // 3.拼接排序
        sb.append("\t\t\t// 拼接排序条件\r\n");
        sb.append("\t\t\tString orderBy = pojo.getOrderBy();\r\n");
        sb.append("\t\t\tif(orderBy != null && ! orderBy.equals(\"\")){\r\n");
        sb.append("\t\t\t\tsql += \" and \" + orderBy;\r\n");
        sb.append("\t\t\t}\r\n");
        sb.append("\r\n");
        // 4.拼接分页
        sb.append("\t\t\t// 拼接分页条件\r\n");
        sb.append("\t\t\tString limit = pojo.getLimit();\r\n");
        sb.append("\t\t\tif(limit != null && ! limit.equals(\"\")){\r\n");
        sb.append("\t\t\t\tsql += \" and \" + limit;\r\n");
        sb.append("\t\t\t}\r\n");
        sb.append("\r\n");
        // 5.测试输出SQL
        sb.append("\t\t\t// System.out.println(sql);\r\n");
        // 6.拼接执行代码
        sb.append("\t\t\t// 连接数据库，并将sql执行结果转换为java对象\r\n");
        sb.append("\t\t\tpst = conn.prepareStatement(sql);\r\n");
        sb.append("\t\t\tResultSet rs = pst.executeQuery();\r\n");
        // 7.将sql执行结果转换为java对象
        sb.append("\t\t\tArrayList<" + tableName + "> al = new ArrayList<" + tableName + ">();\t// 封装数据并返回给Service层\r\n");
        sb.append("\t\t\twhile(rs.next()){\r\n");
        sb.append("\t\t\t\t// 将" + tableName + "相关信息保存到po对象的各个属性中\r\n");
        sb.append("\t\t\t\t" + tableName + " po = new " + tableName + "();\t//持久层对象，用于调用POJO类的方法\r\n");
        for (int i = 0; i < colNames.length; i++) {
            if(judgeType(colTypes[i])=="int"){
                sb.append("\t\t\t\tpo.set"+initCap(colNames[i])+"(rs.getInt(\"" + colNames[i] + "\"));\r\n");
            }
            else{
                sb.append("\t\t\t\tpo.set"+initCap(colNames[i])+"(rs.getString(\"" + colNames[i] + "\"));\r\n");
            }
        }
        /*
         * 如果存在外键，例如有一个userId字段
         * 则应该将user相关信息保存到user对象的相关属性中
         * (记住：多表操作时候，我们在DAO层的做法，都使用这个套路)
         * 本代码可以写在上面的循环中，但为了增加程序的可读性，我们将所有的这类代码，放在结尾处
         */

        for (int i = 0; i < colNames.length; i++) {
            int length = colNames[i].length();
            if (length > 2 && colNames[i].substring(length - 2).toLowerCase().equals("id")) {  // 例如 userId 满足该条件
                // 1.先确定外键对应哪张表，以便实例化对应的pojo类
                String colName_l = colNames[i].substring(0,length-2).toLowerCase();  // colName_l = "user"，即表名
                String colName_u = getTransStr(colName_l, true);  // colName_u = "User"，即对应的pojo类名
                sb.append("\t\t\t\t// 由于存在外键(" + colNames[i] + ")，这里需要将" + colName_l + "相关信息保存到" + colName_u + "对象的相关属性中\r\n");
                sb.append("\t\t\t\t" + colName_u + " " + colName_l + " = new " + colName_u + "();\r\n");
                // 2.连接数据库，根据表名，获得该表的字段名及字段类型
                /*getColInfo(colName_l);
                System.out.println(otherColSize);
                // 3.完成数据的保存：pojo.setColName(rs.getString("colname"));
                for(int j = 0; j < otherColSize; j++){
                    if(judgeType(otherColTypes[i])=="int"){
                        sb.append("\t\t\t\t" + colName_l + ".set"+initCap(otherColNames[i])+"(rs.getInt(\"" + otherColNames[i] + "\"));\r\n");
                    }
                    else{
                        sb.append("\t\t\t\t" + colName_l + ".set"+initCap(otherColNames[i])+"(rs.getString(\"" + otherColNames[i] + "\"));\r\n");
                    }
                }*/
                sb.append("\t\t\t\tpo.set" + colName_u + "(" + colName_l + ");\r\n");
            }
        }

        sb.append("\t\t\t\t// 将以上全部信息添加到集合\r\n");
        sb.append("\t\t\t\tal.add(po);\r\n");
        sb.append("\t\t\t}\r\n");
        sb.append("\t\t\t// System.out.println(al.toString());\r\n");
        sb.append("\t\t\treturn al;\r\n");
        // 8.拼接容错代码
        sb.append("\t\t}catch(Exception e){\r\n");
        sb.append("\t\t\te.printStackTrace();\r\n");
        sb.append("\t\t\treturn null;\r\n");
        sb.append("\t\t}\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    /**
     * 拼接统计记录数的代码
     */
    public String countStr(){
        StringBuffer sb = new StringBuffer();
        // 注释部分
        sb.append("\t/**\r\n");
        sb.append("\t * 统计记录数\r\n");
        sb.append("\t */\r\n");
        // 实体部分
        sb.append("\tpublic int count(" + tableName + " pojo) throws SQLException {\r\n");
        sb.append("\t\ttry {\r\n");
        // 1.拼接SQL
        sb.append("\t\t\tString sql = \"select count(*) as cnt from " + tableName.toLowerCase() + " where 1=1 \";\r\n");
        sb.append("\t\t\tString condition = pojo.getCondition();\r\n");
        sb.append("\t\t\tif(condition != null && !condition.equals(\"\")){\r\n");
        sb.append("\t\t\t\tsql += \" and \" + condition;\r\n");
        sb.append("\t\t\t}\r\n");
        // 2.测试输出SQL
        sb.append("\t\t\t// System.out.println(sql);\r\n");
        // 3.拼接执行代码
        sb.append("\t\t\tpst = conn.prepareStatement(sql);\r\n");
        sb.append("\t\t\tResultSet rs = pst.executeQuery();\r\n");
        sb.append("\t\t\trs.next();\r\n");
        sb.append("\t\t\tint cnt = rs.getInt(\"cnt\");\r\n");
        sb.append("\t\t\treturn cnt;\r\n");
        // 4.拼接容错代码
        sb.append("\t\t}catch(Exception e){\r\n");
        sb.append("\t\t\te.printStackTrace();\r\n");
        sb.append("\t\t\treturn -1;\r\n");
        sb.append("\t\t}\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    /**
     * 拼接添加单个记录的代码
     */
    public String insertStr(){
        StringBuffer sb = new StringBuffer();
        // 注释部分
        sb.append("\t/**\r\n");
        sb.append("\t * 添加单个记录\r\n");
        sb.append("\t */\r\n");
        // 实体部分
        sb.append("\tpublic int insert(" + tableName + " pojo) throws SQLException {\r\n");
        sb.append("\t\ttry {\r\n");
        // 1.拼接SQL
        sb.append("\t\t\tString sql = \"insert into \"\r\n");
        sb.append("\t\t\t\t+ \"" + tableName.toLowerCase() + "(");
        int num = 0; // 用于保存需要出现的？的个数
        String temp = ""; // 用于保存拼接的结果，因为需要去掉最后的逗号，所以等循环结束后，再执行sb.append()
        for (int i = 0; i < colNames.length; i++) {
            if(colNames[i].toLowerCase().equals("id")) {continue;}
            temp += colNames[i] + ",";
            num ++;
        }
        sb.append(temp.substring(0,temp.length()-1));
        sb.append(")\"\r\n");
        sb.append("\t\t\t\t+ \"values(");
        for (int i = 0; i < num; i++) {
            if(i>0) {sb.append(",");}
            sb.append("?");
        }
        sb.append(")\";\r\n");
        // 2.拼接预处理代码
        sb.append("\t\t\tpst = conn.prepareStatement(sql);\r\n");
        int idx = 1; // 用于拼接的序号
        for(int i = 0; i < colNames.length; i++) {
            if(colNames[i].toLowerCase().equals("id")) {continue;}
            if(judgeType(colTypes[i])=="int") {
                sb.append("\t\t\tpst.setInt");
            }
            else{
                sb.append("\t\t\tpst.setString");
            }
            sb.append("(" + idx + ", pojo.get" + getTransStr(colNames[i], true) + "());\r\n");
            idx ++;
        }
        // 3.测试输出SQL
        sb.append("\t\t\t// System.out.println(sql);\r\n");
        // 4.拼接执行代码
        sb.append("\t\t\tint i = pst.executeUpdate();\r\n");
        sb.append("\t\t\treturn i;\r\n");
        // 5.拼接容错代码
        sb.append("\t\t}catch(Exception e){\r\n");
        sb.append("\t\t\te.printStackTrace();\r\n");
        sb.append("\t\t\treturn -1;\r\n");
        sb.append("\t\t}\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    /**
     * 拼接更新单个记录的代码
     */
    public String updateStr(){
        StringBuffer sb = new StringBuffer();
        // 注释部分
        sb.append("\t/**\r\n");
        sb.append("\t * 更新单个记录\r\n");
        sb.append("\t */\r\n");
        // 实体部分
        sb.append("\tpublic int update(" + tableName + " pojo) throws SQLException {\r\n");
        sb.append("\t\ttry {\r\n");
        // 1.拼接SQL
        sb.append("\t\t\tint cnt = 0;\r\n");
        sb.append("\t\t\tString sql = \"update " + tableName.toLowerCase() + " set \";\r\n");
        sb.append("\r\n");
        for(int i = 0; i < colNames.length; i++) {
            if(colNames[i].toLowerCase().equals("id")) {continue;}
            sb.append("\t\t\tif(pojo.get" + getTransStr(colNames[i], true) + "() != null && !pojo.get" + getTransStr(colNames[i], true) + "().equals(\"\")){\r\n");
            sb.append("\t\t\t\tsql += \"" + colNames[i] + "='\" + pojo.get" + getTransStr(colNames[i], true) + "() + \"',\";\r\n");
            sb.append("\t\t\t\tcnt++;\r\n");
            sb.append("\t\t\t}\r\n");
            sb.append("\r\n");
        }
        sb.append("\t\t\t// 去掉最后一个逗号\r\n");
        sb.append("\t\t\tsql = sql.substring(0,sql.length()-1);\r\n");
        sb.append("\t\t\tsql += \" where id=?\";\r\n");
        sb.append("\r\n");
        // 2.拼接预处理代码
        sb.append("\t\t\tpst = conn.prepareStatement(sql);\r\n");
        sb.append("\t\t\tpst.setInt(1, pojo.getId());\r\n");
        // 3.测试输出SQL
        sb.append("\t\t\t//System.out.println(sql);\r\n");
        // 4.拼接执行代码
        sb.append("\t\t\tint i = pst.executeUpdate();\r\n");
        sb.append("\t\t\treturn i;\r\n");
        // 4.拼接容错代码
        sb.append("\t\t}catch(Exception e){\r\n");
        sb.append("\t\t\te.printStackTrace();\r\n");
        sb.append("\t\t\treturn -1;\r\n");
        sb.append("\t\t}\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    /**
     * 拼接删除单个记录的代码
     */
    public String deleteStr(){
        StringBuffer sb = new StringBuffer();
        // 注释部分
        sb.append("\t/**\r\n");
        sb.append("\t * 删除单个记录\r\n");
        sb.append("\t */\r\n");
        // 实体部分
        sb.append("\tpublic int delete(" + tableName + " pojo) throws SQLException {\r\n");
        sb.append("\t\ttry {\r\n");
        // 1.拼接SQL
        sb.append("\t\t\tString sql = \"delete from " + tableName.toLowerCase() + " where id=?\";\r\n");
        // 2.拼接预处理代码
        sb.append("\t\t\tpst = conn.prepareStatement(sql);\r\n");
        sb.append("\t\t\tpst.setInt(1, pojo.getId());\r\n");
        // 3.测试输出SQL
        sb.append("\t\t\t//System.out.println(sql);\r\n");
        // 4.拼接执行代码
        sb.append("\t\t\tint i = pst.executeUpdate();\r\n");
        sb.append("\t\t\treturn i;\r\n");
        // 5.拼接容错代码
        sb.append("\t\t}catch(Exception e){\r\n");
        sb.append("\t\t\te.printStackTrace();\r\n");
        sb.append("\t\t\treturn -1;\r\n");
        sb.append("\t\t}\r\n");
        sb.append("\t}\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    /**
     * 生成对应文件
     */
    public void generate(HttpServletRequest request, HttpServletResponse response,String fileName) throws Exception{
        //与数据库的连接
        Connection con;
        PreparedStatement pst = null;
        ResultSetMetaData rsmd = null;
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, NAME, PASS);
        //System.out.println("connect database success ! please wait...");
        response.getWriter().println("<h5>-----------------------------------------</h5>");
        response.getWriter().println("<h5>成功连接数据库 ! 请稍候...</h4>");
        response.getWriter().println("<h5>-----------------------------------------</h5>");

        Properties props = new Properties();
        props.setProperty("remarks", "true"); //设置为true可以获取remarks信息
        props.setProperty("useInformationSchema", "true");//设置为true可以获取tables remarks信息

        List<String> tableNames = new ArrayList<>();
        List<String> tableComments = new ArrayList<>();
        /*
            //获取数据库的元数据
            DatabaseMetaData db = con.getMetaData();
            //从元数据中获取到所有的表名
            ResultSet rs = db.getTables(null, null, null, new String[] { "TABLE" });
        */
        pst = con.prepareStatement(SCH);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            tableNames.add(rs.getString("TABLE_NAME"));
            tableComments.add(rs.getString("TABLE_COMMENT"));//
        }

        String tableCol;
        String tableSql;
        PrintWriter pw = null;
        for (int j = 0; j < tableNames.size(); j++) {
            tableName = tableNames.get(j);//表名
            comment = tableComments.get(j);//表注释
            tableSql = SQL + tableName;
            pst = con.prepareStatement(tableSql);
            rsmd = pst.getMetaData();
            int size = rsmd.getColumnCount();
            colNames = new String[size];
            colTypes = new String[size];
            colComment = new String[size];
            colSizes = new int[size];
            tableCol = COL + tableName;
            //获取字段备注信息
            ResultSet colRs = pst.executeQuery(tableCol);
            int i = 0;
            while (colRs.next()) {
                colComment[i] = colRs.getString("Comment");
                i++;
            }
            //获取所需的信息
            for (i = 0; i < size; i++) {
                colNames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            //解析生成class的所有内容
            String content = parseDAOImpl();
            //输出生成文件:directory.getAbsolutePath()为 tomcat 的 bin 路径
            File directory = new File("");
            String dirName = directory.getAbsolutePath() + "\\src\\main\\java\\" + parentPackage.replace(".", "\\") + "\\dao";
            File dir = new File(dirName);
            if (!dir.exists() && dir.mkdirs()) {
                //System.out.println("generate dir : " + dirName);
                response.getWriter().println("<h4>创建目录 : <span style='color:red'>" + dirName + "</span></h4>");
            }
            String javaPath = dirName + "/" + getTransStr(tableName, true) + fileName + ".java";
            FileWriter fw = new FileWriter(javaPath);
            pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            //System.out.println("create class : " + tableName);
            response.getWriter().println("<h5>成功创建类 : " + tableName + fileName + ".java</h5>");
        }
        if (pw != null){ pw.close();}
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 说明：
         * 没有提供生成批量添加、批量修改、批量删除、及特殊统计的代码
         * 另外，本代码只为减少部分工作量，让大家从繁琐的体力劳动中脱离出来
         * 很多代码还需要根据实际项目需求，进一步填写完整
         */
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");

            generate(request,response,"DAO");
            //System.out.println("generate DAO classes success!");
            response.getWriter().println("<h4>成功创建所有 DAO 接口类 !</h4>");

            generate(request,response,"DAOImpl");
            //System.out.println("generate DAOImpl classes success!");
            response.getWriter().println("<h4>成功创建所有 DAOImpl 实现类 !</h4>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
