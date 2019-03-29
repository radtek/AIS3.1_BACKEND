package com.digihealth.anesthesia.operProceed.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasOperroom;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.po.DocAnaesPacuRec;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchOperaPatrolRecordFormBean;
import com.digihealth.anesthesia.evt.po.EvtAnaesEvent;
import com.digihealth.anesthesia.evt.po.EvtCheckEvent;
import com.digihealth.anesthesia.evt.po.EvtMedicalEvent;
import com.digihealth.anesthesia.evt.po.EvtParticipant;
import com.digihealth.anesthesia.evt.po.EvtRealAnaesMethod;
import com.digihealth.anesthesia.evt.service.EvtParticipantService;
import com.digihealth.anesthesia.operProceed.controller.MyObserveDataController;
import com.digihealth.anesthesia.operProceed.core.MyConstants;
import com.digihealth.anesthesia.operProceed.datasync.MessageProcess;
import com.digihealth.anesthesia.operProceed.formbean.BasAnaesMonitorConfigFormBean;
import com.digihealth.anesthesia.operProceed.formbean.CmdMsg;
import com.digihealth.anesthesia.operProceed.po.BasAnaesMonitorConfig;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfig;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfigDefault;
import com.digihealth.anesthesia.operProceed.po.BasPacuRecMonitorConfig;
import com.digihealth.anesthesia.pacu.datasync.MsgProcess;

@Service
public class MyOperationDataService extends BaseService {
	
	private BasPacuRecMonitorConfigService basPacuRecMonitorConfigService = SpringContextHolder.getBean(BasPacuRecMonitorConfigService.class);
	private BasAnaesMonitorConfigService basAnaesMonitorConfigService = SpringContextHolder.getBean(BasAnaesMonitorConfigService.class);
	private BasMonitorConfigService basMonitorConfigService = SpringContextHolder.getBean(BasMonitorConfigService.class);
	
