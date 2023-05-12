package com.trickle.os.controller.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.PagingDao;
import com.trickle.os.paging.PagingData;
import com.trickle.os.vo.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemController {
	private final ItemDao itemDao;
	private final PagingDao pagingDao;
	
	@PostMapping("/addItem")
	public String addItem(ItemVo item) {
		if(itemDao.addItem(item) == 1) return "Success";
		return "Fail";
	}
	
	@GetMapping("/getItems")
	public List<ItemVo> getItems(String path) {
		return itemDao.getItems(path);
	}
	
	@PostMapping("/updateItem")
	public ItemVo updateItem(ItemVo item) {
		return itemDao.updateItem(item) == 1 ? item : null;
	}
	
	@GetMapping("/deleteItem")
	public String updateItem(long id) {
		return itemDao.deleteItem(id) == 1 ? "Success" : "Fail";
	}
	
	@GetMapping(value="/getPagingItems") 
	public PagingData getPagingItems(PagingData pd) {
		System.out.println(pd);
		pd.setTable("ITEMS_WITH_SCORE",ItemVo.class);
		pagingDao.addTotalRows(pd);
		pd.setData(pagingDao.getPagingItems(pd));
		return pd;
	}
	
	@GetMapping("/getRecentItems")
	public List<ItemVo> getRecentItems(HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Long> itemIds = (List<Long>) session.getAttribute("recentItems");
		System.out.println(itemIds);
		if(itemIds == null) return null;
		else return itemDao.getRecentItems(itemIds);
	}
	
	@PostMapping("/getComments")
	public PagingData getComments(PagingData cpd) {
		System.out.println(cpd);
		cpd.setTable("COMMENTS_WITH_USER_NAME",CommentVo.class);
		pagingDao.addTotalRows(cpd);
		cpd.setData(pagingDao.getPagingComments(cpd));
		return cpd;
	}
	
	@PostMapping("/login/addComment")
	public String addComment(CommentVo comment, @SessionAttribute("user") UserVo user) {
		comment.setUserId(user.getId());
		return itemDao.addComment(comment) == 1 ? "Success" : null;
	}
}
