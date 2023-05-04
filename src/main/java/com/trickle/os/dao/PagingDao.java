package com.trickle.os.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.trickle.os.vo.ItemVo;

import paging.PagingDto;

@Repository
public class PagingDao {
	private final SqlSession sqlSession;

	public PagingDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}
	
	public void addTotalRows(PagingDto pd) {
		pd.setTotalRows(sqlSession.selectOne("PagingMapper.getTotalRows", pd));
		pd.calcPaging();
	}
	
	public List<ItemVo> getPagingItems(PagingDto pdto) {
		return sqlSession.selectList("PagingMapper.getPagingItems", pdto);
	}
}