	@Transactional
	public void getStartOperDate(SearchFormBean searchBean,Map result,boolean flagInsert){
		String regOptId = searchBean.getRegOptId();
		//麻醉记录单
        DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
        searchBean.setDocType(1);
        // 麻醉事件
        List<EvtAnaesEvent> anaeseventList = evtAnaesEventDao.searchAnaeseventList(searchBean);
        result.put("anaeseventList", anaeseventList);
        //设置文书ID
        searchBean.setDocId(anaesRecord.getAnaRecordId());
        // 手术信息表
        BasRegOpt opt = basRegOptDao.searchRegOptById(regOptId);
		
		//AccessSource有值时代表是术中模块进入 ,为空值时则表示术后查看麻醉记录单
        if(StringUtils.isNotBlank(searchBean.getAccessSource())){
            
            BaseInfoQuery baseQuery = new BaseInfoQuery();
            //baseQuery.setOperRoomId(Global.getConfig("roomId").toString());
            baseQuery.setState(OperationState.SURGERY);

            /*
             * 同一个手术间可开始多台局麻或可开始一台全麻及多台局麻，但是不能同时开多台全麻
             * 
             * 1、当提交开始手术为局麻时直接跳过未完成手术的判断
             * 2、当提交开始手术为全麻时获取未完成的手术列表中存在全麻时，提示有未完成的全麻手术！
             */
            
            if("0".equals(opt.getIsLocalAnaes())){
                //判断当前手术室是否存在未完成的手术，如果存在则返回错误消息
                List<SearchOperaPatrolRecordFormBean> operaPatrolList = basRegOptDao.getOperaPatrolRecordListByRoomId(baseQuery);
                SearchOperaPatrolRecordFormBean po = null;
                if(operaPatrolList.size()>0){
                    for (SearchOperaPatrolRecordFormBean searchOperaPatrolRecordFormBean : operaPatrolList) {
                        if("0".equals(searchOperaPatrolRecordFormBean.getIsLocalAnaes())){
                            po = searchOperaPatrolRecordFormBean;
                        }
                    }
                    if(null != po && !po.getRegOptId().equals(searchBean.getRegOptId())){
                        result.put("resultCode", "40000004");
                        result.put("resultMessage", po.getOperRoomName()+"存在病人信息为："+po.getName()+",手术未完成!");
                        result.put("id", po.getRegOptId());
                        logger.info("------------------end startOper------------------------");
                        throw new RuntimeException(result.get("resultMessage").toString());
                        //return JsonType.jsonType(result);
                    }
                }
                
            }
            
            //当source传入start表示进入手术室，需要更新状态为术中，否则不更新当前状态
            if("start".equals(searchBean.getAccessSource())){
                Controller controller = controllerDao.getControllerById(regOptId);
                if(controller.getState().equals(OperationState.PREOPERATIVE)){
                	
                	//Controller controller = getControllerById(regOptId);
                    controllerDao.checkOperation(regOptId, OperationState.SURGERY, controller.getState());
                    //controllerService.changeControllerState(searchBean.getRegOptId(), OperationState.SURGERY);
                    flagInsert = true;
                }
                
                //判断当前regOptId状态为术前、术中时需要发送直接给采集系统
                if("03,04".contains(controller.getState())){
                    //发送命令给数据处理模块
                    CmdMsg msg = new CmdMsg();
                    msg.setMsgType(MyConstants.OPERATION_STATUS_START);
                    msg.setRegOptId(searchBean.getRegOptId());
                    ResponseValue res = MessageProcess.process(msg);
                    result.put("startOper", res);
                }
            }
            //麻醉记录单
            anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(searchBean.getRegOptId());
            //查询入室时间处理，由于Operation存入入室事件时间是异步的，配置查询 10次麻醉记录单，如果超过，则不处理；
            //int count = 100;
            //for (int i = 0; i < count; i++) {
            /*while(true){
                String inOperRoomTime = anaesRecord.getInOperRoomTime();
                if(StringUtils.isBlank(inOperRoomTime)){
                    anaesRecord = anaesRecordService.searchAnaesRecordByRegOptId(searchBean.getRegOptId());
                }else{
                    break;
                }
                Thread.sleep(1);
            }*/
                
            //}
            logger.info("startOper===anaesRecord---"+JsonType.jsonType(anaesRecord)+"---------");
            //设置文书ID
            searchBean.setDocId(anaesRecord.getAnaRecordId());
            // 手术信息表
            opt = basRegOptDao.searchRegOptById(regOptId);
            //获取排程信息
            BasDispatch dispatch = basDispatchDao.getDispatchOper(regOptId);
            
            boolean flag = false;
            //当麻醉记录表中的手术体位为空时则将排程表中的手术体位字段写入
            if(StringUtils.isBlank(anaesRecord.getOptBody()) && StringUtils.isNotBlank(dispatch.getOptBody())){
                anaesRecord.setOptBody(dispatch.getOptBody());
                flag = true;
                //docAnaesRecordService.saveAnaesRecord(anaesRecord);
            }
            
            //当麻醉记录表中的手术等级为空时则将基本信息表中的手术等级字段写入
            if(StringUtils.isBlank(anaesRecord.getOptLevel()) && StringUtils.isNotBlank(opt.getOptLevel())){
                anaesRecord.setOptLevel(opt.getOptLevel());
                flag = true;
                //docAnaesRecordService.saveAnaesRecord(anaesRecord);
            }
            
            //当麻醉记录表中的手术室为空时则将排程表中的手术室字段写入
            if(StringUtils.isBlank(anaesRecord.getOperRoomName()) && null != dispatch.getOperRoomId())
            {
                BasOperroom operroom = basOperroomDao.queryRoomListById(dispatch.getOperRoomId().toString(),getBeid());
                anaesRecord.setOperRoomName(null == operroom ? null : operroom.getName()); 
                flag = true;
                //docAnaesRecordService.saveAnaesRecord(anaesRecord); 
            }
            if (flag)
            {
            	docAnaesRecordDao.updateByPrimaryKeySelective(anaesRecord);
            }
            
            if(flagInsert){
                // 实际麻醉方法
                List<EvtRealAnaesMethod> realList = evtRealAnaesMethodDao.searchRealAnaesMethodList(searchBean);
                if(realList.size()<1){
                    if(StringUtils.isNotBlank(opt.getDesignedAnaesMethodCode())){
                        String[] methodArray = opt.getDesignedAnaesMethodCode().split(",");
                        String[] methodNameArray = opt.getDesignedAnaesMethodName().split(",");
                        for (int i = 0; i < methodArray.length; i++) {
                            String anaMedId = methodArray[i];
                            String realAnaMedName = methodNameArray[i];
                            EvtRealAnaesMethod realAnaesMethod = new EvtRealAnaesMethod();
                            realAnaesMethod.setDocId(searchBean.getDocId());
                            realAnaesMethod.setAnaMedId(anaMedId);
                            realAnaesMethod.setName(realAnaMedName);
                            realAnaesMethod.setRealAnaMedId(GenerateSequenceUtil.generateSequenceNo());
                            evtRealAnaesMethodDao.insertSelective(realAnaesMethod);
                        }
                    }
                }
                // 手术医生人员
                searchBean.setRole(EvtParticipantService.ROLE_OPERAT);
                List<EvtParticipant> participantList = evtParticipantDao.searchParticipantList(searchBean,getBeid());
                /*
                 * 进入手术时，判断是否编辑了手术人员信息
                 * 如果存在则跳过读取regOpt表的手术人员记录
                 */
                if(participantList.size()<1){
                     //将RegOpt表中的主刀手术医生字段写入到participant表
                    if(StringUtils.isNotBlank(opt.getOperatorId())){
                        String[] optArray = opt.getOperatorId().split(",");
                        for (int i = 0; i < optArray.length; i++) {
                            String operatorId = optArray[i];
                            EvtParticipant participant = new EvtParticipant();
                            participant.setDocId(searchBean.getDocId());
                            participant.setRole(EvtParticipantService.ROLE_OPERAT);
                            participant.setOperatorType("07"); //主刀医生
                            participant.setUserLoginName(operatorId);
                            participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                            evtParticipantDao.insertSelective(participant);
                        }
                    }
                    //助手医生
                    if(StringUtils.isNotBlank(opt.getAssistantId())){
                        String[] assArray = opt.getAssistantId().split(",");
                        for (int i = 0; i < assArray.length; i++) {
                            String assistantId = assArray[i];
                            EvtParticipant participant = new EvtParticipant();
                            participant.setDocId(searchBean.getDocId());
                            participant.setRole(EvtParticipantService.ROLE_OPERAT);
                            participant.setOperatorType("07"); //助手医生
                            participant.setUserLoginName(assistantId);
                            participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                            evtParticipantDao.insertSelective(participant);
                        }
                    }
                }
            }
        }
        
        //术中监测显示项
        BaseInfoQuery baseQuery = new BaseInfoQuery();
        baseQuery.setRegOptId(searchBean.getRegOptId());
        baseQuery.setEnable("1");
        baseQuery.setPosition("0");
        List<BasAnaesMonitorConfigFormBean> showList = basAnaesMonitorConfigService.getAnaesRecordShowListByRegOptId(baseQuery);
        
        //防止出现首次进入术中时没有设备启用，但是后来追加设备启用但是设备并不是默认设备的情况，及时将b_anaes_monitor_config中重复监测项的realEventId更新为启用设备的eventId
        List<BasAnaesMonitorConfigFormBean> anaesMonitorConfigFormBeanList = basAnaesMonitorConfigService.finaAnaesList(baseQuery);
        List<BasMonitorConfigDefault> monitorConfigDefaultList = basMonitorConfigDefaultDao.selectAll(getBeid());
        if (null != monitorConfigDefaultList && monitorConfigDefaultList.size() > 0)
        {
            for (BasMonitorConfigDefault monitorConfigDefault : monitorConfigDefaultList)
            {
                if (null == anaesMonitorConfigFormBeanList || anaesMonitorConfigFormBeanList.size() < 1)
                {
                    break;
                }
                for (BasAnaesMonitorConfigFormBean anaesMonitorConfigFormBean : anaesMonitorConfigFormBeanList)
                {
                    if (monitorConfigDefault.getEventId().equals(anaesMonitorConfigFormBean.getEventId()))
                    {
                        String enableEventId =
                            basMonitorConfigService.selectEventIdByEventName(anaesMonitorConfigFormBean,searchBean.getRegOptId());
                        anaesMonitorConfigFormBean.setRealEventId(enableEventId);
                        BasAnaesMonitorConfig amc = new BasAnaesMonitorConfig();
                        BeanUtils.copyProperties(anaesMonitorConfigFormBean, amc);
                        amc.setRegOptId(searchBean.getRegOptId());
                        basAnaesMonitorConfigService.updateByEventId(amc);
                    }
                }
                
            }
        }
        result.put("showList", anaesMonitorConfigFormBeanList);
        
        if ((null == anaesMonitorConfigFormBeanList || anaesMonitorConfigFormBeanList.size() <=  0) && null != showList && showList.size() > 0)
        {
            List<BasAnaesMonitorConfig> anaesMonitorConfigList = new ArrayList<BasAnaesMonitorConfig>();
            for (BasAnaesMonitorConfigFormBean anaesMonitorConfigFormBean : showList)
            {
                BasAnaesMonitorConfig anaesMonitorConfig = new BasAnaesMonitorConfig();
                BeanUtils.copyProperties(anaesMonitorConfigFormBean, anaesMonitorConfig);
                anaesMonitorConfig.setRegOptId(searchBean.getRegOptId());
                
                BasMonitorConfigDefault monitorConfigDefault =
                		basMonitorConfigDefaultDao.selectByEventName(anaesMonitorConfigFormBean.getEventName(),getBeid());
                if (null != monitorConfigDefault)
                {
                    //获取到启动设备对应的eventId，如果都没启动，则取默认值
                    String enableEventId = basMonitorConfigService.selectEventIdByEventName(anaesMonitorConfigFormBean,searchBean.getRegOptId());
                    if (enableEventId.equals(anaesMonitorConfig.getEventId()))
                    {
                        anaesMonitorConfigList.add(anaesMonitorConfig);
                    }
                }
                else
                {
                    anaesMonitorConfigList.add(anaesMonitorConfig);
                }
            }
            basAnaesMonitorConfigService.saveAnaesMonitorConfig(anaesMonitorConfigList); 
            result.put("showList", anaesMonitorConfigList);
        }
        
        
        BasMonitorConfig O2 = basMonitorConfigDao.selectByPrimaryKey(MyObserveDataController.O2_EVENT_ID,getBeid(),getCurRoomId(regOptId));
        
        //左侧采集项显示
        baseQuery.setPosition("1");
        baseQuery.setEnable(null);
        List<BasAnaesMonitorConfigFormBean> leftShowList = basAnaesMonitorConfigService.getAnaesRecordShowListByRegOptId(baseQuery);
        anaesMonitorConfigFormBeanList = basAnaesMonitorConfigService.finaAnaesList(baseQuery);
        if (null != anaesMonitorConfigFormBeanList && anaesMonitorConfigFormBeanList.size() > 0)
        {
            // 返回给前端的数字监测项中，不能包含O2,去除O2的监测项
            for (int i = 0; i < anaesMonitorConfigFormBeanList.size(); i++)
            {
                BasAnaesMonitorConfigFormBean mc = anaesMonitorConfigFormBeanList.get(i);
                if (mc.getEventId().equals(MyObserveDataController.O2_EVENT_ID))
                {
                    anaesMonitorConfigFormBeanList.remove(mc);
                    break;
                }
            }
        }
        else
        {
            //因为O2为必定展示的数字监测项，所以如果anaesMonitorConfigFormBeanList为空则说明是第一次进入麻醉记录单，则需要添加O2到bas_anaes_monitor_config中
            List<BasAnaesMonitorConfig> anaesMonitorConfigList = new ArrayList<BasAnaesMonitorConfig>();
            BasAnaesMonitorConfig anaesMonitorConfig = new BasAnaesMonitorConfig();
            BeanUtils.copyProperties(O2, anaesMonitorConfig);
            anaesMonitorConfig.setRegOptId(searchBean.getRegOptId());
            anaesMonitorConfig.setEventId(MyObserveDataController.O2_EVENT_ID); 
            anaesMonitorConfigList.add(anaesMonitorConfig);
            basAnaesMonitorConfigService.saveAnaesMonitorConfig(anaesMonitorConfigList);
        }
        
        result.put("leftShowList", anaesMonitorConfigFormBeanList);
        if ((null == anaesMonitorConfigFormBeanList || anaesMonitorConfigFormBeanList.size() <=  0))
        {
            if (null != leftShowList && leftShowList.size() > 0)
            {
                List<BasAnaesMonitorConfig> anaesMonitorConfigList = new ArrayList<BasAnaesMonitorConfig>();
                for (BasAnaesMonitorConfigFormBean anaesMonitorConfigFormBean : leftShowList)
                {
                    BasAnaesMonitorConfig anaesMonitorConfig = new BasAnaesMonitorConfig();
                    BeanUtils.copyProperties(anaesMonitorConfigFormBean, anaesMonitorConfig);
                    anaesMonitorConfig.setRegOptId(searchBean.getRegOptId());
                    anaesMonitorConfigList.add(anaesMonitorConfig);
                }
                basAnaesMonitorConfigService.saveAnaesMonitorConfig(anaesMonitorConfigList);
            }
            result.put("leftShowList", leftShowList);
        }
        result.put("O2", O2);//氧流量
	}
	/**
	 * 复苏记录单开始手术接口
	 * @param searchBean
	 * @param result
	 */
	@Transactional
	public void getPacuStartOperDate(SearchFormBean searchBean,Map result){
		
		String regOptId = searchBean.getRegOptId();
		if (StringUtils.isBlank(searchBean.getBeid()))
		{
		    searchBean.setBeid(getBeid());
		}
		
		//AccessSource有值时代表是术中模块进入 ,为空值时则表示术后查看麻醉记录单
        if(StringUtils.isNotBlank(searchBean.getAccessSource())){
        	//当source传入start表示进入手术室，需要更新状态为术中，否则不更新当前状态
            if("start".equals(searchBean.getAccessSource())){
                Controller controller = controllerDao.getControllerById(regOptId);
                DocAnaesPacuRec anaesPacuRec = docAnaesPacuRecDao.getAnaesPacuRecByRegOptId(regOptId);
                //判断当前regOptId状态为术前、术中时需要发送直接给采集系统
                if("05".contains(controller.getState())){
                    //发送命令给数据处理模块
                	CmdMsg msg = new CmdMsg();
        			msg.setMsgType(com.digihealth.anesthesia.pacu.core.MyConstants.STATUS_START);
        			msg.setBedId(anaesPacuRec.getBedId());
        			msg.setRegOptId(anaesPacuRec.getRegOptId());
        			ResponseValue res = MsgProcess.process(msg);
                    result.put("startOper", res);
                }
            }
        }
        
        //复苏左侧采集项显示
        BaseInfoQuery baseQuery = new BaseInfoQuery();
        baseQuery.setPosition("1");
        baseQuery.setEnable(null);
        List<BasAnaesMonitorConfigFormBean> pacuLeftShowList = basPacuRecMonitorConfigService.getAnaesRecordShowListByRegOptId(baseQuery);
        List<BasAnaesMonitorConfigFormBean> anaesMonitorConfigFormBeanList = basPacuRecMonitorConfigService.finaAnaesList(baseQuery);
        
        result.put("leftShowList", anaesMonitorConfigFormBeanList);
        if ((null == anaesMonitorConfigFormBeanList || anaesMonitorConfigFormBeanList.size() <=  0))
        {
            if (null != pacuLeftShowList && pacuLeftShowList.size() > 0)
            {
                List<BasPacuRecMonitorConfig> pacuRecMonitorConfigList = new ArrayList<BasPacuRecMonitorConfig>();
                for (BasAnaesMonitorConfigFormBean anaesMonitorConfigFormBean : pacuLeftShowList)
                {
                	BasPacuRecMonitorConfig pacuRecMonitorConfig = new BasPacuRecMonitorConfig();
                    BeanUtils.copyProperties(anaesMonitorConfigFormBean, pacuRecMonitorConfig);
                    pacuRecMonitorConfig.setRegOptId(searchBean.getRegOptId());
                    pacuRecMonitorConfigList.add(pacuRecMonitorConfig);
                }
                basPacuRecMonitorConfigService.savePacuRecMonitorConfig(pacuRecMonitorConfigList);
            }
            result.put("leftShowList", pacuLeftShowList);
        }
        
        
        
        //术中复苏监测显示项
        baseQuery = new BaseInfoQuery();
        baseQuery.setRegOptId(searchBean.getRegOptId());
        baseQuery.setEnable("1");
        baseQuery.setPosition("0");
        List<BasAnaesMonitorConfigFormBean> pacuShowList = basPacuRecMonitorConfigService.getAnaesRecordShowListByRegOptId(baseQuery);
        
        //防止出现首次进入术中时没有设备启用，但是后来追加设备启用但是设备并不是默认设备的情况，及时将b_anaes_monitor_config中重复监测项的realEventId更新为启用设备的eventId
        anaesMonitorConfigFormBeanList = basPacuRecMonitorConfigService.finaAnaesList(baseQuery);
        
        result.put("showList", anaesMonitorConfigFormBeanList);
        
        if ((null == anaesMonitorConfigFormBeanList || anaesMonitorConfigFormBeanList.size() <=  0) && null != pacuShowList && pacuShowList.size() > 0)
        {
            List<BasPacuRecMonitorConfig> pacuRecMonitorConfigList = new ArrayList<BasPacuRecMonitorConfig>();
            for (BasAnaesMonitorConfigFormBean anaesMonitorConfigFormBean : pacuShowList)
            {
            	BasPacuRecMonitorConfig pacuRecMonitorConfig = new BasPacuRecMonitorConfig();
                BeanUtils.copyProperties(anaesMonitorConfigFormBean, pacuRecMonitorConfig);
                pacuRecMonitorConfig.setRegOptId(searchBean.getRegOptId());
                pacuRecMonitorConfigList.add(pacuRecMonitorConfig);
            }
            basPacuRecMonitorConfigService.savePacuRecMonitorConfig(pacuRecMonitorConfigList); 
            result.put("showList", pacuRecMonitorConfigList);
        }
        
        DocAnaesPacuRec anaesPacuRec = docAnaesPacuRecDao.selectPacuByRegOptId(regOptId);
        result.put("anaesPacuRec", anaesPacuRec);
        
        searchBean.setDocType(2);
        // 麻醉事件
        List<EvtAnaesEvent> pacueventList = evtAnaesEventDao.searchAnaeseventList(searchBean);
        result.put("pacueventList", pacueventList);
        
	}
	
