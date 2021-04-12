package com.sibd.sumang.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        //servlet名 与 方法名一致
        String uri = requestURI.substring(requestURI.lastIndexOf("/") + 1);
        try {
            Method method = this.getClass().getMethod(uri, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    public void writeJson(HttpServletResponse response,Object data){
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            String json=objectMapper.writeValueAsString(data);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeJson1(HttpServletResponse response,String data) throws IOException {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(data);
    }
}
