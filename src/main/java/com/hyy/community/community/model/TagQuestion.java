package com.hyy.community.community.model;

import lombok.Data;

@Data
public class TagQuestion {
    private Long id;

    private Long questionId;

    private Long tagId;
}