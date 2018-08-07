package com.digihealth.anesthesia.evt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.po.BasIoDefination;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.exception.CustomException;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.CompareObject;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.ChangeValueFormbean;
import com.digihealth.anesthesia.evt.formbean.RegOptOperIoeventFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchOptOperIoevent;
import com.digihealth.anesthesia.evt.po.EvtAnaesModifyRecord;
import com.digihealth.anesthesia.evt.po.EvtInEvent;

@Service
public class EvtInEventService extends BaseService {

	public List<SearchOptOperIoevent> searchIoeventList(SearchFormBean searchBean) {
	    if (StringUtils.isBlank(searchBean.getBeid()))
        {
            searchBean.setBeid(getBeid());
        }
		return evtInEventDao.searchIoeventList(searchBean);
	}

	public List<RegOptOperIoeventFormBean> searchIoeventGroupByDefIdList(SearchFormBean searchBean) {
	    if (StringUtils.isBlank(searchBean.getBeid()))
        {
            searchBean.setBeid(getBeid());
        }
	    
		// 将相同药品的数据重新封装
		List<RegOptOperIoeventFormBean> resultList = evtInEventDao.searchIoeventGroupByDefIdList(searchBean);
		for (RegOptOperIoeventFormBean regOptOperIoeventFormBean : resultList) {
			// 入量事件
			searchBean.setCode(regOptOperIoeventFormBean.getIoDefId().toString());
			SearchOptOperIoevent soi = evtInEventDao.queryIoeventTotalByDefId(searchBean);
			regOptOperIoeventFormBean.setTotalAmout(soi.getTotalAmout());
			regOptOperIoeventFormBean.setIoeventList(evtInEventDao.searchIoeventList(searchBean));
		}
		return resultList;
	}

	public Integer getIoeventCountValueByIoDef(String docId, String ioDefId, String subType) {
		return evtInEventDao.getIoeventCountValueByIoDef(docId, ioDefId, subType);
	}

