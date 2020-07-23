package com.hyy.community.community.controller;

import com.hyy.community.community.dto.GithubUser;
import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.mapper.QuestionMapper;
import com.hyy.community.community.model.Question;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("question",question);
        return "publish";
    }

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/doPublish")
    @ResponseBody
    public Object doPublish(Integer id, Question question, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        Map<String,Object> result=new HashMap<String,Object>();
        if(user!=null){
            if(question.getTitle().equals("")){
                result.put("code",101);//标题为空
                return result;
            }
            if(question.getDescription().equals("")){
                result.put("code",102);//描述为空
                return result;
            }
            question.setCreator(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            if(id!=null){
                QuestionDTO questionDTO = questionService.getById(id);
                question.setId(id);
                question.setCommentCount(questionDTO.getQuestion().getCommentCount());
                question.setViewCount(questionDTO.getQuestion().getViewCount());
                question.setLikeCount(questionDTO.getQuestion().getLikeCount());
                questionService.update(question);
            }else{
                questionService.insert(question);

            }
            result.put("code",1);
        }else{
            result.put("code",0);
        }
        return result;

    }
}
