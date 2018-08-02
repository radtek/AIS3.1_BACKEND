package com.digihealth.anesthesia.tmp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.BeanHelper;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.evt.po.EvtMedicalEvent;
import com.digihealth.anesthesia.tmp.formbean.TmpAnalgesicFromBean;
import com.digihealth.anesthesia.tmp.po.TmpAnalgesic;

@Service
public class TmpAnalgesicService extends BaseService
{

	//保存镇痛模板
	@Transactional
	public void saveTmpAnalgesic(TmpAnalgesicFromBean tmpAnalgesicFromBean)
	{
		if(null != tmpAnalgesicFromBean)
		{
			String beid = getBeid();
			String analgesicType = tmpAnalgesicFromBean.getAnalgesicType();
			if(StringUtils.isNotBlank(analgesicType))
			{
				List<TmpAnalgesic> tmpAnalgesicList = tmpAnalgesicFromBean.getTmpAnalgesicList();
				if(null != tmpAnalgesicList && tmpAnalgesicList.size()>0)
				{
					//前台传过来的analgesicType 和 List里的要保持一致
					if(analgesicType.equals(tmpAnalgesicList.get(0).getAnalgesicType()))
					{
						//先删除原来的数据
						tmpAnalgesicDao.deleteTmpAnalgesicByType(analgesicType,beid);
						//插入新数据
						for(TmpAnalgesic tmpAnalgesic : tmpAnalgesicList)
						{
							tmpAnalgesic.setAnalTmpId(GenerateSequenceUtil.generateSequenceNo());
							tmpAnalgesic.setBeid(getBeid());
							tmpAnalgesicDao.insert(tmpAnalgesic);
						}
					}
				}
				//只传了类型过来，没有列表或者列表为0，就是删除这个模板
				if(null == tmpAnalgesicList || (null != tmpAnalgesicList && tmpAnalgesicList.size() == 0))
				{
					tmpAnalgesicDao.deleteTmpAnalgesicByType(analgesicType,beid);
				}
			}
		}
	}
	
	//通过analgesicType查询镇痛模板
	public List<TmpAnalgesic> selectTmpAnalgesicByType(String analgesicType)
	{
		String beid = getBeid();
		List<TmpAnalgesic> tmpAnalgesicList = null;
		if(StringUtils.isNotBlank(analgesicType))
		{
			tmpAnalgesicList = tmpAnalgesicDao.selectTmpAnalgesicByType(analgesicType,beid);
		}
		return tmpAnalgesicList;
	}
	
	//删除模板
	@Transactional
	public void delTmpAnalgesicByType(String analgesicType)
	{
		String beid = getBeid();
		if(StringUtils.isNotBlank(analgesicType))
		{
			tmpAnalgesicDao.deleteTmpAnalgesicByType(analgesicType,beid);
		}
	}
	
	//使用镇痛模板
	public List<EvtMedicalEvent> useTmpAnalgesicByType(String analgesicType, EvtMedicalEvent evtMedicalEvent)
	{
		List<EvtMedicalEvent> evtMedicalEventList = new ArrayList<EvtMedicalEvent>();
		String beid = getBeid();
		if(StringUtils.isNotBlank(analgesicType) && null != evtMedicalEvent)
		{
			List<TmpAnalgesic> tmpAnalgesicList = tmpAnalgesicDao.selectTmpAnalgesicByType(analgesicType, beid);
			if(null != tmpAnalgesicList && tmpAnalgesicList.size()>0)
			{
				for(TmpAnalgesic tmpAnalgesic : tmpAnalgesicList)
				{
					EvtMedicalEvent newEvtMedicalEvent = new EvtMedicalEvent();
					BeanHelper.copyProperties(evtMedicalEvent, newEvtMedicalEvent);
					newEvtMedicalEvent.setMedicineId(tmpAnalgesic.getMedicineId());
					newEvtMedicalEvent.setDosage(Float.valueOf(tmpAnalgesic.getDosage()));
					newEvtMedicalEvent.setPriceId(tmpAnalgesic.getPriceId());
					newEvtMedicalEvent.setMedTakeWayId(tmpAnalgesic.getMedTakeWayId());
					evtMedicalEventList.add(newEvtMedicalEvent);
				}
			}
		}
		return evtMedicalEventList;
	}
	
}
