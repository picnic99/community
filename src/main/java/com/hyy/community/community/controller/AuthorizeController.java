package com.hyy.community.community.controller;

import com.hyy.community.community.dto.AccessTokenDTO;
import com.hyy.community.community.dto.GithubUser;
import com.hyy.community.community.model.User;
import com.hyy.community.community.provider.GithubProvider;
import com.hyy.community.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String secret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response)
    {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(secret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser!=null){
            String token = UUID.randomUUID().toString();
            User userDB = userService.findByAccountId(String.valueOf(githubUser.getId()));
            if (userDB==null){
                User user = new User();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setBio(githubUser.getBio());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                userService.insert(user);
                request.getSession().setAttribute("user",user);
            }else{
                token=userDB.getToken();
                userDB.setName(githubUser.getName());
                userDB.setAvatarUrl(githubUser.getAvatarUrl());
                userDB.setBio(githubUser.getBio());
                userService.update(userDB);
                request.getSession().setAttribute("user",userDB);
            }
            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else{
            return "redirect:/";

        }

    }
    @GetMapping("/exit")
    public String exit(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/doLogin")
    public String doLogin(String account,String password,
                          HttpServletRequest request,
                          HttpServletResponse response){

        User user = userService.getByAccountAndPassword(account, password);
        if(user!=null){
            String token = user.getToken();
            request.getSession().setAttribute("user",user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }
        return "login";
    }
}
