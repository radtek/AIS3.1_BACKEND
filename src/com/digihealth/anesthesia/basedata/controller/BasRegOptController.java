package com.digihealth.anesthesia.basedata.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.basedata.formbean.DocumentStateFormbean;
import com.digihealth.anesthesia.basedata.formbean.EmgencyOperationFormBean;
import com.digihealth.anesthesia.basedata.formbean.OneRegOptDocumentStateFormbean;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasOperroom;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.utils.BasRegOptUtils;
import com.digihealth.anesthesia.basedata.utils.CustomConfigureUtil;
import com.digihealth.anesthesia.common.beanvalidator.ValidatorBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.po.DocAnaesPacuRec;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.evt.formbean.CancleRegOptFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchConditionFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchMyOperationFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchOperaPatrolRecordFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByLoginNameAndStateFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByRoomIdAndOperDateAndStateFormBean;
import com.digihealth.anesthesia.evt.po.EvtAnaesEvent;
import com.digihealth.anesthesia.evt.po.EvtParticipant;
import com.digihealth.anesthesia.evt.service.EvtAnaesEventService;
import com.digihealth.anesthesia.evt.service.EvtParticipantService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "/operation")
@Api(value="BasRegOptController",description="手术信息处理类")
public class BasRegOptController extends BaseController {

	/**
	 * 
	 * @discription 根据登录账号和手术状态获取病人列表信息描述类
	 * @author chengwang
	 * @created 2015-10-9 上午10:30:21
	 * @return
	 */
	@RequestMapping(value = "/getRegOptByState")
	@ResponseBody
	@ApiOperation(value="根据登录账号和手术状态获取病人列表信息描述类",httpMethod="POST",notes="根据登录账号和手术状态获取病人列表信息描述类")
	public String searchRegOptByUserTypeAndState(@ApiParam(name="searchConditionFormBean", value ="查询参数") @RequestBody SearchConditionFormBean searchConditionFormBean) {
		logger.info("-----------------begin getRegOptByState-----------------");
		ResponseValue resp = new ResponseValue();
		List<SearchRegOptByLoginNameAndStateFormBean> result = basRegOptService.searchRegOptByAnaesDoctorAndState(searchConditionFormBean);
		int total = basRegOptService.searchRegOptTotalByAnaesDoctorAndState(searchConditionFormBean);
		resp.put("resultList", result);
		resp.put("total", total);
		logger.info("-----------------end getRegOptByState-----------------");
		return resp.getJsonStr();
	}

	@RequestMapping(value = "/getRegOptByStateSYBX")
	@ResponseBody
	@ApiOperation(value="根据登录账号和手术状态获取病人列表信息描述类",httpMethod="POST",notes="根据登录账号和手术状态获取病人列表信息描述类")
	public String searchRegOptByUserTypeAndStateSYBX(@ApiParam(name="searchConditionFormBean", value ="查询参数") @RequestBody SearchConditionFormBean searchConditionFormBean) {
		logger.info("-----------------begin getRegOptByStateSYBX-----------------");
		ResponseValue resp = new ResponseValue();
		int total = basRegOptService.searchRegOptTotalByAnaesDoctorAndStateSYBX(searchConditionFormBean);
		List<SearchRegOptByLoginNameAndStateFormBean> result = basRegOptService.searchRegOptByAnaesDoctorAndStateSYBX(searchConditionFormBean);
		resp.put("resultList", result);
		resp.put("total", total);
		logger.info("-----------------end getRegOptByStateSYBX-----------------");
		return resp.getJsonStr();
	}

	/**
     * 
     * @discription 根据角色和归档状态查询手术信息
     * @author zhouyi
     * @created 2018-05-11 上午09:50:21
     * @return
     */
    @RequestMapping(value = "/getRegOptByRoleTypeAndState")
    @ResponseBody
    @ApiOperation(value="根据角色和手术状态获取病人列表信息描述类",httpMethod="POST",notes="根据登录账号和手术状态获取病人列表信息描述类")
    public String getRegOptByRoleTypeAndState(@ApiParam(name="searchConditionFormBean", value ="查询参数") @RequestBody SearchConditionFormBean searchConditionFormBean) {
        logger.info("-----------------begin getRegOptByRoleTypeAndState-----------------");
        ResponseValue resp = new ResponseValue();
        List<SearchRegOptByLoginNameAndStateFormBean> result = basRegOptService.searchRegOptByRoleTypeAndState(searchConditionFormBean);
        int total = basRegOptService.searchRegOptTotalByRoleTypeAndState(searchConditionFormBean);
        resp.put("resultList", result);
        resp.put("total", total);
        logger.info("-----------------end getRegOptByRoleTypeAndState-----------------");
        return resp.getJsonStr();
    }
	
	/**
	 * 
	 * @discription 根据登录账号和手术状态获取归档手术列表信息描述类
	 * @author chengwang
	 * @created 2015-10-9 上午10:30:21
	 * @return
	 */
	@RequestMapping(value = "/getRegOptByArchstate")
	@ResponseBody
	@ApiOperation(value="根据登录账号和手术状态获取归档手术列表信息描述类",httpMethod="POST",notes="根据登录账号和手术状态获取归档手术列表信息描述类")
	public String getRegOptByArchstate(@ApiParam(name="searchConditionFormBean", value ="查询参数") @RequestBody SearchConditionFormBean searchConditionFormBean) {
		logger.info("-----------------begin getRegOptByState-----------------");
		ResponseValue resp = new ResponseValue();
		List<SearchRegOptByLoginNameAndStateFormBean> result = basRegOptService.searchRegOptByArchstate(searchConditionFormBean);
		int total = basRegOptService.searchRegOptTotalByArchstate(searchConditionFormBean);
		resp.put("resultList", result);
		resp.put("total", total);
		logger.info("-----------------end getRegOptByState-----------------");
		return resp.getJsonStr();
	}
	
	
	@RequestMapping(value = "/getAnesDucumentStateByRegOptId")
	@ResponseBody
	@ApiOperation(value="一键式打印查询",httpMethod="POST",notes="一键式打印查询")
	public String getAnesDucumentStateByRegOptId(@ApiParam(name="searchConditionFormBean", value ="查询参数") @RequestBody SearchConditionFormBean searchConditionFormBean) {
		logger.info("-----------------begin getAnesDucumentStateByRegOptId-----------------");
		ResponseValue resp = new ResponseValue();
		List<DocumentStateFormbean> result = basRegOptService.getAnesDucumentStateByRegOptId(searchConditionFormBean);
		resp.put("resultList", result);
		logger.info("-----------------end getAnesDucumentStateByRegOptId-----------------");
		return resp.getJsonStr();
	}

