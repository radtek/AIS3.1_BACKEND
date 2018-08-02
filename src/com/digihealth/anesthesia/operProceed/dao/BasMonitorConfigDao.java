/*
 * BasMonitorConfigDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-04-11 Created
 */
package com.digihealth.anesthesia.operProceed.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfig;
import com.digihealth.anesthesia.operProceed.po.Observe;
import com.digihealth.anesthesia.research.formbean.LifeSignObserveFormBean;
@MyBatisDao
public interface BasMonitorConfigDao {
    int deleteByPrimaryKey(@Param("eventId") String eventId, @Param("beid") String beid, @Param("roomId") String roomId);
    
    int deleteByBeidDeviceId( @Param("beid") String beid, @Param("deviceId") String deviceId);

    int insert(BasMonitorConfig record);

    int insertSelective(BasMonitorConfig record);

    BasMonitorConfig selectByPrimaryKey(@Param("eventId") String eventId, @Param("beid") String beid, @Param("roomId") String roomId);

    int updateByPrimaryKeySelective(BasMonitorConfig record);

    int updateByPrimaryKey(BasMonitorConfig record);
    
    public List<BasMonitorConfig> selectMonitorListByDeviceId(@Param("beid") String beid,@Param("deviceId") String deviceId);
    
    public List<BasMonitorConfig> selectList(@Param("beid") String beid, @Param("roomId") String roomId);
    
    public List<BasMonitorConfig> selectMustShowList(@Param("beid") String beid, @Param("roomId") String roomId);
    
    public List<BasMonitorConfig> selectWithoutMustShowList(@Param("beid") String beid, @Param("roomId") String roomId);

    public List<BasMonitorConfig> queryAllMonitorConfig(@Param("beid") String beid, @Param("roomId") String roomId);

    List<LifeSignObserveFormBean> getLifeSignList(@Param("beid") String beid, @Param("roomId") String roomId);
    
    public List<String> selectEventIdByEventName(@Param("eventName") String eventName, @Param("beid") String beid, @Param("roomId") String roomId);
    
    /* 根据手术室ID，查询所有的需要采集的参数 */
	public List<Observe> searchAnaesObserveList(@Param("beid") String beid, @Param("operId")  String operId, @Param("roomId") String roomId);
	
	//查询所有监测项
	public List<Observe> searchAllAnaesObserveList(@Param("beid") String beid, @Param("roomId") String roomId);
	
	//根据指标位置获取对应指标id的最大值
	public String getMaxEventId(@Param("position") Integer position,@Param("deviceType") String deviceType);
	
	//获取所有采集指标
	public List<BasMonitorConfig> queryBasMonitorConfigList(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
	
	public int queryBasMonitorConfigListTotal(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
}