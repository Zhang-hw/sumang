package com.sibd.sumang.dao.impl;

import com.sibd.sumang.dao.FavoriteDao;
import com.sibd.sumang.pojo.Favorite;
import com.sibd.sumang.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao{
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());


    @Override
    public Favorite findOne(String rid, int uid) {
        Favorite favorite=null;
        try {
             favorite = jdbcTemplate.queryForObject("select * from tab_favorite where uid =? and rid=?", new BeanPropertyRowMapper<Favorite>(Favorite.class), uid, rid);

        }catch (Exception e){
            favorite=null;
        }
        return favorite;
    }

    @Override
    public void add(String uid, String rid, Date date) {
        jdbcTemplate.update("insert into tab_favorite values(?,?,?)",rid,date,uid);
    }
}
