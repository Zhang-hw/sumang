package com.sibd.sumang.web.servlet;

import com.sibd.sumang.biz.RouteBiz;
import com.sibd.sumang.biz.impl.RouteBizImpl;
import com.sibd.sumang.pojo.*;
import com.sibd.sumang.util.PageBean;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    RouteBiz routeBiz = new RouteBizImpl();

    /**
     * 分页展示 listPage
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void listPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String currentPage_ = request.getParameter("currentPage");
        String rname = request.getParameter("rname");
        int currentPage;
        if (StringUtils.isEmpty(cid)) {
            cid = "0";
        }
        if (StringUtils.isEmpty(currentPage_)) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(currentPage_);
        }
        if (!StringUtils.isEmpty(rname) && !rname.equals("null")) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        } else {
            rname = "";
        }

        PageBean pageBean = routeBiz.findPage(cid, currentPage, rname);
        writeJson(response, pageBean);
    }

    /**
     * 用rid获取详情路线
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = routeBiz.findByRid(rid);
        writeJson(response, route);
    }

    /**
     * 判断是否已经被收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isfavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        ResultInfo resultInfo = new ResultInfo();
        if (request.getServletContext().getAttribute("user") != null) {
            User user = (User) request.getServletContext().getAttribute("user");
            Favorite favorite = routeBiz.findFavorite(rid, user.getUid());
            if (favorite != null) {
                resultInfo.setFlag(true);
            } else {
                resultInfo.setFlag(false);
            }
        } else {
            resultInfo.setFlag(false);
        }

        writeJson(response, resultInfo);
    }

    /**
     * 添加收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String uid = request.getParameter("uid");
        final String rid = request.getParameter("rid");
        routeBiz.addFavorite(uid, rid);
    }


    /**
     * 收藏排行榜分页
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void favListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String low = request.getParameter("low");
        String high = request.getParameter("high");
        String currentPage_ = request.getParameter("currentPage");
        String rname = request.getParameter("name");
        int currentPage;
        if (StringUtils.isEmpty(currentPage_)) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(currentPage_);
        }
        if (!StringUtils.isEmpty(rname) && !rname.equals("null")) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        } else {
            rname = "";
        }

        PageBean pageBean = routeBiz.favFindPage(low, high, currentPage, rname);
        writeJson(response, pageBean);
    }

    public void myFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uid = request.getParameter("uid");
        int currentPage;
        String currentPage_ = request.getParameter("currentPage");
        if (StringUtils.isEmpty(currentPage_)) {
            currentPage = 1;
        } else {
            currentPage = Integer.parseInt(currentPage_);
        }
        PageBean pageBean = routeBiz.myFavFindPage(uid, currentPage);
        writeJson(response, pageBean);
    }

    public void popularity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> list = routeBiz.findPopularity();
        writeJson(response, list);
    }

    public void newest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> list = routeBiz.findNewest();
        writeJson(response, list);
    }

    public void theme(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> list = routeBiz.findTheme();
        writeJson(response, list);
    }

    public void hotRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rname = request.getParameter("rname");
        if (!StringUtils.isEmpty(rname) && !rname.equals("null") && !(rname==null)) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        } else {
            rname = "";
        }
        List<Route> list = routeBiz.findHot(rname);
        writeJson(response, list);
    }

    public void bigPic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<BigPic> list = routeBiz.findBigPic();
        writeJson(response, list);
    }

    public void china(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int rondom = (int) (Math.random()*500+1);
        List<Route> list = routeBiz.findChina();
        writeJson(response,list);
    }


}




