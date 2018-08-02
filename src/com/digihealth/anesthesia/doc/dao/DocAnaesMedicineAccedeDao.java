package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocAnaesMedicineAccede;
@MyBatisDao
public interface DocAnaesMedicineAccedeDao {
    int deleteByPrimaryKey(String id);

    int insert(DocAnaesMedicineAccede record);

    int insertSelective(DocAnaesMedicineAccede record);

    DocAnaesMedicineAccede selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocAnaesMedicineAccede record);

    int updateByPrimaryKey(DocAnaesMedicineAccede record);
    
    DocAnaesMedicineAccede selectByRegOptId(@Param("regOptId")String regOptId);
}