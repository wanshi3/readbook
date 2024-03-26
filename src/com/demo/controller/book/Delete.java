package com.demo.controller.book;

import com.demo.pojo.Book;
import com.demo.pojo.JsonData;
import com.demo.service.BookIndexServicelmpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Delete2", value = "/com/demo/controller/book/Delete")
public class Delete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        BookIndexServicelmpl dto = new BookIndexServicelmpl();
        Book pojo = new Book();
        String BookID2 = request.getParameter("BookID");
        int BookID=-1;
        // 1.(参)获取参数值，并保存到POJO对象
        if (BookID2 != null)
            BookID= Integer.parseInt(BookID2);
        pojo.setBookID(BookID);
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
