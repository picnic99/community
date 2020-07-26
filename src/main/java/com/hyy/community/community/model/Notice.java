package com.hyy.community.community.model;

import lombok.Data;

@Data
public class Notice {
    private Long id;

    private Long sendId;

    private Long receiveId;

    private Long relativeId;

    private Integer type;

    private Long gmtCreate;

    private Integer status;

}