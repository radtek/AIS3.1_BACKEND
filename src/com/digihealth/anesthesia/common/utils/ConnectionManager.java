package com.digihealth.anesthesia.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.digihealth.anesthesia.common.config.Global;

public class ConnectionManager {
	private static Logger logger = Logger.getLogger(ConnectionManager.class);
	
	private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();
	
    /**
     * 永兴县人民医院HIS数据库连接
     */
    private static String YXRM_HIS_JDBC_DRIVER_CLASS = Global.getJdbcConfig("yxrm.jdbc.driver.oracle");
    private static String YXRM_HIS_JDBC_URL = Global.getJdbcConfig("yxrm.jdbc.url.oracle");
    private static String YXRM_HIS_JDBC_USERNAME = Global.getJdbcConfig("yxrm.jdbc.username.oracle");
    private static String YXRM_HIS_JDBC_PASSWORD = Global.getJdbcConfig("yxrm.jdbc.password.oracle");

    /**
     * 本溪市中心医院HIS数据库连接
     */
	private static String SYBX_JDBC_SQLSERVER_DRIVER_CLASS = Global.getJdbcConfig("sybx.jdbc.driver.sqlserver");
    private static String SYBX_JDBC_SQLSERVER_URL = Global.getJdbcConfig("sybx.jdbc.url.sqlserver");
    private static String SYBX_JDBC_SQLSERVER_USERNAME = Global.getJdbcConfig("sybx.jdbc.username.sqlserver");
    private static String SYBX_JDBC_SQLSERVER_PASSWORD = Global.getJdbcConfig("sybx.jdbc.password.sqlserver");
    
    private static String SYBX_JDBC_YY_SQLSERVER_DRIVER_CLASS = Global.getJdbcConfig("sybx.jdbc.yy.driver.sqlserver");
    private static String SYBX_JDBC_YY_SQLSERVER_URL = Global.getJdbcConfig("sybx.jdbc.yy.url.sqlserver");
    private static String SYBX_JDBC_YY_SQLSERVER_USERNAME = Global.getJdbcConfig("sybx.jdbc.yy.username.sqlserver");
    private static String SYBX_JDBC_YY_SQLSERVER_PASSWORD = Global.getJdbcConfig("sybx.jdbc.yy.password.sqlserver");
	/**
	 * 从连接池拿Connection
	 * 
	 * getConnection和connectionHolder.get()的区别
	 * connectionHolder.get()是尝试从ThreadLocal中获取Connection,如果没有,返回null,如果有,直接返回.
	 * getConnection也是尝试从ThreadLocal中获取Connection,如果没有,则创建一个,然后返回,如果有,直接返回.
	 */
	public static Connection getConnection(String jdbcDriveClass,String jdbcUrl,String jdbcUserName,String jdbcPassword) {

		Connection connection = connectionHolder.get();

		if (connection == null) {
			// 1.连接池可以理解是一个java类,必须实现接口DateSource
			// 2.DBCP连接池也是一个java类,Tomcat为其new了对象并注册到JNDI,同时Tomcat实现了JavaEE规范之一的JNDI
			// 3.Context接口的lookup()可以从JNDI获取对象,通过名值对的形式;下面语句获取连接池对象(DateSource类型)
			try {
				Class.forName(jdbcDriveClass); // classLoader,加载对应驱动
				connection = (Connection) DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcPassword);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("ConnectionManager--getConnection(SQLException):"+e.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("ConnectionManager--getConnection(ClassNotFoundException):"+e.getMessage());
			}
			// 直接从连接池拿连接,不是此时才让Oracle去创建连接
			connectionHolder.set(connection);
		}
		return connection;
	}
    
    //--------------------------------------------------永兴人民医院--------------------------------------------------------------
    /**
     * 获取永兴人民医院HIS的连接
     * @return
     */
    public static Connection getYXRMHisConnection(){
        logger.info("YXRM_HIS_JDBC_DRIVER_CLASS = " +YXRM_HIS_JDBC_DRIVER_CLASS);
        return getConnection(YXRM_HIS_JDBC_DRIVER_CLASS,YXRM_HIS_JDBC_URL,YXRM_HIS_JDBC_USERNAME,YXRM_HIS_JDBC_PASSWORD);
    }
    //--------------------------------------------------永兴人民医院--------------------------------------------------------------

	//--------------------------------------------------沈阳本溪--------------------------------------------------------------
	/**
     * 连接沈阳本溪his系统数据库
     * 从连接池拿Connection
     * 
     * getConnection和connectionHolder.get()的区别
     * connectionHolder.get()是尝试从ThreadLocal中获取Connection,如果没有,返回null,如果有,直接返回.
     * getConnection也是尝试从ThreadLocal中获取Connection,如果没有,则创建一个,然后返回,如果有,直接返回.
     */
    public static Connection getSYBXHisConnection() {
        logger.info("LIS_JDBC_DRIVER_CLASS = " +SYBX_JDBC_SQLSERVER_DRIVER_CLASS);
        return getConnection(SYBX_JDBC_SQLSERVER_DRIVER_CLASS,SYBX_JDBC_SQLSERVER_URL,SYBX_JDBC_SQLSERVER_USERNAME,SYBX_JDBC_SQLSERVER_PASSWORD);
    }

    /**
     * 连接用友系统数据库
     * 从连接池拿Connection
     * 
     * getConnection和connectionHolder.get()的区别
     * connectionHolder.get()是尝试从ThreadLocal中获取Connection,如果没有,返回null,如果有,直接返回.
     * getConnection也是尝试从ThreadLocal中获取Connection,如果没有,则创建一个,然后返回,如果有,直接返回.
     */
    public static Connection getSYBXYYConnection() {
        logger.info("LIS_JDBC_DRIVER_CLASS = " +SYBX_JDBC_YY_SQLSERVER_DRIVER_CLASS);
        return getConnection(SYBX_JDBC_YY_SQLSERVER_DRIVER_CLASS,SYBX_JDBC_YY_SQLSERVER_URL,SYBX_JDBC_YY_SQLSERVER_USERNAME,SYBX_JDBC_YY_SQLSERVER_PASSWORD);
    }
    //--------------------------------------------------沈阳本溪--------------------------------------------------------------

    /**
	 * Connection使用完毕,关闭
	 * 此处的Connection是从连接池中拿出来的,关闭Connection实质上是让Connection恢复空闲状态
	 */
	public static void closeConnection() {
		// 尝试从ThreadLocal获取Connection,如果没有,关闭Connection失去意义.
		Connection connection = connectionHolder.get();

		if (connection != null) {
			try {
				connection.close();
				connectionHolder.remove();
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("ConnectionManager--closeConnection(SQLException):"+e.getMessage());
			}
		}
	}
	
	/**
     * Connection使用完毕,关闭
     * 此处的Connection是从连接池中拿出来的,关闭Connection实质上是让Connection恢复空闲状态
     */
    public static void closeConnection(Connection connection) {
        // 尝试从ThreadLocal获取Connection,如果没有,关闭Connection失去意义.
        //Connection connection = connectionHolder.get();

        if (connection != null) {
            try {
                connection.close();
                //connectionHolder.remove();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("ConnectionManager--closeConnection(SQLException):"+Exceptions.getStackTraceAsString(e));
            }
        }
    }
}
