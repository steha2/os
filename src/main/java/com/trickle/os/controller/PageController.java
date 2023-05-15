package com.trickle.os.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.MenuDao;
import com.trickle.os.vo.ItemVo;
import com.trickle.os.vo.RootVo;

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
		
		model.addAttribute("item",item);
		itemDao.updateNumView(itemId);
		model.addAttribute("root", root);
		
		@SuppressWarnings("unchecked")
		List<Long> recentItems = (List<Long>) session.getAttribute("recentItems");
	    if (recentItems == null) {
	        recentItems = new ArrayList<>();
	    }
	    recentItems.remove(itemId);
	    recentItems.add(0, itemId);
	    if (recentItems.size() > 4) {
	        recentItems = recentItems.subList(0, 4);
	    }
        session.setAttribute("recentItems", recentItems);
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
}