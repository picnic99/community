package com.hyy.community.community.service.impl;

import com.hyy.community.community.mapper.CommentMapper;
import com.hyy.community.community.model.Comment;
import com.hyy.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public int insert(Comment record) {
        return commentMapper.insert(record);
    }
}
