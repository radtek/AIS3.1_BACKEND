package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.doc.formbean.PostOperRegardFormBean;
import com.digihealth.anesthesia.doc.po.DocPostOperRegard;

@Service
public class DocPostOperRegardService extends BaseService{

	public PostOperRegardFormBean searchPostOperRegard(String regOptId) {
		return docPostOperRegardDao.selectByRegOptId(regOptId, getBeid());
	}
	
	@Transactional
	public void updatePostOperRegard(DocPostOperRegard postOperRegard) {
		docPostOperRegardDao.updateByPrimaryKeySelective(postOperRegard);
	}
	
}
