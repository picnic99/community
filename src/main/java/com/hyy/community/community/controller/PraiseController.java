package com.hyy.community.community.controller;

import com.hyy.community.community.dto.ResultDTO;
import com.hyy.community.community.exception.CustiomizeErrorCode;
import com.hyy.community.community.model.Praise;
import com.hyy.community.community.model.User;
import com.hyy.community.community.service.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PraiseController {
    @Autowired
    private PraiseService likeService;

    @PostMapping("/doPraise")
    @ResponseBody
    public Object doPraise(Praise praise, HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return new ResultDTO(0, CustiomizeErrorCode.USER_NOT_LOGIN.getMessage(),null);
        }
        praise.setUserId(user.getId().longValue());
        if(praise.getStatus()==1){
            //点赞
            likeService.addLike(praise);

            return new ResultDTO(1,"success",null);
        }else{
            //取消点赞
            likeService.removeLike(praise);
            return new ResultDTO(1,"success",null);

        }
    }
}
