package com.sibd.sumang.biz;

import com.sibd.sumang.pojo.User;

public interface UserBiz {
    boolean register(User user);

    User findUserByCode(String code);

    void active(User user);

    User findUserByNameAndPwd(User user);

    User findUserByName(String username);
}
