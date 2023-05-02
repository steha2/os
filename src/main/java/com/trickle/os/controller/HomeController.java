package com.trickle.os.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.trickle.os.dao.HomeDao;
import com.trickle.os.dao.MenuDao;
import com.trickle.os.vo.MenuVo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final MenuDao menuDao;
	private final HomeDao homeDao;
	
	@GetMapping("/")
	public String home2(Model model) {
		model.addAttribute("time",homeDao.getTime());
		System.out.println(model.getAttribute("time"));
		return "/index";
	}
	
	@GetMapping("/os-home")
	public String home(Model model) {
		model.addAttribute("roots", menuDao.getRoots());
		return "/os-home";
	}
	
	@GetMapping(value = {"/admin/index", "/admin"})
	public String admin(Model model) {
		return "/admin/index";
	}
	
	@GetMapping("/page/{rootId}")
	public String openPage(@PathVariable int rootId, Model model) {
		MenuVo root = menuDao.getRootById(rootId);
		model.addAttribute("root", menuDao.getRootById(rootId));
		return "/page/"+root.getType()+"/index-"+rootId;
	}
	
	@GetMapping(value = {"/process/index", "/process"})
	public String process(Model model) {
		return "/process/index";
	}
}
