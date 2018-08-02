/*
 * DocSelfPayInstrumentAccedeDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-08-15 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentAccede;
@MyBatisDao
public interface DocSelfPayInstrumentAccedeDao {
    int deleteByPrimaryKey(String id);

    int insert(DocSelfPayInstrumentAccede record);

    int insertSelective(DocSelfPayInstrumentAccede record);

    DocSelfPayInstrumentAccede selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocSelfPayInstrumentAccede record);

    int updateByPrimaryKey(DocSelfPayInstrumentAccede record);
    
    DocSelfPayInstrumentAccede selectByRegOptId(@Param("regOptId")String regOptId);
}