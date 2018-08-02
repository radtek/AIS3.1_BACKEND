package com.digihealth.anesthesia.evt.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.exception.CustomException;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.CompareObject;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.ChangeValueFormbean;
import com.digihealth.anesthesia.evt.formbean.RegOptOperEgressFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchOptOperEgress;
import com.digihealth.anesthesia.evt.po.EvtAnaesModifyRecord;
import com.digihealth.anesthesia.evt.po.EvtEgress;
import com.digihealth.anesthesia.evt.po.EvtInEvent;

@Service
public class EvtEgressService extends BaseService {

	public List<SearchOptOperEgress> searchEgressList(SearchFormBean searchBean) {
	    if (StringUtils.isBlank(searchBean.getBeid()))
	    {
	        searchBean.setBeid(getBeid());
	    }
		return evtEgressDao.searchEgressList(searchBean);
	}

	public List<RegOptOperEgressFormBean> searchEgressGroupByDefIdList(SearchFormBean searchBean) {
	    if (StringUtils.isBlank(searchBean.getBeid()))
        {
            searchBean.setBeid(getBeid());
        }
	    
		// 将相同药品的数据重新封装
		List<RegOptOperEgressFormBean> resultList = evtEgressDao.searchEgressGroupByDefIdList(searchBean);
		RegOptOperEgressFormBean bean = null;
		if (null != resultList && resultList.size() > 0) {
			for (int i = 0; i < resultList.size(); i++) {
				RegOptOperEgressFormBean regOptOperEgressFormBean = resultList.get(i);
				// 出量事件
				String name = regOptOperEgressFormBean.getName();
				if (name.equals("其他")) {
					bean = regOptOperEgressFormBean;
					// resultList.remove(regOptOperEgressFormBean); //移除为其他的数据
					continue;
				}
				searchBean.setCode(regOptOperEgressFormBean.getIoDefId().toString());
				regOptOperEgressFormBean.setEgressList(evtEgressDao.searchEgressList(searchBean));
				regOptOperEgressFormBean.setTotalAmout(evtEgressDao.getEgressCountValueByIoDef (searchBean.getCode(), searchBean.getDocId())+"");
			}
		}
		resultList.remove(bean);
		
		/*for (RegOptOperEgressFormBean regOptOperEgressFormBean : resultList) { // 出量事件
			String name = regOptOperEgressFormBean.getName();
		
			if (name.equals("其他")) {
				bean = regOptOperEgressFormBean;
				resultList.remove(regOptOperEgressFormBean); // 移除为其他的数据 continue; }
				searchBean.setCode(regOptOperEgressFormBean.getIoDefId().toString());
				regOptOperEgressFormBean.setEgressList(evtEgressDao.searchEgressList(searchBean));
			}
		}*/
		 
		if (null != bean) {
			SearchFormBean sb = new SearchFormBean();
			String docId = searchBean.getDocId();
			String ioDefId = bean.getIoDefId().toString();
			sb.setDocId(docId);
			sb.setCode(ioDefId);
			List<SearchOptOperEgress> searchEgressList = evtEgressDao.searchEgressList(sb);
			List<SearchOptOperEgress> egressList = null;
			RegOptOperEgressFormBean b = null;
			if (null != searchEgressList && searchEgressList.size() > 0) {
				for (SearchOptOperEgress searchOptOperEgress : searchEgressList) {
					b = new RegOptOperEgressFormBean();
					b.setName(searchOptOperEgress.getName());
					b.setIoDefId(bean.getIoDefId());
					b.setUnit(bean.getUnit());
					b.setTotalAmout(evtEgressDao.getEgressCountValueByIoDef(searchBean.getCode(), searchBean.getDocId()).toString());
					egressList = new ArrayList<SearchOptOperEgress>();
					egressList.add(searchOptOperEgress);
					b.setEgressList(egressList);
					resultList.add(b);
				}
			}
		}
		return resultList;
	}

	public List<EvtEgress> queryEgressListById(SearchFormBean searchBean) {
		return evtEgressDao.queryEgressListById(searchBean);
	}

	public Integer getEgressCountValueByIoDef(String ioDefId, String docId) {
		return evtEgressDao.getEgressCountValueByIoDef(ioDefId, docId);
	}

