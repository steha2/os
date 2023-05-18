package com.trickle.os.controller.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.trickle.os.dao.ItemDao;
import com.trickle.os.dao.PagingDao;
import com.trickle.os.paging.FilterCondition;
import com.trickle.os.paging.PagingData;
import com.trickle.os.util.Debug;
import com.trickle.os.vo.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ItemController {
	private final ItemDao itemDao;
	private final PagingDao pagingDao;
	
	@PostMapping("/login/addItem")
	public ItemVo addItemAsLogin(ItemVo item, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		if(user != null) {
			item.setUserId(user.getId());
			if(itemDao.addItem(item) == 1) return item;
		}
		return null;
	}
	
	@PostMapping("/login/updateItem")
	public ItemVo updateItemAsLogin(ItemVo item, HttpSession session) {
		UserVo user = (UserVo) session.getAttribute("user");
		if(user != null) {
			if(itemDao.updateItem(item) == 1) return item;
		}
		return null;
	}
	
	@PostMapping("/addItem")
	public String addItem(ItemVo item) {
		if(itemDao.addItem(item) == 1) return "Success";
		return "Fail";
	}
	
	@GetMapping("/getItems")
	public List<ItemVo> getItems(String path) {
		return itemDao.getItems(path);
	}
	
	@PostMapping("/updateItem")
	public ItemVo updateItem(ItemVo item) {
		return itemDao.updateItem(item) == 1 ? item : null;
	}
	
	@GetMapping("/deleteItem")
	public String updateItem(long id) {
		return itemDao.deleteItem(id) == 1 ? "Success" : "Fail";
	}
	
	@GetMapping("/login/deleteItem")
	public String deleteItemAsLogin(long id) {
		return itemDao.deleteItem(id) == 1 ? "Success" : null;
	}
	
	@GetMapping(value="/getPagingItems") 
	public PagingData getPagingItems(PagingData pd) {
		pd.setTable("ITEMS","NAME, SCORE, ID, PRICE, DISCOUNT, NUM_SOLD, PATH, IMAGE_PATH, USER_ID, REG_DATE, NUM_STOCK, NUM_VIEW");
//		pd.setColumns(pd.getColumns().replace("content,", ""));
		System.out.println(pd);
		pagingDao.addTotalRows(pd);
		long t1 = System.currentTimeMillis();
		pd.setData(pagingDao.getPagingItems(pd));
		Debug.log(System.currentTimeMillis() - t1 + " ms");
		System.out.println(pd);
		System.out.println(pd.getWhereQuery());
		return pd;
	}
	
	@GetMapping(value="/getPagingItems2") 
	public PagingData getPagingItems2(PagingData pd) {
		pd.setTable("ITEMS_WITH_SCORE2",ItemVo.class);
		pd.setColumns(pd.getColumns().replace("content,", ""));
		pagingDao.addTotalRows(pd);
		long t1 = System.currentTimeMillis();
		pd.setData(pagingDao.getPagingItems(pd));
		System.out.println(System.currentTimeMillis() - t1);
		System.out.println(pd);
		return pd;
	}
	
	@RequestMapping(value="/os/getItems", produces = "text/plain; charset=utf8")
	public String getItems(String path, long rowCount, long maxPage, long nowPage) {
		PagingData pd = new PagingData();
		pd.setPath(path);
		pd.setMaxPage(maxPage);
		pd.setRowCount(rowCount);
		pd.setNowPage(nowPage);
		pd = getPagingItems(pd);
		ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(pd);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null; 
	}
	
	@GetMapping("/getRecentItems/{rootId}/{count}")
	public List<ItemVo> getRecentItems(@PathVariable int rootId, @PathVariable int count, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Long> itemIds = (List<Long>) session.getAttribute("recentItems");
		if(itemIds == null) return null;
		else return itemDao.getRecentItems(itemIds.stream().mapToLong(Long::longValue).toArray(), rootId, count);
	}
	
	@PostMapping("/getComments")
	public PagingData getComments(PagingData cpd) {
		cpd.setTable("COMMENTS_WITH_USER_NAME",CommentVo.class);
		pagingDao.addTotalRows(cpd);
		cpd.setData(pagingDao.getPagingComments(cpd));
		return cpd;
	}
	
	@PostMapping("/login/addComment")
	public String addComment(CommentVo comment, @SessionAttribute("user") UserVo user) {
		comment.setUserId(user.getId());
		return itemDao.addComment(comment) == 1 ? "Success" : null;
	}
}
