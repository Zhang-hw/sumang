package com.sibd.sumang.web.servlet;

import com.sibd.sumang.biz.UserBiz;
import com.sibd.sumang.biz.impl.UserBizImpl;
import com.sibd.sumang.pojo.ResultInfo;
import com.sibd.sumang.pojo.User;
import com.sibd.sumang.util.MailUtils;
import com.sibd.sumang.util.UuidUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    /**
     * LoginServlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo resultInfo=new ResultInfo();
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码输入错误");
        }else {
            Map<String, String[]> map = request.getParameterMap();
            User user = new User();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            UserBiz userBiz=new UserBizImpl();
            User user1=userBiz.findUserByNameAndPwd(user);
            if (user1!=null){
                if (user1.getStatus().equals("Y")){
                    resultInfo.setFlag(true);
                    request.getServletContext().setAttribute("user",user1);
                }else {
                    resultInfo.setFlag(false);
                    resultInfo.setErrorMsg("用户未激活，请先去邮箱一键激活");
                }
            }else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("用户名或密码错误");

            }

        }
       writeJson(response,resultInfo);
    }

    /**
     * ActiveServlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        UserBiz userBiz=new UserBizImpl();
        User user=userBiz.findUserByCode(code);
        if (user!=null){
            user.setStatus("Y");
            userBiz.active(user);
            response.sendRedirect("/sumang/login.html");
        }else {
            response.getWriter().write("激活失败，请联系管理员");
        }
    }

    /**
     * ExitServlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().removeAttribute("user");

    }

    /**
     * FindUserServlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user= (User) request.getServletContext().getAttribute("user");
       writeJson(response,user);
    }

    /**
     * RegisterServlet
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
       ResultInfo resultInfo=new ResultInfo();
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){

            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码输入错误");

        }else {

            Map<String, String[]> map = request.getParameterMap();
            User user=new User();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            UserBiz userBiz=new UserBizImpl();
            user.setCode(UuidUtil.getUuid());
            user.setStatus("N");
            boolean flag = userBiz.register(user);

            if(flag){
                //成功 发邮件
                resultInfo.setFlag(true);
                MailUtils.sendMail(user.getEmail(),"你已经注册了苏芒旅游网，"+"<a href='http://localhost:80/sumang/user/active?code="+user.getCode()+"'>单击此处一键激活</a>"+"，如非本人操作，请忽略此邮件，谢谢。","苏芒旅游网");

            }else{
                //注册失败
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("注册失败!");
            }
        }
       writeJson(response,resultInfo);
    }

    /**
     * 判断用户名是否可用
     */
    public void judgeAvailable (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String username = request.getParameter("username");
        UserBiz userBiz = new UserBizImpl();
        User user = userBiz.findUserByName(username);
        String json = "";
        if (user != null && user.getUsername() != "") {
            json = "{\"msg\":\"用户已存在\",\"isTrue\":false,\"data\":null}";
        } else {
            json = "{\"msg\":\"用户不存在\",\"isTrue\":true,\"data\":null}";
        }
        System.out.println(json);
        response.getWriter().write(json);
    }
}
