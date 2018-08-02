package com.digihealth.anesthesia.basedata.formbean;

import java.util.List;

import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 批量手术排程
 * 
 * @author liukui
 * 
 */
@ApiModel(value = "批量手术排程参数对象")
public class DispatchOperationFormBean {

	@ApiModelProperty(value = "排程信息")
	private List<BasDispatch> dispatchList; // 排程信息

	@ApiModelProperty(value = "操作员类型")
	private String roleType; // 操作员类型
	
	private Integer dispatchToHis; // 1即为手术室排班，需要传递消息给his 

	public Integer getDispatchToHis()
    {
        return dispatchToHis;
    }

    public void setDispatchToHis(Integer dispatchToHis)
    {
        this.dispatchToHis = dispatchToHis;
    }

    public List<BasDispatch> getDispatchList() {
		return dispatchList;
	}

	public void setDispatchList(List<BasDispatch> dispatchList) {
		this.dispatchList = dispatchList;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
}
