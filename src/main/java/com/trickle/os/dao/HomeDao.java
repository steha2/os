package com.trickle.os.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HomeDao{
	private final SqlSession sqlSession;
	
	public HomeDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}
	
	public String getTime() {
		return sqlSession.selectOne("HomeMapper.getTime");
	}

	public String getRootNameById(int rootId) {
		return sqlSession.selectOne("HomeMapper.getRootNameById", rootId);
	}
}
