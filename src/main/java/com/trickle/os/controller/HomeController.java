package com.trickle.os.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.trickle.os.dao.HomeDao;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final HomeDao homeDao;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("time",homeDao.getTime());
		System.out.println(model.getAttribute("time"));
		return "/index";
	}
	
	@GetMapping("/os-home")
	public String home2(Model model) {
		return "/os-home";
	}
	
	@GetMapping(value = {"/admin/index", "/admin"})
	public String admin(Model model) {
		return "/admin/index";
	}
	
	@GetMapping(value = {"/shop/index", "/shop"})
	public String shop(Model model) {
		return "/shop/index";
	}	
	
	@GetMapping(value = {"/process/index", "/process"})
	public String process(Model model) {
		return "/process/index";
	}
}
