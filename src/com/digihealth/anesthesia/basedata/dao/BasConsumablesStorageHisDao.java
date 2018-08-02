/*
 * BasConsumablesStorageHisDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-06-28 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import com.digihealth.anesthesia.basedata.po.BasConsumablesStorageHis;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface BasConsumablesStorageHisDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BasConsumablesStorageHis record);

    int insertSelective(BasConsumablesStorageHis record);

    BasConsumablesStorageHis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasConsumablesStorageHis record);

    int updateByPrimaryKey(BasConsumablesStorageHis record);
}