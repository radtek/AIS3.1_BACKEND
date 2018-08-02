package com.digihealth.anesthesia.operProceed.formbean;

import com.digihealth.anesthesia.evt.po.EvtAnaesEvent;

/**
 * 结束手术formBean
     * Title: EndOperationFormBean.java    
     * Description: 
     * @author chenyong       
     * @created 2016年7月18日 上午11:05:16
 */
public class EndOperationFormBean {
	private EvtAnaesEvent anaesevent;

	private String regOptId;
	
	private String reasons;//取消手术原因
	
	private Integer docType;

	public Integer getDocType()
    {
        return docType;
    }

    public void setDocType(Integer docType)
    {
        this.docType = docType;
    }

    public String getReasons()
    {
        return reasons;
    }

    public void setReasons(String reasons)
    {
        this.reasons = reasons;
    }

    public EvtAnaesEvent getAnaesevent() {
		return anaesevent;
	}

	public void setAnaesevent(EvtAnaesEvent anaesevent) {
		this.anaesevent = anaesevent;
	}

	public String getRegOptId() {
		return regOptId;
	}

	public void setRegOptId(String regOptId) {
		this.regOptId = regOptId;
	}

}
