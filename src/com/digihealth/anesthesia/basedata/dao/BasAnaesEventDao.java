/*
 * BasAnaesEventDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-11-20 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesEvent;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface BasAnaesEventDao {
    int deleteByPrimaryKey(String id);

    int insert(BasAnaesEvent record);

    int insertSelective(BasAnaesEvent record);

    BasAnaesEvent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BasAnaesEvent record);

    int updateByPrimaryKey(BasAnaesEvent record);
    
    List<BasAnaesEvent> selectAllAnaesEvent(@Param("filter")String filter,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    int selectAnaesEventTotal(@Param("filter")String filter,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    Integer selectMaxEventValue(@Param("beid")String beid);
    
    List<SysCodeFormbean> selectAnaesEventByCode(@Param("code")String code, @Param("beid")String beid);
    
    List<BasAnaesEvent> findAllAnaesEvent(@Param("baseQuery")BaseInfoQuery baseQuery);
    
    //常用麻醉事件不包括1、2、4、5、8、9
    List<BasAnaesEvent> queryCommonUseList(@Param("baseQuery")BaseInfoQuery baseQuery);
}