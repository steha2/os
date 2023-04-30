package com.trickle.os.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.trickle.os.vo.ItemVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemPaging {
	private String search, path;
	private Paging paging;
	
	public ItemPaging(String path, String search) {
		this.path = path;
		this.search = search;
	}	
	
	public ItemPaging(String path) {
		this.path = path;
	}
	
	public void setPaging(int nowPage, int rowCount, int maxPage, int totalRows) {
		paging = new Paging(nowPage, rowCount, maxPage, totalRows);
	}
	
	public String getColumns() {
		return Arrays.asList(ItemVo.class.getDeclaredFields())
		.stream().map(f->StrUtil.toSnakeCase(f.getName())).collect(Collectors.joining(","));
	}
}
