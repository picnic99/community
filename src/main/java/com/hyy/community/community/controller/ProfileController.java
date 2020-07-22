package com.hyy.community.community.controller;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.QuestionDTO;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "7")Integer pageSize,
                          @PathVariable(name="action") String action,
                          Model model, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        if("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的话题");
            PageInfo<QuestionDTO> list = questionService.list(user.getId(), pageNum, pageSize);
            model.addAttribute("dataList",list);
        }
        if("reply".equals(action)){
            model.addAttribute("section","reply");
            model.addAttribute("sectionName","新的回复");
            PageInfo<QuestionDTO> list = questionService.list(user.getId(), pageNum, pageSize);
            model.addAttribute("dataList",list);
        }
        if("notice".equals(action)){
            model.addAttribute("section","notice");
            model.addAttribute("sectionName","通知");
            PageInfo<QuestionDTO> list = questionService.list(user.getId(), pageNum, pageSize);
            model.addAttribute("dataList",list);
        }
        return "profile";
    }
}
