package com.sibd.sumang.biz;

import com.sibd.sumang.pojo.BigPic;
import com.sibd.sumang.pojo.Fav;
import com.sibd.sumang.pojo.Favorite;
import com.sibd.sumang.pojo.Route;
import com.sibd.sumang.util.PageBean;

import java.util.List;

public interface RouteBiz {

    PageBean findPage(String cid, int currentPage,String rname);


    Route findByRid(String rid);

    Favorite findFavorite(String rid, int uid);

    void addFavorite(String uid, String rid);

    List<Route> findFavoriteRank();

    PageBean favFindPage(String low, String high, int currentPage, String rname);



    PageBean myFavFindPage(String uid, int currentPage);

    List<Route> findPopularity();

    List<Route> findNewest();

    List<Route> findTheme();

    List<Route> findHot(String rname);

    List<BigPic> findBigPic();

    List<Route> findChina();

}
