package com.hyy.community.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.exception.CustiomizeErrorCode;
import com.hyy.community.community.exception.CustomizeException;
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

    @Override
    public PageInfo<QuestionDTO> list(Integer createId,Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questionList=null;
        if(createId==null){
            questionList = questionMapper.list();
        }else{
            questionList = questionMapper.listByCreateId(createId);
        }

        PageInfo<Question> questionPageInfo = new PageInfo<Question>(questionList,10);
        List<QuestionDTO> questionDTOList =new ArrayList<QuestionDTO>();
        for (Question question : questionPageInfo.getList()) {
            QuestionDTO questionDTO = new QuestionDTO();
            User user = userMapper.findById(question.getCreator().toString());
            questionDTO.setQuestion(question);
            questionDTO.setUser(user);
            String tag = question.getTag();
            String[] tags = tag.split(",");
            questionDTO.setTags(tags);
            questionDTOList.add(questionDTO);
        }
        PageInfo<QuestionDTO> questionDTOPageInfo = new PageInfo<>(questionDTOList,10);
        questionDTOPageInfo.setTotal(questionPageInfo.getTotal());
        questionDTOPageInfo.setPages(questionPageInfo.getPages());
        questionDTOPageInfo.setPageNum(questionPageInfo.getPageNum());
        questionDTOPageInfo.setNavigatepageNums(questionPageInfo.getNavigatepageNums());
        return questionDTOPageInfo;
    }

    @Override
    public QuestionDTO getById(Integer id) {

        Question question = questionMapper.getById(id);
        if (question==null){
            throw new CustomizeException(CustiomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findById(question.getCreator().toString());
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(question);
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void update(Question question) {
        questionMapper.update(question);
    }

    @Override
    public void incView(Integer id) {
        questionMapper.incView(id);
    }

}
