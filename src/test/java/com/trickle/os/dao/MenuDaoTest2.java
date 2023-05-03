package com.trickle.os.dao;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trickle.os.controller.rest.ItemController;
import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.MenuDao;
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
	void test() throws JsonProcessingException {
		Map<String,Object> params = new HashMap<>();
		ItemPaging ip = new ItemPaging("/","노");
		System.out.println(id.getTotalRows(ip));
		ItemPaging itemPaging = new ItemPaging("/1", "");
		int i;
		itemPaging.setPaging(3, 3, 5, i = id.getTotalRows(itemPaging));
		System.out.println(i);
		System.out.println(itemPaging.getPaging());
		id.getPagingItems(itemPaging).forEach(System.out::println);//		il.forEach(System.out::println);
		System.out.println();
		params.put("name", "1");
		params.put("id", "114");
		List<ItemVo> il2 = ic.getSearchItems(params);
		il2.forEach(System.out::println);
	}
}
