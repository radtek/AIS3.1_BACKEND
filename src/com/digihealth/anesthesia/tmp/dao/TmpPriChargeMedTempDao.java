/*
 * TmpChargeMedTempDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-05-11 Created
 */
package com.digihealth.anesthesia.tmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.MedicineFormBean;
import com.digihealth.anesthesia.basedata.formbean.SearchLiquidTempFormBean;
import com.digihealth.anesthesia.basedata.po.BasChargeItem;
import com.digihealth.anesthesia.basedata.po.BasDept;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.tmp.formbean.TmpChargeTempOptFormBean;
import com.digihealth.anesthesia.tmp.po.TmpPriChargeMedTemp;

@MyBatisDao
public interface TmpPriChargeMedTempDao {
    int deleteByPrimaryKey(String chargeMedTempId);

    int insert(TmpPriChargeMedTemp record);

    int insertSelective(TmpPriChargeMedTemp record);

    TmpPriChargeMedTemp selectByPrimaryKey(String chargeMedTempId);

    int updateByPrimaryKeySelective(TmpPriChargeMedTemp record);

    int updateByPrimaryKey(TmpPriChargeMedTemp record);
    
    List<TmpPriChargeMedTemp> queryChargeMedTempList(@Param("filters")List<Filter> filters,@Param("searchLiquidTempFormBean")SearchLiquidTempFormBean searchLiquidTempFormBean);

    int queryCountChargeMedTempList(@Param("filters")List<Filter> filters,@Param("searchLiquidTempFormBean")SearchLiquidTempFormBean searchLiquidTempFormBean);
    
    
    List<BasChargeItem> queryInvalidChargeItemList(@Param("beid")String beid);
    
    List<MedicineFormBean> queryInvalidMedOrPriceList(@Param("beid")String beid);
    
    //删除收费包模板表数据
    void batchDelChargePayInvalidData(@Param("chargeItemId")String chargeItemId,@Param("beid")String beid);
    
    //删除药品模板表数据
    void batchDelChargeMedInvalidData(@Param("medicineId")String medicineId,@Param("firmId")String firmId,@Param("beid")String beid);
    
    //替换收费模板收费项目详情数据
    void batchReplaceChargePayData(@Param("chargeTempOptFormBean")TmpChargeTempOptFormBean chargeTempOptFormBean,@Param("beid")String beid);
    
    //替换收费模板药品详情数据
    void batchReplaceChargeMedData(@Param("chargeTempOptFormBean")TmpChargeTempOptFormBean chargeTempOptFormBean,@Param("beid")String beid);
    
    
    List<BasChargeItem> queryChargeTempPayItemByPy(@Param("pinyin")String pinyin,@Param("beid")String beid);
    
    List<MedicineFormBean> queryChargeTempMedicineItemByPy(@Param("name")String name,@Param("beid")String beid);
    
    List<BasDept> queryRemarkByChargeTempList(@Param("tempType") String tempType,@Param("beid")String beid);
    
}