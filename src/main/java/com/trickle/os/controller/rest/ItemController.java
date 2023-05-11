package com.trickle.os.controller.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.PagingDao;
import com.trickle.os.paging.PagingData;
import com.trickle.os.vo.ItemVo;

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
		pd.setTable("Items","ID,NAME,PATH,USER_ID,CONTENT,IMAGE_PATH,DISCOUNT,REG_DATE,SCORE,PRICE,NUM_SOLD,NUM_STOCK,NUM_VIEW");
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
}
