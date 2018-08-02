package com.digihealth.anesthesia.doc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.po.DocBloodTransItem;
import com.digihealth.anesthesia.interfacedata.po.Blood;
@Service
public class DocBloodTransItemService extends BaseService{
	
	public List<DocBloodTransItem> getBloodTransItemList(String bloodTransId){
		return docBloodTransItemDao.queryBloodItemListByRegoptId(bloodTransId);
	}
	
	public List<Blood> queryNotSendBloodItemListByRegoptId(String bloodTransId,String status){
		return docBloodTransItemDao.queryNotSendBloodItemListByRegoptId(bloodTransId, status);
	}
	
	@Transactional
	public void saveBloodTransItem(DocBloodTransItem record){
		
		if(StringUtils.isNotEmpty(record.getId())){
			docBloodTransItemDao.updateByPrimaryKeySelective(record);
		}else{
			record.setId(GenerateSequenceUtil.generateSequenceNo());
			record.setStatus("1");
			docBloodTransItemDao.insertSelective(record);
		}
	}
	
	@Transactional
	public void updateBloodItemState(List<Blood> bloodList){
		for (Blood blood : bloodList) {
			docBloodTransItemDao.updateBloodItemState(blood.getId());
		}
	}
	
	@Transactional
	public void deleteBloodTransItem(DocBloodTransItem record){
		docBloodTransItemDao.deleteByPrimaryKey(record.getId());
	}
	
	@Transactional(readOnly = false)
	public void insertBloodTransItem(DocBloodTransItem record){
		docBloodTransItemDao.insertSelective(record);
	}
}
