package com.trickle.os.cr.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//import oracle.jdbc.pool.OracleDataSource;

public class DAO {
	private static final String url = "jdbc:oracle:thin:@//192.168.0.213:1521/xe";
	private static final String user = "os2";
	private static final String password = "1234";
	
	private static DataSource dataSource;

//    private static final Object lock = new Object();

    private DAO() {}

    public static Connection getConnection() {
		try { return getDataSource().getConnection(); } catch (SQLException e) {e.printStackTrace(); }
		return null;
	}

	public static DataSource getDataSource() {
		return dataSource == null ? dataSource = initDataSource(url, user, password) : dataSource;
	}

	public static DataSource initDataSource(String url, String user, String password) {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(url);
		config.setUsername(user);
		config.setPassword(password);
		config.setMaximumPoolSize(5);
		config.setConnectionTimeout(30000);
		config.setValidationTimeout(5000);
		return new HikariDataSource(config);
	}
	
//	public static DataSource initDataSource(String url, String user, String password) {
//		OracleDataSource dataSource = null;
//		try {
//			dataSource = new OracleDataSource();
//			dataSource.setURL(url);
//			dataSource.setUser(user);
//			dataSource.setPassword(password);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return dataSource;
//	}
}
