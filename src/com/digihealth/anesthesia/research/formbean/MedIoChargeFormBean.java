package com.digihealth.anesthesia.research.formbean;

import java.io.Serializable;
import java.util.List;

import com.digihealth.anesthesia.doc.po.DocEventBilling;
import com.digihealth.anesthesia.doc.po.DocPackagesItem;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 
 * Title: MedIoChargeFormBean.java Description: 修改费用统计
 * 
 * @author chengwang
 * @created 2015年12月17日 下午3:23:51
 */
@ApiModel(value = "查询参数对象")
public class MedIoChargeFormBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "medList")
	private List<DocEventBilling> medList;

	@ApiModelProperty(value = "费用统计单")
	private List<DocEventBilling> ioList;

	@ApiModelProperty(value = "收费项目")
	private List<DocPackagesItem> packagesCharge;

	@ApiModelProperty(value = "麻醉费用")
	private List<DocPackagesItem> anaesChargeList;
	
	@ApiModelProperty(value = "耗材费用")
	private List<DocPackagesItem> materialList;
	
	@ApiModelProperty(value = "麻醉操作项目")
    private List<DocPackagesItem> anaesOptList;
	
	@ApiModelProperty(value = "是否需要排序 1是")
	private String isNeedSort; 

	

    public String getIsNeedSort() {
		return isNeedSort;
	}

	public void setIsNeedSort(String isNeedSort) {
		this.isNeedSort = isNeedSort;
	}

	public List<DocPackagesItem> getAnaesChargeList() {
		return anaesChargeList;
	}

	public void setAnaesChargeList(List<DocPackagesItem> anaesChargeList) {
		this.anaesChargeList = anaesChargeList;
	}

	public List<DocPackagesItem> getAnaesOptList() {
		return anaesOptList;
	}

	public void setAnaesOptList(List<DocPackagesItem> anaesOptList) {
		this.anaesOptList = anaesOptList;
	}

	public List<DocPackagesItem> getMaterialList()
    {
        return materialList;
    }

    public void setMaterialList(List<DocPackagesItem> materialList)
    {
        this.materialList = materialList;
    }

    public List<DocEventBilling> getMedList() {
		return medList;
	}

	public void setMedList(List<DocEventBilling> medList) {
		this.medList = medList;
	}

	public List<DocEventBilling> getIoList() {
		return ioList;
	}

	public void setIoList(List<DocEventBilling> ioList) {
		this.ioList = ioList;
	}

	public List<DocPackagesItem> getPackagesCharge() {
		return packagesCharge;
	}

	public void setPackagesCharge(List<DocPackagesItem> packagesCharge) {
		this.packagesCharge = packagesCharge;
	}


}
