/*
 * EvtAnaesModifyRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-10-27 Created
 */
package com.digihealth.anesthesia.evt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.evt.formbean.SearchConditionFormBean;
import com.digihealth.anesthesia.evt.po.EvtAnaesModifyRecord;

@MyBatisDao
public interface EvtAnaesModifyRecordDao {
    int deleteByPrimaryKey(String id);

    int insert(EvtAnaesModifyRecord record);

    int insertSelective(EvtAnaesModifyRecord record);

    EvtAnaesModifyRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EvtAnaesModifyRecord record);

    int updateByPrimaryKey(EvtAnaesModifyRecord record);
    
    List<EvtAnaesModifyRecord> queryEvtAnaesModifyRecordList(@Param("searchFormBean") SearchConditionFormBean searchFormBean,@Param("filters") List<Filter> filters);
    
    int queryCountEvtAnaesModifyRecordList(@Param("filters") List<Filter> filters);
}