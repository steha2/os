package com.trickle.os.vo;

import java.util.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RootVo implements Comparable<RootVo>{
	private long id;
	private String name, type, style;
	private List<MenuVo> childs;
	private List<ItemVo> items;
	
	public void addMenu(MenuVo menu) {
		if(childs == null) childs = new ArrayList<>();
		menu.setPath(getPath()+"/"+menu.getId());
		childs.add(menu);
	}
	
	public void addItem(ItemVo item) {
		if(items == null) items= new ArrayList<>();
		items.add(item);
	}

	@Override
	public int compareTo(RootVo o) {
		return (int) (id - o.id);
	}
	
	public String getPath() {
		return "/"+id;
	}
}