	@Transactional
	public void saveEgress(EvtEgress egress) {
		DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(egress.getDocId());
		
		String egressId = GenerateSequenceUtil.generateSequenceNo();
        /**
		 * 2017-10-30沈阳本溪
		 * 将修改痕迹保存到表中
		 */
        if(null!=anaesRecord){
        	BasRegOpt regOpt = basRegOptDao.searchRegOptById(anaesRecord.getRegOptId());
	        //如果当前状态不为术中时，则需要记录变更信息
        	if(null!=regOpt && !"04".equals(regOpt.getState())){
	        	if (StringUtils.isNotBlank(egress.getEgressId())) {
	        		EvtEgress oldEvt = evtEgressDao.selectByPrimaryKey(egress.getEgressId());
	    			
	    			CompareObject compare = new CompareObject();
	    			List<ChangeValueFormbean> changeList = new ArrayList<ChangeValueFormbean>();
	    			try {
	    				changeList = compare.getCompareResultByFields(oldEvt, egress);
	    				if(null!=changeList && changeList.size()>0){
	    					for (ChangeValueFormbean changeValueFormbean : changeList) {
	    						//排除非表内字段产生的差异，如medTakeWayIdList等
	    						Map<String,String> hisMap = compare.getColumnListByTableName("evt_egress");
	    						if(hisMap.containsKey(changeValueFormbean.getModProperty())){
	    							EvtAnaesModifyRecord evtModRd = new EvtAnaesModifyRecord();
	    							evtModRd.setBeid(getBeid());
	    							evtModRd.setIp(getIP());
	    							evtModRd.setOperId(getUserName());
	    							evtModRd.setEventId(egress.getEgressId());
	    							evtModRd.setRegOptId(anaesRecord.getRegOptId());
	    							evtModRd.setModifyDate(new Date());
	    							evtModRd.setModTable("evt_egress(出量事件)");
	    							evtModRd.setOperModule("术中出量("+basIoDefinationDao.selectByPrimaryKey(egress.getIoDefId()).getName()+")");
	    							evtModRd.setId(GenerateSequenceUtil.generateSequenceNo());
	    							evtModRd.setModProperty(compare.getColumnContentByProperty("evt_egress", changeValueFormbean.getModProperty()));
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
	    			evtModRd.setEventId(egressId);
	    			evtModRd.setRegOptId(anaesRecord.getRegOptId());
	    			evtModRd.setModifyDate(new Date());
					evtModRd.setModTable("evt_egress(出量事件)");
					evtModRd.setOperModule("术中出量");
	    			evtModRd.setId(GenerateSequenceUtil.generateSequenceNo());
	    			evtModRd.setModProperty("新增出量("+basIoDefinationDao.selectByPrimaryKey(egress.getIoDefId()).getName()+")");
	    			
	    			StringBuffer buffer = new StringBuffer();
					buffer.append("开始时间:"+DateUtils.formatDateTime(egress.getStartTime()));
					if(StringUtils.isNotBlank(egress.getValue())){
						buffer.append("; 数量:"+egress.getValue());
					}
					evtModRd.setNewValue(buffer.toString());
	    			evtModRd.setRemark("新增");
	    			evtAnaesModifyRecordDao.insert(evtModRd);
	        	}
	        }
        }
		
		
		
		if (StringUtils.isNotBlank(egress.getEgressId())) {
			evtEgressDao.updateByPrimaryKeySelective(egress);
		} else {
			egress.setEgressId(egressId);
			evtEgressDao.insert(egress);
		}
		LogUtils.saveOperateLog(anaesRecord.getRegOptId(), LogUtils.OPT_TYPE_INFO_SAVE, LogUtils.OPT_MODULE_INTERFACE, "术中人员出量事件保存", JsonType.jsonType(egress), UserUtils.getUserCache(), getBeid());
	}

	/**
	 * 新增出量事件
	 * 
	 * @param Anaesevent
	 */
	@Transactional
	public void insertEgress(EvtEgress egress) {
		egress.setEgressId(GenerateSequenceUtil.generateSequenceNo());
		evtEgressDao.insert(egress);
	}

	/**
	 * 修改出量事件
	 * 
	 * @param Anaesevent
	 */
	@Transactional
	public void updateEgress(EvtEgress egress) {
		evtEgressDao.updateByPrimaryKeySelective(egress);
	}

	/**
	 * 删除出入量事件
	 * 
	 * @param Egress
	 */
	@Transactional
	public void deleteEgress(EvtEgress egress) {
		
		EvtEgress evtEgress = evtEgressDao.selectByPrimaryKey(egress.getEgressId());
		
		int cnt = evtEgressDao.deleteByPrimaryKey(egress.getEgressId());
		if(cnt>0){
			/**
			 * 2017-10-30沈阳本溪
			 * 将修改痕迹保存到表中
			 */
			DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordById(evtEgress.getDocId());
	        if(null!=anaesRecord){
	        	BasRegOpt regOpt = basRegOptDao.searchRegOptById(anaesRecord.getRegOptId());
		        //如果当前状态不为术中时，则需要记录变更信息
	        	if(null!=regOpt && !"04".equals(regOpt.getState())){
	        		EvtAnaesModifyRecord evtModRd = new EvtAnaesModifyRecord();
	        		evtModRd.setId(GenerateSequenceUtil.generateSequenceNo());
	        		evtModRd.setBeid(getBeid());
	        		evtModRd.setIp(getIP());
	        		evtModRd.setOperId(getUserName());
	        		evtModRd.setEventId(evtEgress.getEgressId());
	        		evtModRd.setRegOptId(anaesRecord.getRegOptId());
	        		evtModRd.setModifyDate(new Date());
	        		evtModRd.setModTable("evt_egress(出量事件)");
	        		evtModRd.setOperModule("术中出量");
	        		evtModRd.setModProperty("删除出量("+basIoDefinationDao.selectByPrimaryKey(evtEgress.getIoDefId()).getName()+")");
	        		
	        		StringBuffer buffer = new StringBuffer();
					buffer.append("开始时间:"+DateUtils.formatDateTime(evtEgress.getStartTime()));
					if(StringUtils.isNotBlank(evtEgress.getValue())){
						buffer.append("; 数量:"+evtEgress.getValue());
					}
	        		evtModRd.setOldValue(buffer.toString());
	        		evtModRd.setRemark("删除");
	        		evtAnaesModifyRecordDao.insert(evtModRd);
	        	}
	        }
		}
	}
	
	public Integer getEgressCountValueByIoDefName(String name, String docId)
	{
	    return evtEgressDao.getEgressCountValueByIoDefName(name, docId);
	}
}
