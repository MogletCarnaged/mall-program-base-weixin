package com.mall.dao;

import com.mall.model.User;

public interface UserDao {
    User getUserByOpenid(String openid);
    boolean addUser(User user);
}
