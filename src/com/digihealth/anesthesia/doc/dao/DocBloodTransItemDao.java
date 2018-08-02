/*
 * DocBloodTransItemDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-07-21 Created
 */
package com.digihealth.anesthesia.doc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocBloodTransItem;
import com.digihealth.anesthesia.interfacedata.po.Blood;
@MyBatisDao
public interface DocBloodTransItemDao {
    int deleteByPrimaryKey(String id);

    int insert(DocBloodTransItem record);

    int insertSelective(DocBloodTransItem record);

    DocBloodTransItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocBloodTransItem record);

    int updateByPrimaryKey(DocBloodTransItem record);
    
    public List<DocBloodTransItem> queryBloodItemListByRegoptId(@Param("bloodTransId")String bloodTransId);
    
    public List<Blood> queryNotSendBloodItemListByRegoptId(@Param("bloodTransId")String bloodTransId, @Param("status")String status);
    
    int updateBloodItemState(@Param("id")String id);
}