package com.digihealth.anesthesia.evt.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtAnaesEvent;
import com.digihealth.anesthesia.evt.po.EvtCtlBreath;

@Service
public class EvtCtlBreathService extends BaseService {
	
	public List<EvtCtlBreath> searchCtlBreathList(SearchFormBean searchBean) {
		return evtCtlBreathDao.searchCtlBreathList(searchBean);
	}

	@Transactional
	public void saveCtlBreath(EvtCtlBreath ctlBreath,ResponseValue resp) {
		String docId = ctlBreath.getDocId();
        DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(docId);
		//ctlBreath.setState(anaesRecord.getState());
		// 判断当前的呼吸模式是否和页面传递过来的模式相同，如果相同，则不做任何操作，如果不相同，则置为0，再新增一条1
		EvtCtlBreath cb = evtCtlBreathDao.queryCurCtlBreath(docId);
		EvtAnaesEvent rsEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 1);// 麻醉开始
		if (ctlBreath.getStartTime().compareTo(rsEvent.getOccurTime()) < 0)
		{
		    logger.info("saveCtlBreath----呼吸事件的开始时间不能小于入室时间");
            resp.setResultCode("200000000");
            resp.setResultMessage("呼吸事件的开始时间不能小于入室时间!");
            return;
		}
		
		if(null != ctlBreath ){
            String ctlBreId = ctlBreath.getCtlBreId();
            if(StringUtils.isNotBlank(ctlBreId)){
                evtCtlBreathDao.updateByPrimaryKey(ctlBreath);
            }else{
                ctlBreath.setCtlBreId(GenerateSequenceUtil.generateSequenceNo());
                evtCtlBreathDao.insert(ctlBreath);
            }
        }
		
		
		/*if (null != cb) {
			Integer type = ctlBreath.getType();
            logger.info("saveCtlBreath---cb.type===" + cb.getType() + ",ctlBreath.type=" + type);
			if (cb.getType().equals(type)) {
				logger.info("saveCtlBreath----呼吸事件的type一致，无需修改----");
				resp.setResultCode("200000000");
				resp.setResultMessage("呼吸事件的类型一致，无需修改!");
				return;
			} else { // 修改状态、新增记录
				evtCtlBreathDao.updateCurrentState(docId, "0");
				ctlBreath.setCtlBreId(GenerateSequenceUtil.generateSequenceNo());
				ctlBreath.setCurrentState(1);
				evtCtlBreathDao.insertSelective(ctlBreath);
			}
		} else {
			evtCtlBreathDao.updateCurrentState(docId, "0");
			ctlBreath.setCtlBreId(GenerateSequenceUtil.generateSequenceNo());
			ctlBreath.setCurrentState(1);
			evtCtlBreathDao.insert(ctlBreath);
		}*/
		LogUtils.saveOperateLog(anaesRecord.getRegOptId(), LogUtils.OPT_TYPE_INFO_SAVE, LogUtils.OPT_MODULE_INTERFACE, "术中人员呼吸事件保存", JsonType.jsonType(ctlBreath), UserUtils.getUserCache(), getBeid());
	}

	public EvtCtlBreath selCtlBreathCur(String regOptId) {
		return evtCtlBreathDao.selCtlBreathCur(regOptId);
	}

	public List<EvtCtlBreath> searchBreathListOrder(String regOptId) {
		return evtCtlBreathDao.searchBreathListOrder(regOptId);
	}
	
	@Transactional
    public void deleteCtlBreath(EvtCtlBreath ctlBreath) {
	    evtCtlBreathDao.deleteByPrimaryKey(ctlBreath.getCtlBreId());
    }
}
