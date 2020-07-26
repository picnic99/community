package com.hyy.community.community.service.impl;

import com.hyy.community.community.mapper.TagMapper;
import com.hyy.community.community.mapper.TagQuestionMapper;
import com.hyy.community.community.model.Tag;
import com.hyy.community.community.model.TagQuestion;
import com.hyy.community.community.service.TagQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagQuestionServiceImpl implements TagQuestionService {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagQuestionMapper tagQuestionMapper;
    @Override
    @Transactional
    public void addTagQuestion(List<Tag> tags, Long questionId) {
        TagQuestion tagQuestion = new TagQuestion();

        for (Tag tag : tags) {
            Tag temp = tagMapper.getByName(tag.getName());
            if(temp==null){
                tagMapper.insert(tag);
                tagQuestion.setTagId(tag.getId());
            }else{
                temp.setUseCount(temp.getUseCount()+1);
                tagMapper.updateByPrimaryKeySelective(temp);
                tagQuestion.setTagId(temp.getId());
            }
            tagQuestion.setQuestionId(questionId);
            tagQuestionMapper.insert(tagQuestion);
        }

    }
}
