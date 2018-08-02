/*
 * TmpChargePayTempDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-05-11 Created
 */
package com.digihealth.anesthesia.tmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocPackagesItem;
import com.digihealth.anesthesia.tmp.po.TmpChargePayTemp;
@MyBatisDao
public interface TmpChargePayTempDao {
    int deleteByPrimaryKey(String chargePayTempId);
    
    int deleteByChargeMedTempId(@Param("chargeMedTempId")String chargeMedTempId);

    int insert(TmpChargePayTemp record);

    int insertSelective(TmpChargePayTemp record);

    TmpChargePayTemp selectByPrimaryKey(String chargePayId);

    int updateByPrimaryKeySelective(TmpChargePayTemp record);

    int updateByPrimaryKey(TmpChargePayTemp record);
    
    List<DocPackagesItem> queryItemListByChargeMedTempId(@Param("chargeMedTempId")String chargeMedTempId);
    
    List<TmpChargePayTemp> searchChargePayListById(@Param("chargeMedTempId")String chargeMedTempId,@Param("chargedType")String chargedType);
}