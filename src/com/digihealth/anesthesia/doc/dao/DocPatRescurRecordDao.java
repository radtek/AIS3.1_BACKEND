package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocPatRescurRecord;

@MyBatisDao
public interface DocPatRescurRecordDao {
    int deleteByPrimaryKey(String id);

    int insert(DocPatRescurRecord record);

    int insertSelective(DocPatRescurRecord record);

    DocPatRescurRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocPatRescurRecord record);

    int updateByPrimaryKey(DocPatRescurRecord record);
    
    DocPatRescurRecord selectByRegOptId(@Param("regOptId")String regOptId);
}