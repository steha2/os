package com.trickle.os.create;

import java.io.File;
import java.util.*;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trickle.os.controller.rest.ItemController;
import com.trickle.os.controller.rest.MenuController;
import com.trickle.os.dao.*;
import com.trickle.os.test.cr.MyBatisConn;
import com.trickle.os.test.cr.NaverCrawling;
import com.trickle.os.vo.*;

@SpringBootTest
public class CreateMenus {
//	private static SqlSessionFactory factory = new MyBatisConn("os2").sqlSessionFactory();
//	public static ItemDao ID = new ItemDao(factory);
//	public static MenuDao MD = new MenuDao(factory, ID);
//	public static MenuController MC = new MenuController(MD);
//	public static PagingDao PD = new PagingDao(factory);
//	public static ItemController IC = new ItemController(ID, PD);
	
	@Autowired ItemDao ID;
	@Autowired MenuDao MD ;
	@Autowired MenuController MC;
	@Autowired PagingDao PD;
	@Autowired ItemController IC;
	
	private String S1 = "";
	private String S2 = "";
	
	@Test
	void t() {
		Scanner s = new Scanner( System.in);
		String str = s.nextLine();
		if(!str.equals("y")) return;
		
		CreateMenus cm = new CreateMenus();
//		cm.createRoot("B-Market2", "shop", "자전거", {"산악용2"});
//		cm.createRoot("B-Market2", "shop", "자전거", "산악용2");
		
		cm.createRoot("K-Music2", "score", "클래식", null);
//		cm.createRoot("B-Market2", "board", "자전거", new String[] { "산악용2" } );
	}


	public void createRoot(String rootName, String type, String d1Name, String[] d2Names) {
		RootVo root = new RootVo();
		root.setName(rootName);
		root.setType(type);
//		MC.addMenu("", rootName);
		MD.addRoot(root);
		
		long f = MC.createIndexPage(root);

		root.setStyle(MD.getRootById(f).getStyle());

		MD.updateStyle(root);

		System.out.println(root.getId());
		long rootId = root.getId();

		MenuVo d1 = new MenuVo();
		d1.setName(d1Name);
		d1.setParentId(rootId);

		MD.addDepth1(d1);
		
		if(d2Names != null) {
			for(String d2Name : d2Names) {
				MenuVo d2 = new MenuVo();
				d2.setName(d2Name);
				d2.setParentId(d1.getId());
				
				MD.addDepth2(d2);
				
				cr2(d2.getId());
			}
		}else {
			cr1(d1.getId());
		}
	}

	void cr1(long d1id) {

		int count = 4;
		int p1 = 5000, p2 = 200000;
		MenuVo d1 = MD.getDepth1(d1id);

		String search = S1 + d1.getName() + S2;
		

		RootVo root = MD.getRootById(d1.getParentId());

//		String pathName = "/"+root.getName()+"/"+d1.getName()+"/"+d2.getName();
		String path = "/" + root.getId() + "/" + d1.getId();
//		String search = d2.getName();

		for (int c = 0; c < count; c++) {
			NaverCrawling cr = new NaverCrawling();
			cr.parse(search + (c == 0 ? "" : ("" + c)));
			List<String> ts = cr.getTitles();
			List<String> ss = cr.getSrcs();

			File saveFile = new File("D:/resources/images");

			for (String f : path.split("/")) {
				File f1 = new File(saveFile, f);
				saveFile = f1;
				if (!f1.exists())
					f1.mkdir();
			}

			for (int i = 0; i < ts.size(); i++) {
				String t = NaverCrawling.removeSpChar(ts.get(i));
				String s = ss.get(i);

				if (ID.getItemByName(t) != null || !s.startsWith("http"))
					continue;

				System.out.println(s);
				System.out.println(t);
				String s1 = s.split("\\?")[1];
				String s2 = s1.split("&")[0];
				String ext = s2.substring(s2.lastIndexOf("."));

				if (ext.endsWith("jpg") || ext.endsWith("png") || ext.endsWith("jpeg") || ext.endsWith("gif")) {
					ItemVo item = new ItemVo();
					item.setContent(t);
					item.setName(t);
					item.setPrice(getRandomPrice(p1, p2, 100));
					item.setNumStock(100);
					item.setScore("" + getRandomPrice(1, 4, 1) + "." + getRandomPrice(0, 9, 1));
					if(Math.random() < 0.5) item.setDiscount(getRandomPrice(5,12,1));
					item.setImagePath(ext);
					item.setPath(path);
					item.setUserId(1);
					ID.addItem(item);
					System.out.println("ITEMID:" + item.getId());
					NaverCrawling.saveImg(s, new File(saveFile, item.getId() + ext));
				}
			}
			cr.getDriver().close();
			cr.getDriver().quit();
		}
	}
	
	
	
	void cr2(long d2id) {

		int count = 4;
		int p1 = 5000, p2 = 200000;
		
		MenuVo d2 = MD.getDepth2(d2id); // 검색어
		String search = S1 + d2.getName() + S2;
		

		MenuVo d1 = MD.getDepth1(d2.getParentId());
		RootVo root = MD.getRootById(d1.getParentId());

//		String pathName = "/"+root.getName()+"/"+d1.getName()+"/"+d2.getName();
		String path = "/" + root.getId() + "/" + d1.getId() + "/" + d2.getId();
//		String search = d2.getName();

		for (int c = 0; c < count; c++) {
			NaverCrawling cr = new NaverCrawling();
			cr.parse(search + (c == 0 ? "" : ("" + c)));
			List<String> ts = cr.getTitles();
			List<String> ss = cr.getSrcs();

			File saveFile = new File("D:/resources/images");

			for (String f : path.split("/")) {
				File f1 = new File(saveFile, f);
				saveFile = f1;
				if (!f1.exists())
					f1.mkdir();
			}

			for (int i = 0; i < ts.size(); i++) {
				String t = NaverCrawling.removeSpChar(ts.get(i));
				String s = ss.get(i);

				if (ID.getItemByName(t) != null || !s.startsWith("http"))
					continue;

				System.out.println(s);
				System.out.println(t);
				String s1 = s.split("\\?")[1];
				String s2 = s1.split("&")[0];
				String ext = s2.substring(s2.lastIndexOf("."));

				if (ext.endsWith("jpg") || ext.endsWith("png") || ext.endsWith("jpeg") || ext.endsWith("gif")) {
					ItemVo item = new ItemVo();
					item.setContent(t);
					item.setName(t);
					item.setPrice(getRandomPrice(p1, p2, 100));
					item.setNumStock(100);
					item.setScore("" + getRandomPrice(1, 4, 1) + "." + getRandomPrice(0, 9, 1));
					if(Math.random() < 0.5) item.setDiscount(getRandomPrice(5,12,1));
					item.setImagePath(ext);
					item.setPath(path);
					item.setUserId(1);
					ID.addItem(item);
					System.out.println("ITEMID:" + item.getId());
					NaverCrawling.saveImg(s, new File(saveFile, item.getId() + ext));
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
