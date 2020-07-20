package com.hyy.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @GetMapping(value = "/{type}/{id}")
    public String hello(Model model, @PathVariable(value = "type")Integer type, @PathVariable(value = "id")Integer id){
        model.addAttribute("type",type);
        model.addAttribute("id",id);

        return "forward:/";
    }
    @RequestMapping("/")
    public String index(){
        return "hello";
    }
}
