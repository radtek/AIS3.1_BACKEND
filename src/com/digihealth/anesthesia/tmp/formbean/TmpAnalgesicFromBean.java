package com.digihealth.anesthesia.tmp.formbean;

import java.util.List;

import com.digihealth.anesthesia.evt.po.EvtMedicalEvent;
import com.digihealth.anesthesia.tmp.po.TmpAnalgesic;

public class TmpAnalgesicFromBean
{

	//镇痛方式 PCIA,PCEA等
	private String analgesicType;
	
	//镇痛方式对象列表
	private List<TmpAnalgesic> tmpAnalgesicList;
	
	//用药事件对象
	private EvtMedicalEvent medicalevent;

	public String getAnalgesicType()
	{
		return analgesicType;
	}

	public void setAnalgesicType(String analgesicType)
	{
		this.analgesicType = analgesicType;
	}

	public List<TmpAnalgesic> getTmpAnalgesicList()
	{
		return tmpAnalgesicList;
	}

	public void setTmpAnalgesicList(List<TmpAnalgesic> tmpAnalgesicList)
	{
		this.tmpAnalgesicList = tmpAnalgesicList;
	}

	public EvtMedicalEvent getMedicalevent()
	{
		return medicalevent;
	}

	public void setMedicalevent(EvtMedicalEvent medicalevent)
	{
		this.medicalevent = medicalevent;
	}
	
}
