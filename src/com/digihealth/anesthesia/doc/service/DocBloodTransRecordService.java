package com.digihealth.anesthesia.doc.service;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.doc.po.DocBloodTransRecord;

@Service
public class DocBloodTransRecordService extends BaseService{

	public DocBloodTransRecord searchOptBloodTransRecordByRegOptId(String regOptId){
	    DocBloodTransRecord bloodTransRecord = docBloodTransRecordDao.searchOptBloodTransRecordByRegOptId(regOptId);
	    if (null == bloodTransRecord.getAnaestheitistId())
        {
            DispatchFormbean dispatchPeople =
                basDispatchDao.getDispatchOperByRegOptId(regOptId, getBeid());
            bloodTransRecord.setAnaestheitistId(dispatchPeople.getAnesthetistId());
        }
        
        if (null == bloodTransRecord.getCreateTime())
        {
            bloodTransRecord.setCreateTime(DateUtils.getDate());
        }
	    
	    setMapValue(bloodTransRecord);
	    return bloodTransRecord;
	}
	
	@Transactional
	public void updateBloodTransRecord(DocBloodTransRecord record){
	    setSelectValue(record);
		docBloodTransRecordDao.updateByPrimaryKeySelective(record);
	}

	private void setSelectValue(DocBloodTransRecord record)
    {
	    record.setHemolysisReact1(String.valueOf(record.getHemolysisReact1Map()));
	    record.setHemolysisReact2(String.valueOf(record.getHemolysisReact2Map()));
	    record.setHemolysisReact3(String.valueOf(record.getHemolysisReact3Map()));
	    record.setAllergicReact(String.valueOf(record.getAllergicReactMap()));
	    record.setFeverReact(String.valueOf(record.getFeverReactMap()));
    }

    private void setMapValue(DocBloodTransRecord bloodTransRecord)
    {
        JSONObject jasonObject1 = JSONObject.fromObject(bloodTransRecord.getHemolysisReact1());
        bloodTransRecord.setHemolysisReact1Map(null == jasonObject1 ? new HashMap<String, Object>() : jasonObject1);
        
        JSONObject jasonObject2 = JSONObject.fromObject(bloodTransRecord.getHemolysisReact2());
        bloodTransRecord.setHemolysisReact2Map(null == jasonObject2 ? new HashMap<String, Object>() : jasonObject2);
        
        JSONObject jasonObject3 = JSONObject.fromObject(bloodTransRecord.getHemolysisReact3());
        bloodTransRecord.setHemolysisReact3Map(null == jasonObject3 ? new HashMap<String, Object>() : jasonObject3);
        
        JSONObject jasonObject4 = JSONObject.fromObject(bloodTransRecord.getAllergicReact());
        bloodTransRecord.setAllergicReactMap(null == jasonObject4 ? new HashMap<String, Object>() : jasonObject4);
        
        JSONObject jasonObject5 = JSONObject.fromObject(bloodTransRecord.getFeverReact());
        bloodTransRecord.setFeverReactMap(null == jasonObject5 ? new HashMap<String, Object>() : jasonObject5);
    }
}
