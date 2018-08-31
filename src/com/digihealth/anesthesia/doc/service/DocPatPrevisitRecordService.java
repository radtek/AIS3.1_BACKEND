package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.po.DocPatPrevisitRecord;

@Service
public class DocPatPrevisitRecordService extends BaseService {

	/**
	 * 
	 * @discription 根据手术ID获取麻醉同意书信息
	 * @author chengwang
	 * @created 2015-10-10 下午5:13:48
	 * @param regOptId
	 * @return
	 */
	public DocPatPrevisitRecord searchPatPrevisitRecordByRegOptId(String regOptId) {
		return docPatPrevisitRecordDao.searchPatPrevisitRecordByRegOptId(regOptId);
	}

	/**
	 * 
	 * @discription 通过ID查询麻醉同意书
	 * @author chengwang
	 * @created 2015-10-20 下午1:44:32
	 * @param id
	 * @return
	 */
//	public DocPatPrevisitRecord searchDocPatPrevisitRecord(String id) {
//		return docPatPrevisitRecordDao.selectByPrimaryKey(id);
//	}

	
	/**
	 * 
	 * @discription 保存麻醉同意书
	 * @author chengwang
	 * @created 2015-10-20 下午1:44:18
	 * @param preVisit
	 * @return
	 */
	@Transactional
	public String updateDocPatPrevisitRecord(DocPatPrevisitRecord record) {
		ResponseValue resp = new ResponseValue();
		Controller controller = controllerDao.getControllerById(record
				.getRegOptId() != null ? record.getRegOptId() : "");

		if (controller == null) {
			resp.setResultCode("70000001");
			resp.setResultMessage("手术控制信息不存在!");
			return resp.getJsonStr();
		}

//		optNurseRecord.setBlood(String.valueOf(optNurseRecord.getBloodMap()));
//		optNurseRecord.setInfusion(String.valueOf(optNurseRecord.getInfusionMap()));
//		optNurseRecord.setBradenCond(String.valueOf(optNurseRecord.getBradenMap()));
//		optNurseRecord.setFallCond(String.valueOf(optNurseRecord.getFallMap()));
//
//		getAnaesMethod(optNurseRecord);
//		getOperatiomName(optNurseRecord);


		docPatPrevisitRecordDao.updateByPrimaryKey(record);

		resp.setResultCode("1");
		resp.setResultMessage("患者访视记录单修改成功!");
		return resp.getJsonStr();
	}
	
	
	
}
