/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:32:33    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.PacuBedEventConfigFormBean;
import com.digihealth.anesthesia.basedata.formbean.PacuDeviceConfigFormBean;
import com.digihealth.anesthesia.basedata.formbean.PacuDeviceEventFormBean;
import com.digihealth.anesthesia.basedata.formbean.PacuDeviceSpecFormBean;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasPacuBedEventConfig;
import com.digihealth.anesthesia.basedata.po.BasPacuDeviceConfig;
import com.digihealth.anesthesia.basedata.po.BasPacuMonitorConfig;
import com.digihealth.anesthesia.basedata.po.BasRegionBed;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.formbean.AnaesPacuChangeBedFormBean;
import com.digihealth.anesthesia.doc.formbean.AnaesPacuRecFormBean;
import com.digihealth.anesthesia.doc.po.DocAnaesPacuRec;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.evt.po.EvtAnaesEvent;
import com.digihealth.anesthesia.evt.service.EvtAnaesEventService;
import com.digihealth.anesthesia.operProceed.formbean.CmdMsg;
import com.digihealth.anesthesia.pacu.core.MyConstants;
import com.digihealth.anesthesia.pacu.datasync.MsgProcess;

/**
 * Title: PreVisitService.java Description: 描述
 * 
 * @author chengwang
 * @created 2015-10-10 下午5:32:33
 */
@Service
public class DocAnaesPacuRecService extends BaseService {
	
	/**
	 * 恢复室卡片列表
	 * @return
	 */
	public List<AnaesPacuRecFormBean> getAnaesPacuRecCard(){
		return docAnaesPacuRecDao.getAnaesPacuRecCard(getBeid());
	}
	
	
	public AnaesPacuRecFormBean getOptInfoByPacuId(String id){
		return docAnaesPacuRecDao.getOptInfoByPacuId(id);
	}

	public List<AnaesPacuRecFormBean> searchAnaesPacuRecList(SystemSearchFormBean searchFormBean){
		if(StringUtils.isBlank(searchFormBean.getSort())){
			searchFormBean.setSort("id");
		}
		if(StringUtils.isBlank(searchFormBean.getOrderBy())){
			searchFormBean.setOrderBy("DESC");
		}
		List<Filter> filters = searchFormBean.getFilters();
		if(filters.size()==0){
			filters = new ArrayList<Filter>();
		}
		return docAnaesPacuRecDao.searchAnaesPacuRecList(searchFormBean,filters,getBeid());
	}

	public List<BasPacuMonitorConfig> getPacuMonitorConfigCheck(String deviceId) {
		return basPacuMonitorConfigDao.getPacuMonitorConfigCheck(deviceId,getBeid());
	}
	
	public int searchTotalAnaesPacuRecList(SystemSearchFormBean searchFormBean){
		if(StringUtils.isBlank(searchFormBean.getSort())){
			searchFormBean.setSort("id");
		}
		if(StringUtils.isBlank(searchFormBean.getOrderBy())){
			searchFormBean.setOrderBy("DESC");
		}
		List<Filter> filters = searchFormBean.getFilters();
		if(filters.size()==0){
			filters = new ArrayList<Filter>();
		}
		return docAnaesPacuRecDao.searchTotalAnaesPacuRecList(searchFormBean,filters,getBeid());
	}
	
	@Transactional
	public void savePacuNumber(DocAnaesPacuRec record) {
		docAnaesPacuRecDao.updateByPrimaryKeySelective(record);
	}
	
