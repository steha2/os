package com.trickle.os.test;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.MenuDao;
import com.trickle.os.vo.ItemVo;
import com.trickle.os.vo.MenuVo;
@SpringBootTest
class CRTEST {

	@Autowired
	MenuDao md;
	@Autowired
	ItemDao id;
	
	
	@Test
	void test() {
		MenuVo d2 = md.getDepth2(2); 
		MenuVo d1 = md.getDepth1(d2.getParentId());
		MenuVo root = md.getRootById(d1.getParentId());
		
		String pathName = "/"+root.getName()+"/"+d1.getName()+"/"+d2.getName();
		String path= "/"+root.getId()+"/"+d1.getId()+"/"+d2.getId();
		String search = d2.getName();
		
		System.out.println(pathName);
		Crawling cr = new Crawling();
		cr.parse(search);
		List<String> ts = cr.getTitles();
		List<String> ss = cr.getSrcs();
		
		File saveFile = new File("D:/resources/images");
		
		
		for(String f : pathName.split("/")) {
			File f1 = new File(saveFile,f);
			saveFile = f1;
			if(!f1.exists()) f1.mkdir();
		}
		
		for (int i = 0; i < ts.size(); i++) {
			String t = Crawling.removeSpChar(ts.get(i));
			String s = ss.get(i);
			String s1 = s.split("\\?")[1];
			String s2 = s1.split("&")[0];
			String ext = s2.substring(s2.lastIndexOf("."));

			if(id.getItemByName(t) != null || !s.startsWith("http") ||
			   ext.contains("jpg") || ext.contains("png") || ext.contains("jpeg") || ext.contains("gif")) {
			   continue;
			}
			ItemVo item = new ItemVo();
			item.setContent(t);
			item.setName(t);
			item.setPrice(10000);
			item.setNumStock(100);
			item.setImagePath(ext);
			item.setPath(path);
			item.setUserId("Admin");
			id.addItem(item);
			System.out.println("ITEMID:" + item.getId());
			Crawling.saveImg(s, new File(saveFile, item.getId() + ext));
		}
		cr.getDriver().quit();
		cr.getDriver().close();
	}
}
