/*
 * DocSelfPayInstrumentItemDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-08-15 Created
 */
package com.digihealth.anesthesia.doc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentItem;
@MyBatisDao
public interface DocSelfPayInstrumentItemDao {
    int deleteByPrimaryKey(String id);

    int insert(DocSelfPayInstrumentItem record);

    int insertSelective(DocSelfPayInstrumentItem record);

    DocSelfPayInstrumentItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocSelfPayInstrumentItem record);

    int updateByPrimaryKey(DocSelfPayInstrumentItem record);
    
    List<DocSelfPayInstrumentItem> selectByAccedeId(@Param("accedeId")String accedeId);
    
    public void deleteByAccedeId(@Param("accedeId")String accedeId);
}