	/**
     * 在术中页面返回术前(本溪局点)
     * @return
     */
	@Transactional
	public ResponseValue backPreoperative(String regOptId, ResponseValue resp) {
		basAnaesMonitorConfigDao.deleteByRegOptId(regOptId, getBeid());
		basMonitorDisplayDao.deleteByRegOptId(regOptId);
		basMonitorDisplayChangeHisDao.deleteByRegOptId(regOptId);
		basMonitorFreqChangeDao.deleteByRegOptId(regOptId);
		String docId = "";
		DocAnaesRecord docAnaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
		if (docAnaesRecord != null) {
			SearchFormBean searchBean = new SearchFormBean();
			searchBean.setDocId(docId);
			docId = docAnaesRecord.getAnaRecordId();
			evtAnaesEventDao.deleteByDocId(docId);
			String evtCheckEventId = "";
			List<EvtCheckEvent> evtCheckEventList = evtCheckEventDao.searchCheckeventList(searchBean);
			if (evtCheckEventList.size() > 0) {
				evtCheckEventId = evtCheckEventList.get(0).getCheEventId();
				evtCheckEventItemRelationDao.deleteCheckeventItemRelation(evtCheckEventId);
				evtCheckEventDao.deleteByDocId(docId);
			}
			evtCtlBreathDao.deleteByDocId(docId);
			evtEgressDao.deleteByDocId(docId);
			evtInEventDao.deleteByDocId(docId);
			String medicaleventId = "";
			List<EvtMedicalEvent> evtMedicalEventList = evtMedicaleventDao.queryMedicaleventListById(searchBean);
			if (evtMedicalEventList.size() > 0) {
				medicaleventId = evtMedicalEventList.get(0).getMedEventId();
				evtMedicalEventDetailDao.deleteByMedEventId(medicaleventId);
				evtMedicaleventDao.deleteByDocId(docId);
			}
			evtOptLatterDiagDao.deleteByDocId(docId);
			evtOptRealOperDao.deleteByDocId(docId);
			evtOtherEventDao.deleteByDocId(docId);
			evtParticipantDao.deleteByDocId(docId);
			evtRealAnaesMethodDao.deleteByDocId(docId);
			evtRescueeventDao.deleteByDocId(docId);
			
			docAnaesRecord.setAnaesStartTime(null);
			docAnaesRecord.setOperStartTime(null);
			docAnaesRecord.setOperEndTime(null);
			docAnaesRecord.setInOperRoomTime(null);
			docAnaesRecord.setOutOperRoomTime(null);
			docAnaesRecord.setLeaveTo(null);
			docAnaesRecordDao.updateByPrimaryKey(docAnaesRecord);
		} else {
			resp.setResultCode("10000000");
			resp.setResultMessage("麻醉记录单不存在!");
			return resp;
		}
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
		if (regOpt != null) {
			regOpt.setOperTime(null);
			regOpt.setState(OperationState.PREOPERATIVE);
			regOpt.setPreviousState(OperationState.SURGERY);
			basRegOptDao.updateByPrimaryKey(regOpt);
		} else {
			resp.setResultCode("10000000");
			resp.setResultMessage("患者信息不存在!");
		}
		return resp;
	}
}