	@Transactional
	public void saveAnaesPacuRec(DocAnaesPacuRec record,ResponseValue resp) {
		//if(StringUtils.isBlank(record.getId())){
			String bedId = record.getBedId();
			if(StringUtils.isNotBlank(bedId)){
				BasRegionBed regionBed = basRegionBedDao.selectByPrimaryKey(bedId);
				if(null != regionBed && !record.getRegOptId().equals(regionBed.getRegOptId())){
					if(regionBed.getStatus()==1){
						resp.setResultCode("100000000");
						resp.setResultMessage("该床位已被占用请选择其他床位!");
						return;
					}
					regionBed.setRegOptId(record.getRegOptId());
					regionBed.setStatus(1);
					basRegionBedDao.updateByPrimaryKey(regionBed);
				}
			}
//			DocAnaesPacuRec p = anaesPacuRecDao.selectPacuByRegOptId(record.getRegOptId());
//			if(p == null){
//				record.setId(GenerateSequenceUtil.generateSequenceNo());
//				record.setProcessState("0");
//				anaesPacuRecDao.insertSelective(record);
//			}
			
		//}else{
			//当leaveTo不为空则代表患者出复苏室，则需要将床位状态改成有效
			
			record.setPortablePipe(StringUtils.getStringByList(record.getPortablePipeList()));
			record.setPortableRes(StringUtils.getStringByList(record.getPortableResList()));
			String processState = record.getProcessState();
			if(StringUtils.isNotBlank(processState) && "END".equals(processState)){
				BasRegionBed regionBed = basRegionBedDao.selectByPrimaryKey(record.getBedId().toString());
				regionBed.setStatus(0);
				regionBed.setRegOptId("");
				basRegionBedDao.updateByPrimaryKey(regionBed);
				record.setProcessState("END");
				record.setAnabioticState(2);
				Controller controller = controllerDao.getControllerById(record.getRegOptId());
				controller.setPreviousState(controller.getState());
				controller.setState("06");
				controllerDao.update(controller);
				//basRegOptDao.updateState(record.getRegOptId(), "06");
				
				EvtAnaesEvent anaesEventPacu = new EvtAnaesEvent();
                anaesEventPacu.setCode(EvtAnaesEventService.OUT_ROOM);
                anaesEventPacu.setOccurTime(record.getExitTime());
                if(null != record){
                    anaesEventPacu.setDocId(record.getId());
                }
                anaesEventPacu.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
                anaesEventPacu.setDocType(2);
                evtAnaesEventDao.insert(anaesEventPacu);
                
				DocAnaesPacuRec p = docAnaesPacuRecDao.selectPacuByRegOptId(record.getRegOptId());
				//出复苏室时，调用采集结束命令，结束采集床旁设备数据
				if(null != p  && p.getAnabioticState() != 2)
				{
					CmdMsg msg = new CmdMsg();
					msg.setMsgType(MyConstants.STATUS_END);
					msg.setBedId(record.getBedId());
					msg.setRegOptId(record.getRegOptId());
					MsgProcess.process(msg);
				}
				
			}else{
				//regOptDao.updateState(record.getRegOptId(), "05");
				record.setAnabioticState(1);
			}
			
			docAnaesPacuRecDao.updateByPrimaryKeySelective(record);
			
		//}
		
	}
	
	public DocAnaesPacuRec getAnaesPacuRecById(String id){
		return docAnaesPacuRecDao.selectByPrimaryKey(id);
	}
	
	public DocAnaesPacuRec getAnaesPacuRecByRegOptId(String regOptId){
		return docAnaesPacuRecDao.getAnaesPacuRecByRegOptId(regOptId);
	}
	
	public boolean hasAnaesPacuByRegOptId(String regOptId){
		boolean flag = false;
		Integer count = docAnaesPacuRecDao.hasAnaesPacuRecByRegOptId(regOptId);
		if(count > 0) {
			flag =  true;
		}
		return flag;
	}
	
	@Transactional
    public void updatePacuRecEnterRoomTime(Date enterTime,String pacuRecId){
	    docAnaesPacuRecDao.updatePacuRecEnterRoomTime(enterTime,pacuRecId);
    }

	public List<PacuDeviceSpecFormBean> getPacuDeviceByType() {
		return basPacuDeviceSpecificationDao.getPacuDeviceByType(getBeid());
	}
	
	public List<PacuDeviceConfigFormBean> getPacuDeviceConfigList(String bedId, String beid) {
		List<PacuDeviceConfigFormBean> deviceConfigList = basPacuDeviceConfigDao.selectByBedId(bedId, beid);
		if(null != deviceConfigList && deviceConfigList.size()>0){
			for (PacuDeviceConfigFormBean pacuDeviceConfig : deviceConfigList) {
				pacuDeviceConfig.setBedEventConfigList(basPacuBedEventConfigDao.selectByBedId(pacuDeviceConfig.getDeviceId(), bedId, beid));
			}
		}
		return deviceConfigList;
	}

