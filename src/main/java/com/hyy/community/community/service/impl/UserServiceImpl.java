package com.hyy.community.community.service.impl;

import com.hyy.community.community.mapper.UserMapper;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public User findByToken(String token) {
        return userMapper.findByToken(token);
    }

    @Override
    public User findByAccountId(String accountId) {
        return userMapper.findByAccountId(accountId);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public User getByAccountAndPassword(String account, String password) {
        return userMapper.getByAccountAndPassword(account,password);
    }
}
