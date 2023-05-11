package com.trickle.os.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trickle.os.controller.rest.ItemController;

//@SpringBootTest
class MenuDaoTest3 {
//	@Autowired
//	MenuDao md;
//	@Autowired
//	ItemDao id;
//	@Autowired
//	ItemController ic;
//	@Autowired
//	MenuController mc;
	public static void main(String[] args) {
//		MenuDao md = new MenuDao(new MyBatisConn().sqlSessionFactory());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		ItemController ic = new ItemController(new ItemDao(new MyBatisConn().sqlSessionFactory()));
		
//		md.getRoots().forEach(System.out::println);
		
		String s = "";
//		System.out.println(gson.toJson(gson.fromJson(id.getPagingItems("/1"),ObjectNode.class)));
		
	}
}
