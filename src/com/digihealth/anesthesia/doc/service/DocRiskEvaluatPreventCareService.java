package com.digihealth.anesthesia.doc.service;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.doc.po.DocRiskEvaluatPreventCare;

@Service
public class DocRiskEvaluatPreventCareService extends BaseService
{
    public DocRiskEvaluatPreventCare searchByRegOptId(String regOptId)
    {
        DocRiskEvaluatPreventCare docRiskEvaluatPreventCare = docRiskEvaluatPreventCareDao.searchByRegOptId(regOptId);
        if (null != docRiskEvaluatPreventCare)
        {
            if (null == docRiskEvaluatPreventCare.getEvaluatNurse())
            {
                DispatchFormbean dispatchPeople = basDispatchDao.getDispatchOperByRegOptId(regOptId, getBeid());
                docRiskEvaluatPreventCare.setEvaluatNurse(dispatchPeople.getCircunurseId1());
            }
            
            if (null == docRiskEvaluatPreventCare.getDate())
            {
                docRiskEvaluatPreventCare.setDate(DateUtils.getDate());
            }
            
            JSONObject jasonObject1 = JSONObject.fromObject(docRiskEvaluatPreventCare.getImplantDetail());
            docRiskEvaluatPreventCare.setImplantMap(null == jasonObject1 ? new HashMap<String, Object>() : jasonObject1);
            JSONObject jasonObject2 = JSONObject.fromObject(docRiskEvaluatPreventCare.getPipelineDetail());
            docRiskEvaluatPreventCare.setPipelineMap(null == jasonObject2 ? new HashMap<String, Object>() : jasonObject2);
            JSONObject jasonObject3 = JSONObject.fromObject(docRiskEvaluatPreventCare.getLowTemp());
            docRiskEvaluatPreventCare.setLowTempMap(null == jasonObject3 ? new HashMap<String, Object>() : jasonObject3);
            JSONObject jasonObject4 = JSONObject.fromObject(docRiskEvaluatPreventCare.getDisengage());
            docRiskEvaluatPreventCare.setDisengageMap(null == jasonObject4 ? new HashMap<String, Object>() : jasonObject4);
            JSONObject jasonObject5 = JSONObject.fromObject(docRiskEvaluatPreventCare.getBradenCare());
            docRiskEvaluatPreventCare.setBradenCareMap(null == jasonObject5 ? new HashMap<String, Object>() : jasonObject5);
            JSONObject jasonObject6 = JSONObject.fromObject(docRiskEvaluatPreventCare.getFallCare());
            docRiskEvaluatPreventCare.setFallCareMap(null == jasonObject6 ? new HashMap<String, Object>() : jasonObject6);
        }
        
        return docRiskEvaluatPreventCare;
    }
    
    @Transactional
    public void updateRiskEvaluatPreventCare(DocRiskEvaluatPreventCare docRiskEvaluatPreventCare)
    {
        docRiskEvaluatPreventCare.setImplantDetail(String.valueOf(docRiskEvaluatPreventCare.getImplantMap()));
        docRiskEvaluatPreventCare.setPipelineDetail(String.valueOf(docRiskEvaluatPreventCare.getPipelineMap()));
        docRiskEvaluatPreventCare.setLowTemp(String.valueOf(docRiskEvaluatPreventCare.getLowTempMap()));
        docRiskEvaluatPreventCare.setDisengage(String.valueOf(docRiskEvaluatPreventCare.getDisengageMap()));
        docRiskEvaluatPreventCare.setBradenCare(String.valueOf(docRiskEvaluatPreventCare.getBradenCareMap()));
        docRiskEvaluatPreventCare.setFallCare(String.valueOf(docRiskEvaluatPreventCare.getFallCareMap()));
        docRiskEvaluatPreventCareDao.updateByPrimaryKey(docRiskEvaluatPreventCare);
    }
}