	@Transactional
	public void saveIoevent(EvtInEvent ioevent, ResponseValue value) {

		SearchFormBean searchFormBean = new SearchFormBean();
		searchFormBean.setDocId(ioevent.getDocId());
		searchFormBean.setId(ioevent.getInEventId());
		Date ioEndTime = ioevent.getEndTime();
		if (null != ioEndTime)
		{
    		List<EvtInEvent> List = evtInEventDao.checkIoeventCanInsert(searchFormBean, ioevent.getIoDefId() + "");
    		if (null != List && List.size() > 0) {
    			for (EvtInEvent event : List) {
    				Date ioStartTime = ioevent.getStartTime();
    				Date startTime = event.getStartTime();
                    Date endTime = event.getEndTime();
                    if ((ioStartTime.getTime() < startTime.getTime() && ioEndTime.getTime() < endTime.getTime()) || ioStartTime.getTime() > endTime.getTime()) {
                        continue;
                    } 
                    /*else {
                        value.setResultCode("10000001");
                        value.setResultMessage("该输液在开始时间：" + ioevent.getStartTime() + "至结束时间：" + ioevent.getEndTime() + ", 已经存在持续输液情况,请勿重复添加!");
                        return;
                    }*/
    			}
    		}
		}
		
        String inEventId = GenerateSequenceUtil.generateSequenceNo();
        /**
		 * 2017-10-30沈阳本溪
		 * 将修改痕迹保存到表中
		 */
        DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(ioevent.getDocId());
        if(null!=anaesRecord){
        	BasRegOpt regOpt = basRegOptDao.searchRegOptById(anaesRecord.getRegOptId());
	        //如果当前状态不为术中时，则需要记录变更信息
        	if(null!=regOpt && !"04".equals(regOpt.getState())){
	        	if (StringUtils.isNotBlank(ioevent.getInEventId())) {
	        		EvtInEvent oldEvt = evtInEventDao.selectByPrimaryKey(ioevent.getInEventId());
	    			
	    			CompareObject compare = new CompareObject();
	    			List<ChangeValueFormbean> changeList = new ArrayList<ChangeValueFormbean>();
	    			try {
	    				changeList = compare.getCompareResultByFields(oldEvt, ioevent);
	    				if(null!=changeList && changeList.size()>0){
	    					for (ChangeValueFormbean changeValueFormbean : changeList) {
	    						//排除非表内字段产生的差异，如medTakeWayIdList等
	    						Map<String,String> hisMap = compare.getColumnListByTableName("evt_inevent");
	    						if(hisMap.containsKey(changeValueFormbean.getModProperty())){
	    							EvtAnaesModifyRecord evtModRd = new EvtAnaesModifyRecord();
	    							evtModRd.setBeid(getBeid());
	    							evtModRd.setIp(getIP());
	    							evtModRd.setOperId(getUserName());
	    							evtModRd.setEventId(ioevent.getInEventId());
	    							evtModRd.setRegOptId(anaesRecord.getRegOptId());
	    							evtModRd.setModifyDate(new Date());
	    							evtModRd.setModTable("evt_inevent(入量药品事件)");
	    							evtModRd.setOperModule("术中输液("+basIoDefinationDao.selectByPrimaryKey(ioevent.getIoDefId()).getName()+")");
	    							evtModRd.setId(GenerateSequenceUtil.generateSequenceNo());
	    							evtModRd.setModProperty(compare.getColumnContentByProperty("evt_inevent", changeValueFormbean.getModProperty()));
	    							evtModRd.setOldValue(changeValueFormbean.getOldValue());
	    							evtModRd.setNewValue(changeValueFormbean.getNewValue());
	    							evtModRd.setRemark("修改");
	    							evtAnaesModifyRecordDao.insert(evtModRd);
	    						}
	    					}
	    				}
	    			} catch (Exception e) {
	    				logger.info("------getCompareResultByFields-----"+Exceptions.getStackTraceAsString(e));
	    				throw new CustomException(Exceptions.getStackTraceAsString(e));
	    			}
	        	}else{
	        		EvtAnaesModifyRecord evtModRd = new EvtAnaesModifyRecord();
	    			evtModRd.setBeid(getBeid());
	    			evtModRd.setIp(getIP());
	    			evtModRd.setOperId(getUserName());
	    			evtModRd.setEventId(inEventId);
	    			evtModRd.setRegOptId(anaesRecord.getRegOptId());
	    			evtModRd.setModifyDate(new Date());
	    			evtModRd.setModTable("evt_inevent(入量药品事件)");
					evtModRd.setOperModule("术中输液");
	    			evtModRd.setId(GenerateSequenceUtil.generateSequenceNo());
	    			evtModRd.setModProperty("新增输液("+basIoDefinationDao.selectByPrimaryKey(ioevent.getIoDefId()).getName()+")");
	    			StringBuffer buffer = new StringBuffer();
					buffer.append("开始时间:"+DateUtils.formatDateTime(ioevent.getStartTime()));
					if(null!=ioevent.getDosageAmount() && ioevent.getDosageAmount()>0){
						buffer.append("; 数量:"+ioevent.getDosageAmount());
					}
					evtModRd.setNewValue(buffer.toString());
	    			evtModRd.setRemark("新增");
	    			evtAnaesModifyRecordDao.insert(evtModRd);
	        	}
	        }
        }

		if (StringUtils.isNotBlank(ioevent.getInEventId())) {
			evtInEventDao.updateByPrimaryKeySelective(ioevent);
		} else {
			ioevent.setInEventId(inEventId);
			evtInEventDao.insert(ioevent);
		}
		LogUtils.saveOperateLog(ioevent.getDocId(), LogUtils.OPT_TYPE_INFO_SAVE, LogUtils.OPT_MODULE_INTERFACE, "术中入量事件保存", JsonType.jsonType(ioevent), UserUtils.getUserCache(), getBeid());

	}

