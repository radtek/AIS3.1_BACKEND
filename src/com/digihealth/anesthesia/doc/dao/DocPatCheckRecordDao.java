/*
 * DocPatCheckRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-07-24 Created
 */
package com.digihealth.anesthesia.doc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocPatCheckRecord;
import com.digihealth.anesthesia.evt.formbean.Filter;
@MyBatisDao
public interface DocPatCheckRecordDao {
    int deleteByPrimaryKey(String id);

    int insert(DocPatCheckRecord record);

    int insertSelective(DocPatCheckRecord record);

    DocPatCheckRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocPatCheckRecord record);

    int updateByPrimaryKey(DocPatCheckRecord record);
    
    List<DocPatCheckRecord> getPatCheckRecordList(@Param("searchFormBean") SystemSearchFormBean searchFormBean,@Param("filters") List<Filter> filters);
    
    int getTotalPatCheckRecordList(@Param("filters") List<Filter> filters);
    
    DocPatCheckRecord selectRecordByCheckId(@Param("regOptId")String regOptId,@Param("checkId")String checkId);
}