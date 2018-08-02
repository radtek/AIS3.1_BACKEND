/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.digihealth.anesthesia.basedata.service;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.BasDeviceConfigFormBean;
import com.digihealth.anesthesia.basedata.formbean.BasDeviceConfigOperateFormBean;
import com.digihealth.anesthesia.basedata.formbean.BasDeviceMonitorConfigFormBean;
import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.OperRoomFormBean;
import com.digihealth.anesthesia.basedata.po.BasDeviceConfig;
import com.digihealth.anesthesia.basedata.po.BasDeviceMonitorConfig;
import com.digihealth.anesthesia.basedata.po.BasMonitorConfigFreq;
import com.digihealth.anesthesia.basedata.utils.CustomConfigureUtil;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfig;
import com.digihealth.anesthesia.sysMng.po.BasDictItem;

/**
 * 
 * Title: DeviceConfigService.java Description: 设备型号Service
 * 
 * @author liukui
 * @created 2015-10-7 下午6:00:54
 */
@Service
public class BasDeviceConfigService extends BaseService {
	private static final Integer ENBLE = 1; // 床旁设备可用

	public List<BasDeviceConfig> getDeviceConfigList() {
		return basDeviceConfigDao.getDeviceConfigList(getBeid(),getCurRoomId(null));
	}

	public List<BasDeviceConfig> getEnableDeviceConfigList() {
		return basDeviceConfigDao.getEnableDeviceConfigList(getBeid(),getCurRoomId(null));
	}

	@Transactional
	public void saveDeviceConfig(BasDeviceConfigOperateFormBean deviceConfigOperateFormBean) {
		if (deviceConfigOperateFormBean != null) {
			
			String roomId = getCurRoomId(null);

			// 只有当传入的deviceConfig及子集不为空才保存当下数据
			if (deviceConfigOperateFormBean.getDeviceConfig() != null && deviceConfigOperateFormBean.getDeviceMonitorConfigList() != null) {

				BasDeviceConfig deviceConfig = deviceConfigOperateFormBean.getDeviceConfig();
				if (StringUtils.isBlank(deviceConfig.getBeid())) {
					deviceConfig.setBeid(getBeid());
				}

				deviceConfig.setRoomId(roomId);
				deviceConfig.setId(GenerateSequenceUtil.generateSequenceNo());
				// 先删除床旁设备配置表的数据，再做新增处理
				basDeviceConfigDao.deleteByPrimaryKey(deviceConfig);
				if (null == deviceConfig.getEnable()) {
					deviceConfig.setEnable(ENBLE);
				}
				basDeviceConfigDao.insert(deviceConfig);

				// 获取之前设置的设备监测项勾选配置信息，设置监测项为空。注：必选项不做修改
				List<BasDeviceMonitorConfigFormBean> isChecklist = basDeviceMonitorConfigDao.getDeviceMonitorConfigList(deviceConfig.getBeid(), deviceConfig.getDeviceId(), "O",roomId);
				for (BasDeviceMonitorConfigFormBean checkPo : isChecklist) {
					BasDeviceMonitorConfig deviceMonitorConfig = new BasDeviceMonitorConfig();
					deviceMonitorConfig.setOptional(null);
					deviceMonitorConfig.setRoomId(roomId);
					deviceMonitorConfig.setDeviceId(deviceConfig.getDeviceId());
					deviceMonitorConfig.setEventId(checkPo.getEventId());
					if (StringUtils.isBlank(deviceMonitorConfig.getBeid())) {
						deviceMonitorConfig.setBeid(getBeid());
					}

					basDeviceMonitorConfigDao.update(deviceMonitorConfig);
				}
				// 将页面传入的监测项设置为勾选
				List<BasDeviceMonitorConfig> list = deviceConfigOperateFormBean.getDeviceMonitorConfigList();
				for (BasDeviceMonitorConfig deviceMonitorConfig : list) {
					deviceMonitorConfig.setRoomId(roomId);
					deviceMonitorConfig.setDeviceId(deviceConfig.getDeviceId());
					deviceMonitorConfig.setOptional("O");
					if (StringUtils.isBlank(deviceMonitorConfig.getBeid())) {
						deviceMonitorConfig.setBeid(getBeid());
					}
					basDeviceMonitorConfigDao.update(deviceMonitorConfig);
				}
			}
			// 保存采集频率配置
			if (deviceConfigOperateFormBean.getMonitorConfigFreqList() != null) {
				List<BasMonitorConfigFreq> freqList = deviceConfigOperateFormBean.getMonitorConfigFreqList();
				for (BasMonitorConfigFreq monitorConfigFreq : freqList) {
					if (StringUtils.isBlank(monitorConfigFreq.getBeid())) {
						monitorConfigFreq.setBeid(getBeid());
					}
					basMonitorConfigFreqDao.update(monitorConfigFreq);
				}
			}
		}
	}

	/**
	 * 获取串口信息
	 */
	public List<String> getUseSerialPortList() {
		List<String> serList = basDeviceConfigDao.getUseSerialPortList(getBeid(),getCurRoomId(null));
		return serList;
	}

