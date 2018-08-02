/*
 * BasAnaesDoctorAmountDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2018-06-05 Created
 */
package com.digihealth.anesthesia.basedata.dao;

import java.util.List;

import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasAnaesDoctorAmount;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface BasAnaesDoctorAmountDao {
    int deleteByPrimaryKey(@Param("recordMonth") String recordMonth, @Param("beid") String beid);

    int insert(BasAnaesDoctorAmount record);

    int insertSelective(BasAnaesDoctorAmount record);

    BasAnaesDoctorAmount selectByPrimaryKey(@Param("recordMonth") String recordMonth, @Param("beid") String beid);

    int updateByPrimaryKeySelective(BasAnaesDoctorAmount record);

    int updateByPrimaryKey(BasAnaesDoctorAmount record);

    List<BasAnaesDoctorAmount> selectAllAnaesDoctorAmount(@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
    
    int selectAnaesDoctorAmountTotal(@Param("systemSearchFormBean")SystemSearchFormBean systemSearchFormBean);
}