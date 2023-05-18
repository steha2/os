package com.trickle.os.test;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trickle.os.controller.rest.ItemController;
import com.trickle.os.controller.rest.MenuController;
import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.PagingDao;
import com.trickle.os.paging.FilterCondition;
import com.trickle.os.paging.PagingData;
import com.trickle.os.vo.ItemVo;

@SpringBootTest
public class Test2 {
	
	
	@Autowired
	MenuController mc;
	@Autowired
	ItemController ic;
	@Autowired
	ItemDao id;
	@Autowired
	PagingDao pd2;
	
	@Test
	public void a() throws IOException {
//		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//    	Resource res = resolver.getResource("file:src/main/resources");
//    	System.out.println(res.getFile().getAbsolutePath());
		
//		mc.addMenu("", "newMenu1, newType, S1");
//		System.out.println(ic.getTotalCount("/", ""));
//		System.out.println(mc.getMenus());
		PagingData pd = new PagingData();
		pd.addOption("path", FilterCondition.STARTS_WITH, "/1");
		pd.setNowPage(1);
		pd.setMaxPage(10);
		pd.setRowCount(20);
		long t1 = System.currentTimeMillis();
		for(int i=0; i<10; i++) ic.getPagingItems(pd);
		System.out.println(pd.getData());
		System.out.println(System.currentTimeMillis() - t1);
	}
}
