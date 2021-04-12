package com.sibd.sumang.dao.impl;

import com.sibd.sumang.dao.UserDao;
import com.sibd.sumang.pojo.User;
import com.sibd.sumang.util.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao{
    private JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
    @Override
    public boolean insert(User user) {
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,code,status) values(?,?,?,?,?,?,?,?,?)";

        int i = jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),user.getCode(),user.getStatus());
        if (i>=1){
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("update tab_user set status = 'Y' where uid=? ",user.getUid());
    }

    @Override
    public User findUserByCode(String code) {
        User user=null;
        try {
            user=jdbcTemplate.queryForObject("select * from tab_user where code=?",new BeanPropertyRowMapper<User>(User.class),code);
        }catch (Exception e){
            user=null;
        }
        return user;
    }

    @Override
    public User findUserByNameAndPwd(User user) {
        User user1=null;
        try {
            user1=jdbcTemplate.queryForObject("select * from tab_user where username=? and password=?",new BeanPropertyRowMapper<User>(User.class),user.getUsername(),user.getPassword());
        }catch (Exception e){
            user1=null;
        }
        return user1;

    }

    @Override
    public User findUserByName(String username) {
        User user=null;
        try {
            user=jdbcTemplate.queryForObject("select * from tab_user where username=?",new BeanPropertyRowMapper<User>(User.class),username);
        }catch (Exception e){
            user = null;
        }
        return user;
    }

    @Override
    public User findUserById(int uid) {
        User user1=null;
        try {
            user1=jdbcTemplate.queryForObject("select * from tab_user where uid=?",new BeanPropertyRowMapper<User>(User.class),uid);
        }catch (Exception e){
            user1=null;
        }
        return user1;
    }

}
