package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.po.DocPreAnaesTalkRecord;

@Service
public class DocPreAnaesTalkRecordService extends BaseService
{
    public DocPreAnaesTalkRecord selectByRegOptId(String regOptId)
    {
        return docPreAnaesTalkRecordDao.selectByRegOptId(regOptId);
    }
    
    @Transactional
    public void updatePreAnaesTalkRecord(DocPreAnaesTalkRecord preAnaesTalkRecord)
    {
    	if(StringUtils.isBlank(preAnaesTalkRecord.getId())){
    		preAnaesTalkRecord.setId(GenerateSequenceUtil.generateSequenceNo());
    		docPreAnaesTalkRecordDao.insertSelective(preAnaesTalkRecord);
    	}else{
    		docPreAnaesTalkRecordDao.updateByPrimaryKey(preAnaesTalkRecord);
    	}
    }
}
