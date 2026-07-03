package com.mall.service.Impl;

import com.mall.dao.Impl.UserDaoImpl;
import com.mall.dao.UserDao;
import com.mall.model.User;
import com.mall.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();
    @Override
    public User loginAndRegister(User user) {
        User target = userDao.getUserByOpenid(user.getOpenid());
        if(target==null && user.getNickname()!=null && !user.getNickname().isEmpty()){
            userDao.addUser(user);
            target = user;
        }
        return target;
    }
}
