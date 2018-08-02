/*
 * DocAnalgesicPostflupDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-30 Created
 */
package com.digihealth.anesthesia.doc.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocAnalgesicPostflup;
@MyBatisDao
public interface DocAnalgesicPostflupDao {
    int deleteByPrimaryKey(String id);

    int insert(DocAnalgesicPostflup record);

    int insertSelective(DocAnalgesicPostflup record);

    DocAnalgesicPostflup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocAnalgesicPostflup record);

    int updateByPrimaryKey(DocAnalgesicPostflup record);
    
    List<DocAnalgesicPostflup> getByanalgesicId(@Param("analgesicId") String analgesicId);
    
    int deleteByanalgesicId(@Param("analgesicId") String analgesicId);
}