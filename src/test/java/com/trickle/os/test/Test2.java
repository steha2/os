package com.trickle.os.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.multipart.MultipartFile;

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
		System.out.println(ic.getTotalCount("/", ""));
		System.out.println(mc.getMenus());
	}
}