	@Transactional
	public void savePacuDeviceConfig(PacuDeviceEventFormBean bean) {
		BasPacuDeviceConfig pacuDeviceConfig = bean.getPacuDeviceConfig();
		if (StringUtils.isBlank(pacuDeviceConfig.getBeid())) {
			pacuDeviceConfig.setBeid(getBeid());
		}
		List<BasPacuBedEventConfig> bedEventConfigList = bean.getBedEventConfigList();
		//1、修改pacuDeviceConfig
		BasPacuDeviceConfig pdc = basPacuDeviceConfigDao.selectByPrimaryKey(pacuDeviceConfig.getDeviceId(), pacuDeviceConfig.getBedId());
		if(null!=pdc){
			if(null == pacuDeviceConfig.getRoomId() || StringUtils.isBlank(pacuDeviceConfig.getRoomId())){
				String roomId = "";
				List<SysCodeFormbean> list = basDictItemDao.searchSysCodeByGroupId("revive_room", getBeid());
				if(null != list && list.size()>0){
					SysCodeFormbean sysCodeFormbean = list.get(0);
					roomId = sysCodeFormbean.getCodeValue();
					pacuDeviceConfig.setRoomId(roomId);
				}
			}
			basPacuDeviceConfigDao.updateByPrimaryKeySelective(pacuDeviceConfig);
		}else{
			if(null == pacuDeviceConfig.getRoomId() || StringUtils.isBlank(pacuDeviceConfig.getRoomId())){
				String roomId = "";
				List<SysCodeFormbean> list = basDictItemDao.searchSysCodeByGroupId("revive_room", getBeid());
				if(null != list && list.size()>0){
					SysCodeFormbean sysCodeFormbean = list.get(0);
					roomId = sysCodeFormbean.getCodeValue();
					pacuDeviceConfig.setRoomId(roomId);
				}
			}
			if(null == pacuDeviceConfig.getStatus()){
				pacuDeviceConfig.setStatus(-1);
			}
			basPacuDeviceConfigDao.insertSelective(pacuDeviceConfig);
		}
		//2、删除床位和event的列表
		List<PacuBedEventConfigFormBean> pacuBedList = basPacuBedEventConfigDao.selectByBedId(pacuDeviceConfig.getDeviceId(), pacuDeviceConfig.getBedId(), pacuDeviceConfig.getBeid());
		if(null != pacuBedList && pacuBedList.size()>0){
			basPacuBedEventConfigDao.deleteByBedId(pacuDeviceConfig.getDeviceId(), "", pacuDeviceConfig.getBedId());
		}
		// 新增b_pacu_bed_event_config
		if(null != bedEventConfigList && bedEventConfigList.size()>0){
			for (BasPacuBedEventConfig pacuBedEventConfig : bedEventConfigList) {
				pacuBedEventConfig.setBedId(pacuDeviceConfig.getBedId());
				pacuBedEventConfig.setDeviceId(pacuDeviceConfig.getDeviceId());
				pacuBedEventConfig.setBeid(getBeid()); 
				basPacuBedEventConfigDao.insertSelective(pacuBedEventConfig); 
			}
		}
	}

	@Transactional
	public void deletePacuDeviceConfig(String bedId, String deviceId) {
		basPacuDeviceConfigDao.deleteByPrimaryKey(deviceId, bedId);//删除pacu设备配置
		basPacuBedEventConfigDao.deleteByBedId(deviceId, "", bedId); //删除pacu采集配置记录列表
	}
	
	
	/**
	 * pacu换床
	 * @param bean
	 */
	@Transactional
	public void changeBedByPacuRec(AnaesPacuChangeBedFormBean bean,ResponseValue resp) {
		if(null != bean && StringUtils.isNotBlank(bean.getSourceBed()) && StringUtils.isNotBlank(bean.getTargetBed()) && StringUtils.isNotBlank(bean.getSourceRegOptId())){
			
			BasRegionBed targetBed = basRegionBedDao.selectByPrimaryKey(bean.getTargetBed());
			DocAnaesPacuRec targetRec = docAnaesPacuRecDao.getAnaesPacuRecByRegOptId(targetBed.getRegOptId());
			
			BasRegionBed soureceBed = basRegionBedDao.selectByPrimaryKey(bean.getSourceBed());
			DocAnaesPacuRec sourceRec = docAnaesPacuRecDao.getAnaesPacuRecByRegOptId(bean.getSourceRegOptId());
			
			//换床时，两床位都存在患者时
			if(targetBed!=null && StringUtils.isNotBlank(targetBed.getRegOptId())){
				String tarOptId = targetBed.getRegOptId();
				String souOptId = soureceBed.getRegOptId();
				targetBed.setRegOptId(souOptId);
				soureceBed.setRegOptId(tarOptId);
				
				basRegionBedDao.updateByPrimaryKey(targetBed);
				basRegionBedDao.updateByPrimaryKey(soureceBed);
				
				if(null!=targetRec){
					targetRec.setBedId(soureceBed.getId());
					docAnaesPacuRecDao.updateByPrimaryKey(targetRec);
					
				}
			}else{
				String souOptId = soureceBed.getRegOptId();
				targetBed.setRegOptId(souOptId);
				targetBed.setStatus(1);
				basRegionBedDao.updateByPrimaryKey(targetBed);
				
				soureceBed.setRegOptId("");
				soureceBed.setStatus(0);
				basRegionBedDao.updateByPrimaryKey(soureceBed);
				
			}
			
			sourceRec.setBedId(targetBed.getId());
			docAnaesPacuRecDao.updateByPrimaryKey(sourceRec);
		}
		
	}
}
