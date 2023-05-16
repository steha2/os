package com.trickle.os.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.trickle.os.util.Debug;
import com.trickle.os.util.ItemPaging;
import com.trickle.os.vo.CommentVo;
import com.trickle.os.vo.ItemVo;

@Repository
public class ItemDao {
	private final SqlSession sqlSession;
	
	@Value("${config.resources}")
	private String resources;
	
	public ItemDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}
	
	public ItemVo getItemById(long id) {
		return sqlSession.selectOne("ItemMapper.getItemById", id);
	}
	
	public long addItem(ItemVo item) {
		return sqlSession.insert("ItemMapper.addItem", item);
	}

	public List<ItemVo> getItems(String path) {
		return sqlSession.selectList("ItemMapper.getItems", path);
	}

	public int updateItem(ItemVo item) {
		return sqlSession.update("ItemMapper.updateItem", item);
	}

	public int deleteItem(long id) {
		ItemVo item = getItemById(id);
		if(item == null) {
			Debug.log("ITEM IS NULL id:" + id);
			return -1;
		}
		File file = new File(resources+"/images", item.getImagePath());
		if(file.exists()) file.delete();
		return sqlSession.update("ItemMapper.deleteItem", id);
	}
	
	public List<ItemVo> getPagingItems(ItemPaging paging){
		return sqlSession.selectList("PagingMapper.getPagingItems",paging);
	}

	public int getTotalRows(ItemPaging paging) {
		Integer result = sqlSession.selectOne("ItemMapper.getTotalRows", paging);
		return result == null ? 0 : result;
	}

	public ItemVo getItemByName(String name) {
		return sqlSession.selectOne("ItemMapper.getItemByName", name);
	}

	public void updateNumView(long id) {
		sqlSession.update("ItemMapper.updateNumView",id);
	}
	
	public List<ItemVo> getRecentItems(long[] itemIds, int rootId, int count) {
		List<ItemVo> result = new ArrayList<>();
		for(int i=0; i<itemIds.length; i++) {
			ItemVo item = getItemById(itemIds[i]);
			long itemRootId = Long.parseLong(item.getPath().split("/")[1]);
			if(itemRootId == rootId) result.add(item);
			if(result.size() >= count) break;
		}
		return result;
	}

	public int addComment(CommentVo comment) {
		return sqlSession.insert("ItemMapper.addComment",comment);
	}
	
	public long getItemCount(String path) {
		Long count = sqlSession.selectOne("ItemMapper.getItemCount",path);
		return count == null ? 0 : count;
	}

	public List<CommentVo> getComments(long itemId) {
		return sqlSession.selectList("ItemMapper.getComments",itemId);
	}

	public void deleteComment(long id) {
		sqlSession.delete("ItemMapper.deleteComment",id);
	}
}
