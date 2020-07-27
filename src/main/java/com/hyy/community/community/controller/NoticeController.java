package com.hyy.community.community.controller;

import com.github.pagehelper.PageInfo;
import com.hyy.community.community.dto.NoticeDTO;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping("/getNotice")
    public String notice(HttpServletRequest request, Model model){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        PageInfo<NoticeDTO> notice = noticeService.getNotice(user.getId().longValue(), 1, 10);
        model.addAttribute("notices",notice);
        System.out.println(notice);
        return "noticeList";

    }
}
