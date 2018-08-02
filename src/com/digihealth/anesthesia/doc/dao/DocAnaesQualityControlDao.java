/*
 * DocAnaesQualityControlDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-06-05 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocAnaesQualityControl;

@MyBatisDao
public interface DocAnaesQualityControlDao {
    int deleteByPrimaryKey(String id);

    int insert(DocAnaesQualityControl record);

    int insertSelective(DocAnaesQualityControl record);

    DocAnaesQualityControl selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocAnaesQualityControl record);

    int updateByPrimaryKey(DocAnaesQualityControl record);
    
    DocAnaesQualityControl selectAnaesQualityControlByRegOptId(@Param("regOptId")String regOptId);
}