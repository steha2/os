package com.trickle.os.util;

import java.util.*;
import java.util.stream.Collectors;

import com.trickle.os.vo.ItemVo;

import lombok.*;

@Getter
@Setter
@ToString
public class ItemPaging {
	private String path = "/";
	private Paging paging;
	private Map<String,Object> searchMap;
	
	public ItemPaging(String path) {
		this.path = path;
	}
	
	public ItemPaging(String path, String nameSearch) {
		searchMap = new HashMap<>();
		searchMap.put("name", nameSearch);
		this.path = path;
	}
	
	public ItemPaging(Object path, Map<String,Object> searchMap) {
		if(path != null && !path.toString().isBlank()) this.path = path.toString();
		this.searchMap = searchMap;
	}
	
	public ItemPaging(Map<String,Object> searchMap) {
		this.searchMap = searchMap;
	}

	public void setPaging(int nowPage, int rowCount, int maxPage, int totalRows) {
		paging = new Paging(nowPage, rowCount, maxPage, totalRows);
	}
	
	public String getColumns() {
		return Arrays.asList(ItemVo.class.getDeclaredFields())
		.stream().map(f->StrUtil.toSnakeCase(f.getName())).collect(Collectors.joining(","));
	}
}
