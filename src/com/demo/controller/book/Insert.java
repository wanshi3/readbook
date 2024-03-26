package com.demo.controller.book;

import com.demo.pojo.Book;
import com.demo.pojo.JsonData;
import com.demo.service.BookIndexServicelmpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Insert2", value = "/com/demo/controller/book/Insert")
public class Insert extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        BookIndexServicelmpl dto = new BookIndexServicelmpl();//数据传输层对象，用于调用业务层方法
        Book pojo = new Book();
        // 1.(参)获取参数值，并保存到POJO对象
        String Title = request.getParameter("Title");
        if(Title == ""){Title = null;}

        String Author = request.getParameter("Author");
        if(Author == ""){Author = null;}

        String Price = request.getParameter("Price");
        if(Price == ""){Price = null;}

        String PublicationYear = request.getParameter("PublicationYear");
        if(PublicationYear == ""){PublicationYear = null;}

        String Theme = request.getParameter("Theme");
        if(Theme == ""){Theme = null;}

        String Genre = request.getParameter("Genre");
        if(Genre == ""){Genre  = null;}

        String InStock = request.getParameter("InStock");
        if(InStock == ""){InStock = null;}

        String Picture = request.getParameter("Picture");
        if(Picture == ""){Picture = null;}

        pojo.setTitle(Title);
        pojo.setAuthor(Author);
        pojo.setPrice(Double.parseDouble(Price));
        pojo.setPublicationYear(PublicationYear);
        pojo.setTheme(Theme);
        pojo.setGenre(Genre);
        pojo.setInStock(InStock);
        pojo.setPicture(Picture);
        // 2.(调)调用ServiceDAO的方法，完成对应业务
        JsonData jd = dto.insert(pojo);

        // 3.(存)将数据对象存储到request作用范围变量
        request.setAttribute("JsonData", jd);

        // 4.(转)将业务转发到View
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);

    }
}
