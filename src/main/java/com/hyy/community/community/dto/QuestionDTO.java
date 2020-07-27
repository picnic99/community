package com.hyy.community.community.dto;

import com.hyy.community.community.model.Question;
import com.hyy.community.community.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    Question question;
    String[] tags;
    User user;
    Integer isPraise;
}
