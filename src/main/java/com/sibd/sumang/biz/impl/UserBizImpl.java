package com.sibd.sumang.biz.impl;

import com.sibd.sumang.biz.UserBiz;
import com.sibd.sumang.dao.UserDao;
import com.sibd.sumang.dao.impl.UserDaoImpl;
import com.sibd.sumang.pojo.User;

public class UserBizImpl implements UserBiz{

    private UserDao userDao=new UserDaoImpl();
    @Override
    public boolean register(User user) {
        return userDao.insert(user);
    }

    @Override
    public User findUserByCode(String code) {
        return userDao.findUserByCode(code);
    }

    @Override
    public void active(User user) {
        userDao.update(user);
    }

    @Override
    public User findUserByNameAndPwd(User user) {
        return userDao.findUserByNameAndPwd(user);
    }

    @Override
    public User findUserByName(String username) {
        return userDao.findUserByName(username);
    }
}
