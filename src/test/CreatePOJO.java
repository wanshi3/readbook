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
import java.util.Properties;

@WebServlet(name = "com.test.CreatePOJO", value = "/test/CreatePOJO")
public class CreatePOJO extends HttpServlet {

    public String tableName;  // 表名
    public String comment;  // 表注释
    public String[] colNames; // 列名数组
    public String[] colTypes; // 列名类型数组
    public String[] colComment; // 列名注释数组
    public int[] colSizes; // 列名大小数组

    public boolean needUtil = false; // 是否需要导入包java.util.*
    public boolean needSql = false; // 是否需要导入包java.sql.*

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
     * 构造方法
     */
    public void CreatePOJO() {

    }

    /**
     * 生成class的所有内容
     */
    public String parse() {
        StringBuffer sb = new StringBuffer();
        // 文件头部
        sb.append("package " + parentPackage + ".pojo;\r\n");
        sb.append("\r\n");
        sb.append("import java.io.Serializable;\r\n");
        sb.append("import java.util.*;\r\n");
        sb.append("\r\n");
        sb.append("import " + parentPackage + ".pojo.Base;\r\n");
        /** 需要额外导入的类
        for (int i = 0; i < colNames.length; i++) {
            // 如果字段中存在 xxxId 字段的话，需要添加 xxx 的引用
            int length = colNames[i].length();
            if (length > 2 && colNames[i].substring(length - 2).toLowerCase().equals("id")) {
                String objName = colNames[i].substring(0, length - 2).toLowerCase();
                sb.append("import " + parentPackage + ".pojo." + getTransStr(objName, true) + ";\r\n");
            }
        }
        */
        sb.append("\r\n");
        // 注释部分
        sb.append("/**\r\n");
        sb.append(" * " + comment + "(" + tableName + ")\r\n");
        sb.append(" * \r\n");
        sb.append(" * 该POJO是一个JavaBean，所有的属性都是跟数据库一致，包括字段名，数据类型\r\n");
        sb.append(" * 为每个属性添加一个get()和set()，顺便再加一个toString()，便于程序调试\r\n");
        sb.append(" * 另外，所有的POJO类都继承Base，就不用再考虑where、limit、order by的问题了\r\n");
        sb.append(" */ \r\n");
        sb.append("\r\n");
        // 实体部分
        sb.append("public class " + getTransStr(tableName, true) + " extends Base implements Serializable {\r\n\r\n");
        processAllAttrs(sb);    // 属性
        processAllMethod(sb);   // get()、set()方法
        processToString(sb);    // toString()方法
        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * 生成所有成员变量
     */
    public void processAllAttrs(StringBuffer sb) {
        sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");
        sb.append("\r\n");
        int size = colNames.length;
        for (int i = 0; i < size; i++) {
            // 数据库中每个字段对应的属性
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + getTransStr(colNames[i], false) + ";\t// " + colComment[i] + "\r\n");
            // 另外，如果字段中存在 xxxId 字段的话，也添加一个 xxx 属性
            int length = colNames[i].length();
            if (length > 2 && colNames[i].substring(length - 2).toLowerCase().equals("id")) {
                String objName = colNames[i].substring(0, length - 2).toLowerCase();
                sb.append("\tprivate " + getTransStr(objName, true) + " " + objName + ";\t// 外键\r\n");
            }
        }
        sb.append("\r\n");
    }