	@Transactional
	public void batchSaveIoevent(List<EvtInEvent> ioeventList, ResponseValue value) {
		if (null != ioeventList && ioeventList.size() > 0) {
			List<String> successList = new ArrayList<String>();
			List<String> failList = new ArrayList<String>();
			for (EvtInEvent ioevent : ioeventList) {
				SearchFormBean searchFormBean = new SearchFormBean();
				searchFormBean.setDocId(ioevent.getDocId());
				searchFormBean.setId(ioevent.getInEventId());
				Date ioEndTime = ioevent.getEndTime();
				boolean flag = false;
				if (null != ioEndTime)
				{
    				List<EvtInEvent> List = evtInEventDao.checkIoeventCanInsert(searchFormBean, ioevent.getIoDefId() + "");
    				for (EvtInEvent event : List) {
    				    if ((ioevent.getStartTime().compareTo(event.getStartTime()) < 0 
                            && ioEndTime.compareTo(event.getStartTime()) < 0) || ioevent.getStartTime().compareTo(event.getEndTime()) > 0) {
                            continue;
                        } else {
    						// value.setResultCode("10000001");
    						// value.setResultMessage("该输液在开始时间：" +
    						// ioevent.getStarttime() + "至结束时间：" +
    						// ioevent.getEndtime()
    						// + ", 已经存在持续输液情况,请勿重复添加!");
    						// return;
    						flag = true;
    					}
    				}
				}

				BasIoDefination ioDefination = basIoDefinationDao.selectByPrimaryKey(ioevent.getIoDefId() + "");

				if (flag) {
					failList.add(ioDefination.getName());
					continue;
				}

				if (StringUtils.isBlank(ioevent.getInEventId())) {
					ioevent.setInEventId(GenerateSequenceUtil.generateSequenceNo());
					evtInEventDao.insert(ioevent);
					successList.add(ioDefination.getName());
				}
			}
			value.put("success", successList);
			value.put("fail", failList);
		}
	}

	/**
	 * 新增出入量事件
	 * 
	 * @param Anaesevent
	 */
	@Transactional
	public void insertIoevent(EvtInEvent ioevent) {
		ioevent.setInEventId(GenerateSequenceUtil.generateSequenceNo());
		evtInEventDao.insert(ioevent);
	}

	/**
	 * 修改出入量事件
	 * 
	 * @param Anaesevent
	 */
	@Transactional
	public void updateIoevent(EvtInEvent ioevent) {
		evtInEventDao.updateByPrimaryKeySelective(ioevent);
	}

	/**
	 * 删除出入量事件
	 * 
	 * @param ioevent
	 */
	@Transactional
	public void deleteIoevent(EvtInEvent ioevent) {
		
		EvtInEvent evtInEvent = evtInEventDao.selectByPrimaryKey(ioevent.getInEventId());
		
		int cnt = evtInEventDao.deleteByPrimaryKey(ioevent.getInEventId());
		
		if(cnt>0){
			 /**
			 * 2017-10-30沈阳本溪
			 * 将修改痕迹保存到表中
			 */
			DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(evtInEvent.getDocId());
	        if(null!=anaesRecord){
	        	BasRegOpt regOpt = basRegOptDao.searchRegOptById(anaesRecord.getRegOptId());
		        //如果当前状态不为术中时，则需要记录变更信息
	        	if(null!=regOpt && !"04".equals(regOpt.getState())){
					EvtAnaesModifyRecord evtModRd = new EvtAnaesModifyRecord();
					evtModRd.setBeid(getBeid());
					evtModRd.setIp(getIP());
					evtModRd.setOperId(getUserName());
					evtModRd.setEventId(evtInEvent.getInEventId());
					evtModRd.setRegOptId(anaesRecord.getRegOptId());
					evtModRd.setModifyDate(new Date());
					evtModRd.setModTable("evt_inevent(入量药品事件)");
					evtModRd.setOperModule("术中输液");
					evtModRd.setId(GenerateSequenceUtil.generateSequenceNo());
					evtModRd.setModProperty("删除输液("+basIoDefinationDao.selectByPrimaryKey(evtInEvent.getIoDefId()).getName()+")");
					
					StringBuffer buffer = new StringBuffer();
					buffer.append("开始时间:"+DateUtils.formatDateTime(evtInEvent.getStartTime()));
					if(null!=evtInEvent.getDosageAmount() && evtInEvent.getDosageAmount()>0){
						buffer.append("; 数量:"+evtInEvent.getDosageAmount());
					}
					evtModRd.setOldValue(evtInEvent.getDosageAmount()+"");
					evtModRd.setRemark("删除");
					evtAnaesModifyRecordDao.insert(evtModRd);
	        	}
	        }
		}
	}

	public String seleteIoeventSumByDocId(String docId) {
		return evtInEventDao.seleteIoeventSumByDocId(docId,getBeid());
	}

	public String seleteEgressSumByDocId(String docId) {
		return evtInEventDao.seleteEgressSumByDocId(docId);
	}

	public String seleteEmictionSumByDocId(String docId) {
		return evtInEventDao.seleteEmictionSumByDocId(docId);
	}

	public String seleteBloodSumByDocId(String docId) {
		return evtInEventDao.seleteBloodSumByDocId(docId);
	}

