package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocDifficultCaseDiscuss;

@Service
public class DocDifficultCaseDiscussService extends BaseService
{
    public DocDifficultCaseDiscuss selectByRegOptId(String regOptId)
    {
        return docDifficultCaseDiscussDao.selectByRegOptId(regOptId);
    }
    
    @Transactional
    public void updateDifficultCaseDiscuss(DocDifficultCaseDiscuss difficultCaseDiscuss)
    {
        docDifficultCaseDiscussDao.updateByPrimaryKey(difficultCaseDiscuss);
    }
}
