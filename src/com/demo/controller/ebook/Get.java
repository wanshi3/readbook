package com.demo.controller.ebook;
import com.demo.pojo.Ebook;
import com.demo.pojo.JsonData;
import com.demo.service.EBookIndexService;
import com.demo.service.EBookIndexServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Get2", value = "/com/demo/controller/ebook/Get")
public class Get extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        EBookIndexServiceImpl dto = new EBookIndexServiceImpl();//数据传输层对象，用于调用业务层方法
        Ebook pojo = new Ebook();

        // 1.(参)获取参数值，并保存到POJO对象
        // 查询条件
        // 如果参数不固定怎么办？
        // 回忆一下前面讲到的request.getParamterNames()的处理方式
        String param = request.getParameter("param");
        System.out.println(param);
        //param = new String(param.getBytes("iso-8859-1"),"utf-8");
        if (param != null && param.length() > 0){
            pojo.setCondition(" ebook.Title like '%"+param+"%' or ebook.Author like '%"+param+"%'");
        }else{
            pojo.setCondition("");
        }

        // 分页条件
        String offset = request.getParameter("offset");//起始页(bootstrap-table默认用这个名字)
        String limit = request.getParameter("limit");//每页记录数(bootstrap-table默认用这个名字)
        if (offset != null && offset.length() > 0){
            pojo.setLimit(" limit "+offset+","+limit);
        }else{
            pojo.setLimit("");
        }
        String flage =request.getParameter("flage");
        System.out.println(flage);
        if (flage!=null && flage.length()>0)
        {
            pojo.setLimit(" limit "+limit);
        }

        String Genre = request.getParameter("Genre");
        String Theme = request.getParameter("Theme");
        String str = "";
        if (Theme != null && Theme.length() > 0){
            str+="Theme = '"+Theme;
        }else{
            str="";
        }
        if (Genre != null && Genre.length() > 0){
            str+="' and Genre ='"+Genre+"'";
        }else{
            str="";
        }
        pojo.setCondition(str);
        // 排序条件
        String sort = request.getParameter("sort");//排序字段(bootstrap-table默认用这个名字)
        String order = request.getParameter("order");//排序方式(bootstrap-table默认用这个名字)
        if (sort != null && sort.length() > 0){
            pojo.setOrderBy(" order by "+sort+" "+order);
        }else{
            pojo.setOrderBy("");
        }



        System.out.println(pojo.getOrderBy());
        // 2.(调)调用ServiceDAO的方法，完成对应业务
//         System.out.println(pojo.toString());
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
