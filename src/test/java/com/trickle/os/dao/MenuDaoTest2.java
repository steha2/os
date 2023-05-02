package com.trickle.os.dao;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trickle.os.controller.rest.ItemController;
import com.trickle.os.util.ItemPaging;
import com.trickle.os.vo.ItemVo;

@SpringBootTest
class MenuDaoTest2 {

	@Autowired
	MenuDao md;
	@Autowired
	ItemDao id;
	@Autowired
	ItemController ic;
	
	@Test
	void test() {
		Map<String,Object> params = new HashMap<>();
		ItemPaging ip = new ItemPaging("/","노");
		System.out.println(id.getTotalRows(ip));
		List<ItemVo> il = ic.getPagingItems(1, 10, "/", "노");
		il.forEach(System.out::println);
		System.out.println();
		params.put("name", "1");
		params.put("id", "114");
		List<ItemVo> il2 = ic.getSearchItems(params);
		il2.forEach(System.out::println);
	}
}
