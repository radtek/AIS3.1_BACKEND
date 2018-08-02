package com.digihealth.anesthesia.doc.formbean;

import java.util.List;

import com.digihealth.anesthesia.doc.po.DocTheme;
import com.digihealth.anesthesia.doc.po.DocTitle;

public class BatchSaveDocTitleFormBean
{

	private DocTheme docTheme;
	
	private List<DocTitle> docTitleList;

	public DocTheme getDocTheme()
	{
		return docTheme;
	}

	public void setDocTheme(DocTheme docTheme)
	{
		this.docTheme = docTheme;
	}

	public List<DocTitle> getDocTitleList()
	{
		return docTitleList;
	}

	public void setDocTitleList(List<DocTitle> docTitleList)
	{
		this.docTitleList = docTitleList;
	}
	
}