	/**
	 * 
	 * @discription 获取手术申请界面查看病人详细信息
	 * @author chengwang
	 * @created 2015-10-19 下午4:14:23
	 * @return
	 */
	@RequestMapping(value = "/getRegOptApplicationById",method= RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="获取手术申请界面查看病人详细信息",httpMethod="POST",notes="获取手术申请界面查看病人详细信息")
	public String searchApplicationById(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
		logger.info("-----------------begin getRegOptApplicationById-----------------");
		ResponseValue resp = new ResponseValue();
		String regOptId = map.get("regOptId")!=null?map.get("regOptId").toString():"";
		SearchRegOptByIdFormBean resultFormBean = basRegOptService.searchApplicationById(regOptId);
		resp.put("resultList", resultFormBean);
		
		logger.info("-----------------end getRegOptApplicationById-----------------");
		return resp.getJsonStr();
	}

	/**
	 * 
	 * @discription 审核
	 * @author chengwang
	 * @created 2015-10-19 下午5:21:17
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/checkOperation")
	@ResponseBody
	@ApiOperation(value="审核",httpMethod="POST",notes="审核")
	public String checkOperation(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
		logger.info("-----------------begin checkOperation-----------------");
		ResponseValue respValue = new ResponseValue();
		controllerService.checkOperation(map.get("regOptIds").toString(), OperationState.NO_SCHEDULING, OperationState.NOT_REVIEWED,respValue);
		logger.info("-----------------begin checkOperation-----------------");
		return respValue.getJsonStr();
	}

	/**
     * 
     * @discription 审核
     * @author chengwang
     * @created 2015-10-19 下午5:21:17
     * @param ids
     * @return
     */
    @RequestMapping(value = "/checkOperationSYBX")
    @ResponseBody
    @ApiOperation(value="审核",httpMethod="POST",notes="审核")
    public String checkOperationSYBX(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
        logger.info("-----------------begin checkOperationSYBX-----------------");
        ResponseValue respValue = new ResponseValue();
        controllerService.checkOperationSYBX(map.get("regOptIds").toString(), OperationState.NO_SCHEDULING, OperationState.NOT_REVIEWED,respValue);
        basDispatchService.initDocDataSYBX(map.get("regOptIds").toString());
        logger.info("-----------------begin checkOperationSYBX-----------------");
        return respValue.getJsonStr();
    }

    @RequestMapping(value = "/checkOperationLLZY")
    @ResponseBody
    @ApiOperation(value="审核",httpMethod="POST",notes="审核")
    public String checkOperationLLZY(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
        logger.info("-----------------begin checkOperationLLZY-----------------");
        ResponseValue respValue = new ResponseValue();
        controllerService.checkOperation(map.get("regOptIds").toString(), OperationState.NO_SCHEDULING, OperationState.NOT_REVIEWED,respValue);
        basDispatchService.initDocDataLLZY(map.get("regOptIds").toString());
        logger.info("-----------------begin checkOperationLLZY-----------------");
        return respValue.getJsonStr();
    }

    /**
	 * 
	 * @discription 获取单个手术室即将进行手术的病人列表
	 * @author chengwang
	 * @created 2015-10-10 上午9:46:23
	 * @param roomId
	 * @param operDate
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "/getRegOptByRoomIdAndOperDateAndState")
	@ResponseBody
	@ApiOperation(value="获取单个手术室即将进行手术的病人列表",httpMethod="POST",notes="获取单个手术室即将进行手术的病人列表")
	public String searchRegOptByRoomIdAndOperDateAndState(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
		logger.info("-----------------begin getRegOptByRoomIdAndOperDateAndState-----------------");
		ResponseValue respValue = new ResponseValue();
		List<String> stateList = new ArrayList<String>();
		String[] states = (map.get("state").toString()).split(",");
		for (int i = 0; i < states.length; i++) {
			stateList.add(states[i]);
		}
		List<SearchRegOptByRoomIdAndOperDateAndStateFormBean> resultList = basRegOptService
				.searchRegOptByRoomIdAndOperDateAndState(DateUtils.getDate(), stateList);
		respValue.put("resultList", resultList);
		BasOperroom operRoom = basOperroomService.queryRoomListById(null);
		String operRoomName = operRoom!=null?operRoom.getName():"";
		String roomId = operRoom!=null?operRoom.getOperRoomId():"";
		respValue.put("operRoomName", operRoomName);
		respValue.put("roomId", roomId);
		logger.info("-----------------end getRegOptByRoomIdAndOperDateAndState-----------------");
		return respValue.getJsonStr();
	}
	/**
	 * 术中巡视人员列表查询
	 * @param baseQuery
	 * @return
	 */
	@RequestMapping(value = "/queryOperaPatrolRecordList")
	@ResponseBody
	@ApiOperation(value="术中巡视人员列表查询",httpMethod="POST",notes="术中巡视人员列表查询")
	public String queryOperaPatrolRecordList(@ApiParam(name="baseQuery", value ="查询参数") @RequestBody BaseInfoQuery baseQuery) {
		logger.info("-----------------begin queryOperaPatrolRecordList-----------------");
		ResponseValue respValue = new ResponseValue();
		List<SearchOperaPatrolRecordFormBean> operaPatrolList = basRegOptService.getOperaPatrolRecordList(baseQuery);
		respValue.put("resultList", operaPatrolList);
		logger.info("-----------------end queryOperaPatrolRecordList-----------------");
		return respValue.getJsonStr();
	}
	
