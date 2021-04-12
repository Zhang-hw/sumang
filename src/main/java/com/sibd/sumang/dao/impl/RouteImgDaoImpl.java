package com.sibd.sumang.dao.impl;

import com.sibd.sumang.dao.RouteImgDao;
import com.sibd.sumang.pojo.RouteImg;
import com.sibd.sumang.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao{
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());


    @Override
    public List<RouteImg> findImagesByRid(String rid) {
         List<RouteImg> images
                = jdbcTemplate.query("select * from tab_route_img where rid=?", new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return images;
    }
}