	public String seleteOtherSumByDocId(String docId) {
		return evtInEventDao.seleteOtherSumByDocId(docId);
	}

	public String getBloodByDocId(@Param("docId") String docId) {
		return evtInEventDao.getBloodByDocId(docId);
	}
	
	public List<SearchOptOperIoevent> queryIoeventTotal(SearchFormBean searchBean) {
        return evtInEventDao.queryIoeventTotal(searchBean);
    }
	
	public EvtInEvent selectById(String inEventId)
	{
	    return evtInEventDao.selectByPrimaryKey(inEventId);
	}

	/**
	 * 本溪项目同一种输液不能在一个时间段内重复
	 * @param ioevent
	 * @param value
	 */
	@Transactional
	public void saveIoeventSYBX(EvtInEvent ioevent, ResponseValue value) {
		SearchFormBean searchFormBean = new SearchFormBean();
		searchFormBean.setDocId(ioevent.getDocId());
		searchFormBean.setId(ioevent.getInEventId());
		Date ioEndTime = ioevent.getEndTime();
		if (null != ioEndTime)
		{
    		List<EvtInEvent> List = evtInEventDao.checkIoeventCanInsert(searchFormBean, ioevent.getIoDefId() + "");
    		if (null != List && List.size() > 0) {
    			for (EvtInEvent event : List) {
    				Date ioStartTime = ioevent.getStartTime();
    				Date startTime = event.getStartTime();
                    Date endTime = event.getEndTime();
                    
                    if ((ioStartTime.getTime() < startTime.getTime() && ioEndTime.getTime() < startTime.getTime()) || ioStartTime.getTime() > endTime.getTime()) {
                        continue;
                    }else {
                        value.setResultCode("10000001");
                        value.setResultMessage("该输液在开始时间：" + DateUtils.formatDateTime(ioevent.getStartTime()) + "至结束时间：" + DateUtils.formatDateTime(ioevent.getEndTime()) + ", 已经存在持续输液情况,请勿重复添加!");
                        return;
                    }
    			}
    		}
		}
		
        String inEventId = GenerateSequenceUtil.generateSequenceNo();
        /**
		 * 2017-10-30沈阳本溪
		 * 将修改痕迹保存到表中
		 */
        DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(ioevent.getDocId());
        if(null!=anaesRecord){
        	BasRegOpt regOpt = basRegOptDao.searchRegOptById(anaesRecord.getRegOptId());
	        //如果当前状态不为术中时，则需要记录变更信息
        	if(null!=regOpt && !"04".equals(regOpt.getState())){
	        	if (StringUtils.isNotBlank(ioevent.getInEventId())) {
	        		EvtInEvent oldEvt = evtInEventDao.selectByPrimaryKey(ioevent.getInEventId());
	    			
	    			CompareObject compare = new CompareObject();
	    			List<ChangeValueFormbean> changeList = new ArrayList<ChangeValueFormbean>();
	    			try {
	    				changeList = compare.getCompareResultByFields(oldEvt, ioevent);
	    				if(null!=changeList && changeList.size()>0){
	    					for (ChangeValueFormbean changeValueFormbean : changeList) {
	    						//排除非表内字段产生的差异，如medTakeWayIdList等
	    						Map<String,String> hisMap = compare.getColumnListByTableName("evt_inevent");
	    						if(hisMap.containsKey(changeValueFormbean.getModProperty())){
	    							EvtAnaesModifyRecord evtModRd = new EvtAnaesModifyRecord();
	    							evtModRd.setBeid(getBeid());
	    							evtModRd.setIp(getIP());
	    							evtModRd.setOperId(getUserName());
	    							evtModRd.setEventId(ioevent.getInEventId());
	    							evtModRd.setRegOptId(anaesRecord.getRegOptId());
	    							evtModRd.setModifyDate(new Date());
	    							evtModRd.setModTable("evt_inevent(入量药品事件)");
	    							evtModRd.setOperModule("术中输液("+basIoDefinationDao.selectByPrimaryKey(ioevent.getIoDefId()).getName()+")");
	    							evtModRd.setId(GenerateSequenceUtil.generateSequenceNo());
	    							evtModRd.setModProperty(compare.getColumnContentByProperty("evt_inevent", changeValueFormbean.getModProperty()));
	    							evtModRd.setOldValue(changeValueFormbean.getOldValue());
	    							evtModRd.setNewValue(changeValueFormbean.getNewValue());
	    							evtModRd.setRemark("修改");
	    							evtAnaesModifyRecordDao.insert(evtModRd);
	    						}
	    					}
	    				}
	    			} catch (Exception e) {
	    				logger.info("------getCompareResultByFields-----"+Exceptions.getStackTraceAsString(e));
	    				throw new CustomException(Exceptions.getStackTraceAsString(e));
	    			}
	        	}else{
	        		EvtAnaesModifyRecord evtModRd = new EvtAnaesModifyRecord();
	    			evtModRd.setBeid(getBeid());
	    			evtModRd.setIp(getIP());
	    			evtModRd.setOperId(getUserName());
	    			evtModRd.setEventId(inEventId);
	    			evtModRd.setRegOptId(anaesRecord.getRegOptId());
	    			evtModRd.setModifyDate(new Date());
	    			evtModRd.setModTable("evt_inevent(入量药品事件)");
					evtModRd.setOperModule("术中输液");
	    			evtModRd.setId(GenerateSequenceUtil.generateSequenceNo());
	    			evtModRd.setModProperty("新增输液("+basIoDefinationDao.selectByPrimaryKey(ioevent.getIoDefId()).getName()+")");
	    			StringBuffer buffer = new StringBuffer();
					buffer.append("开始时间:"+DateUtils.formatDateTime(ioevent.getStartTime()));
					if(null!=ioevent.getDosageAmount() && ioevent.getDosageAmount()>0){
						buffer.append("; 数量:"+ioevent.getDosageAmount());
					}
					evtModRd.setNewValue(buffer.toString());
	    			evtModRd.setRemark("新增");
	    			evtAnaesModifyRecordDao.insert(evtModRd);
	        	}
	        }
        }

		if (StringUtils.isNotBlank(ioevent.getInEventId())) {
			evtInEventDao.updateByPrimaryKeySelective(ioevent);
		} else {
			ioevent.setInEventId(inEventId);
			evtInEventDao.insert(ioevent);
		}
		LogUtils.saveOperateLog(ioevent.getDocId(), LogUtils.OPT_TYPE_INFO_SAVE, LogUtils.OPT_MODULE_INTERFACE, "术中入量事件保存", JsonType.jsonType(ioevent), UserUtils.getUserCache(), getBeid());
	}

