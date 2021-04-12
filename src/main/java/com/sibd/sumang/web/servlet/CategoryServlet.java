package com.sibd.sumang.web.servlet;

import com.sibd.sumang.biz.CategoryBiz;
import com.sibd.sumang.biz.impl.CategoryBizImpl;
import com.sibd.sumang.pojo.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategoryBiz categoryBiz=new CategoryBizImpl();
        List<Category> list=categoryBiz.findAll();

        writeJson(response,list);
    }

}
