package com.hyy.community.community.service;

import com.hyy.community.community.model.Question;
import com.hyy.community.community.model.Tag;
import com.hyy.community.community.model.TagQuestion;

import java.util.List;

public interface TagQuestionService {
    public void addTagQuestion(List<Tag> tags, Long questionId);
}
