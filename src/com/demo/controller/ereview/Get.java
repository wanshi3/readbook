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

@WebServlet(name = "Get7", value = "/com/demo/controller/ereview/Get")
public class Get extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        EReviewIndexServicelmpl dto = new EReviewIndexServicelmpl();//数据传输层对象，用于调用业务层方法
        Reviewebook pojo =new Reviewebook();
        String BookID = request.getParameter("BookID");
        if (BookID != null && BookID.length() > 0){
            pojo.setCondition(" reviewebook.EBookID = "+BookID);
        }else{
            pojo.setCondition("");
        }
//        if (BookID != null && BookID.length() > 0){
//            pojo2.setCondition(" BookID = '"+BookID+"'");
//        }else{
//            pojo2.setCondition("");
//        }
        JsonData jd = dto.select(pojo);
        // System.out.println(jd.toString());
        // 3.(存)将数据对象存储到request作用范围变量
        request.setAttribute("JsonData", jd);

        // 4.(转)将业务转发到View层
        // 利用JsonResultSet将记录数total与记录集rows拼接成“total=50,rows=[{},{}...{}]”格式
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);
    }
}
