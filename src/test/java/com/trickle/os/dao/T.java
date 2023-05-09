package com.trickle.os.dao;

import java.sql.Clob;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;

import com.trickle.os.controller.rest.*;
import com.trickle.os.paging.PagingData;
import com.trickle.os.util.StrUtil;
import com.trickle.os.vo.ItemVo;

public class T {
	private static SqlSessionFactory factory = new MyBatisConn().sqlSessionFactory();
	public static MenuDao MD = new MenuDao(factory);
	public static MenuController MC = new MenuController(MD);
	public static ItemDao ID = new ItemDao(factory);
	public static PagingDao PD = new PagingDao(factory);
	public static ItemController IC = new ItemController(ID, PD);
	public static ProcessController PC = new ProcessController();
	public static ResponseController RC = new ResponseController();
	
	public static void main(String[] args) {
//		System.out.println(factory);
//		System.out.println(StrUtil.toJson(MC.getMenus()));
//		ItemPaging p = new ItemPaging("/1");
//		p.setPaging(1, 10, 10, ID.getTotalRows(p));
//		ID.getPagingItems(p).forEach(System.out::println);
//		IC
		
		PagingData p = new PagingData();
//		p.setTable("Items", null);
		System.out.println(p.getColumns());
		p.addContains("NAME", "나");
		
//		p.setPaging(1, 10, 10, pd.getTotalRows(p));
//		p.setData(pd.getPagingItems(p));
//		System.out.println("data : " + p.getData());
//		System.out.println(StrUtil.toPrettyJson(p.getData()));
//		System.out.println(p.getData());
		
//		Map<String, Object > m = new HashMap<>();
	}
}