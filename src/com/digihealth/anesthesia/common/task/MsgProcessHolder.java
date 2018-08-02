package com.digihealth.anesthesia.common.task;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.dao.BasBusEntityDao;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.service.BasSysCodeService;
import com.digihealth.anesthesia.basedata.utils.CustomConfigureUtil;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.utils.CacheUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.config.Constants;
import com.digihealth.anesthesia.common.listener.ConstantHolder;

/**
 * 消息处理存入b_message表 Title: MsgProcessSender.java Description:
 * 
 * @author chenyong
 * @created 2017年5月18日 下午4:07:29
 */
@Component
public class MsgProcessHolder {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private BasSysCodeService basSysCodeService;

	private final static Logger logger = Logger.getLogger(MsgProcessHolder.class);

	// MasterOrSlave=slave
	// WorkstationName=workstation1,workstation2,workstation3
	// Workstation=workstation2
	public final static String model = Global.getConfig(Constants.MASTERORSLAVE_STR).toString();
	public static String queues = Global.getConfig(Constants.QUEUES_STR).toString();
	public final static String curQueue = Global.getConfig(Constants.CURQUEUE_STR).toString();

	/**
	 * 找到当前的beid
	 * 
	 * @return
	 */
	public static String findCurBeid() {
		String beid = null;
		beid = CacheUtils.get(ConstantHolder.ROUTING_ACCESS_CACHE, ConstantHolder.CUR_BEID).toString();
		if (StringUtils.isBlank(beid)) {
			BasBusEntityDao sysBusEntityDao = SpringContextHolder.getBean(BasBusEntityDao.class);
			beid = sysBusEntityDao.getBeid();
		}
		return beid;
	}

	@Transactional
	public void saveMsg(final String message) {
		try {
			String beid = MsgProcessHolder.findCurBeid();
			
			//判断是否需要同步
			if(CustomConfigureUtil.isSync()){
	  			logger.info("-----------------MsgProcessHolder 不保存mybatis拦截的sql ----------------");
	  			return; 
	  		}
			
			logger.info("saveMsg------bas_message消息：" + message);
			// 从b_sys_code表中获取不需要存入b_message表的列表
			List<SysCodeFormbean> msgNotSendList = basSysCodeService.searchSysCodeByGroupId(Constants.MSGNOTSEND, beid); // 传递beid=null
			// 从b_sys_code表中获取需要slave to other slave 存入b_message表的列表
			List<SysCodeFormbean> msgSlave2SlaveList = basSysCodeService.searchSysCodeByGroupId(Constants.MSGSLAVE2SLAVE, beid);// 传递beid=null
			// 判断是否需要存入b_message表
			boolean flag = jdgeMsgSave(message, msgNotSendList);

			if (!flag) { // 如果为false，则执行插入操作(message的table不属于列表中)

				if (model.equals(Constants.MASTER)) { // 如果是master，则生成多条receiver的记录
					if (StringUtils.isBlank(queues)) {
						queues = Constants.MASTER;
					}
					String[] ques = queues.split(",");
					for (int i = 0; i < ques.length; i++) { // 分别插入对应的b_message_(receiver)表中，取的时候，只需要到对应的表中获取并消费即可
						String receiver = ques[i];
						String table = Constants.MSG_TABLE + Constants.LINK + receiver;
						logger.info("current model " + model + ",current queue " + curQueue + ",table " + table + ",save to " + curQueue + ",receiver= " + receiver + ",message= " + message);
						String sql = "INSERT INTO " + table + " (message, time, priority, sender, receiver, enable, beid) values (?,now(),?,?,?,?,?)";
						jdbcTemplate.update(sql, new Object[] { message, 0, model, receiver, 1, beid });
					}
				} else { // slave
							// 特殊情况处理 slave 2 slave 实际则是发送到master和其他workstation
					boolean slaveFlag = jugeSlave2Slave(message, msgSlave2SlaveList, "msgSlave2Slave");
					String table = Constants.MSG_TABLE + Constants.LINK + curQueue;
					if (slaveFlag) { // if true,then need save to other
										// workstation without self & master
						logger.info("current model " + model + ",current queue " + curQueue + ",save to " + curQueue + ",receiver= " + Constants.MASTER + ",message= " + message);
						// 先生成master的b_message记录
						String sql = "INSERT INTO " + table + "(message, time, priority, sender, receiver, enable, beid) values (?,now(),?,?,?,?,?)";
						jdbcTemplate.update(sql, new Object[] { message, 0, curQueue, Constants.MASTER, 1, beid });

						// 执行生成other workstation 记录
						if (StringUtils.isBlank(queues)) {
							queues = Constants.SLAVE;
						}
						String[] ques = queues.split(",");
						for (int i = 0; i < ques.length; i++) {
							String receiver = ques[i];
							if (receiver.equals(curQueue)) { // 去除自己的workstation
								continue;
							}
							// 存自己的对应的表（如果为wk1，则table为b_message_workstation1）
							logger.info("current model " + model + ",current queue " + curQueue + ",table " + table + ",save to " + curQueue + ",receiver= " + receiver + ",message= " + message);
							sql = "INSERT INTO " + table + "(message, time, priority, sender, receiver, enable, beid) values (?,now(),?,?,?,?,?)";
							jdbcTemplate.update(sql, new Object[] { message, 0, curQueue, receiver, 1, beid });
						}

					} else {// if false ,only save to slave(myself) DB
							// 只需要存入bas_message_(slave)
						logger.info("current model " + model + ",current queue " + curQueue + ",save to " + curQueue + ",receiver= " + Constants.MASTER + ",message= " + message);
						String sql = "INSERT INTO " + table + "(message, time, priority, sender, receiver, enable, beid) values (?,now(),?,?,?,?,?)";
						jdbcTemplate.update(sql, new Object[] { message, 0, curQueue, Constants.MASTER, 1, beid });
					}
				}
			}
		} catch (Exception e) {
			logger.error("exception:saveMsg==" + Exceptions.getStackTraceAsString(e));
		}

	}

	/**
	 * 
	 * @param message
	 * @param msgSlave2SlaveList
	 * @param slaveFlag
	 * @return
	 */
	private boolean jugeSlave2Slave(final String message, List<SysCodeFormbean> msgSlave2SlaveList, String type) {
		boolean slaveFlag = false;
		if (null != msgSlave2SlaveList && msgSlave2SlaveList.size() > 0) {
			for (int i = 0; i < msgSlave2SlaveList.size(); i++) {
				SysCodeFormbean sysCodeFormbean = msgSlave2SlaveList.get(i);
				String table = sysCodeFormbean.getCodeValue();
				boolean contains = message.contains(table);
				if (contains) {
					if (type.equals("msgNotSave")) {
						logger.info("(msgNotSave)拦截sql=" + message + ",包含" + table + ",contains=" + contains);
					} else {
						logger.info("(msgSlave2Slave)拦截sql=" + message + ",包含" + table + ",contains=" + contains);
					}
					slaveFlag = true;
					break;// 不存入b_message
				}
			}
		}
		return slaveFlag;
	}

	/**
	 * 
	 * @param message
	 * @param msgNotSendList
	 * @return
	 */
	private boolean jdgeMsgSave(final String message, List<SysCodeFormbean> msgNotSendList) {
		return jugeSlave2Slave(message, msgNotSendList, "msgNotSave");
	}
}
