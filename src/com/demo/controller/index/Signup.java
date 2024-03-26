package com.demo.controller.index;

import com.demo.pojo.JsonData;
import com.demo.pojo.User;
import com.demo.service.CreateServicelmpl;
import com.demo.service.IndexServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Signup", value = "/com/demo/controller/index/Signup")
public class Signup extends HttpServlet {
    IndexServiceImpl dto = new IndexServiceImpl();
    CreateServicelmpl dto2 = new CreateServicelmpl();
    User pojo1 = new User();
    User pojo2 = new User();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //初始化
        request.setCharacterEncoding("utf-8");
        //1.(参)获取参数值，并保存到POJO对象
        String name = request.getParameter("name");
        String Password = request.getParameter("Password");
        String str1 =" ";
        String str2 =" ";
        String str = " ";
        str=pojo1.toUsernamer();
        str1 +="Username = '"+str+"'";
        if (name==null||name.equals("")){
            return;
        }
        if ( Password==null||Password.equals("")){
            return;
        }

        pojo1.setCondition(str1);
        JsonData jd = dto.login(pojo1);
        System.out.println(jd.getTotal());
        while (jd.getTotal()>0)
        {
            str=pojo1.toUsernamer();
            str1 ="Username = '"+str+"'";
            pojo1.setCondition(str1);
            jd = dto.login(pojo1);
        }
        if (jd.getMsg().equals("查询失败"))
            return;
        pojo2.setName(name);
        pojo2.setPassword(Password);
        pojo2.setUsername(str);
        JsonData jd2 = dto2.create(pojo2);
        if (jd2.isSuccess())
        {
            ArrayList<User> rows = new ArrayList<User>();
            rows.add(pojo2);
            System.out.println(jd.getMsg());
            jd.setRows(rows);
            jd.setMsg("成功注册");
            jd.setTotal(1);

        }

//        System.out.println(str1);

        request.setAttribute("JsonData", jd);
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);

    }
}
