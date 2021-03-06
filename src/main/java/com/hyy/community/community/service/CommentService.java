package com.hyy.community.community.service;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.CommentDTO;
import com.hyy.community.community.dto.CommentParamDTO;
import com.hyy.community.community.model.Comment;
import com.hyy.community.community.model.Notice;

public interface CommentService {

    void addComment(Comment comment);

    PageInfo<CommentDTO> getComments(Integer currentUser,CommentParamDTO commentParamDTO, Integer pageNum, Integer pageSize);

}
