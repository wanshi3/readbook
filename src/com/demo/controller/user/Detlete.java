package com.demo.controller.user;

import com.demo.pojo.JsonData;
import com.demo.pojo.User;
import com.demo.service.UserServiclmpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Detlete4", value = "/com/demo/controller/user/Delete")
public class Detlete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        UserServiclmpl dto = new UserServiclmpl();//数据传输层对象，用于调用业务层方法
        User pojo = new User();
        String UserID2 = request.getParameter("UserID");
        int UserID=-1;
        // 1.(参)获取参数值，并保存到POJO对象
        if (UserID2 != null)
            UserID= Integer.parseInt(UserID2);
        pojo.setUserID(UserID);
//        pojo.setCondition("book.BookID="+BookID);
        // 2.(调)调用ServiceDAO的方法，完成对应业务
        JsonData jd = dto.delete(pojo);

        // 3.(存)将数据对象存储到request作用范围变量
        request.setAttribute("JsonData", jd);

        // 4.(转)将业务转发到View
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);
    }
}
