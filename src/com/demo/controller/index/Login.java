package com.demo.controller.index;

import com.demo.pojo.JsonData;
import com.demo.pojo.User;
import com.demo.service.IndexServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Login", value = "/com/demo/controller/index/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IndexServiceImpl dto = new IndexServiceImpl();
    User pojo = new User();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //��ʼ��
        request.setCharacterEncoding("utf-8");
        //1.(��)��ȡ����ֵ�������浽POJO����
        String Username = request.getParameter("Username");
        String Password = request.getParameter("Password");
//        System.out.println(Username);
//        System.out.println(Password);

        String str = "";
        if (Username == null || Username.equals("")) {
            return;
        }else{
            str +=" Username = '"+Username+"'";
        }
        if (Password == null || Password.equals("")) {
            return;
        }else{
            str +=" and `Password` = '"+Password+"'";
        }
        //System.out.println(str);
        pojo.setCondition(str);
        //2.(��)����ServiceDAO�ķ�������ɶ�Ӧҵ��
        // System.out.println(pojo.toString());
        JsonData jd = dto.login(pojo);
        request.setAttribute("JsonData", jd);
        //��¼ҵ���У��������һ������
        long i = jd.getTotal();
//        System.out.println(i);
        if(i==0){ jd.setMsg("�˺Ż��������"); }
        else if(i>1){ jd.setMsg("�ظ����˺ţ�����ϵ����Ա��"); }
        else {jd.setMsg("����ɹ�");}
//        System.out.println(jd.getMsg());
        //4.(ת)��ҵ��ת����View��
        //����JsonResultSet����¼��total���¼��rowsƴ�ӳɡ�total=50,rows=[{},{}...{}]����ʽ
        RequestDispatcher rd = request.getRequestDispatcher("/com/demo/view/JSON");
        rd.forward(request, response);
    }
}
