package com.digihealth.anesthesia.doc.formbean;

import java.util.Map;


public class DocZdyFormBean
{

	private String tableName;
	
	private Map<String,Object> columns;

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public Map<String, Object> getColumns()
	{
		return columns;
	}

	public void setColumns(Map<String, Object> columns)
	{
		this.columns = columns;
	}
	
}
