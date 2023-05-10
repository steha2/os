package com.trickle.os.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.trickle.os.dao.UserDao;
import com.trickle.os.util.Debug;
import com.trickle.os.vo.UserVo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserDao userDao;
	
    @GetMapping("/user/auth")
    @ResponseBody
    public UserVo auth(UserVo user, HttpSession session) {
    	user = userDao.findByNamePw(user);
    	session.setAttribute("user", user);
    	return user;
    }
    
    @GetMapping("/user/isLogin")
    @ResponseBody
    public String isLogin(HttpSession session) {
    	return session.getAttribute("user") == null ? "false" : "true";
    }
    
    @PostMapping("/user/signUp")
    public String signUp(UserVo user, @RequestParam(required = false, defaultValue = "") String isClose,  Model model) {
    	System.out.println(user);
    	if(userDao.findByName(user) == null && userDao.addUser(user) == 1) {
    		model.addAttribute("success", "회원 가입 성공");
    		if(isClose.equals("true")) model.addAttribute("isClose", true);
    	} else {
    		model.addAttribute("error", "중복된 이름");
    	}
    	return "/user/sign-up-form";
    }
    
    @GetMapping("/user/logout")
    public String logout(@RequestParam(required = false, defaultValue = "") String returnUrl, HttpSession session) {
    	session.invalidate();
    	return returnUrl.isEmpty() ? "redirect:/home" : returnUrl; 
    }
    
    @GetMapping("/user/loginForm")
    public String loginForm() {
    	return "/user/login-form";
    }
    
    @GetMapping("/user/signUpForm")
    public String signUpForm(@RequestParam(required = false, defaultValue = "") String isClose, Model model) {
		if(isClose.equals("true")) model.addAttribute("isClose", true);
    	return "/user/sign-up-form";
    }
}