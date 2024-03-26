package com.demo.controller.order2;

import com.demo.pojo.JsonData;
import com.demo.pojo.Order2;
import com.demo.service.Order2Servicelml;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet(name = "Insert7", value = "/com/demo/controller/order2/Insert")
public class Insert extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Order2Servicelml dto = new Order2Servicelml();//数据传输层对象，用于调用业务层方法
        Order2 pojo =new Order2();
        // 1.(参)获取参数值，并保存到POJO对象
        int BookID=-1;
        if (request.getParameter("BookID")!=null)
            BookID= Integer.parseInt(request.getParameter("BookID"));
        System.out.println(BookID);
        pojo.setBookID(BookID);
        int UserID=-1;
        // sequence,name,gender,birthday,card,nation,nativePlace,political,userId
        if (request.getParameter("UserID")!=null)
            UserID= Integer.parseInt(request.getParameter("UserID"));

        String CreatedTime = request.getParameter("CreatedTime");
        if(CreatedTime == ""){CreatedTime = null;}
        float TotalAmout=0;
        if(request.getParameter("TotalAmout")!=null)
        {TotalAmout = Float.parseFloat((request.getParameter("TotalAmout")));}

        int OrderQuantity=-1;
        // sequence,name,gender,birthday,card,nation,nativePlace,political,userId
        if (request.getParameter("OrderQuantity")!=null)
            OrderQuantity= Integer.parseInt(request.getParameter("OrderQuantity"));
        pojo.setUserID(UserID);
        pojo.setBookID(BookID);
        pojo.setCreatedTime(CreatedTime);
        pojo.setTotalAmout(TotalAmout);
        pojo.setOrderQuantity(OrderQuantity);

        /// 2.(调)调用ServiceDAO的方法，完成对应业务
        // 2.(调)调用ServiceDAO的方法，完成对应业务
        JsonData jd = dto.insert(pojo);

        // 3.(存)将数据对象存储到request作用范围变量
        request.setAttribute("JsonData", jd);



        // 3.(存)将数据对象存储到request作用范围变量
        request.setAttribute("JsonData", jd);

        // 4.(转)将业务转发到View
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);
    }
}
