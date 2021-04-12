package com.sibd.sumang.dao.impl;

import com.sibd.sumang.dao.CategoryDao;
import com.sibd.sumang.pojo.Category;
import com.sibd.sumang.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao{
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        System.out.println("访问数据库了。。。。。。。。。");
        return jdbcTemplate.query("select * from tab_category",new BeanPropertyRowMapper<Category>(Category.class));
    }
}
