/*
 * DocAnalgesicInformedConsentDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-05-29 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocAnalgesicInformedConsent;

@MyBatisDao
public interface DocAnalgesicInformedConsentDao {
    int deleteByPrimaryKey(String analgesicId);

    int insert(DocAnalgesicInformedConsent record);

    int insertSelective(DocAnalgesicInformedConsent record);

    DocAnalgesicInformedConsent selectByPrimaryKey(String analgesicId);

    int updateByPrimaryKeySelective(DocAnalgesicInformedConsent record);

    int updateByPrimaryKey(DocAnalgesicInformedConsent record);
    
    DocAnalgesicInformedConsent selectInformedConsentByRegOptId(@Param("regOptId") String regOptID);
}