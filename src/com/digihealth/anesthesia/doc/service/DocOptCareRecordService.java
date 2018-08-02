package com.digihealth.anesthesia.doc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.formbean.DesignedOptCodes;
import com.digihealth.anesthesia.basedata.formbean.DispatchFormbean;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.po.BasOperdef;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.basedata.utils.BasRegOptUtils;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.formbean.OptCareRecordFormBean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.po.EvtOptRealOper;
import com.digihealth.anesthesia.evt.po.EvtRealAnaesMethod;
import com.digihealth.anesthesia.evt.service.EvtParticipantService;
import com.digihealth.anesthesia.sysMng.formbean.UserInfoFormBean;
import com.digihealth.anesthesia.websocket.WebSocketHandler;

@Service
public class DocOptCareRecordService extends BaseService {

	/**
	 * 
	 * @discription 根据手术ID获取手术护理记录单
	 * @author chengwang
	 * @created 2015-10-10 下午5:13:48
	 * @param regOptId
	 * @return
	 */
	@Transactional
	public ResponseValue searchOptCareRecordByRegOptId(Map<String, Object> map) {
		ResponseValue resp = new ResponseValue();
		try {
			String regOptId = map.get("regOptId") != null ? map.get("regOptId").toString() : "";
			DocOptCareRecord optCareRecord = docOptCareRecordDao.selectByRegOptId(regOptId);
			if (null == optCareRecord) {
				resp.setResultCode("40000002");
				resp.setResultMessage("护理记录不存在");
				return resp;
			}
			// 根据患者id获取到手术基本信息
			SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptDao.searchApplicationById(regOptId, getBeid());

			// 获取麻醉记录单信息
			DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);

			String anaRecordId = null;

			if (null != anaesRecord) {
				anaRecordId = anaesRecord.getAnaRecordId();
			}

			List<String> shiftChangedNurseList = new ArrayList<String>();
			List<String> instrnurseList = new ArrayList<String>();
			List<String> shiftChangeNurseList = new ArrayList<String>();

			// 如果是全麻，则开始时间、手术名称等信息需要从麻醉记录单获取
			if ("0".equals(searchRegOptByIdFormBean.getIsLocalAnaes())) {
				SearchFormBean searchBean = new SearchFormBean();
				searchBean.setDocId(anaRecordId);

				// 全麻时从麻醉记录单中获取到开始、结束、入室、出室等时间
				if (null != anaesRecord) {
					if (null == optCareRecord.getInOperRoomTime() || "1".equals(map.get("type"))) {
						optCareRecord.setInOperRoomTime(anaesRecord.getInOperRoomTime());
					}

					if (null == optCareRecord.getOutOperRoomTime() || "1".equals(map.get("type"))) {
						optCareRecord.setOutOperRoomTime(anaesRecord.getOutOperRoomTime());
					}

					if (null == optCareRecord.getOptbody() || "1".equals(map.get("type"))) {
						optCareRecord.setOptbody(anaesRecord.getOptBody());
					}

					if (StringUtils.isNotBlank(optCareRecord.getOptbody())) {
						String optbody = optCareRecord.getOptbody();
						List<String> optbodys = new ArrayList<String>();
						String[] obodys = optbody.split(",");
						for (String id : obodys) {
							optbodys.add(id);
						}
						optCareRecord.setOptbodys(optbodys);
					}
					List<DesignedOptCodes> operationNameList = new ArrayList<DesignedOptCodes>();
					if (null == optCareRecord.getOperationCode() || "1".equals(map.get("type"))) {
						// 根据麻醉记录单获取到手术名称
						List<EvtOptRealOper> optROList = evtOptRealOperDao.searchOptRealOperList(searchBean);
						if (optROList.size() > 0 && optROList != null) {
							optCareRecord.setOperationCode("");
							optCareRecord.setOperationName("");
							for (int i = 0; i < optROList.size(); i++) {
							    DesignedOptCodes designedOptCode = new DesignedOptCodes();
							    designedOptCode.setOperDefId(optROList.get(i).getOperDefId());
							    designedOptCode.setName(optROList.get(i).getName());
							    designedOptCode.setPinYin(PingYinUtil.getFirstSpell(optROList.get(i).getName()));
							    operationNameList.add(designedOptCode);
								optCareRecord.setOperationCode(optCareRecord.getOperationCode() + optROList.get(i).getOperDefId() + ",");
								optCareRecord.setOperationName(optCareRecord.getOperationName() + optROList.get(i).getName() + ",");
							}

							if (StringUtils.isNotBlank(optCareRecord.getOperationCode())) {
								optCareRecord.setOperationCode(optCareRecord.getOperationCode().substring(0, optCareRecord.getOperationCode().length() - 1));
							}

							if (StringUtils.isNotBlank(optCareRecord.getOperationName())) {
								optCareRecord.setOperationName(optCareRecord.getOperationName().substring(0, optCareRecord.getOperationName().length() - 1));
							}

						}
						optCareRecord.setOperationNameList(operationNameList);
					} else {
						optCareRecord.setOperationNameList(BasRegOptUtils.getOperDefList(optCareRecord.getOperationCode()));
					}
					
					//麻醉方法
					List<String> anaesMethodList = new ArrayList<String>();
					if (null == optCareRecord.getAnaesMethodCode())
					{
                        List<EvtRealAnaesMethod> realAnaesMethodList = evtRealAnaesMethodDao.searchRealAnaesMethodList(searchBean);
                        if (realAnaesMethodList != null && realAnaesMethodList.size() > 0)
                        {
                            for (int i = 0; i < realAnaesMethodList.size(); i++)
                            {
                                anaesMethodList.add(realAnaesMethodList.get(i).getAnaMedId());
                            }
                        }
                        optCareRecord.setAnaesMethodList(anaesMethodList);
                        optCareRecord.setAnaesMethodCode(StringUtils.getStringByList(anaesMethodList));
					}
					else
					{
					    optCareRecord.setAnaesMethodList(StringUtils.getListByString(optCareRecord.getAnaesMethodCode()));
					}

					// 交班巡回护士
					if (null == optCareRecord.getShiftChangedNurse() || "1".equals(map.get("type"))) {
						searchBean.setRole(EvtParticipantService.ROLE_NURSE);
						searchBean.setType(EvtParticipantService.OPER_TYPE_TOUR);
						List<UserInfoFormBean> tourNurseList = evtParticipantDao.getSelectParticipantList(searchBean, getBeid());

						if (tourNurseList != null && tourNurseList.size() > 0) {
							optCareRecord.setShiftChangedNurse("");
							for (UserInfoFormBean userInfoFormBean : tourNurseList) {
								shiftChangedNurseList.add(userInfoFormBean.getId());
							}
							optCareRecord.setShiftChangedNurse(StringUtils.getStringByList(shiftChangedNurseList));
						}
					} else if (StringUtils.isNotBlank(optCareRecord.getShiftChangedNurse())) {
						String[] arys = optCareRecord.getShiftChangedNurse().split(",");

						for (int i = 0; i < arys.length; i++) {
							shiftChangedNurseList.add(arys[i]);
						}
					}

					// 交班洗手护士
					if (null == optCareRecord.getInstrnurseId() || "1".equals(map.get("type"))) {
						searchBean.setRole(EvtParticipantService.ROLE_NURSE);
						searchBean.setType(EvtParticipantService.OPER_TYPE_TOUR);
						List<UserInfoFormBean> instruNurseList = evtParticipantDao.getSelectParticipantList(searchBean, getBeid());
						if (instruNurseList != null && instruNurseList.size() > 0) {
							optCareRecord.setInstrnurseId("");
							for (UserInfoFormBean userInfoFormBean : instruNurseList) {
								instrnurseList.add(userInfoFormBean.getId());
							}
							optCareRecord.setInstrnurseId(StringUtils.getStringByList(instrnurseList));
						}
					} else if (!"".equals(optCareRecord.getInstrnurseId())) {
						String[] arys = optCareRecord.getInstrnurseId().split(",");
						for (int i = 0; i < arys.length; i++) {
							instrnurseList.add(arys[i]);
						}
					}
				}
			} else {
				// 局麻时从基本信息表中获取手术名称
				if (null == optCareRecord.getOperationCode()) {
					optCareRecord.setOperationNameList(BasRegOptUtils.getOperDefList(searchRegOptByIdFormBean.getDesignedOptCode()));
					optCareRecord.setOperationCode(searchRegOptByIdFormBean.getDesignedOptCode());
					optCareRecord.setOperationName(searchRegOptByIdFormBean.getDesignedOptName());
				} else {
					optCareRecord.setOperationNameList(BasRegOptUtils.getOperDefList(optCareRecord.getOperationCode()));
				}

                if (null == optCareRecord.getAnaesMethodCode())
                {
                    optCareRecord.setAnaesMethodCode(searchRegOptByIdFormBean.getDesignedAnaesMethodCode());
                    optCareRecord.setAnaesMethodName(searchRegOptByIdFormBean.getDesignedAnaesMethodName());
                    optCareRecord.setAnaesMethodList(StringUtils.getListByString(searchRegOptByIdFormBean.getDesignedAnaesMethodCode()));
                }
                else
                {
                    optCareRecord.setAnaesMethodList(StringUtils.getListByString(optCareRecord.getAnaesMethodCode()));
                }
				
				
				DispatchFormbean dispatchFormbean = basDispatchDao.getDispatchOperByRegOptId(regOptId, getBeid());
				// 交班巡回护士
				if (null == optCareRecord.getShiftChangedNurse() && null != dispatchFormbean) {
					if (null != dispatchFormbean.getCircunurseId1()) {
						shiftChangedNurseList.add(dispatchFormbean.getCircunurseId1());
					}
					if (null != dispatchFormbean.getCircunurseId2()) {
						shiftChangedNurseList.add(dispatchFormbean.getCircunurseId2());
					}
				} else if (!"".equals(optCareRecord.getShiftChangedNurse())) {
					String[] arys = optCareRecord.getShiftChangedNurse().split(",");

					for (int i = 0; i < arys.length; i++) {
						shiftChangedNurseList.add(arys[i]);
					}
				}

				// 交班洗手护士
				if (null == optCareRecord.getInstrnurseId() && null != dispatchFormbean) {
					if (null != dispatchFormbean.getInstrnurseId1()) {
						instrnurseList.add(dispatchFormbean.getInstrnurseId1());
					}
					if (null != dispatchFormbean.getInstrnurseId2()) {
						instrnurseList.add(dispatchFormbean.getInstrnurseId2());
					}
				} else if (!"".equals(optCareRecord.getInstrnurseId())) {
					String[] arys = optCareRecord.getInstrnurseId().split(",");

					for (int i = 0; i < arys.length; i++) {
						instrnurseList.add(arys[i]);
					}
				}

				if (null == optCareRecord.getOptbody() && null != dispatchFormbean) {
					optCareRecord.setOptbody(dispatchFormbean.getOptBody());
					String optbody = optCareRecord.getOptbody();
//					List<String> optbodys = new ArrayList<String>();
//					String[] obodys = optbody.split(",");
//					for (String id : obodys) {
//						optbodys.add(id);
//					}
					optCareRecord.setOptbodys(StringUtils.getListByString(optbody));
				}
				if (StringUtils.isNotBlank(optCareRecord.getOptbody())) {
					String optbody = optCareRecord.getOptbody();
					/*List<String> optbodys = new ArrayList<String>();
					String[] obodys = optbody.split(",");
					for (String id : obodys) {
						optbodys.add(id);
					}*/
					optCareRecord.setOptbodys(StringUtils.getListByString(optbody));
				}
				// 局麻手术第一次进入手术室时，需要将手术状态改为术中
				if (null == optCareRecord.getInOperRoomTime()) {
					controllerDao.checkOperation(searchRegOptByIdFormBean.getRegOptId(), OperationState.SURGERY, searchRegOptByIdFormBean.getState());
					optCareRecord.setInOperRoomTime(DateUtils.formatDateTime(new Date()));
					docOptCareRecordDao.updateByPrimaryKey(optCareRecord);

					// 将消息推送到手术室大屏
					BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
					String bedStr = StringUtils.isNotBlank(regOpt.getBed()) == true ? regOpt.getBed() + "床的" : "";
					WebSocketHandler.sentMessageToAllUser(regOpt.getDeptName() + regOpt.getRegionName() + bedStr + regOpt.getName() + "开始手术");
				}
			}

			// 接班护士
			if (StringUtils.isNotBlank(optCareRecord.getShiftChangeNurse())) {
				String[] shiftChangeNurseAry = optCareRecord.getShiftChangeNurse().split(",");
				if (null != shiftChangeNurseAry && shiftChangeNurseAry.length > 0) {
					for (int i = 0; i < shiftChangeNurseAry.length; i++) {
						shiftChangeNurseList.add(shiftChangeNurseAry[i]);
					}
				}
			}
			optCareRecord.setShiftChangeNurseList(shiftChangeNurseList);
			optCareRecord.setShiftChangedNurseList(shiftChangedNurseList);
			optCareRecord.setInstrnurseList(instrnurseList);
			optCareRecord.setDrainageTube2(optCareRecord.getDrainageTube());

			// 神志列表
			List<SysCodeFormbean> sensesList = basDictItemDao.searchSysCodeByGroupId("senses", getBeid());
			// 管道列表
			List<SysCodeFormbean> pipelineList = basDictItemDao.searchSysCodeByGroupId("pipeline", getBeid());

			// 数据同步直接将数据保存到数据库
			if ("1".equals(map.get("type"))) {
				docOptCareRecordDao.updateByPrimaryKeySelective(optCareRecord);
			}

			resp.put("sensesList", sensesList);
			resp.put("pipelineList", pipelineList);
			resp.put("result", "true");
			resp.put("resultCode", "1");
			resp.put("resultMessage", "查询成功!");
			resp.put("optCareRecord", optCareRecord);
			resp.put("searchRegOptByIdFormBean", searchRegOptByIdFormBean);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(Exceptions.getStackTraceAsString(e));
			}
			resp.put("resultCode", "10000000");
			resp.put("resultMessage", "系统错误，请与系统管理员联系!");
			return resp;
		}
		return resp;
	}

	/**
	 * 
	 * @discription 保存手术护理
	 * @author chengwang
	 * @created 2015-10-20 下午1:44:18
	 * @param preVisit
	 * @return
	 */
	@Transactional
	public ResponseValue updateOptCareRecord(OptCareRecordFormBean optCareRecordFormBean) {
		ResponseValue resp = new ResponseValue();
		Controller controller = controllerDao.getControllerById(optCareRecordFormBean.getRegOptId() != null ? optCareRecordFormBean.getRegOptId() : "");
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(optCareRecordFormBean.getRegOptId() != null ? optCareRecordFormBean.getRegOptId() : "");
		if (controller == null) {
			resp.setResultCode("70000001");
			resp.setResultMessage("手术控制信息不存在!");
			return resp;
		}

		DocOptCareRecord optCareRecord = new DocOptCareRecord();
		BeanUtils.copyProperties(optCareRecordFormBean, optCareRecord, new String[] { "skin1", "negativePosition", "tourniquet", "supportMaterial", "implants", "venousInfusion2", "drainageTube", "skin2" });// 除json串的不需要传递之外，其他都传递

		optCareRecord.setSkin1(JsonType.jsonType(optCareRecordFormBean.getSkin1()));
		optCareRecord.setNegativePosition(JsonType.jsonType(optCareRecordFormBean.getNegativePosition()));
		optCareRecord.setTourniquet(JsonType.jsonType(optCareRecordFormBean.getTourniquet()));
		optCareRecord.setSupportMaterial(JsonType.jsonType(optCareRecordFormBean.getSupportMaterial()));
		optCareRecord.setImplants(JsonType.jsonType(optCareRecordFormBean.getImplants()));
		//optCareRecord.setLeaveTo(JsonType.jsonType(optCareRecordFormBean.getLeaveTo()));
		optCareRecord.setVenousInfusion2(JsonType.jsonType(optCareRecordFormBean.getVenousInfusion2()));
		if (null == optCareRecordFormBean.getDrainageTube())
		{
		    optCareRecord.setDrainageTube(optCareRecordFormBean.getDrainageTube2());
		}
		else
		{
		    optCareRecord.setDrainageTube(JsonType.jsonType(optCareRecordFormBean.getDrainageTube()));
		}
		optCareRecord.setSkin2(JsonType.jsonType(optCareRecordFormBean.getSkin2()));
		optCareRecord.setAnaesMethodCode(StringUtils.getStringByList(optCareRecord.getAnaesMethodList()));

		// 手术体位
		optCareRecord.setOptbody(StringUtils.getStringByList(optCareRecordFormBean.getOptbodys()));
		
        List<String> shiftChangedNurseList = optCareRecordFormBean.getShiftChangedNurseList();
        optCareRecord.setShiftChangedNurse(StringUtils.getStringByList(shiftChangedNurseList));

        List<String> instrnurseList = optCareRecordFormBean.getInstrnurseList();
        optCareRecord.setInstrnurseId(StringUtils.getStringByList(instrnurseList));

        List<String> shiftChangeNurseList = optCareRecordFormBean.getShiftChangeNurseList();
        optCareRecord.setShiftChangeNurse(StringUtils.getStringByList(shiftChangeNurseList));

		getOperatiomName(optCareRecord);

		// 局麻手术时，提交护理单需要将手术状态改为术后
		if ("1".equals(controller.getIsLocalAnaes()) && "END".equals(optCareRecordFormBean.getProcessState())) {
			controllerDao.checkOperation(optCareRecord.getRegOptId(), OperationState.POSTOPERATIVE, controller.getState());

			String leaveTo = "";
			// 将消息推送到手术室大屏
			if (null != optCareRecordFormBean.getLeaveTo()) {
				if ("1".equals(optCareRecordFormBean.getLeaveTo())) {
					leaveTo = "病室";
				}

				if ("2".equals(optCareRecordFormBean.getLeaveTo())) {
					leaveTo = "ICU";
				}

				if ("3".equals(optCareRecordFormBean.getLeaveTo())) {
					leaveTo = "复苏室";
				}

				if ("4".equals(optCareRecordFormBean.getLeaveTo())) {
					leaveTo = optCareRecordFormBean.getLeaveToOther();
				}
			}
			WebSocketHandler.sentMessageToAllUser(regOpt.getDeptName() + regOpt.getRegionName() + regOpt.getBed() + regOpt.getName() + "手术已结束,去往" + leaveTo);

			// 获取麻醉记录单信息，局麻时将手术开始时间和结束时间写入到麻醉记录单中
			DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(optCareRecord.getRegOptId());
			anaesRecord.setOperStartTime(optCareRecordFormBean.getInOperRoomTime());
			anaesRecord.setInOperRoomTime(optCareRecordFormBean.getInOperRoomTime());
			anaesRecord.setOperEndTime(optCareRecordFormBean.getOutOperRoomTime());
			anaesRecord.setOutOperRoomTime(optCareRecordFormBean.getOutOperRoomTime());
			anaesRecord.setOptBody(optCareRecordFormBean.getOptbody());
			docAnaesRecordDao.updateByPrimaryKey(anaesRecord);
		}

		try {
			docOptCareRecordDao.updateByPrimaryKeySelective(optCareRecord);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(Exceptions.getStackTraceAsString(e));
			}
			resp.setResultCode("10000000");
			resp.setResultMessage("系统错误，请与系统管理员联系!");
			return resp;
		}
		resp.setResultCode("1");
		resp.setResultMessage("护理记录单修改成功!");
		return resp;
	}

	private void getOperatiomName(DocOptCareRecord optCareRecord) {
	    List<DesignedOptCodes> designedOptList = optCareRecord.getOperationNameList();
	    String designedOptCode = "";
	    String designedOptName = "";
			
        if (null != designedOptList && designedOptList.size() > 0)
        {
            for (DesignedOptCodes operDef : designedOptList)
            {
                if (StringUtils.isBlank(operDef.getOperDefId()))
                {
                    BasOperdef operdef = new BasOperdef();
                    String operdefId = GenerateSequenceUtil.generateSequenceNo(GenerateSequenceUtil.getRoomId(optCareRecord.getRegOptId()));
                    operdef.setOperdefId(operdefId);
                    operdef.setName(operDef.getName());
                    operdef.setPinYin(PingYinUtil.getFirstSpell(operDef.getName()));
                    operdef.setEnable(1);
                    operdef.setBeid(getBeid());
                    basOperdefDao.insert(operdef);
                    if (StringUtils.isBlank(designedOptCode))
                    {
                        designedOptCode = operdefId;
                    }
                    else
                    {
                        designedOptCode = designedOptCode + "," + operdefId;
                    }
                }
                else
                {
                    if (StringUtils.isBlank(designedOptCode))
                    {
                        designedOptCode = operDef.getOperDefId();
                    }
                    else
                    {
                        designedOptCode = designedOptCode + "," + operDef.getOperDefId();
                    }
                }
                

                if (StringUtils.isBlank(designedOptName))
                {
                    designedOptName = operDef.getName();
                }
                else
                {
                    designedOptName = designedOptName + "," + operDef.getName();
                }
            }
        }
        optCareRecord.setOperationCode(designedOptCode);	
        optCareRecord.setOperationName(designedOptName);
	}
	
	public DocOptCareRecord selectByRegOptId(String regOptId) {
        return docOptCareRecordDao.selectByRegOptId(regOptId);
    }

}
