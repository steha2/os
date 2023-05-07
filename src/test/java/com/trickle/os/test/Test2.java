package com.trickle.os.test;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trickle.os.controller.rest.ItemController;
import com.trickle.os.controller.rest.MenuController;

@SpringBootTest
public class Test2 {
	
	
	@Autowired
	MenuController mc;
	@Autowired
	ItemController ic;
	
	@Test
	public void a() throws IOException {
//		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//    	Resource res = resolver.getResource("file:src/main/resources");
//    	System.out.println(res.getFile().getAbsolutePath());
		
//		mc.addMenu("", "newMenu1, newType, S1");
//		System.out.println(ic.getTotalCount("/", ""));
		System.out.println(mc.getMenus());
	}
}
