package com.hyy.community.community.service.impl;

import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.mapper.QuestionMapper;
import com.hyy.community.community.mapper.UserMapper;
import com.hyy.community.community.model.Question;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void insert(Question question) {
        questionMapper.insert(question);
    }

    public List<QuestionDTO> list(){
        List<Question> questionList= questionMapper.list();
        List<QuestionDTO> questionDTOList =new ArrayList<QuestionDTO>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findById(question.getCreator().toString());
            questionDTO.setQuestion(question);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