	@Transactional
	public void batchSaveIoeventSYBX(List<EvtInEvent> ioeventList, ResponseValue value) {
		if (null != ioeventList && ioeventList.size() > 0) {
			List<String> successList = new ArrayList<String>();
			List<String> failList = new ArrayList<String>();
			for (EvtInEvent ioevent : ioeventList) {
				SearchFormBean searchFormBean = new SearchFormBean();
				searchFormBean.setDocId(ioevent.getDocId());
				searchFormBean.setId(ioevent.getInEventId());
				Date ioEndTime = ioevent.getEndTime();
				boolean flag = false;
				if (null != ioEndTime)
				{
    				List<EvtInEvent> List = evtInEventDao.checkIoeventCanInsert(searchFormBean, ioevent.getIoDefId() + "");
    				for (EvtInEvent event : List) {
    					
    					if ((ioevent.getStartTime().compareTo(event.getStartTime()) < 0 
                        		&& ioEndTime.compareTo(event.getStartTime()) < 0) || ioevent.getStartTime().compareTo(event.getEndTime()) > 0) {
                            continue;
                        }else {
    						// value.setResultCode("10000001");
    						// value.setResultMessage("该输液在开始时间：" +
    						// ioevent.getStarttime() + "至结束时间：" +
    						// ioevent.getEndtime()
    						// + ", 已经存在持续输液情况,请勿重复添加!");
    						// return;
    						flag = true;
    					}
    				}
				}

				BasIoDefination ioDefination = basIoDefinationDao.selectByPrimaryKey(ioevent.getIoDefId() + "");

				if (flag) {
					failList.add(ioDefination.getName());
					continue;
				}

				if (StringUtils.isBlank(ioevent.getInEventId())) {
					ioevent.setInEventId(GenerateSequenceUtil.generateSequenceNo());
					evtInEventDao.insert(ioevent);
					successList.add(ioDefination.getName());
				}
			}
			value.put("success", successList);
			value.put("fail", failList);
		}
	}
}
