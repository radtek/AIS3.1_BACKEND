/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.digihealth.anesthesia.tmp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.tmp.po.TmpChargePayTemp;

/**
 * 
     * Title: ChargePayTempService.java    
     * Description: 收费项目模板明细Service
     * @author        
 */
@Service
@Transactional(readOnly = true)
public class TmpChargePayTempService extends BaseService {
	
	/**
	 * 删除收费项目模板明细数据
	 */
	@Transactional(readOnly =false)
	public void deleteByChargePayId(TmpChargePayTemp chargePayTemp){
		if(StringUtils.isNotBlank(chargePayTemp.getChargePayTempId())){
			tmpChargePayTempDao.deleteByPrimaryKey(chargePayTemp.getChargePayTempId());
		}
	}
	
	/**
	 * 修改收费项目模板明细数据
	 */
	@Transactional(readOnly =false)
	public void updateChargePayTemp(TmpChargePayTemp chargePayTemp){
		tmpChargePayTempDao.updateByPrimaryKeySelective(chargePayTemp);
	}
	
}
