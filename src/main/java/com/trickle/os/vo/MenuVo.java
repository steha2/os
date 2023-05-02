package com.trickle.os.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuVo implements Comparable<MenuVo>  {
	private String name, type, style;
	private long id, parentId;
	private List<MenuVo> childs;
	private List<ItemVo> items;
	
	public void addMenu(MenuVo menu) {
		if(childs == null) childs = new ArrayList<>();
		childs.add(menu);
	}
	
	public void addItem(ItemVo item) {
		if(items == null) items= new ArrayList<>();
		items.add(item);
	}

	@Override
	public int compareTo(MenuVo o) {
		return (int) (id - o.id);
	}
}
