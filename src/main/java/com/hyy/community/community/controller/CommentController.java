package com.hyy.community.community.controller;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.CommentDTO;
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
    public String reply(Model model,Integer type,Integer parentId,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10")Integer pageSize,
                        @RequestParam(defaultValue = "2")Integer orderBy
                        ){
        PageInfo<CommentDTO> comments = commentService.getComments(type, parentId, pageNum, pageSize,orderBy);
        model.addAttribute("pageData",comments);
        HashMap<String, Integer> feedBack = new HashMap<>();
        feedBack.put("type",type);
        feedBack.put("parentId",parentId);
        feedBack.put("orderBy",orderBy);
        model.addAttribute("pageData",comments);
        model.addAttribute("feedBack",feedBack);
        return "comment";
    }
}
