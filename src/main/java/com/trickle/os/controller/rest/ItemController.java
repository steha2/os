package com.trickle.os.controller.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trickle.os.dao.ItemDao;
import com.trickle.os.util.ItemPaging;
import com.trickle.os.vo.ItemVo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemController {
	private final ItemDao itemDao;
	
	@PostMapping("/addItem")
	public String addItem(ItemVo item) {
		if(itemDao.addItem(item) == 1) return "상품 등록 성공";
		return "실패";
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
		return itemDao.deleteItem(id) == 1 ? "삭제 성공" : "실패";
	}
	
	@GetMapping("/getPagingItems")
	public List<ItemVo> getPagingItems(
			 @RequestParam(required = false, defaultValue = "1") int nowPage, 
			 @RequestParam(required = false, defaultValue = "10") int rowCount,
			 String path, 
			 @RequestParam(required = false, defaultValue = "") String search){
		
		ItemPaging itemPaging = new ItemPaging(path, search);
		itemPaging.setPaging(nowPage, rowCount, 5, itemDao.getTotalRows(itemPaging));
		return itemDao.getPagingItems(itemPaging);
	}
}
