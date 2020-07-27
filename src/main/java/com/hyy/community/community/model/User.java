package com.hyy.community.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;

    private String accountId;

    private String name;

    private String token;

    private Long gmtCreate;

    private Long gmtModified;

    private String bio;

    private String avatarUrl;

    private String account;

    private String password;

    }