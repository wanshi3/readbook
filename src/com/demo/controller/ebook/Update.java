package com.demo.controller.ebook;


import com.demo.pojo.Ebook;
import com.demo.pojo.JsonData;
import com.demo.service.EBookIndexServiceImpl;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.swing.text.View;
import java.io.IOException;

@WebServlet(name = "Update3", value = "/com/demo/controller/ebook/Update")
public class Update extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        EBookIndexServiceImpl dto = new EBookIndexServiceImpl();
        Ebook pojo = new Ebook();
        // 1.(参)获取参数值，并保存到POJO对象
        int EbookID=0;
        // sequence,name,gender,birthday,card,nation,nativePlace,political,userId
        if (request.getParameter("EbookID")!=null)
            EbookID= Integer.parseInt(request.getParameter("EbookID"));
        pojo.setEBookID(EbookID);
        // 1.(参)获取参数值，并保存到POJO对象
        String Title = request.getParameter("Title");
        if(Title == ""){Title = null;}

        String Author = request.getParameter("Author");
        if(Author == ""){Author = null;}

        String views = request.getParameter("Views");
        if(views == ""){views = null;}

        String Theme = request.getParameter("Theme");
        if(Theme == ""){Theme = null;}

        String Genre = request.getParameter("Genre");
        if(Genre == ""){Genre  = null;}

        String Picture = request.getParameter("Picture");
        if(Picture == ""){Picture = null;}

        pojo.setTitle(Title);
        pojo.setAuthor(Author);
        pojo.setViews(views);
        pojo.setTheme(Theme);
        pojo.setGenre(Genre);
        pojo.setPicture(Picture);
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
