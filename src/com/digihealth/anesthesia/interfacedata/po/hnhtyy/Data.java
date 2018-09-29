package com.digihealth.anesthesia.interfacedata.po.hnhtyy;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import com.digihealth.anesthesia.interfacedata.po.hnhtyy.Row;

public class Data
{
	private List<Row> rows;
	
	@XmlElement(name = "RECORDINFO")
	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
}
