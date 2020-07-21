package com.hyy.community.community.controller;

import com.hyy.community.community.dto.GithubUser;
import com.hyy.community.community.mapper.QuestionMapper;
import com.hyy.community.community.model.Question;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/doPublish")
    @ResponseBody
    public Object doPublish(Question question, HttpServletRequest request){
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
            questionService.insert(question);
            result.put("code",1);
        }else{
            result.put("code",0);
        }
        return result;

    }
}
