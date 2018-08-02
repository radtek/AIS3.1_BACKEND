package com.digihealth.anesthesia.doc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryVenipuncture;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;

@Service
public class DocAnaesSummaryVenipunctureService extends BaseService
{
    public List<DocAnaesSummaryVenipuncture> searchVenipunctureList(SearchFormBean searchBean){
        return docAnaesSummaryVenipunctureDao.searchVenipunctureList(searchBean);
    }
}
