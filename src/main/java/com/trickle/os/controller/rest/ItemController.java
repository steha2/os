package com.trickle.os.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.PagingDao;
import com.trickle.os.util.ItemPaging;
import com.trickle.os.vo.ItemVo;

import lombok.RequiredArgsConstructor;
import paging.PagingDto;

@RestController
@RequiredArgsConstructor
public class ItemController {
	private final ItemDao itemDao;
	private final PagingDao pagingDao;
	
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
	
	@GetMapping("/getTotalCount")
	public Integer getTotalCount (String path, @RequestParam(required = false, defaultValue = "") String search) {
		return itemDao.getTotalRows(new ItemPaging(path, search));
	}
	
	@GetMapping(value="/getPagingItems") 
	public PagingDto getPagingItems(PagingDto pdto) {
		pdto.setTable("Items",ItemVo.class);
		pagingDao.addTotalRows(pdto);
		pdto.setData(pagingDao.getPagingItems(pdto));
		return pdto;
	}
	
	@GetMapping("/getCategoryItems")
	public List<ItemVo> getCategoryItems(Map<String,Object> params){
		int nowPage = getInt(params.get("nowPage"), 1);
		int rowCount = getInt(params.get("rowCount"), 10);
		ItemPaging itemPaging = new ItemPaging(params.get("path"), params);
		itemPaging.setPaging(nowPage, rowCount, 5, itemDao.getTotalRows(itemPaging));
		return itemDao.getPagingItems(itemPaging);
	}
	
	public int getInt(Object value, int defaultValue) {
		return value == null ? defaultValue : Integer.parseInt(value.toString());
	}
}
