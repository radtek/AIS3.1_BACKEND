package com.digihealth.anesthesia.evt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.RegOptOperEgressFormBean;
import com.digihealth.anesthesia.evt.formbean.RegOptOperIoeventFormBean;
import com.digihealth.anesthesia.evt.formbean.RegOptOperMedicaleventFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchOptOperEgress;
import com.digihealth.anesthesia.evt.formbean.SearchOptOperIoevent;
import com.digihealth.anesthesia.evt.formbean.SearchOptOperMedicalevent;
import com.digihealth.anesthesia.evt.po.EvtAnaesEvent;
import com.digihealth.anesthesia.evt.po.EvtCheckEvent;
import com.digihealth.anesthesia.evt.po.EvtCtlBreath;
import com.digihealth.anesthesia.evt.po.EvtOtherEvent;
import com.digihealth.anesthesia.evt.po.EvtRescueevent;
import com.digihealth.anesthesia.evt.po.EvtShiftChange;
import com.digihealth.anesthesia.evt.po.EvtSpecialMaterialEvent;
import com.digihealth.anesthesia.evt.service.EvtAnaesEventService;
import com.digihealth.anesthesia.interfacedata.service.OperBaseDataServiceSYBX;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/operation")
@Api(value = "EvtAnaesEventController", description = "麻醉事件处理类")
public class EvtAnaesEventController extends BaseController {

	@RequestMapping(value = "/searchAnaeseventList")
	@ResponseBody
	@ApiOperation(value = "查询所有的麻醉事件", httpMethod = "POST", notes = "查询所有的麻醉事件")
	public String searchAnaeseventList(@ApiParam(name = "searchBean", value = "参数") @RequestBody SearchFormBean searchBean) {
		logger.info("begin searchAnaeseventList");
		ResponseValue resp = new ResponseValue();
		List<EvtAnaesEvent> resultList = evtAnaesEventService.searchAnaeseventList(searchBean);
		resp.put("resultList", resultList);
		logger.info("end searchAnaeseventList");
		return resp.getJsonStr();
	}

    @RequestMapping(value = "/searchAnaesEventPacuList")
    @ResponseBody
	@ApiOperation(value = "查询所有的PACU麻醉事件", httpMethod = "POST", notes = "查询所有的PACU麻醉事件")
    public String searchAnaesEventPacuList(@ApiParam(name = "searchBean", value = "查询参数") @RequestBody SearchFormBean searchBean) {
    	logger.info("begin searchAnaesEventPacuList");
		ResponseValue resp = new ResponseValue();
		List<EvtAnaesEvent> resultList = evtAnaesEventService.searchAnaesEventPacuList(searchBean);
		resp.put("resultList", resultList);
		logger.info("end searchAnaesEventPacuList");
		return resp.getJsonStr();
    }

    @RequestMapping(value = "/saveAnaeseventPacu")
    @ResponseBody
	@ApiOperation(value = "保存PACU麻醉事件", httpMethod = "POST", notes = "保存PACU麻醉事件")
    public String saveAnaeseventPacu(@ApiParam(name = "anaesEventPacu", value = "保存参数") @RequestBody EvtAnaesEvent anaesEventPacu) {
    	logger.info("begin saveAnaeseventPacu");
		ResponseValue resp = new ResponseValue();
		evtAnaesEventService.saveAnaeseventPacu(anaesEventPacu, resp);
		logger.info("end saveAnaeseventPacu");
        return resp.getJsonStr();
    }
    
    @RequestMapping(value = "/deleteAnaeseventPacu")
    @ResponseBody
	@ApiOperation(value = "删除PACU麻醉事件", httpMethod = "POST", notes = "删除PACU麻醉事件")
    public String deleteAnaeseventPacu(@ApiParam(name = "anaesEventPacu", value = "删除参数") @RequestBody EvtAnaesEvent anaesEventPacu) {
    	logger.info("begin deleteAnaeseventPacu");
		ResponseValue resp = new ResponseValue();
		evtAnaesEventService.deleteAnaeseventPacu(anaesEventPacu);
		logger.info("end deleteAnaeseventPacu");
        return resp.getJsonStr();
    }

