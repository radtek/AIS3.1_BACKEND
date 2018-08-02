package com.digihealth.anesthesia.interfacedata.formbean.yxrm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.digihealth.anesthesia.interfacedata.po.yxrm.HisDispatch;
import com.digihealth.anesthesia.interfacedata.po.yxrm.HisRegOpt;

/**
 * 手术排程信息
 * @author dell
 *
 */
@XmlRootElement(name = "Request")
public class EmergencyOperFormbean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1020398189900520462L;
	private HisRegOpt regopt;
	private HisDispatch dispatch;
	
	
	public HisRegOpt getRegopt()
    {
        return regopt;
    }
    public void setRegopt(HisRegOpt regopt)
    {
        this.regopt = regopt;
    }
    public HisDispatch getDispatch() {
		return dispatch;
	}
	public void setDispatch(HisDispatch dispatch) {
		this.dispatch = dispatch;
	}
	
	
}
