package com.digihealth.anesthesia.doc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.doc.po.DocDocordRecord;
import com.digihealth.anesthesia.interfacedata.formbean.DoctorOrderFormbean;

@Service
public class DocDocordRecordService extends BaseService{

	@Transactional
	public void updateDoctorOrder(List<DoctorOrderFormbean> orderList) {
		if (null != orderList && orderList.size() > 0) {
			for (DoctorOrderFormbean doctorOrderFormbean : orderList) {
				List<DocDocordRecord> ls = docDocordRecordDao.queryListByHidGroupId(
						doctorOrderFormbean.getZyhm(),
						doctorOrderFormbean.getGroupId(), null);
				for (DocDocordRecord docordRecord : ls) {
					docordRecord.setExcutor(doctorOrderFormbean.getExcutor());
					docordRecord.setExcutorId(doctorOrderFormbean.getExcutorId());
					docordRecord.setExcuteTime(DateUtils.getParseTime(doctorOrderFormbean.getExcuteTime()));
					docordRecord.setState(3);// 执行完成
					docDocordRecordDao.updateByPrimaryKeySelective(docordRecord);
				}
			}
		}
	}
}
