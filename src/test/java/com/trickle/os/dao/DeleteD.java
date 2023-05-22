package com.trickle.os.dao;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.trickle.os.controller.rest.*;
import com.trickle.os.test.Crawling;
import com.trickle.os.test.GmSql;
import com.trickle.os.test.cr.MyBatisConn;
import com.trickle.os.vo.*;

public class DeleteD {
	private static SqlSessionFactory factory = new MyBatisConn("os2").sqlSessionFactory();
	public static ItemDao ID = new ItemDao(factory);
	public static MenuDao MD = new MenuDao(factory, ID);
	public static MenuController MC = new MenuController(MD);
	public static PagingDao PD = new PagingDao(factory);
	public static ItemController IC = new ItemController(ID, PD);
	public static ProcessController PC = new ProcessController();
	public static ResponseController RC = new ResponseController();
	public static JdbcTemplate sql = GmSql.sql();
	
	public static String deletePath = "/1/47/48";
	
	public static void main(String[] args) {
//		List<RootVo> roots = MC.getMenus();
//		roots.forEach(root->{
//			System.out.println(root.getName());
//			all(root.getChilds(), root,1);
//		});
		ID.getItems("/").forEach(item->{
			File file = new File("d:/resources/images",item.getImagePath());
        	if(!file.exists()) {
        		ID.deleteItem(item.getId());
        		System.out.println(item);
        	}
		});
//		removeRoot(2);
//		ID.deleteItem(31);
	}
	
	public static void all(List<MenuVo> menus, RootVo root, int depth) {
		if(menus == null) return;
	    for (MenuVo menu : menus) {
	    	if(depth == 1) all(menu.getChilds(), root, 2);
	    	
	    	System.out.println(menu.getPath());
//	        if(!menu.getPath().equals(deletePath)) continue;
	        List<ItemVo> items = ID.getItems(menu.getPath());
	        System.out.println(items);
	        items.forEach(item->{
	        	String ip = "/"+item.getId();
	        	
//	        	List<CommentVo> cs = ID.getComments(item.getId());
//	        	cs.forEach(c->{
//	        		ID.deleteComment(c.getId());
//	        	});
//	        	ID.deleteItem(item.getId());
	        });
//	        MD.deleteMenu(menu.getId());
//	        System.out.println("dleete: " + menu);
	    }
	}
	
	void add(long d2Id, String adds) {
		MenuVo d2 = MD.getDepth2(    d2Id    ); //검색어 
		
		String add = "" + adds;
		
		MenuVo d1 = MD.getDepth1(d2.getParentId());
		RootVo root = MD.getRootById(d1.getParentId());
		
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
				
				if(ID.getItemByName(t) != null || !s.startsWith("http")) continue;
	
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
					item.setUserId(1);
					ID.addItem(item);
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
    
    
    static void removeRoot(long id) {
    	List<RootVo> roots = MC.getMenus();
		roots.stream().filter(root->root.getId()==id).forEach(root->{
			root.getChilds().forEach(d1->{
				d1.getChilds().forEach(d2->{
					List<ItemVo> items = ID.getItems(d2.getPath());
					items.forEach(i->ID.deleteItem(i.getId()));
				});
			});
		});
    }
}