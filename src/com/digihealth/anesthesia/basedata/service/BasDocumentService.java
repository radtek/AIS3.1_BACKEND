package com.digihealth.anesthesia.basedata.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.po.BasDocument;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;

@Service
public class BasDocumentService extends BaseService
{

	//通过beid查找本局点下的所有文书
	public List<BasDocument> selectBasDocByBeid(String beid)
	{
		if(StringUtils.isBlank(beid))
		{
			beid = getBeid();
		}
		return basDocumentDao.searchAllDocumentMenu(beid);
	}
	
	//通过文书ID查询文书定义信息
	public BasDocument selectBasDocByDocId(String docId)
	{
		return basDocumentDao.selectByPrimaryKey(docId);
	}
	
	//保存文书定义信息，传了docId为修改，没有传为新增
	@Transactional
	public void saveBasDocument(BasDocument basDocument)
	{
		if(null != basDocument)
		{
			String docId = basDocument.getDocId();
			if(StringUtils.isBlank(docId))
			{
				docId = GenerateSequenceUtil.generateSequenceNo();
				basDocument.setDocId(docId);
				basDocumentDao.insertSelective(basDocument);
			}else
			{
				basDocumentDao.updateByPrimaryKeySelective(basDocument);
			}
		}
	}
}
