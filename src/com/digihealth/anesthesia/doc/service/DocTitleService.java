package com.digihealth.anesthesia.doc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.doc.formbean.DocTitleFormBean;
import com.digihealth.anesthesia.doc.po.DocTitle;

@Service
public class DocTitleService extends BaseService
{

	//通过主题id查询标题列表
	public void delDocTitleByThemeId(String docThemeId)
	{
		docTitleDao.delDocTitleByThemeId(docThemeId);
	}
		
	//通过主题id查询标题列表
	public List<DocTitleFormBean> searchDocTitleByThemeId(String themeId)
	{
		List<DocTitleFormBean> docTitleList = docTitleDao.searchDocTitleByThemeId(themeId);
		
		return docTitleList;
	}
	
	//通过标题id查询标题对象
	public DocTitleFormBean searchDocTitleByTileId(String titleId)
	{
		return docTitleDao.searchDocTitleByTitleId(titleId);
	}
	
	//保存自定义文书标题
	public void saveDocTitle(List<DocTitle> docTitleList,String tableName)
	{
		if(null != docTitleList && docTitleList.size()>0)
		{
			for(DocTitle docTitle:docTitleList)
			{
				String docTitleId =  GenerateSequenceUtil.generateSequenceNo();
				docTitle.setDocTitleId(docTitleId);
				docTitle.setTableName(tableName);
				docTitleDao.insertSelective(docTitle);
			}
		}
	}
}
