package com.hyy.community.community.service;

import com.hyy.community.community.model.User;

public interface UserService {
    void insert(User user);
    User findByToken(String token);
    User findByAccountId(String accountId);
    void update(User user);
    User getByAccountAndPassword(String account,String password);
}
