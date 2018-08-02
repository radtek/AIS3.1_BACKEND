/*
 * DocAnaesPreDiscussRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-03-13 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocAnaesPreDiscussRecord;

@MyBatisDao
public interface DocAnaesPreDiscussRecordDao {
    int deleteByPrimaryKey(String preDiscussId);

    int insert(DocAnaesPreDiscussRecord record);

    int insertSelective(DocAnaesPreDiscussRecord record);

    DocAnaesPreDiscussRecord selectByPrimaryKey(String preDiscussId);

    DocAnaesPreDiscussRecord selectByRegOptId(@Param("regOptId")String regOptId);
    
    int updateByPrimaryKeySelective(DocAnaesPreDiscussRecord record);

    int updateByPrimaryKey(DocAnaesPreDiscussRecord record);
}