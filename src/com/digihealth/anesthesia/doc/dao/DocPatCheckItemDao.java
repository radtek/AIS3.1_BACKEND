/*
 * DocPatCheckItemDao.java
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
import com.digihealth.anesthesia.doc.po.DocPatCheckItem;
import com.digihealth.anesthesia.evt.formbean.Filter;

@MyBatisDao
public interface DocPatCheckItemDao {
    int deleteByPrimaryKey(String id);

    int insert(DocPatCheckItem record);

    int insertSelective(DocPatCheckItem record);

    DocPatCheckItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocPatCheckItem record);

    int updateByPrimaryKeyWithBLOBs(DocPatCheckItem record);

    int updateByPrimaryKey(DocPatCheckItem record);
    
    List<DocPatCheckItem> getPatCheckItemList(@Param("searchFormBean") SystemSearchFormBean searchFormBean,@Param("filters") List<Filter> filters);
    
    List<DocPatCheckItem> queryRecordByCheckId(@Param("regOptId")String regOptId,@Param("checkId")String checkId);
}