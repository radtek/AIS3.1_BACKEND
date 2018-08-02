/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.digihealth.anesthesia.tmp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.tmp.po.TmpMedPayTemp;

/**
 * 
     * Title: MedChargeTempService.java    
     * Description: 药品模板明细Service
     * @author        
 */
@Service
@Transactional(readOnly = true)
public class TmpMedPayTempService extends BaseService {
	
	/**
	 * 删除药品模板明细数据
	 */
	@Transactional(readOnly =false)
	public void deleteByMedChargeId(TmpMedPayTemp tmpMedPayTemp){
		if(StringUtils.isNotBlank(tmpMedPayTemp.getMedPayTempId())){
			tmpMedPayTempDao.deleteByPrimaryKey(tmpMedPayTemp.getMedPayTempId());
		}
	}
	
	/**
	 * 修改药品模板明细数据
	 */
	@Transactional(readOnly =false)
	public void updateMedChargeTemp(TmpMedPayTemp tmpMedPayTemp){
		tmpMedPayTempDao.updateByPrimaryKeySelective(tmpMedPayTemp);
	}
	
}
