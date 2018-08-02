package com.digihealth.anesthesia.basedata.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.po.BasBloodDefination;
import com.digihealth.anesthesia.common.service.BaseService;
/**
 * 
     * Title: BloodDefinationService.java    
     * Description: 日志Service
     * @author liukui       
     * @created 2015-10-7 下午6:00:54
 */
@Service
@Transactional
public class BasBloodDefinationService extends BaseService {
	
	public List<BasBloodDefination> queryAllList() {
		return basBloodDefinationDao.queryAllList(getBeid());
	}
}
