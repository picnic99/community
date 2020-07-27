package com.hyy.community.community.dto;

import com.hyy.community.community.model.Comment;
import com.hyy.community.community.model.Question;
import com.hyy.community.community.model.User;
import lombok.Data;

@Data
public class NoticeDTO {
    private User user;
    private Integer Type;
    private String TypeMessage;
    private Question question;
    private Comment comment;
}
