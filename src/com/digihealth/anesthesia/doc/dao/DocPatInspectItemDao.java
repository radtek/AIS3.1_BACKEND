/*
 * DocPatInspectItemDao.java
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
import com.digihealth.anesthesia.doc.po.DocPatInspectItem;
import com.digihealth.anesthesia.evt.formbean.Filter;
@MyBatisDao
public interface DocPatInspectItemDao {
    int deleteByPrimaryKey(String id);

    int insert(DocPatInspectItem record);

    int insertSelective(DocPatInspectItem record);

    DocPatInspectItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocPatInspectItem record);

    int updateByPrimaryKey(DocPatInspectItem record);
    
    List<DocPatInspectItem> getPatInspectItemList(@Param("searchFormBean") SystemSearchFormBean searchFormBean,@Param("filters") List<Filter> filters);
    
    List<DocPatInspectItem> queryRecordByInspectId(@Param("regOptId")String regOptId,@Param("inspectId")String inspectId);
    
    List<DocPatInspectItem> queryBloodTypeByRegOptId(@Param("regOptId")String regOptId);
    
    List<DocPatInspectItem> queryItemByInstruction(@Param("regOptId")String regOptId, @Param("instruction")String instruction);
}