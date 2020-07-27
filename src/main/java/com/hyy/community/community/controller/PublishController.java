package com.hyy.community.community.controller;

import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.dto.ResultDTO;
import com.hyy.community.community.model.Question;
import com.hyy.community.community.model.Tag;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.QuestionService;
import com.hyy.community.community.service.TagQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private TagQuestionService tagQuestionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        QuestionDTO question = questionService.getById(null,id);
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
        ResultDTO result = null;
        if(user!=null){
            if(question.getTitle().equals("")){
                result= new ResultDTO(0,"标题不能为空",null);
                return result;
            }
            if(question.getDescription().equals("")){
                result= new ResultDTO(0,"描述不能为空",null);
                return result;
            }

            Long time=System.currentTimeMillis();
            question.setGmtModified(time);
            question.setGmtCreate(time);
            question.setCreator(user.getId());
            if(id!=null){
                question.setId(id);
                questionService.edit(question);
            }else{
                String tags = question.getTag();
                String[] split = tags.split(",");

                List<Tag> tagList=new ArrayList<>();
                for (String s : split) {
                    Tag tag = new Tag();
                    tag.setGmtCreate(time);
                    tag.setCreator(user.getId().longValue());
                    tag.setName(s);
                    tagList.add(tag);
                }
                Integer questionid = (Integer) questionService.insert(question);
                tagQuestionService.addTagQuestion(tagList,questionid.longValue());

            }
            result= new ResultDTO(1,"success",null);
        }else{
            result= new ResultDTO(0,"请先登录后重试!",null);

        }
        return result;

    }
}
