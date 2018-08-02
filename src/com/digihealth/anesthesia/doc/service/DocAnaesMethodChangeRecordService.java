package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.po.DocAnaesMethodChangeRecord;

@Service
public class DocAnaesMethodChangeRecordService extends BaseService {
    /** 
     * 查询麻醉计划
     * <功能详细描述>
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    public DocAnaesMethodChangeRecord searchAnaesMethodChangeRecord(String regOptId) {
    	DocAnaesMethodChangeRecord docPo = docAnaesMethodChangeRecordDao.selectByRegOptId(regOptId);
    	if(docPo !=null){
    		docPo.setAnaesMethodCodes(StringUtils.getListByString(docPo.getAnaesMethodCode()));
    	}
        return docPo;
    }
    
    /** 
     * 更新保存麻醉计划
     * <功能详细描述>
     * @param anaesPlan
     * @return
     * @see [类、类#方法、类#成员]
     */
    @Transactional
    public void updateAnaesMethodChange(DocAnaesMethodChangeRecord record) {
    	
    	record.setAnaesMethodCode(StringUtils.getStringByList(record.getAnaesMethodCodes()));
    	
    	if(StringUtils.isBlank(record.getId())){
    		record.setId(GenerateSequenceUtil.generateSequenceNo());
    		docAnaesMethodChangeRecordDao.insertSelective(record);
    	}else{
    		docAnaesMethodChangeRecordDao.updateByPrimaryKeySelective(record);
    	}
    }
}
