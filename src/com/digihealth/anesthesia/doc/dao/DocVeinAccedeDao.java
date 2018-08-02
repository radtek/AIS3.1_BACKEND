package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocVeinAccede;

@MyBatisDao
public interface DocVeinAccedeDao {
    int deleteByPrimaryKey(String id);

    int insert(DocVeinAccede record);

    int insertSelective(DocVeinAccede record);

    DocVeinAccede selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DocVeinAccede record);

    int updateByPrimaryKey(DocVeinAccede record);
    
    DocVeinAccede searchByRegOptId(@Param("regOptId")String regOptId);
}