package com.digihealth.anesthesia.doc.service;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.doc.po.DocVeinAccede;

@Service
public class DocVeinAccedeService extends BaseService
{
    public DocVeinAccede searchByRegOptId(String regOptId)
    {
        DocVeinAccede docVeinAccede = docVeinAccedeDao.searchByRegOptId(regOptId);
        if (null != docVeinAccede)
        {
            if (null == docVeinAccede.getAnaesDoctor())
            {
                DispatchFormbean dispatchPeople = basDispatchDao.getDispatchOperByRegOptId(regOptId, getBeid());
                docVeinAccede.setAnaesDoctor(dispatchPeople.getAnesthetistId());
            }
            
            if (null == docVeinAccede.getVisitDoctor())
            {
                DispatchFormbean dispatchPeople = basDispatchDao.getDispatchOperByRegOptId(regOptId, getBeid());
                docVeinAccede.setVisitDoctor(dispatchPeople.getAnesthetistId());
            }
            
            if (null == docVeinAccede.getDate())
            {
                docVeinAccede.setDate(DateUtils.getDate());
            }
            
            JSONObject jasonObject1 = JSONObject.fromObject(docVeinAccede.getHighRisk());
            docVeinAccede.setHighRiskMap(null == jasonObject1 ? new HashMap<String, Object>() : jasonObject1);
            JSONObject jasonObject2 = JSONObject.fromObject(docVeinAccede.getNervousDetail());
            docVeinAccede.setNervousMap(null == jasonObject2 ? new HashMap<String, Object>() : jasonObject2);
            JSONObject jasonObject3 = JSONObject.fromObject(docVeinAccede.getBreathDetail());
            docVeinAccede.setBreathMap(null == jasonObject3 ? new HashMap<String, Object>() : jasonObject3);
            JSONObject jasonObject4 = JSONObject.fromObject(docVeinAccede.getHeartBoolDetail());
            docVeinAccede.setHeartBoolMap(null == jasonObject4 ? new HashMap<String, Object>() : jasonObject4);
            JSONObject jasonObject5 = JSONObject.fromObject(docVeinAccede.getHereditaryDetail());
            docVeinAccede.setHereditaryMap(null == jasonObject5 ? new HashMap<String, Object>() : jasonObject5);
            JSONObject jasonObject6 = JSONObject.fromObject(docVeinAccede.getOtherDetail());
            docVeinAccede.setOtherMap(null == jasonObject6 ? new HashMap<String, Object>() : jasonObject6);
            
        }
        return docVeinAccede;
    }
    
    @Transactional
    public void updateDocVeinAccede(DocVeinAccede docVeinAccede)
    {
        docVeinAccede.setHighRisk(String.valueOf(docVeinAccede.getHighRiskMap()));
        docVeinAccede.setNervousDetail(String.valueOf(docVeinAccede.getNervousMap()));
        docVeinAccede.setBreathDetail(String.valueOf(docVeinAccede.getBreathMap()));
        docVeinAccede.setHeartBoolDetail(String.valueOf(docVeinAccede.getHeartBoolMap()));
        docVeinAccede.setHereditaryDetail(String.valueOf(docVeinAccede.getHereditaryMap()));
        docVeinAccede.setOtherDetail(String.valueOf(docVeinAccede.getOtherMap()));
        docVeinAccedeDao.updateByPrimaryKey(docVeinAccede);
    }
}