	@RequestMapping(value = "/saveAnaesevent")
	@ResponseBody
	@ApiOperation(value = "保存麻醉事件", httpMethod = "POST", notes = "保存麻醉事件")
	public String saveAnaesevent(@ApiParam(name = "anaesevent", value = "麻醉事件对象") @RequestBody EvtAnaesEvent anaesevent) {
		logger.info("begin saveAnaesevent");
		ResponseValue resp = new ResponseValue();
		if (anaesevent != null) {
			evtAnaesEventService.saveAnaesevent(anaesevent, resp);
			resp.put("anaEventId", anaesevent.getAnaEventId());
			SearchFormBean searchBean = new SearchFormBean();
			searchBean.setDocId(anaesevent.getDocId());
			List<EvtAnaesEvent> resultList = evtAnaesEventService.searchAnaeseventList(searchBean);
			resp.put("resultList", resultList);
		} else {
			resp.setResultCode("70000000");
			resp.setResultMessage(Global.getRetMsg(resp.getResultCode()));
		}
		logger.info("end saveAnaesevent");
		return resp.getJsonStr();
	}

	@RequestMapping(value = "/saveAnaeseventSYBX")
    @ResponseBody
    @ApiOperation(value = "保存麻醉事件", httpMethod = "POST", notes = "保存麻醉事件")
    public String saveAnaeseventSYBX(@ApiParam(name = "anaesevent", value = "麻醉事件对象") @RequestBody EvtAnaesEvent anaesevent) {
        logger.info("begin saveAnaeseventSYBX");
        ResponseValue resp = new ResponseValue();
        if (anaesevent != null) {
        	boolean flag = false;
        	SearchFormBean searchBean = new SearchFormBean();
        	searchBean.setDocId(anaesevent.getDocId());
        	searchBean.setCode(String.valueOf(EvtAnaesEventService.OPER_START));
        	List<EvtAnaesEvent> lst = evtAnaesEventService.searchAnaeseventList(searchBean);
        	if (!lst.isEmpty() && lst.size() > 0) {
        		flag = true;
        	}
            evtAnaesEventService.saveAnaesevent(anaesevent, resp);
            resp.put("anaEventId", anaesevent.getAnaEventId());
            searchBean.setCode(null);
            List<EvtAnaesEvent> resultList = evtAnaesEventService.searchAnaeseventList(searchBean);
            resp.put("resultList", resultList);
            
            //如果是手术开始，则将排程信息回传到his
            if (EvtAnaesEventService.OPER_START.equals(anaesevent.getCode())) {
            	if (!flag) {
                    String isConnectionFlag = Global.getConfig("isConnectionHis").trim();
                    if(StringUtils.isEmpty(isConnectionFlag) || "true".equals(isConnectionFlag)) {
    	                DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordById(anaesevent.getDocId());
    	                logger.info("===============================发送排程信息到his===========================================");
    	                OperBaseDataServiceSYBX operBaseDataServiceSYBX = SpringContextHolder.getBean(OperBaseDataServiceSYBX.class);
    	                if(null != operBaseDataServiceSYBX) {
    	                	operBaseDataServiceSYBX.sendScheduleToHis(anaesRecord.getRegOptId());
    	                }
                    }
				}
            }
        } else {
            resp.setResultCode("70000000");
            resp.setResultMessage(Global.getRetMsg(resp.getResultCode()));
        }
        logger.info("end saveAnaeseventSYBX");
        return resp.getJsonStr();
    }

