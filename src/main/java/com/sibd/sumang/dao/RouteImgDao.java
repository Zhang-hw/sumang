package com.sibd.sumang.dao;

import com.sibd.sumang.pojo.RouteImg;

import java.util.List;

public interface RouteImgDao {

    List<RouteImg> findImagesByRid(String rid);

}
