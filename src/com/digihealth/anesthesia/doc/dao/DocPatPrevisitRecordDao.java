/*
 * DocPatPrevisitRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-08-27 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocPatPrevisitRecord;

@MyBatisDao
public interface DocPatPrevisitRecordDao {
    int deleteByPrimaryKey(String patVisitId);

    int insert(DocPatPrevisitRecord record);

    int insertSelective(DocPatPrevisitRecord record);

    DocPatPrevisitRecord selectByPrimaryKey(String patVisitId);

    int updateByPrimaryKeySelective(DocPatPrevisitRecord record);

    int updateByPrimaryKey(DocPatPrevisitRecord record);
    
    DocPatPrevisitRecord searchPatPrevisitRecordByRegOptId(@Param("regOptId")String regOptId);
}