	@RequestMapping(value = "/deleteByCodeAndDocId")
	@ResponseBody
	@ApiOperation(value = "删除麻醉事件根据code和文书id", httpMethod = "POST", notes = "删除麻醉事件根据code和文书id")
	public String deleteByCodeAndDocId(@ApiParam(name = "anaesevent", value = "麻醉事件对象") @RequestBody EvtAnaesEvent anaesevent) {
		logger.info("begin deleteByCodeAndDocId");
		ResponseValue resp = new ResponseValue();
		evtAnaesEventService.deleteByCodeAndDocId(anaesevent, resp);
		SearchFormBean searchBean = new SearchFormBean();
		searchBean.setDocId(anaesevent.getDocId());
		List<EvtAnaesEvent> resultList = evtAnaesEventService.searchAnaeseventList(searchBean);
		resp.put("resultList", resultList);
		logger.info("end deleteByCodeAndDocId");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/searchAllEventList")
    @ResponseBody
    @ApiOperation(value = "查询所有的事件", httpMethod = "POST", notes = "查询所有的事件")
    public String searchAllEventList(@ApiParam(name = "searchBean", value = "参数")@RequestBody SearchFormBean searchBean) {
        logger.info("begin searchAllEventList");
        ResponseValue resp = new ResponseValue();
        Map map = new HashMap();
        DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordById(searchBean.getDocId());
        if(null != anaesRecord)
        {
            map.put("analgesicMethod", anaesRecord.getAnalgesicMethod());
            map.put("flow1", anaesRecord.getFlow1());
            map.put("flowUnit1", anaesRecord.getFlowUnit1());
            map.put("flow2", anaesRecord.getFlow2());
            map.put("flowUnit2", anaesRecord.getFlowUnit2());
        }
        Long startTime = 0l;
        Long endTime = 2145887999000l; //2037-12-31 23:59:59
        if (StringUtils.isNotBlank(searchBean.getStartTime()))
        {
            startTime = Long.parseLong(searchBean.getStartTime()); 
        }
        if (StringUtils.isNotBlank(searchBean.getEndTime()))
        {
            endTime = Long.parseLong(searchBean.getEndTime());
        }
        
        // 麻醉事件
        searchBean.setCode(null);
        List<EvtAnaesEvent> anaeseventList = evtAnaesEventService.searchAnaeseventList(searchBean);
        if (null != anaeseventList && anaeseventList.size() > 0 && null != searchBean.getAnaesEvtNum())
        {
            int anaesEvtNum = searchBean.getAnaesEvtNum();
            for (int i = anaeseventList.size() - 1; i >= 0; i--)
            {
                EvtAnaesEvent evtAnaesEvent = anaeseventList.get(i);
                Long time = evtAnaesEvent.getOccurTime().getTime();
                if ((evtAnaesEvent.getCode() <= anaesEvtNum && 8 != anaesEvtNum) || time < startTime || time > endTime)
                {
                    anaeseventList.remove(evtAnaesEvent);
                }
            }
        }
        resp.put("anaesevent", anaeseventList); 

         //检测事件
         List<EvtCheckEvent> checkeventList = evtCheckEventService.serarchCheckevent(searchBean);
         if (null != checkeventList && checkeventList.size() > 0)
         {
             for (int i = checkeventList.size() - 1; i >=0; i--)
             {
                 Long time = checkeventList.get(i).getOccurTime().getTime();
                 if (time < startTime || time > endTime)
                 {
                     checkeventList.remove(i);
                 }
             }
         }
         resp.put("checkeventList", checkeventList);

        // 其他事件
        List<EvtOtherEvent> othereventList = evtOtherEventService.searchOthereventList(searchBean);
        if (null != othereventList && othereventList.size() > 0)
        {
            for (int i = othereventList.size() - 1; i >=0; i--)
            {
                Long time = othereventList.get(i).getStartTime().getTime();
                if (time < startTime || time > endTime)
                {
                    othereventList.remove(i);
                }
            }
        }
        resp.put("otherevent", othereventList);

        // 呼吸事件
        List<EvtCtlBreath> ctlBreathList = evtCtlBreathService.searchCtlBreathList(searchBean);
        if (null != ctlBreathList && ctlBreathList.size() > 0)
        {
            for (int i = ctlBreathList.size() - 1; i >=0; i--)
            {
                Long time = ctlBreathList.get(i).getStartTime().getTime();
                if (time < startTime || time > endTime)
                {
                    ctlBreathList.remove(i);
                }
            }
        }
        resp.put("ctlBreath", ctlBreathList);

        // 抢救事件
        List<EvtRescueevent> rescueeventList = evtRescueeventService.searchRescueeventList(searchBean);
        if (null != rescueeventList && rescueeventList.size() > 0)
        {
            for (int i = rescueeventList.size() - 1; i >=0; i--)
            {
                Long time = rescueeventList.get(i).getStartTime().getTime();
                if (time < startTime || time > endTime)
                {
                    rescueeventList.remove(i);
                }
            }
        }
        resp.put("rescueevent", rescueeventList);

        // 交换班事件
        List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(searchBean);
        if (null != shiftChangeList && shiftChangeList.size() > 0)
        {
            for (int i = shiftChangeList.size() - 1; i >=0; i--)
            {
                Long time = shiftChangeList.get(i).getShiftChangeTime().getTime();
                if (time < startTime || time > endTime)
                {
                    shiftChangeList.remove(i);
                }
            }
        }
        resp.put("shiftChange", shiftChangeList);
        
        // 治疗用药事件
        searchBean.setType("01");
        List<RegOptOperMedicaleventFormBean> list =
            evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);
        List<RegOptOperMedicaleventFormBean> medicaleventList = new ArrayList<RegOptOperMedicaleventFormBean>();
        if (list != null && list.size() > 0 && null != searchBean.getMedEventNum())
        {
            for (int i = searchBean.getMedEventNum(); i < list.size(); i++)
            {
                RegOptOperMedicaleventFormBean medicalEventFormBean = list.get(i);
                List<SearchOptOperMedicalevent> medicalEventList = medicalEventFormBean.getMedicalEventList();
                if (null != medicalEventList && medicalEventList.size() > 0)
                {
                    for (int k = medicalEventList.size() - 1; k >=0; k--)
                    {
                        SearchOptOperMedicalevent medicalevent = medicalEventList.get(k);
                        Long eventStartTime = medicalevent.getStartTime().getTime();
                        Long eventEndTime = medicalevent.getStartTime().getTime();
                        if ("1".equals(medicalevent.getDurable()) && null != medicalevent.getEndTime())
                        {
                            eventEndTime = medicalevent.getEndTime().getTime();
                        }
                        if (eventStartTime > endTime || eventEndTime < startTime)
                        {
                            medicalEventList.remove(k);
                        }
                    }
                }
                if (null != medicalEventList && medicalEventList.size() > 0)
                {
                    list.get(i).setMedicalEventList(medicalEventList); 
                    medicaleventList.add(list.get(i));
                }
            }
        }
        resp.put("medicalevent", medicaleventList);
        
        // 麻醉用药事件
        searchBean.setType("02");
        List<RegOptOperMedicaleventFormBean> list2 =
            evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);
        List<RegOptOperMedicaleventFormBean> anaesMedeventList = new ArrayList<RegOptOperMedicaleventFormBean>();
        if (list2 != null && list2.size() > 0 && null != searchBean.getAnaesMedNum())
        {
            for (int i = searchBean.getAnaesMedNum(); i < list2.size(); i++)
            {
                RegOptOperMedicaleventFormBean medicalEventFormBean = list2.get(i);
                List<SearchOptOperMedicalevent> medicalEventList = medicalEventFormBean.getMedicalEventList();
                if (null != medicalEventList && medicalEventList.size() > 0)
                {
                    for (int k = medicalEventList.size() - 1; k >=0; k--)
                    {
                        SearchOptOperMedicalevent medicalevent = medicalEventList.get(k);
                        Long eventStartTime = medicalevent.getStartTime().getTime();
                        Long eventEndTime = medicalevent.getStartTime().getTime();
                        if ("1".equals(medicalevent.getDurable()) && null != medicalevent.getEndTime())
                        {
                            eventEndTime = medicalevent.getEndTime().getTime();
                        }
                        if (eventStartTime > endTime || eventEndTime < startTime)
                        {
                            medicalEventList.remove(k);
                        }
                    }
                }
                if (null != medicalEventList && medicalEventList.size() > 0)
                {
                    list2.get(i).setMedicalEventList(medicalEventList); 
                    anaesMedeventList.add(list2.get(i));
                }
            }
        }
        resp.put("anaesMedevent", anaesMedeventList);
        // 镇痛用药事件
        searchBean.setType("03");
        List<RegOptOperMedicaleventFormBean> list3 =
            evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);
        map.put("analgesicMedEvtList", list3);
        resp.put("analgesicMedEvt", map); 
        
        // 输液事件
        searchBean.setSubType(null);
        List<RegOptOperIoeventFormBean> infusionAllList = evtInEventService.searchIoeventGroupByDefIdList(searchBean);
        List<RegOptOperIoeventFormBean> infusionList = new ArrayList<RegOptOperIoeventFormBean>();
        if (null != infusionAllList && infusionAllList.size() > 0 && null != searchBean.getInfusionNum())
        {
            for (int i = searchBean.getInfusionNum(); i < infusionAllList.size(); i++)
            {
                RegOptOperIoeventFormBean ioeventFormBean = infusionAllList.get(i);
                List<SearchOptOperIoevent> ioeventList = ioeventFormBean.getIoeventList();
                if (null != ioeventList && ioeventList.size() > 0)
                {
                    for (int k = ioeventList.size() - 1; k >=0; k--)
                    {
                        SearchOptOperIoevent ioevent = ioeventList.get(k);
                        Long time = ioevent.getStartTime().getTime();
                        if (time < startTime || time > endTime)
                        {
                            ioeventList.remove(k);
                        }
                    }
                }
                if (null != ioeventList && ioeventList.size() > 0)
                {
                    infusionAllList.get(i).setIoeventList(ioeventList);
                    infusionList.add(infusionAllList.get(i));
                }
            }
        }
        resp.put("infusionList", infusionList);
        
        // 出量事件
        List<RegOptOperEgressFormBean> egressAllList = evtEgressService.searchEgressGroupByDefIdList(searchBean);
        List<RegOptOperEgressFormBean> egressList = new ArrayList<RegOptOperEgressFormBean>();
        if (null != egressAllList && egressAllList.size() > 0 && null != searchBean.getEgressNum())
        {
            for (int i = searchBean.getEgressNum(); i < egressAllList.size(); i++)
            {
                RegOptOperEgressFormBean egress = egressAllList.get(i);
                List<SearchOptOperEgress> egressEventList = egress.getEgressList();
                if (null != egressEventList && egressEventList.size() > 0)
                {
                    for (int k = egressEventList.size() - 1; k >=0; k--)
                    {
                        SearchOptOperEgress egressEvent = egressEventList.get(k);
                        Long time = egressEvent.getStartTime().getTime();
                        if (time < startTime || time > endTime)
                        {
                            egressEventList.remove(k);
                        }
                    }
                }
                if (null != egressEventList && egressEventList.size() > 0)
                {
                    egressAllList.get(i).setEgressList(egressEventList);
                    egressList.add(egressAllList.get(i));
                }
            }
        }
        resp.put("egress", egressList);
        
        //特殊材料事件
        List<EvtSpecialMaterialEvent> sepecialMaterialEventList = evtSpecialMaterialEventService.selectSpecialMaterialEvent(searchBean);
        if(null != sepecialMaterialEventList && sepecialMaterialEventList.size()>0)
        {
        	for (int i = sepecialMaterialEventList.size() - 1; i >=0; i--)
            {
                Long time = sepecialMaterialEventList.get(i).getStartTime().getTime();
                if (time < startTime || time > endTime)
                {
                	sepecialMaterialEventList.remove(i);
                }
            }
        }
        resp.put("sepecialMaterialEvent", sepecialMaterialEventList);
        
        logger.info("end searchAllEventList");
        return resp.getJsonStr();
    }

	@RequestMapping(value = "/searchAllEventListSYBX")
    @ResponseBody
    @ApiOperation(value = "查询所有的事件", httpMethod = "POST", notes = "查询所有的事件")
    public String searchAllEventListSYBX(@ApiParam(name = "searchBean", value = "参数")@RequestBody SearchFormBean searchBean) {
        logger.info("begin searchAllEventListSYBX");
        ResponseValue resp = new ResponseValue();
        Map map = new HashMap();
        DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordById(searchBean.getDocId());
        if(null != anaesRecord)
        {
            map.put("analgesicMethod", anaesRecord.getAnalgesicMethod());
            map.put("flow1", anaesRecord.getFlow1());
            map.put("flowUnit1", anaesRecord.getFlowUnit1());
            map.put("flow2", anaesRecord.getFlow2());
            map.put("flowUnit2", anaesRecord.getFlowUnit2());
        }
        Long startTime = 0l;
        Long endTime = 2145887999000l; //2037-12-31 23:59:59
        if (StringUtils.isNotBlank(searchBean.getStartTime()))
        {
            startTime = Long.parseLong(searchBean.getStartTime()); 
        }
        if (StringUtils.isNotBlank(searchBean.getEndTime()))
        {
            endTime = Long.parseLong(searchBean.getEndTime());
        }
        
        // 麻醉事件
        searchBean.setCode(null);
        List<EvtAnaesEvent> anaeseventList = evtAnaesEventService.searchAnaeseventList(searchBean);
        if (null != anaeseventList && anaeseventList.size() > 0 && null != searchBean.getAnaesEvtNum())
        {
            int anaesEvtNum = searchBean.getAnaesEvtNum();
            for (int i = anaeseventList.size() - 1; i >= 0; i--)
            {
                EvtAnaesEvent evtAnaesEvent = anaeseventList.get(i);
                Long time = evtAnaesEvent.getOccurTime().getTime();
                if ((evtAnaesEvent.getCode() <= anaesEvtNum && 8 != anaesEvtNum) || time < startTime || time > endTime)
                {
                    anaeseventList.remove(evtAnaesEvent);
                }
            }
        }
        resp.put("anaesevent", anaeseventList); 

         //检测事件
         List<EvtCheckEvent> checkeventList = evtCheckEventService.serarchCheckevent(searchBean);
         if (null != checkeventList && checkeventList.size() > 0)
         {
             for (int i = checkeventList.size() - 1; i >=0; i--)
             {
                 Long time = checkeventList.get(i).getOccurTime().getTime();
                 if (time < startTime || time > endTime)
                 {
                     checkeventList.remove(i);
                 }
             }
         }
         resp.put("checkeventList", checkeventList);

        // 其他事件
        List<EvtOtherEvent> othereventList = evtOtherEventService.searchOthereventList(searchBean);
        if (null != othereventList && othereventList.size() > 0)
        {
            for (int i = othereventList.size() - 1; i >=0; i--)
            {
                Long time = othereventList.get(i).getStartTime().getTime();
                if (time < startTime || time > endTime)
                {
                    othereventList.remove(i);
                }
            }
        }
        resp.put("otherevent", othereventList);

        // 呼吸事件
        List<EvtCtlBreath> ctlBreathList = evtCtlBreathService.searchCtlBreathList(searchBean);
        if (null != ctlBreathList && ctlBreathList.size() > 0)
        {
            for (int i = ctlBreathList.size() - 1; i >=0; i--)
            {
                Long time = ctlBreathList.get(i).getStartTime().getTime();
                if (time < startTime || time > endTime)
                {
                    ctlBreathList.remove(i);
                }
            }
        }
        resp.put("ctlBreath", ctlBreathList);

        // 抢救事件
        List<EvtRescueevent> rescueeventList = evtRescueeventService.searchRescueeventList(searchBean);
        if (null != rescueeventList && rescueeventList.size() > 0)
        {
            for (int i = rescueeventList.size() - 1; i >=0; i--)
            {
                Long time = rescueeventList.get(i).getStartTime().getTime();
                if (time < startTime || time > endTime)
                {
                    rescueeventList.remove(i);
                }
            }
        }
        resp.put("rescueevent", rescueeventList);

        // 交换班事件
        List<EvtShiftChange> shiftChangeList = evtShiftChangeService.searchShiftChangeList(searchBean);
        if (null != shiftChangeList && shiftChangeList.size() > 0)
        {
            for (int i = shiftChangeList.size() - 1; i >=0; i--)
            {
                Long time = shiftChangeList.get(i).getShiftChangeTime().getTime();
                if (time < startTime || time > endTime)
                {
                    shiftChangeList.remove(i);
                }
            }
        }
        resp.put("shiftChange", shiftChangeList);
        
        // 治疗用药事件
        searchBean.setType("01");
        List<RegOptOperMedicaleventFormBean> list =
            evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);
        List<RegOptOperMedicaleventFormBean> medicaleventList = new ArrayList<RegOptOperMedicaleventFormBean>();
        if (list != null && list.size() > 0 && null != searchBean.getMedEventNum())
        {
            for (int i = searchBean.getMedEventNum(); i < list.size(); i++)
            {
                RegOptOperMedicaleventFormBean medicalEventFormBean = list.get(i);
                List<SearchOptOperMedicalevent> medicalEventList = medicalEventFormBean.getMedicalEventList();
                if (null != medicalEventList && medicalEventList.size() > 0)
                {
                    for (int k = medicalEventList.size() - 1; k >=0; k--)
                    {
                        SearchOptOperMedicalevent medicalevent = medicalEventList.get(k);
                        Long eventStartTime = medicalevent.getStartTime().getTime();
                        Long eventEndTime = medicalevent.getStartTime().getTime();
                        if ("1".equals(medicalevent.getDurable()) && null != medicalevent.getEndTime())
                        {
                            eventEndTime = medicalevent.getEndTime().getTime();
                        }
                        if (eventStartTime > endTime || eventEndTime < startTime)
                        {
                            medicalEventList.remove(k);
                        }
                    }
                }
                if (null != medicalEventList && medicalEventList.size() > 0)
                {
                    list.get(i).setMedicalEventList(medicalEventList); 
                    medicaleventList.add(list.get(i));
                }
            }
        }
        resp.put("medicalevent", medicaleventList);
        
        // 麻醉用药事件
        /*searchBean.setType("02");
        List<RegOptOperMedicaleventFormBean> list2 =
            evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);
        List<RegOptOperMedicaleventFormBean> anaesMedeventList = new ArrayList<RegOptOperMedicaleventFormBean>();
        if (list2 != null && list2.size() > 0 && null != searchBean.getAnaesMedNum())
        {
            for (int i = searchBean.getAnaesMedNum(); i < list2.size(); i++)
            {
                RegOptOperMedicaleventFormBean medicalEventFormBean = list2.get(i);
                List<SearchOptOperMedicalevent> medicalEventList = medicalEventFormBean.getMedicalEventList();
                if (null != medicalEventList && medicalEventList.size() > 0)
                {
                    for (int k = medicalEventList.size() - 1; k >=0; k--)
                    {
                        SearchOptOperMedicalevent medicalevent = medicalEventList.get(k);
                        Long eventStartTime = medicalevent.getStartTime().getTime();
                        Long eventEndTime = medicalevent.getStartTime().getTime();
                        if ("1".equals(medicalevent.getDurable()) && null != medicalevent.getEndTime())
                        {
                            eventEndTime = medicalevent.getEndTime().getTime();
                        }
                        if (eventStartTime > endTime || eventEndTime < startTime)
                        {
                            medicalEventList.remove(k);
                        }
                    }
                }
                if (null != medicalEventList && medicalEventList.size() > 0)
                {
                    list2.get(i).setMedicalEventList(medicalEventList); 
                    anaesMedeventList.add(list2.get(i));
                }
            }
        }
        resp.put("anaesMedevent", anaesMedeventList);*/
        // 镇痛用药事件
        searchBean.setType("03");
        List<RegOptOperMedicaleventFormBean> list3 =
            evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);
        map.put("analgesicMedEvtList", list3);
        resp.put("analgesicMedEvt", map); 
        
        // 输液事件
        searchBean.setSubType("1");
        List<RegOptOperIoeventFormBean> infusionAllList = evtInEventService.searchIoeventGroupByDefIdListSYBX(searchBean);
        List<RegOptOperIoeventFormBean> infusionList = new ArrayList<RegOptOperIoeventFormBean>();
        if (null != infusionAllList && infusionAllList.size() > 0 && null != searchBean.getInfusionNum())
        {
            for (int i = searchBean.getInfusionNum(); i < infusionAllList.size(); i++)
            {
                RegOptOperIoeventFormBean ioeventFormBean = infusionAllList.get(i);
                List<SearchOptOperIoevent> ioeventList = ioeventFormBean.getIoeventList();
                if (null != ioeventList && ioeventList.size() > 0)
                {
                    for (int k = ioeventList.size() - 1; k >=0; k--)
                    {
                        SearchOptOperIoevent ioevent = ioeventList.get(k);
                        Long time = ioevent.getStartTime().getTime();
                        if (time < startTime || time > endTime)
                        {
                            ioeventList.remove(k);
                        }
                    }
                }
                if (null != ioeventList && ioeventList.size() > 0)
                {
                    infusionAllList.get(i).setIoeventList(ioeventList);
                    infusionList.add(infusionAllList.get(i));
                }
            }
        }
        resp.put("infusionList", infusionList);

        // 输血事件
        searchBean.setSubType("2");
        List<RegOptOperIoeventFormBean> bloodAllList = evtInEventService.searchIoeventGroupByDefIdList(searchBean);
        List<RegOptOperIoeventFormBean> bloodList = new ArrayList<RegOptOperIoeventFormBean>();
        if (null != bloodAllList && bloodAllList.size() > 0 && null != searchBean.getBloodNum())
        {
            for (int i = searchBean.getBloodNum(); i < bloodAllList.size(); i++)
            {
                RegOptOperIoeventFormBean ioeventFormBean = bloodAllList.get(i);
                List<SearchOptOperIoevent> ioeventList = ioeventFormBean.getIoeventList();
                if (null != ioeventList && ioeventList.size() > 0)
                {
                    for (int k = ioeventList.size() - 1; k >=0; k--)
                    {
                        SearchOptOperIoevent ioevent = ioeventList.get(k);
                        Long time = ioevent.getStartTime().getTime();
                        if (time < startTime || time > endTime)
                        {
                            ioeventList.remove(k);
                        }
                    }
                }
                if (null != ioeventList && ioeventList.size() > 0)
                {
                    bloodAllList.get(i).setIoeventList(ioeventList);
                    bloodList.add(bloodAllList.get(i));
                }
            }
        }
        resp.put("bloodList", bloodList);
        // 出量事件
        List<RegOptOperEgressFormBean> egressAllList = evtEgressService.searchEgressGroupByDefIdList(searchBean);
        List<RegOptOperEgressFormBean> egressList = new ArrayList<RegOptOperEgressFormBean>();
        if (null != egressAllList && egressAllList.size() > 0 && null != searchBean.getEgressNum())
        {
            for (int i = searchBean.getEgressNum(); i < egressAllList.size(); i++)
            {
                RegOptOperEgressFormBean egress = egressAllList.get(i);
                List<SearchOptOperEgress> egressEventList = egress.getEgressList();
                if (null != egressEventList && egressEventList.size() > 0)
                {
                    for (int k = egressEventList.size() - 1; k >=0; k--)
                    {
                        SearchOptOperEgress egressEvent = egressEventList.get(k);
                        Long time = egressEvent.getStartTime().getTime();
                        if (time < startTime || time > endTime)
                        {
                            egressEventList.remove(k);
                        }
                    }
                }
                if (null != egressEventList && egressEventList.size() > 0)
                {
                    egressAllList.get(i).setEgressList(egressEventList);
                    egressList.add(egressAllList.get(i));
                }
            }
        }
        resp.put("egress", egressList);
        
        //特殊材料事件
        List<EvtSpecialMaterialEvent> sepecialMaterialEventList = evtSpecialMaterialEventService.selectSpecialMaterialEvent(searchBean);
        if(null != sepecialMaterialEventList && sepecialMaterialEventList.size()>0)
        {
        	for (int i = sepecialMaterialEventList.size() - 1; i >=0; i--)
            {
                Long time = sepecialMaterialEventList.get(i).getStartTime().getTime();
                if (time < startTime || time > endTime)
                {
                	sepecialMaterialEventList.remove(i);
                }
            }
        }
        resp.put("sepecialMaterialEvent", sepecialMaterialEventList);
        
        logger.info("end searchAllEventListSYBX");
        return resp.getJsonStr();
    }

	/**
	 * 查询麻醉时长和手术时长 <功能详细描述>
	 * 
	 * @param searchBean
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/searchTimesByCode")
	@ResponseBody
	@ApiOperation(value = "查询麻醉时长和手术时长", httpMethod = "POST", notes = "查询麻醉时长和手术时长")
	public String searchTimesByCode(@ApiParam(name = "searchBean", value = "系统查询参数") @RequestBody SearchFormBean searchBean) {
		logger.info("begin searchTimesByCode");
		ResponseValue resp = new ResponseValue();
		resp = evtAnaesEventService.searchTimesByCode(searchBean);
		resp.put("resultMessage", "查询手术时长和麻醉时长成功!");
		logger.info("end searchTimesByCode");
		return resp.getJsonStr();
	}
}
