/*
 * DocPatInspectRecordDao.java
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
import com.digihealth.anesthesia.doc.po.DocPatInspectRecord;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.inspect.formbean.PatInspectRecordFormbean;
@MyBatisDao
public interface DocPatInspectRecordDao {
    int deleteByPrimaryKey(String id);

    int insert(DocPatInspectRecord record);

    int insertSelective(DocPatInspectRecord record);

    DocPatInspectRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocPatInspectRecord record);

    int updateByPrimaryKey(DocPatInspectRecord record);
    
    List<DocPatInspectRecord> getPatInspectRecordList(@Param("searchFormBean") SystemSearchFormBean searchFormBean,@Param("filters") List<Filter> filters);
    
    int getTotalPatInspectRecordList(@Param("filters") List<Filter> filters);
    
    List<PatInspectRecordFormbean> getRegInfoListByPatInspect(@Param("searchFormBean") SystemSearchFormBean searchFormBean,@Param("filters") List<Filter> filters);
    
    int getTotalRegInfoListByPatInspect(@Param("filters") List<Filter> filters);
    
    DocPatInspectRecord selectRecordByInspectId(@Param("regOptId")String regOptId,@Param("inspectId")String inspectId);
}