package com.hyy.community.community.dto;

import com.hyy.community.community.model.Comment;
import com.hyy.community.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Comment comment;
    private Integer count;
    private User formuser;
    private User touser;

}
