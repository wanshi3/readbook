package com.demo.controller.user;

import com.demo.pojo.JsonData;
import com.demo.pojo.User;
import com.demo.service.UserServiclmpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Insert4", value = "/com/demo/controller/user/Insert")
public class Insert extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        UserServiclmpl dto = new UserServiclmpl();//数据传输层对象，用于调用业务层方法
        User pojo = new User();
        // 1.(参)获取参数值，并保存到POJO对象
        // 1.(参)获取参数值，并保存到POJO对象
        String str="";
        String str2="";
        str2 = pojo.toUsernamer();
        str+="Username=" + str2;
        pojo.setCondition(str);
        JsonData jd = dto.select(pojo);
        while (jd.getTotal()>0)
        {
            str2 =pojo.toUsernamer();
            str ="Username="+ str2;
            pojo.setCondition(str);
            jd = dto.select(pojo);
        }

        String Name= request.getParameter("Name");
        if(Name == ""){Name = null;}

        String Password = request.getParameter("Password");
        if(Password == ""){Password = null;}
        pojo.setUsername(str2);
        pojo.setName(Name);
        pojo.setPassword(Password);
        // 2.(调)调用ServiceDAO的方法，完成对应业务

        // 2.(调)调用ServiceDAO的方法，完成对应业务
        JsonData jd2 = dto.insert(pojo);
        if (jd2.isSuccess())
        {
            ArrayList<User> rows = new ArrayList<User>();
            rows.add(pojo);
            System.out.println(jd.getMsg());
            jd.setRows(rows);
            jd.setMsg("成功注册");
            jd.setTotal(1);

        }
        // 3.(存)将数据对象存储到request作用范围变量
        request.setAttribute("JsonData", jd);

        // 4.(转)将业务转发到View
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);
    }
}
