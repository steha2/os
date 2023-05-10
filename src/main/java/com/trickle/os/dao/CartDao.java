package com.trickle.os.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.trickle.os.vo.CartVo;
import com.trickle.os.vo.ItemVo;
import com.trickle.os.vo.OrderVo;

@Repository
public class CartDao {
	private final SqlSession sqlSession;
	
	public CartDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}

	public List<CartVo> getCarts(long rootId, long userId) {
		return sqlSession.selectList("CartMapper.getCarts", cartMapper(rootId,userId));
	}
	
	public List<ItemVo> getCartItems(long rootId, long userId) {
		return sqlSession.selectList("CartMapper.getCartItems", cartMapper(rootId,userId));
	}
	
	public Map<String,Object> cartMapper(long rootId, long userId) {
		Map<String,Object> map = new HashMap<>();
		map.put("rootId", rootId);
		map.put("userId", userId);
		return map;
	}

	public void addCart(CartVo cart) {
		sqlSession.insert("CartMapper.addCart",cart);
	}
	
	public CartVo getCartItem(CartVo cart) {
		return sqlSession.selectOne("CartMapper.getCartItem", cart);
	}

	public void deleteCart(long cartId, long userId) {
		Map<String,Object> map = new HashMap<>();
		map.put("cartId", cartId);
		map.put("userId", userId);
		sqlSession.delete("CartMapper.deleteCart",map);
	}
	
	public void deleteCarts(long rootId, long userId) {
		sqlSession.delete("CartMapper.deleteCarts",cartMapper(rootId, userId));
	}

	public Integer getOrderSeq() {
		return sqlSession.selectOne("CartMapper.getOrderSeq");
	}

	public void addOrder(OrderVo order) {
		sqlSession.insert("CartMapper.addOrder",order);
	}
}
