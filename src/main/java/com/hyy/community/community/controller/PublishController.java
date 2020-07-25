package com.hyy.community.community.controller;

import com.hyy.community.community.dto.QuestionDTO;
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
            Long time=System.currentTimeMillis();
            question.setCreator(user.getId());
            question.setGmtCreate(time);
            question.setGmtModified(time);
            if(id!=null){
                QuestionDTO questionDTO = questionService.getById(id);
                question.setId(id);
                question.setCommentCount(questionDTO.getQuestion().getCommentCount());
                question.setViewCount(questionDTO.getQuestion().getViewCount());
                question.setLikeCount(questionDTO.getQuestion().getLikeCount());
                questionService.update(question);
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
            result.put("code",1);
        }else{
            result.put("code",0);
        }
        return result;

    }
}
