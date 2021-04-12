package com.sibd.sumang.dao;

import com.sibd.sumang.pojo.BigPic;
import com.sibd.sumang.pojo.Fav;
import com.sibd.sumang.pojo.Route;

import java.util.List;

public interface RouteDao {

    int findCount(String cid,String rname);

    List<Route> findByPage(String cid, int currentPage, int pageSize,String rname);

    Route findRouteById(String rid);

    void updateCount(String rid);

    List<Route> findFavoriteRank();

    int favFindCount(String low, String high, String rname);

    List<Route> favFindByPage(String low, String high, int currentPage, int pageSize, String rname);

    int myFavFindCount(String uid);

    List<Route> myFavFindByPage(List<Fav> favs, int currentPage);

    List<Fav> findRids(String uid);

    List<Route> findPopularity();

    List<Route> findNewest();

    List<Route> findTheme();

    List<Route> findHot(String rname);

    List<BigPic> findBigPic();

    List<Route> findChina();
}
