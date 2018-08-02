package com.digihealth.anesthesia.common.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.dao.DataAccessResourceFailureException;
//import org.springframework.dao.RecoverableDataAccessException;
//import org.springframework.jdbc.CannotGetJdbcConnectionException;
//import org.springframework.jdbc.UncategorizedSQLException;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.utils.CustomConfigureUtil;
import com.digihealth.anesthesia.common.config.Constants;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.utils.ConnectionUtils;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLQueryInterruptedException;

public class MsgProcessReceiver {


	private final static Logger logger = Logger.getLogger(MsgProcessReceiver.class);

	public final static String model = Global.getConfig(Constants.MASTERORSLAVE_STR).toString();
	public static String queues = Global.getConfig(Constants.QUEUES_STR).toString();
	public final static String curQueue = Global.getConfig(Constants.CURQUEUE_STR).toString();

	public final static String DRIVER_OPPOSED = Global.getConfig("jdbc.driver_opposed").toString();
	public final static String URL_OPPOSED = Global.getConfig("jdbc.url_opposed").toString();
	public final static String USERNAME_OPPOSED = Global.getConfig("jdbc.username_opposed").toString();
	public final static String PASSWORD_OPPOSED = Global.getConfig("jdbc.password_opposed").toString();
	
	public final static String DRIVER = Global.getConfig("jdbc.driver").toString();
	public final static String URL = Global.getConfig("jdbc.url").toString();
	public final static String USERNAME = Global.getConfig("jdbc.username").toString();
	public final static String PASSWORD = Global.getConfig("jdbc.password").toString();

	private static final Object lock = new Object();

	private Connection connection = null;//master的connection 

	private PreparedStatement pstm = null; //master的pstm
	
	private PreparedStatement prepareStm = null; //slave的prepareStm
	
	private Statement stmt = null;//master的stmt

	private ResultSet rs = null;

	private String sql = null;
	
	private boolean bool = false;
	
	private Connection conn = null; //slave的conn
	
	private Statement statement = null; //slave 的 statement 

	public MsgProcessReceiver() {
		
		super();
		
		logger.info("-----------------MsgProcessReceiver 启动----------------");
		logger.info("model=" + model + ",queues=" + queues + ",curQueue=" + curQueue + "url_opposed=" + URL_OPPOSED + ",username_opposed=" + USERNAME_OPPOSED );
	}

