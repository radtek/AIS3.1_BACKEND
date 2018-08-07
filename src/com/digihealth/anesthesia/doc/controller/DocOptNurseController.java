/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:34:19    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.formbean.DesignedOptCodes;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.utils.BasRegOptUtils;
import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.OptNurseInstrubillItemFormbean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocInstrubillItem;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.doc.po.DocOptNurse;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchOptOperIoevent;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;
import com.digihealth.anesthesia.evt.po.EvtRealAnaesMethod;
import com.digihealth.anesthesia.evt.service.EvtParticipantService;
import com.digihealth.anesthesia.sysMng.formbean.UserInfoFormBean;
import com.digihealth.anesthesia.websocket.WebSocketHandler;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/**
 * Title: PreVisitController.java Description: 描述
 * 
 * @author chengwang
 * @created 2015-10-10 下午5:34:19
 */
@Controller
@RequestMapping(value = "/document")
@Api(value="DocOptNurseController",description="手术清点单处理类")
public class DocOptNurseController extends BaseController {

	/**
	 * 
	 * @discription 根据手术ID获取手术护理
	 * @author chengwang
	 * @created 2015-10-10 下午5:13:48
	 * @param regOptId
	 * @return
	 */
	@RequestMapping(value = "/searchOptNurseByRegOptId")
	@ResponseBody
	@ApiOperation(value="根据手术ID获取手术清点单",httpMethod="POST",notes="根据手术ID获取手术清点单")
	public String searchOptNurseByRegOptId(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
		logger.info("begin searchOptNurseByRegOptId");
		ResponseValue resp = new ResponseValue();
		String regOptId = map.get("regOptId")!=null?map.get("regOptId").toString():"";
		String type = map.get("type")!=null?map.get("type").toString():"";
		
		DocOptNurse optNurse = docOptNurseService.searchOptNurseByRegOptId(regOptId);
		if(optNurse == null){
			resp.setResultCode("40000002");
			resp.setResultMessage("护理记录单不存在");
			return resp.getJsonStr();
		}
		
		List<DocInstrubillItem> instrubillItem = docInstrubillItemService.searchInstrubillItemByRegOptId(regOptId, null);
		SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
		
		DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
		SearchFormBean search = new SearchFormBean();
		search.setDocId(anaesRecord.getAnaRecordId());
		List<EvtOptRealOper> evtOptRealOperList = evtOptRealOperService.searchOptRealOperList(search);
		//手术名字来源麻醉记录单中的 手术方式
		StringBuffer designedOptName = new StringBuffer();
		if(null != evtOptRealOperList && evtOptRealOperList.size()>0)
		{
			for(int i =0;i<evtOptRealOperList.size();i++)
			{
				EvtOptRealOper evtOptRealOper = evtOptRealOperList.get(i);
				if(i ==( evtOptRealOperList.size() -1))
				{
					designedOptName.append(evtOptRealOper.getName());
				}else
				{
					designedOptName.append(evtOptRealOper.getName());
					designedOptName.append(" | ");
				}
			}
		}
		searchRegOptByIdFormBean.setDesignedOptName(designedOptName.toString());
				
		//术前巡回护士
		SearchFormBean searchBean = new SearchFormBean();
	    searchBean.setDocId(anaesRecord.getAnaRecordId());
		String isLocalAnaes = searchRegOptByIdFormBean.getIsLocalAnaes();
		List<String> preCircunurseList = new ArrayList<String>();
        if (null == optNurse.getPreCircunurseId() || "1".equals(type))
        {
            if ("0".equals(isLocalAnaes))
            {
                searchBean.setRole(EvtParticipantService.ROLE_NURSE);
                searchBean.setType(EvtParticipantService.OPER_TYPE_TOUR);
                List<UserInfoFormBean> tourNurseList = evtParticipantService.getSelectParticipantList(searchBean);
                if (tourNurseList != null && tourNurseList.size() > 0) {
                    for (int i = 0; i < tourNurseList.size(); i++) {
                        preCircunurseList.add(tourNurseList.get(i).getId());
                    }
                }
            } else {
                //局麻时从手术排程中获取到器械护士
                BasDispatch dispatch = basDispatchService.getDispatchOper(regOptId);
                if (null != dispatch) {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(dispatch.getCircunurseId1())) {
                        preCircunurseList.add(dispatch.getCircunurseId1());
                    }
                    
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(dispatch.getCircunurseId2())) {
                        preCircunurseList.add(dispatch.getCircunurseId2());
                    }
                }
            }
        }
        else if (!"".equals(optNurse.getPreCircunurseId()))
        {
            String[] circuNurseAry = optNurse.getPreCircunurseId().split(",");
            if (null != circuNurseAry && circuNurseAry.length > 0) {
                for (int i = 0; i < circuNurseAry.length; i++) {
                    preCircunurseList.add(circuNurseAry[i]);
                }
            }
        }
        optNurse.setPreCircunurseList(preCircunurseList);
        
        //术后巡回护士
        optNurse.setPostCircunurseList(StringUtils.getListByString(optNurse.getPostCircunurseId()));
        
        //术中巡回护士
        optNurse.setMidCircunurseList(StringUtils.getListByString(optNurse.getMidCircunurseId()));
        
        List<String> preInstrnurseList = new ArrayList<String>();
        //术前洗手护士
        if (null == optNurse.getInstrnuseId() || "1".equals(type))
        {
            if ("0".equals(isLocalAnaes))
            {
                searchBean.setRole(EvtParticipantService.ROLE_NURSE);
                searchBean.setType(EvtParticipantService.OPER_TYPE_INSTRUMENT);
                List<UserInfoFormBean> instruNurseList = evtParticipantService.getSelectParticipantList(searchBean);
                if (instruNurseList != null && instruNurseList.size() > 0)
                {
                	for(UserInfoFormBean userInfoFormBean : instruNurseList)
                	{
                		preInstrnurseList.add(userInfoFormBean.getId());
                	}
                	optNurse.setPreInstrnurseList(preInstrnurseList);
                }
            }
            else
            {
                //局麻时从手术排程中获取到巡回护士
                BasDispatch dispatch = basDispatchService.getDispatchOper(regOptId);
                if (null != dispatch)
                {
                    if (StringUtils.isNotBlank(dispatch.getInstrnurseId1()))
                    {
                    	preInstrnurseList.add(basUserService.searchUserById(dispatch.getInstrnurseId1(), getBeid()).getUserName());
                    }
                    if (StringUtils.isNotBlank(dispatch.getInstrnurseId2()))
                    {
                    	preInstrnurseList.add(basUserService.searchUserById(dispatch.getInstrnurseId2(), getBeid()).getUserName());
                    }
                }
            }
        }else if (!"".equals(optNurse.getPreInstrnurseId()))
        {
            String[] instrnurseAry = optNurse.getPreInstrnurseId().split(",");
            if (null != instrnurseAry && instrnurseAry.length > 0) {
                for (int i = 0; i < instrnurseAry.length; i++) {
                    preInstrnurseList.add(instrnurseAry[i]);
                }
            }
        }
        optNurse.setPreInstrnurseList(preInstrnurseList);
        
        //术中巡回护士
        optNurse.setMidInstrnurseList(StringUtils.getListByString(optNurse.getMidInstrnurseId()));
        
        //术后巡回护士
        optNurse.setPostInstrnurseList(StringUtils.getListByString(optNurse.getPostInstrnurseId()));
        
        // 手术体位处理
        List<String> optBodyList = new ArrayList<String>();
        if (null == optNurse.getOptBody() || "1".equals(map.get("type")))
        {
            // 全麻时从麻醉记录单获取
            if ("0".equals(isLocalAnaes))
            {
                String optBodys = anaesRecord.getOptBody();
                optBodyList = StringUtils.getListByString(optBodys);
                optNurse.setOptBody(optBodys);
            }
            // 局麻时从基本信息获取
            else
            {
                BasDispatch dispatch = basDispatchService.getDispatchOper(regOptId);
                optBodyList.add(dispatch.getOptBody());
            }
        }
        else
        {
            optBodyList = StringUtils.getListByString(optNurse.getOptBody());
        }
        optNurse.setOptBodyList(optBodyList);
        
        if ((null == optNurse.getInOperRoomTime() && "0".equals(isLocalAnaes)) || "1".equals(map.get("type")))
        {
            String inOperRoomTime = anaesRecord.getInOperRoomTime();
            
            if (null != inOperRoomTime && null != DateUtils.getParseTime(inOperRoomTime))
            {
                optNurse.setInOperRoomTime(DateUtils.getParseTime(inOperRoomTime));
            }
        }
        
        if ((null == optNurse.getOutOperRoomTime() && "0".equals(isLocalAnaes)) || "1".equals(map.get("type")))
        {
            String outOperRoomTime = anaesRecord.getOutOperRoomTime();
            
            if (null != outOperRoomTime && null != DateUtils.getParseTime(outOperRoomTime))
            {
                optNurse.setOutOperRoomTime(DateUtils.getParseTime(outOperRoomTime));
            }
        }
        
        if ("0".equals(isLocalAnaes) && null == optNurse.getAnaesTime())
        {
            String anaesStartTime = anaesRecord.getAnaesStartTime();
            if (null != anaesStartTime && null != DateUtils.getParseTime(anaesStartTime))
            {
                optNurse.setAnaesTime(DateUtils.getParseTime(anaesStartTime));
            }
        }
        
        //手术名称list
        BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
		List<DesignedOptCodes> operDefList = new ArrayList<DesignedOptCodes>();
		if (0 == regOpt.getIsLocalAnaes())
		{ // 全麻
			List<EvtOptRealOper> optRealOperList = evtOptRealOperService.searchOptRealOperList(searchBean);
			for (EvtOptRealOper optRealOper : optRealOperList)
			{
				DesignedOptCodes designedOptCode = new DesignedOptCodes();
				designedOptCode.setOperDefId(optRealOper.getOperDefId());
				designedOptCode.setName(optRealOper.getName());
				designedOptCode.setPinYin(PingYinUtil.getFirstSpell(optRealOper
						.getName()));
				operDefList.add(designedOptCode);
			}
		} else
		{
			if(StringUtils.isBlank(optNurse.getOperatorId()))
			{
				operDefList = BasRegOptUtils.getOperDefList(searchRegOptByIdFormBean.getDesignedOptCode());
			}else
			{
				operDefList = BasRegOptUtils.getOperDefList(optNurse.getOperatorId());
			}
		}
		optNurse.setOperationNameList(operDefList);
        
        // 局麻手术第一次进入手术室时，需要将手术状态改为术中
        if ("1".equals(isLocalAnaes) && null == optNurse.getInOperRoomTime())
        {
            controllerService.changeControllerState(searchRegOptByIdFormBean.getRegOptId(), OperationState.SURGERY);
            optNurse.setInOperRoomTime(new Date());
            OptNurseInstrubillItemFormbean optNurseItem = new OptNurseInstrubillItemFormbean();
            optNurseItem.setOptNurse(optNurse);
            docOptNurseService.updateOptNurse(optNurseItem);
            
            // 将消息推送到手术室大屏
            String bedStr = StringUtils.isNotBlank(regOpt.getBed()) == true ? regOpt.getBed() + "床的" : "";
            WebSocketHandler.sentMessageToAllUser(regOpt.getDeptName() + regOpt.getRegionName() + bedStr
                + regOpt.getName() + "开始手术");
        }
        
        JSONObject jasonObject1 = JSONObject.fromObject(optNurse.getNegativePlate());
        optNurse.setNegativePlateMap(null == jasonObject1 ? new HashMap<String, Object>() : jasonObject1);
        JSONObject jasonObject2 = JSONObject.fromObject(optNurse.getTourniquetPlace());
        optNurse.setTourniquetPlaceMap(null == jasonObject2 ? new HashMap<String, Object>() : jasonObject2);
        JSONObject jasonObject3 = JSONObject.fromObject(optNurse.getHeatDeviceDetail());
        optNurse.setHeatDeviceMap(null == jasonObject3 ? new HashMap<String, Object>() : jasonObject3);
        
        if ("1".equals(type))
        {
            OptNurseInstrubillItemFormbean optNurseItem = new OptNurseInstrubillItemFormbean();
            optNurseItem.setOptNurse(optNurse);
            docOptNurseService.updateOptNurse(optNurseItem);
        }
        
		resp.put("result", "true");
		resp.put("resultCode", "1");
		resp.put("resultMessage", "查询成功!");
		resp.put("optNurseItem", optNurse);
		resp.put("instrubillItem", instrubillItem);
		resp.put("searchRegOptByIdFormBean", searchRegOptByIdFormBean);
		logger.info("-----------------end searchOptNurseByRegOptId-----------------");
		return resp.getJsonStr();
	}

	@RequestMapping(value = "/searchOptNurseForSYBX")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取手术清点单",httpMethod="POST",notes="根据手术ID获取手术清点单")
    public String searchOptNurseForSYBX(@ApiParam(name="map", value ="查询参数") @RequestBody Map map) {
        logger.info("----------------------begin searchOptNurseForSYBX-----------------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        
        DocOptNurse optNurse = docOptNurseService.searchOptNurseByRegOptId(regOptId);
        if (optNurse == null)
        {
            resp.setResultCode("40000002");
            resp.setResultMessage("护理记录单不存在");
            return resp.getJsonStr();
        }
        
        List<DocInstrubillItem> instrubillItem = docInstrubillItemService.searchInstrubillItemByRegOptId(regOptId, null);
        if (null != instrubillItem && instrubillItem.size() > 0)
        {
            for (DocInstrubillItem dib : instrubillItem)
            {
                dib.setInaddInt(null != dib.getInadd() ? Integer.parseInt(dib.getInadd()) : 0);
            }
        }
        
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        
        DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
        SearchFormBean searchBean = new SearchFormBean();
        searchBean.setDocId(anaesRecord.getAnaRecordId());
        String isLocalAnaes = searchRegOptByIdFormBean.getIsLocalAnaes();
        
        // 器械护士处理 首次进入清点单或者点击数据同步时，不从清点表中获取数据
        List<String> instrnuseList = new ArrayList<String>();
        if (null == optNurse.getInstrnuseId() || "1".equals(map.get("type")))
        {
            // 全麻时从麻醉记录单中获取到器械护士
            if ("0".equals(isLocalAnaes))
            {
                searchBean.setRole(EvtParticipantService.ROLE_NURSE);
                searchBean.setType(EvtParticipantService.OPER_TYPE_INSTRUMENT);
                List<UserInfoFormBean> instruNurseList = evtParticipantService.getSelectParticipantList(searchBean);
                if (instruNurseList != null && instruNurseList.size() > 0)
                {
                    for (int i = 0; i < instruNurseList.size(); i++)
                    {
                        instrnuseList.add(instruNurseList.get(i).getId());
                    }
                    optNurse.setInstrnuseId(StringUtils.getStringByList(instrnuseList));
                }
            }
            else
            {
                // 局麻时从手术排程中获取到器械护士
                BasDispatch dispatch = basDispatchService.getDispatchOper(regOptId);
                if (null != dispatch)
                {
                    if (StringUtils.isNotBlank(dispatch.getInstrnurseId1()))
                    {
                        instrnuseList.add(dispatch.getInstrnurseId1());
                    }
                    
                    if (StringUtils.isNotBlank(dispatch.getInstrnurseId2()))
                    {
                        instrnuseList.add(dispatch.getInstrnurseId2());
                    }
                }
            }
        }
        else
        {
            instrnuseList = StringUtils.getListByString(optNurse.getInstrnuseId());
        }
        optNurse.setInstrnuseList(instrnuseList);
        
        // 巡回护士处理 首次进入清点单或者点击数据同步时，不从清点表中获取数据
        List<String> circunurseList = new ArrayList<String>();
        if (null == optNurse.getCircunurseId() || "1".equals(map.get("type")))
        {
            // 全麻时从麻醉记录单中获取到巡回护士
            if ("0".equals(isLocalAnaes))
            {
                searchBean.setRole(EvtParticipantService.ROLE_NURSE);
                searchBean.setType(EvtParticipantService.OPER_TYPE_TOUR);
                List<UserInfoFormBean> circuNurseList = evtParticipantService.getSelectParticipantList(searchBean);
                if (circuNurseList != null && circuNurseList.size() > 0)
                {
                    for (int i = 0; i < circuNurseList.size(); i++)
                    {
                        circunurseList.add(circuNurseList.get(i).getId());
                    }
                    optNurse.setCircunurseId(StringUtils.getStringByList(circunurseList));
                }
            }
            else
            {
                // 局麻时从手术排程中获取到巡回护士
                BasDispatch dispatch = basDispatchService.getDispatchOper(regOptId);
                if (null != dispatch)
                {
                    if (StringUtils.isNotBlank(dispatch.getCircunurseId1()))
                    {
                        circunurseList.add(dispatch.getCircunurseId1());
                    }
                    
                    if (StringUtils.isNotBlank(dispatch.getCircunurseId2()))
                    {
                        circunurseList.add(dispatch.getCircunurseId2());
                    }
                }
            }
        }
        else
        {
            circunurseList = StringUtils.getListByString(optNurse.getCircunurseId());
        }
        optNurse.setCircunurseList(circunurseList);
        
        // 手术名称处理 首次进入清点单或者点击数据同步时，不从清点表中获取数据
        List<DesignedOptCodes> operationNameList = new ArrayList<DesignedOptCodes>();
        String operatorIds = optNurse.getOperatorId();
        String operatorId = "";
        String operatorName = "";
        if (null == operatorIds || "1".equals(map.get("type")))
        {
            // 全麻时从麻醉记录单中获取到手术名称
            if ("0".equals(isLocalAnaes))
            {
                List<EvtOptRealOper> optROList = evtOptRealOperService.searchOptRealOperList(searchBean);
                if (optROList != null && optROList.size() > 0)
                {
                    for (int i = 0; i < optROList.size(); i++)
                    {
                        DesignedOptCodes optCode = new DesignedOptCodes();
                        String operId = optROList.get(i).getOperDefId();
                        BasOperdef basOperdef = basOperdefService.queryOperdefById(operId);
                        if(null != basOperdef)
                        {
                            optCode.setOperDefId(operId);
                            optCode.setName(basOperdef.getName());
                            optCode.setPinYin(basOperdef.getPinYin());
                            operationNameList.add(optCode);
                            if (StringUtils.isBlank(operatorId))
                            {
                                operatorId = operId;
                            }
                            else
                            {
                                operatorId = operatorId + "," + operId;
                            }
                            
                            if (StringUtils.isBlank(operatorName))
                            {
                                operatorName = basOperdef.getName();
                            }
                            else
                            {
                                operatorName = operatorName + "," + basOperdef.getName();
                            }
                        }
                        
                    }
                }
            }
            else
            {
                // 局麻时从手术基本信息中获取到手术名称
                if (StringUtils.isNotBlank(searchRegOptByIdFormBean.getDesignedOptCode()))
                {
                    operationNameList = BasRegOptUtils.getOperDefList(searchRegOptByIdFormBean.getDesignedOptCode());
                    operatorId = searchRegOptByIdFormBean.getDesignedOptCode();
                    operatorName = searchRegOptByIdFormBean.getDesignedOptName();
//                    String[] ary = searchRegOptByIdFormBean.getDesignedOptCode().split(",");
//                    for (int i = 0; i < ary.length; i++)
//                    {
//                        DesignedOptCodes optCode = new DesignedOptCodes();
//                        BasOperdef basOperdef = basOperdefService.queryOperdefById(ary[i]);
//                        if (null != basOperdef)
//                        {
//                            optCode.setOperDefId(ary[i]);
//                            optCode.setName(basOperdef.getName());
//                            optCode.setPinYin(basOperdef.getPinYin());
//                        }
//                        operationNameList.add(optCode);
//                    }
                }
            }
        }
        else if (StringUtils.isNotBlank(operatorIds))
        {
            operationNameList = BasRegOptUtils.getOperDefList(operatorIds);
//            String[] ary = operatorId.split(",");
//            for (int i = 0; i < ary.length; i++)
//            {
//                DesignedOptCodes optCode = new DesignedOptCodes();
//                BasOperdef basOperdef = basOperdefService.queryOperdefById(ary[i]);
//                if (null != basOperdef)
//                {
//                    optCode.setOperDefId(ary[i]);
//                    optCode.setName(basOperdef.getName());
//                    optCode.setPinYin(basOperdef.getPinYin());
//                }
//                operationNameList.add(optCode);
//            }
        }
        optNurse.setOperationNameList(operationNameList);
        
        // 手术体位处理
        List<String> optBodyList = new ArrayList<String>();
        if (null == optNurse.getOptBody() || "1".equals(map.get("type")))
        {
            // 全麻时从麻醉记录单获取
            if ("0".equals(isLocalAnaes))
            {
                String optBodys = anaesRecord.getOptBody();
                optBodyList = StringUtils.getListByString(optBodys);
                optNurse.setOptBody(optBodys);
            }
            // 局麻时从基本信息获取
            else
            {
                BasDispatch dispatch = basDispatchService.getDispatchOper(regOptId);
                optBodyList.add(dispatch.getOptBody());
            }
        }
        else
        {
            optBodyList = StringUtils.getListByString(optNurse.getOptBody());
        }
        optNurse.setOptBodyList(optBodyList);
        
        // 麻醉方法处理
        List<String> anaesMethodList = new ArrayList<String>();
        if (null == optNurse.getAnaesMethodId() || "1".equals(map.get("type")))
        {
            if ("0".equals(isLocalAnaes))
            {
                List<EvtRealAnaesMethod> realAnaesMethodList =
                    evtRealAnaesMethodService.searchRealAnaesMethodList(searchBean);
                if (null != realAnaesMethodList && realAnaesMethodList.size() > 0)
                {
                    for (int i = 0; i < realAnaesMethodList.size(); i++)
                    {
                        anaesMethodList.add(realAnaesMethodList.get(i).getAnaMedId());
                    }
                    optNurse.setAnaesMethodId(StringUtils.getStringByList(anaesMethodList));
                }
            }
            else
            {
                // 局麻从手术基本信息中获取到麻醉方法
                anaesMethodList = StringUtils.getListByString(searchRegOptByIdFormBean.getDesignedAnaesMethodCode());
            }
        }
        else
        {
            anaesMethodList = StringUtils.getListByString(optNurse.getAnaesMethodId());
        }
        optNurse.setAnaesMethodList(anaesMethodList);
        
        if ((null == optNurse.getInOperRoomTime() && "0".equals(isLocalAnaes)) || "1".equals(map.get("type")))
        {
            String inOperRoomTime = anaesRecord.getInOperRoomTime();
            
            if (null != inOperRoomTime && null != DateUtils.getParseTime(inOperRoomTime))
            {
                optNurse.setInOperRoomTime(DateUtils.getParseTime(inOperRoomTime));
            }
        }
        
        if ((null == optNurse.getOutOperRoomTime() && "0".equals(isLocalAnaes)) || "1".equals(map.get("type")))
        {
            String outOperRoomTime = anaesRecord.getOutOperRoomTime();
            
            if (null != outOperRoomTime && null != DateUtils.getParseTime(outOperRoomTime))
            {
                optNurse.setOutOperRoomTime(DateUtils.getParseTime(outOperRoomTime));
            }
        }
        
        // 血型列表
        List<SysCodeFormbean> bloodTypeList = basSysCodeService.searchSysCodeByGroupId("blood_type", getBeid());
        searchBean.setSubType("1");
        List<SearchOptOperIoevent> transfusionList = evtInEventService.searchIoeventList(searchBean);
        searchBean.setSubType("2");
        List<SearchOptOperIoevent> bloodList = evtInEventService.searchIoeventList(searchBean);
        
        //全麻或者数据同步时，从麻醉记录单获取尿量和血型
        if ("0".equals(isLocalAnaes) || "1".equals(map.get("type")))
        {
            if (anaesRecord != null) {
                Integer bleeding = evtEgressService.getEgressCountValueByIoDefName("出血量", anaesRecord.getAnaRecordId())==null?0:evtEgressService.getEgressCountValueByIoDefName("出血量", anaesRecord.getAnaRecordId());
                optNurse.setBleeding(bleeding); //失血量
                Integer urine = evtEgressService.getEgressCountValueByIoDefName("尿量", anaesRecord.getAnaRecordId()) == null ?0:evtEgressService.getEgressCountValueByIoDefName("尿量", anaesRecord.getAnaRecordId());
                optNurse.setUrine(urine);    //尿量
                Integer infusion = evtInEventService.getIoeventCountValueByIoDef(anaesRecord.getAnaRecordId(),null,null) == null?0:evtInEventService.getIoeventCountValueByIoDef(anaesRecord.getAnaRecordId(),null,null);
                optNurse.setInfusion(infusion);      //输液
            }
            
            if (null != bloodList && bloodList.size() > 0)
            {
                for (SearchOptOperIoevent searchOptOperIoevent : bloodList)
                {
                    if (StringUtils.isNotBlank(searchOptOperIoevent.getBlood()))
                    {
                        boolean flag = false;
                        for (SysCodeFormbean scf : bloodTypeList)
                        {
                            if (scf.getCodeName().equals(searchOptOperIoevent.getBlood()))
                            {
                                optNurse.setBloodType(scf.getCodeValue());
                                flag = true;
                                break;
                            } 
                        }
                        if (flag)
                        {
                            break;
                        }
                    }
                }
            }
        }
        
        // 点击数据同步时 需要将数据更新到数据库中
        if ("1".equals("type"))
        {
            OptNurseInstrubillItemFormbean optNurseItem = new OptNurseInstrubillItemFormbean();
            optNurseItem.setOptNurse(optNurse);
            docOptNurseService.updateOptNurse(optNurseItem);
        }
        
        // 局麻手术第一次进入手术室时，需要将手术状态改为术中
        if ("1".equals(isLocalAnaes) && null == optNurse.getInOperRoomTime())
        {
            controllerService.changeControllerState(searchRegOptByIdFormBean.getRegOptId(), OperationState.SURGERY);
            optNurse.setInOperRoomTime(new Date());
            OptNurseInstrubillItemFormbean optNurseItem = new OptNurseInstrubillItemFormbean();
            optNurseItem.setOptNurse(optNurse);
            docOptNurseService.updateOptNurse(optNurseItem);
            
            // 将消息推送到手术室大屏
            BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
            String bedStr = StringUtils.isNotBlank(regOpt.getBed()) == true ? regOpt.getBed() + "床的" : "";
            WebSocketHandler.sentMessageToAllUser(regOpt.getDeptName() + regOpt.getRegionName() + bedStr
                + regOpt.getName() + "开始手术");
        }
        optNurse.setBloodSignList(StringUtils.getListByString(optNurse.getBloodSign()));
        
        
        resp.put("result", "true");
        resp.put("optNurseItem", optNurse);
        resp.put("instrubillItem", instrubillItem);
        resp.put("searchRegOptByIdFormBean", searchRegOptByIdFormBean);
        resp.put("bloodTypeList", bloodTypeList);
        resp.put("transfusionList", transfusionList);
        resp.put("bloodList", bloodList);
        
        logger.info("---------------------------end searchOptNurseByRegOptId------------------------");
        return resp.getJsonStr();
    }
    /**
     * 
     * @discription 根据手术ID获取手术护理(永兴人民)
     * @author 
     * @created 2018-03-22 下午5:13:48
     * @param regOptId
     * @return
     */
    @RequestMapping(value = "/searchOptNurseByRegOptIdYXRM")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取手术清点单",httpMethod="POST",notes="根据手术ID获取手术清点单")
    public String searchOptNurseByRegOptIdYXRM(@ApiParam(name="map", value ="查询参数")@RequestBody Map<String,Object> map) {
        logger.info("------------------begin searchOptNurseByRegOptIdYXRM----------------------------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        
        DocOptNurse optNurse = docOptNurseService.searchOptNurseByRegOptId(regOptId);
        if (optNurse == null)
        {
            resp.put("resultCode", "40000002");
            resp.put("resultMessage", "护理记录单不存在");
            return resp.getJsonStr();
        }
        List<DocInstrubillItem> instrubillItem1 =
            docInstrubillItemService.searchInstrubillItemByRegOptId(regOptId, "1");// 器械
        
        List<DocInstrubillItem> instrubillItem2 =
            docInstrubillItemService.searchInstrubillItemByRegOptId(regOptId, "2");// 敷料
        
        
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        
            
        
        DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
        SearchFormBean searchBean = new SearchFormBean();
        searchBean.setDocId(anaesRecord.getAnaRecordId());
        
        // 首次进入清点单或者点击数据同步时，不从清点表中获取数据
        List<String> instrnuseList = new ArrayList<String>();
        if ("1".equals(map.get("type")))
        {
            // 全麻时从麻醉记录单中获取到器械护士
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_INSTRUMENT);
            List<UserInfoFormBean> instruNurseList = evtParticipantService.getSelectParticipantList(searchBean);
            if (instruNurseList != null && instruNurseList.size() > 0)
            {
                for (int i = 0; i < instruNurseList.size(); i++)
                {
                    instrnuseList.add(instruNurseList.get(i).getId());
                }
            }
        }
        else if (StringUtils.isNotBlank(optNurse.getInstrnuseId()))
        {
            String[] instruNurseAry = optNurse.getInstrnuseId().split(",");
            if (null != instruNurseAry && instruNurseAry.length > 0)
            {
                for (int i = 0; i < instruNurseAry.length; i++)
                {
                    instrnuseList.add(instruNurseAry[i]);
                }
            }
        }
        optNurse.setInstrnuseList(instrnuseList);
        optNurse.setInstrnuseId(StringUtils.getStringByList(instrnuseList));
        
        // 首次进入清点单或者点击数据同步时，不从清点表中获取数据
        List<String> circunurseList = new ArrayList<String>();
        if ("1".equals(map.get("type")))
        {
            // 全麻时从麻醉记录单中获取到巡回护士
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_TOUR);
            List<UserInfoFormBean> circuNurseList = evtParticipantService.getSelectParticipantList(searchBean);
            if (circuNurseList != null && circuNurseList.size() > 0)
            {
                for (int i = 0; i < circuNurseList.size(); i++)
                {
                    circunurseList.add(circuNurseList.get(i).getId());
                }
            }
        }
        else if (StringUtils.isNotBlank(optNurse.getCircunurseId()))
        {
            String[] circuNurseAry = optNurse.getCircunurseId().split(",");
            if (null != circuNurseAry && circuNurseAry.length > 0)
            {
                for (int i = 0; i < circuNurseAry.length; i++)
                {
                    circunurseList.add(circuNurseAry[i]);
                }
            }
        }
        optNurse.setCircunurseList(circunurseList);
        optNurse.setCircunurseId(StringUtils.getStringByList(circunurseList));
        
        // 手术医生
        optNurse.setOperDoctorList(StringUtils.getListByString(optNurse.getOperDoctor()));
        
        optNurse.setShiftCircunurseList(StringUtils.getListByString(optNurse.getShiftCircunurseId()));
        optNurse.setShiftInstrnuseList(StringUtils.getListByString(optNurse.getShiftInstrnuseId())); 
        
        // 手术名称
        BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
        List<DesignedOptCodes> operDefList = new ArrayList<DesignedOptCodes>();
        String operatorId = "";
        String operatorName = "";
        
        if (0 == regOpt.getIsLocalAnaes())
        { // 全麻
            List<EvtOptRealOper> optRealOperList = evtOptRealOperService.searchOptRealOperList(searchBean);
            for (EvtOptRealOper optRealOper : optRealOperList)
            {
                DesignedOptCodes designedOptCode = new DesignedOptCodes();
                designedOptCode.setOperDefId(optRealOper.getOperDefId());
                designedOptCode.setName(optRealOper.getName());
                designedOptCode.setPinYin(PingYinUtil.getFirstSpell(optRealOper.getName()));
                operDefList.add(designedOptCode);
                
                if (StringUtils.isBlank(operatorId))
                {
                    operatorId = optRealOper.getOperDefId();
                }
                else
                {
                    operatorId = operatorId + "," + optRealOper.getOperDefId();
                }
                
                if (StringUtils.isBlank(operatorName))
                {
                    operatorName = optRealOper.getName();
                }
                else
                {
                    operatorName = operatorName + "," + optRealOper.getName();
                }
            }
        }
        else
        {
            DocOptCareRecord optCareRecord = docOptCareRecordService.selectByRegOptId(regOptId);
            if (null != optCareRecord)
            {
                operDefList = BasRegOptUtils.getOperDefList(optCareRecord.getOperationCode());
                operatorId = optCareRecord.getOperationCode();
                operatorName = optCareRecord.getOperationName();
            }
        }
        optNurse.setOperationNameList(operDefList);
        optNurse.setOperatorId(operatorId);
        optNurse.setOperatorName(operatorName);
        
        // 数据同步
        if ("1".equals("type"))
        {
            OptNurseInstrubillItemFormbean optNurseItem = new OptNurseInstrubillItemFormbean();
            optNurseItem.setOptNurse(optNurse);
            docOptNurseService.updateOptNurse(optNurseItem);
        }
        resp.put("result", "true");
        resp.put("resultCode", "1");
        resp.put("resultMessage", "查询成功!");
        resp.put("optNurseItem", optNurse);
        resp.put("instrubillItem1", instrubillItem1);
        resp.put("instrubillItem2", instrubillItem2);
        resp.put("searchRegOptByIdFormBean", searchRegOptByIdFormBean);
        
        logger.info("---------------end searchOptNurseByRegOptIdYXRM----------------------");
        return resp.getJsonStr();
    }
    

	/**
	 * 
	 * @discription 修改手术护理
	 * @author chengwang
	 * @created 2015-10-20 上午9:33:40
	 * @param docId
	 * @return
	 */
	@RequestMapping(value = "/updateOptNurse")
	@ResponseBody
	@ApiOperation(value="修改手术清点单",httpMethod="POST",notes="修改手术清点单")
	public String updateOptNurse(@ApiParam(name="optNurseItem", value ="修改参数") @RequestBody OptNurseInstrubillItemFormbean optNurseItem) {
		logger.info("-----------------begin updateOptNurse-----------------");
		ResponseValue resp = new ResponseValue();
		ValidatorBean validatorBean = beanValidator(optNurseItem.getOptNurse());
		if(!(validatorBean.isValidator())){
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
		resp = docOptNurseService.updateOptNurse(optNurseItem);
		logger.info("-----------------end updateOptNurse-----------------");
		return resp.getJsonStr();
	}

    @RequestMapping(value = "/updateOptNurseForSYBX")
    @ResponseBody
    @ApiOperation(value="修改手术清点单",httpMethod="POST",notes="修改手术清点单")
    public String updateOptNurseForSYBX(@ApiParam(name="optNurseFormBean", value ="修改参数") @RequestBody OptNurseInstrubillItemFormbean optNurseFormBean) 
    {
        logger.info("-------------------------begin updateOptNurseForSYBX---------------------------------");
        ResponseValue resp = new ResponseValue();
        DocOptNurse optNurse = optNurseFormBean.getOptNurse();
        if (optNurse == null)
        {
            resp.setResultCode("40000002");
            resp.setResultMessage("护理记录单不存在");
            return resp.getJsonStr();
        }
        String regOptId = optNurse.getRegOptId() != null ? optNurse.getRegOptId() : "";
        DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
        BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
        optNurse.setInstrnuseId(StringUtils.getStringByList(optNurse.getInstrnuseList()));
        optNurse.setCircunurseId(StringUtils.getStringByList(optNurse.getCircunurseList()));
        optNurse.setBloodSign(StringUtils.getStringByList(optNurse.getBloodSignList()));
        List<DesignedOptCodes> operationNameList = optNurse.getOperationNameList();
        String operatorId = "";
        String operatorName = "";
        if (null != operationNameList && operationNameList.size() > 0)
        {
            for (DesignedOptCodes designedOptCode : operationNameList)
            {
                if (StringUtils.isBlank(operatorId))
                {
                    operatorId = designedOptCode.getOperDefId(); 
                }
                else
                {
                    operatorId = operatorId + "," + designedOptCode.getOperDefId();
                }
                
                if (StringUtils.isBlank(operatorName))
                {
                    operatorName = designedOptCode.getName();
                }
                else
                {
                    operatorName = operatorName + "," + designedOptCode.getName();
                }
            }
        }
        optNurse.setOperatorId(operatorId);
        optNurse.setOperatorName(operatorName);
        
        optNurse.setOptBody(StringUtils.getStringByList(optNurse.getOptBodyList()));
        optNurse.setAnaesMethodId(StringUtils.getStringByList(optNurse.getAnaesMethodList()));
        
        optNurse.setNegativePlate(String.valueOf(optNurse.getNegativePlateMap()));
        optNurse.setTourniquetPlace(String.valueOf(optNurse.getTourniquetPlaceMap()));
        optNurse.setHeatDeviceDetail(String.valueOf(optNurse.getHeatDeviceMap()));
        
        // 局麻手术时，提交护理单需要将手术状态改为术后
        if (1 == regOpt.getIsLocalAnaes() && "END".equals(optNurse.getProcessState()))
        {
            controllerService.changeControllerState(regOptId, OperationState.POSTOPERATIVE);
            
            String leaveTo = "";
            // 将消息推送到手术室大屏
            if (null != optNurse.getLeaveTo())
            {
                if (1 == optNurse.getLeaveTo())
                {
                    leaveTo = "病房";
                }
                
                if (2 == optNurse.getLeaveTo())
                {
                    leaveTo = "PACU";
                }
                
                if (3 == optNurse.getLeaveTo())
                {
                    leaveTo = "ICU";
                }
                
                if (4 == optNurse.getLeaveTo())
                {
                    leaveTo = "死亡";
                }
            }
            
            WebSocketHandler.sentMessageToAllUser(regOpt.getDeptName() + regOpt.getRegionName() + regOpt.getBed()
                + regOpt.getName() + "手术已结束,去往" + leaveTo);
            
            // 获取麻醉记录单信息，局麻时将手术开始时间和结束时间写入到麻醉记录单中
            anaesRecord.setInOperRoomTime(DateUtils.formatDateTime(optNurse.getInOperRoomTime()));
            anaesRecord.setOperStartTime(DateUtils.formatDateTime(optNurse.getInOperRoomTime()));
            anaesRecord.setOperEndTime(DateUtils.formatDateTime(optNurse.getOutOperRoomTime()));
            anaesRecord.setOutOperRoomTime(DateUtils.formatDateTime(optNurse.getOutOperRoomTime()));
            anaesRecord.setOptBody(optNurse.getOptBody());
            anaesRecord.setLeaveTo(optNurse.getLeaveTo()+"");
            docAnaesRecordService.saveAnaesRecord(anaesRecord);
        }
        optNurseFormBean.setOptNurse(optNurse);
        resp = docOptNurseService.updateOptNurseForSYBX(optNurseFormBean);
        
        logger.info("--------------------------end updateOptNurseForSYBX----------------------------");
        return resp.getJsonStr();
    }

	/**
	 * 
	 * @discription 插入器械
	 * @author chengwang
	 * @created 2015-10-22 下午1:56:23
	 * @return
	 */
	@RequestMapping(value = "/insertInstrubillItem")
	@ResponseBody
	@ApiOperation(value="插入器械",httpMethod="POST",notes="插入器械")
	public String insertInstrubillItem(@ApiParam(name="map", value ="保存参数") @RequestBody Map<String, Object> map) {
		logger.info("-----------------begin insertInstrubillItem-----------------");
		ResponseValue resp = new ResponseValue();
		String regOptId = map.get("regOptId") == null ? "" : map.get("regOptId").toString();
		String optNurseId = map.get("optNurseId") == null ? "" : map.get("optNurseId").toString();
		String instrumentCode = map.get("instrumentId") == null ? "" : map.get("instrumentId").toString();
		String instrsuitCode = map.get("instrsuitId") == null ? "" : map.get("instrsuitId").toString();
		String type = map.get("type") == null ? "" : map.get("type").toString();
		String instrumentName = map.get("instrumentName") == null ? "" : map.get("instrumentName").toString();
		List<DocInstrubillItem> list = docOptNurseService.insertInstubillItem(regOptId,
				optNurseId, instrumentCode, instrsuitCode, type, instrumentName);
		resp.put("result", list);
		logger.info("-----------------end insertInstrubillItem-----------------");
		return resp.getJsonStr();
	}
	
	
	/**
     * 
     * @discription 插入器械
     * @author chengwang
     * @created 2015-10-22 下午1:56:23
     * @return
     */
    @RequestMapping(value = "/insertInstrubillItemSYZXYY")
    @ResponseBody
    @ApiOperation(value="插入器械",httpMethod="POST",notes="插入器械")
    public String insertInstrubillItemSYZXYY(@ApiParam(name="map", value ="保存参数") @RequestBody Map<String, Object> map) {
        logger.info("-----------------begin insertInstrubillItem-----------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") == null ? "" : map.get("regOptId").toString();
        String optNurseId = map.get("optNurseId") == null ? "" : map.get("optNurseId").toString();
        String instrumentCode = map.get("instrumentId") == null ? "" : map.get("instrumentId").toString();
        String instrsuitCode = map.get("instrsuitId") == null ? "" : map.get("instrsuitId").toString();
        String type = map.get("type") == null ? "" : map.get("type").toString();
        String instrumentName = map.get("instrumentName") == null ? "" : map.get("instrumentName").toString();
        List<DocInstrubillItem> list = docOptNurseService.insertInstubillItemSYZXYY(regOptId,
                optNurseId, instrumentCode, instrsuitCode, type, instrumentName);
        resp.put("result", list);
        logger.info("-----------------end insertInstrubillItem-----------------");
        return resp.getJsonStr();
    }

	/**
	 * 
	 * @discription 删除器械
	 * @author chengwang
	 * @created 2015-10-23 上午9:39:38
	 */
	@RequestMapping(value = "/deleteInstrubillItem")
	@ResponseBody
	@ApiOperation(value="删除器械",httpMethod="POST",notes="删除器械")
	public String deleteInstrubillItem(@ApiParam(name="map", value ="删除参数") @RequestBody Map<String, Object> map) {
		logger.info("-----------------begin deleteInstrubillItem-----------------");
		ResponseValue resp = new ResponseValue();
		String emptyFlag = map.get("emptyFlag")!=null?map.get("emptyFlag").toString():null;
		String instruItemId = map.get("instruItemId")!=null?map.get("instruItemId").toString():"";
		String regOptId = map.get("regOptId")!=null?map.get("regOptId").toString():null;
		String type = map.get("type")!=null?map.get("type").toString():null;
		int result = 0;
		if ("1".equals(emptyFlag))
		{
		    result = docInstrubillItemService.deleteByRegOptId(regOptId, type);
		}
		else
		{
		    result =docInstrubillItemService.deleteInstrubillItem(instruItemId);
		}
		if(result == 1){
			resp.setResultCode("1");
			resp.setResultMessage("删除手术所用器械成功!");
		}
		if(result == 0){
			resp.setResultCode("1");
			resp.setResultMessage("删除手术所用器械失败!");
		}
		logger.info("-----------------end deleteInstrubillItem-----------------");
		return resp.getJsonStr();
	}
	
    /**
     * 
     * @discription 根据手术ID获取手术护理(南华局点)
     * @author chengwang
     * @created 2015-10-10 下午5:13:48
     * @param regOptId
     * @return
     */
    @RequestMapping(value = "/searchOptNurseByRegOptIdQNZZY")
    @ResponseBody
    @ApiOperation(value="根据手术ID获取手术清点单",httpMethod="POST",notes="根据手术ID获取手术清点单")
    public String searchOptNurseByRegOptIdQNZZY(@ApiParam(name="map", value ="查询参数")@RequestBody Map map) {
        logger.info("------------------begin searchOptNurseByRegOptIdForQNZZY----------------------------------");
        ResponseValue resp = new ResponseValue();
        String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
        
        DocOptNurse optNurse = docOptNurseService.searchOptNurseByRegOptId(regOptId);
        if (optNurse == null)
        {
            resp.put("resultCode", "40000002");
            resp.put("resultMessage", "护理记录单不存在");
            return resp.getJsonStr();
        }
        List<DocInstrubillItem> instrubillItem1 =
            docInstrubillItemService.searchInstrubillItemByRegOptId(regOptId, "1");// 器械
        
        List<DocInstrubillItem> instrubillItem2 =
            docInstrubillItemService.searchInstrubillItemByRegOptId(regOptId, "2");// 敷料
        
        
        SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptService.searchApplicationById(regOptId);
        
            
        
        DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
        SearchFormBean searchBean = new SearchFormBean();
        searchBean.setDocId(anaesRecord.getAnaRecordId());
        String isLocalAnaes = searchRegOptByIdFormBean.getIsLocalAnaes();
        
        // 首次进入清点单或者点击数据同步时，不从清点表中获取数据
        List<String> instrnuseList = new ArrayList<String>();
        if ("1".equals(map.get("type")))
        {
            // 全麻时从麻醉记录单中获取到器械护士
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_INSTRUMENT);
            List<UserInfoFormBean> instruNurseList = evtParticipantService.getSelectParticipantList(searchBean);
            if (instruNurseList != null && instruNurseList.size() > 0)
            {
                for (int i = 0; i < instruNurseList.size(); i++)
                {
                    instrnuseList.add(instruNurseList.get(i).getId());
                }
            }
        }
        else if (StringUtils.isNotBlank(optNurse.getInstrnuseId()))
        {
            String[] instruNurseAry = optNurse.getInstrnuseId().split(",");
            if (null != instruNurseAry && instruNurseAry.length > 0)
            {
                for (int i = 0; i < instruNurseAry.length; i++)
                {
                    instrnuseList.add(instruNurseAry[i]);
                }
            }
        }
        optNurse.setInstrnuseList(instrnuseList);
        optNurse.setInstrnuseId(StringUtils.getStringByList(instrnuseList));
        
        // 首次进入清点单或者点击数据同步时，不从清点表中获取数据
        List<String> circunurseList = new ArrayList<String>();
        if ("1".equals(map.get("type")))
        {
            // 全麻时从麻醉记录单中获取到巡回护士
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_TOUR);
            List<UserInfoFormBean> circuNurseList = evtParticipantService.getSelectParticipantList(searchBean);
            if (circuNurseList != null && circuNurseList.size() > 0)
            {
                for (int i = 0; i < circuNurseList.size(); i++)
                {
                    circunurseList.add(circuNurseList.get(i).getId());
                }
            }
        }
        else if (StringUtils.isNotBlank(optNurse.getCircunurseId()))
        {
            String[] circuNurseAry = optNurse.getCircunurseId().split(",");
            if (null != circuNurseAry && circuNurseAry.length > 0)
            {
                for (int i = 0; i < circuNurseAry.length; i++)
                {
                    circunurseList.add(circuNurseAry[i]);
                }
            }
        }
        optNurse.setCircunurseList(circunurseList);
        optNurse.setCircunurseId(StringUtils.getStringByList(circunurseList));
        
        // 手术医生
        optNurse.setOperDoctorList(StringUtils.getListByString(optNurse.getOperDoctor()));
        
        optNurse.setShiftCircunurseList(StringUtils.getListByString(optNurse.getShiftCircunurseId()));
        optNurse.setShiftInstrnuseList(StringUtils.getListByString(optNurse.getShiftInstrnuseId())); 
        
        //术中巡回护士
        optNurse.setMidCircunurseList(StringUtils.getListByString(optNurse.getMidCircunurseId()));
        
        // 手术名称
        BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
        List<DesignedOptCodes> operDefList = new ArrayList<DesignedOptCodes>();
        String operatorId = "";
        String operatorName = "";
        
        if (0 == regOpt.getIsLocalAnaes())
        { // 全麻
            List<EvtOptRealOper> optRealOperList = evtOptRealOperService.searchOptRealOperList(searchBean);
            for (EvtOptRealOper optRealOper : optRealOperList)
            {
                DesignedOptCodes designedOptCode = new DesignedOptCodes();
                designedOptCode.setOperDefId(optRealOper.getOperDefId());
                designedOptCode.setName(optRealOper.getName());
                designedOptCode.setPinYin(PingYinUtil.getFirstSpell(optRealOper.getName()));
                operDefList.add(designedOptCode);
                
                if (StringUtils.isBlank(operatorId))
                {
                    operatorId = optRealOper.getOperDefId();
                }
                else
                {
                    operatorId = operatorId + "," + optRealOper.getOperDefId();
                }
                
                if (StringUtils.isBlank(operatorName))
                {
                    operatorName = optRealOper.getName();
                }
                else
                {
                    operatorName = operatorName + "," + optRealOper.getName();
                }
            }
        }
        else
        {
            DocOptCareRecord optCareRecord = docOptCareRecordService.selectByRegOptId(regOptId);
            if (null != optCareRecord)
            {
                operDefList = BasRegOptUtils.getOperDefList(optCareRecord.getOperationCode());
                operatorId = optCareRecord.getOperationCode();
                operatorName = optCareRecord.getOperationName();
            }
        }
        optNurse.setOperationNameList(operDefList);
        optNurse.setOperatorId(operatorId);
        optNurse.setOperatorName(operatorName);
        
        // 数据同步
        if ("1".equals("type"))
        {
            OptNurseInstrubillItemFormbean optNurseItem = new OptNurseInstrubillItemFormbean();
            optNurseItem.setOptNurse(optNurse);
            docOptNurseService.updateOptNurse(optNurseItem);
        }
        resp.put("result", "true");
        resp.put("resultCode", "1");
        resp.put("resultMessage", "查询成功!");
        resp.put("optNurseItem", optNurse);
        resp.put("instrubillItem1", instrubillItem1);
        resp.put("instrubillItem2", instrubillItem2);
        resp.put("searchRegOptByIdFormBean", searchRegOptByIdFormBean);
        
        logger.info("---------------end searchOptNurseByRegOptIdForQNZZY----------------------");
        return resp.getJsonStr();
    }
}
