package com.digihealth.anesthesia.doc.formbean;

/**
 * 自定义动态sql产生的对象 
 */
public class DynamicSqlFormBean
{

	private String code;
	
	private String name;
	
	private String pinyin;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPinyin()
	{
		return pinyin;
	}

	public void setPinyin(String pinyin)
	{
		this.pinyin = pinyin;
	}
}
