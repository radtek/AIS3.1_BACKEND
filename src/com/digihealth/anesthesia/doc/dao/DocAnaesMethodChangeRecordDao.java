/*
 * DocAnaesMethodChangeRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-03-13 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocAnaesMethodChangeRecord;

@MyBatisDao
public interface DocAnaesMethodChangeRecordDao {
    int deleteByPrimaryKey(String id);

    int insert(DocAnaesMethodChangeRecord record);

    int insertSelective(DocAnaesMethodChangeRecord record);

    DocAnaesMethodChangeRecord selectByPrimaryKey(String id);
    
    DocAnaesMethodChangeRecord selectByRegOptId(@Param("regOptId")String regOptId);

    int updateByPrimaryKeySelective(DocAnaesMethodChangeRecord record);

    int updateByPrimaryKey(DocAnaesMethodChangeRecord record);
}