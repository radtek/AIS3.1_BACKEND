package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.doc.po.DocAnalgesicInformedConsent;

/**
 *后（术前）镇疼知情同意书service 
 */
@Service
public class DocAnalgesicInformedConsentService extends BaseService
{

	public DocAnalgesicInformedConsent selectInformedConsentByRegOptId(String regOptId)
	{
		return docAnalgesicInformedConsentDao.selectInformedConsentByRegOptId(regOptId);
	}
	
	@Transactional
	public void saveAnalgesicInformedConsent(DocAnalgesicInformedConsent docAnalgesicInformedConsent)
	{
		
		if(null != docAnalgesicInformedConsent)
		{
			DocAnalgesicInformedConsent old = docAnalgesicInformedConsentDao.selectInformedConsentByRegOptId(docAnalgesicInformedConsent.getRegOptId());
			if(null != old)
			{
				docAnalgesicInformedConsent.setAnalgesicId(old.getAnalgesicId());
				docAnalgesicInformedConsentDao.updateByPrimaryKey(docAnalgesicInformedConsent);
			}else
			{
				docAnalgesicInformedConsent.setAnalgesicId(GenerateSequenceUtil.generateSequenceNo());
				docAnalgesicInformedConsentDao.insertSelective(docAnalgesicInformedConsent);
			}
			
		}
	}
}
