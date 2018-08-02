/*
 * BasConsumablesStorageDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-06-13 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.BasChargeAmountDetailRecordFormBean;
import com.digihealth.anesthesia.basedata.formbean.BasConsumablesOutRecordFormBean;
import com.digihealth.anesthesia.basedata.formbean.BasConsumablesStorageFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasConsumablesStorage;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;

@MyBatisDao
public interface BasConsumablesStorageDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BasConsumablesStorage record);

    int insertSelective(BasConsumablesStorage record);

    BasConsumablesStorage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasConsumablesStorage record);

    int updateByPrimaryKey(BasConsumablesStorage record);
    
    BasConsumablesStorage selectConsumablesByMFSB(@Param("instrumentId")String instrumentId,@Param("batch")String batch);
    
    List<BasConsumablesStorage> queryConsumablesListGroupByNFS(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    int queryConsumablesListGroupByNFSTotal(@Param("filters")List<Filter> filters);
    
    List<BasConsumablesStorage> queryConsumablesList(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    int queryConsumablesListTotal(@Param("filters")List<Filter> filters);
    
    List<BasConsumablesStorageFormbean> queryConsumablesByMonth(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean,@Param("queryMonth")String queryMonth);
    
    int queryConsumablesByMonthTotal(@Param("filters")List<Filter> filters);
    
    List<BasConsumablesOutRecordFormBean> queryConsumablesByPersonal(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    int queryConsumablesByPersonalTotal(@Param("filters")List<Filter> filters);
    
    List<BasConsumablesStorage> queryConsumablesStorageByBeid(@Param("beid")String beid);
    
    List<BasChargeAmountDetailRecordFormBean> queryChargeAmountDetailByChargeId(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    List<BasConsumablesOutRecordFormBean> cntInstrumentUseByOperat(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    int cntInstrumentUseByOperatTotal(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
}