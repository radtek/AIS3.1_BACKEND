package com.digihealth.anesthesia.interfacedata.formbean.syzxyy;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class HisOptcostFormBean implements Serializable{
	
	private List<CostRow> row;

	public List<CostRow> getRow() {
		return row;
	}

	public void setRow(List<CostRow> row) {
		this.row = row;
	}
	
	
}
