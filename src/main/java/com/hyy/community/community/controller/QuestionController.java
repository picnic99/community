package com.hyy.community.community.controller;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "7")Integer pageSize, Model model){
        PageInfo<QuestionDTO> list = questionService.list(null,pageNum,pageSize);
        model.addAttribute("questions",list);
        QuestionDTO QuestionDTO = questionService.getById(id);
        model.addAttribute("questionData",QuestionDTO);
        questionService.incView(id);
        return "question";
    }
}
