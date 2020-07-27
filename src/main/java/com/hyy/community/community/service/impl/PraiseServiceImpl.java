package com.hyy.community.community.service.impl;

import com.hyy.community.community.enums.CommentTypeEnum;
import com.hyy.community.community.enums.NoticeTypeEnum;
import com.hyy.community.community.mapper.CommentMapper;
import com.hyy.community.community.mapper.NoticeMapper;
import com.hyy.community.community.mapper.PraiseMapper;
import com.hyy.community.community.mapper.QuestionMapper;
import com.hyy.community.community.model.Comment;
import com.hyy.community.community.model.Notice;
import com.hyy.community.community.model.Praise;
import com.hyy.community.community.model.Question;
import com.hyy.community.community.service.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PraiseServiceImpl implements PraiseService {
    @Autowired
    private PraiseMapper praiseMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Override
    @Transactional
    public void addLike(Praise praise) {
        Praise praiseDB = praiseMapper.getByParam(praise);

        if (praiseDB==null){
            //插入字段
            praise.setStatus(1);
            praise.setGmtCreate(System.currentTimeMillis());
            praiseMapper.insert(praise);
            addPraiseNotice(praise);

        }else{
            //更新字段
            praiseDB.setStatus(1);
            praiseDB.setGmtCreate(System.currentTimeMillis());
            praiseMapper.updateByPrimaryKey(praiseDB);
        }

        if (praise.getType()== CommentTypeEnum.QUESTION.getType()){
            questionMapper.incLike(praise.getRelativeId());
        }
        if (praise.getType()== CommentTypeEnum.COMMENT.getType()){
            commentMapper.incLike(praise.getRelativeId());
        }

    }

    private void addPraiseNotice(Praise praise) {
        Notice notice = new Notice();
        notice.setSendId(praise.getUserId());
        if (praise.getType()== CommentTypeEnum.QUESTION.getType()){
            Question question = questionMapper.getById(praise.getRelativeId().intValue());
            notice.setReceiveId(question.getCreator().longValue());
            notice.setType(NoticeTypeEnum.LIKE_QUESTION.getCode());
        }
        if (praise.getType()==CommentTypeEnum.COMMENT.getType()){
            Comment comment = commentMapper.selectByPrimaryKey(praise.getRelativeId());
            notice.setReceiveId(comment.getCommentator().longValue());
            notice.setType(NoticeTypeEnum.LIKE_COMMENT.getCode());

        }
        notice.setRelativeId(praise.getRelativeId());
        notice.setGmtCreate(System.currentTimeMillis());
        noticeMapper.insert(notice);
    }

    @Override
    public void removeLike(Praise praise) {
        Praise praiseDB = praiseMapper.getByParam(praise);
        praiseDB.setStatus(0);
        praiseMapper.updateByPrimaryKey(praiseDB);
        if (praise.getType()== CommentTypeEnum.QUESTION.getType()){
            questionMapper.decLike(praise.getRelativeId());
        }
        if (praise.getType()== CommentTypeEnum.COMMENT.getType()){
            commentMapper.decLike(praise.getRelativeId());
        }
    }
}
