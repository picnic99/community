package com.hyy.community.community.controller;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.CommentDTO;
import com.hyy.community.community.dto.CommentParamDTO;
import com.hyy.community.community.dto.ResultDTO;
import com.hyy.community.community.exception.CustomizeException;
import com.hyy.community.community.model.Comment;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("/doComment")
    @ResponseBody
    public Object post(Comment comment, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            comment.setCommentator(null);
        }else{
            comment.setCommentator(user.getId());
        }

        if(comment.getContent()==null||comment.getContent()==""){
            return new ResultDTO(-1,"请输入回复内容!",null);
        }
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        try {
            commentService.addComment(comment);
        }catch (CustomizeException e){
            return  new ResultDTO(0,e.getMessage(),null);
        }

        return new ResultDTO(1,"操作成功",null);
    }


    @PostMapping("/reply")
    public String reply(Model model,
                        CommentParamDTO commentParamDTO,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10")Integer pageSize,
                        HttpServletRequest request
                        ){


        if (commentParamDTO.getOrderBy()==null){
            commentParamDTO.setOrderBy(2);
        }

        User user = (User)request.getSession().getAttribute("user");
        PageInfo<CommentDTO> comments=null;
        if (user==null){
            comments = commentService.getComments(null,commentParamDTO,pageNum, pageSize);

        }else{
            comments = commentService.getComments(user.getId(),commentParamDTO,pageNum, pageSize);
        }

        HashMap<String, Integer> feedBack = new HashMap<>();
        feedBack.put("type",commentParamDTO.getType());
        feedBack.put("parentId",commentParamDTO.getParentId());
        feedBack.put("orderBy",commentParamDTO.getOrderBy());

        model.addAttribute("pageData",comments);
        model.addAttribute("feedBack",feedBack);

        if(commentParamDTO.getType()==1){
            return "comment1";
        }
        if (commentParamDTO.getType()==2){
            return "comment2";
        }

        return "/";

    }
}
