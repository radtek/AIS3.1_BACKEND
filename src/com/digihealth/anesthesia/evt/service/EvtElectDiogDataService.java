/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.digihealth.anesthesia.evt.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtElectDiogData;

/**
 * 
     * Title: ElectDiogDataService.java    
     * Description:心电图数据
     * @author liukui       
     * @created 2015-10-7 下午6:00:54
 */
@Service
@Transactional(readOnly = true)
public class EvtElectDiogDataService extends BaseService {

	
	public List<EvtElectDiogData> searchElectDiogDataList(SearchFormBean searchBean) {
		return evtElectDiogDataDao.searchElectDiogDataList(searchBean);
	}
	@Transactional(readOnly = false)
	public void saveElectDiogData(@RequestBody EvtElectDiogData electDiogData) {
		String useType = StringUtils.isBlank(electDiogData.getUseType())==true?"1":electDiogData.getUseType();
		electDiogData.setUseType(useType);
		electDiogData.setOperaDate(new Date());
		evtElectDiogDataDao.delete(electDiogData);
		evtElectDiogDataDao.insert(electDiogData);
		
		DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(electDiogData.getDocId());
		
	}
	
	
	@Transactional(readOnly = false)
	public void insertOthereventHis(EvtElectDiogData electDiogData){
		evtElectDiogDataDao.insertElectDiogDataHis(electDiogData);
	}
	
	@Transactional(readOnly = false)
	public void deleteElectDiogData(EvtElectDiogData electDiogData){
		evtElectDiogDataDao.delete(electDiogData);
	}
}
