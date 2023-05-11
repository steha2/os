package com.trickle.os.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.trickle.os.dao.MenuDao;
import com.trickle.os.paging.FilterOption;
import com.trickle.os.util.Debug;
import com.trickle.os.util.StrUtil;
import com.trickle.os.vo.RootVo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final MenuDao menuDao;
	
	@GetMapping(value = {"/home","/"})
	public String home(Model model) {
		model.addAttribute("roots", menuDao.getRoots());
		return "/index";
	}
	
	@GetMapping("/admin/os-home")
	public String osHome(Model model) {
		model.addAttribute("roots", menuDao.getRoots());
		return "/admin/os-home";
	}
	
	@GetMapping("/main")
	public String main(Model model) {
		model.addAttribute("roots", menuDao.getRoots());
		return "/main";
	}
	
	@GetMapping(value = {"/admin/admin-index", "/admin"})
	public String admin(Model model) {
		return "/admin/admin-index";
	}
	
	@GetMapping("/admin/page/{rootId}")
	public String openAdminPage(@PathVariable long rootId, Model model) {
		model.addAttribute("root", menuDao.getRootById(rootId));
		return "/admin/admin-page";
	}

	@GetMapping(value = {"/process/index", "/process"})
	public String process(Model model) {
		return "/process/index";
	}
	
	@PostMapping("/updateStyle")
	public String updateStyle(RootVo root, Model model) {
		Debug.log(root.getStyle());
		root.setStyle(StrUtil.toJson(root.getStyle()));
		menuDao.updateStyle(root);
		return openAdminPage(root.getId(), model);
	}
	
	@GetMapping("/test")
	@ResponseBody
	public String test(@RequestParam(value="ar[]") List<FilterOption> ar) {
		System.out.println(ar);
		return "test";
	}
}