package com.sibd.sumang.biz.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sibd.sumang.biz.CategoryBiz;
import com.sibd.sumang.dao.CategoryDao;
import com.sibd.sumang.dao.impl.CategoryDaoImpl;
import com.sibd.sumang.pojo.Category;
import com.sibd.sumang.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryBizImpl implements CategoryBiz{
    private CategoryDao categoryDao=new CategoryDaoImpl();
    @Override
    public List<Category> findAll() throws JsonProcessingException {
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> set = jedis.zrangeWithScores("cate", 0, -1);

        if (set!=null&&set.size()>0){
            List<Category> list=new ArrayList<>();
            for (Tuple t:set
                 ) {
                Category category=new Category();
                category.setCid((int) t.getScore());
                category.setCname(t.getElement());
                list.add(category);
            }
          return list;
        }else {
            List<Category> list= categoryDao.findAll();
            for (Category c:list
                 ) {
                jedis.zadd("cate",c.getCid(),c.getCname());
            }
            return list;
        }
    }
}
