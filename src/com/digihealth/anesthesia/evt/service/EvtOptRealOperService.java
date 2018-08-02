package com.digihealth.anesthesia.evt.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.OperDefFormBean;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;

@Service
public class EvtOptRealOperService extends BaseService {

	public List<OperDefFormBean> getSelectOptRealOperList(SearchFormBean searchBean) {
		if (StringUtils.isBlank(searchBean.getBeid())) {
			searchBean.setBeid(getBeid());
		}
		return evtOptRealOperDao.getSelectOptRealOperList(searchBean);
	}

	public List<EvtOptRealOper> searchOptRealOperList(SearchFormBean searchBean) {
		if (StringUtils.isBlank(searchBean.getBeid())) {
			searchBean.setBeid(getBeid());
		}
		return evtOptRealOperDao.searchOptRealOperList(searchBean);
	}

	/**
	 * 实施手术
	 * 
	 * @param OptRealOper
	 */
	@Transactional
    public void saveOptRealOper(List<EvtOptRealOper> optRealOperList)
    {
        if (null != optRealOperList && optRealOperList.size() > 0)
        {
            String docId = optRealOperList.get(0).getDocId();
            evtOptRealOperDao.deleteByDocId(docId);
            for (EvtOptRealOper optRealOper : optRealOperList)
            {
                if (StringUtils.isBlank(optRealOper.getOperDefId()) && StringUtils.isBlank(optRealOper.getName()))
                {
                    continue;
                }
                
                if (StringUtils.isBlank(optRealOper.getOptRealOperId()))
                {
                    optRealOper.setOptRealOperId(GenerateSequenceUtil.generateSequenceNo()); 
                }
                
                if (StringUtils.isBlank(optRealOper.getOperDefId()))
                {
                    String operDefId = GenerateSequenceUtil.generateSequenceNo();
                    optRealOper.setOperDefId(operDefId);
                    
                    BasOperdef operdef = new BasOperdef();
                    operdef.setOperdefId(operDefId);
                    operdef.setName(optRealOper.getName());
                    operdef.setPinYin(PingYinUtil.getFirstSpell(optRealOper.getName()));
                    operdef.setEnable(1);
                    operdef.setBeid(getBeid());
                    basOperdefDao.insert(operdef);
                }
                evtOptRealOperDao.insert(optRealOper);
            }
        }
        
        String regOptId = "";
        if (optRealOperList.size() > 0)
        {
            DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(optRealOperList.get(0).getDocId());
            regOptId = anaesRecord.getRegOptId();
        }
        
        LogUtils.saveOperateLog(regOptId,
            LogUtils.OPT_TYPE_INFO_SAVE,
            LogUtils.OPT_MODULE_OPER_RECORD,
            "实施手术保存",
            JsonType.jsonType(optRealOperList),
            UserUtils.getUserCache(),
            getBeid());
    }

	/**
	 * 检验事件
	 * 
	 * @param OptRealOper
	 */
	@Transactional
	public void insertOptRealOper(EvtOptRealOper optRealOper) {
		optRealOper.setOptRealOperId(GenerateSequenceUtil.generateSequenceNo());
		evtOptRealOperDao.insert(optRealOper);
	}

	/**
	 * 检验事件
	 * 
	 * @param OptRealOper
	 */
	@Transactional
	public void updateOptRealOper(EvtOptRealOper optRealOper) {
		evtOptRealOperDao.updateByPrimaryKeySelective(optRealOper);
	}

	/**
	 * 删除
	 * 
	 * @param optRealOper
	 */
	@Transactional
	public void deleteOptRealOper(EvtOptRealOper optRealOper) {
		evtOptRealOperDao.deleteByPrimaryKey(optRealOper.getOptRealOperId());
	}
}
