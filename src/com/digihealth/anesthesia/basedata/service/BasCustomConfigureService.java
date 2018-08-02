package com.digihealth.anesthesia.basedata.service;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digihealth.anesthesia.basedata.po.BasCustomConfigure;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;

@Service
public class BasCustomConfigureService extends BaseService {
	
	public List<BasCustomConfigure> searchBasCustomConfigureList(BasCustomConfigure record) {
		return basCustomConfigureDao.searchBasCustomConfigureList(record,getBeid());
	}
	
	@Transactional
	public void saveBasCustomConfigure(BasCustomConfigure basCustomConfigure){
		if(StringUtils.isBlank(basCustomConfigure.getConfigureId())){
			basCustomConfigure.setBeid(getBeid());
			basCustomConfigure.setConfigureId(GenerateSequenceUtil.generateSequenceNo());
			basCustomConfigureDao.insertSelective(basCustomConfigure);
		}else{
			basCustomConfigureDao.updateByPrimaryKeyWithBLOBs(basCustomConfigure);
		}
	}

}
