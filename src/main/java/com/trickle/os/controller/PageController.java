package com.trickle.os.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.MenuDao;
import com.trickle.os.vo.*;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PageController {

	private final MenuDao menuDao;
	private final ItemDao itemDao;
	
	@GetMapping("/page/content/{itemId}") 
	public String openContent(@PathVariable long itemId, Model model, HttpSession session) {
		ItemVo item = itemDao.getItemById(itemId);
		if(item == null) {
			return "/home";
		}
		RootVo root = menuDao.getRootByItemId(itemId);
		System.out.println(root);
		
		model.addAttribute("item",item);
		model.addAttribute("root", root);
		
		@SuppressWarnings("unchecked")
		List<Long> recentItems = (List<Long>) session.getAttribute("recentItems");
	    if (recentItems == null) {
	        recentItems = new ArrayList<>();
	    }
	    if(recentItems.contains(itemId)) recentItems.remove(itemId);
	    else itemDao.updateNumView(itemId);
	    
	    recentItems.add(0, itemId);
	    if (recentItems.size() > 30) {
	        recentItems = recentItems.subList(0, 30);
	    }
        session.setAttribute("recentItems", recentItems);
        System.out.println("/page/"+root.getType()+"/"+root.getId()+"/content-"+root.getId());
		return "/page/"+root.getType()+"/"+root.getId()+"/content-"+root.getId();
	}
	
	@GetMapping("/page/{rootId}")
	public String openPage(@PathVariable long rootId, Model model) {
		RootVo root = menuDao.getRootById(rootId);
		model.addAttribute("root", menuDao.getRootById(rootId));
		System.out.println("/page/"+root.getType()+"/index-"+rootId);
		return "/page/"+root.getType()+"/"+root.getId()+"/index-"+rootId;
	}
	
	@GetMapping("/page/reqItemId/{itemId}")
	public String openItemPage(@PathVariable long itemId, Model model) {
		RootVo root = menuDao.getRootByItemId(itemId); 
		model.addAttribute("reqItemId", itemId);
		return openPage(root.getId(), model);
	}
	

	@GetMapping("/login/writeForm")
	public String openWriteForm(@RequestParam String path, HttpSession session, Model model) {
		RootVo root = menuDao.getRootByPath(path);
		UserVo user = (UserVo) session.getAttribute("user");
		ItemVo item = new ItemVo();
		item.setPath(path);
		item.setUserId(user.getId());
		item.setUserName(user.getName());
		model.addAttribute("item", item);
		model.addAttribute("path", path);
		model.addAttribute("pathName", menuDao.getPathName(path));
		return "/page/"+root.getType()+"/"+root.getId()+"/content-write-"+root.getId();
	}

	@GetMapping("/login/updateForm")
	public String openUpdateForm(@RequestParam String path, @RequestParam long itemId, Model model) {
		RootVo root = menuDao.getRootByItemId(itemId);
		model.addAttribute("pathName", menuDao.getPathName(path));
		model.addAttribute("item", itemDao.getItemById(itemId));
		model.addAttribute("action", "update");
		model.addAttribute("path", path);
		return "/page/"+root.getType()+"/"+root.getId()+"/content-write-"+root.getId();
	}
}