package com.trickle.os.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.trickle.os.dao.HomeDao;
import com.trickle.os.dao.MenuDao;
import com.trickle.os.vo.RootVo;

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
	
	@GetMapping("/admin/page/{rootId}")
	public String openAdminPage(@PathVariable long rootId, Model model) {
		model.addAttribute("root", menuDao.getRootById(rootId));
		return "/admin/page-admin";
	}
	
	@GetMapping("/page/{rootId}")
	public String openPage(@PathVariable long rootId, Model model) {
		RootVo root = menuDao.getRootById(rootId);
		model.addAttribute("root", menuDao.getRootById(rootId));
		return "/page/"+root.getType()+"/index-"+rootId;
	}
	
	@GetMapping(value = {"/process/index", "/process"})
	public String process(Model model) {
		return "/process/index";
	}
	
	@PostMapping("/updateStyle")
	public String updateStyle(RootVo root, Model model) {
		menuDao.updateStyle(root);
		return openAdminPage(root.getId(), model);
	}
}
