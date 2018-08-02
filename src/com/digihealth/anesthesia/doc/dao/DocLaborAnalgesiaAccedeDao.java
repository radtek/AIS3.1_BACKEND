/*
 * DocAccedeDao.java
 * Copyright(C) 2016 digihealth
 * All rights reserved.
 * ----------------------------------------
 * @author cy
 * 2017-03-30 Created
 */
package com.digihealth.anesthesia.doc.dao;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.doc.po.DocLaborAnalgesiaAccede;

@MyBatisDao
public interface DocLaborAnalgesiaAccedeDao {
    int deleteByPrimaryKey(String accedeId);

    int insert(DocLaborAnalgesiaAccede record);

    int insertSelective(DocLaborAnalgesiaAccede record);

    DocLaborAnalgesiaAccede selectByPrimaryKey(String accedeId);

    int updateByPrimaryKeySelective(DocLaborAnalgesiaAccede record);

    int updateByPrimaryKey(DocLaborAnalgesiaAccede record);

	/**
	 * 
	 * @discription 根据手术ID获取分娩镇痛同意书
	 * @author chengwang
	 * @created 2015-10-10 下午5:13:48
	 * @param regOptId
	 * @return
	 */
	public DocLaborAnalgesiaAccede searchLaborAccedeByRegOptId(@Param("regOptId") String regOptId);
	
	/**
	 * 
	     * @discription 更新分娩镇痛同意书
	     * @author chengwang       
	     * @created 2015-10-21 上午11:18:09     
	     * @param preVisit
	 */
	public void updateLaborAccede(DocLaborAnalgesiaAccede accede);
	
	/**
	 * 
	     * @discription 根据ID获取分娩镇痛同意书
	     * @author chengwang       
	     * @created 2015-10-21 上午11:18:52     
	     * @param id
	     * @return
	 */
	public DocLaborAnalgesiaAccede searchLaborAccedeById(@Param("id") String id);
}