package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocDifficultCaseDiscuss;

@MyBatisDao
public interface DocDifficultCaseDiscussDao {
    int deleteByPrimaryKey(String id);

    int insert(DocDifficultCaseDiscuss record);

    int insertSelective(DocDifficultCaseDiscuss record);

    DocDifficultCaseDiscuss selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocDifficultCaseDiscuss record);

    int updateByPrimaryKey(DocDifficultCaseDiscuss record);
    
    DocDifficultCaseDiscuss selectByRegOptId(@Param("regOptId")String regOptId);
}