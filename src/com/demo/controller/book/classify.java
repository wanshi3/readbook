package com.demo.controller.book;

import com.demo.pojo.Book;
import com.demo.pojo.JsonData;
import com.demo.service.BookIndexServicelmpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "classify", value = "/com/demo/controller/book/classify")
public class classify extends HttpServlet {
    Book book =new Book();
    BookIndexServicelmpl dto =new BookIndexServicelmpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        //1.(参)获取参数值，并保存到POJO对象
        String Genre = request.getParameter("Genre");
        String Theme = request.getParameter("Theme");
        String param = request.getParameter("param");
        if (param != null && param.length() > 0){
            book.setCondition(" book.Title like '%"+param+"%' or book.Author like '%"+param+"%'");
        }else{
            book.setCondition("");
        }
        String str = "";
        str+="Theme = '"+Theme+"' and Genre ="+"'"+Genre+"'";
        System.out.println(str);
        book.setCondition(str);
        book.setLimit(" and "+"9");
        JsonData jd =dto.index(book);
        request.setAttribute("JsonData", jd);
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);

    }
}
