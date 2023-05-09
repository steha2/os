package com.trickle.os.dao;

import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.trickle.os.util.ItemPaging;
import com.trickle.os.vo.*;

@Repository
public class CartDao {
	private final SqlSession sqlSession;
	
	public CartDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}

	public CartVo getCart(long rootId, long userIdx) {
		CartVo cart = new CartVo();
		cart.setRootId(rootId);
		cart.setUserIdx(userIdx);
		cart.setItems(sqlSession.selectList("CartMapper.getCartItems", cart));
		return cart;
	}
}
