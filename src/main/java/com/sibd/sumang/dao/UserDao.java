package com.sibd.sumang.dao;

import com.sibd.sumang.pojo.User;

public interface UserDao {
    boolean insert(User user);

    void update(User user);

    User findUserByCode(String code);

    User findUserByNameAndPwd(User user);

    User findUserByName(String username);

    User findUserById(int uid);
}
