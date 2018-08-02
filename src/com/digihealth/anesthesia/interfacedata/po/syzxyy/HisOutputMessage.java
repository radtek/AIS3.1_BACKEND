package com.digihealth.anesthesia.interfacedata.po.syzxyy;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HIS")
public class HisOutputMessage
{
	
	private Result result;
    private Data rows;
    
    

	public Data getRows() {
		return rows;
	}
	public Result getResult() {
		return result;
	}
	@XmlElement(name = "Result")
	public void setResult(Result result) {
		this.result = result;
	}
	public void setRows(Data rows) {
		this.rows = rows;
	}
	
	


}
