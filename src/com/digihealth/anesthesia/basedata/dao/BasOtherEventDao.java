/*
 * BasOtherEventDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-01-05 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasOtherEvent;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface BasOtherEventDao {
    int deleteByPrimaryKey(String id);

    int insert(BasOtherEvent record);

    int insertSelective(BasOtherEvent record);

    BasOtherEvent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BasOtherEvent record);

    int updateByPrimaryKey(BasOtherEvent record);
    
    List<BasOtherEvent> selectAllOtherEvent(@Param("filter")String filter,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    int selectOtherEventTotal(@Param("filter")String filter,@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
}