	/**
	 * 获取可用串口信息列表
	 */
	@Transactional
	@SuppressWarnings("rawtypes")
	public void listPortChoices() {
		logger.info(" ---- start get Serial port ---- ");
		
		/**
		 * 如果是直接访问控制中心，直接在BasDictItem手动配置串口信息
		 */
		if(CustomConfigureUtil.isSync()){
			return;
		}
		
		CommPortIdentifier portId;
		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		// iterate through the ports.
		// 删除码表中配置的串口信息
		basDictItemDao.deleteDictItemGroup("serial_port", getBeid()+getCurRoomId(null));

		boolean hasSerial = en.hasMoreElements();

		if (!hasSerial) {
			logger.info(" ---- current system has no Serial port ---- ");
		}
		int i = 1;
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier) en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				String str = portId.getName();
				logger.info("listPortChoices------" + str);

				BasDictItem dictItem = new BasDictItem();
				dictItem.setGroupId("serial_port");
				dictItem.setCodeValue(str);
				dictItem.setCodeName(str);
				dictItem.setOrder(i++);
				dictItem.setBeid(getBeid()+getCurRoomId(null));
				basDictItemDao.insertSelective(dictItem);
			}
		}
	}
	
	@Transactional
	public void saveBeidDeviceConfig(BasDeviceConfigFormBean record) {
		
		//添加设备时判断该手术室是否初始化了氧流量的监测项，存在则跳过不存在则添加
		BasMonitorConfig basMonitorO2 = basMonitorConfigDao.selectByPrimaryKey("91001", record.getBeid(), "0");
		if(null==basMonitorO2){
			basMonitorO2 = basMonitorConfigDao.selectByPrimaryKey("91001", "0", "0");
			basMonitorO2.setBeid(record.getBeid());
			basMonitorO2.setRoomId("0");
			basMonitorConfigDao.insertSelective(basMonitorO2);
		}
		
		
		BasDeviceConfig basDeviceConfig = record.getBasDeviceConfig();
		basDeviceConfig.setEnable(0);
		basDeviceConfig.setRoomId("0");
		
		//如果是启用状态则插数据
		if("1".equals(record.getChecked())){
			
			/**
			 * 将设备表的数据初始化到device_monitor_config
			 */
			List<BasDeviceMonitorConfig> devMoList = basDeviceMonitorConfigDao.getRoomDeviceMonitorConfigByBeid("0", basDeviceConfig.getDeviceId(), basDeviceConfig.getRoomId());
			
			List<BasMonitorConfig> monitorList = basMonitorConfigDao.selectMonitorListByDeviceId(basDeviceConfig.getBeid(), basDeviceConfig.getDeviceId());
			
			basDeviceConfigDao.insert(basDeviceConfig);
			
			for (BasDeviceMonitorConfig basDeviceMonitorConfig : devMoList) {
				basDeviceMonitorConfig.setBeid(basDeviceConfig.getBeid());
				basDeviceMonitorConfig.setRoomId(basDeviceConfig.getRoomId());
				basDeviceMonitorConfigDao.insert(basDeviceMonitorConfig);
			}
			
			if(null!=monitorList && monitorList.size()>0){
				for (BasMonitorConfig basMonitorConfig : monitorList) {
					basMonitorConfig.setBeid(basDeviceConfig.getBeid());
					basMonitorConfig.setRoomId(basDeviceConfig.getRoomId());
					basMonitorConfigDao.insert(basMonitorConfig);
				}
			}
			
			BaseInfoQuery baseQuery = new BaseInfoQuery();
			baseQuery.setBeid(record.getBeid());
			List<OperRoomFormBean> roomList = basOperroomDao.findList(baseQuery);
			
			for (OperRoomFormBean operRoomFormBean : roomList) {
				basDeviceConfig.setRoomId(operRoomFormBean.getOperRoomId());
				basDeviceConfigDao.insert(basDeviceConfig);
				
				
				for (BasDeviceMonitorConfig basDeviceMonitorConfig : devMoList) {
					basDeviceMonitorConfig.setBeid(basDeviceConfig.getBeid());
					basDeviceMonitorConfig.setRoomId(operRoomFormBean.getOperRoomId());
					basDeviceMonitorConfigDao.insert(basDeviceMonitorConfig);
				}
				
				if(null!=monitorList && monitorList.size()>0){
					for (BasMonitorConfig basMonitorConfig : monitorList) {
						basMonitorConfig.setBeid(basDeviceConfig.getBeid());
						basMonitorConfig.setRoomId(operRoomFormBean.getOperRoomId());
						basMonitorConfigDao.insert(basMonitorConfig);
					}
				}
				
			}
		}else{//否则就删除局点对应的设备信息
			//basMonitorConfigDao.deleteByBeidDeviceId(basDeviceConfig.getBeid(), basDeviceConfig.getDeviceId());
			basDeviceMonitorConfigDao.deleteDeviceMonitorConfig(basDeviceConfig.getBeid(), basDeviceConfig.getDeviceId(), null);
			basDeviceConfigDao.deleteDeviceConfig(basDeviceConfig.getBeid(), basDeviceConfig.getDeviceId(), null);
		}

	}
}
