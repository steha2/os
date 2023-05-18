package com.trickle.os.test;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class GmSql {
    private static String driver ="oracle.jdbc.OracleDriver";
    private static String url="jdbc:oracle:thin:@localhost:1521:xe";
    private static String user="gm";
    private static String password="1234";

	public static DataSource initDataSource() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driver);
		config.setJdbcUrl(url);
		config.setUsername(user);
		config.setPassword(password);
		config.setMaximumPoolSize(5);
		config.setConnectionTimeout(30000);
		config.setValidationTimeout(5000);
		System.out.println("Create Data Source");
		return new HikariDataSource(config);
	}
	
	public static JdbcTemplate sql() {
		return new JdbcTemplate(initDataSource());
	}
}

