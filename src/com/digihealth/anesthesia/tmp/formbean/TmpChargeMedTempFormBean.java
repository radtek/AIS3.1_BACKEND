package com.digihealth.anesthesia.tmp.formbean;

import java.util.List;

import com.digihealth.anesthesia.tmp.po.TmpChargePayTemp;
import com.digihealth.anesthesia.tmp.po.TmpMedPayTemp;
import com.digihealth.anesthesia.tmp.po.TmpPriChargeMedTemp;

/**
 * 麻醉收费单模板
 * @author dell
 *
 */
public class TmpChargeMedTempFormBean {
	
	private TmpPriChargeMedTemp tmpPriChargeMedTemp;
	private List<TmpChargePayTemp> tmpChargePayTempList;
	private List<TmpMedPayTemp> tmpMedPayTempList;
	
	
	public TmpPriChargeMedTemp getTmpPriChargeMedTemp() {
		return tmpPriChargeMedTemp;
	}
	public void setTmpPriChargeMedTemp(TmpPriChargeMedTemp tmpPriChargeMedTemp) {
		this.tmpPriChargeMedTemp = tmpPriChargeMedTemp;
	}
	public List<TmpChargePayTemp> getTmpChargePayTempList() {
		return tmpChargePayTempList;
	}
	public void setTmpChargePayTempList(List<TmpChargePayTemp> tmpChargePayTempList) {
		this.tmpChargePayTempList = tmpChargePayTempList;
	}
	public List<TmpMedPayTemp> getTmpMedPayTempList() {
		return tmpMedPayTempList;
	}
	public void setTmpMedPayTempList(List<TmpMedPayTemp> tmpMedPayTempList) {
		this.tmpMedPayTempList = tmpMedPayTempList;
	}

	
}
