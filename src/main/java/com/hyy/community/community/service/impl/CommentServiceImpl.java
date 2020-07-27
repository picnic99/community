package com.hyy.community.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.CommentDTO;
import com.hyy.community.community.dto.CommentParamDTO;
import com.hyy.community.community.enums.CommentTypeEnum;
import com.hyy.community.community.enums.NoticeTypeEnum;
import com.hyy.community.community.enums.OrderByEnum;
import com.hyy.community.community.exception.CustiomizeErrorCode;
import com.hyy.community.community.exception.CustomizeException;
import com.hyy.community.community.mapper.*;
import com.hyy.community.community.model.*;
import com.hyy.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private PraiseMapper praiseMapper;

    @Override
    @Transactional
    public void addComment(Comment comment) {
        Integer type = comment.getType();
        Comment commentDB=null;
        Question questionDB=null;
        Notice notice = new Notice();
        if(comment.getCommentator()==null){
            throw new CustomizeException(CustiomizeErrorCode.USER_NOT_LOGIN);
        }

        if(type.equals(CommentTypeEnum.QUESTION.getType())){
            questionDB = questionMapper.selectByPrimaryKey(comment.getParentId().intValue());
            if(questionDB==null){
                throw new CustomizeException(CustiomizeErrorCode.QUESTION_NOT_FOUND);
            }else{
                notice.setType(NoticeTypeEnum.REPLY_QUESTION.getCode());
                notice.setReceiveId(questionDB.getCreator().longValue());
            }

        }
        if(type.equals(CommentTypeEnum.COMMENT.getType())){
            commentDB = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(commentDB==null){
                throw new CustomizeException(CustiomizeErrorCode.COMMENT_NOT_FOUND);
            }else{
                notice.setType(NoticeTypeEnum.REPLY_COMMENT.getCode());
                notice.setReceiveId(commentDB.getCommentator().longValue());
            }
        }

        notice.setSendId(comment.getCommentator().longValue());
        notice.setRelativeId(comment.getParentId());
        notice.setGmtCreate(comment.getGmtCreate());
        if(notice.getSendId()!=notice.getReceiveId()){
            noticeMapper.insert(notice);
        }
        commentMapper.insert(comment);
        if(type.equals(CommentTypeEnum.QUESTION.getType())){
            questionMapper.incComment(comment.getParentId().intValue());
        }

    }

    @Override
    public PageInfo<CommentDTO> getComments(Integer currentUser,CommentParamDTO commentParamDTO, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> comments=null;
        /**
         *设置查询排序方式
         */
        if(commentParamDTO.getOrderBy()== OrderByEnum.ORDER_BY_TIME.getType()){
            comments = commentMapper.listByTypeAndIdDescByTime(commentParamDTO.getType(),commentParamDTO.getParentId());
        }
        if(commentParamDTO.getOrderBy()== OrderByEnum.ORDER_BY_HOT.getType()){
            comments = commentMapper.listByTypeAndIdDescByLike(commentParamDTO.getType(),commentParamDTO.getParentId());
        }

        /**
         * 装入分页插件中
         */
        PageInfo<Comment> commentPageInfo = new PageInfo<Comment>(comments,10);
        List<CommentDTO> commentDTOList =new ArrayList<CommentDTO>();
        for (Comment comment : commentPageInfo.getList()) {
            CommentDTO commentDTO = new CommentDTO();
            User formuser = userMapper.findById(comment.getCommentator().toString());
            User touser=null;

            if (comment.getBecommentator()!=null){
                touser = userMapper.findById(comment.getBecommentator().toString());
            }

            /**
             * 若该条评论是一级，则获取该条评论的二级评论数量
             */
            if(commentParamDTO.getType()==1){
                int i = commentMapper.countByTypeAndId(2, comment.getId().intValue());
                commentDTO.setCount(i);
            }

            commentDTO.setComment(comment);
            commentDTO.setFormuser(formuser);
            commentDTO.setTouser(touser);
            commentDTO.setIsPraise(0);
            if(currentUser!=null){
                Praise praise = new Praise();
                praise.setUserId(currentUser.longValue());
                praise.setType(CommentTypeEnum.COMMENT.getType());
                praise.setRelativeId(comment.getId());
                Praise praiseDB = praiseMapper.getByParam(praise);
                if (praiseDB!=null&&praiseDB.getStatus()==1){
                    commentDTO.setIsPraise(1);
                }
            }
            commentDTOList.add(commentDTO);
        }
        PageInfo<CommentDTO> commentDTOPageInfo = new PageInfo<>(commentDTOList,10);
        commentDTOPageInfo.setNavigatepageNums(commentPageInfo.getNavigatepageNums());
        commentDTOPageInfo.setPageNum(commentPageInfo.getPageNum());
        commentDTOPageInfo.setTotal(commentPageInfo.getTotal());
        commentDTOPageInfo.setPages(commentPageInfo.getPages());
        return commentDTOPageInfo;
    }
}
