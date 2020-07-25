package com.hyy.community.community.service;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.model.Question;

import java.util.List;

public interface QuestionService {
    int insert(Question question);
    PageInfo<QuestionDTO> list(Integer createId,Integer pageNum, Integer pageSize);
    QuestionDTO getById(Integer id);
    void update(Question question);


    void incView(Integer id);
}
