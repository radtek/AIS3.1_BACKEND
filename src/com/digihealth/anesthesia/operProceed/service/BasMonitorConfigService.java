package com.digihealth.anesthesia.operProceed.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.BasDeviceSpecificationFormBean;
import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.operProceed.formbean.BasAnaesMonitorConfigFormBean;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfig;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfigDefault;

@Service
public class BasMonitorConfigService extends BaseService{
	
	public List<BasMonitorConfig> selectMustShowList(String regOptId){
		return basMonitorConfigDao.selectMustShowList(getBeid(),getCurRoomId(regOptId));
	}
	
	public List<BasMonitorConfig> selectWithoutMustShowList(String regOptId){
		return basMonitorConfigDao.selectWithoutMustShowList(getBeid(),getCurRoomId(regOptId));
	}
	
	public List<BasMonitorConfig> queryAllMonitorConfig(String regOptId){
		List<BasMonitorConfig> list = basMonitorConfigDao.queryAllMonitorConfig(getBeid(),getCurRoomId(regOptId));
		// 去除O2的监测项
		if(null != list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				BasMonitorConfig mc = list.get(i);
				if("91001".equals(mc.getEventId())){
					list.remove(mc);
				}
			}
		}
		return list;
	}
	
	
	public List<BasMonitorConfig> queryBasMonitorConfigList(SystemSearchFormBean systemSearchFormBean){
		
		if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
			systemSearchFormBean.setSort("eventId");
		}
		if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
			systemSearchFormBean.setOrderBy("ASC");
		}
		List<Filter> filters = systemSearchFormBean.getFilters();
		
		List<BasMonitorConfig> list = basMonitorConfigDao.queryBasMonitorConfigList(filters,systemSearchFormBean);
		
		return list;
	}
	
	public Integer queryBasMonitorConfigListTotal(SystemSearchFormBean systemSearchFormBean){
		List<Filter> filters = systemSearchFormBean.getFilters();
		return  basMonitorConfigDao.queryBasMonitorConfigListTotal(filters,systemSearchFormBean);
	}
	
	
	
	
	@Transactional
	public void updMonitorConfig(List<BasMonitorConfig> monitorConfigList) {
		for (BasMonitorConfig monitorConfig : monitorConfigList) {
			basMonitorConfigDao.updateByPrimaryKeySelective(monitorConfig);
		}
	}
	
	@Transactional
	public void insertBasMonitorConfig(BasMonitorConfig basMonitorConfig) {
		
		if(StringUtils.isNotBlank(basMonitorConfig.getEventId())){
			basMonitorConfigDao.updateByPrimaryKey(basMonitorConfig);
		}else{
			String eventId = basMonitorConfigDao.getMaxEventId(basMonitorConfig.getPosition(),basMonitorConfig.getDeviceType());
			if(StringUtils.isEmpty(eventId)){
				eventId = basMonitorConfig.getDeviceType()+(basMonitorConfig.getPosition()+1)+"001";
			}else{
				basMonitorConfig.setEventId(eventId);
			}
			basMonitorConfigDao.insert(basMonitorConfig);
		}
		
	}
	
	
	@Transactional
	public void batUpdateMonitorConfig(BasMonitorConfig monitorConfig) {
		List<Filter> filters = new ArrayList<Filter>();
		Filter f = new Filter();
		f.setField("beid");
		f.setValue(monitorConfig.getBeid());
		filters.add(f);
		f = new Filter();
		f.setField("eventId");
		f.setValue(monitorConfig.getEventId());
		filters.add(f);
	
		SystemSearchFormBean systemSearchFormBean = new SystemSearchFormBean();
		systemSearchFormBean.setOrderBy("asc");
		systemSearchFormBean.setSort("eventId");
		
		List<BasMonitorConfig> monitoList = basMonitorConfigDao.queryBasMonitorConfigList(filters, systemSearchFormBean);
		
		for (BasMonitorConfig monitor : monitoList) {
			basMonitorConfigDao.updateByPrimaryKeySelective(monitor);
		}
	}
	
	public List<BasMonitorConfig> selectMustShowListWithoutO2(String regOptId) {
		List<BasMonitorConfig> list = basMonitorConfigDao.selectMustShowList(getBeid(),getCurRoomId(regOptId));
		// 去除O2的监测项
		if(null != list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				BasMonitorConfig mc = list.get(i);
				if("91001".equals(mc.getEventId())){
					list.remove(mc);
				}
			}
		}
		return list;
	}
	
	
	public BasMonitorConfig selectByEventId(String eventId,String regOptId){
		return basMonitorConfigDao.selectByPrimaryKey(eventId, getBeid(),getCurRoomId(regOptId));
	}
	
	public String selectEventIdByEventName(BasAnaesMonitorConfigFormBean anaesMonitorConfigFormBean,String regOptId)
	{
	    List<String> strList = basMonitorConfigDao.selectEventIdByEventName(anaesMonitorConfigFormBean.getEventName(), getBeid(),getCurRoomId(regOptId));
	    BasMonitorConfigDefault monitorConfigDefault = basMonitorConfigDefaultDao.selectByEventName(anaesMonitorConfigFormBean.getEventName(), getBeid());

	    //如果b_anaes_monitor_config中配置的realEventId为已启用设备，则直接返回即可
	    //如果b_anaes_monitor_config中配置的realEventId为未启用设备，则返回启用设备中的第一个即可
	    //如果没有设备启用，返回默认eventId
        if (null != strList && strList.size() > 0)
        {
            for (String str : strList)
            {
                if (str.equals(anaesMonitorConfigFormBean.getRealEventId()))
                {
                    return str;
                }
            }
            if (strList.size() > 1 && strList.contains(monitorConfigDefault.getDefaultEventId()))
            {
                return monitorConfigDefault.getDefaultEventId();
            }
            else
            {
                return strList.get(0);
            }
            
        }
	    return monitorConfigDefault.getDefaultEventId();
	}
	
}
