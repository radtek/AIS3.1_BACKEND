/*
 * TmpPacuLiquidTempDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-07-24 Created
 */
package com.digihealth.anesthesia.tmp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SearchLiquidTempFormBean;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.tmp.po.TmpPacuLiquidTemp;
@MyBatisDao
public interface TmpPacuLiquidTempDao {
    int deleteByPrimaryKey(String tempId);

    int insert(TmpPacuLiquidTemp record);

    int insertSelective(TmpPacuLiquidTemp record);

    TmpPacuLiquidTemp selectByPrimaryKey(String tempId);

    int updateByPrimaryKeySelective(TmpPacuLiquidTemp record);

    int updateByPrimaryKey(TmpPacuLiquidTemp record);
    
    List<TmpPacuLiquidTemp> selectLiquidTempByFormBean(@Param("filters")List<Filter> filters,@Param("searchLiquidTempFormBean")SearchLiquidTempFormBean searchLiquidTempFormBean);

    int selectLiquidTempTotalByFormBean(@Param("filters")List<Filter> filters,@Param("searchLiquidTempFormBean")SearchLiquidTempFormBean searchLiquidTempFormBean);
}