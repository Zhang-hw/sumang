package com.sibd.sumang.biz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sibd.sumang.pojo.Category;

import java.util.List;

public interface CategoryBiz {
    List<Category> findAll() throws JsonProcessingException;

}
