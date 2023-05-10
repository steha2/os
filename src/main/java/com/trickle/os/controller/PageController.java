package com.trickle.os.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.trickle.os.dao.*;
import com.trickle.os.paging.FilterOption;
import com.trickle.os.util.Debug;
import com.trickle.os.util.StrUtil;
import com.trickle.os.vo.*;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {

	private final MenuDao menuDao;
	private final ItemDao itemDao;
	
	//id : Item Id
	@GetMapping("/page/content/{rootId}/{id}") 
	public String openContent(@PathVariable long rootId, @PathVariable long id, Model model) {
		model.addAttribute("item", itemDao.getCommentsItem(id));
		itemDao.updateNumView(id);
		RootVo root = menuDao.getRootById(rootId);
		model.addAttribute("root", root);
		return "/page/"+root.getType()+"/"+root.getId()+"/content-"+rootId;
	}
	
	@GetMapping("/page/{rootId}")
	public String openPage(@PathVariable long rootId, Model model) {
		RootVo root = menuDao.getRootById(rootId);
		model.addAttribute("root", menuDao.getRootById(rootId));
		System.out.println("/page/"+root.getType()+"/index-"+rootId);
		return "/page/"+root.getType()+"/"+root.getId()+"/index-"+rootId;
	}
}