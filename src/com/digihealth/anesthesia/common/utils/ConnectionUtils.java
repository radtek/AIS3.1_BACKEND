package com.digihealth.anesthesia.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

	/**
	 * 获取连接
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException {
		Connection connection = null;
		Class.forName(driver); // classLoader,加载对应驱动
		connection = (Connection) DriverManager.getConnection(url, username, password);
		return connection;
	}

	/**
	 * 关闭connection
	 * 
	 * @throws SQLException
	 */
	public static void closeConnection(Connection connection) throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}
}
