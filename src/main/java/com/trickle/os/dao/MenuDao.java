package com.trickle.os.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.trickle.os.vo.MenuVo;
import com.trickle.os.vo.RootVo;

@Repository
public class MenuDao {
private final SqlSession sqlSession;
	
	public MenuDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSession = new SqlSessionTemplate(sqlSessionFactory);
	}
	
	public RootVo getRootByType(String type) {
		return sqlSession.selectOne("MenuMapper.getRootByType", type);
	}
	
	public List<RootVo> getRoots(){
		return sqlSession.selectList("MenuMapper.getRoot");
	}
	
	public RootVo addChilds(RootVo root) {
		List<MenuVo> depth1 = sqlSession.selectList("MenuMapper.getDepth1");
		List<MenuVo> depth2 = sqlSession.selectList("MenuMapper.getDepth2");
		depth1.forEach(d1->{
			if(root.getId() == d1.getParentId()) {
				root.addMenu(d1);
			}
			depth2.forEach(d2->{
				if(d2.getParentId() == d1.getId()) d1.addMenu(d2);
			});
		});
		return root;
	}
	public MenuVo getDepth1(long id) {
		return sqlSession.selectOne("MenuMapper.getDepth1ById", id);
	}
	
	public MenuVo getDepth2(long id) {
		return sqlSession.selectOne("MenuMapper.getDepth2ById", id);
	}

	public RootVo getRootById(long id) {
		return sqlSession.selectOne("MenuMapper.getRootById", id);
	}
	
	public int addRoot(RootVo root) {
		return sqlSession.insert("MenuMapper.addRoot", root);
	}

	public int addDepth1(MenuVo menu) {
		return sqlSession.insert("MenuMapper.addDepth1", menu);
	}
	
	public int addDepth2(MenuVo menu) {
		return sqlSession.insert("MenuMapper.addDepth2", menu);
	}

	public int updateRootName(MenuVo root) {
		return sqlSession.insert("MenuMapper.updateRootName", root);
	}

	public int updateDepth1Name(MenuVo menu) {
		return sqlSession.insert("MenuMapper.updateDepth1Name", menu);
	}
	
	public int updateDepth2Name(MenuVo menu) {
		return sqlSession.insert("MenuMapper.updateDepth2Name", menu);
	}

	public String getPathName(String path) {
		String pathName = "";
		for(int i=1; i<path.split("/").length; i++) {
			int id = Integer.parseInt(path.split("/")[i]);
			if(i==1) {
				pathName += "/"+sqlSession.selectOne("MenuMapper.getRootName",id);
			}else if(i==2) {
				pathName += "/"+sqlSession.selectOne("MenuMapper.getDepth1Name",id);
			}else if(i==3) {
				pathName += "/"+sqlSession.selectOne("MenuMapper.getDepth2Name",id);
			}
		};
		return pathName;
	}

	public int updateStyle(RootVo root) {
		return sqlSession.update("MenuMapper.updateStyle", root);
	}
}