	public void job() {
		
		//判断是否需要同步
  		if(CustomConfigureUtil.isSync()){
  			logger.info("-----------------MsgProcessReceiver 不接受拦截数据，不做数据同步----------------");
  			return; 
  		}
		
		logger.info("job------数据同步receiver定时任务开始");
		try {
			String beid = MsgProcessHolder.findCurBeid();
			if (model.equals(Constants.MASTER)) { // 自己是master,master对应的表名是bas_message
				synchronized (lock) {
					while(true){ 
						// 查询master数据库 接收者是master(bas_message)的消息
						sql = "SELECT * FROM " + Constants.MSG_TABLE + " WHERE receiver = ?  AND ENABLE = 1 AND priority = 0 and beid = ? ORDER BY TIME,id LIMIT 1000 ";
						connection = ConnectionUtils.getConnection(DRIVER, URL, USERNAME, PASSWORD); //获取当前的connection，与druid的区分开来
						if(null != connection){
							pstm = connection.prepareStatement(sql);
							pstm.setString(1, Constants.MASTER);
							pstm.setString(2, beid);
							rs = pstm.executeQuery();
							if(rs.next()){
								connection.setAutoCommit(false);
								stmt = connection.createStatement();
								String ids = "0";
								String errorIds = "0";
								String noResIds = "0";
								
								do {
									int id = rs.getInt("id");
									String message = rs.getString("message");
									logger.info("master:next-->id="+id+",message="+message);
									try {
										int execUpdate = stmt.executeUpdate(message);//是否执行成功，成功条数大于0，则加入到ids中，如果执行成功条数等于0，则加入到noResIds中
										if (execUpdate > 0) { //大于0，则执行成功
											ids = ids + "," + id;
										}else{
											noResIds = noResIds + "," + id;
										}
									} catch (SQLException e) {
										logger.error("job--从master获取，slave执行出错："+Exceptions.getStackTraceAsString(e));
										if(e instanceof MySQLQueryInterruptedException || e instanceof MySQLNonTransientConnectionException || e instanceof CommunicationsException){ //当为连接异常的时候，直接break出去
											logger.info("break-->master--CommunicationsException|MySQLNonTransientConnectionException-"+",id="+id+",message="+message);
											bool = true;
											if(null != connection){ //连接异常，说明获取不到连接，直接置为null   数据库自身保护机制，原来执行的sql不会执行
												connection.rollback();
												connection.close();
												connection = null;
											}
											logger.warn("break-->master连接异常，将conn置为null，ids=0，errorIds=0，noResIds=0；"+"原ids="+ids+",errorIds="+errorIds+",noResIds="+noResIds);
											ids = "0";
											errorIds = "0";
											noResIds = "0";
											
											break;
										}
										errorIds = errorIds + "," + id; //如果为sql异常，则将id加入到errorIds中
										continue; //处理有错，continue
									}
									
								} while (rs.next());
								
								// 执行修改操作
								logger.info("MsgProcessReceiver:job(master)------b_message表消息置为不可用：" + ids+",errorIds="+errorIds+",noResIds="+noResIds);
								stmt.executeUpdate("UPDATE " + Constants.MSG_TABLE + " SET ENABLE = 0 WHERE id IN (" + ids + ")");//执行成功的数据大于0，则修改enable=0
								stmt.executeUpdate("UPDATE " + Constants.MSG_TABLE + " SET ENABLE = 2 WHERE id IN (" + errorIds + ")"); //enable为2的数据，则为异常数据
								stmt.executeUpdate("UPDATE " + Constants.MSG_TABLE + " SET ENABLE = 3 WHERE id IN (" + noResIds + ")"); //enable为3的数据，则为执行成功但未返回成功条数的数据
								
								if(null != connection){
									connection.commit();
									connection.setAutoCommit(true);
								}
								if(bool){
									bool = false;
									break; //break 外层while;
								}
							}else{ //rs.next() 数据为false，break出while-true 循环
								logger.info("master:rs.next未获取到数据，break 出while-true循环");
								break;
							}
						}else{ //从jdbcTemplate获取不到连接
							logger.info("master:从jdbcTemplate中获取connection获取不到，break出while-true循环");
							break;
						}
						
						Thread.sleep(1);//睡眠1毫秒
					}
				}

			} else { // 自己是slave
				synchronized (lock) {
					connection = ConnectionUtils.getConnection(DRIVER_OPPOSED, URL_OPPOSED, USERNAME_OPPOSED, PASSWORD_OPPOSED); // 获取master的连接
					if (null != connection) {

						String table = Constants.MSG_TABLE + Constants.LINK + curQueue;

						conn = ConnectionUtils.getConnection(DRIVER, URL, USERNAME, PASSWORD); //获取slave自身的connection，与jdbcTemplate区分开来
						if (null != conn) {

							// step 1 从master取 receiver 为curQueue的消息列表
							while (true) {
								logger.info("进入while循环：从master取  receiver 为" + curQueue + "的消息列表----");
								// 查询master(b_message_(curQueue))的消息：receiver是自己，sender是master
								// or other slave(WK1)
								sql = "SELECT * FROM " + table + " WHERE receiver = ?  AND ENABLE = 1 and priority = 0 and beid = ? ORDER BY TIME,id LIMIT 1000 ";
								// 获取PreparedStatement对象
								pstm = connection.prepareStatement(sql);
								// 对SQL语句的占位符参数进行动态赋值
								pstm.setString(1, curQueue);
								pstm.setString(2, beid);
								// 执行查询获取结果集
								rs = pstm.executeQuery();

								if (rs.next()) {// 判断是否存在记录
									
									conn.setAutoCommit(false);
									statement = conn.createStatement();
									String ids = "0";
									String errorIds = "0";
									String noResIds = "0";
									do {
										int id = rs.getInt("id");
										String message = rs.getString("message");
										// String sender =
										// rs.getString("sender");
										// String receiver =
										// rs.getString("receiver");
										// int enable = rs.getInt("enable");
										logger.info("next-->id=" + id + ",message=" + message);
										try {
											int execUpdate = statement.executeUpdate(message);
											// int execUpdate =
											// jdbcTemplate.update(message);//
											// 执行是否成功

											if (execUpdate > 0) { // 大于0，则执行成功
												ids = ids + "," + id;
											} else {
												noResIds = noResIds + "," + id;
											}
										} catch (SQLException e) {
											logger.error("job--从master获取，slave执行出错：" + Exceptions.getStackTraceAsString(e));
											if (e instanceof MySQLQueryInterruptedException || e instanceof MySQLNonTransientConnectionException || e instanceof CommunicationsException) { // 当为连接异常的时候，直接break出去
												logger.info("break-->slave:" + curQueue + "-CannotGetJdbcConnectionException|DataAccessResourceFailureException-" + ",id=" + id + ",message=" + message);
												bool = true; // 设置为true，跳出外层while；
												if (null != conn) { // 连接异常，说明获取不到连接，直接置为null
																	// 数据库自身保护机制，原来执行的sql不会执行
													conn.rollback();
													conn.close();
													conn = null;
												}
												logger.warn("break-->slave(" + curQueue + ")连接异常，将conn置为null，ids=0，errorIds=0，noResIds=0；" + "原ids=" + ids + ",errorIds=" + errorIds + ",noResIds=" + noResIds);
												ids = "0";
												errorIds = "0";
												noResIds = "0";
												break;
											}
											errorIds = errorIds + "," + id;
											continue; // 处理有错，continue
										}
									} while (rs.next());

									// 执行修改操作
									logger.info("MsgProcessReceiver:job(slave获取master连接，将master的消息置为不可用)：ids=" + ids + ",errorIds=" + errorIds + ",noResIds=" + noResIds);
									sql = "UPDATE " + table + " SET ENABLE = 0 WHERE id IN  ( " + ids + ")";
									stmt = connection.createStatement();
									stmt.executeUpdate(sql);
									// 将异常的数据存入
									stmt.executeUpdate("UPDATE " + table + " SET ENABLE = 2 WHERE id IN  ( " + errorIds + ")");

									// 将执行成功但未返回成功条数的数据
									stmt.executeUpdate("UPDATE " + table + " SET ENABLE = 3 WHERE id IN  ( " + noResIds + ")");

									if (null != conn) {
										conn.commit();
										conn.setAutoCommit(true);
									}

									if (bool) {
										bool = false;
										break; // break 外层while
									}

								} else {
									logger.info("slave处理：(接收端是slave，发送端是master or other slave)--------查询未返回记录--------------");
									break; // 跳出while循环
								}

								Thread.sleep(1);// 睡眠1毫秒
							}

							// step 2 : 查自己（slave）的b_message表
							// 1、receiver是master的数据 2、receiver 不是master的数据（slave
							// 2 other slave） 发送者是自己的消息
							
							while(true){
								String sql = "SELECT * FROM " + table + " WHERE sender = ?  AND ENABLE = 1 and priority = 0 and beid = ? ORDER BY TIME,id  LIMIT 1000 ";
								prepareStm = conn.prepareStatement(sql);
								prepareStm.setString(1, curQueue);
								prepareStm.setString(2,beid);
								
								rs = prepareStm.executeQuery();
								if(rs.next()){
									String ids = "0";
									String errorIds = "0";
									String noResIds = "0";
									connection.setAutoCommit(false); // 设置connection为autocommit=false
									do {
										int id = rs.getInt("id");
										String message = rs.getString("message");
										String sender = rs.getString("sender");
										String receiver = rs.getString("receiver");
										logger.info("slave2master&otherSlave:id=" + id + ",message=" + message + ",sender=" + sender + ",receiver=" + receiver);
										try {
											// case 2
											// 处理：直接写入到master的b_message_(receiver)表中，方便slave获取并处理
											String curTable = Constants.MSG_TABLE;
											if (!receiver.equals(Constants.MASTER)) { // 如果receiver不是master,则表名需加上
																						// (_receiver)的值
												curTable = curTable + Constants.LINK + receiver;
											}
											sql = "INSERT INTO " + curTable + "  (message, time, priority, sender, receiver, enable, beid) values (?,now(),?,?,?,?,?)";
											pstm = connection.prepareStatement(sql);
											pstm.setString(1, message);
											pstm.setInt(2, 0);//priority
											pstm.setString(3, sender);//发送者
											pstm.setString(4, receiver);//接收者
											pstm.setInt(5, 1);//enable
											pstm.setString(6, beid);//beid
											
											int executeUpdate = pstm.executeUpdate();
											if (executeUpdate > 0) {
												ids = ids + "," + id;
											} else {
												noResIds = noResIds + "," + id;
											}
										} catch (SQLException e) { // 只处理sql异常的异常
											logger.error("job--slave2master,write 2 master b_message执行出错：" + Exceptions.getStackTraceAsString(e));

											if (e instanceof MySQLQueryInterruptedException || e instanceof MySQLNonTransientConnectionException || e instanceof CommunicationsException) {
												logger.warn("break-->master database can not obtain the connection: " + e.getMessage() + ",id=" + id + ",message=" + message);
												bool = true; // 如果连接异常，则直接break当前while，并设置bool为true，使得外层while也跳出；

												if (null != connection) { // 连接异常，说明获取不到连接，直接置为null
																			// 数据库自身保护机制，原来执行的sql不会执行
													connection.rollback();
													connection.close();
													connection = null;
												}
												logger.warn("break-->连接异常，将connection置为null，ids置为0，errorIds置为0，noResIds置为0；" + "原ids=" + ids + ",errorIds=" + errorIds + ",noResIds=" + noResIds);
												ids = "0";
												errorIds = "0";
												noResIds = "0";

												// 如果不跳出外层while，则会继续进入循环，这样就导致进程阻塞
												break;
											}
											errorIds = errorIds + "," + id;
											continue; // 处理有错，continue

										}
									} while (rs.next());
									
									// slave 执行批量修改为不可用的消息
									logger.info("MsgProcessReceiver:job(slave(sender:" + curQueue + ",receiver:master))------" + table + "表消息置为不可用：" + ids + ",errorIds=" + errorIds + ",noResIds=" + noResIds);
									statement = conn.createStatement();
									statement.executeUpdate("UPDATE " + table + " SET ENABLE = 0 WHERE id IN  (" + ids + ")");
									statement.executeUpdate("UPDATE " + table + " SET ENABLE = 2 WHERE id IN  (" + errorIds + ")");
									statement.executeUpdate("UPDATE " + table + " SET ENABLE = 3 WHERE id IN  (" + noResIds + ")");

									if (null != connection) {
										connection.commit();
										connection.setAutoCommit(true);
									}

									if (bool) { // true,则break 最外层的while
										bool = false; // 将bool状态置为false
										break;
									}

								}else{
									logger.info("slave(" + curQueue + "):rs.next未获取到数据，break 出while-true循环");
									break;
								}
							}
							
							
						} else {
							logger.info("slave处理:"+curQueue + "-------获取到(" + Constants.MASTER + ")的 connection is null --------");
						}

					} else {
						logger.info("slave处理：获取到" + curQueue + "的conn为null-----------break----");
					}
				}

			}
		} catch (Exception e) {
			bool = false;
			logger.error("ERROR:job=" + Exceptions.getStackTraceAsString(e));
		} finally {
			try {
				if(null != stmt){ //释放Statement对象
					stmt.close();
				}
				if (null != pstm) {// 释放 PreparedStatement 对象
					pstm.close();
				}
				if(null != prepareStm){ //释放prepareStm对象
					prepareStm.close();
				}
				if (null != rs) {// 释放 ResultSet
					rs.close();
				}
				if(null != statement){
					statement.close();
				}
				if(null != conn){
					conn.close();
				}
				if (null != connection) {// 释放 connection
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection conn = ConnectionUtils.getConnection("com.mysql.jdbc.Driver", "jdbc:mysql://192.168.5.223:3306/ais_szlg_test_master?useUnicode=true&characterEncoding=utf-8", "root", "123456");
		Statement statement = conn.createStatement();
		String ids = "1903,1904";
		String txt = "select * from b_message where id in (" + ids +")";
		ResultSet rs = statement.executeQuery(txt);
		if(rs.next()){
			do {
				int id = rs.getInt("id");
				String message = rs.getString("message");
				//statement.addBatch(message);
				System.out.println(id+"----"+message);
			} while (rs.next());
		}else{
			System.out.println("-------------no record----------------");
		}
		statement.addBatch("update b_message set enable = 1 where id in( 1,2 )");
		statement.addBatch("update b_message set enable = 1 where id in( 1898,1901 )");
		int[] is = null;
		try {
			is = statement.executeBatch();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		String string = Arrays.toString(is);
		System.out.println(string);
		
		if(statement!=null){
			statement.close();
		}
		if(rs != null){
			rs.close();
		}
		ConnectionUtils.closeConnection(conn);
	}*/
	
}
