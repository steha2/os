package com.trickle.os.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trickle.os.controller.rest.ItemController;
import com.trickle.os.util.ItemPaging;

@SpringBootTest
class MenuDaoTest {

	@Autowired
	MenuDao md;
	@Autowired
	ItemDao id;
	@Autowired
	ItemController ic;
	@Test
	
	void test() {
//		md.getMenus().forEach(System.out::println);
		System.out.println(md.getRootByType("shop"));
		System.out.println(md.getPathName("/1/1/2"));
//		System.out.println(id.getTotalRows(null))
		ItemPaging itemPaging = new ItemPaging("/1/1/2", "");
		itemPaging.setPaging(1, 9, 5, id.getTotalRows(itemPaging));
		System.out.println(itemPaging);
//		System.out.println(itemPaging.getColumns());
		System.out.println(ic.getPagingItems(1, 9, "/1/1/2", ""));
	}
}
