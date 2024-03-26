package com.demo.controller.index;

import com.demo.pojo.JsonData;
import com.demo.pojo.User;
import com.demo.service.IndexServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Login", value = "/com/demo/controller/index/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IndexServiceImpl dto = new IndexServiceImpl();
    User pojo = new User();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //初始化
        request.setCharacterEncoding("utf-8");
        //1.(参)获取参数值，并保存到POJO对象
        String Username = request.getParameter("Username");
        String Password = request.getParameter("Password");
//        System.out.println(Username);
//        System.out.println(Password);

        String str = "";
        if (Username == null || Username.equals("")) {
            return;
        }else{
            str +=" Username = '"+Username+"'";
        }
        if (Password == null || Password.equals("")) {
            return;
        }else{
            str +=" and `Password` = '"+Password+"'";
        }
        //System.out.println(str);
        pojo.setCondition(str);
        //2.(调)调用ServiceDAO的方法，完成对应业务
        // System.out.println(pojo.toString());
        JsonData jd = dto.login(pojo);
        request.setAttribute("JsonData", jd);
        //登录业务中，这里多做一步处理
        long i = jd.getTotal();
//        System.out.println(i);
        if(i==0){ jd.setMsg("账号或密码错误"); }
        else if(i>1){ jd.setMsg("重复的账号，请联系管理员！"); }
        else {jd.setMsg("登入成功");}
//        System.out.println(jd.getMsg());
        //4.(转)将业务转发到View层
        //利用JsonResultSet将记录数total与记录集rows拼接成“total=50,rows=[{},{}...{}]”格式
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);
    }
}
