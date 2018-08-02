/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.digihealth.anesthesia.evt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digihealth.anesthesia.common.persistence.CrudDao;
import com.digihealth.anesthesia.common.persistence.annotation.MyBatisDao;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtElectDiogData;


/**
 * 
     * Title: ElectDiogDataDao.java    
     * Description: 心电图数据
     * @author liukui       
     * @created 2015-10-7 下午5:54:49
 */
@MyBatisDao
public interface EvtElectDiogDataDao extends CrudDao<EvtElectDiogData> {
	
	public List<EvtElectDiogData> searchElectDiogDataList(@Param("searchBean")SearchFormBean searchBean);
	
	public void insertElectDiogDataHis(@Param("electDiogData")EvtElectDiogData electDiogData);
	
}
