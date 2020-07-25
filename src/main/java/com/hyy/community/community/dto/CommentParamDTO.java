package com.hyy.community.community.dto;

import lombok.Data;

@Data
public class CommentParamDTO {
    private Integer type;
    private Integer parentId;
    private Integer orderBy;
}
