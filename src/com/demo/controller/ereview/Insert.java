package com.demo.controller.ereview;

import com.demo.pojo.JsonData;
import com.demo.pojo.Reviewbook;
import com.demo.pojo.Reviewebook;
import com.demo.service.EReviewIndexServicelmpl;
import com.demo.service.ReviewIndexServicelmpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Insert6", value = "/com/demo/controller/ereview/Insert")
public class Insert extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        EReviewIndexServicelmpl dto = new EReviewIndexServicelmpl();//数据传输层对象，用于调用业务层方法
        Reviewebook pojo =new Reviewebook();
        // 1.(参)获取参数值，并保存到POJO对象
        int BookID=-1;
        if (request.getParameter("BookID")!=null)
            BookID= Integer.parseInt(request.getParameter("BookID"));
        pojo.setEBookID(BookID);
        int UserID=-1;
        // sequence,name,gender,birthday,card,nation,nativePlace,political,userId
        if (request.getParameter("UserID")!=null)
            UserID= Integer.parseInt(request.getParameter("UserID"));

        String Comment = request.getParameter("Comment");
        if(Comment == ""){Comment = null;}
        double Rating=0;
        if(request.getParameter("Rating")!=null)
        {Rating = Double.parseDouble((request.getParameter("Rating")));}

        String ReviewDate = request.getParameter("ReviewDate");
        if(ReviewDate == ""){ReviewDate = null;}

        String Genre = request.getParameter("Genre");
        if(Genre == ""){Genre  = null;}

        pojo.setUserID(UserID);
        pojo.setComment(Comment);
        pojo.setRating(Rating);
        pojo.setReviewDate(ReviewDate);

        // 2.(调)调用ServiceDAO的方法，完成对应业务
        JsonData jd = dto.insert(pojo);

        // 3.(存)将数据对象存储到request作用范围变量
        request.setAttribute("JsonData", jd);

        // 4.(转)将业务转发到View
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);
    }
}
