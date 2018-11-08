package com.boot.dubbo.demo.dubboprovider.service;

import com.boot.dubbo.demo.api.UserService;
import com.boot.dubbo.demo.domain.UserInfo;

public class UserServiceImpl implements UserService{
    @Override
    public UserInfo login(UserInfo user) {
        UserInfo reUser = new UserInfo();
        reUser.setAccount("登录的账号为:" + reUser.getAccount());
        reUser.setPassword("登录密码为:" + reUser.getPassword());

        return reUser;

    }
}