    /**
     * 生成所有get/set方法
     */
    public void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colNames.length; i++) {
            String bigName = getTransStr(colNames[i], true);
            String smallName = getTransStr(colNames[i], false);
            // 数据库中每个字段对应的 setXxx() 和 getXxx()
            sb.append("\tpublic void set" + bigName + "(" + sqlType2JavaType(colTypes[i]) + " " + smallName + "){\r\n");
            sb.append("\t\tthis." + smallName + " = " + smallName + ";\r\n\t}\r\n");
            sb.append("\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + bigName + "(){\r\n");
            sb.append("\t\treturn " + smallName + ";\r\n\t}\r\n");
            sb.append("\r\n");
            // 另外，如果字段中存在 xxxId 字段的话
            // 也添加一个 xxx 对应的 setXxx() 和 getXxx()
            // 便于后续的数据保存
            int length = colNames[i].length();
            if (length > 2 && colNames[i].substring(length - 2).toLowerCase().equals("id")) {
                String objName = colNames[i].substring(0, length - 2);
                bigName = getTransStr(objName, true);
                smallName = getTransStr(objName, false);
                sb.append("\tpublic void set" + bigName + "(" + bigName + " " + smallName + "){\r\n");
                sb.append("\t\tthis." + smallName + " = " + smallName + ";\r\n\t}\r\n");
                sb.append("\r\n");
                sb.append("\tpublic " + bigName + " get" + bigName + "(){\r\n");
                sb.append("\t\treturn " + smallName + ";\r\n\t}\r\n");
                sb.append("\r\n");
            }
        }
    }

    /**
     * 重写toString()方法
     */
    public void processToString(StringBuffer sb) {
        sb.append("\tpublic String toString() {\r\n");
        sb.append("\t\treturn \"" + getTransStr(tableName, true) + "[\"\r\n");
        for (int i = 0; i < colNames.length; i++) {
            sb.append("\t\t\t+ \"");
            if (i != 0) {sb.append(",");}
            sb.append(colNames[i] + " = \" + " + colNames[i] + "\r\n");
        }
        sb.append("\t\t\t+\"]\";\r\n");
        sb.append("\t}\r\n");
    }

    /**
     * 将传入字符串的首字母转成大写
     */
    public String initCap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 将mysql中表名和字段名转换成驼峰形式
     */
    public String getTransStr(String before, boolean firstChar2Upper) {
        //不带"_"的字符串,则直接首字母大写后返回
        if (!before.contains("_")) {
            return firstChar2Upper ? initCap(before) : before;
        }
        String[] strs = before.split("_");
        StringBuffer after = null;
        if (firstChar2Upper) {
            after = new StringBuffer(initCap(strs[0]));
        } else {
            after = new StringBuffer(strs[0]);
        }
        if (strs.length > 1) {
            for (int i = 1; i < strs.length; i++) {
                after.append(initCap(strs[i]));
            }
        }
        return after.toString();
    }

    /**
     * 查找sql字段类型所对应的Java类型
     */
    public String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")
                || sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "int";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("float")
                || sqlType.equalsIgnoreCase("double")
                || sqlType.equalsIgnoreCase("decimal")
                || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real")
                || sqlType.equalsIgnoreCase("smallmoney")
                || sqlType.equalsIgnoreCase("money")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar")
                || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("text")
                || sqlType.equalsIgnoreCase("date")
                || sqlType.equalsIgnoreCase("datetime")) {
            return "String";
        } else {
            return "String";
        }
    }

    /**
     * 生成对应文件
     */
    public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //与数据库的连接
        Connection con;
        PreparedStatement pst = null;
        ResultSetMetaData rsmd = null;
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, NAME, PASS);
        //System.out.println("connect database success ! please wait...");
        response.getWriter().println("<p>-----------------------------------------</p>");
        response.getWriter().println("<h5>成功连接数据库 ! 请稍候...</h4>");
        response.getWriter().println("<p>-----------------------------------------</p>");

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
            tableNames.add(rs.getString("TABLE_NAME"));  // 表名
            tableComments.add(rs.getString("TABLE_COMMENT"));  // 表注释
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
                if (colTypes[i].equalsIgnoreCase("datetime") || colTypes[i].equalsIgnoreCase("date")){ needUtil = true;}
                if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")){ needSql = true;}
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            //解析生成class的所有内容
            String content = parse();
            //输出生成文件:directory.getAbsolutePath()为 tomcat 的 bin 路径
            File directory = new File("");
            String dirName = directory.getAbsolutePath() + "\\src\\main\\java\\" + parentPackage.replace(".", "\\") + "\\pojo";
            File dir = new File(dirName);
            if (!dir.exists() && dir.mkdirs()) {
                //System.out.println("generate dir : " + dirName);
                response.getWriter().println("<h4>创建目录 : <span style='color:red'>" + dirName + "</span></h4>");
            }
            String javaPath = dirName + "/" + getTransStr(tableName, true) + ".java";
            FileWriter fw = new FileWriter(javaPath);
            pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            //System.out.println("create class : " + tableName);
            response.getWriter().println("<h5>成功创建类 : " + tableName + "</h5>");
        }
        if (pw != null){ pw.close();}
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");

            generate(request, response);
            //System.out.println("generate POJO classes success!");
            response.getWriter().println("<h4>成功创建所有 POJO 类 !</h43>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
