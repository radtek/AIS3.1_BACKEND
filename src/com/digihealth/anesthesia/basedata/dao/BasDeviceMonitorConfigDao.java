/*
 * BasDeviceMonitorConfigDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-31 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.BasDeviceMonitorConfigControlBean;
import com.digihealth.anesthesia.basedata.formbean.BasDeviceMonitorConfigFormBean;
import com.digihealth.anesthesia.basedata.po.BasBusEntity;
import com.digihealth.anesthesia.basedata.po.BasDeviceMonitorConfig;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface BasDeviceMonitorConfigDao {
    int deleteByPrimaryKey(BasDeviceMonitorConfig record);

    int insert(BasDeviceMonitorConfig record);

    int insertSelective(BasDeviceMonitorConfig record);

    BasDeviceMonitorConfig selectByPrimaryKey(BasDeviceMonitorConfig record);

    int updateByPrimaryKeySelective(BasDeviceMonitorConfig record);

    int update(BasDeviceMonitorConfig record);
    
    public List<BasDeviceMonitorConfigFormBean> getDeviceMonitorConfigList(@Param("beid") String beid,@Param("deviceId") String deviceId,@Param("optional") String optional,@Param("roomId") String roomId);
    
    public List<BasDeviceMonitorConfigControlBean> getControlRoomDeviceMonitorConfigByBeid(@Param("beid") String beid,@Param("deviceId") String deviceId,@Param("roomId") String roomId);
    
    public List<BasDeviceMonitorConfig> getRoomDeviceMonitorConfigByBeid(@Param("beid") String beid,@Param("deviceId") String deviceId,@Param("roomId") String roomId);
    
    int deleteDeviceMonitorConfig(@Param("beid") String beid,@Param("deviceId") String deviceId,@Param("roomId") String roomId);
    
    public List<BasBusEntity> queryBusEntityListByDeviceIdAndEventId(@Param("deviceId") String deviceId,@Param("eventId") String eventId); 
    
    public List<BasDeviceMonitorConfig> queryRoomListByBeid(@Param("beid") String beid);
    
}