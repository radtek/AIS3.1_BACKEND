/*
 * BasConsumablesLoseRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-06-13 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.BasConsumablesLoseRecordFormBean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasConsumablesLoseRecord;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;

@MyBatisDao
public interface BasConsumablesLoseRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BasConsumablesLoseRecord record);

    int insertSelective(BasConsumablesLoseRecord record);

    BasConsumablesLoseRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasConsumablesLoseRecord record);

    int updateByPrimaryKey(BasConsumablesLoseRecord record);
    
    //通过取药id逻辑删除所有报损记录
    int updateEnableByOutRecordId(@Param("outRecordId")Integer outRecordId);
    
    public List<BasConsumablesLoseRecordFormBean> queryConsumablesLoseRecordList(@Param("filters")List<Filter> filters,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    int queryConsumablesLoseRecordListTotal(@Param("filters")List<Filter> filters);
    
    public Integer queryConsumablesLoseRecordForLine(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("beid")String beid);
    
    public Integer queryConsumablesLoseRecordByReason(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("reason")String reason,@Param("beid")String beid);
}