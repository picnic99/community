package com.hyy.community.community.model;

import lombok.Data;

@Data
public class Tag {
    private Long id;

    private String name;

    private Long creator;

    private Long gmtCreate;
}