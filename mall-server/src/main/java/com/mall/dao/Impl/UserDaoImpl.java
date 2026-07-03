package com.mall.dao.Impl;

import com.mall.common.Global;
import com.mall.dao.UserDao;
import com.mall.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User getUserByOpenid(String openid) {
        String sql = "select * from user where openid = ?";
        JdbcTemplate jt = new JdbcTemplate(Global.getDataSource());
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
        List<User> users = jt.query(sql, rowMapper, openid);
        if(users.isEmpty()){
           return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public boolean addUser(User user) {
        String sql = "insert into user (openid, nickname) values (?, ?)";
        JdbcTemplate jt = new JdbcTemplate(Global.getDataSource());
        return  jt.update(sql, user.getOpenid(), user.getNickname()) > 0;
    }

}
