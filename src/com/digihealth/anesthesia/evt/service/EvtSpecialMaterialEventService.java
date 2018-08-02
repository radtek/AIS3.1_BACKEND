package com.digihealth.anesthesia.evt.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.po.EvtSpecialMaterialEvent;

@Service
public class EvtSpecialMaterialEventService extends BaseService
{
    //查询术中特殊材料事件
	public List<EvtSpecialMaterialEvent> selectSpecialMaterialEvent(SearchFormBean searchBean)
	{
		return evtSpecialMaterialEventDao.selectSpecialMaterialEvent(searchBean);
	}
	
	@Transactional
	public void saveSpecialMaterialEvent(EvtSpecialMaterialEvent evtSpecialMaterialEvent)
	{
		if(null != evtSpecialMaterialEvent)
		{
			String specialMaterialEventId = evtSpecialMaterialEvent.getSpecialMaterialEventId();
			if(StringUtils.isNotBlank(specialMaterialEventId))
			{
				evtSpecialMaterialEventDao.updateByPrimaryKeySelective(evtSpecialMaterialEvent);
			}else
			{
				evtSpecialMaterialEvent.setSpecialMaterialEventId(GenerateSequenceUtil.generateSequenceNo());
				evtSpecialMaterialEventDao.insert(evtSpecialMaterialEvent);
			}
			
			DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(evtSpecialMaterialEvent.getDocId());
			if(null != anaesRecord)
			{
				LogUtils.saveOperateLog(anaesRecord.getRegOptId(), LogUtils.OPT_TYPE_INFO_SAVE,
			            LogUtils.OPT_MODULE_OPER_RECORD,"术中特殊材料事件保存", JsonType.jsonType(evtSpecialMaterialEvent), UserUtils.getUserCache(), getBeid());
			}
		}
	}
	
	@Transactional
	public void deleteSpecialMaterialEvent(EvtSpecialMaterialEvent evtSpecialMaterialEvent)
	{
		if(null != evtSpecialMaterialEvent)
		{
			evtSpecialMaterialEventDao.deleteByPrimaryKey(evtSpecialMaterialEvent.getSpecialMaterialEventId());
		}
	}
}
