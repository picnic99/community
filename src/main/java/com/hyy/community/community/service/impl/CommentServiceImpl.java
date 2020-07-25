package com.hyy.community.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.CommentDTO;
import com.hyy.community.community.dto.CommentParamDTO;
import com.hyy.community.community.enums.CommentTypeEnum;
import com.hyy.community.community.enums.OrderByEnum;
import com.hyy.community.community.exception.CustiomizeErrorCode;
import com.hyy.community.community.exception.CustomizeException;
import com.hyy.community.community.mapper.CommentMapper;
import com.hyy.community.community.mapper.QuestionMapper;
import com.hyy.community.community.mapper.UserMapper;
import com.hyy.community.community.model.Comment;
import com.hyy.community.community.model.User;
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

    @Override
    @Transactional
    public void addComment(Comment comment) {
        Integer type = comment.getType();
        Object commentDB=null;
        if(comment.getCommentator()==null){
            throw new CustomizeException(CustiomizeErrorCode.USER_NOT_LOGIN);
        }

        if(type.equals(CommentTypeEnum.QUESTION.getType())){
            commentDB = questionMapper.selectByPrimaryKey(comment.getParentId().intValue());
            if(commentDB==null){
                throw new CustomizeException(CustiomizeErrorCode.QUESTION_NOT_FOUND);
            }

        }
        if(type.equals(CommentTypeEnum.COMMENT.getType())){
            commentDB = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(commentDB==null){
                throw new CustomizeException(CustiomizeErrorCode.COMMENT_NOT_FOUND);
            }
        }

        commentMapper.insert(comment);
        if(type.equals(CommentTypeEnum.QUESTION.getType())){
            questionMapper.incComment(comment.getParentId().intValue());
        }

    }

    @Override
    public PageInfo<CommentDTO> getComments(CommentParamDTO commentParamDTO, Integer pageNum, Integer pageSize) {
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
