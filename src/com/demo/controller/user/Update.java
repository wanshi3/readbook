package com.demo.controller.user;

import com.demo.pojo.JsonData;
import com.demo.pojo.User;
import com.demo.service.UserServiclmpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Update4", value = "/com/demo/controller/user/Update")
public class Update extends HttpServlet {
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
        int UserID=0;
        // sequence,name,gender,birthday,card,nation,nativePlace,political,userId
        if (request.getParameter("UserID")!=null)
            UserID= Integer.parseInt(request.getParameter("UserID"));
        System.out.println(UserID);
        pojo.setUserID(UserID);
        // 1.(参)获取参数值，并保存到POJO对象
        String Name= request.getParameter("Name");
        if(Name == ""){Name = null;}

        String Password = request.getParameter("Password");
        if(Password == ""){Password = null;}

        pojo.setName(Name);
        pojo.setPassword(Password);
        // 2.(调)调用ServiceDAO的方法，完成对应业务

        // 2.(调)调用ServiceDAO的方法，完成对应业务
        JsonData jd = dto.update(pojo);

        // 3.(存)将数据对象存储到request作用范围变量
        request.setAttribute("JsonData", jd);

        // 4.(转)将业务转发到View
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);
    }
}
