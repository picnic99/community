package com.hyy.community.community.controller;

import com.hyy.community.community.dto.ResultDTO;
import com.hyy.community.community.model.Comment;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("/comment")
    @ResponseBody
    public Object post(Comment comment, HttpServletRequest request){
        System.out.println(comment);
        User user = (User)request.getAttribute("user");
        if (user==null){
            return new ResultDTO(0,"请先登录!",null);
        }
        comment.setCommentator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        commentService.insert(comment);
        return new ResultDTO(1,"操作成功",null);

    }
}