	@RequestMapping(value = "/endOperation")
	@ResponseBody
	@ApiOperation(value="结束手术",httpMethod="POST",notes="结束手术")
	public String endOperation(){
		logger.info("-----------------begin endOperation-----------------");
		ResponseValue resp = new ResponseValue();
		BaseInfoQuery baseQuery = new BaseInfoQuery();
		baseQuery.setState(OperationState.SURGERY);
		//判断当前手术室是否存在未完成的手术，如果存在则返回错误消息
		List<SearchOperaPatrolRecordFormBean> operaPatrolList = basRegOptService.getOperaPatrolRecordListByRoomId(baseQuery);
		if(operaPatrolList.size()>0){
			for(int i = 0;i<operaPatrolList.size();i++){
				DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(operaPatrolList.get(i).getRegOptId());
				if(anaesRecord!=null){
					EvtAnaesEvent anaesevent = new EvtAnaesEvent();
					anaesevent.setDocId(anaesRecord.getAnaRecordId());
					anaesevent.setOccurTime(new Date());
					anaesevent.setLeaveTo("1");
					anaesevent.setCode(EvtAnaesEventService.OUT_ROOM);
					evtAnaesEventService.saveAnaesevent(anaesevent,resp);
				}
			}
		}
		//resp.setResultCode("1");
		//resp.setResultMessage("强制结束手术成功!");
		logger.info("-----------------end endOperation-----------------");
		return resp.getJsonStr();
	}
	
