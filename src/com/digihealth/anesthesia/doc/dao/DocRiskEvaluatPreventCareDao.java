package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocRiskEvaluatPreventCare;

@MyBatisDao
public interface DocRiskEvaluatPreventCareDao {
    int deleteByPrimaryKey(String id);

    int insert(DocRiskEvaluatPreventCare record);

    int insertSelective(DocRiskEvaluatPreventCare record);

    DocRiskEvaluatPreventCare selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocRiskEvaluatPreventCare record);

    int updateByPrimaryKey(DocRiskEvaluatPreventCare record);
    
    DocRiskEvaluatPreventCare searchByRegOptId(@Param("regOptId")String regOptId);
}