package com.hyy.community.community.service;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.CommentDTO;
import com.hyy.community.community.dto.CommentParamDTO;
import com.hyy.community.community.model.Comment;

public interface CommentService {

    void addComment(Comment comment);

    PageInfo<CommentDTO> getComments(CommentParamDTO commentParamDTO, Integer pageNum, Integer pageSize);

}
