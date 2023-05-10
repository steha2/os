package com.trickle.os.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.trickle.os.vo.UserVo;

@Repository
public class UserDao {
	
	private final SqlSession sqlSession;
	
	public UserDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}
	
	public int addUser(UserVo user) {
		return sqlSession.insert("UserMapper.addUser",user);
	}
	
	public UserVo findById(long idx) {
		return sqlSession.selectOne("UserMapper.findById",idx);
	}

	public UserVo findByNamePw(UserVo user) {
		return sqlSession.selectOne("UserMapper.findByNamePw",user);
	}

	public UserVo findByName(UserVo user) {
		return sqlSession.selectOne("UserMapper.findByName",user);
	}
}