	/**
     * @discription 创建普通手术
     * @author chengwang       
     * @created 2015年10月30日 上午11:16:07     
     * @param regOpt
     * @return
	 */
	@RequestMapping(value = "/createRegOpt")
	@ResponseBody
	@ApiOperation(value="创建普通手术",httpMethod="POST",notes="创建普通手术")
	public String insertRegOpt(@ApiParam(name="regOpt", value ="手术信息参数") @RequestBody BasRegOpt regOpt) {
		logger.info("-----------------begin createRegOpt-----------------");
		ResponseValue resp = new ResponseValue();
		ValidatorBean validatorBean = beanValidator(regOpt);
		if(!(validatorBean.isValidator())){
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
		if(regOpt.getAge()==null&&regOpt.getAgeMon()==null&&regOpt.getAgeDay()==null){
			resp.setResultCode("10000001");
			resp.setResultMessage("年、月、天必填一项");
			return resp.getJsonStr();
		}
		int age = regOpt.getAge()==null?0:regOpt.getAge();
		int ageMon = regOpt.getAgeMon()==null?0:regOpt.getAgeMon();
		int ageDay = regOpt.getAgeDay()==null?0:regOpt.getAgeDay();
		if(age<1 && ageMon<1 && ageDay<1){
			resp.setResultCode("10000001");
			resp.setResultMessage("年、月、天其中一项必须要大于0");
			return resp.getJsonStr();
		}
		
		//String result = 
		basRegOptService.insertRegOpt(regOpt);
		resp.setResultCode("1");
		resp.setResultMessage("保存成功!");
		logger.info("-----------------end createRegOpt-----------------");
		return resp.getJsonStr();
	}
	
	/**
     * @discription 创建普通手术
     * @author chengwang       
     * @created 2015年10月30日 上午11:16:07     
     * @param regOpt
     * @return
     */
    @RequestMapping(value = "/createRegOptSYZXYY")
    @ResponseBody
    @ApiOperation(value="创建普通手术",httpMethod="POST",notes="创建普通手术")
    public String insertRegOptSYZXYY(@ApiParam(name="regOpt", value ="手术信息参数") @RequestBody BasRegOpt regOpt) {
        logger.info("-----------------begin createRegOptSYZXYY-----------------");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(regOpt);
        if(!(validatorBean.isValidator())){
            resp.setResultCode("10000001");
            resp.setResultMessage(validatorBean.getMessage());
            return resp.getJsonStr();
        }
        if(regOpt.getAge()==null&&regOpt.getAgeMon()==null&&regOpt.getAgeDay()==null){
            resp.setResultCode("10000001");
            resp.setResultMessage("年、月、天必填一项");
            return resp.getJsonStr();
        }
        int age = regOpt.getAge()==null?0:regOpt.getAge();
        int ageMon = regOpt.getAgeMon()==null?0:regOpt.getAgeMon();
        int ageDay = regOpt.getAgeDay()==null?0:regOpt.getAgeDay();
        if(age<1 && ageMon<1 && ageDay<1){
            resp.setResultCode("10000001");
            resp.setResultMessage("年、月、天其中一项必须要大于0");
            return resp.getJsonStr();
        }
        
        //String result = 
        basRegOptService.insertRegOptSYZXYY(regOpt);
        resp.setResultCode("1");
        resp.setResultMessage("保存成功!");
        logger.info("-----------------end createRegOptSYZXYY-----------------");
        return resp.getJsonStr();
    }

	/**
     * @discription 创建普通手术
     * @author chengwang       
     * @created 2015年10月30日 上午11:16:07     
     * @param regOpt
     * @return
     */
    @RequestMapping(value = "/createRegOptLLZY")
    @ResponseBody
    @ApiOperation(value="创建普通手术",httpMethod="POST",notes="创建普通手术")
    public String insertRegOptLLZY(@ApiParam(name="regOpt", value ="手术信息参数") @RequestBody BasRegOpt regOpt) {
        logger.info("-----------------begin createRegOptLLZY-----------------");
        ResponseValue resp = new ResponseValue();
        ValidatorBean validatorBean = beanValidator(regOpt);
        if(!(validatorBean.isValidator())){
            resp.setResultCode("10000001");
            resp.setResultMessage(validatorBean.getMessage());
            return resp.getJsonStr();
        }
        if(regOpt.getAge()==null&&regOpt.getAgeMon()==null&&regOpt.getAgeDay()==null){
            resp.setResultCode("10000001");
            resp.setResultMessage("年、月、天必填一项");
            return resp.getJsonStr();
        }
        int age = regOpt.getAge()==null?0:regOpt.getAge();
        int ageMon = regOpt.getAgeMon()==null?0:regOpt.getAgeMon();
        int ageDay = regOpt.getAgeDay()==null?0:regOpt.getAgeDay();
        if(age<1 && ageMon<1 && ageDay<1){
            resp.setResultCode("10000001");
            resp.setResultMessage("年、月、天其中一项必须要大于0");
            return resp.getJsonStr();
        }
        
        basRegOptService.insertRegOptLLZY(regOpt);
        resp.setResultCode("1");
        resp.setResultMessage("保存成功!");
        logger.info("-----------------end createRegOptLLZY-----------------");
        return resp.getJsonStr();
    }

	@RequestMapping(value = "/updateRegOptByHis")
	@ResponseBody
	@ApiOperation(value="修改手术信息",httpMethod="POST",notes="修改手术信息")
	public String updateRegOptByHis(@ApiParam(name="regOpt", value ="手术信息参数") @RequestBody BasRegOpt regOpt) {
		logger.info("-----------------begin updateRegOptByHis-----------------");
		ResponseValue resp = new ResponseValue();
		ValidatorBean validatorBean = beanValidator(regOpt);
		if(!(validatorBean.isValidator())){
			resp.setResultCode("10000001");
			resp.setResultMessage(validatorBean.getMessage());
			return resp.getJsonStr();
		}
		if(regOpt.getAge()==null&&regOpt.getAgeMon()==null&&regOpt.getAgeDay()==null){
			resp.setResultCode("10000001");
			resp.setResultMessage("年、月、天必填一项");
			return resp.getJsonStr();
		}
		int age = regOpt.getAge()==null?0:regOpt.getAge();
		int ageMon = regOpt.getAgeMon()==null?0:regOpt.getAgeMon();
		int ageDay = regOpt.getAgeDay()==null?0:regOpt.getAgeDay();
		if(age<1 && ageMon<1 && ageDay<1){
			resp.setResultCode("10000001");
			resp.setResultMessage("年、月、天其中一项必须要大于0");
			return resp.getJsonStr();
		}
		//String result = 
		basRegOptService.updateRegOptByHis(regOpt);
		resp.setResultCode("1");
		resp.setResultMessage("保存成功!");
		logger.info("-----------------end updateRegOptByHis-----------------");
		return resp.getJsonStr();
	}
	
	
	/**
	    * @discription 批量增加手术申请功能 演示用
	 */
	@RequestMapping(value = "/batchCreateRegOpt")
	@ResponseBody
	@ApiOperation(value="批量增加手术申请功能 演示用",httpMethod="POST",notes="批量增加手术申请功能 演示用")
	public String batchCreateRegOpt() {
		logger.info("-----------------begin batchCreateRegOpt-----------------");
		ResponseValue respValue = new ResponseValue();
		basRegOptService.batchCreateRegOpt(10);
		
		logger.info("-----------------end batchCreateRegOpt-----------------");
		return respValue.getJsonStr();
	}
	
	@RequestMapping(value = "/updateRegOpt")
	@ResponseBody
	@ApiOperation(value="修改手术信息",httpMethod="POST",notes="修改手术信息")
	public String updateRegOptInfo(@ApiParam(name="emgencyOperationFormBean", value ="手术信息参数") @RequestBody EmgencyOperationFormBean emgencyOperationFormBean) {
		logger.info("-----------------begin updateRegOptInfo-----------------");
		ResponseValue respValue = new ResponseValue();
		BasRegOpt regOpt = emgencyOperationFormBean.getRegOpt();
		BasDispatch dispatch = emgencyOperationFormBean.getDispatch();
		String beid = regOpt.getBeid();
        if (StringUtils.isBlank(beid)) {
            beid = getBeid();
            regOpt.setBeid(beid);
        }
        ValidatorBean validatorBean = beanValidator(regOpt);
		if(!(validatorBean.isValidator())){
			respValue.setResultCode("10000001");
			respValue.setResultMessage(validatorBean.getMessage());
			return respValue.getJsonStr();
		}
		BasRegOpt regOpt1 = basRegOptService.searchRegOptById(regOpt.getRegOptId());
		BasDispatch oldDispatch = basDispatchService.getDispatchOper(regOpt.getRegOptId());
		//如果是术中且变更了手术室，则需要判断变更后的手术室是否存在未结束的手术信息
		if (OperationState.SURGERY.equals(regOpt1.getState()) && !dispatch.getOperRoomId().equals(oldDispatch.getOperRoomId())){
            BaseInfoQuery baseQuery = new BaseInfoQuery();
            baseQuery.setState(OperationState.SURGERY);
			//判断当前手术室是否存在未完成的手术，如果存在则返回错误消息
            List<SearchOperaPatrolRecordFormBean> operaPatrolList = basRegOptService.getOperaPatrolRecordListByRoomId(baseQuery,dispatch.getOperRoomId());
            SearchOperaPatrolRecordFormBean po = null;
            if(operaPatrolList.size()>0){
                for (SearchOperaPatrolRecordFormBean searchOperaPatrolRecordFormBean : operaPatrolList) {
                    if("0".equals(searchOperaPatrolRecordFormBean.getIsLocalAnaes())){
                        po = searchOperaPatrolRecordFormBean;
                    }
                }
                if(null != po && !po.getRegOptId().equals(regOpt.getRegOptId())){
                	respValue.setResultCode("40000004");
                	respValue.setResultMessage(po.getOperRoomName()+"存在病人信息为："+po.getName()+",手术未完成!");
                	return respValue.getJsonStr();
                }
            }
		}
		
		if (dispatch != null) {
			ValidatorBean validatorBean1 = beanValidator(dispatch);
			if(!(validatorBean1.isValidator())){
				respValue.setResultCode("10000001");
				respValue.setResultMessage(validatorBean.getMessage());
				return respValue.getJsonStr();
			}
		}
		if(regOpt.getAge()==null&&regOpt.getAgeMon()==null&&regOpt.getAgeDay()==null){
			respValue.setResultCode("10000001");
			respValue.setResultMessage("年、月、天必填一项");
			return respValue.getJsonStr();
		}
		BasRegOptUtils.getOtherInfo(regOpt);
		BasRegOptUtils.IsLocalAnaesSet(regOpt);
		
		String result = "";
		if (null != dispatch)
		{
		    BasDispatch basDispatch = basDispatchService.getDispatchOper(regOpt.getRegOptId());
	        if (basDispatch != null) {
	            if(StringUtils.isEmpty(dispatch.getAnesthetistId()) && regOpt.getIsLocalAnaes() == 0
	                    && StringUtils.isNotEmpty(basDispatch.getOperRoomId())){
	                respValue.setResultCode("10000001");
	                respValue.setResultMessage("全麻手术必填麻醉医生");
	                return respValue.getJsonStr();
	            }
	        }
	        //如果是局麻手术，且isNeedDoct =1时，也需要填写麻醉医生
	        if(Objects.equals(regOpt.getIsLocalAnaes(), 1)){
				String isNeedDoct = CustomConfigureUtil.isNeedDoct();
				if("1".equals(isNeedDoct) && StringUtils.isEmpty(dispatch.getAnesthetistId())){
					respValue.setResultCode("10000001");
					respValue.setResultMessage("局麻手术必填麻醉医生");
	                return respValue.getJsonStr();
				}
	        }
	        
	        result = basRegOptService.updateRegOptInfo(regOpt, dispatch ,respValue);
		}
		else
		{
		    basRegOptService.updateRegOptByHis(regOpt);
		    result = "保存基本信息成功";
		}
		
		respValue.setResultCode("1");
		respValue.put("resultMessage", result);
		
		logger.info("-----------------end updateRegOptInfo-----------------");
		return respValue.getJsonStr();
	}
	
	/** 
	 * 修改手术信息(黔南州中医院定制)
	 * <功能详细描述>
	 * @param emgencyOperationFormBean
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@RequestMapping(value = "/updateRegOptQNZZY")
    @ResponseBody
    @ApiOperation(value="修改手术信息",httpMethod="POST",notes="修改手术信息")
    public String updateRegOptQNZZY(@ApiParam(name="emgencyOperationFormBean", value ="手术信息参数") @RequestBody EmgencyOperationFormBean emgencyOperationFormBean) {
        logger.info("-----------------begin updateRegOptInfo-----------------");
        ResponseValue respValue = new ResponseValue();
        BasRegOpt regOpt = emgencyOperationFormBean.getRegOpt();
        BasDispatch dispatch = emgencyOperationFormBean.getDispatch();
        String beid = regOpt.getBeid();
        if (StringUtils.isBlank(beid)) {
            beid = getBeid();
            regOpt.setBeid(beid);
        }
        ValidatorBean validatorBean = beanValidator(regOpt);
        if(!(validatorBean.isValidator())){
            respValue.setResultCode("10000001");
            respValue.setResultMessage(validatorBean.getMessage());
            return respValue.getJsonStr();
        }
        
        BasRegOpt regOpt1 = basRegOptService.searchRegOptById(regOpt.getRegOptId());
		BasDispatch oldDispatch = basDispatchService.getDispatchOper(regOpt.getRegOptId());
		//如果是术中且变更了手术室，则需要判断变更后的手术室是否存在未结束的手术信息
		if (OperationState.SURGERY.equals(regOpt1.getState()) && !dispatch.getOperRoomId().equals(oldDispatch.getOperRoomId())){
            BaseInfoQuery baseQuery = new BaseInfoQuery();
            baseQuery.setState(OperationState.SURGERY);
			//判断当前手术室是否存在未完成的手术，如果存在则返回错误消息
            List<SearchOperaPatrolRecordFormBean> operaPatrolList = basRegOptService.getOperaPatrolRecordListByRoomId(baseQuery,dispatch.getOperRoomId());
            SearchOperaPatrolRecordFormBean po = null;
            if(operaPatrolList.size()>0){
                for (SearchOperaPatrolRecordFormBean searchOperaPatrolRecordFormBean : operaPatrolList) {
                    if("0".equals(searchOperaPatrolRecordFormBean.getIsLocalAnaes())){
                        po = searchOperaPatrolRecordFormBean;
                    }
                }
                if(null != po && !po.getRegOptId().equals(regOpt.getRegOptId())){
                	respValue.setResultCode("40000004");
                	respValue.setResultMessage(po.getOperRoomName()+"存在病人信息为："+po.getName()+",手术未完成!");
                	return respValue.getJsonStr();
                }
            }
		}
        
        if (dispatch != null) {
            ValidatorBean validatorBean1 = beanValidator(dispatch);
            if(!(validatorBean1.isValidator())){
                respValue.setResultCode("10000001");
                respValue.setResultMessage(validatorBean.getMessage());
                return respValue.getJsonStr();
            }
        }
        if(regOpt.getAge()==null&&regOpt.getAgeMon()==null&&regOpt.getAgeDay()==null){
            respValue.setResultCode("10000001");
            respValue.setResultMessage("年、月、天必填一项");
            return respValue.getJsonStr();
        }
        BasRegOptUtils.getOtherInfo(regOpt);
        BasRegOptUtils.IsLocalAnaesSet(regOpt);
        
        String result = "";
        if (null != dispatch)
        {
            BasDispatch basDispatch = basDispatchService.getDispatchOper(regOpt.getRegOptId());
            if (basDispatch != null) {
                if(StringUtils.isEmpty(dispatch.getAnesthetistId()) && regOpt.getIsLocalAnaes() == 0
                        && StringUtils.isNotEmpty(basDispatch.getOperRoomId())){
                    respValue.setResultCode("10000001");
                    respValue.setResultMessage("全麻手术必填麻醉医生");
                    return respValue.getJsonStr();
                }
            }
            //如果是局麻手术，且isNeedDoct =1时，也需要填写麻醉医生
            if(Objects.equals(regOpt.getIsLocalAnaes(), 1)){
                String isNeedDoct = CustomConfigureUtil.isNeedDoct();
                if("1".equals(isNeedDoct) && StringUtils.isEmpty(dispatch.getAnesthetistId())){
                    respValue.setResultCode("10000001");
                    respValue.setResultMessage("局麻手术必填麻醉医生");
                    return respValue.getJsonStr();
                }
            }
            
            result = basRegOptService.updateRegOptInfoQNZZY(regOpt, dispatch);
        }
        else
        {
            basRegOptService.updateRegOptByHis(regOpt);
            result = "保存基本信息成功";
        }
        
        respValue.setResultCode("1");
        respValue.put("resultMessage", result);
        
        logger.info("-----------------end updateRegOptInfo-----------------");
        return respValue.getJsonStr();
    }
	
	/**
	 * 修改病人身高体重
	 * @param emgencyOperationFormBean
	 * @return
	 */
	@RequestMapping(value = "/saveRegOptWH")
	@ResponseBody
	@ApiOperation(value="修改病人身高体重",httpMethod="POST",notes="修改病人身高体重")
	public String saveRegOptWH(@ApiParam(name="regOpt", value ="手术信息参数") @RequestBody BasRegOpt regOpt) {
		logger.info("-----------------begin saveRegOptWH-----------------");
		ResponseValue value = new ResponseValue();
		BasRegOpt reg = basRegOptService.searchRegOptById(regOpt.getRegOptId());
		reg.setWeight(regOpt.getWeight());
		reg.setHeight(regOpt.getHeight());
		basRegOptService.updateRegOpt(reg);
		
		logger.info("-----------------end saveRegOptWH-----------------");
		return value.getJsonStr();
	}
	

	/**
	 * 
	 * @discription 取消手术
	 * @author chengwang
	 * @created 2015年10月30日 上午9:50:15
	 * @param cancleRegOptFormBean
	 * @return
	 */
	@RequestMapping(value = "/cancelRegOpt")
	@ResponseBody
	@ApiOperation(value="取消手术",httpMethod="POST",notes="取消手术")
	public String cancleRegOpt(@ApiParam(name="cancleRegOptFormBean", value ="手术信息参数") @RequestBody CancleRegOptFormBean cancleRegOptFormBean) {
		logger.info("-----------------begin cancelRegOpt-----------------");
		ResponseValue resp = new ResponseValue();
		basRegOptService.cancleRegOpt(cancleRegOptFormBean,resp);
		logger.info("-----------------end cancelRegOpt-----------------");
		return resp.getJsonStr();
	}
	
	/**
     * 
     * @discription 批量取消手术
     * @author chengwang
     * @created 2015年10月30日 上午9:50:15
     * @param cancleRegOptFormBean
     * @return
     */
    @RequestMapping(value = "/batchCancleRegOpt")
    @ResponseBody
    @ApiOperation(value="批量取消手术",httpMethod="POST",notes="批量取消手术")
    public String batchCancleRegOpt(@ApiParam(name="cancleRegOptFormBean", value ="手术信息参数") @RequestBody CancleRegOptFormBean cancleRegOptFormBean) {
        logger.info("-----------------begin batchCancleRegOpt-----------------");
        ResponseValue resp = new ResponseValue();
        basRegOptService.batchCancleRegOpt(cancleRegOptFormBean,resp);
        logger.info("-----------------end batchCancleRegOpt-----------------");
        return resp.getJsonStr();
    }
	
	/**
	 * 
	 * @discription 取消手术
	 * @created 2018年4月12日 上午9:50:15
	 * @param cancleRegOptFormBean
	 * @return
	 */
	@RequestMapping(value = "/cancelRegOptYXRM")
	@ResponseBody
	@ApiOperation(value="取消手术",httpMethod="POST",notes="取消手术")
	public String cancelRegOptYXRM(@ApiParam(name="cancleRegOptFormBean", value ="手术信息参数") @RequestBody CancleRegOptFormBean cancleRegOptFormBean) {
		logger.info("-----------------begin cancelRegOpt-----------------");
		ResponseValue resp = new ResponseValue();
		basRegOptService.cancleRegOptYXRM(cancleRegOptFormBean,resp);
		logger.info("-----------------end cancelRegOpt-----------------");
		return resp.getJsonStr();
	}
	
	/**
	 * 激活取消的数据
	 * @param activeRegOpt
	 * @return
	 */
	@RequestMapping(value = "/activeRegOpt")
	@ResponseBody
	@ApiOperation(value="激活取消的数据",httpMethod="POST",notes="激活取消的数据")
	public String activeRegOpt(@ApiParam(name="acticRegOptFormBean", value ="手术信息参数") @RequestBody CancleRegOptFormBean acticRegOptFormBean) {
		logger.info("-----------------begin activeRegOpt-----------------");
		ResponseValue resp = new ResponseValue();
		basRegOptService.activeRegOpt(acticRegOptFormBean,resp);
		logger.info("-----------------end activeRegOpt-----------------");
		return resp.getJsonStr();
	}
	
	/**
     * 批量激活取消的数据
     * @param activeRegOpt
     * @return
     */
    @RequestMapping(value = "/batchActiveRegOpt")
    @ResponseBody
    @ApiOperation(value="批量激活取消的数据",httpMethod="POST",notes="批量激活取消的数据")
    public String batchActiveRegOpt(@ApiParam(name="acticRegOptFormBean", value ="手术信息参数") @RequestBody CancleRegOptFormBean acticRegOptFormBean) {
        logger.info("-----------------begin batchActiveRegOpt-----------------");
        ResponseValue resp = new ResponseValue();
        basRegOptService.batchActiveRegOpt(acticRegOptFormBean,resp);
        logger.info("-----------------end batchActiveRegOpt-----------------");
        return resp.getJsonStr();
    }
	
	@RequestMapping(value = "/searchMyOperation")
	@ResponseBody
	@ApiOperation(value="查询我的手术排班信息",httpMethod="POST",notes="查询我的手术排班信息")
	public String searchMyOperation(@ApiParam(name="searchConditionFormBean", value ="查询参数") @RequestBody SearchConditionFormBean searchConditionFormBean){
		logger.info("-----------------begin searchMyOperation-----------------");
		ResponseValue resp = new ResponseValue();
		List<SearchMyOperationFormBean> resultList = basRegOptService.searchMyOperation(searchConditionFormBean);
		resp.setResultCode("1");
		resp.setResultMessage("获取我的排班成功!");
		resp.put("resultList", resultList);
		resp.put("total", resultList.size());
		
		logger.info("-----------------end searchMyOperation-----------------");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/searchTodayOperation")
	@ResponseBody
	@ApiOperation(value="查询今日手术信息",httpMethod="POST",notes="查询今日手术信息")
	public String searchTodayOperation(@ApiParam(name="searchConditionFormBean", value ="查询参数") @RequestBody SearchConditionFormBean searchConditionFormBean){
		logger.info("-----------------begin searchTodayOperation-----------------");
		ResponseValue resp = new ResponseValue();
		List<SearchMyOperationFormBean> resultList = basRegOptService.searchTodayOperation(searchConditionFormBean);
		resp.setResultCode("1");
		resp.setResultMessage("获取今日手术成功!");
		resp.put("resultList", resultList);
		resp.put("total", resultList.size());
		
		logger.info("-----------------end searchTodayOperation-----------------");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/searchApplication",method= RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="查询手术基础信息",httpMethod="POST",notes="查询手术基础信息")
	public String searchApplication(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
		logger.info("-----------------begin getRegOptApplicationById-----------------");
		ResponseValue resp = new ResponseValue();
		String regOptId = map.get("regOptId")!=null?map.get("regOptId").toString():"";
		BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
		DispatchFormbean dispatch = basDispatchService.getDispatchOperByRegOptId(regOptId);
		if (dispatch == null) {
			dispatch = new DispatchFormbean();
		}
		resp.setResultCode("1");
		resp.setResultMessage("查询基础信息成功!");
		/**
		 * 黔南州中医院需要返回
		 */
		DocAnaesPacuRec pacuRec = docAnaesPacuRecService.getAnaesPacuRecByRegOptId(regOptId);
		if(null != pacuRec){
			regOpt.setPacuId(pacuRec.getId());	
		}
		DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
		if (null != anaesRecord)
		{
		    regOpt.setAnalgesicMethod(anaesRecord.getAnalgesicMethod());
		}
		resp.put("resultRegOpt", regOpt);
		resp.put("resultDispatch", dispatch);
		logger.info("-----------------end getRegOptApplicationById-----------------");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/searchDocumentState",method= RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="查询文书状态",httpMethod="POST",notes="查询文书状态")
	public String searchDocumentState(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map) {
		logger.info("-----------------begin searchDocumentState-----------------");
		ResponseValue resp = new ResponseValue();
		String sql = "select processState from "+map.get("table").toString()+" where regOptId ='"+map.get("regOptId").toString()+"'";
		String result = basRegOptService.searchDocumentState(sql);
		resp.put("resultList", result);
		logger.info("-----------------end searchDocumentState-----------------");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/getDocumentStatuByRegOptId",method= RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value="根据手术ID查询文书状态",httpMethod="POST",notes="根据手术ID查询文书状态")
	public String getDocumentStatuByRegOptId(@ApiParam(name="map", value ="查询参数") @RequestBody Map<String, Object> map){
		logger.info("-----------------begin getDocumentStatuByRegOptId-----------------");
		String regOptId = map.get("regOptId").toString();
		ResponseValue req = new ResponseValue();
		List<OneRegOptDocumentStateFormbean> list = basRegOptService.getDocumentStatuByRegOptId(regOptId);
		req.put("resultList", list);
		logger.info("-----------------end getDocumentStatuByRegOptId-----------------");
		return req.getJsonStr();
	}
	
	/**
	 * 判断传入的删除的麻醉医生数据是否可以
	 * @param searchBean
	 * @return
	 */
	@RequestMapping(value = "/checkAnaesDocIsDel")
	@ResponseBody
	@ApiOperation(value="判断传入的删除的麻醉医生数据是否可以",httpMethod="POST",notes="判断传入的删除的麻醉医生数据是否可以")
	public String checkAnaesDocIsDel(@ApiParam(name="searchBean", value ="查询参数") @RequestBody SearchFormBean searchBean) {
		logger.info("-----------------begin checkAnaesDocIsDel-----------------");
		ResponseValue resp = new ResponseValue();
		//麻醉记录单
		DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(searchBean.getRegOptId());
		//设置文书ID
		searchBean.setDocId(anaesRecord.getAnaRecordId());
		//麻醉医生列表
		searchBean.setRole(EvtParticipantService.ROLE_ANESTH);
		List<EvtParticipant> anesDocList = evtParticipantService.searchParticipantList(searchBean);
		
		for (EvtParticipant participant : anesDocList) {
			//在手术排程时录入的麻醉医生不允许删除
			if(participant.getOperatorType().equals("01")){
				resp.setResultCode("200000001");
				resp.setResultMessage("主麻医生不允许删除!!");
				return resp.getJsonStr();
			}
			if(participant.getIsShiftChange()!=null){
				if(participant.getIsShiftChange().equals("1")&& participant.getUserLoginName().equals(searchBean.getId())){
					resp.setResultCode("200000001");
					resp.setResultMessage("人员已交接班，不允许删除!!");
					return resp.getJsonStr();
				}
			}
		}
		logger.info("-----------------end checkAnaesDocIsDel-----------------");
		return resp.getJsonStr();
	}
	
	@RequestMapping(value = "/checkIsDel")
	@ResponseBody
	@ApiOperation(value="checkIsDel",httpMethod="POST",notes="checkIsDel")
	public String checkIsDel(@ApiParam(name="searchBean", value ="查询参数") @RequestBody SearchFormBean searchBean) {
		logger.info("-----------------begin checkIsDel-----------------");
		ResponseValue resp = new ResponseValue();
		//麻醉记录单
		DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(searchBean.getRegOptId());
		//设置文书ID
		searchBean.setDocId(anaesRecord.getAnaRecordId());
		//麻醉医生列表
		//searchBean.setRole(searchBean.getType());
		List<EvtParticipant> pList = evtParticipantService.searchParticipantList(searchBean);
		
		for (EvtParticipant participant : pList) {
			if(participant.getRole().equals("O")){
				if(participant.getOperatorType().equals("06")){
					resp.setResultCode("200000001");
					resp.setResultMessage("主刀医生不允许删除!!");
					return resp.getJsonStr();
				}
			}
//			if(participant.getRole().equals("N")){
//				if(participant.getOperatorType().equals("06")){
//					resp.setResultCode("200000001");
//					resp.setResultMessage("主刀医生不允许删除!!");
//					return resp.getJsonStr();
//				}
//			}
		}
		logger.info("-----------------end checkIsDel-----------------");
		return resp.getJsonStr();
	}
	
	/**
	 * 
	     * @discription 保存医保号
	     * @author chengwang       
	     * @created 2016年5月19日 下午2:29:31     
	     * @param regOpt
	     * @return
	 */
	@RequestMapping(value = "/updateMsid")
	@ResponseBody
	@ApiOperation(value="保存医保号",httpMethod="POST",notes="保存医保号")
	public String updateMsid(@ApiParam(name="regOpt", value ="手术基本信息参数") @RequestBody BasRegOpt regOpt){
		logger.info("-----------------begin updateMsid-----------------");
		ResponseValue req = new ResponseValue();
		basRegOptService.updateMsid(regOpt.getRegOptId(), regOpt.getMsId());
		logger.info("-----------------end updateMsid-----------------");
		return req.getJsonStr();
	}
	
    /**
     * 
     * @discription 麻醉医生归档数据
     * @author chengwang
     * @created 2016年5月19日 下午2:29:31
     * @param regOpt
     * @return
     */
    @RequestMapping(value = "/updateArchstate")
    @ResponseBody
	@ApiOperation(value="麻醉医生归档数据",httpMethod="POST",notes="麻醉医生归档数据")
    public String updateArchstate(@ApiParam(name="map", value ="归档数据参数") @RequestBody Map<String, Object> map) {
		logger.info("-----------------begin updateArchstate-----------------");
		ResponseValue resp = new ResponseValue();
		resp = basRegOptService.updateArchstate((String)map.get("regOptIds"), (String)map.get("archstate"));
		logger.info("-----------------end updateArchstate-----------------");
        return resp.getJsonStr();
    }
	
    /**
     * 
     * @discription 护士归档数据
     * @author chengwang
     * @created 2016年5月19日 下午2:29:31
     * @param regOpt
     * @return
     */
    @RequestMapping(value = "/updateNurseArchstate")
    @ResponseBody
	@ApiOperation(value="护士归档数据",httpMethod="POST",notes="护士归档数据")
    public String updateNurseArchstate(@ApiParam(name="map", value ="归档数据参数") @RequestBody Map<String, Object> map) {
		logger.info("-----------------begin updateNurseArchstate-----------------");
		ResponseValue resp = new ResponseValue();
		resp = basRegOptService.updateNurseArchstate((String)map.get("regOptIds"), (String)map.get("nurseArchstate"));
		logger.info("-----------------end updateNurseArchstate-----------------");
        return resp.getJsonStr();
    }

    /**
     * 
     * @discription 根据登录账号和手术状态获取病人列表信息描述类
     * @author chengwang
     * @created 2015-10-9 上午10:30:21
     * @return
     */
    @RequestMapping(value = "/getRegOpt")
    @ResponseBody
    @ApiOperation(value="根据登录账号和手术状态获取病人列表信息描述类",httpMethod="POST",notes="根据登录账号和手术状态获取病人列表信息描述类")
    public String getRegOpt(@ApiParam(name="searchConditionFormBean", value ="查询参数") @RequestBody SearchConditionFormBean searchConditionFormBean) {
        logger.info("-----------------begin getRegOpt-----------------");
        ResponseValue resp = new ResponseValue();
        List<SearchRegOptByLoginNameAndStateFormBean> result = basRegOptService.getRegOpt(searchConditionFormBean);
        int total = basRegOptService.getRegOptTotal(searchConditionFormBean);
        resp.put("resultList", result);
        resp.put("total", total);
        logger.info("-----------------end getRegOpt-----------------");
        return resp.getJsonStr();
    }
}
