package com.hyy.community.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.enums.CommentTypeEnum;
import com.hyy.community.community.exception.CustiomizeErrorCode;
import com.hyy.community.community.exception.CustomizeException;
import com.hyy.community.community.mapper.PraiseMapper;
import com.hyy.community.community.mapper.QuestionMapper;
import com.hyy.community.community.mapper.UserMapper;
import com.hyy.community.community.model.Praise;
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
    @Autowired
    private PraiseMapper praiseMapper;
    @Override
    public int insert(Question question) {
        questionMapper.insert(question);
        return question.getId();
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
            //数据写入包装类
            QuestionDTO questionDTO = setQuestionDTO(createId, question);
            questionDTOList.add(questionDTO);
        }
        //写入分页相关数据
        PageInfo<QuestionDTO> questionDTOPageInfo = setQuestionDTOPageInfo(questionPageInfo, questionDTOList);
        return questionDTOPageInfo;
    }



    @Override
    public QuestionDTO getById(Integer currentUser,Integer id) {

        Question question = questionMapper.getById(id);
        if (question==null){
            throw new CustomizeException(CustiomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.findById(question.getCreator().toString());
        //数据写入包装类
        QuestionDTO questionDTO = setQuestionDTO(currentUser, question, user);

        return questionDTO;
    }

    @Override
    public void edit(Question question) {
        Question questionTemp = questionMapper.getById(question.getId());
        question.setCreator(questionTemp.getCreator());
        question.setGmtCreate(questionTemp.getGmtCreate());
        question.setGmtModified(System.currentTimeMillis());
        question.setCommentCount(questionTemp.getCommentCount());
        question.setViewCount(questionTemp.getViewCount());
        question.setLikeCount(questionTemp.getLikeCount());
        questionMapper.update(question);
    }

    @Override
    public void incView(Integer id) {
        questionMapper.incView(id);
    }

    private PageInfo<QuestionDTO> setQuestionDTOPageInfo(PageInfo<Question> questionPageInfo, List<QuestionDTO> questionDTOList) {
        PageInfo<QuestionDTO> questionDTOPageInfo = new PageInfo<>(questionDTOList,10);
        questionDTOPageInfo.setTotal(questionPageInfo.getTotal());
        questionDTOPageInfo.setPages(questionPageInfo.getPages());
        questionDTOPageInfo.setPageNum(questionPageInfo.getPageNum());
        questionDTOPageInfo.setNavigatepageNums(questionPageInfo.getNavigatepageNums());
        return questionDTOPageInfo;
    }

    private QuestionDTO setQuestionDTO(Integer createId, Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        User user = userMapper.findById(question.getCreator().toString());
        questionDTO.setQuestion(question);
        questionDTO.setUser(user);
        String tag = question.getTag();
        String[] tags = tag.split(",");
        questionDTO.setTags(tags);
        questionDTO.setIsPraise(0);
        if(createId!=null){
            Praise praise = new Praise();
            praise.setUserId(createId.longValue());
            praise.setRelativeId(question.getId().longValue());
            praise.setType(CommentTypeEnum.QUESTION.getType());
            Praise praiseDB = praiseMapper.getByParam(praise);
            if (praiseDB!=null&&praiseDB.getStatus()==1){
                questionDTO.setIsPraise(1);
            }
        }
        return questionDTO;
    }


    private QuestionDTO setQuestionDTO(Integer currentUser, Question question, User user) {
        QuestionDTO questionDTO = new QuestionDTO();
        String tag = question.getTag();
        String[] tags = tag.split(",");
        questionDTO.setTags(tags);
        questionDTO.setQuestion(question);
        questionDTO.setUser(user);
        questionDTO.setIsPraise(0);

        //是否点赞
        if(currentUser!=null){
            Praise praise = new Praise();
            praise.setUserId(currentUser.longValue());
            praise.setType(CommentTypeEnum.QUESTION.getType());
            praise.setRelativeId(question.getId().longValue());
            Praise praiseDB = praiseMapper.getByParam(praise);
            if (praiseDB!=null&&praiseDB.getStatus()==1){
                questionDTO.setIsPraise(1);
            }
        }
        return questionDTO;
    }



}
