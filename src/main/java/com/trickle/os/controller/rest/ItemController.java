package com.trickle.os.controller.rest;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
	
	@GetMapping("/getTotalCount")
	public Integer getTotalCount (String path, @RequestParam(required = false, defaultValue = "") String search) {
		return itemDao.getTotalRows(new ItemPaging(path, search));
	}
	
	@RequestMapping(value="/getPagingItems", produces="text/plain;charset=UTF-8")
	public String getPagingItems(
			 @RequestParam(required = false, defaultValue = "1") int nowPage, 
			 @RequestParam(required = false, defaultValue = "10") int rowCount,
			 String path, 
			 @RequestParam(required = false, defaultValue = "") String search) throws JsonProcessingException{
		
		ItemPaging itemPaging = new ItemPaging(path, search);
		itemPaging.setPaging(nowPage, rowCount, 10, itemDao.getTotalRows(itemPaging));
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode data = mapper.createObjectNode();
		data.set("items", mapper.valueToTree(itemDao.getPagingItems(itemPaging)));
		data.set("paging", mapper.valueToTree(itemPaging.getPaging()));
		return mapper.writeValueAsString(data);
	}
	
	@GetMapping("/getSearchItems")
	public List<ItemVo> getSearchItems(Map<String,Object> params){
		int nowPage = getInt(params.get("nowPage"), 1);
		int rowCount = getInt(params.get("rowCount"), 10);
		ItemPaging itemPaging = new ItemPaging(params.get("path"), params);
		itemPaging.setPaging(nowPage, rowCount, 5, itemDao.getTotalRows(itemPaging));
		return itemDao.getPagingItems(itemPaging);
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
