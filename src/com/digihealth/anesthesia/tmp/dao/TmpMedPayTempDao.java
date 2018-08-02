/*
 * TmpMedPayTempDao.java
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
import com.digihealth.anesthesia.doc.po.DocEventBilling;
import com.digihealth.anesthesia.tmp.po.TmpMedPayTemp;

@MyBatisDao
public interface TmpMedPayTempDao {
    int deleteByPrimaryKey(String medPayTempId);
    
    int deleteByChargeTempId(@Param("chargeMedTempId")String chargeMedTempId);

    int insert(TmpMedPayTemp record);

    int insertSelective(TmpMedPayTemp record);

    TmpMedPayTemp selectByPrimaryKey(String medPayTempId);

    int updateByPrimaryKeySelective(TmpMedPayTemp record);

    int updateByPrimaryKey(TmpMedPayTemp record);
    
    List<TmpMedPayTemp> searchMedChargeListById(@Param("chargeMedTempId")String chargeMedTempId,@Param("chargedType")String chargedType);
    
    List<DocEventBilling> queryItemListByChargeTempId(@Param("chargeMedTempId")String chargeMedTempId);
}