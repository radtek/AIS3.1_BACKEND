/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.digihealth.anesthesia.basedata.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.ApplyMonitorFormBean;
import com.digihealth.anesthesia.basedata.formbean.BasDeviceMonitorConfigControlBean;
import com.digihealth.anesthesia.basedata.formbean.BasDeviceMonitorConfigFormBean;
import com.digihealth.anesthesia.basedata.po.BasBusEntity;
import com.digihealth.anesthesia.basedata.po.BasDeviceMonitorConfig;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.BeanHelper;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfig;

/**
 * 
 * Title: DeviceConfigService.java Description: 设备型号Service
 * 
 * @author liukui
 * @created 2015-10-7 下午6:00:54
 */
@Service
public class BasDeviceMonitorConfigService extends BaseService {
	/**
	 * 根据房间号、设备id获取设备可采集项
	 * @param deviceId
	 * @return
	 */
	public List<BasDeviceMonitorConfigFormBean> getDeviceMonitorConfigList(String deviceId,String optional) {
		return basDeviceMonitorConfigDao.getDeviceMonitorConfigList(getBeid(),deviceId,optional,getCurRoomId(null));
	}
	
	/**
	 * 根据房间号、设备id获取设备可采集项
	 * @param deviceId
	 * @return
	 */
	public List<BasDeviceMonitorConfigControlBean> getControlRoomDeviceMonitorConfigByBeid(String beid,String deviceId,String roomId) {
		if(StringUtils.isEmpty(roomId)){
			roomId = "0";
		}
		return basDeviceMonitorConfigDao.getControlRoomDeviceMonitorConfigByBeid(beid,deviceId,roomId);
	}

	/**
	 * 修改床旁设备配置监测项
	 * @param Device
	 */
	@Transactional
	public void updateDeviceMonitorConfig(BasDeviceMonitorConfig deviceMonitorConfig) {
	    if (StringUtils.isBlank(deviceMonitorConfig.getBeid()))
	    {
	        deviceMonitorConfig.setBeid(getBeid());
	    }
	    
		basDeviceMonitorConfigDao.updateByPrimaryKeySelective(deviceMonitorConfig);
	}
	
	@Transactional
	public void bindDeviceMonitorConfig(Map map) {
		BasDeviceMonitorConfig deviceMonitorConfig = new BasDeviceMonitorConfig();
    	deviceMonitorConfig.setBeid(map.get("beid").toString());
    	deviceMonitorConfig.setDeviceId(map.get("deviceId").toString());
    	deviceMonitorConfig.setEventId(map.get("eventId").toString());
    	deviceMonitorConfig.setRoomId(map.get("roomId").toString());
    	
	    if (null != map.get("checked") && "1".equals(map.get("checked").toString()))
	    {
	    	basDeviceMonitorConfigDao.insert(deviceMonitorConfig);
	    }else{
	    	basDeviceMonitorConfigDao.deleteByPrimaryKey(deviceMonitorConfig);
	    }
		
	}
	
	public List<BasBusEntity> queryBusEntityListByDeviceIdAndEventId(BasDeviceMonitorConfig deviceMonitorConfig) {
		return basDeviceMonitorConfigDao.queryBusEntityListByDeviceIdAndEventId(deviceMonitorConfig.getDeviceId(),deviceMonitorConfig.getEventId());
	}
	
	@Transactional
	public void applyMonitorByBeid(ApplyMonitorFormBean applyMonitorFormBean) {
		
		if(StringUtils.isNotBlank(applyMonitorFormBean.getBeidLs())){
			String[] beidLs = applyMonitorFormBean.getBeidLs().split(",");
			for (int i = 0; i < beidLs.length; i++) {
				List<BasDeviceMonitorConfig> devList = basDeviceMonitorConfigDao.queryRoomListByBeid(beidLs[i]);
				if(null!=devList && devList.size()>0){
					for (BasDeviceMonitorConfig basDeviceMonitorConfig : devList) {
						BeanHelper.copyProperties(applyMonitorFormBean, basDeviceMonitorConfig);
						basDeviceMonitorConfig.setBeid(beidLs[i]);
						basDeviceMonitorConfigDao.insertSelective(basDeviceMonitorConfig);
					}
				}
				BasMonitorConfig monitor = basMonitorConfigDao.selectByPrimaryKey(applyMonitorFormBean.getEventId(), beidLs[i], "0");	
				if(monitor==null){
					monitor = basMonitorConfigDao.selectByPrimaryKey(applyMonitorFormBean.getEventId(), "0", "0");
					monitor.setBeid(beidLs[i]);
			    	basMonitorConfigDao.insertSelective(monitor);
				}
				
			}
		}
	}
}
