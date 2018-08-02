package com.digihealth.anesthesia.basedata.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.dao.BasBusEntityDao;
import com.digihealth.anesthesia.basedata.dao.BasRegOptDao;
import com.digihealth.anesthesia.basedata.formbean.AnaesMethodFormBean;
import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.DeptFormBean;
import com.digihealth.anesthesia.basedata.formbean.DesignedOptCodes;
import com.digihealth.anesthesia.basedata.formbean.DiagnosedefFormBean;
import com.digihealth.anesthesia.basedata.formbean.DiagnosisCodes;
import com.digihealth.anesthesia.basedata.formbean.DocStateArrayFormbean;
import com.digihealth.anesthesia.basedata.formbean.DocumentStateFormbean;
import com.digihealth.anesthesia.basedata.formbean.OneRegOptDocumentStateFormbean;
import com.digihealth.anesthesia.basedata.formbean.OperDefFormBean;
import com.digihealth.anesthesia.basedata.formbean.OperPeopleFormBean;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasDept;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasDocument;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.BasRegion;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.basedata.utils.BasRegOptUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.HttpUtils;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.SpringContextHolder;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.utils.SysUtil;
import com.digihealth.anesthesia.doc.formbean.AnaesPacuRecFormBean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.evt.formbean.CancleRegOptFormBean;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.evt.formbean.SearchConditionFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchMyOperationFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchOperaPatrolRecordFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByIdFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByLoginNameAndStateFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchRegOptByRoomIdAndOperDateAndStateFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchSafeCheckRegOptFormBean;
import com.digihealth.anesthesia.evt.po.EvtAnaesEvent;
import com.digihealth.anesthesia.evt.po.EvtParticipant;
import com.digihealth.anesthesia.evt.po.EvtRescueevent;
import com.digihealth.anesthesia.evt.po.EvtShiftChange;
import com.digihealth.anesthesia.evt.service.EvtAnaesEventService;
import com.digihealth.anesthesia.evt.service.EvtParticipantService;
import com.digihealth.anesthesia.interfacedata.service.HisInterfaceServiceQNZZY;
import com.digihealth.anesthesia.interfacedata.service.HisInterfaceServiceYXRM;
import com.digihealth.anesthesia.operProceed.core.MyConstants;
import com.digihealth.anesthesia.operProceed.datasync.MessageProcess;
import com.digihealth.anesthesia.operProceed.formbean.CentralBigScreenDataFormbean;
import com.digihealth.anesthesia.operProceed.formbean.CmdMsg;
import com.digihealth.anesthesia.operProceed.formbean.RegOptFormBean;
import com.digihealth.anesthesia.operProceed.formbean.SuspendFormBean;
import com.digihealth.anesthesia.sysMng.dao.BasDictItemDao;
import com.digihealth.anesthesia.sysMng.formbean.BasRoleFormBean;
import com.digihealth.anesthesia.sysMng.po.BasRole;
import com.digihealth.anesthesia.sysMng.po.BasUser;
import com.digihealth.anesthesia.websocket.WebSocketHandler;

@Service
public class BasRegOptService extends BaseService {

	// public static final String ANAESMETHODJM = "局麻";

	public static final Integer ISLOCALANAES_Y = 1; // 局麻
	public static final Integer ISLOCALANAES_N = 0; // 全麻

