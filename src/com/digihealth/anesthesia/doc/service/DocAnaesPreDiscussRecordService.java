package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.po.DocAnaesPreDiscussRecord;

@Service
public class DocAnaesPreDiscussRecordService extends BaseService {
    /** 
     * 查询麻醉术前讨论记录
     * <功能详细描述>
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    public DocAnaesPreDiscussRecord searchPreDiscussRecord(String regOptId) {
    	
    	DocAnaesPreDiscussRecord record = docAnaesPreDiscussRecordDao.selectByRegOptId(regOptId);
    	// 麻醉方法
		record.setDesignedAnaesMethodCodes(StringUtils.getListByString(record.getDesignedAnaesMethodCode()));
        return record;
    }
    
    /** 
     * 更新保存麻醉术前讨论记录
     * <功能详细描述>
     * @param anaesPlan
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void updatePreDiscussRecord(DocAnaesPreDiscussRecord record) {
    	record.setDesignedAnaesMethodCode(StringUtils.getStringByList(record.getDesignedAnaesMethodCodes()));
    	if(StringUtils.isBlank(record.getPreDiscussId())){
    		record.setPreDiscussId(GenerateSequenceUtil.generateSequenceNo());
    		docAnaesPreDiscussRecordDao.insertSelective(record);
    	}else{
    		docAnaesPreDiscussRecordDao.updateByPrimaryKeySelective(record);
    	}
    }
}
