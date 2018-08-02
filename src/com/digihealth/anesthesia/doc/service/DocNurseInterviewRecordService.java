package com.digihealth.anesthesia.doc.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.doc.formbean.NurseInterviewFormBean;
import com.digihealth.anesthesia.doc.formbean.NurseInteviewRecordFormBean;
import com.digihealth.anesthesia.doc.po.DocNurseInterviewRecord;

@Service
public class DocNurseInterviewRecordService extends BaseService {

	public NurseInteviewRecordFormBean searchNurseInterviewRecord(String regOptId) {
		return docNurseInterviewRecordDao.selectByRegOptId(regOptId, getBeid());
	}

	@Transactional
	public void updateNurseInterviewRecord(DocNurseInterviewRecord interviewRecord) {

		docNurseInterviewRecordDao.updateByPrimaryKeySelective(interviewRecord);
	}

	@Transactional
	public void updateNurseInterviewRecord(NurseInterviewFormBean interviewRecord) {
		DocNurseInterviewRecord record = new DocNurseInterviewRecord();
		BeanUtils.copyProperties(interviewRecord, record, new String[] { "medicalRecord", "conditionIntroduce", "preOperExplain", 
				"operPressureSore", "preventPressureMeasure" ,"prePrepareCase"});//除json串的不需要传递之外，其他都传递
		if (null != interviewRecord.getMedicalRecord()) {
			record.setMedicalRecord(JsonType.jsonType(interviewRecord.getMedicalRecord()));
		}
		if (null != interviewRecord.getConditionIntroduce()) {
			record.setConditionIntroduce(JsonType.jsonType(interviewRecord.getConditionIntroduce()));
		}
		if (null != interviewRecord.getPreOperExplain()) {
			record.setPreOperExplain(JsonType.jsonType(interviewRecord.getPreOperExplain()));
		}
		if (null != interviewRecord.getOperPressureSore()) {
			record.setOperPressureSore(JsonType.jsonType(interviewRecord.getOperPressureSore()));
		}
		if (null != interviewRecord.getPreventPressureMeasure()) {
			record.setPreventPressureMeasure(JsonType.jsonType(interviewRecord.getPreventPressureMeasure()));
		}
		if(null != interviewRecord.getPrePrepareCase()){
			record.setPrePrepareCase(JsonType.jsonType(interviewRecord.getPrePrepareCase()));
		}
		docNurseInterviewRecordDao.updateByPrimaryKeySelective(record);
	}

}
