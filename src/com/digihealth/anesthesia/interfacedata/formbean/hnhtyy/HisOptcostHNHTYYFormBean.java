package com.digihealth.anesthesia.interfacedata.formbean.hnhtyy;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RECORDLIST")
public class HisOptcostHNHTYYFormBean
{
	private List<CostHNHTYYRow> rows;
	
	@XmlElement(name = "RECORDINFO")
	public List<CostHNHTYYRow> getRows() {
		return rows;
	}

	public void setRows(List<CostHNHTYYRow> rows) {
		this.rows = rows;
	}
}