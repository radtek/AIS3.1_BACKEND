/*
 * BasConsumablesInRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-06-13 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasConsumablesInRecord;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;

@MyBatisDao
public interface BasConsumablesInRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BasConsumablesInRecord record);

    int insertSelective(BasConsumablesInRecord record);

    BasConsumablesInRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasConsumablesInRecord record);

    int updateByPrimaryKey(BasConsumablesInRecord record);
    
    List<BasConsumablesInRecord> queryConsumablesInRecordList(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    int queryConsumablesInRecordListTotal(@Param("filters")List<Filter> filters);
}