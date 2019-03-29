/*
 * TmpToxicOutstockTempDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-04-06 Created
 */
package com.digihealth.anesthesia.tmp.dao;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.digihealth.anesthesia.basedata.formbean.SearchDoctempFormBean;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.tmp.po.TmpToxicOutstockTemp;

@MyBatisDao
public interface TmpToxicOutstockTempDao {
    int deleteByPrimaryKey(String id);

    int insert(TmpToxicOutstockTemp record);

    int insertSelective(TmpToxicOutstockTemp record);

    TmpToxicOutstockTemp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TmpToxicOutstockTemp record);

    int updateByPrimaryKeyWithBLOBs(TmpToxicOutstockTemp record);

    int updateByPrimaryKey(TmpToxicOutstockTemp record);
    
    List<TmpToxicOutstockTemp> getToxicOutstockTempList(@Param("beid")String beid);
    
    List<TmpToxicOutstockTemp>  queryToxicStocktempByForbean(@Param("filters")List<Filter> filters,@Param("searchFormBean")SearchDoctempFormBean searchDoctempFormBean);
    
    int queryToxicStocktempTotalByForbean(@Param("filters")List<Filter> filters,@Param("searchFormBean")SearchDoctempFormBean searchDoctempFormBean);
    
}