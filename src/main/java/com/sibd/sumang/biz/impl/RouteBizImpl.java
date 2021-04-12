package com.sibd.sumang.biz.impl;

import com.sibd.sumang.biz.RouteBiz;
import com.sibd.sumang.dao.*;
import com.sibd.sumang.dao.impl.*;
import com.sibd.sumang.pojo.*;
import com.sibd.sumang.util.ConstUtils;
import com.sibd.sumang.util.PageBean;

import java.util.Date;
import java.util.List;

public class RouteBizImpl implements RouteBiz{
    private RouteDao routeDao=new RouteDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private RouteImgDao routeImgDao=new RouteImgDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    private UserDao userDao=new UserDaoImpl();
    private String uid;

    @Override
    public PageBean findPage(String cid, int currentPage,String rname) {
        PageBean<Route> pageBean=new PageBean<>();
        pageBean.setPageSize(ConstUtils.PAGE_SIZE);
        pageBean.setCurrentPage(currentPage);
        int totalCount=routeDao.findCount(cid,rname);
        List<Route> list=routeDao.findByPage(cid,currentPage,ConstUtils.PAGE_SIZE,rname);
        pageBean.setList(list);
        pageBean.setTotalCount(totalCount);
        int totalPage=totalCount%pageBean.getPageSize()==0?totalCount/pageBean.getPageSize():totalCount/pageBean.getPageSize()+1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public Route findByRid(String rid) {
        Route route=routeDao.findRouteById(rid);
        Seller seller=sellerDao.findBySid(route.getSid());
        List<RouteImg> images=routeImgDao.findImagesByRid(rid);
        route.setSeller(seller);
        route.setImages(images);
        return route;
    }

    @Override
    public Favorite findFavorite(String rid, int uid) {
        Favorite favorite=favoriteDao.findOne(rid,uid);
        Route route = routeDao.findRouteById(rid);
        favorite.setRoute(route);
        User user=userDao.findUserById(uid);
        favorite.setUser(user);
        return favorite;
    }

    @Override
    public void addFavorite(String uid, String rid) {
        favoriteDao.add(uid,rid,new Date());
        routeDao.updateCount(rid);
    }

    @Override
    public List<Route> findFavoriteRank() {
        List<Route> list = routeDao.findFavoriteRank();
        return list;
    }

    @Override
    public PageBean favFindPage(String low, String high, int currentPage, String rname) {
        PageBean<Route> pageBean=new PageBean<>();
        pageBean.setPageSize(ConstUtils.PAGE_SIZE);
        pageBean.setCurrentPage(currentPage);
        int totalCount=routeDao.favFindCount(low,high,rname);
        List<Route> list=routeDao.favFindByPage(low,high,currentPage,ConstUtils.PAGE_SIZE,rname);
        pageBean.setList(list);
        pageBean.setTotalCount(totalCount);
        int totalPage=totalCount%pageBean.getPageSize()==0?totalCount/pageBean.getPageSize():totalCount/pageBean.getPageSize()+1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }



    public PageBean myFavFindPage(String uid, int currentPage) {
        PageBean<Route> pageBean=new PageBean<>();
        pageBean.setPageSize(ConstUtils.PAGE_SIZE);
        pageBean.setCurrentPage(currentPage);
        List<Fav> favs = routeDao.findRids(uid);
        int totalCount=routeDao.myFavFindCount(uid);
        List<Route> list=routeDao.myFavFindByPage(favs,currentPage);
        pageBean.setList(list);
        pageBean.setTotalCount(totalCount);
        int totalPage=totalCount%pageBean.getPageSize()==0?totalCount/pageBean.getPageSize():totalCount/pageBean.getPageSize()+1;
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public List<Route> findPopularity() {
        return routeDao.findPopularity();
    }

    @Override
    public List<Route> findNewest() {
        return routeDao.findNewest();
    }

    @Override
    public List<Route> findTheme() {
        return routeDao.findTheme();
    }

    @Override
    public List<Route> findHot(String rname) {
        return routeDao.findHot(rname);
    }

    @Override
    public List<BigPic> findBigPic() {
        return routeDao.findBigPic();
    }

    @Override
    public List<Route> findChina() {
        return routeDao.findChina();
    }

}
