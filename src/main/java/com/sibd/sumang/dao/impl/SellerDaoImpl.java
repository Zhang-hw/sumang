package com.sibd.sumang.dao.impl;

import com.sibd.sumang.dao.SellerDao;
import com.sibd.sumang.pojo.Seller;
import com.sibd.sumang.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao{
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public Seller findBySid(int sid) {
         Seller seller = jdbcTemplate.queryForObject("select * from tab_seller where sid=?", new BeanPropertyRowMapper<Seller>(Seller.class), sid);
        return seller;
    }
}
