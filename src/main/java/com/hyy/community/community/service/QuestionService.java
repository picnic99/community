package com.hyy.community.community.service;

import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.model.Question;

import java.util.List;

public interface QuestionService {
    void insert(Question question);
    public List<QuestionDTO> list();
}
