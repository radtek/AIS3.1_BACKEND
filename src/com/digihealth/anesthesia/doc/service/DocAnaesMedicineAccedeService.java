package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocAnaesMedicineAccede;

@Service
public class DocAnaesMedicineAccedeService extends BaseService
{
    public DocAnaesMedicineAccede selectByRegOptId(String regOptId)
    {
        return docAnaesMedicineAccedeDao.selectByRegOptId(regOptId);
    }
    
    @Transactional
    public void updateDocAnaesMedicineAccede(DocAnaesMedicineAccede docAnaesMedicineAccede)
    {
        docAnaesMedicineAccedeDao.updateByPrimaryKey(docAnaesMedicineAccede);
    }
}
