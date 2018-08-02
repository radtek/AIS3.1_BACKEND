/*
 * DocDocordRecordDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-07-21 Created
 */
package com.digihealth.anesthesia.doc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocDocordRecord;
@MyBatisDao
public interface DocDocordRecordDao {
    int deleteByPrimaryKey(String docRecordId);

    int insert(DocDocordRecord record);

    int insertSelective(DocDocordRecord record);

    DocDocordRecord selectByPrimaryKey(String docRecordId);

    int updateByPrimaryKeySelective(DocDocordRecord record);

    int updateByPrimaryKey(DocDocordRecord record);
    
    List<DocDocordRecord> queryListByHidGroupId(@Param("hid")String hid,@Param("groupId")String groupId,@Param("jlxh")String jlxh);
    
    List<DocDocordRecord> queryDocordRecordListByRegOptId(@Param("regOptId")String regOptId);
}