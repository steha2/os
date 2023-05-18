package com.trickle.os.dao;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.trickle.os.controller.rest.*;
import com.trickle.os.test.Crawling;
import com.trickle.os.test.GmSql;
import com.trickle.os.test.cr.MyBatisConn;
import com.trickle.os.vo.*;

public class AddComments {
	private SqlSessionFactory factory = new MyBatisConn().sqlSessionFactory();
	public ItemDao ID = new ItemDao(factory);
	public MenuDao MD = new MenuDao(factory, ID);
	public MenuController MC = new MenuController(MD);
	public PagingDao PD = new PagingDao(factory);
	public ItemController IC = new ItemController(ID, PD);
	public ProcessController PC = new ProcessController();
	public ResponseController RC = new ResponseController();
	public JdbcTemplate sql = GmSql.sql();

	@Test
	public void t () {
		System.out.println("T Start");
		List<RootVo> roots = MC.getMenus();
		System.out.println(roots);
		roots.forEach(root -> {
			all(root.getChilds(), root, 1);
		});
		
		System.out.println("T End");
//		removeRoot(2);
//		ID.deleteItem(31);
	}

	public void all(List<MenuVo> menus, RootVo root, int depth) {
		if (menus == null)
			return;
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

			items.forEach(item -> {
				String ip = "/" + item.getId();
				List<CommentVo> cs = ID.getComments(item.getId());
				
//				System.out.println(item);
				
//				if(cs.size()<5) {
//					int r = getRandomPrice(10, 25, 1);
//
//					for(int a=0; a<r; a++) {
//						CommentVo c = new CommentVo();
//						c.setUserId(1);;
//						c.setScore(getRandomPrice(1, 5, 1));
//						c.setPath("/"+item.getId());
//						c.setContent(ip);
//						ID.addComment(c);
//						System.out.println(c);
//					}
//				};
			});
					
			// 자식 메뉴가 있는 경우 재귀적으로 호출하여 출력
			if (menu.getChilds() != null && !menu.getChilds().isEmpty()) {
				all(menu.getChilds(), root, depth + 1);
			}
		}
	}

	void add(long d2Id, String adds) {
		MenuVo d2 = MD.getDepth2(d2Id); // 검색어

		String add = "" + adds;

		MenuVo d1 = MD.getDepth1(d2.getParentId());
		RootVo root = MD.getRootById(d1.getParentId());

		String path = "/" + root.getId() + "/" + d1.getId() + "/" + d2.getId();
		String search = d2.getName();

		int count = 5;

		for (int c = 0; c < count; c++) {
			Crawling cr = new Crawling();
			cr.parse(search + add + (c == 0 ? "" : ("" + c)));
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
				String t = Crawling.removeSpChar(ts.get(i));
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
					item.setPrice(getRandomPrice(5000, 200000, 100));
					item.setNumStock(100);
					item.setScore("" + getRandomPrice(1, 5, 1));
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

	void removeRoot(long id) { // 해당 루트에 포함된 모든 아이템을 삭제한다.
		List<RootVo> roots = MC.getMenus();
		roots.stream().filter(root -> root.getId() == id).forEach(root -> {
			root.getChilds().forEach(d1 -> {
				d1.getChilds().forEach(d2 -> {
					List<ItemVo> items = ID.getItems(d2.getPath());
					items.forEach(i -> ID.deleteItem(i.getId()));
				});
			});
		});
	}
}