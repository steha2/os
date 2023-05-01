package com.trickle.os.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.trickle.os.util.ItemPaging;
import com.trickle.os.vo.ItemVo;

@Repository
public class ItemDao {
private final SqlSession sqlSession;
	
	public ItemDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}
	
	public ItemVo getItem(long id) {
		return sqlSession.selectOne("ItemMapper.getItem", id);
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
		return sqlSession.update("ItemMapper.deleteItem", id);
	}
	
	public List<ItemVo> getPagingItems(ItemPaging paging){
		return sqlSession.selectList("ItemMapper.getPagingItems",paging);
	}

	public int getTotalRows(ItemPaging paging) {
		return sqlSession.selectOne("ItemMapper.getTotalRows", paging);
	}

	public ItemVo getItemByName(String name) {
		return sqlSession.selectOne("ItemMapper.getItemByName", name);
	}
}
