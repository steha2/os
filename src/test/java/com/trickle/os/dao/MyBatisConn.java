package com.trickle.os.dao;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MyBatisConn {
    private String driver ="oracle.jdbc.OracleDriver";
    private String url="jdbc:oracle:thin:@localhost:1521:xe";
    private String user="os";
    private String password="1234";

	public DataSource initDataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driver);
		config.setJdbcUrl(url);
		config.setUsername(user);
		config.setPassword(password);
		config.setMaximumPoolSize(5);
		config.setConnectionTimeout(30000);
		config.setValidationTimeout(5000);
		return new HikariDataSource(config);
	}

	public SqlSessionFactory sqlSessionFactory() {
		try {
			SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
			sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
					.getResources("classpath*:com/trickle/os/mapper/**/*.xml"));
			sessionFactory.setDataSource(initDataSource());
			sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
			return sessionFactory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SqlSession getSqlSession() {
		try {
			return new SqlSessionTemplate(sqlSessionFactory());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
