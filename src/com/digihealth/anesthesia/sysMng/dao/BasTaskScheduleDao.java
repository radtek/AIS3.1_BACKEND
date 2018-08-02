/*
 * BasTaskScheduleDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-12-12 Created
 */
package com.digihealth.anesthesia.sysMng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.sysMng.po.BasTaskSchedule;
@MyBatisDao
public interface BasTaskScheduleDao {
    int deleteByPrimaryKey(String id);

    int insert(BasTaskSchedule record);

    int insertSelective(BasTaskSchedule record);

    BasTaskSchedule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BasTaskSchedule record);

    int updateByPrimaryKey(BasTaskSchedule record);
    
    List<BasTaskSchedule> searchAllTaskSchedule(@Param("beid")String beid);
}