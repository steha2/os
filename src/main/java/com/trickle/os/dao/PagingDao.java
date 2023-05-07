package com.trickle.os.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.trickle.os.vo.ItemVo;

import paging.PagingData;

@Repository
public class PagingDao {
	private final SqlSession sqlSession;

	public PagingDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}
	
	public void addTotalRows(PagingData pd) {
		pd.calcPaging(sqlSession.selectOne("PagingMapper.getTotalRows", pd));
	}
	
	public List<ItemVo> getPagingItems(PagingData pd) {
		return sqlSession.selectList("PagingMapper.getPagingItems", pd);
	}
}