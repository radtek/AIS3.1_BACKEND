/*
 * DocBloodTransRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-07-21 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocBloodTransRecord;
@MyBatisDao
public interface DocBloodTransRecordDao {
    int deleteByPrimaryKey(String bloodTransId);

    int insert(DocBloodTransRecord record);

    int insertSelective(DocBloodTransRecord record);

    DocBloodTransRecord selectByPrimaryKey(String bloodTransId);

    int updateByPrimaryKeySelective(DocBloodTransRecord record);

    int updateByPrimaryKey(DocBloodTransRecord record);
    
    DocBloodTransRecord searchOptBloodTransRecordByRegOptId(@Param("regOptId")String regOptId);
}