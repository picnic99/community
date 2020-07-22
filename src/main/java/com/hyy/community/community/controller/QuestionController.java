package com.hyy.community.community.controller;

import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id")Integer id, Model model){
        QuestionDTO QuestionDTO = questionService.getById(id);
        model.addAttribute("questionData",QuestionDTO);
        return "question";
    }
}
