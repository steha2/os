package com.trickle.os.dao;

import java.io.File;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import com.trickle.os.controller.rest.*;
import com.trickle.os.test.Crawling;
import com.trickle.os.test.GmSql;
import com.trickle.os.vo.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class T2 {
	private static SqlSessionFactory factory = new MyBatisConn().sqlSessionFactory();
	public static ItemDao ID = new ItemDao(factory);
	public static MenuDao MD = new MenuDao(factory, ID);
	public static MenuController MC = new MenuController(MD);
	public static PagingDao PD = new PagingDao(factory);
	public static ItemController IC = new ItemController(ID, PD);
	public static ProcessController PC = new ProcessController();
	public static ResponseController RC = new ResponseController();
	public static JdbcTemplate sql = GmSql.sql();
	
	public static void main(String[] args) {
		List<RootVo> roots = MC.getMenus();
		roots.forEach(root->{
			System.out.println(root.getName());
			all(root.getChilds(), root,1);
		});
		removeRoot(2);
		ID.deleteItem(31);
	}
	
	public static void all(List<MenuVo> menus, RootVo root, int depth) {
		if(menus == null) return;
	    for (MenuVo menu : menus) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < depth; i++) {
	            sb.append("  "); // 공백 2개
	        }
	        sb.append(menu.getName());
	        List<ItemVo> items = ID.getItems(menu.getPath());
	        
	        Long itemCount = (long) items.size();
	        sb.append(" (Items: ").append(itemCount).append(")");
	        System.out.println(sb.toString());
	        
	        items.forEach(item->{
	        	String ip = "/"+item.getId();
	        	List<CommentVo> cs = ID.getComments(item.getId());
	        	cs.forEach(c->{
	        		
	        	});
	        });
	        
	        if(depth == 2 && itemCount == 0) {
	        	System.out.println("뎁스2에 아이템이 없음.. 채우자! " + menu.getName());
	        	if(root.getType().equals("shop")) {
	        		
	        	}
	        	if(root.getType().equals("board")) {
	        		List<String> ds = sql.query("SELECT DEFINITION, VOCABULARY FROM DICTIONARY WHERE LENGTH(DEFINITION) > 50 ORDER BY DBMS_RANDOM.VALUE()", (rs,rowNum) -> {
	        			
	        			return  rs.getString("VOCABULARY")+ "#@" + rs.getString("DEFINITION");
	        			});
	        		String rt2 = ds.get(0).split("#@")[0];
	        		String rt = ds.get(0).split("#@")[1];
	        		String name = rt.substring(0,10);
	        		String cont = rt.substring(11, depth);
	        		ItemVo item = new ItemVo();
	        		item.setName(name);
	        		item.setContent(cont);
	        		item.setUserId(rt2);
	        	}
	        	if(root.getType().equals("score")) {
	        		
	        	}
	        }
	        
	        // 자식 메뉴가 있는 경우 재귀적으로 호출하여 출력
	        if (menu.getChilds() != null && !menu.getChilds().isEmpty()) {
	            all(menu.getChilds(), root, depth + 1);
	        }
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
					item.setUserId("Admin");
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