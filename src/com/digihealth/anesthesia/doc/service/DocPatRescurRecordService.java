package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocPatRescurRecord;

@Service
public class DocPatRescurRecordService extends BaseService
{
    public DocPatRescurRecord selectByRegOptId(String regOptId)
    {
        return docPatRescurRecordDao.selectByRegOptId(regOptId);
    }
    
    @Transactional
    public void updatePatRescurRecord(DocPatRescurRecord patRescurRecord)
    {
        docPatRescurRecordDao.updateByPrimaryKey(patRescurRecord);
    }
}