	public List<SearchMyOperationFormBean> searchNoScheduling(SearchConditionFormBean searchConditionFormBean) {
		searchConditionFormBean.setState("02");
		if (StringUtils.isEmpty(searchConditionFormBean.getSort())) {
			searchConditionFormBean.setSort("operaDate");
		}
		if (StringUtils.isEmpty(searchConditionFormBean.getOrderBy())) {
			searchConditionFormBean.setOrderBy("DESC");
		}
		String beid = null;
		if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
			beid = getBeid();
			searchConditionFormBean.setBeid(beid);
		} else {
			beid = searchConditionFormBean.getBeid();
		}
		// String filter = " AND a.operaDate >='"+DateUtils.getDate()+"'";
		String filter = " ";
		return basRegOptDao.searchMyOperation(filter, null, "", searchConditionFormBean);
	}

	public List<SearchMyOperationFormBean> searchMyOperation(SearchConditionFormBean searchConditionFormBean) {
		searchConditionFormBean.setState("03,04,05,06");
		if (StringUtils.isEmpty(searchConditionFormBean.getSort())) {
			searchConditionFormBean.setSort("operaDate");
		}
		if (StringUtils.isEmpty(searchConditionFormBean.getOrderBy())) {
			searchConditionFormBean.setOrderBy("DESC");
		}
		String beid = null;
		if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
			beid = getBeid();
			searchConditionFormBean.setBeid(beid);
		} else {
			beid = searchConditionFormBean.getBeid();
		}

		String filter = " AND a.operaDate >='" + DateUtils.getDate() + "'";

		BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", beid);

		return basRegOptDao.searchMyOperation(filter, user == null ? "" : user.getUserName(), user == null ? "" : user.getRoleType(), searchConditionFormBean);
	}

	public List<SearchMyOperationFormBean> searchTodayOperation(SearchConditionFormBean searchConditionFormBean) {
		searchConditionFormBean.setState("03,04,05,06");
		if (StringUtils.isEmpty(searchConditionFormBean.getSort())) {
			searchConditionFormBean.setSort("operaDate");
		}
		if (StringUtils.isEmpty(searchConditionFormBean.getOrderBy())) {
			searchConditionFormBean.setOrderBy("DESC");
		}
		String beid = null;
		if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
			beid = getBeid();
			searchConditionFormBean.setBeid(beid);
		} else {
			beid = searchConditionFormBean.getBeid();
		}
		String filter = " AND a.operaDate ='" + DateUtils.getDate() + "'";

		BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", beid);

		return basRegOptDao.searchMyOperation(filter, "", user == null ? "" : user.getRoleType(), searchConditionFormBean);
	}

	/**
	 * 
	 * @discription 根据登录账号和手术状态获取人员列表信息
	 * @author chengwang
	 * @created 2015-10-9 上午10:29:34
	 * @param loginName
	 * @param statu
	 * @return
	 */
	public List<SearchRegOptByLoginNameAndStateFormBean> searchRegOptByAnaesDoctorAndState(SearchConditionFormBean searchConditionFormBean) {
		String state = searchConditionFormBean.getState();
		if (state != null) {
			searchConditionFormBean.setState(searchConditionFormBean.getState().replace(" ", ""));
		}
		if (StringUtils.isBlank(searchConditionFormBean.getSort())) {
			searchConditionFormBean.setSort("operaDate");
		}
		if (StringUtils.isBlank(searchConditionFormBean.getOrderBy())) {
			searchConditionFormBean.setOrderBy("DESC");
		}
		String beid = null;
		if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
			beid = getBeid();
			searchConditionFormBean.setBeid(beid);
		} else {
			beid = searchConditionFormBean.getBeid();
		}
		String filter = getFilterStr(searchConditionFormBean);
		BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", beid);
		List<SearchRegOptByLoginNameAndStateFormBean> resultList = new ArrayList<SearchRegOptByLoginNameAndStateFormBean>();
		List<SearchRegOptByLoginNameAndStateFormBean> result = new ArrayList<SearchRegOptByLoginNameAndStateFormBean>();

		if(StringUtils.isNotBlank(searchConditionFormBean.getQueryMethod())){
            result = basRegOptDao.searchAllOptList(
                    filter,searchConditionFormBean);
        }else{
            result = basRegOptDao.searchRegOptByAnaesDoctorAndState(filter, user == null ? "" : user.getUserName(), user == null ? "" : user.getRoleType(), searchConditionFormBean, beid);
        }
		
		
		if (result != null && result.size() > 0) {

			String regOptStr = "";
			for (int i = 0; i < result.size(); i++) {
				regOptStr += "'" + result.get(i).getRegOptId() + "',";
			}
			regOptStr = regOptStr.substring(0, regOptStr.length() - 1);
			List<DocStateArrayFormbean> arrList = getDocumentState(user, searchConditionFormBean.getState(), regOptStr, beid);
			for (int i = 0; i < result.size(); i++) {
				SearchRegOptByLoginNameAndStateFormBean bean = result.get(i);
				String documentState = "完成";

				List<DocumentStateFormbean> stateFormbeanList = new ArrayList<DocumentStateFormbean>();
				for (DocStateArrayFormbean docStateArrayFormbean : arrList) {
					DocumentStateFormbean stateFormbean = new DocumentStateFormbean();
					List<DocumentStateFormbean> statList = docStateArrayFormbean.getDocStateList();
					for (DocumentStateFormbean documentStateFormbean : statList) {
						if (bean.getRegOptId().equals(documentStateFormbean.getRegOptId())) { // 只把未完成的存入的stateFormbean中
							if (!"END".equals(documentStateFormbean.getState())) {
								if (1 == docStateArrayFormbean.getIsNeed()) { // 1
																				// 必须完成
									documentState = "未完成";
									stateFormbean.setState("未完成");
									stateFormbean.setName(docStateArrayFormbean.getDocName());
									stateFormbeanList.add(stateFormbean);
									break;
								}
							} /*
							 * else { stateFormbean.setState("完成"); }
							 */

						}
					}
				}
				bean.setDocumentState(documentState);
				bean.setDocumentStateList(stateFormbeanList);
				resultList.add(bean);
			}

		}

		return resultList;
	}

	public int searchRegOptTotalByAnaesDoctorAndState(SearchConditionFormBean searchConditionFormBean) {
		if (StringUtils.isEmpty(searchConditionFormBean.getSort())) {
			searchConditionFormBean.setSort("operaDate");
		}
		if (StringUtils.isEmpty(searchConditionFormBean.getOrderBy())) {
			searchConditionFormBean.setOrderBy("DESC");
		}
		String beid = null;
		if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
			beid = getBeid();
			searchConditionFormBean.setBeid(beid);
		} else {
			beid = searchConditionFormBean.getBeid();
		}
		String filter = getFilterStr(searchConditionFormBean);
		BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", beid);
		int result = 0;
		if(StringUtils.isNotBlank(searchConditionFormBean.getQueryMethod())){
            result = basRegOptDao.searchTotalAllOptList(
                    filter,searchConditionFormBean);
        }else{
            result = basRegOptDao.searchRegoptTotalByAnaesDoctorAndState(filter, user == null ? "" : user.getUserName(), user == null ? "" : user.getRoleType(), searchConditionFormBean, beid);
        }
		return result;
	}

	/**
	 * 
	 * @discription 获取单个手术室即将进行手术的病人列表
	 * @author chengwang
	 * @created 2015-10-10 上午9:43:03
	 * @param roomId
	 * @param operDate
	 * @param state
	 * @return
	 */
	public List<SearchRegOptByRoomIdAndOperDateAndStateFormBean> searchRegOptByRoomIdAndOperDateAndState(String operDate, List<String> state) {
		return basRegOptDao.searchRegOptByRoomIdAndOperDateAndState(getCurRoomId(null), operDate, state, getBeid());
	}

	/**
	 * 获取当前手术下当日的手术
	 * 
	 * @param roomId
	 * @param operDate
	 * @return
	 */
	public List<SearchRegOptByRoomIdAndOperDateAndStateFormBean> searchDayRegOpt(String operDate,SystemSearchFormBean searchFormBean) {
		List<Filter> filters = searchFormBean.getFilters();
		if(filters.size()==0){
			filters = new ArrayList<Filter>();
		}
		return basRegOptDao.searchDayRegOpt(getCurRoomId(null), operDate, getBeid(),filters,searchFormBean);
	}
	
	public int searchDayRegOptTotal(String operDate,SystemSearchFormBean searchFormBean) {
		List<Filter> filters = searchFormBean.getFilters();
		if(filters.size()==0){
			filters = new ArrayList<Filter>();
		}
		List<SearchRegOptByRoomIdAndOperDateAndStateFormBean> list = basRegOptDao.searchDayRegOpt(getCurRoomId(null), operDate, getBeid(),filters,new SystemSearchFormBean());
		int total = 0;
		if(null != list){
			total = list.size(); 
		}
		return total;
	}
	

	/**
	 * 获取当前麻醉医生或巡回护士1的下面的手术
	 * 
	 * @param roomId
	 * @param operDate
	 * @param loginName
	 * @return
	 */
	public List<SearchRegOptByRoomIdAndOperDateAndStateFormBean> searchCurUserRegOpt(String operDate, String userId) {
		return basRegOptDao.searchCurUserRegOpt(getCurRoomId(null), operDate, userId, getBeid());
	}

	public AnaesPacuRecFormBean getPostopOptInfo(String regOptId) {
		return basRegOptDao.getPostopOptInfo(regOptId, getBeid());
	}

	public List<SearchRegOptByRoomIdAndOperDateAndStateFormBean> queryAllRegOpt(RegOptFormBean entity) {
		return basRegOptDao.queryAllRegOpt(entity, getBeid());
	}

	/**
	 * 
	 * @discription 根据ID获取regOpt
	 * @author chengwang
	 * @created 2015-10-10 下午5:47:31
	 * @param id
	 * @return
	 */
	public BasRegOpt searchRegOptById(String id) {
		BasRegOpt basRegOpt = basRegOptDao.searchRegOptById(id);
		if (basRegOpt != null) {
			BasDept basDept = new BasDept();
			basDept.setDeptId(String.valueOf(basRegOpt.getDeptId()));
			basDept.setName(basRegOpt.getDeptName());
			basRegOpt.setBasDept(basDept);

			BasRegion basRegion = new BasRegion();
			basRegion.setRegionId(String.valueOf(basRegOpt.getRegionId()));
			basRegion.setName(basRegOpt.getRegionName());
			basRegOpt.setBasRegion(basRegion);
			// 助手医生
			String assistantId = basRegOpt.getAssistantId();
			if (StringUtils.isNoneBlank(assistantId)) {
				List<String> codes = new ArrayList<String>();
				String[] assistantIds = assistantId.split(",");
				for (String code : assistantIds) {
					codes.add(code);
				}
				basRegOpt.setAssistants(codes);
			} else {
				basRegOpt.setAssistants(new ArrayList<String>());
			}
			// 麻醉方法
			String anaesMethodCode = basRegOpt.getDesignedAnaesMethodCode();
			if (StringUtils.isNoneBlank(anaesMethodCode)) {
				List<String> codes = new ArrayList<String>();
				String[] anaesMethodCodes = anaesMethodCode.split(",");
				for (String code : anaesMethodCodes) {
					codes.add(code);
				}
				basRegOpt.setDesignedAnaesMethodCodes(codes);
			} else {
				basRegOpt.setDesignedAnaesMethodCodes(new ArrayList<String>());
			}
			// 拟施手术
			List<DesignedOptCodes> designedOptCodeList = BasRegOptUtils.getOperDefList(basRegOpt.getDesignedOptCode());
			basRegOpt.setDesignedOptCodes(designedOptCodeList);

			// 拟施诊断
			List<DiagnosisCodes> diagnosisCodesList = BasRegOptUtils.getDiagnosisList(basRegOpt.getDiagnosisCode());
			basRegOpt.setDiagnosisCodes(diagnosisCodesList);

		}
		return basRegOpt;
	}

	/**
	 * 
	 * @discription 根据ID获取手术申请列表病人详细信息
	 * @author chengwang
	 * @created 2015-10-19 下午3:42:41
	 * @param id
	 * @return
	 */
	public SearchRegOptByIdFormBean searchApplicationById(String id) {
	    SearchRegOptByIdFormBean searchRegOptByIdFormBean = basRegOptDao.searchApplicationById(id, getBeid());
        
        DocAnaesRecord anaRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(id);
        SearchFormBean searchBean = new SearchFormBean();
        searchBean.setDocId(anaRecord.getAnaRecordId());
        List<EvtShiftChange> shiftChangeList = evtShiftChangeDao.getAllShiftChangeList(searchBean);
        if (null != shiftChangeList && shiftChangeList.size() > 0)
        {
            EvtShiftChange shiftChange = shiftChangeList.get(shiftChangeList.size() - 1);
            searchRegOptByIdFormBean.setAnesthetistId(shiftChange.getShiftChangePeopleId());
            searchRegOptByIdFormBean.setAnesthetistName(shiftChange.getShiftChangePeople());
        }
        
        return searchRegOptByIdFormBean;
	}

	/**
	 * 
	 * @discription 手术核查需要的基本信息
	 * @author chengwang
	 * @created 2015年10月28日 上午11:00:19
	 * @param id
	 * @return
	 */
	public SearchSafeCheckRegOptFormBean searchSafeCheckRegOptById(String id) {
		return basRegOptDao.searchSafeCheckRegOptById(id, getBeid());
	}

	/**
	 * 
	 * @discription 创建一条新手术信息
	 * @author chengwang
	 * @created 2015年10月30日 上午9:18:17
	 * @param regOpt
	 * @return
	 */
	@Transactional
	public String insertRegOpt(BasRegOpt regOpt) {
		String beid = regOpt.getBeid();
		if (StringUtils.isBlank(beid)) {
			beid = getBeid();
			regOpt.setBeid(beid);
		}

		// 处理拟施手术、拟施诊断、麻醉方法等字段的值。
		BasRegOptUtils.getOtherInfo(regOpt);
		BasRegOptUtils.IsLocalAnaesSet(regOpt);

		BasDept basDept = basDeptDao.searchDeptById(regOpt.getDeptId(), beid);
		if (basDept != null) {
			regOpt.setDeptName(basDept.getName());
		}
		BasRegion basRegion = basRegionDao.searchRegionById(regOpt.getRegionId(), beid);
		if (basRegion != null) {
			regOpt.setRegionName(basRegion.getName());
		}
		BasUser user = UserUtils.getUserCache();
		String regOptId = GenerateSequenceUtil.generateSequenceNo();
		regOpt.setRegOptId(regOptId);
		regOpt.setCostsettlementState("0");
		regOpt.setState(OperationState.NOT_REVIEWED);
		if (user != null) {
			regOpt.setCreateUser(user.getUserName());
		}
		regOpt.setCreateTime(DateUtils.formatDateTime(new Date()));
		regOpt.setBeid(beid);

		basRegOptDao.insertSelective(regOpt);
		
        int dispatchCount = basDispatchDao.searchDistchByRegOptId(regOpt.getRegOptId());
        if(dispatchCount<1){
            BasDispatch dispatch = new BasDispatch();
            dispatch.setRegOptId(regOpt.getRegOptId());
            dispatch.setBeid(getBeid());
            basDispatchDao.insert(dispatch);
        }
        
		if ("1".equals(regOpt.getGetData())) {
			return JsonType.jsonType(searchRegOptById(regOptId));
		}
		return "true";
	}

	/**
     * 
     * @discription 创建一条新手术信息
     * @author chengwang
     * @created 2015年10月30日 上午9:18:17
     * @param regOpt
     * @return
     */
    @Transactional
    public String insertRegOptSYZXYY(BasRegOpt regOpt) {
        String beid = regOpt.getBeid();
        if (StringUtils.isBlank(beid)) {
            beid = getBeid();
            regOpt.setBeid(beid);
        }

        // 处理拟施手术、拟施诊断、麻醉方法等字段的值。
        BasRegOptUtils.getOtherInfo(regOpt);
        BasRegOptUtils.IsLocalAnaesSet(regOpt);

        BasDept basDept = basDeptDao.searchDeptById(regOpt.getDeptId(), beid);
        if (basDept != null) {
            regOpt.setDeptName(basDept.getName());
        }
        BasRegion basRegion = basRegionDao.searchRegionById(regOpt.getRegionId(), beid);
        if (basRegion != null) {
            regOpt.setRegionName(basRegion.getName());
        }
        BasUser user = UserUtils.getUserCache();
        String regOptId = GenerateSequenceUtil.generateSequenceNo();
        regOpt.setRegOptId(regOptId);
        regOpt.setCostsettlementState("0");
        
        //邵阳中心医院去掉批准手术的步骤
        //regOpt.setState(OperationState.NOT_REVIEWED);
        regOpt.setState(OperationState.NO_SCHEDULING);
        if (user != null) {
            regOpt.setCreateUser(user.getUserName());
        }
        regOpt.setCreateTime(DateUtils.formatDateTime(new Date()));
        regOpt.setBeid(beid);

        basRegOptDao.insertSelective(regOpt);
        creatDocument(regOpt);
        
        int dispatchCount = basDispatchDao.searchDistchByRegOptId(regOpt.getRegOptId());
        if(dispatchCount<1){
            BasDispatch dispatch = new BasDispatch();
            dispatch.setRegOptId(regOpt.getRegOptId());
            dispatch.setBeid(getBeid());
            basDispatchDao.insert(dispatch);
        }
        
        if ("1".equals(regOpt.getGetData())) {
            return JsonType.jsonType(searchRegOptById(regOptId));
        }
        return "true";
    }
	
	@Transactional
	public void batchCreateRegOpt(int count) {
		BaseInfoQuery query = new BaseInfoQuery();
		List<DiagnosedefFormBean> diagList = basDiagnosedefDao.findList(query);
		List<BasRegion> regList = basRegionDao.findList(query);
		List<DeptFormBean> deptList = basDeptDao.findList(query);
		List<OperDefFormBean> operdefList = basOperdefDao.findList(query);
		List<AnaesMethodFormBean> anaesMethodList = basAnaesMethodDao.findList(query);
		List<OperPeopleFormBean> operList = basOperationPeopleDao.findList(query);
		for (int i = 1; i <= count; i++) {
			String regOptId = GenerateSequenceUtil.generateSequenceNo();
			BasRegOpt regOpt = new BasRegOpt();
			regOpt.setIsLocalAnaes(0); // 默认全麻
			regOpt.setRegOptId(regOptId);
			regOpt.setCreateTime(DateUtils.formatDateTime(new Date()));
			Random r = new Random();
			String[] firstName = { "张", "李", "刘", "曹", "王", "陈", "邹", "许" };
			String[] secedName = { "杨", "志", "宇", "勇", "鑫", "泽", "翔", "茹", "诚" };
			regOpt.setName(firstName[r.nextInt(firstName.length)] + secedName[r.nextInt(secedName.length)]);
			regOpt.setAge(r.nextInt(100) == 0 ? 1 : r.nextInt(100));
			String sex = "男";
			String medicalType = "自费";
			if (i % 2 == 0) {
				sex = "女";
				medicalType = "医保";
			}
			regOpt.setSex(sex);
			regOpt.setHid("100010" + i + "" + r.nextInt(10));
			BasRegion region = regList.get(r.nextInt(regList.size() - 1));
			regOpt.setRegionId(region.getRegionId());
			regOpt.setRegionName(region.getName());
			DeptFormBean dept = deptList.get(r.nextInt(deptList.size() - 1));
			regOpt.setDeptId(dept.getDeptId());
			regOpt.setDeptName(dept.getName());
			regOpt.setBed(i + "" + r.nextInt(9));
			regOpt.setMedicalType(medicalType);
			OperDefFormBean operdef = operdefList.get(r.nextInt(operdefList.size() - 1));
			regOpt.setDesignedOptCode(operdef.getOperDefId() + "");
			regOpt.setDesignedOptName(operdef.getName());

			DiagnosedefFormBean diagnosedef = diagList.get(r.nextInt(diagList.size() - 1));
			regOpt.setDiagnosisCode(diagnosedef.getDiagDefId() + "");
			regOpt.setDiagnosisName(diagnosedef.getName());
			Date date = new Date();
			regOpt.setOperaDate(DateUtils.formatDate(date, "yyyy-MM-dd"));
			regOpt.setCreateUser("11");
			regOpt.setCreateTime(DateUtils.formatDateTime(date));
			regOpt.setEmergency(0);
			AnaesMethodFormBean anaesMethod = anaesMethodList.get(r.nextInt(anaesMethodList.size() - 1));
			regOpt.setDesignedAnaesMethodCode(anaesMethod.getAnaMedId() + "");
			regOpt.setDesignedAnaesMethodName(anaesMethod.getName());
			OperPeopleFormBean operationPeople = operList.get(r.nextInt(operList.size() - 1));
			regOpt.setOperatorId(operationPeople.getOperatorId() + "");
			regOpt.setOperatorName(operationPeople.getName() + "");
			basRegOptDao.insert(regOpt);
			Controller controller = new Controller();
			controller.setRegOptId(regOptId);
			controller.setCostsettlementState("0");
			controller.setState(OperationState.NOT_REVIEWED);
			controllerDao.update(controller);
		}
	}

	@Transactional
	public void updateRegOptByHis(BasRegOpt regOpt) {
		String beid = regOpt.getBeid();
		if (StringUtils.isBlank(beid)) {
			beid = getBeid();
			regOpt.setBeid(getBeid());
		}
		BasRegOptUtils.getOtherInfo(regOpt);
		BasRegOptUtils.IsLocalAnaesSet(regOpt);
		
		BasDept basDept = basDeptDao.searchDeptById(regOpt.getDeptId(), beid);
        if (basDept != null) {
            regOpt.setDeptName(basDept.getName());
        }
        BasRegion basRegion = basRegionDao.searchRegionById(regOpt.getRegionId(), beid);
        if (basRegion != null) {
            regOpt.setRegionName(basRegion.getName());
        }
		
		basRegOptDao.updateByPrimaryKey(regOpt);
	}
	
	@Transactional
    public void saveRegOptDesigned(BasRegOpt regOpt) {
        basRegOptDao.updateByPrimaryKey(regOpt);
    }
	
	@Transactional
    public String updateRegOptInfo(BasRegOpt regOpt, BasDispatch dispatch,ResponseValue resp) {
	    String beid = getBeid();
	    if (regOpt != null && (!StringUtils.isEmpty(regOpt.getRegOptId()))) {
            if (StringUtils.isBlank(regOpt.getBeid()))
            {
                regOpt.setBeid(beid);
            }
            else
            {
                beid = regOpt.getBeid();
            }
            BasDept basDept = basDeptDao.searchDeptById(regOpt.getDeptId(), beid);
            if (basDept != null) {
                regOpt.setDeptName(basDept.getName());
            }
            BasRegion basRegion = basRegionDao.searchRegionById(regOpt.getRegionId(), beid);
            if (basRegion != null) {
                regOpt.setRegionName(basRegion.getName());
            }
        }
	    
	    /**
		 * 在术中修改手术信息时，需判断是否更换手术室，如更换则需要将原手术数据同步至当前手术室
		 */
		BasRegOpt oldOpt = basRegOptDao.searchRegOptById(regOpt.getRegOptId());
		
		if(null!=oldOpt && OperationState.SURGERY.equals(oldOpt.getState())){
			
			//更换手术室是否需要将原手术室采集服务停止
			BasDispatch oldDispatch = basDispatchDao.getDispatchOper(regOpt.getRegOptId());
			if("0".equals(oldOpt.getIsLocalAnaes()) && !oldDispatch.getOperRoomId().equals(dispatch.getOperRoomId())){
				CmdMsg msg = new CmdMsg();
				msg.setRegOptId(regOpt.getRegOptId());
				msg.setMsgType(MyConstants.OPERATION_STATUS_FORCEEND);
				MessageProcess.process(msg);
			}
			
			
			//如果原手术为局麻手术修改后为全麻或者全麻手术更换手术间时，需要判断修改后的手术室是否存在未完成的手术信息
//			if (("1".equals(oldOpt.getIsLocalAnaes()) && "0".equals(regOpt.getIsLocalAnaes())) ||
//					("0".equals(regOpt.getIsLocalAnaes()) && !oldDispatch.getOperRoomId().equals(dispatch.getOperRoomId()))){
//				BaseInfoQuery baseQuery = new BaseInfoQuery();
//				baseQuery.setOperRoomId(dispatch.getOperRoomId());
//				baseQuery.setState(OperationState.SURGERY);
//				
//				//判断当前手术室是否存在未完成的手术，如果存在则返回错误消息
//				List<SearchOperaPatrolRecordFormBean> operaPatrolList = basRegOptDao.getOperaPatrolRecordListByRoomId(baseQuery);
//				SearchOperaPatrolRecordFormBean po = null;
//				if(operaPatrolList.size()>0){
//					for (SearchOperaPatrolRecordFormBean searchOperaPatrolRecordFormBean : operaPatrolList) {
//						if("0".equals(searchOperaPatrolRecordFormBean.getIsLocalAnaes())){
//							po = searchOperaPatrolRecordFormBean;
//						}
//					}
//					if(null != po && !po.getRegOptId().equals(regOpt.getRegOptId())){
//						resp.setResultCode("40000004");
//						resp.setResultMessage(po.getOperRoomName()+"存在病人信息为："+po.getName()+",手术未完成!");
//						logger.info("-----------存在未完成的全麻手术信息-------end syncOperateInfo------------------------");
//						return resp.getJsonStr();
//					}
//				}
//			}
			//更换手术室后将原手术室采集服务停止
//			if("0".equals(regOpt.getIsLocalAnaes()) && !oldDispatch.getOperRoomId().equals(dispatch.getOperRoomId())){
//				logger.info("syncOperateInfo====原手术室ID："+oldDispatch.getOperRoomId()+"更换后的手术室ID："+dispatch.getOperRoomId());
//		        try {
//	        		String xmlInfo = "{regOptId:'"+regOpt.getRegOptId()+"'}";
//			        String URL = "http://"+getControlCenterIp()+":"+getControlCenterPort()+"/operCtl/stopCollectService.action";
//			        String msg = HttpUtils.doHttpPost(xmlInfo, URL);
//			        logger.info("stopCollectService=="+regOpt.getRegOptId()+"====术中更换手术室停止原采集服务信息为："+msg);
//				} catch (Exception e) {
//					logger.info("stopCollectService=="+regOpt.getRegOptId()+"====术中更换手术室停止原采集服务错误!!!"+Exceptions.getStackTraceAsString(e));
//				}
//			}
			//如果在术中主刀医生修改
			if(StringUtils.isNotBlank(regOpt.getOperatorId()) && !regOpt.getOperatorId().equals(oldOpt.getOperatorId())){
				DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOpt.getRegOptId());
				if(null!=anaesRecord){
					evtParticipantDao.deleteByUserId(anaesRecord.getAnaRecordId(), oldOpt.getOperatorId());
					EvtParticipant participant = new EvtParticipant();
					participant.setDocId(anaesRecord.getAnaRecordId());
					participant.setRole(EvtParticipantService.ROLE_OPERAT);
					participant.setOperatorType("06"); //主刀医生
					participant.setUserLoginName(regOpt.getOperatorId());
					participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
					evtParticipantDao.insert(participant);
				}
			}
			/**
			 * 如果是术中全麻改局麻，需要停掉采集服务
			 */
			if ("0".equals(oldOpt.getIsLocalAnaes())
					&& "1".equals(regOpt.getIsLocalAnaes())) {
				CmdMsg msg = new CmdMsg();
				msg.setRegOptId(regOpt.getRegOptId());
				msg.setMsgType(MyConstants.OPERATION_STATUS_FORCEEND);
				MessageProcess.process(msg);
			}
			/**
			 * 术中局麻改成全麻\全麻改局麻时，修改手术护理记录单的麻醉方法字段
			 */
			if (!oldOpt.getIsLocalAnaes().equals(regOpt.getIsLocalAnaes())) {
				DocOptCareRecord optCareRecord = docOptCareRecordDao.selectByRegOptId(regOpt.getRegOptId());
				if (null != optCareRecord && !regOpt.getDesignedAnaesMethodCode().equals(optCareRecord.getAnaesMethodCode())) {
					optCareRecord.setAnaesMethodCode(regOpt.getDesignedAnaesMethodCode());
					optCareRecord.setAnaesMethodName(regOpt.getDesignedAnaesMethodName());
					docOptCareRecordDao.updateByPrimaryKeySelective(optCareRecord);
				}
			}
			/**
			 * 当全麻，需判断anesDocList是否为空,如果为空则插入排班表的麻醉医生字段
			 */
			if("0".equals(regOpt.getIsLocalAnaes())){
				SearchFormBean searchBean = new SearchFormBean();
				DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOpt.getRegOptId());
				String docId = anaesRecord.getAnaRecordId();
				searchBean.setDocId(docId);
	
				 // 麻醉医生列表
	            searchBean.setRole(EvtParticipantService.ROLE_ANESTH);
	            searchBean.setType("01");
	            EvtParticipant participant = new EvtParticipant();
	            List<EvtParticipant> anesDocList = evtParticipantDao.searchParticipantList(searchBean, beid);
	            if(null != anesDocList && anesDocList.size()>0)
	            {
	                participant = anesDocList.get(0);
	                if (!dispatch.getAnesthetistId().equals(participant.getUserLoginName())) {
	                    participant.setUserLoginName(dispatch.getAnesthetistId());
	                    BasUser user = basUserDao.searchUserById(dispatch.getAnesthetistId(), beid);
	                    participant.setName(user == null ? "" : user.getName());
	                    evtParticipantDao.update(participant);
	                }
	            }
	            else
	            {
	                participant.setUserLoginName(dispatch.getAnesthetistId());
	                BasUser user = basUserDao.searchUserById(dispatch.getAnesthetistId(), beid);
	                participant.setName(user == null ? "" : user.getName());
	                participant.setDocId(docId);
	                participant.setRole(EvtParticipantService.ROLE_ANESTH);
	                participant.setOperatorType("01"); 
	                participant.setIsShiftChange(1);
	                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
	                evtParticipantDao.insert(participant);
	            }	
			}
		}
	    
		basRegOptDao.updateByPrimaryKey(regOpt); 
	    

        if (dispatch != null && (!StringUtils.isEmpty(dispatch.getRegOptId()))) {
            if (StringUtils.isNotEmpty(dispatch.getOperRoomId())) {
                dispatch.setBeid(beid);
                basDispatchDao.update(dispatch);
            }
        }
        
        //从his传过来的急诊手术，审核之后，修改完基本信息，就将状态改为术前
        if (null != regOpt && 1 == regOpt.getEmergency() && "02".equals(regOpt.getState()))
       {
            controllerDao.checkOperation(regOpt.getRegOptId(), "03", regOpt.getState());
            regOpt.setState("03");
        }

        // 如果在术前修改了手术的人员信息，或者是his传过来的急诊手术在未排班的时候修改了手术信息，需要同步将s_participant表中的人员信息同步修改
        if (null != regOpt && 
            ("03".equals(regOpt.getState()) 
                || (1 == regOpt.getEmergency() && "02".equals(regOpt.getState()))) 
            && 0 == regOpt.getIsLocalAnaes()) 
        {
            SearchFormBean searchBean = new SearchFormBean();
            DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOpt.getRegOptId());
            String docId = anaesRecord.getAnaRecordId();
            searchBean.setDocId(docId);

            // 麻醉医生列表
            searchBean.setRole(EvtParticipantService.ROLE_ANESTH);
            searchBean.setType("01");
            EvtParticipant participant = new EvtParticipant();
            List<EvtParticipant> anesDocList = evtParticipantDao.searchParticipantList(searchBean, beid);
            if(null != anesDocList && anesDocList.size()>0)
            {
                participant = anesDocList.get(0);
                if (!dispatch.getAnesthetistId().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getAnesthetistId());
                    BasUser user = basUserDao.searchUserById(dispatch.getAnesthetistId(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            }
            else
            {
                participant.setUserLoginName(dispatch.getAnesthetistId());
                BasUser user = basUserDao.searchUserById(dispatch.getAnesthetistId(), beid);
                participant.setName(user == null ? "" : user.getName());
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_ANESTH);
                participant.setOperatorType("01"); 
                participant.setIsShiftChange(1);
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
            
            
            // 副麻医生列表
            searchBean.setRole(EvtParticipantService.ROLE_ANESTH);
            searchBean.setType("03");
            List<EvtParticipant> ciruAnesDocList = evtParticipantDao.searchParticipantList(searchBean, beid);
            if(null != ciruAnesDocList && ciruAnesDocList.size()>0)
            {
                participant = ciruAnesDocList.get(0);
                if (StringUtils.isBlank(dispatch.getCircuAnesthetistId()))
                {
                    evtParticipantDao.deleteById(participant);
                }
                else if (!dispatch.getCircuAnesthetistId().equals(participant.getUserLoginName())) 
                {
                    participant.setUserLoginName(dispatch.getCircuAnesthetistId());
                    BasUser user = basUserDao.searchUserById(dispatch.getCircuAnesthetistId(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            }
            else
            {
                participant.setUserLoginName(dispatch.getCircuAnesthetistId());
                BasUser user = basUserDao.searchUserById(dispatch.getCircuAnesthetistId(), beid);
                participant.setName(user == null ? "" : user.getName());
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_ANESTH);
                participant.setOperatorType("03"); 
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
            
            // 巡回护士列表
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_TOUR);// 05
            List<EvtParticipant> nurseList = evtParticipantDao.searchParticipantList(searchBean, beid);
            if(null != nurseList && nurseList.size()>0)
            {
                // 第一巡回护士
                participant = nurseList.get(0);
                if (!dispatch.getCircunurseId1().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getCircunurseId1());
                    BasUser user = basUserDao.searchUserById(dispatch.getCircunurseId1(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            }
            else
            {
                participant.setUserLoginName(dispatch.getCircunurseId1());
                BasUser user = basUserDao.searchUserById(dispatch.getCircunurseId1(), beid);
                participant.setName(user == null ? "" : user.getName());
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_NURSE);
                participant.setOperatorType(EvtParticipantService.OPER_TYPE_TOUR); 
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
            
            // 第二巡回护士
            if (null != nurseList && nurseList.size() > 1) {
                participant = nurseList.get(1);
                if (StringUtils.isBlank(dispatch.getCircunurseId2())) {
                    evtParticipantDao.deleteById(participant);
                } else if (!dispatch.getCircunurseId2().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getCircunurseId2());
                    BasUser user = basUserDao.searchUserById(dispatch.getCircunurseId2(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            } else if (StringUtils.isNotBlank(dispatch.getCircunurseId2())) {
                participant = new EvtParticipant();
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_NURSE);
                participant.setUserLoginName(dispatch.getCircunurseId2());
                participant.setOperatorType(EvtParticipantService.OPER_TYPE_TOUR); // 巡回护士
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
            // 器械护士列表
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_INSTRUMENT);// 04
            List<EvtParticipant> instruNurseList = evtParticipantDao.searchParticipantList(searchBean, beid);

            // 第一洗手护士
            if (null != instruNurseList && instruNurseList.size() > 0) {
                participant = instruNurseList.get(0);
                if (StringUtils.isBlank(dispatch.getInstrnurseId1())) {
                    evtParticipantDao.deleteById(participant);
                } else if (!dispatch.getInstrnurseId1().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getInstrnurseId1());
                    BasUser user = basUserDao.searchUserById(dispatch.getInstrnurseId1(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            } else if (StringUtils.isNotBlank(dispatch.getInstrnurseId1())) {
                participant = new EvtParticipant();
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_NURSE);
                participant.setUserLoginName(dispatch.getInstrnurseId1());
                participant.setOperatorType(EvtParticipantService.OPER_TYPE_INSTRUMENT); // 洗手护士
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }

            // 第二洗手护士
            if (null != instruNurseList && instruNurseList.size() > 1) {
                participant = instruNurseList.get(1);
                if (StringUtils.isBlank(dispatch.getInstrnurseId2())) {
                    evtParticipantDao.deleteById(participant);
                } else if (!dispatch.getInstrnurseId2().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getInstrnurseId2());
                    BasUser user = basUserDao.searchUserById(dispatch.getInstrnurseId2(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            } else if (StringUtils.isNotBlank(dispatch.getInstrnurseId2())) {
                participant = new EvtParticipant();
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_NURSE);
                participant.setUserLoginName(dispatch.getInstrnurseId2());
                participant.setOperatorType(EvtParticipantService.OPER_TYPE_INSTRUMENT); // 洗手护士
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
        }

        return "保存基本信息成功!";
    }
	
	@Transactional
    public String updateRegOptInfoQNZZY(BasRegOpt regOpt, BasDispatch dispatch) {
        String beid = getBeid();
        if (regOpt != null && (!StringUtils.isEmpty(regOpt.getRegOptId()))) {
            if (StringUtils.isBlank(regOpt.getBeid()))
            {
                regOpt.setBeid(beid);
            }
            else
            {
                beid = regOpt.getBeid();
            }
            BasDept basDept = basDeptDao.searchDeptById(regOpt.getDeptId(), beid);
            if (basDept != null) {
                regOpt.setDeptName(basDept.getName());
            }
            BasRegion basRegion = basRegionDao.searchRegionById(regOpt.getRegionId(), beid);
            if (basRegion != null) {
                regOpt.setRegionName(basRegion.getName());
            }
            BasRegOptUtils.IsLocalAnaesSet(regOpt);
            
        }
        

       
        
	    /**
		 * 在术中修改手术信息时，需判断是否更换手术室，如更换则需要将原手术数据同步至当前手术室
		 */
		BasRegOpt oldOpt = basRegOptDao.searchRegOptById(regOpt.getRegOptId());
		
		if(null!=oldOpt && OperationState.SURGERY.equals(oldOpt.getState())){
			
			//更换手术室是否需要将原手术室采集服务停止
			BasDispatch oldDispatch = basDispatchDao.getDispatchOper(regOpt.getRegOptId());
			if("0".equals(oldOpt.getIsLocalAnaes()) && !oldDispatch.getOperRoomId().equals(dispatch.getOperRoomId())){
				CmdMsg msg = new CmdMsg();
				msg.setRegOptId(regOpt.getRegOptId());
				msg.setMsgType(MyConstants.OPERATION_STATUS_FORCEEND);
				MessageProcess.process(msg);
			}
			
			
			//如果在术中主刀医生修改
			if(StringUtils.isNotBlank(regOpt.getOperatorId()) && !regOpt.getOperatorId().equals(oldOpt.getOperatorId())){
				DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOpt.getRegOptId());
				if(null!=anaesRecord){
					evtParticipantDao.deleteByUserId(anaesRecord.getAnaRecordId(), oldOpt.getOperatorId());
					EvtParticipant participant = new EvtParticipant();
					participant.setDocId(anaesRecord.getAnaRecordId());
					participant.setRole(EvtParticipantService.ROLE_OPERAT);
					participant.setOperatorType("06"); //主刀医生
					participant.setUserLoginName(regOpt.getOperatorId());
					participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
					evtParticipantDao.insert(participant);
				}
			}
			/**
			 * 如果是术中全麻改局麻，需要停掉采集服务
			 */
			if ("0".equals(oldOpt.getIsLocalAnaes())
					&& "1".equals(regOpt.getIsLocalAnaes())) {
				CmdMsg msg = new CmdMsg();
				msg.setRegOptId(regOpt.getRegOptId());
				msg.setMsgType(MyConstants.OPERATION_STATUS_FORCEEND);
				MessageProcess.process(msg);
			}
			/**
			 * 术中局麻改成全麻\全麻改局麻时，修改手术护理记录单的麻醉方法字段
			 */
			if (!oldOpt.getIsLocalAnaes().equals(regOpt.getIsLocalAnaes())) {
				DocOptCareRecord optCareRecord = docOptCareRecordDao.selectByRegOptId(regOpt.getRegOptId());
				if (null != optCareRecord && !regOpt.getDesignedAnaesMethodCode().equals(optCareRecord.getAnaesMethodCode())) {
					optCareRecord.setAnaesMethodCode(regOpt.getDesignedAnaesMethodCode());
					optCareRecord.setAnaesMethodName(regOpt.getDesignedAnaesMethodName());
					docOptCareRecordDao.updateByPrimaryKeySelective(optCareRecord);
				}
			}
			/**
			 * 当全麻，需判断anesDocList是否为空,如果为空则插入排班表的麻醉医生字段
			 */
			if("0".equals(regOpt.getIsLocalAnaes())){
				SearchFormBean searchBean = new SearchFormBean();
				DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOpt.getRegOptId());
				String docId = anaesRecord.getAnaRecordId();
				searchBean.setDocId(docId);
	
				 // 麻醉医生列表
	            searchBean.setRole(EvtParticipantService.ROLE_ANESTH);
	            searchBean.setType("01");
	            EvtParticipant participant = new EvtParticipant();
	            List<EvtParticipant> anesDocList = evtParticipantDao.searchParticipantList(searchBean, beid);
	            if(null != anesDocList && anesDocList.size()>0)
	            {
	                participant = anesDocList.get(0);
	                if (!dispatch.getAnesthetistId().equals(participant.getUserLoginName())) {
	                    participant.setUserLoginName(dispatch.getAnesthetistId());
	                    BasUser user = basUserDao.searchUserById(dispatch.getAnesthetistId(), beid);
	                    participant.setName(user == null ? "" : user.getName());
	                    evtParticipantDao.update(participant);
	                }
	            }
	            else
	            {
	                participant.setUserLoginName(dispatch.getAnesthetistId());
	                BasUser user = basUserDao.searchUserById(dispatch.getAnesthetistId(), beid);
	                participant.setName(user == null ? "" : user.getName());
	                participant.setDocId(docId);
	                participant.setRole(EvtParticipantService.ROLE_ANESTH);
	                participant.setOperatorType("01");
	                participant.setIsShiftChange(1);
	                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
	                evtParticipantDao.insert(participant);
	            }	
			}
		}
		
		if (dispatch != null && (!StringUtils.isEmpty(dispatch.getRegOptId()))) {
	        if (StringUtils.isNotEmpty(dispatch.getOperRoomId())) {
	            dispatch.setBeid(beid);
	            basDispatchDao.update(dispatch);
	        }
	    }
		

		basRegOptDao.updateByPrimaryKey(regOpt); 
		
        //从his传过来的急诊手术，审核之后，修改完基本信息，就将状态改为术前
        if (null != regOpt && 1 == regOpt.getEmergency() && "02".equals(regOpt.getState()))
       {
            controllerDao.checkOperation(regOpt.getRegOptId(), "03", regOpt.getState());
            regOpt.setState("03");
        }

        // 如果在术前修改了手术的人员信息，或者是his传过来的急诊手术在未排班的时候修改了手术信息，需要同步将s_participant表中的人员信息同步修改
        if (null != regOpt && 
            ("03".equals(regOpt.getState()) 
                || (1 == regOpt.getEmergency() && "02".equals(regOpt.getState()))) 
            && 0 == regOpt.getIsLocalAnaes()) 
        {
            SearchFormBean searchBean = new SearchFormBean();
            DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOpt.getRegOptId());
            String docId = anaesRecord.getAnaRecordId();
            searchBean.setDocId(docId);

            // 麻醉医生列表
            searchBean.setRole(EvtParticipantService.ROLE_ANESTH);
            searchBean.setType("01");
            EvtParticipant participant = new EvtParticipant();
            List<EvtParticipant> anesDocList = evtParticipantDao.searchParticipantList(searchBean, beid);
            if(null != anesDocList && anesDocList.size()>0)
            {
                participant = anesDocList.get(0);
                if (!dispatch.getAnesthetistId().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getAnesthetistId());
                    BasUser user = basUserDao.searchUserById(dispatch.getAnesthetistId(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            }
            else
            {
                participant.setUserLoginName(dispatch.getAnesthetistId());
                BasUser user = basUserDao.searchUserById(dispatch.getAnesthetistId(), beid);
                participant.setName(user == null ? "" : user.getName());
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_ANESTH);
                participant.setOperatorType("01"); 
                participant.setIsShiftChange(1);
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
            
            
            // 副麻医生列表
            searchBean.setRole(EvtParticipantService.ROLE_ANESTH);
            searchBean.setType("03");
            List<EvtParticipant> ciruAnesDocList = evtParticipantDao.searchParticipantList(searchBean, beid);
            if(null != ciruAnesDocList && ciruAnesDocList.size()>0)
            {
                participant = ciruAnesDocList.get(0);
                if (StringUtils.isBlank(dispatch.getCircuAnesthetistId()))
                {
                    evtParticipantDao.deleteById(participant);
                }
                else if (!dispatch.getCircuAnesthetistId().equals(participant.getUserLoginName())) 
                {
                    participant.setUserLoginName(dispatch.getCircuAnesthetistId());
                    BasUser user = basUserDao.searchUserById(dispatch.getCircuAnesthetistId(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            }
            else
            {
                participant.setUserLoginName(dispatch.getCircuAnesthetistId());
                BasUser user = basUserDao.searchUserById(dispatch.getCircuAnesthetistId(), beid);
                participant.setName(user == null ? "" : user.getName());
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_ANESTH);
                participant.setOperatorType("03"); 
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
            
            // 巡回护士列表
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_TOUR);// 05
            List<EvtParticipant> nurseList = evtParticipantDao.searchParticipantList(searchBean, beid);
            if(null != nurseList && nurseList.size()>0)
            {
                // 第一巡回护士
                participant = nurseList.get(0);
                if (!dispatch.getCircunurseId1().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getCircunurseId1());
                    BasUser user = basUserDao.searchUserById(dispatch.getCircunurseId1(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            }
            else
            {
                participant.setUserLoginName(dispatch.getCircunurseId1());
                BasUser user = basUserDao.searchUserById(dispatch.getCircunurseId1(), beid);
                participant.setName(user == null ? "" : user.getName());
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_NURSE);
                participant.setOperatorType(EvtParticipantService.OPER_TYPE_TOUR); 
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
            
            // 第二巡回护士
            if (null != nurseList && nurseList.size() > 1) {
                participant = nurseList.get(1);
                if (StringUtils.isBlank(dispatch.getCircunurseId2())) {
                    evtParticipantDao.deleteById(participant);
                } else if (!dispatch.getCircunurseId2().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getCircunurseId2());
                    BasUser user = basUserDao.searchUserById(dispatch.getCircunurseId2(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            } else if (StringUtils.isNotBlank(dispatch.getCircunurseId2())) {
                participant = new EvtParticipant();
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_NURSE);
                participant.setUserLoginName(dispatch.getCircunurseId2());
                participant.setOperatorType(EvtParticipantService.OPER_TYPE_TOUR); // 巡回护士
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
            // 器械护士列表
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_INSTRUMENT);// 04
            List<EvtParticipant> instruNurseList = evtParticipantDao.searchParticipantList(searchBean, beid);

            // 第一洗手护士
            if (null != instruNurseList && instruNurseList.size() > 0) {
                participant = instruNurseList.get(0);
                if (StringUtils.isBlank(dispatch.getInstrnurseId1())) {
                    evtParticipantDao.deleteById(participant);
                } else if (!dispatch.getInstrnurseId1().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getInstrnurseId1());
                    BasUser user = basUserDao.searchUserById(dispatch.getInstrnurseId1(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            } else if (StringUtils.isNotBlank(dispatch.getInstrnurseId1())) {
                participant = new EvtParticipant();
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_NURSE);
                participant.setUserLoginName(dispatch.getInstrnurseId1());
                participant.setOperatorType(EvtParticipantService.OPER_TYPE_INSTRUMENT); // 洗手护士
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }

            // 第二洗手护士
            if (null != instruNurseList && instruNurseList.size() > 1) {
                participant = instruNurseList.get(1);
                if (StringUtils.isBlank(dispatch.getInstrnurseId2())) {
                    evtParticipantDao.deleteById(participant);
                } else if (!dispatch.getInstrnurseId2().equals(participant.getUserLoginName())) {
                    participant.setUserLoginName(dispatch.getInstrnurseId2());
                    BasUser user = basUserDao.searchUserById(dispatch.getInstrnurseId2(), beid);
                    participant.setName(user == null ? "" : user.getName());
                    evtParticipantDao.update(participant);
                }
            } else if (StringUtils.isNotBlank(dispatch.getInstrnurseId2())) {
                participant = new EvtParticipant();
                participant.setDocId(docId);
                participant.setRole(EvtParticipantService.ROLE_NURSE);
                participant.setUserLoginName(dispatch.getInstrnurseId2());
                participant.setOperatorType(EvtParticipantService.OPER_TYPE_INSTRUMENT); // 洗手护士
                participant.setPartpId(GenerateSequenceUtil.generateSequenceNo());
                evtParticipantDao.insert(participant);
            }
            
            String isConnectionFlag = Global.getConfig("isConnectionHis").trim();
            if(StringUtils.isEmpty(isConnectionFlag) || "true".equals(isConnectionFlag)){
                logger.info("===============================发送手术排班信息到his===========================================");
                HisInterfaceServiceQNZZY hisInterfaceService = SpringContextHolder.getBean(HisInterfaceServiceQNZZY.class);
                hisInterfaceService.sendDispatchToHis(dispatch, regOpt);
            }
        }

        return "保存基本信息成功!";
    }

	/**
	 * 
	 * @discription 修改regOpt
	 * @author chengwang
	 * @created 2015年10月30日 上午9:32:33
	 * @param regOpt
	 * @return
	 */
	@Transactional
	public void updateRegOpt(BasRegOpt regOpt) {
		List<String> anaesMethodCodes = regOpt.getDesignedAnaesMethodCodes();
		if (null != anaesMethodCodes && anaesMethodCodes.size() > 0) {
			String beid = regOpt.getBeid();
			if (StringUtils.isBlank("beid")) {
				beid = getBeid();
				regOpt.setBeid(beid);
			}
			BasRegOptUtils.IsLocalAnaesSet(regOpt);
		}
		basRegOptDao.saveRegOptWH(regOpt);
	}

	/**
	 * 
	 * @discription
	 * @author chengwang
	 * @created 2015年10月30日 上午10:03:08
	 * @param cancleRegOptFormBean
	 * @return
	 */
	@Transactional
	public void cancleRegOpt(CancleRegOptFormBean cancleRegOptFormBean, ResponseValue resp) {
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(cancleRegOptFormBean.getRegOptId());
		regOpt.setReasons(cancleRegOptFormBean.getReasons());
		basRegOptDao.updateByPrimaryKeySelective(regOpt);
		Controller controller = controllerDao.getControllerById(cancleRegOptFormBean.getRegOptId());
		/**
		 * 未审核、未排班、术前 NOT_REVIEWED、NO_SCHEDULING、PREOPERATIVE允许取消手术
		 */
		if (OperationState.NOT_REVIEWED.equals(controller.getState()) || OperationState.NO_SCHEDULING.equals(controller.getState()) || OperationState.PREOPERATIVE.equals(controller.getState())) {
			controllerDao.checkOperation(cancleRegOptFormBean.getRegOptId(), OperationState.CANCEL, controller.getState());
			resp.setResultCode("1");
			resp.setResultMessage("取消手术成功!");
		} else {
			resp.setResultCode("30000001");
			resp.setResultMessage("当前手术状态已不允许取消手术，请核实当前病人信息!");
		}
	}
	
	/**
     * 
     * @discription
     * @author chengwang
     * @created 2015年10月30日 上午10:03:08
     * @param cancleRegOptFormBean
     * @return
     */
    @Transactional
    public void batchCancleRegOpt(CancleRegOptFormBean cancleRegOptFormBean, ResponseValue resp) {
        List<String> regOptIds = cancleRegOptFormBean.getRegOptIdList();
        List<String> failList = new ArrayList<String>();
        if (null != regOptIds && regOptIds.size() > 0)
        {
            for (String regOptId : regOptIds)
            {
                BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
                regOpt.setReasons(cancleRegOptFormBean.getReasons());
                basRegOptDao.updateByPrimaryKeySelective(regOpt);
                Controller controller = controllerDao.getControllerById(regOptId);
                /**
                 * 未审核、未排班、术前 NOT_REVIEWED、NO_SCHEDULING、PREOPERATIVE允许取消手术
                 */
                if (OperationState.NOT_REVIEWED.equals(controller.getState())
                    || OperationState.NO_SCHEDULING.equals(controller.getState())
                    || OperationState.PREOPERATIVE.equals(controller.getState()))
                {
                    controllerDao.checkOperation(regOptId,
                        OperationState.CANCEL,
                        controller.getState());
                    resp.setResultCode("1");
                    resp.setResultMessage("取消手术成功!");
                }
                else
                {
                    failList.add(regOpt.getName());
                }
            }
        }
        
        if (failList.size() > 1)
        {
            resp.put("failList", StringUtils.getStringByList(failList) + "的手术状态已不允许取消手术，请核实当前病人信息!");
        }
    }
	
	/**
	 * 取消手术（永兴局点）
	 * @discription
	 * @created 2018年04月12日 上午10:03:08
	 * @param cancleRegOptFormBean
	 * @return
	 */
	@Transactional
	public void cancleRegOptYXRM(CancleRegOptFormBean cancleRegOptFormBean, ResponseValue resp) {
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(cancleRegOptFormBean.getRegOptId());
		regOpt.setReasons(cancleRegOptFormBean.getReasons());
		basRegOptDao.updateByPrimaryKeySelective(regOpt);
		Controller controller = controllerDao.getControllerById(cancleRegOptFormBean.getRegOptId());
		/**
		 * 未审核、未排班、术前 NOT_REVIEWED、NO_SCHEDULING、PREOPERATIVE允许取消手术
		 */
		if (OperationState.NOT_REVIEWED.equals(controller.getState()) || OperationState.NO_SCHEDULING.equals(controller.getState()) || OperationState.PREOPERATIVE.equals(controller.getState())) {
			controllerDao.checkOperation(cancleRegOptFormBean.getRegOptId(), OperationState.CANCEL, controller.getState());
			
			String isConnectionFlag = Global.getConfig("isConnectionHis").trim();
            if(StringUtils.isEmpty(isConnectionFlag) || "true".equals(isConnectionFlag))
            {
                logger.info("===============================发送手术麻醉记录到his===========================================");
                HisInterfaceServiceYXRM hisInterfaceService = SpringContextHolder.getBean(HisInterfaceServiceYXRM.class);
                //把手术结束状态发送给HIS
                hisInterfaceService.updateState(cancleRegOptFormBean.getRegOptId(), "08");
            }
            
			resp.setResultCode("1");
			resp.setResultMessage("取消手术成功!");
		} else {
			resp.setResultCode("30000001");
			resp.setResultMessage("当前手术状态已不允许取消手术，请核实当前病人信息!");
		}
	}

	@Transactional
	public void activeRegOpt(CancleRegOptFormBean acticRegOptFormBean, ResponseValue resp) {
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(acticRegOptFormBean.getRegOptId());
		if (!OperationState.CANCEL.equals(regOpt.getState())) {
			resp.setResultCode("30000001");
			resp.setResultMessage("当前手术状态已不允许激活手术!");
		} else {
			regOpt.setReasons(acticRegOptFormBean.getReasons());
			basRegOptDao.updateByPrimaryKeySelective(regOpt);
			Controller controller = controllerDao.getControllerById(acticRegOptFormBean.getRegOptId());
			controllerDao.checkOperation(acticRegOptFormBean.getRegOptId(), controller.getPreviousState(), OperationState.CANCEL);
		}
	}
	
	@Transactional
    public void batchActiveRegOpt(CancleRegOptFormBean acticRegOptFormBean, ResponseValue resp) {
	    List<String> regOptIds = acticRegOptFormBean.getRegOptIdList();
	    List<String> failList = new ArrayList<String>();
	    if (null != regOptIds && regOptIds.size() > 0)
	    {
	        for (String regOptId : regOptIds)
	        {
    	        BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
    	        if (!OperationState.CANCEL.equals(regOpt.getState())) {
    	            failList.add(regOpt.getName());
    	        } else {
    	            regOpt.setReasons(acticRegOptFormBean.getReasons());
    	            basRegOptDao.updateByPrimaryKeySelective(regOpt);
    	            Controller controller = controllerDao.getControllerById(regOptId);
    	            controllerDao.checkOperation(regOptId, controller.getPreviousState(), OperationState.CANCEL);
    	        }
	        }
	    }
	    if (failList.size() > 1)
        {
	        resp.put("failList", StringUtils.getStringByList(failList) + "的手术状态已不允许激活手术!");
        } 
    }

	/**
	 * 查询术中巡视记录
	 * 
	 * @param baseQuery
	 * @return
	 */
	public List<SearchOperaPatrolRecordFormBean> getOperaPatrolRecordList(BaseInfoQuery baseQuery) {
		if (StringUtils.isEmpty(baseQuery.getBeid())) {
			baseQuery.setBeid(getBeid());
		}
		return basRegOptDao.getOperaPatrolRecordList(baseQuery);
	}

	public List<SearchOperaPatrolRecordFormBean> getOperaPatrolRecordListByRoomId(BaseInfoQuery baseQuery) {
		if (StringUtils.isEmpty(baseQuery.getBeid())) {
			baseQuery.setBeid(getBeid());
		}
		baseQuery.setOperRoomId(getCurRoomId(baseQuery.getRegOptId()));
		return basRegOptDao.getOperaPatrolRecordListByRoomId(baseQuery);
	}
	
	
	public List<SearchOperaPatrolRecordFormBean> getOperaPatrolRecordListByRoomId(BaseInfoQuery baseQuery,String roomId) {
		if (StringUtils.isEmpty(baseQuery.getBeid())) {
			baseQuery.setBeid(getBeid());
		}
		baseQuery.setOperRoomId(roomId);
		return basRegOptDao.getOperaPatrolRecordListByRoomId(baseQuery);
	}
	
	
	public List<SearchOperaPatrolRecordFormBean> getAgaintOperaPatrolByRoomId(BaseInfoQuery baseQuery) {
		if (StringUtils.isEmpty(baseQuery.getBeid())) {
			baseQuery.setBeid(getBeid());
		}
		return basRegOptDao.getOperaPatrolRecordListByRoomId(baseQuery);
	}
	

	@Transactional
	public int autoArchiveDocuments() {
	    BasDictItemDao basDictItemDao = SpringContextHolder.getBean(BasDictItemDao.class);
	    BasRegOptDao basRegOptDao = SpringContextHolder.getBean(BasRegOptDao.class);
	    BasBusEntityDao basBusEntityDao = SpringContextHolder.getBean(BasBusEntityDao.class);
	    String beid = basBusEntityDao.getBeid();
		int result = 0;
		List<SysCodeFormbean> dayList = basDictItemDao.searchSysCodeByGroupId("archive_day", beid);
		int days = Integer.parseInt(dayList.get(0).getCodeValue());
		long currentTime = System.currentTimeMillis();
		Date finishTime = new Date(currentTime - days * 24 * 60 * 60 * 1000);
		String time = DateUtils.DateToString(finishTime);
		List<SearchMyOperationFormBean> list = basRegOptDao.searchNoArchiveRegOpt(time, beid);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String regOptId = list.get(i).getRegOptId();
				BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
				regOpt.setArchState("AR");
				regOpt.setNurseArchState("AR");
				basRegOptDao.updateByPrimaryKey(regOpt);
				result++;
			}
		}
		return result;
	}

	/**
	 * 查询文书的状态
	 * 
	 * @param sql
	 * @return
	 */
	public String searchDocumentState(String sql) {
		return basRegOptDao.searchDocumentState(sql);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map searchDocumentUnFinish(SearchConditionFormBean searchConditionFormBean) {
		Map map = new HashMap();
		String beid = null;
		if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
			beid = getBeid();
			searchConditionFormBean.setBeid(beid);
		} else {
			beid = searchConditionFormBean.getBeid();
		}
		searchConditionFormBean.setState("03");
		BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", beid);
		List<SearchMyOperationFormBean> resultList = new ArrayList<SearchMyOperationFormBean>();
		List<SearchMyOperationFormBean> result = basRegOptDao.searchRegOpt(user == null ? "" : user.getUserName(), user == null ? "" : user.getUserType(), searchConditionFormBean);
		int total = 0;
		if (result != null && result.size() > 0) {
			String regOptStr = "";
			for (int i = 0; i < result.size(); i++) {
				regOptStr += "'" + result.get(i).getRegOptId() + "',";
			}
			regOptStr = regOptStr.substring(0, regOptStr.length() - 1);
			List<DocStateArrayFormbean> arrList = getDocumentState(user, searchConditionFormBean.getState(), regOptStr, beid);

			for (int i = 0; i < result.size(); i++) {
				SearchMyOperationFormBean bean = result.get(i);
				String documentState = "完成";

				List<DocumentStateFormbean> stateFormbeanList = new ArrayList<DocumentStateFormbean>();
				for (DocStateArrayFormbean docStateArrayFormbean : arrList) {
					DocumentStateFormbean stateFormbean = new DocumentStateFormbean();
					List<DocumentStateFormbean> statList = docStateArrayFormbean.getDocStateList();
					for (DocumentStateFormbean documentStateFormbean : statList) {
						if (bean.getRegOptId().equals(documentStateFormbean.getRegOptId())) {
							if (!"END".equals(documentStateFormbean.getState()) && docStateArrayFormbean.getIsNeed() == 1) {
								if (1 == docStateArrayFormbean.getIsNeed()) { // 1
																				// 必须完成
									documentState = "未完成";
								}
								// documentState = "未完成";
								stateFormbean.setState("未完成");
								stateFormbeanList.add(stateFormbean);
							} else {
								stateFormbean.setState("完成");

							}
							stateFormbean.setName(docStateArrayFormbean.getDocName());
						}
					}
				}

				// 已完成所有文书的手术不需要再加入到首页待补文书手术列表中
				if (documentState.equals("未完成")) {
					bean.setDocumentState(documentState);
					total = total + 1;
					bean.setDocumentStateList(stateFormbeanList);
					resultList.add(bean);
				} else {
					bean.setDocumentState("完成");
				}
			}
		}

		searchConditionFormBean.setState("06");
		List<SearchMyOperationFormBean> resultSH = basRegOptDao.searchRegOpt(user == null ? "" : user.getUserName(), user == null ? "" : user.getUserType(), searchConditionFormBean);

		if (resultSH != null && resultSH.size() > 0) {

			String regOptStr = "";
			for (int i = 0; i < resultSH.size(); i++) {
				regOptStr += "'" + resultSH.get(i).getRegOptId() + "',";
			}
			regOptStr = regOptStr.substring(0, regOptStr.length() - 1);
			List<DocStateArrayFormbean> arrList = getDocumentState(user, searchConditionFormBean.getState(), regOptStr, searchConditionFormBean.getBeid());

			for (int i = 0; i < resultSH.size(); i++) {
				SearchMyOperationFormBean bean = resultSH.get(i);
				String documentState = "完成";

				List<DocumentStateFormbean> stateFormbeanList = new ArrayList<DocumentStateFormbean>();
				for (DocStateArrayFormbean docStateArrayFormbean : arrList) {
					DocumentStateFormbean stateFormbean = new DocumentStateFormbean();
					List<DocumentStateFormbean> statList = docStateArrayFormbean.getDocStateList();
					for (DocumentStateFormbean documentStateFormbean : statList) {
						if (bean.getRegOptId().equals(documentStateFormbean.getRegOptId())) {
							if (!"END".equals(documentStateFormbean.getState()) && docStateArrayFormbean.getIsNeed() == 1) {
								if (1 == docStateArrayFormbean.getIsNeed()) { // 1
																				// 必须完成
									documentState = "未完成";
								}
								// documentState = "未完成";
								stateFormbean.setState("未完成");
								stateFormbeanList.add(stateFormbean);
							} else {
								stateFormbean.setState("完成");

							}
							stateFormbean.setName(docStateArrayFormbean.getDocName());
						}
					}
				}
				// 已完成所有文书的手术不需要再加入到首页待补文书手术列表中
				if (documentState.equals("未完成")) {
					bean.setDocumentState(documentState);
					total = total + 1;
					bean.setDocumentStateList(stateFormbeanList);
					resultList.add(bean);
				} else {
					bean.setDocumentState("完成");
				}
			}
		}
		map.put("documentNoFinishRegOpt", resultList);
		map.put("total", total);
		return map;
	}

	public List<DocStateArrayFormbean> getDocumentState(BasUser user, String state, String regOptStr, String beid) {
		List<BasDocument> documentList = basDocumentDao.searchDocument(user != null ? user.getRoleId() : "0", state, beid);
		// logger.info("regOptStr===="+regOptStr);
		// regOptStr = regOptStr.substring(0, regOptStr.length() - 1);
		List<DocStateArrayFormbean> arrList = new ArrayList<DocStateArrayFormbean>();
		for (int j = 0; j < documentList.size(); j++) {
			String sql = "select regOptId,processState as state from " + documentList.get(j).getTable() + " where regOptId in (" + regOptStr + ") ";
			List<DocumentStateFormbean> stateList = basRegOptDao.queryDocState(sql);
			DocStateArrayFormbean doc = new DocStateArrayFormbean();
			doc.setIsNeed(documentList.get(j).getIsNeed());
			doc.setDocName(documentList.get(j).getName());
			doc.setDocStateList(stateList);
			arrList.add(doc);
		}
		return arrList;
	}

	public List<OneRegOptDocumentStateFormbean> getDocumentStatuByRegOptId(String regOptId) {
		String beid = getBeid();
		List<BasDocument> documents = basDocumentDao.searchAllDocumentMenu(beid);
		List<OneRegOptDocumentStateFormbean> stateFormbeanList = new ArrayList<OneRegOptDocumentStateFormbean>();
		if (documents != null && documents.size() > 0) {
			for (int j = 0; j < documents.size(); j++) {
				String table = documents.get(j).getTable();
				String name = documents.get(j).getName();
				String required = documents.get(j).getRequired();
				OneRegOptDocumentStateFormbean stateFormbean = new OneRegOptDocumentStateFormbean();
				String sql = "select processState from " + table + " where regOptId ='" + regOptId + "'";
				String state = basRegOptDao.searchDocumentState(sql);
				if (!"END".equals(state)) {
					stateFormbean.setName(name);
					stateFormbean.setState(false);
					stateFormbean.setRequired(required);
				} else {
					stateFormbean.setName(name);
					stateFormbean.setState(true);
					stateFormbean.setRequired(required);
				}
				stateFormbeanList.add(stateFormbean);

			}
		}

		return stateFormbeanList;
	}

	@Transactional
	public int updateMsid(String regOptId, String msid) {
		return basRegOptDao.updateMsid(regOptId, msid);
	}

	@Transactional
	public int updateOperTime(String operTime, String regOptId) {
		return basRegOptDao.updateOperTime(operTime, regOptId);
	}
	
	@Transactional
    public int updateIsLocalAnaes(int isLocalAnaes, String regOptId) {
        return basRegOptDao.updateIsLocalAnaes(isLocalAnaes, regOptId);
    }

	@Transactional
	public void suspendOperation(SuspendFormBean bean) {
		String regOptId = bean.getRegOptId();
		DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
		if (anaesRecord != null) {
			String docId = anaesRecord.getAnaRecordId();
			EvtAnaesEvent anaesevent = new EvtAnaesEvent();
			anaesevent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());// 中止手术时，ana_event_id
																				// 为null，插入会不成功
			String curTime = SysUtil.getTimeFormat(bean.getSuspendTime()); // 取前端传递的时间
			anaesevent.setDocId(docId);
			anaesevent.setOccurTime(bean.getSuspendTime()); // 取前端传递的时间
			anaesevent.setCode(EvtAnaesEventService.OUT_ROOM);
			// anaesevent.setState(OperationState.STOP); //state不能为空
			evtAnaesEventDao.insert(anaesevent);

			// 中止手术 入室事件、麻醉开始时间、手术开始时间、手术结束时间、麻醉结束时间、出室时间都不能为空
			EvtAnaesEvent rsEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 1); // 入室
			EvtAnaesEvent mzksEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 2);// 麻醉开始
			EvtAnaesEvent ssksEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 4);// 手术开始
			EvtAnaesEvent ssjsEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 5);// 手术结束
			EvtAnaesEvent mzjsEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 8);// 麻醉开始
			if (null == rsEvent) { // 入室
				rsEvent = new EvtAnaesEvent();
				rsEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
				rsEvent.setDocId(docId);
				rsEvent.setCode(1);
				rsEvent.setOccurTime(bean.getSuspendTime());
				// rsEvent.setState(OperationState.STOP);
				evtAnaesEventDao.insertSelective(rsEvent);
				anaesRecord.setInOperRoomTime(curTime); // 修改入室时间为 中止时间
			}

			if (null == mzksEvent) {// 麻醉开始
				mzksEvent = new EvtAnaesEvent();
				mzksEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
				mzksEvent.setDocId(docId);
				mzksEvent.setCode(2);
				mzksEvent.setOccurTime(bean.getSuspendTime());
				// mzksEvent.setState(OperationState.STOP);
				evtAnaesEventDao.insertSelective(mzksEvent);

				anaesRecord.setAnaesStartTime(curTime); // 修改麻醉开始时间为 中止时间
			}

			if (null == ssksEvent) {// 手术开始
				ssksEvent = new EvtAnaesEvent();
				ssksEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
				ssksEvent.setDocId(docId);
				ssksEvent.setCode(4);
				ssksEvent.setOccurTime(bean.getSuspendTime());
				// ssksEvent.setState(OperationState.STOP);
				evtAnaesEventDao.insertSelective(ssksEvent);

				anaesRecord.setOperStartTime(curTime); // 修改手术开始时间为 中止时间
			}

			if (null == ssjsEvent) {// 手术结束
				ssjsEvent = new EvtAnaesEvent();
				ssjsEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
				ssjsEvent.setDocId(docId);
				ssjsEvent.setCode(5);
				ssjsEvent.setOccurTime(bean.getSuspendTime());
				// ssjsEvent.setState(OperationState.STOP);
				evtAnaesEventDao.insertSelective(ssjsEvent);
				anaesRecord.setOperEndTime(curTime); // 修改手术结束时间为 中止时间
			}

			if (null == mzjsEvent) {// 麻醉结束
				mzjsEvent = new EvtAnaesEvent();
				mzjsEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
				mzjsEvent.setDocId(docId);
				mzjsEvent.setCode(8);
				mzjsEvent.setOccurTime(bean.getSuspendTime());
				// mzjsEvent.setState(OperationState.STOP);
				evtAnaesEventDao.insertSelective(mzjsEvent);

				anaesRecord.setAnaesEndTime(curTime); // 修改麻醉结束时间为 中止时间
			}

			// 将消息推送到手术室大屏
			BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
			WebSocketHandler.sentMessageToAllUser(regOpt.getDeptName() + regOpt.getRegionName() + regOpt.getBed() + "床的" + regOpt.getName() + "手术已中止");

			Controller controller = controllerDao.getControllerById(bean.getRegOptId());
			// 当麻醉事件提交数据为出室时，需要将控制表的状态改成POSTOPERATIVE 术后
			if (null != controller && controller.getState().equals(OperationState.SURGERY)) {
				controller.setState(OperationState.STOP);
				controller.setPreviousState(OperationState.SURGERY);
				controllerDao.update(controller);

				// 不生成多个d_anaes_record
				anaesRecord.setOutOperRoomTime(curTime);
				anaesRecord.setProcessState(OperationState.SURGERY);
				// anaesRecord.setState(controller.getState());
				anaesRecord.setLeaveTo("1"); // 默认回病房
				anaesRecord.setProcessState("END"); // 文书结束标志
				docAnaesRecordDao.updateByPrimaryKey(anaesRecord);

				/*
				 * AnaesRecord oldAnaesRecord = anaesRecord; //
				 * 如果状态发生改变，麻醉记录表新增一条数据为新的有用的数据，上个数据flag=0作为历史数据
				 * oldAnaesRecord.setFlag("0");
				 * anaesRecordDao.update(oldAnaesRecord);
				 * 
				 * //将当前传入的数据保存 anaesRecord.setState(controller.getState());
				 * anaesRecord.setOutOperRoomTime(curTime);
				 * anaesRecord.setProcessState("END"); anaesRecord.setFlag("1");
				 * anaesRecordDao.insert(anaesRecord);
				 */
			}
		}

		// 修改b_reg_opt的中止原因
		basRegOptDao.updateIntermitcause(bean.getCause(), regOptId);
	}

	/**
	 * 强制结束手术 如果有多个regOptIds
	 * 
	 * @param regOptIds
	 */
	@Transactional
	public void forceEndOperation(List<String> regOptIds) {
		
		logger.info("----------------forceEndOper-------------------regOptIds:"+regOptIds);
		
		if (null != regOptIds && regOptIds.size() > 0) {
			for (int i = 0; i < regOptIds.size(); i++) {
				String regOptId = regOptIds.get(i);
				DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
				if (anaesRecord != null) {
					Date curDate = new Date();
					String curTime = SysUtil.getTimeFormat(curDate); // 取前端传递的时间
					String docId = anaesRecord.getAnaRecordId();

					// 强制结束手术 入室事件、麻醉开始时间、手术开始时间、手术结束时间、麻醉结束时间、出室时间都不能为空
					EvtAnaesEvent rsEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 1); // 入室
					EvtAnaesEvent mzksEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 2);// 麻醉开始
					EvtAnaesEvent ssksEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 4);// 手术开始
					EvtAnaesEvent ssjsEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 5);// 手术结束
					EvtAnaesEvent mzjsEvent = evtAnaesEventDao.selectAnaesEventByCodeAndDocId(docId, 8);// 麻醉开始
					if (null == rsEvent) { // 入室
						rsEvent = new EvtAnaesEvent();
						rsEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
						rsEvent.setDocId(docId);
						rsEvent.setCode(1);
						rsEvent.setOccurTime(curDate);
						// rsEvent.setState(OperationState.POSTOPERATIVE);
						evtAnaesEventDao.insertSelective(rsEvent);
						anaesRecord.setInOperRoomTime(curTime); // 修改入室时间为 中止时间
					}

					if (null == mzksEvent) {// 麻醉开始
						mzksEvent = new EvtAnaesEvent();
						mzksEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
						mzksEvent.setDocId(docId);
						mzksEvent.setCode(2);
						mzksEvent.setOccurTime(curDate);
						// mzksEvent.setState(OperationState.POSTOPERATIVE);
						evtAnaesEventDao.insert(mzksEvent);

						anaesRecord.setAnaesStartTime(curTime); // 修改麻醉开始时间为
																// 中止时间
					}

					if (null == ssksEvent) {// 手术开始
						ssksEvent = new EvtAnaesEvent();
						ssksEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
						ssksEvent.setDocId(docId);
						ssksEvent.setCode(4);
						ssksEvent.setOccurTime(curDate);
						// ssksEvent.setState(OperationState.POSTOPERATIVE);
						evtAnaesEventDao.insertSelective(ssksEvent);

						anaesRecord.setOperStartTime(curTime); // 修改手术开始时间为 中止时间
					}

					if (null == ssjsEvent) {// 手术结束
						ssjsEvent = new EvtAnaesEvent();
						ssjsEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
						ssjsEvent.setDocId(docId);
						ssjsEvent.setCode(5);
						ssjsEvent.setOccurTime(curDate);
						// ssjsEvent.setState(OperationState.POSTOPERATIVE);
						evtAnaesEventDao.insertSelective(ssjsEvent);
						anaesRecord.setOperEndTime(curTime); // 修改手术结束时间为 中止时间
					}

					if (null == mzjsEvent) {// 麻醉结束
						mzjsEvent = new EvtAnaesEvent();
						mzjsEvent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
						mzjsEvent.setDocId(docId);
						mzjsEvent.setCode(8);
						mzjsEvent.setOccurTime(curDate);
						// mzjsEvent.setState(OperationState.POSTOPERATIVE);
						evtAnaesEventDao.insertSelective(mzjsEvent);

						anaesRecord.setAnaesEndTime(curTime); // 修改麻醉结束时间为 中止时间
					}

					EvtAnaesEvent anaesevent = new EvtAnaesEvent();
					anaesevent.setAnaEventId(GenerateSequenceUtil.generateSequenceNo());
					anaesevent.setDocId(docId);
					anaesevent.setOccurTime(curDate);
					anaesevent.setLeaveTo("1"); // 默认回病房
					anaesevent.setCode(EvtAnaesEventService.OUT_ROOM);
					// anaesevent.setState(OperationState.POSTOPERATIVE);
					// //变成术后状态
					evtAnaesEventDao.insertSelective(anaesevent);

					// 将消息推送到手术室大屏
					BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
					WebSocketHandler.sentMessageToAllUser(regOpt.getDeptName() + regOpt.getRegionName() + regOpt.getBed() + "床的" + regOpt.getName() + "手术已结束");

					
					Controller controller = controllerDao.getControllerById(regOptId);
					logger.info("----------------forceEndOper-----------regOptId:"+regOptId);
					
					// 当麻醉事件提交数据为出室时，需要将控制表的状态改成POSTOPERATIVE 术后
					if (null != controller && controller.getState().equals(OperationState.SURGERY)) {
						controller.setState(OperationState.POSTOPERATIVE);
						controller.setPreviousState(OperationState.SURGERY);
						controllerDao.update(controller);

						logger.info("----------------forceEndOper-----------updateState:"+OperationState.POSTOPERATIVE);
						
						// 不生成多个d_anaes_record
						anaesRecord.setOutOperRoomTime(curTime);
						anaesRecord.setProcessState(OperationState.SURGERY);
						// anaesRecord.setState(controller.getState());
						anaesRecord.setLeaveTo("1"); // 默认回病房
						anaesRecord.setProcessState("END"); // 文书结束标志
						docAnaesRecordDao.updateByPrimaryKey(anaesRecord);
						
						logger.info("----------------forceEndOper-----------ProcessState:END");
					}
				}

			}

		}
	}

	public String getCurrentModel(String regOptId) {
		String operModel = MyConstants.OPERATION_MODEL_NORMAL;
		DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
		String anaRecordId = "";
		if (null != anaesRecord) {
			anaRecordId = anaesRecord.getAnaRecordId();
			SearchFormBean searchBean = new SearchFormBean();
			searchBean.setDocId(anaRecordId);
			searchBean.setCurrentState("1");
			List<EvtRescueevent> list = evtRescueeventDao.searchRescueeventList(searchBean);
			if (list.size() > 0) {
				operModel = list.get(0).getModel();
				logger.info("getCurrentModel---当前的手术模式===" + operModel);
			}
			logger.info("getCurrentModel--- 返回当前的手术模式operModel===" + operModel + ",list的size===" + list.size());
		} else {
			logger.error("getCurrentModel-------根据regOptId返回的doc_id为空，请检查！");
		}

		return operModel;
	}

	/**
	 * 
	 * @discription 根据登录账号和手术状态获取归档的手术列表信息
	 * @author chengwang
	 * @created 2015-10-9 上午10:29:34
	 * @param loginName
	 * @param statu
	 * @return
	 */
	public List<SearchRegOptByLoginNameAndStateFormBean> searchRegOptByArchstate(SearchConditionFormBean searchConditionFormBean) {
		if (StringUtils.isBlank(searchConditionFormBean.getSort())) {
			searchConditionFormBean.setSort("operaDate");
		}
		if (StringUtils.isBlank(searchConditionFormBean.getOrderBy())) {
			searchConditionFormBean.setOrderBy("DESC");
		}

		String beid = null;
		if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
			beid = getBeid();
			searchConditionFormBean.setBeid(beid);
		} else {
			beid = searchConditionFormBean.getBeid();
		}

		String filter = getFilterStr(searchConditionFormBean);
		/*List<Filter> filters = getFilterStr(searchConditionFormBean);
		if (filters != null && filters.size() > 0) {
			for (int i = 0; i < filters.size(); i++) {
				if (StringUtils.isNotBlank(filters.get(i).getValue())) {
					if (filters.get(i).getField().equals("stateName")) {
						filter = filter + " AND a.state= '" + filters.get(i).getValue() + "' ";
					} else {
						filter = filter + " AND a." + filters.get(i).getField() + " like '%" + filters.get(i).getValue() + "%' ";
					}

				}

			}
		}*/
		BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", beid);
		List<SearchRegOptByLoginNameAndStateFormBean> resultList = new ArrayList<SearchRegOptByLoginNameAndStateFormBean>();
		List<SearchRegOptByLoginNameAndStateFormBean> result = basRegOptDao.searchRegOptByArchstate(filter, user == null ? "" : user.getUserName(), user == null ? "" : user.getRoleType(), searchConditionFormBean, getBeid());
		if (searchConditionFormBean.getOptType() == null || searchConditionFormBean.getOptType().size() == 0) {
			return result;
		}
		if (result != null && result.size() > 0) {

			String regOptStr = "";
			for (int i = 0; i < result.size(); i++) {
				regOptStr += "'" + result.get(i).getRegOptId() + "',";
			}
			regOptStr = regOptStr.substring(0, regOptStr.length() - 1);
			List<DocStateArrayFormbean> arrList = getDocumentState(user, searchConditionFormBean.getState(), regOptStr, beid);
			for (int i = 0; i < result.size(); i++) {
				SearchRegOptByLoginNameAndStateFormBean bean = result.get(i);
				String documentState = "完成";

				List<DocumentStateFormbean> stateFormbeanList = new ArrayList<DocumentStateFormbean>();
				for (DocStateArrayFormbean docStateArrayFormbean : arrList) {
					DocumentStateFormbean stateFormbean = new DocumentStateFormbean();
					List<DocumentStateFormbean> statList = docStateArrayFormbean.getDocStateList();
					for (DocumentStateFormbean documentStateFormbean : statList) {
						if (bean.getRegOptId().equals(documentStateFormbean.getRegOptId())) {
							if (!"END".equals(documentStateFormbean.getState())) {
								if (1 == docStateArrayFormbean.getIsNeed()) { // 1
																				// 必须完成
									documentState = "未完成";
								}
								stateFormbean.setState("未完成");
								// documentState = "未完成";
							} else {
								stateFormbean.setState("完成");
							}
							stateFormbean.setName(docStateArrayFormbean.getDocName());
							stateFormbeanList.add(stateFormbean);
							break;
						}
					}
				}
				bean.setDocumentState(documentState);
				bean.setDocumentStateList(stateFormbeanList);
				resultList.add(bean);
			}

		}

		return resultList;
	}

	public int searchRegOptTotalByArchstate(SearchConditionFormBean searchConditionFormBean) {

		if (StringUtils.isEmpty(searchConditionFormBean.getSort())) {
			searchConditionFormBean.setSort("operaDate");
		}
		if (StringUtils.isEmpty(searchConditionFormBean.getOrderBy())) {
			searchConditionFormBean.setOrderBy("DESC");
		}
		String beid = null;
		if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
			beid = getBeid();
			searchConditionFormBean.setBeid(beid);
		} else {
			beid = searchConditionFormBean.getBeid();
		}

		String filter = getFilterStr(searchConditionFormBean); 
		/*List<Filter> filters = searchConditionFormBean.getFilters();
		if (filters != null && filters.size() > 0) {
			for (int i = 0; i < filters.size(); i++) {
				if (StringUtils.isNotBlank(filters.get(i).getValue())) {
					if (filters.get(i).getField().equals("stateName")) {
						filter = filter + " AND a.state= '" + filters.get(i).getValue() + "' ";
					} else {
						filter = filter + " AND a." + filters.get(i).getField() + " like '%" + filters.get(i).getValue() + "%' ";
					}

				}

			}
		}*/
		BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", beid);

		return basRegOptDao.searchRegoptTotalByArchstate(filter, user == null ? "" : user.getUserName(), user == null ? "" : user.getRoleType(), searchConditionFormBean, beid);
	}

	@Transactional
	public ResponseValue updateArchstate(String regOptIds, String archstate) {
		ResponseValue resp = new ResponseValue();
		// List<String> optList = new ArrayList<String>();
		// optList.add("1");
		// optList.add("2");

		String[] regOptId = null;
		if (null != regOptIds && "" != regOptIds) {
			regOptId = regOptIds.split(",");
		}
		String beid = null;
		if (StringUtils.isBlank(beid)) {
			beid = getBeid();
		}

		List<String> regOptIdList = new ArrayList<String>();

		if (null != regOptId && regOptId.length > 0) {
			for (int i = 0; i < regOptId.length; i++) {
				if ("AR".equals(archstate)) {
					// 查询出麻醉医生所有文书列表
					// List<Menu> menuList = menuDao.searchDocumentMenu("4",
					// optList);
					String roleId = "";
					BasRoleFormBean basRole = new BasRoleFormBean();
					basRole.setBeid(beid);
					basRole.setRoleType("ANAES_DOCTOR");
					List<BasRole> list = basRoleDao.selectEntityList(basRole);
					if (!list.isEmpty() && list.size() > 0) {
						roleId = list.get(0).getId();
					}
					List<BasDocument> documentList = basDocumentDao.searchDocument(roleId, "06", beid); // 查询麻醉医生所有文书
					for (int j = 0; j < documentList.size(); j++) {
						BasDocument basDocument = documentList.get(j);

						// 如果文书为非必填文书，则不需要去查询状态
						if (0 == basDocument.getIsNeed()) { // 是否为必须完成的文书，才能归档。0:否,1:是
							continue;
						}

						String sql = "select processState as state from " + basDocument.getTable() + " where regOptId = " + regOptId[i];
						String state = basRegOptDao.searchDocumentState(sql);

						//如果等于空时则直接跳过
						if(StringUtils.isBlank(state)){
							continue;
						}
						
						if (!"END".equals(state)) {
							resp.setResultCode("10000000");
							resp.setResultMessage("当前选择的病案有必须完成的文书未完成，不能进行归档");
							return resp;
						}
					}
				}

				regOptIdList.add(regOptId[i]);

			}
		}

		if (null != regOptIdList && regOptIdList.size() > 0) {
			basRegOptDao.updateArchstate(regOptIdList, archstate);
		}

		resp.setResultCode("1");
		resp.setResultMessage("操作成功!");
		return resp;
	}

	@Transactional
	public ResponseValue updateNurseArchstate(String regOptIds, String nurseArchstate) {
		ResponseValue resp = new ResponseValue();
		List<String> optList = new ArrayList<String>();
		optList.add("1");
		optList.add("2");

		String[] regOptId = null;
		if (null != regOptIds && "" != regOptIds) {
			regOptId = regOptIds.split(",");
		}
		String beid = null;
		if (StringUtils.isBlank(beid)) {
			beid = getBeid();
		}
		String roleId = "";
		BasRoleFormBean basRole = new BasRoleFormBean();
		basRole.setBeid(beid);
		basRole.setRoleType("NURSE");
		List<BasRole> list = basRoleDao.selectEntityList(basRole);
		if (!list.isEmpty() && list.size() > 0) {
			roleId = list.get(0).getId();
		}
		List<String> regOptIdList = new ArrayList<String>();

		if (null != regOptId && regOptId.length > 0) {
			for (int i = 0; i < regOptId.length; i++) {
				if ("AR".equals(nurseArchstate)) {
					List<BasDocument> documentList = basDocumentDao.searchDocument(roleId, "06", beid); // 查询出护士所有文书列表
					for (int j = 0; j < documentList.size(); j++) {
						BasDocument basDocument = documentList.get(j);
						// 如果文书为非必填文书，则不需要去查询状态
						if (0 == basDocument.getIsNeed()) { // 是否为必须完成的文书，才能归档。0:否,1:是
							continue;
						}
						String sql = "select processState as state from " + basDocument.getTable() + " where regOptId = " + regOptId[i];
						String state = basRegOptDao.searchDocumentState(sql);

						if (!"END".equals(state)) {
							resp.setResultCode("10000000");
							resp.setResultMessage("当前选择的病案有文书未完成，不能进行归档");
							return resp;
						}
					}
				}

				regOptIdList.add(regOptId[i]);

			}
		}
		basRegOptDao.updateNurseArchstate(regOptIdList, nurseArchstate);
		resp.setResultCode("1");
		resp.setResultMessage("操作成功!");
		return resp;
	}

	public BasRegOpt queryRegOptByState(String state) {
		return basRegOptDao.selectByState(getCurRoomId(null), state, getBeid());
	}
	
	
	public List<CentralBigScreenDataFormbean> queryCentralBigScreenData(String showFreeBed) {
		return basRegOptDao.queryCentralBigScreenData(showFreeBed,getBeid());
	}

	/**
	 * 获取患者的文书完成情况
	 * 
	 * @param regOptId
	 * @param loginName
	 * @return
	 */
	public List<DocumentStateFormbean> getAnesDucumentStateByRegOptId(SearchConditionFormBean searchConditionFormBean) {
		String beid = null;
		if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
			beid = getBeid();
			searchConditionFormBean.setBeid(beid);
		} else {
			beid = searchConditionFormBean.getBeid();
		}

		String stateStr = "05,06,07";
		BasRegOpt basRegOpt = basRegOptDao.searchRegOptById(searchConditionFormBean.getRegOptId());
		if (stateStr.contains(basRegOpt.getState())) {
			stateStr = "06";
		} else {
			stateStr = "03";
		}
		BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName(), searchConditionFormBean.getBeid());
		List<BasDocument> documentList = basDocumentDao.searchDocument(user != null ? user.getRoleId() : "0", stateStr, beid);
		String sql = "";
		for (int j = 0; j < documentList.size(); j++) {
			BasDocument basDocument = documentList.get(j);

			// 男性不需要返回胎盘处置同意书
			if ("doc_placenta_handle_agree".equals(basDocument.getTable()) && "男".equals(basRegOpt.getSex())) {
				continue;
			}
			// 当前sql获取state状态，文书名称，文书英文名（用于一键打印），表名
			sql += "select regOptId,processState as state,'" + basDocument.getName() + "' name,'" + basDocument.getAliasName() + "' as impName ,'" + basDocument.getTable() + "' tabName from " + basDocument.getTable() + " where regOptId = '" + searchConditionFormBean.getRegOptId() + "' ";
			if (j + 1 < documentList.size()) {
				sql += " union all ";
			}
		}
		List<DocumentStateFormbean> stateList = basRegOptDao.queryDocState(sql);
		for (DocumentStateFormbean documentStateFormbean : stateList) {
			if (!"END".equals(documentStateFormbean.getState())) {
				documentStateFormbean.setState("未完成");
			} else {
				documentStateFormbean.setState("完成");
			}
		}
		return stateList;
	}

	public static List<DocumentStateFormbean> setDocumentList() {
		List<DocumentStateFormbean> ls = new ArrayList<DocumentStateFormbean>();

		DocumentStateFormbean map = new DocumentStateFormbean();
		map.setTabName("doc_accede");
		map.setImpName("accedeDoc");// 麻醉同意书
		ls.add(map);

		map = new DocumentStateFormbean();
		map.setTabName("doc_pre_visit");
		map.setImpName("preVisitDoc");// 术前访视单
		ls.add(map);

		map = new DocumentStateFormbean();
		map.setTabName("doc_anaes_record");
		map.setImpName("anesDoc");// 麻醉记录单
		ls.add(map);

		map = new DocumentStateFormbean();
		map.setTabName("doc_anaes_summary");
		map.setImpName("summaryDoc");// 麻醉记录单附页
		ls.add(map);

		map = new DocumentStateFormbean();
		map.setTabName("doc_analgesic_record");
		map.setImpName("anesAnalgesDoc");// 自控记录单
		ls.add(map);

		map = new DocumentStateFormbean();
		map.setTabName("doc_safe_check");
		map.setImpName("safeDoc");// 安全核查单
		ls.add(map);

		return ls;
	}

	public String getFilterStr(SearchConditionFormBean searchConditionFormBean) {
		String filter = "";
		List<Filter> filters = searchConditionFormBean.getFilters();
		if (filters != null && filters.size() > 0) {
			for (int i = 0; i < filters.size(); i++) {
				if (StringUtils.isNotBlank(filters.get(i).getValue())) {
					/*if (filters.get(i).getField().equals("inRoomStartTime")) {
						filter += " AND DATE_FORMAT(d.inOperRoomTime,'%Y-%m-%d %H:%m') >= '" + filters.get(i).getValue() + "' ";
					}
					if (filters.get(i).getField().equals("inRoomEndTime")) {
						filter += " AND DATE_FORMAT(d.inOperRoomTime,'%Y-%m-%d %H:%m') <= '" + filters.get(i).getValue() + "' ";
					}
					if (filters.get(i).getField().equals("outRoomStartTime")) {
						filter += " AND DATE_FORMAT(d.outOperRoomTime,'%Y-%m-%d %H:%m') >= '" + filters.get(i).getValue() + "' ";
					}
					if (filters.get(i).getField().equals("outRoomEndTime")) {
						filter += " AND DATE_FORMAT(d.outOperRoomTime,'%Y-%m-%d %H:%m') <= '" + filters.get(i).getValue() + "' ";
					}*/
					if (filters.get(i).getField().equals("stateName")) {
						filter += " AND a.state= '" + filters.get(i).getValue() + "' ";
					} else if (filters.get(i).getField().equals("operaDate")) {
						if (filters.get(i).getValue().length() == 2) {
							filter += " AND a." + filters.get(i).getField() + " like '%-" + filters.get(i).getValue() + "'";
						} else {
							filter += " AND a." + filters.get(i).getField() + " like '%" + filters.get(i).getValue() + "%' ";
						}
					} else if (filters.get(i).getField().equals("age")) {
						if (filters.get(i).getValue().contains("岁")) {
							filter += " AND a.age like '%" + filters.get(i).getValue().replaceAll("岁", "") + "%' ";
						} else if (filters.get(i).getValue().contains("月")) {
							filter += " AND a.ageMon like '%" + filters.get(i).getValue().replaceAll("月", "") + "%' ";
						} else if (filters.get(i).getValue().contains("天")) {
							filter += " AND a.ageDay like '%" + filters.get(i).getValue().replaceAll("天", "") + "%' ";
						} else {
							filter += " AND a." + filters.get(i).getField() + " like '%" + filters.get(i).getValue() + "%' ";
						}
					}else if (filters.get(i).getField().equals("operRoomId")) {
						filter += " AND c.operRoomId IN(select operRoomId from bas_operroom where `name` like '%" + filters.get(i).getValue() + "%' and beid = '" + searchConditionFormBean.getBeid() + "')";
					} else if (filters.get(i).getField().equals("operRoomName")) {
						filter += " AND c.operRoomId IN(select operRoomId from bas_operroom where `name` like '%" + filters.get(i).getValue() + "%' and beid = '" + searchConditionFormBean.getBeid() + "')";
					} else if (filters.get(i).getField().equals("anesthetistName")) {
						filter += " AND c.anesthetistId IN(select userName from bas_user where `name` like '%" + filters.get(i).getValue() + "%' and beid = '" + searchConditionFormBean.getBeid() + "')";
					} else if (filters.get(i).getField().equals("circunurseName1")) {
						filter += " AND c.circunurseId1 IN(select userName from bas_user where `name` like '%" + filters.get(i).getValue() + "%' and beid = '" + searchConditionFormBean.getBeid() + "')";
					} else if (filters.get(i).getField().equals("instrnurseName1")) {
						filter += " AND c.instrnurseId1 IN(select userName from bas_user where `name` like '%" + filters.get(i).getValue() + "%' and beid = '" + searchConditionFormBean.getBeid() + "')";
					} else {
						filter += " AND a." + filters.get(i).getField() + " like '%" + filters.get(i).getValue() + "%' ";
					}
				}

			}
		}
		return filter;
	}

	public static void main(String[] args) {
		String regOptStr = "'201704261352520015','201704261354540045','201704260955480003','201704201021180003','201704201159110024','201704201402400014','201704201148310000',";
		regOptStr = regOptStr.substring(0, regOptStr.length() - 1);
		System.out.println(regOptStr);
	}

    public List<SearchRegOptByLoginNameAndStateFormBean> getRegOpt(SearchConditionFormBean searchConditionFormBean)
    {
        if (StringUtils.isBlank(searchConditionFormBean.getSort())) {
            searchConditionFormBean.setSort("operaDate");
        }
        if (StringUtils.isBlank(searchConditionFormBean.getOrderBy())) {
            searchConditionFormBean.setOrderBy("DESC");
        }
        if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
            searchConditionFormBean.setBeid(getBeid());
        } 
        BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", searchConditionFormBean.getBeid());
        searchConditionFormBean.setRoleType(user == null ? "" : user.getRoleType());
        List<SearchRegOptByLoginNameAndStateFormBean> list = new ArrayList<SearchRegOptByLoginNameAndStateFormBean>();
        if (StringUtils.isBlank(searchConditionFormBean.getStartTime()))
        {
            list = basRegOptDao.getCurrentDateRegOpt(searchConditionFormBean);
        }
        else
        {
            list = basRegOptDao.getRegOpt(searchConditionFormBean);
        }
        if (null != list && list.size() > 0)
        {
            for (SearchRegOptByLoginNameAndStateFormBean sr : list)
            {
                String regOptId = sr.getRegOptId();
                DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
                String docId = anaesRecord.getAnaRecordId();
                SearchFormBean searchBean = new SearchFormBean();
                searchBean.setDocId(docId);
                List<EvtShiftChange> shiftList = evtShiftChangeDao.searchShiftChangeList(searchBean);
                if (null != shiftList && shiftList.size() > 0)
                {
                    sr.setAnesthetistName(shiftList.get(shiftList.size() - 1).getShiftChangePeople());
                }
            }
        }
        return list;
    }

    public int getRegOptTotal(SearchConditionFormBean searchConditionFormBean)
    {
        if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
            searchConditionFormBean.setBeid(getBeid());
        } 
        BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", searchConditionFormBean.getBeid());
        searchConditionFormBean.setRoleType(user == null ? "" : user.getRoleType());
        if (StringUtils.isBlank(searchConditionFormBean.getStartTime()))
        {
            return basRegOptDao.getCurrentDateRegOptTotal(searchConditionFormBean);
        }
        else
        {
            return basRegOptDao.getRegOptTotal(searchConditionFormBean);
        }
    }
    
    
    @Transactional(readOnly = false)
	public void updatePayState(String regOptId,String costType,Integer payState){
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
		if(null!=regOpt){
			logger.info("sendOperCostDataToHis==>updatePayState==>姓名："+regOpt.getName()+",costType："+costType+",payState:"+payState);
			
			if("1".equals(costType)){//麻醉费用单收费标志
				if(!Objects.equals(payState, regOpt.getDocPayState())){
					regOpt.setDocPayState(payState);
					basRegOptDao.updatePayState(regOpt);
				}
			}else{//手术核算单收费标志
				if(!Objects.equals(payState, regOpt.getNurPayState())){
					regOpt.setNurPayState(payState);
					basRegOptDao.updatePayState(regOpt);
				}
			}
		}
	}
    
    public List<SearchRegOptByLoginNameAndStateFormBean> searchRegOptByRoleTypeAndState(SearchConditionFormBean searchConditionFormBean) {
        String state = searchConditionFormBean.getState();
        if (state != null) {
            searchConditionFormBean.setState(searchConditionFormBean.getState().replace(" ", ""));
        }
        if (StringUtils.isBlank(searchConditionFormBean.getSort())) {
            searchConditionFormBean.setSort("operaDate");
        }
        if (StringUtils.isBlank(searchConditionFormBean.getOrderBy())) {
            searchConditionFormBean.setOrderBy("DESC");
        }
        String beid = null;
        if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
            beid = getBeid();
            searchConditionFormBean.setBeid(beid);
        } else {
            beid = searchConditionFormBean.getBeid();
        }
        String filter = getFilterStr(searchConditionFormBean);
        BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", beid);
        List<SearchRegOptByLoginNameAndStateFormBean> resultList = new ArrayList<SearchRegOptByLoginNameAndStateFormBean>();
        List<SearchRegOptByLoginNameAndStateFormBean> result = new ArrayList<SearchRegOptByLoginNameAndStateFormBean>();
        result = basRegOptDao.searchRegOptByRoleTypeAndState(filter, user == null ? "" : user.getRoleType(), searchConditionFormBean, beid);
        if (result != null && result.size() > 0) {

            String regOptStr = "";
            for (int i = 0; i < result.size(); i++) {
                regOptStr += "'" + result.get(i).getRegOptId() + "',";
            }
            regOptStr = regOptStr.substring(0, regOptStr.length() - 1);
            List<DocStateArrayFormbean> arrList = getDocumentState(user, searchConditionFormBean.getState(), regOptStr, beid);
            for (int i = 0; i < result.size(); i++) {
                SearchRegOptByLoginNameAndStateFormBean bean = result.get(i);
                String documentState = "完成";

                List<DocumentStateFormbean> stateFormbeanList = new ArrayList<DocumentStateFormbean>();
                for (DocStateArrayFormbean docStateArrayFormbean : arrList) {
                    DocumentStateFormbean stateFormbean = new DocumentStateFormbean();
                    List<DocumentStateFormbean> statList = docStateArrayFormbean.getDocStateList();
                    for (DocumentStateFormbean documentStateFormbean : statList) {
                        if (bean.getRegOptId().equals(documentStateFormbean.getRegOptId())) { // 只把未完成的存入的stateFormbean中
                            if (!"END".equals(documentStateFormbean.getState())) {
                                if (1 == docStateArrayFormbean.getIsNeed()) { // 1// 必须完成
                                	if("分娩镇痛同意书".equals(docStateArrayFormbean.getDocName()) && "男".equals(bean.getSex())){
                                		continue;
                                	}
                                    documentState = "未完成";
                                    stateFormbean.setState("未完成");
                                    stateFormbean.setName(docStateArrayFormbean.getDocName());
                                    stateFormbeanList.add(stateFormbean);
                                    break;
                                }
                            } /*
                             * else { stateFormbean.setState("完成"); }
                             */

                        }
                    }
                }
                bean.setDocumentState(documentState);
                bean.setDocumentStateList(stateFormbeanList);
                resultList.add(bean);
            }

        }

        return resultList;
    }

    public int searchRegOptTotalByRoleTypeAndState(SearchConditionFormBean searchConditionFormBean) {
        if (StringUtils.isEmpty(searchConditionFormBean.getSort())) {
            searchConditionFormBean.setSort("operaDate");
        }
        if (StringUtils.isEmpty(searchConditionFormBean.getOrderBy())) {
            searchConditionFormBean.setOrderBy("DESC");
        }
        String beid = null;
        if (StringUtils.isBlank(searchConditionFormBean.getBeid())) {
            beid = getBeid();
            searchConditionFormBean.setBeid(beid);
        } else {
            beid = searchConditionFormBean.getBeid();
        }
        String filter = getFilterStr(searchConditionFormBean);
        BasUser user = basUserDao.getByLoginName(searchConditionFormBean.getLoginName() != null ? searchConditionFormBean.getLoginName() : "", beid);
        int result = basRegOptDao.searchRegoptTotalByRoleTypeAndState(filter, user == null ? "" : user.getRoleType(), searchConditionFormBean, beid);
        return result;
    }
}
