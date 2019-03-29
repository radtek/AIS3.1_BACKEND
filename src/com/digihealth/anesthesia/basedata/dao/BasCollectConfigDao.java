/*
 * BasCollectConfigDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-06-28 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.po.BasCollectConfig;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface BasCollectConfigDao {
    int deleteByPrimaryKey(@Param("roomId") String roomId, @Param("beid") String beid);

    int insert(BasCollectConfig record);

    int insertSelective(BasCollectConfig record);

    BasCollectConfig selectByPrimaryKey(@Param("roomId") String roomId, @Param("beid") String beid);

    int updateByPrimaryKeySelective(BasCollectConfig record);

    int updateByPrimaryKey(BasCollectConfig record);
}