package com.digihealth.anesthesia.evt.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.DiagnosedefFormBean;
import com.digihealth.anesthesia.basedata.po.BasDiagnosedef;
import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptLatterDiag;

@Service
public class EvtOptLatterDiagService extends BaseService {

	public List<DiagnosedefFormBean> getSelectOptLatterDiagList(SearchFormBean searchBean) {
		if (StringUtils.isBlank(searchBean.getBeid())) {
			searchBean.setBeid(getBeid());
		}
		return evtOptLatterDiagDao.getSelectOptLatterDiagList(searchBean);
	}

	public List<EvtOptLatterDiag> searchOptLatterDiagList(SearchFormBean searchBean) {
		if (StringUtils.isBlank(searchBean.getBeid())) {
			searchBean.setBeid(getBeid());
		}
		return evtOptLatterDiagDao.searchOptLatterDiagList(searchBean);
	}

	/**
	 * 术中诊断事件
	 * 
	 * @param OptLatterDiag
	 */
	@Transactional
	public void saveOptLatterDiag(List<EvtOptLatterDiag> optLatterDiagList) {

		if (null != optLatterDiagList && optLatterDiagList.size() > 0) {
			String docId = optLatterDiagList.get(0).getDocId();
			evtOptLatterDiagDao.deleteByDocId(docId);
		for (EvtOptLatterDiag optLatterDiag : optLatterDiagList) {
		    
		    if (StringUtils.isBlank(optLatterDiag.getDiagDefId()) && StringUtils.isBlank(optLatterDiag.getName()))
		    {
		        continue;
		    }
		    
		    if (StringUtils.isBlank(optLatterDiag.getOptLatterDiagId()))
		    {
		        optLatterDiag.setOptLatterDiagId(GenerateSequenceUtil.generateSequenceNo());
		    }
		    
			if (StringUtils.isBlank(optLatterDiag.getDiagDefId())) {
			    String diagDefId = GenerateSequenceUtil.generateSequenceNo();
				optLatterDiag.setDiagDefId(diagDefId);
				BasDiagnosedef diagnosedef = new BasDiagnosedef();
                diagnosedef.setDiagDefId(diagDefId);
                diagnosedef.setName(optLatterDiag.getName());
                diagnosedef.setPinYin(PingYinUtil.getFirstSpell(optLatterDiag.getName()));
                diagnosedef.setEnable(1);
                diagnosedef.setBeid(getBeid());
                basDiagnosedefDao.insert(diagnosedef);
			}
			evtOptLatterDiagDao.insert(optLatterDiag);
		}

		}
		String regOptId = "";
		if (optLatterDiagList.size() > 0) {
			DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(optLatterDiagList.get(0).getDocId());
			regOptId = anaesRecord.getRegOptId();
		}
		LogUtils.saveOperateLog(regOptId, LogUtils.OPT_TYPE_INFO_SAVE, LogUtils.OPT_MODULE_OPER_RECORD, "术中诊断保存", JsonType.jsonType(optLatterDiagList), UserUtils.getUserCache(), getBeid());
	}

	/**
	 * 术中诊断事件
	 * 
	 * @param OptLatterDiag
	 */
	@Transactional
	public void insertOptLatterDiag(EvtOptLatterDiag optLatterDiag) {
		optLatterDiag.setOptLatterDiagId(GenerateSequenceUtil.generateSequenceNo());
		evtOptLatterDiagDao.insert(optLatterDiag);
	}

	/**
	 * 术中诊断事件
	 * 
	 * @param OptLatterDiag
	 */
	@Transactional
	public void updateOptLatterDiag(EvtOptLatterDiag optLatterDiag) {
		evtOptLatterDiagDao.updateByPrimaryKeySelective(optLatterDiag);
	}

	/**
	 * 术中诊断事件
	 * 
	 * @param OptLatterDiag
	 */
	@Transactional
	public void deleteOptLatterDiag(EvtOptLatterDiag optLatterDiag) {
		evtOptLatterDiagDao.deleteByPrimaryKey(optLatterDiag.getOptLatterDiagId());
	}
}
