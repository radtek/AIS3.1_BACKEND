package com.digihealth.anesthesia.basedata.formbean;

import java.io.Serializable;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "文书状态对象")
public class OneRegOptDocumentStateFormbean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "文书名称")
	private String name;
	
	@ApiModelProperty(value = "完成状态")
	private boolean state;
	
	@ApiModelProperty(value = "文书必填项")
	private String required;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getRequired()
	{
		return required;
	}

	public void setRequired(String required)
	{
		this.required = required;
	}

}
