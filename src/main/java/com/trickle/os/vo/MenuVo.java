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
	private long id, parentId;
	private String name, path, pathName;
	private List<MenuVo> childs = new ArrayList<>();
	private List<ItemVo> items;
	
	public void addMenu(MenuVo menu) {
		if(path != null) {
			menu.setPath(path+"/"+menu.id);
			menu.setPathName(pathName+"/"+menu.name);
		}
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
