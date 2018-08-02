/*
 * BasPriceDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-31 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasPrice;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface BasPriceDao {
    int deleteByPrimaryKey(String priceId);

    int deleteByBeid(@Param("beid")String beid);
    
    int insert(BasPrice record);

    int insertSelective(BasPrice record);

    BasPrice selectByPrimaryKey(String priceId);

    int updateByPrimaryKeySelective(BasPrice record);

    int update(BasPrice record);
    
    public List<BasPrice> searchPriceList(@Param("baseQuery") BaseInfoQuery baseQuery); 
    
    public BasPrice queryPriceByPriceId(@Param("priceId") String priceId); 
    
    public List<BasPrice> queryPriceList(@Param("filter")String filter,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);

    public int queryPriceListTotal(@Param("filter")String filter,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    public BasPrice selectByCode(@Param("code")String code, @Param("beid")String beid);
    
	public List<BasPrice> selectByCodeFirmId(@Param("code")String code,@Param("firmId")String firmId, @Param("beid")String beid);
    
    public int updateEnable(@Param("beid")String beid);
}