/*
 * DocPreAnaesTalkRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-07-31 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocPreAnaesTalkRecord;

@MyBatisDao
public interface DocPreAnaesTalkRecordDao {
    int deleteByPrimaryKey(String id);

    int insert(DocPreAnaesTalkRecord record);

    int insertSelective(DocPreAnaesTalkRecord record);

    DocPreAnaesTalkRecord selectByPrimaryKey(String id);
    
    DocPreAnaesTalkRecord selectByRegOptId(@Param("regOptId")String regOptId);

    int updateByPrimaryKeySelective(DocPreAnaesTalkRecord record);

    int updateByPrimaryKey(DocPreAnaesTalkRecord record);
}