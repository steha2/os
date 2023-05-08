package com.trickle.os.test;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.MenuDao;
import com.trickle.os.vo.ItemVo;
import com.trickle.os.vo.MenuVo;
import com.trickle.os.vo.RootVo;
@SpringBootTest
class CRTEST {

	@Autowired
	MenuDao md;
	@Autowired
	ItemDao id;
	
	//이미지를 검색해서 DB에 넣는다.
	@Test
	void test() {
		
		MenuVo d2 = md.getDepth2(    44    ); //검색어 
		
		
		String add = "뉴스 기사";

		
		MenuVo d1 = md.getDepth1(d2.getParentId());
		RootVo root = md.getRootById(d1.getParentId());
		
//		String pathName = "/"+root.getName()+"/"+d1.getName()+"/"+d2.getName();
		String path= "/"+root.getId()+"/"+d1.getId()+"/"+d2.getId();
		String search = d2.getName();
		
		int count = 5;
		
		for(int c=0; c<count; c++) {
			Crawling cr = new Crawling();
			cr.parse(search+add+(c==0?"":(""+c)));
			List<String> ts = cr.getTitles();
			List<String> ss = cr.getSrcs();
			
			File saveFile = new File("D:/resources/images");
			
			for(String f : path.split("/")) {
				File f1 = new File(saveFile,f);
				saveFile = f1;
				if(!f1.exists()) f1.mkdir();
			}
			
			for (int i = 0; i < ts.size(); i++) {
				String t = Crawling.removeSpChar(ts.get(i));
				String s = ss.get(i);
				
				if(id.getItemByName(t) != null || !s.startsWith("http")) continue;
	
				System.out.println(s);
				System.out.println(t);
				String s1 = s.split("\\?")[1];
				String s2 = s1.split("&")[0];
				String ext = s2.substring(s2.lastIndexOf("."));
	
				if(ext.endsWith("jpg") || ext.endsWith("png") || ext.endsWith("jpeg") || ext.endsWith("gif")) {
					ItemVo item = new ItemVo();
					item.setContent(t);
					item.setName(t);
					item.setPrice(getRandomPrice(5000, 200000, 100));
					item.setNumStock(100);
					item.setScore(""+getRandomPrice(1,5,1));
					item.setImagePath(ext);
					item.setPath(path);
					item.setUserId("Admin");
					id.addItem(item);
					System.out.println("ITEMID:" + item.getId());
					Crawling.saveImg(s, new File(saveFile, item.getId() + ext));
				}
			}
			cr.getDriver().close();
			cr.getDriver().quit();
		}
	}
	
    public static int getRandomPrice(int min, int max, int q) {
        Random rand = new Random();
        int randomPrice = rand.nextInt((max - min) / q + 1) * q + min;
        return randomPrice;
    }
}
