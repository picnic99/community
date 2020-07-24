package com.hyy.community.community.controller;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.service.QuestionService;
import com.hyy.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10")Integer pageSize, HttpServletRequest request, Model model){

        PageInfo<QuestionDTO> list = questionService.list(null,pageNum,pageSize);
        model.addAttribute("questions",list);
        return "index";
    }
}
