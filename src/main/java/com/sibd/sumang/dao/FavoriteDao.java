package com.sibd.sumang.dao;

import com.sibd.sumang.pojo.Favorite;

import java.util.Date;

public interface FavoriteDao {


    Favorite findOne(String rid, int uid);

    void add(String uid, String rid, Date date);
}
