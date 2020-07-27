package com.hyy.community.community.model;

import lombok.Data;

@Data
public class Praise {
    private Long id;

    private Long userId;

    private Long relativeId;

    private Integer type;

    private Long gmtCreate;

    private Integer status;

}