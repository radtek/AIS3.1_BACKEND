package com.digihealth.anesthesia.operProceed.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.DiagnosedefFormBean;
import com.digihealth.anesthesia.basedata.formbean.OperDefFormBean;
import com.digihealth.anesthesia.basedata.formbean.SysCodeFormbean;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasMonitorConfigFreq;
import com.digihealth.anesthesia.basedata.po.BasOperroom;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.utils.DateUtils;
import com.digihealth.anesthesia.common.utils.Exceptions;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.common.utils.SysUtil;
import com.digihealth.anesthesia.common.web.BaseController;
import com.digihealth.anesthesia.doc.formbean.AnaesRecordFormBean;
import com.digihealth.anesthesia.doc.po.DocAnaesBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocAnaesPacuRec;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocExitOperSafeCheck;
import com.digihealth.anesthesia.doc.po.DocOperBeforeSafeCheck;
import com.digihealth.anesthesia.evt.formbean.EvtAnaesMethodFormBean;
import com.digihealth.anesthesia.evt.formbean.OperBodyFormBean;
import com.digihealth.anesthesia.evt.formbean.RegOptOperEgressFormBean;
import com.digihealth.anesthesia.evt.formbean.RegOptOperIoeventFormBean;
import com.digihealth.anesthesia.evt.formbean.RegOptOperMedicaleventFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.evt.formbean.SearchOperaPatrolRecordFormBean;
import com.digihealth.anesthesia.evt.po.EvtAnaesEvent;
import com.digihealth.anesthesia.evt.po.EvtCtlBreath;
import com.digihealth.anesthesia.evt.po.EvtElectDiogData;
import com.digihealth.anesthesia.evt.po.EvtParticipant;
import com.digihealth.anesthesia.evt.po.EvtRealAnaesMethod;
import com.digihealth.anesthesia.evt.service.EvtAnaesEventService;
import com.digihealth.anesthesia.evt.service.EvtParticipantService;
import com.digihealth.anesthesia.operProceed.core.MyConstants;
import com.digihealth.anesthesia.operProceed.datasync.MessageProcess;
import com.digihealth.anesthesia.operProceed.formbean.BasAnaesMonitorConfigFormBean;
import com.digihealth.anesthesia.operProceed.formbean.CmdMsg;
import com.digihealth.anesthesia.operProceed.formbean.CtlBreathDateFormBean;
import com.digihealth.anesthesia.operProceed.formbean.EndOperationFormBean;
import com.digihealth.anesthesia.operProceed.formbean.EnterRoomFormBean;
import com.digihealth.anesthesia.operProceed.formbean.FirstSpotFormBean;
import com.digihealth.anesthesia.operProceed.formbean.IntervalDataFormBean;
import com.digihealth.anesthesia.operProceed.formbean.LegendData;
import com.digihealth.anesthesia.operProceed.formbean.MonDataFormBean;
import com.digihealth.anesthesia.operProceed.formbean.MonitorData;
import com.digihealth.anesthesia.operProceed.formbean.MonitorDataFormBean;
import com.digihealth.anesthesia.operProceed.formbean.SeriesData;
import com.digihealth.anesthesia.operProceed.formbean.SeriesDataObj;
import com.digihealth.anesthesia.operProceed.formbean.XAxisData1;
import com.digihealth.anesthesia.operProceed.formbean.XAxisDataBean;
import com.digihealth.anesthesia.operProceed.formbean.YAxisData;
import com.digihealth.anesthesia.operProceed.po.BasAnaesMonitorConfig;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfig;
import com.digihealth.anesthesia.operProceed.po.BasMonitorConfigDefault;
import com.digihealth.anesthesia.operProceed.po.BasMonitorDisplay;
import com.digihealth.anesthesia.operProceed.po.BasMonitorFreqChange;
import com.digihealth.anesthesia.operProceed.po.BasPacuRecMonitorConfig;
import com.digihealth.anesthesia.pacu.datasync.MsgProcess;
import com.digihealth.anesthesia.sysMng.formbean.UserInfoFormBean;
import com.digihealth.anesthesia.sysMng.po.BasDictItem;
import com.google.common.base.Objects;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@org.springframework.stereotype.Controller
@RequestMapping("/operCtl")
@Api(value = "MyOperationDataController", description = "术中总控处理类")
public class MyOperationDataController extends BaseController {

	public static int SYMBOL_OBSDATA = 12;
	public static int SYMBOL_PRINTDATA = 11;

	public static String O2_EVENT_ID = "91001";

	public static String RESP_EVENT_ID = "100001";

	public static String NP_TEMP_POSITION_RT = "30008"; // 鼻咽温实时数据位置体温
	public static String NP_TEMP_POSITION_LINE = "31008"; // 鼻咽温图形位置体温

	public static String TEMP_POSITION_RT = "30016"; // 体温实时数据位置体温
	public static String TEMP_POSITION_LINE = "31016"; // 体温图形位置体温

	public static String RE_TEMP_POSITION_RT = "30010"; // 直肠温实时数据位置体温
	public static String RE_TEMP_POSITION_LINE = "31010"; // 直肠温图形位置体温

	public static String CVP_MEAN_POSITION_RT = "30011"; // 中心静脉压 实时数据位置
	public static String CVP_MEAN_POSITION_LINE = "31011"; // 中心静脉压 图形位置

	
	/**
	 * 描点数据和表格数据(历史)，无需发送采集数据模块 如果是已经修改的值，在具体的点上面增加flag
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getobsDataBase")
	@ResponseBody
	@ApiOperation(value = "描点历史数据查询", httpMethod = "POST", notes = "描点历史数据查询")
	public Map getobsDataBase(@ApiParam(name = "params", value = "参数") @RequestBody MonitorDataFormBean formBean) {
		logger.info("----------------start getobsDataBase------------------------");
		Map map = new HashMap();
		LegendData legend = new LegendData();
		List<String> legendData = new ArrayList<String>();

		List<XAxisData1> xAxis = new ArrayList<XAxisData1>();
		XAxisData1 xaisData = null;
		List<XAxisDataBean> data = new ArrayList<XAxisDataBean>();

		// 建造yAxis数据
		List<YAxisData> yAxis = new ArrayList<YAxisData>();
		YAxisData yd = null;
        yd = new YAxisData();
        yd.setType("value");
        yd.setName("bpm");
        yd.setOrder(1);
        yAxis.add(yd);
        yd = new YAxisData();
        yd.setType("value");
        yd.setName("℃");
        yd.setOrder(2);
        yAxis.add(yd);
        yd = new YAxisData();
        yd.setType("value");
        yd.setName("mmHg");
        yd.setOrder(3);
        yAxis.add(yd);
        Collections.sort(yAxis);

		List<SeriesData> series = new ArrayList<SeriesData>();
		SeriesData seriesdata = null;
		List<SeriesDataObj> da = null;
		SeriesDataObj obj = null;
		Map<String, SeriesData> seriesMap = new HashMap<String, SeriesData>();

		// 获取需要显示的数据
		String regOptId = formBean.getRegOptId();
		Integer position = 0;
		String beid = formBean.getBeid();
		if (StringUtils.isBlank(beid))
		{
		    beid = getBeid();
		    formBean.setBeid(beid);
		}

		BaseInfoQuery baseQuery = new BaseInfoQuery();
		baseQuery.setRegOptId(regOptId);
		baseQuery.setPosition(position + "");
		baseQuery.setEnable("1");
		List<BasAnaesMonitorConfigFormBean> anaesLists = new ArrayList<BasAnaesMonitorConfigFormBean>();
		
		if(Objects.equal(2, formBean.getDocType())){//如果是进入pacu则获取pacu对应的显示指标项
			anaesLists = basPacuRecMonitorConfigService.finaAnaesList(baseQuery);
		}else{
			anaesLists = basAnaesMonitorConfigService.finaAnaesList(baseQuery);
		}
		
		List<String> observeIds = new ArrayList<String>();

		if (null != anaesLists && anaesLists.size() > 0) {
			for (BasAnaesMonitorConfigFormBean bean : anaesLists) {
				String observeId = bean.getEventId();
				// 获取显示项需要的observeIds
				observeIds.add(observeId);
				String observeName = bean.getEventName();
				String color = bean.getColor();// 对应图标
				String icon = bean.getIconId();// 对应颜色
				String svg = basIconSvgService.getPathByIcon(bean.getIconId(), beid);
				String units = bean.getUnits(); // 默认单位
				Float max = bean.getMax(); // max
				Float min = bean.getMin(); // min
				legendData.add(observeName);

				// ABP_HR，CVP，℃
				if (observeId.equals(NP_TEMP_POSITION_RT) || observeId.equals(RE_TEMP_POSITION_RT) || observeId.equals(NP_TEMP_POSITION_LINE) || observeId.equals(RE_TEMP_POSITION_LINE) || observeId.equals(TEMP_POSITION_RT) || observeId.equals(TEMP_POSITION_LINE)) { // 如果是温度，则y轴为2
					seriesMap.put(observeId, new SeriesData(observeId, observeName, "line", new ArrayList<SeriesDataObj>(), icon, svg, 1, MyObserveDataController.SYMBOL_OBSDATA, color, units, max, min));
				} else if (observeId.equals(CVP_MEAN_POSITION_RT) || observeId.equals(CVP_MEAN_POSITION_LINE)) { // cvp,则y轴为1
					seriesMap.put(observeId, new SeriesData(observeId, observeName, "line", new ArrayList<SeriesDataObj>(), icon, svg, 2, MyObserveDataController.SYMBOL_OBSDATA, color, units, max, min));
				} else {// abp_hp等
					seriesMap.put(observeId, new SeriesData(observeId, observeName, "line", new ArrayList<SeriesDataObj>(), icon, svg, 0, MyObserveDataController.SYMBOL_OBSDATA, color, units, max, min));
				}
			}
		}

		List<BasMonitorDisplay> monitorList = basMonitorDisplayService.getobsDataBase(formBean, observeIds);

		DocAnaesRecord anaesRecord = docAnaesRecordService.getAnaesRecord(regOptId);
        String docId = "";
        if( null != anaesRecord ){
            docId = anaesRecord.getAnaRecordId();
        }
        SearchFormBean searchBean = new SearchFormBean();
        searchBean.setDocId(docId);
        List<EvtCtlBreath> ctlBreathList = evtCtlBreathService.searchCtlBreathList(searchBean);
        CtlBreathDateFormBean cbFormBean = null;
        List<CtlBreathDateFormBean> cbList = new ArrayList<CtlBreathDateFormBean>();
        
        if(null != ctlBreathList && ctlBreathList.size()>0){
            for (int i = 0; i < ctlBreathList.size(); i++) {
                EvtCtlBreath ctlBreath = ctlBreathList.get(i);
                //String curState = ctlBreath.getCurrentState();
                Integer type = ctlBreath.getType();
                if(i == 0){
                    cbFormBean = new CtlBreathDateFormBean();
                    cbFormBean.setStartTime(ctlBreath.getStartTime());
                    cbFormBean.setType(type);
                    List<SysCodeFormbean> scList = basSysCodeService.searchSysCodeByGroupIdAndCodeName("breath_event", type + "", beid);
                    if(null != scList && scList.size()>0){
                        String codeValue = scList.get(0).getCodeValue();
                        cbFormBean.setCodeValue(codeValue);
                        cbFormBean.setCodeSvg(basIconSvgService.getPathByIcon(codeValue, beid));
                    }
                    cbList.add(cbFormBean);
                }else if(i > 0){
                    cbFormBean = new CtlBreathDateFormBean();
                    cbFormBean.setStartTime(ctlBreath.getStartTime());
                    cbFormBean.setEndTime(cbList.get(cbList.size()-1).getStartTime());
                    cbFormBean.setType(type);
                    List<SysCodeFormbean> scList = basSysCodeService.searchSysCodeByGroupIdAndCodeName("breath_event", type + "", beid);
                    if(null != scList && scList.size()>0){
                        String codeValue = scList.get(0).getCodeValue();
                        cbFormBean.setCodeValue(codeValue);
                        cbFormBean.setCodeSvg(basIconSvgService.getPathByIcon(codeValue, beid));
                    }
                    cbList.add(cbFormBean);
                }
                    
            }
        }

		Date t = new Date(1L);
		if (null != monitorList && monitorList.size() > 0) {
			for (int i = 0; i < monitorList.size(); i++) {
				BasMonitorDisplay md = monitorList.get(i);
				String key = md.getObserveId();
				Date time = md.getTime();

				if (t.getTime() != time.getTime()) {
					t = time;
					XAxisDataBean bean = new XAxisDataBean();
					bean.setValue(t);
					Integer freq = md.getIntervalTime();
					bean.setFreq(freq);
					data.add(bean);
				}
				// series
				if (!seriesMap.containsKey(key)) {
					logger.info("------------------没有当前key" + key + "------------------------");
				} else {
					seriesdata = seriesMap.get(key);
					da = seriesdata.getData();
					seriesdata.setType("line");
					seriesdata.setName(md.getObserveName());

					// 设置指定对应的y轴
					if (NP_TEMP_POSITION_LINE.equals(md.getObserveId()) || NP_TEMP_POSITION_RT.equals(md.getObserveId()) || RE_TEMP_POSITION_LINE.equals(md.getObserveId()) || RE_TEMP_POSITION_RT.equals(md.getObserveId()) || TEMP_POSITION_RT.equals(md.getObserveId()) || TEMP_POSITION_LINE.equals(md.getObserveId())) {
						seriesdata.setyAxisIndex(1);
					} else if (CVP_MEAN_POSITION_LINE.equals(md.getObserveId()) || CVP_MEAN_POSITION_RT.equals(md.getObserveId())) {
						seriesdata.setyAxisIndex(2);
					} else {
						seriesdata.setyAxisIndex(0);
					}

					// 增加呼吸事件图标的判断(呼吸机和麻醉机的observeId)
					if (RESP_EVENT_ID.equals(md.getObserveId())) {
						if (null != cbList && cbList.size() > 0) {
							int flag = -1;
							for (CtlBreathDateFormBean cb : cbList) {
								Date st = cb.getStartTime();
								Date et = cb.getEndTime();
								// logger.info("getobsData----st="+st+",et="+et+",time="+time);
								if (null != et) {
									if (time.getTime() >= st.getTime() && time.getTime() < et.getTime()) {
										obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId(), cb.getCodeValue(), md.getValue() != null ? cb.getCodeSvg() : "");
										flag = 0;
										break;
									}
								} else {
									if (time.getTime() >= st.getTime()) {
										obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId(), cb.getCodeValue(), md.getValue() != null ? cb.getCodeSvg() : "");
										flag = 0;
										break;
									}
								}
							}
							if (flag == -1) {
								obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId());
							}
						} else {
							obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId());
						}
					} else {
						obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId());
					}
					// 如果 amendFlag为2，则设值
					if (null != md.getAmendFlag()) {
						if (md.getAmendFlag() == 2) {
							obj.setAmendFlag(md.getAmendFlag());
						}
					}
					da.add(obj);
					seriesdata.setData(da);
					seriesMap.put(key, seriesdata);
				}

			}

		} else {
			// 无采集数据
			logger.info("----------无采集数据----------------");
		}

		// 循环seriesMap中的数据
		for (String key : seriesMap.keySet()) {
			SeriesData sd = seriesMap.get(key);
			series.add(sd);
		}

		// 添加times到x轴
		xaisData = new XAxisData1();
		xaisData.setData(data);
		xAxis.add(xaisData);

		// 获取总数
		int total = basMonitorDisplayService.getobsDataTotalBase(formBean, observeIds);

		// 获取修改频率的List
		List<BasMonitorFreqChange> monitorFreqChanges = basMonitorFreqChangeService.getMonitorFreqChanges(regOptId);

		String f = "";
		Integer freq = 0;
		
		if(Objects.equal(2, formBean.getDocType())){
			BasDictItem dictItem = basDictItemService.getListByGroupIdandCodeName("pacu_freq", "复苏室显示频率",beid);
			if(null != dictItem){
				freq = Integer.valueOf(dictItem.getCodeValue());
			}
		}else{
			// 获取当前频率
			String currentModel = basRegOptService.getCurrentModel(regOptId);
			BasMonitorConfigFreq monitorFreq = basMonitorConfigFreqService.searchMonitorFreqByType(currentModel,regOptId);
			if (monitorFreq != null) {
				f = monitorFreq.getValue();
				freq = Integer.valueOf(f);
			}
		}
		
		// 获取最近点
		BasMonitorDisplay md = basMonitorDisplayService.findLastestMonitorDisplay(regOptId);

		map.put("xAxis", xAxis);
		map.put("yAxis", yAxis);
		map.put("series", series);
		// 有显示项 则增加legend数据，传递给前端
		legend.setData(legendData);
		map.put("legend", legend);
		// 获取数据total
		map.put("total", total);
		map.put("monitorFreqChanges", monitorFreqChanges);
		map.put("freq", freq);
		map.put("md", md);

		// 计算偏移量
		Date inTime = formBean.getInTime();

		long offset = 0;// 偏移量
		BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
		if (null == regOpt) {
			logger.info("getobsData----regOpt手术为null！--------------");
			map.put("resultCode", "10000000");
			map.put("resultMessage", "查询数据有误，请与系统管理员联系!");
			return map;
		} else {
			Date operTime = regOpt.getOperTime();
			if (null == operTime) {
				logger.info("getobsData----operTime手术时间为null！--------------");
				map.put("resultCode", "10000000");
				map.put("resultMessage", "查询数据有误，请与系统管理员联系!");
				return map;
			} else {
				BasMonitorFreqChange monitorFreqChange = basMonitorFreqChangeService.selectFirstChangeTime(regOptId, DateUtils.formatDateTime(inTime));
				if (null == monitorFreqChange) {
					map.put("changeFreqTime", null);
				} else {
					map.put("changeFreqTime", monitorFreqChange.getTime());
				}
				long time = inTime.getTime() - operTime.getTime();
				if (time == 0) {
					map.put("offset", offset);
				} else if (time < 0) { // 修改后的入室时间小于第一次的手术命令开始时间
					BasMonitorDisplay firstMd = basMonitorDisplayService.findMonitorDisplaybyTime(regOptId, SysUtil.getTimeFormat(operTime));
					long operFreq = 0;
					if (null != firstMd) {
						operFreq = firstMd.getFreq();
						offset = (Math.abs(time) % (operFreq * 1000)) / 1000;// 取绝对值取mod
						map.put("offset", offset);
					} else {
						logger.info("getobsData----operTime查询出来的MonitorDisplay为null！--------------");
						map.put("resultCode", "10000000");
						map.put("resultMessage", "查询数据有误，请与系统管理员联系!");
						return map;
					}
				} else { // time > 0
					BasMonitorDisplay curMd = basMonitorDisplayService.findMonitorDisplayByInTimeLimit1(regOptId, inTime);
					if (null != curMd) {
						Date curTime = curMd.getTime();
						Integer operFreq = curMd.getFreq();
						Integer intervalTime = curMd.getIntervalTime();
						if (operFreq.intValue() != intervalTime.intValue()) {
							long myTime = ((curTime.getTime() + intervalTime * 1000) - inTime.getTime()) / 1000;
							if (myTime > 0) { // 1、如果最近点+intervalTime大于intime，则 offset =(curTime+interval_time)-inTime
								map.put("offset", myTime);
							} else if (myTime == 0) {
								map.put("offset", 0);
							} else { // curTime+intervalTime 必须大于 inTime ，不然数据有误
								logger.info("getobsData----获取到的curTime+intervalTime小于修改的inTime，数据有误--------------");
								map.put("resultCode", "10000000");
								map.put("resultMessage", "查询数据有误，请与系统管理员联系!");
								return map;
							}
						} else {
							long cTime = (inTime.getTime() - curTime.getTime()) / 1000; // offset = operFreq - (intime -curTime) ;
							if (cTime == 0) {
								map.put("offset", 0);
							} else {
								offset = operFreq - cTime;// 当前频率-（入室时间-当前最近点时间）
								logger.info("offset===" + offset + ",cTime==" + cTime + ",inTime=" + inTime + ",curTime==" + curTime);
								map.put("offset", offset);
							}
						}
					} else {
						logger.info("getobsData----inTime查询出来的最近的右边的MonitorDisplay为null！--------------");
						map.put("resultCode", "10000000");
						map.put("resultMessage", "查询数据有误，请与系统管理员联系!");
						return map;
					}
				}
			}
		}

		map.put("resultCode", "1");
		map.put("resultMessage", "操作成功！");

		logger.info("getobsData---allData===" + JsonType.jsonType(map) + ",inTime==" + formBean.getInTime());
		logger.info("------------------end getobsData------------------------");
		return map;
	}
	
	
	/**
     * 获取新点
     * 
     * @param formBean
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping("/getobsDataNewBase")
    @ResponseBody
    @ApiOperation(value = "获取新点", httpMethod = "POST", notes = "获取新点")
    public Map getobsDataNewBase(@ApiParam(name = "params", value = "参数") @RequestBody MonitorDataFormBean formBean) {
        logger.info("----------------start getobsDataNew------------------------");
        Map map = new HashMap();
        if (StringUtils.isBlank(formBean.getBeid())) {
            formBean.setBeid(getBeid());
        }
        Date inTime = formBean.getInTime();
        Date startTime = formBean.getStartTime();
        // Date endTime = formBean.getEndTime();
        if (null == inTime) {
            map.put("resultCode", "30000001");
            map.put("resultMessage", "入室时间不能为空！");
            return map;
        }
        if (null == startTime) {
            map.put("resultCode", "30000001");
            map.put("resultMessage", "开始时间不能为空！");
            return map;
        }

        LegendData legend = new LegendData();
        List<String> legendData = new ArrayList<String>();

        List<XAxisData1> xAxis = new ArrayList<XAxisData1>();
        XAxisData1 xaisData = null;
        List<XAxisDataBean> data = new ArrayList<XAxisDataBean>();

        // 建造yAxis数据
        List<YAxisData> yAxis = new ArrayList<YAxisData>();
        YAxisData yd = null;
        yd = new YAxisData();
        yd.setType("value");
        yd.setName("bpm");
        yd.setOrder(1);
        yAxis.add(yd);
        yd = new YAxisData();
        yd.setType("value");
        yd.setName("℃");
        yd.setOrder(2);
        yAxis.add(yd);
        yd = new YAxisData();
        yd.setType("value");
        yd.setName("mmHg");
        yd.setOrder(3);
        yAxis.add(yd);
        Collections.sort(yAxis);

        List<SeriesData> series = new ArrayList<SeriesData>();
        SeriesData seriesdata = null;
        List<SeriesDataObj> da = null;
        SeriesDataObj obj = null;
        Map<String, SeriesData> seriesMap = new HashMap<String, SeriesData>();

        // 获取需要显示的数据
        String regOptId = formBean.getRegOptId();
        Integer position = 0;
        String beid = formBean.getBeid();
        if (StringUtils.isBlank(beid))
        {
            beid = getBeid();
            formBean.setBeid(getBeid());
        }

        BaseInfoQuery baseQuery = new BaseInfoQuery();
        baseQuery.setRegOptId(regOptId);
        baseQuery.setPosition(position + "");
        baseQuery.setEnable("1");
        List<BasAnaesMonitorConfigFormBean> anaesLists = new ArrayList<BasAnaesMonitorConfigFormBean>();
		
		if(Objects.equal(2, formBean.getDocType())){//如果是进入pacu则获取pacu对应的显示指标项
			anaesLists = basPacuRecMonitorConfigService.finaAnaesList(baseQuery);
		}else{
			anaesLists = basAnaesMonitorConfigService.finaAnaesList(baseQuery);
		}
        
        List<String> observeIds = new ArrayList<String>();

        if (null != anaesLists && anaesLists.size() > 0) {
            for (BasAnaesMonitorConfigFormBean bean : anaesLists) {
                String observeId = bean.getEventId();
                // 获取显示项需要的observeIds
                observeIds.add(observeId);
                String observeName = bean.getEventName();
                String color = bean.getColor();// 对应颜色
                String icon = bean.getIconId();// 对应图标
                String svg = basIconSvgService.getPathByIcon(icon, beid);
                String units = bean.getUnits(); // 默认单位
                Float max = bean.getMax();
                Float min = bean.getMin();
                legendData.add(observeName);

                // ABP_HR，CVP，℃
                if (observeId.equals(NP_TEMP_POSITION_RT) || observeId.equals(RE_TEMP_POSITION_RT) || observeId.equals(NP_TEMP_POSITION_LINE) || observeId.equals(RE_TEMP_POSITION_LINE) || observeId.equals(TEMP_POSITION_RT) || observeId.equals(TEMP_POSITION_LINE)) { // 如果是温度，则y轴为2
                    seriesMap.put(observeId, new SeriesData(observeId, observeName, "line", new ArrayList<SeriesDataObj>(), icon, svg, 1, MyObserveDataController.SYMBOL_OBSDATA, color, units, max, min));
                } else if (observeId.equals(CVP_MEAN_POSITION_RT) || observeId.equals(CVP_MEAN_POSITION_LINE)) { // cvp,则y轴为1
                    seriesMap.put(observeId, new SeriesData(observeId, observeName, "line", new ArrayList<SeriesDataObj>(), icon, svg, 2, MyObserveDataController.SYMBOL_OBSDATA, color, units, max, min));
                } else {// abp_hp等,y轴为0
                    seriesMap.put(observeId, new SeriesData(observeId, observeName, "line", new ArrayList<SeriesDataObj>(), icon, svg, 0, MyObserveDataController.SYMBOL_OBSDATA, color, units, max, min));
                }

            }
        }

        List<BasMonitorDisplay> monitorList = basMonitorDisplayService.getobsDataNew4Base(formBean, observeIds); // 增加特殊处理逻辑
        
        SearchFormBean searchBean = new SearchFormBean();
        DocAnaesRecord anaesRecord = docAnaesRecordService.getAnaesRecord(regOptId);
        String docId = "";
        if( null != anaesRecord ){
            docId = anaesRecord.getAnaRecordId();
        }
        searchBean.setDocId(docId);
        
        List<EvtCtlBreath> ctlBreathList = evtCtlBreathService.searchCtlBreathList(searchBean);
        int type = 0;
        if(null != ctlBreathList && ctlBreathList.size()>0){ 
            type = ctlBreathList.get(0).getType();
        }
        List<SysCodeFormbean> sysCodeList = basSysCodeService.searchSysCodeByGroupId("breath_event", formBean.getBeid());
        String code_value_1 = "", svg_value_1 = "";
        String code_value_2 = "", svg_value_2 = "";
        String code_value_3 = "", svg_value_3 = "";
        if(null != sysCodeList && sysCodeList.size()>0){
            for (SysCodeFormbean sysCodeFormbean : sysCodeList) {
                String codeName = sysCodeFormbean.getCodeName();
                if("1".equals(codeName)){
                    code_value_1 = sysCodeFormbean.getCodeValue();
                    svg_value_1 = basIconSvgService.getPathByIcon(code_value_1, beid);
                }else if("2".equals(codeName)){
                    code_value_2 = sysCodeFormbean.getCodeValue();
                    svg_value_2 = basIconSvgService.getPathByIcon(code_value_2, beid);
                }else if("3".equals(codeName)){
                    code_value_3 = sysCodeFormbean.getCodeValue();
                    svg_value_3 = basIconSvgService.getPathByIcon(code_value_3, beid);
                }
            }
        }

        Date t = new Date(1L);
        if (null != monitorList && monitorList.size() > 0) {
            for (int i = 0; i < monitorList.size(); i++) {
                BasMonitorDisplay md = monitorList.get(i);
                String key = md.getObserveId();
                Date time = md.getTime();
                if (t.getTime() != time.getTime()) {
                    t = time;
                    // data.add(t);
                    XAxisDataBean bean = new XAxisDataBean();
                    bean.setValue(t);
                    Integer freq = md.getIntervalTime();
                    bean.setFreq(freq);
                    data.add(bean);
                }
                // series
                if (!seriesMap.containsKey(key)) {
                    logger.info("------------------没有当前key" + key + "------------------------");
                } else {
                    seriesdata = seriesMap.get(key);
                    da = seriesdata.getData();
                    seriesdata.setType("line");
                    seriesdata.setName(md.getObserveName());
                    // 设置指定对应的y轴
                    if (NP_TEMP_POSITION_LINE.equals(md.getObserveId()) || NP_TEMP_POSITION_RT.equals(md.getObserveId()) || RE_TEMP_POSITION_LINE.equals(md.getObserveId()) || RE_TEMP_POSITION_RT.equals(md.getObserveId()) || TEMP_POSITION_RT.equals(md.getObserveId()) || TEMP_POSITION_LINE.equals(md.getObserveId())) {
                        seriesdata.setyAxisIndex(1);
                    } else if (CVP_MEAN_POSITION_LINE.equals(md.getObserveId()) || CVP_MEAN_POSITION_RT.equals(md.getObserveId())) {
                        seriesdata.setyAxisIndex(2);
                    } else {
                        seriesdata.setyAxisIndex(0);
                    }
                    // seriesdata.setSymbolSize(8);
                    if (RESP_EVENT_ID.equals(md.getObserveId())) {
                        if (1 == type) {
                            obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId(), code_value_1, svg_value_1);
                        } else if (2 == type) {
                            obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId(), code_value_2, svg_value_2);
                        } else if (3 == type) {
                            obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId(), code_value_3, svg_value_3);
                        } else {
                            obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId());
                        }
                    } else {
                        obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId());
                    }

                    da.add(obj);
                    seriesdata.setData(da);
                    seriesMap.put(key, seriesdata);
                }

            }

        } else {
            // 无采集数据
            logger.info("----------无采集数据----------------");
        }

        // 循环seriesMap中的数据
        for (String key : seriesMap.keySet()) {
            SeriesData sd = seriesMap.get(key);
            series.add(sd);
        }

        // 添加times到x轴
        xaisData = new XAxisData1();
        xaisData.setData(data);
        xAxis.add(xaisData);

        // 获取总数
        int total = basMonitorDisplayService.getobsDataTotalBase(formBean, observeIds);

       
        String f = "";
		Integer freq = 0;
		
		if(Objects.equal(2, formBean.getDocType())){
			BasDictItem dictItem = basDictItemService.getListByGroupIdandCodeName("pacu_freq", "复苏室显示频率",beid);
			if(null != dictItem){
				freq = Integer.valueOf(dictItem.getCodeValue());
			}
		}else{
			// 获取当前频率
			String currentModel = basRegOptService.getCurrentModel(regOptId);
			BasMonitorConfigFreq monitorFreq = basMonitorConfigFreqService.searchMonitorFreqByType(currentModel,regOptId);
			if (monitorFreq != null) {
				f = monitorFreq.getValue();
				freq = Integer.valueOf(f);
			}
		}
		// 获取修改频率的List
        BasMonitorFreqChange monitorFreqChange = basMonitorFreqChangeService.selectFirstChangeTime(regOptId, DateUtils.formatDateTime(inTime));
        if (null == monitorFreqChange) {
            map.put("changeFreqTime", null);
        } else {
            map.put("changeFreqTime", monitorFreqChange.getTime());
        }

        map.put("xAxis", xAxis);
        map.put("yAxis", yAxis);
        map.put("series", series);
        // 有显示项 则增加legend数据，传递给前端
        legend.setData(legendData);
        map.put("legend", legend);
        // 获取数据total
        map.put("total", total);
        map.put("freq", freq);

        map.put("resultCode", "1");
        map.put("resultMessage", "操作成功！");

        logger.info("------------------end getobsDataNew------------------------");
        return map;
    }
	
	
	
	/**
	 * 页面再次打开，间隔点的处理
	 * 
	 * @param formbean
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/getIntervalObsDataBase")
	@ResponseBody
	@ApiOperation(value = "页面再次打开，间隔点的处理", httpMethod = "POST", notes = "页面再次打开，间隔点的处理")
    public Map getIntervalObsDataBase(@ApiParam(name = "formbean", value = "参数")@RequestBody IntervalDataFormBean formbean) {
        logger.info("----------------start getIntervalObsData------------------------");
        ResponseValue res = new ResponseValue();
        Map map = new HashMap();
        if (formbean != null) {
            basMonitorDisplayService.getIntervalObsDataQNZZY(formbean); // 获取中间断的点

            LegendData legend = new LegendData();
            List<String> legendData = new ArrayList<String>();

            List<XAxisData1> xAxis = new ArrayList<XAxisData1>();
            XAxisData1 xaisData = null;
            List<XAxisDataBean> data = new ArrayList<XAxisDataBean>();

            // 建造yAxis数据
            List<YAxisData> yAxis = new ArrayList<YAxisData>();
            YAxisData yd = null;
            yd = new YAxisData();
            yd.setType("value");
            yd.setName("bpm");
            yd.setOrder(1);
            yAxis.add(yd);
            yd = new YAxisData();
            yd.setType("value");
            yd.setName("℃");
            yd.setOrder(2);
            yAxis.add(yd);
            yd = new YAxisData();
            yd.setType("value");
            yd.setName("mmHg");
            yd.setOrder(3);
            yAxis.add(yd);
            Collections.sort(yAxis);

            List<SeriesData> series = new ArrayList<SeriesData>();
            SeriesData seriesdata = null;
            List<SeriesDataObj> da = null;
            SeriesDataObj obj = null;
            Map<String, SeriesData> seriesMap = new HashMap<String, SeriesData>();

            // 获取需要显示的数据
            String regOptId = formbean.getRegOptId();
            Integer position = 0;
            String beid = formbean.getBeid();
            if (StringUtils.isBlank(beid))
            {
                beid = getBeid();
                formbean.setBeid(beid);
            }

            BaseInfoQuery baseQuery = new BaseInfoQuery();
            baseQuery.setRegOptId(regOptId);
            baseQuery.setPosition(position + "");
            baseQuery.setEnable("1");
            
            List<BasAnaesMonitorConfigFormBean> anaesLists = new ArrayList<BasAnaesMonitorConfigFormBean>();
    		
    		if(Objects.equal(2, formbean.getDocType())){//如果是进入pacu则获取pacu对应的显示指标项
    			anaesLists = basPacuRecMonitorConfigService.finaAnaesList(baseQuery);
    		}else{
    			anaesLists = basAnaesMonitorConfigService.finaAnaesList(baseQuery);
    		}
            
            List<String> observeIds = new ArrayList<String>();

            if (null != anaesLists && anaesLists.size() > 0) {
                for (BasAnaesMonitorConfigFormBean bean : anaesLists) {
                    String observeId = bean.getEventId();
                    // 获取显示项需要的observeIds
                    observeIds.add(observeId);
                    String observeName = bean.getEventName();
                    String color = bean.getColor();// 对应图标
                    String icon = bean.getIconId();// 对应颜色
                    String svg = basIconSvgService.getPathByIcon(icon, beid);
                    String units = bean.getUnits(); // 默认单位
                    Float max = bean.getMax(); // max
                    Float min = bean.getMin(); // min
                    legendData.add(observeName);
                    // ABP_HR，CVP，℃
                    if (observeId.equals(NP_TEMP_POSITION_RT) || observeId.equals(RE_TEMP_POSITION_RT) || observeId.equals(NP_TEMP_POSITION_LINE) || observeId.equals(RE_TEMP_POSITION_LINE) || observeId.equals(TEMP_POSITION_RT) || observeId.equals(TEMP_POSITION_LINE)) { // 如果是温度，则y轴为2
                        seriesMap.put(observeId, new SeriesData(observeId, observeName, "line", new ArrayList<SeriesDataObj>(), icon, svg, 1, MyObserveDataController.SYMBOL_OBSDATA, color, units, max, min));
                    } else if (observeId.equals(CVP_MEAN_POSITION_RT) || observeId.equals(CVP_MEAN_POSITION_LINE)) { // cvp,则y轴为1
                        seriesMap.put(observeId, new SeriesData(observeId, observeName, "line", new ArrayList<SeriesDataObj>(), icon, svg, 2, MyObserveDataController.SYMBOL_OBSDATA, color, units, max, min));
                    } else {// abp_hp等,y轴为0
                        seriesMap.put(observeId, new SeriesData(observeId, observeName, "line", new ArrayList<SeriesDataObj>(), icon, svg, 0, MyObserveDataController.SYMBOL_OBSDATA, color, units, max, min));
                    }
                }
            }
            MonitorDataFormBean mdFormBean = new MonitorDataFormBean();
            BeanUtils.copyProperties(formbean, mdFormBean);
            List<BasMonitorDisplay> monitorList = basMonitorDisplayService.getobsDataBase(mdFormBean, observeIds);

            DocAnaesRecord anaesRecord = docAnaesRecordService.getAnaesRecord(regOptId);
            String docId = "";
            if( null != anaesRecord ){
                docId = anaesRecord.getAnaRecordId();
            }
            SearchFormBean searchBean = new SearchFormBean();
            searchBean.setDocId(docId);
            List<EvtCtlBreath> ctlBreathList = evtCtlBreathService.searchCtlBreathList(searchBean);
            CtlBreathDateFormBean cbFormBean = null;
            List<CtlBreathDateFormBean> cbList = new ArrayList<CtlBreathDateFormBean>();
            
            if(null != ctlBreathList && ctlBreathList.size()>0){
                for (int i = 0; i < ctlBreathList.size(); i++) {
                    EvtCtlBreath ctlBreath = ctlBreathList.get(i);
                    //String curState = ctlBreath.getCurrentState();
                    Integer type = ctlBreath.getType();
                    if(i == 0){
                        cbFormBean = new CtlBreathDateFormBean();
                        cbFormBean.setStartTime(ctlBreath.getStartTime());
                        cbFormBean.setType(type);
                        List<SysCodeFormbean> scList = basSysCodeService.searchSysCodeByGroupIdAndCodeName("breath_event", type + "", beid);
                        if(null != scList && scList.size()>0){
                            String codeValue = scList.get(0).getCodeValue();
                            cbFormBean.setCodeValue(codeValue);
                            cbFormBean.setCodeSvg(basIconSvgService.getPathByIcon(codeValue, beid));
                        }
                        cbList.add(cbFormBean);
                    }else if(i > 0){
                        cbFormBean = new CtlBreathDateFormBean();
                        cbFormBean.setStartTime(ctlBreath.getStartTime());
                        cbFormBean.setEndTime(cbList.get(cbList.size()-1).getStartTime());
                        cbFormBean.setType(type);
                        List<SysCodeFormbean> scList = basSysCodeService.searchSysCodeByGroupIdAndCodeName("breath_event", type + "", beid);
                        if(null != scList && scList.size()>0){
                            String codeValue = scList.get(0).getCodeValue();
                            cbFormBean.setCodeValue(codeValue);
                            cbFormBean.setCodeSvg(basIconSvgService.getPathByIcon(codeValue, beid));
                        }
                        cbList.add(cbFormBean);
                    }
                        
                }
            }

            Date t = new Date(1L);
            if (null != monitorList && monitorList.size() > 0) {
                for (int i = 0; i < monitorList.size(); i++) {
                    BasMonitorDisplay md = monitorList.get(i);
                    String key = md.getObserveId();
                    Date time = md.getTime();

                    if (t.getTime() != time.getTime()) {
                        t = time;
                        XAxisDataBean bean = new XAxisDataBean();
                        bean.setValue(t);
                        Integer freq = md.getIntervalTime();
                        bean.setFreq(freq);
                        data.add(bean);
                    }
                    // series
                    if (!seriesMap.containsKey(key)) {
                        logger.info("------------------没有当前key" + key + "------------------------");
                    } else {
                        seriesdata = seriesMap.get(key);
                        da = seriesdata.getData();
                        seriesdata.setType("line");
                        seriesdata.setName(md.getObserveName());
                        // 设置指定对应的y轴
                        if (NP_TEMP_POSITION_LINE.equals(md.getObserveId()) || NP_TEMP_POSITION_RT.equals(md.getObserveId()) || RE_TEMP_POSITION_LINE.equals(md.getObserveId()) || RE_TEMP_POSITION_RT.equals(md.getObserveId()) || TEMP_POSITION_RT.equals(md.getObserveId()) || TEMP_POSITION_LINE.equals(md.getObserveId())) {
                            seriesdata.setyAxisIndex(1);
                        } else if (CVP_MEAN_POSITION_LINE.equals(md.getObserveId()) || CVP_MEAN_POSITION_RT.equals(md.getObserveId())) {
                            seriesdata.setyAxisIndex(2);
                        } else {
                            seriesdata.setyAxisIndex(0);
                        }
                        // seriesdata.setSymbolSize(8);

                        // 增加呼吸事件图标的判断
                        if (RESP_EVENT_ID.equals(md.getObserveId())) {
                            if (null != cbList && cbList.size() > 0) {
                                int flag = -1;
                                for (CtlBreathDateFormBean cb : cbList) {
                                    Date st = cb.getStartTime();
                                    Date et = cb.getEndTime();
                                    // logger.info("getobsData----st="+st+",et="+et+",time="+time);
                                    if (null != et) {
                                        if (time.getTime() >= st.getTime() && time.getTime() < et.getTime()) {
                                            obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId(), cb.getCodeValue(), cb.getCodeSvg());
                                            flag = 0;
                                            break;
                                        }
                                    } else {
                                        if (time.getTime() >= st.getTime()) {
                                            obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId(), cb.getCodeValue(), cb.getCodeSvg());
                                            flag = 0;
                                            break;
                                        }
                                    }
                                }
                                if (flag == -1) {
                                    obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId());
                                }
                            } else {
                                obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId());
                            }
                        } else {
                            obj = new SeriesDataObj(md.getId(), md.getValue(), md.getTime(), md.getObserveId());
                        }
                        // 如果 amendFlag为2，则设值
                        if (null != md.getAmendFlag()) {
                            if (md.getAmendFlag() == 2) {
                                obj.setAmendFlag(md.getAmendFlag());
                            }
                        }
                        da.add(obj);
                        seriesdata.setData(da);
                        seriesMap.put(key, seriesdata);
                    }

                }

            } else {
                // 无采集数据
                logger.info("----------无采集数据----------------");
            }

            // 循环seriesMap中的数据
            for (String key : seriesMap.keySet()) {
                SeriesData sd = seriesMap.get(key);
                series.add(sd);
            }

            // 添加times到x轴
            xaisData = new XAxisData1();
            xaisData.setData(data);
            xAxis.add(xaisData);

            // 获取总数
            int total = basMonitorDisplayService.getobsDataTotalBase(mdFormBean, observeIds);

            // 获取修改频率的List
            List<BasMonitorFreqChange> monitorFreqChanges = basMonitorFreqChangeService.getMonitorFreqChanges(regOptId);

            String f = "";
            Integer freq = 0;
            
            if(Objects.equal(2, formbean.getDocType())){
            	BasDictItem dictItem = basDictItemService.getListByGroupIdandCodeName("pacu_freq", "复苏室显示频率",beid);
            	if(null != dictItem){
            		freq =  SysUtil.strParseToInt(dictItem.getCodeValue());
            	}
            }else{
            	// 获取当前频率
                String currentModel = basRegOptService.getCurrentModel(regOptId);
                BasMonitorConfigFreq monitorFreq = basMonitorConfigFreqService.searchMonitorFreqByType(currentModel,regOptId);
            	if (monitorFreq != null) {
                    f = monitorFreq.getValue();
                    freq = Integer.valueOf(f);
                }
            }
            
            // 获取最近点
            BasMonitorDisplay md = basMonitorDisplayService.findLastestMonitorDisplay(regOptId);

            map.put("xAxis", xAxis);
            map.put("yAxis", yAxis);
            map.put("series", series);
            // 有显示项 则增加legend数据，传递给前端
            legend.setData(legendData);
            map.put("legend", legend);
            // 获取数据total
            map.put("total", total);
            map.put("monitorFreqChanges", monitorFreqChanges);
            map.put("freq", freq);
            map.put("md", md);

            map.put("resultCode", "1");
            map.put("resultMessage", "操作成功！");

        } else {
            map.put("resultCode", "70000000");
            map.put("resultMessage", Global.getRetMsg(res.getResultCode()));
        }

        logger.info("------------------end getIntervalObsData------------------------");
        return map;
    }
	
	
	
	/**
	 * 数字部分：获取历史数据，需分页
	 * 
	 * @param formbean
	 * @return
	 */
	@RequestMapping("/getmonDataBase")
	@ResponseBody
	@ApiOperation(value = "获取监测项分页历史数据", httpMethod = "POST", notes = "获取监测项分页历史数据")
	public String getmonDataBase(@ApiParam(name = "params", value = "参数") @RequestBody MonitorDataFormBean formBean) {
		logger.info("----------------start getmonData------------------------");
		ResponseValue res = new ResponseValue();
		String regOptId = formBean.getRegOptId();
		if (StringUtils.isBlank(regOptId)) {
			res.setResultCode("70000000");
			res.setResultMessage(Global.getRetMsg(res.getResultCode()));
			logger.info("----------------end getmonData------------------------");
			return res.getJsonStr();
		}

		BaseInfoQuery baseQuery = new BaseInfoQuery();
		baseQuery.setRegOptId(regOptId);
		baseQuery.setPosition("1");
		
		List<BasAnaesMonitorConfigFormBean> anaesLists = new ArrayList<BasAnaesMonitorConfigFormBean>();
		
		if(Objects.equal(2, formBean.getDocType())){//如果是进入pacu则获取pacu对应的显示指标项
			anaesLists = basPacuRecMonitorConfigService.finaAnaesList(baseQuery);
		}else{
			anaesLists = basAnaesMonitorConfigService.finaAnaesList(baseQuery);
		}
		List<String> observeIds = new ArrayList<String>();

		if (null != anaesLists && anaesLists.size() > 0) {
			for (BasAnaesMonitorConfigFormBean mc : anaesLists) {
				if (!observeIds.contains(mc.getEventId() + "")) {
					observeIds.add(mc.getEventId() + "");
				}
			}
		}
		List<BasMonitorDisplay> monitorList = basMonitorDisplayService.getobsDataBase(formBean, observeIds);
		Map<Date, List<BasMonitorDisplay>> map = new TreeMap<Date, List<BasMonitorDisplay>>();
		List<BasMonitorDisplay> mds = null;
		if (null != monitorList && monitorList.size() > 0) {
			for (BasMonitorDisplay md : monitorList) {
				Date time = md.getTime();
				if (!map.containsKey(time)) {
					mds = new ArrayList<BasMonitorDisplay>();
					mds.add(md);
					map.put(time, mds);
				} else {
					mds = map.get(time);
					//前端为了展示需要，需要将O2放在第一个位置
					if ("91001".equals(md.getObserveId())) 
					{
					    mds.add(0, md);
					}
					else
					{
					    mds.add(md);
					}
					map.put(time, mds);
				}
			}
		} else {
			// 无采集数据
			logger.info("----------无采集数据----------------");
		}

		List<MonDataFormBean> monDataList = new ArrayList<MonDataFormBean>();
		MonDataFormBean monData = null;
		MonitorData monitorData = null;

		if (!map.isEmpty() && map.size() > 0) {
			// int i = 0;
			int index = 0;
			// 循环seriesMap中的数据
			for (Date key : map.keySet()) {
				// if(i%3==0){
				monData = new MonDataFormBean();
				List<MonitorData> monitorDataList = new ArrayList<MonitorData>();
				List<BasMonitorDisplay> list = map.get(key);
				monData.setTime(key);
				monData.setIndex(index++);
				if (null != list && list.size() > 0) {
					for (BasMonitorDisplay md : list) {
						Integer freq = md.getFreq();
						if (null != freq) {
							monData.setFreq(freq);
						}
						monitorData = new MonitorData();
						BeanUtils.copyProperties(md, monitorData);
						monitorDataList.add(monitorData);
					}
				}
				monData.setMonitorDataList(monitorDataList);
				monDataList.add(monData);
				// }
				// i++;
			}
		}

		// 获取总数
		int total = basMonitorDisplayService.getobsDataTotalBase(formBean, observeIds);

		res.setResultCode("1");
		res.setResultMessage("查询监测项数据成功！");
		res.put("total", total);
		res.put("monDataList", monDataList);
		logger.info("----------------end getmonData------------------------");
		return res.getJsonStr();
	}
	
	
	
	/**
     * 开始手术，术前转术中 ，发送采集数据模块，并发送采集服务
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping("/startOperBase")
    @ResponseBody
    public String startOperBase(@RequestBody SearchFormBean searchBean){
        logger.info("----------------start startOper------------------------");
        
        Map result = new HashMap();
        
        try {
        	
        	String regOptId = searchBean.getRegOptId();
        	
            //麻醉记录单
            DocAnaesRecord anaesRecord = docAnaesRecordService.searchAnaesRecordByRegOptId(regOptId);
            //设置文书ID
            searchBean.setDocId(anaesRecord.getAnaRecordId());
            // 手术信息表
            BasRegOpt opt = basRegOptService.searchRegOptById(regOptId);
            
            //只有手术从术前到术中时，才需要将手术排程的麻醉医生、麻醉方法等数据插入到事件表
            boolean flagInsert = false;
            
            //AccessSource有值时代表是术中模块进入 ,为空值时则表示术后查看麻醉记录单
            if(StringUtils.isNotBlank(searchBean.getAccessSource())){
                //如果已经是术后or中止状态，则直接返回给前端
                if(("06").equals(opt.getState())){ //术后状态
                    result.put("resultCode", "40000004");
                    result.put("resultMessage", "当前患者("+opt.getName()+")的手术已结束!");
                    logger.info("------------------end startOper------------------------");
                    return JsonType.jsonType(result);
                }else if("07".equals(opt.getState())){
                    result.put("resultCode", "40000004");
                    result.put("resultMessage", "当前患者("+opt.getName()+")的手术已中止!");
                    logger.info("------------------end startOper------------------------");
                    return JsonType.jsonType(result);
                }
            }
            
            try {
            	if(Objects.equal(2, searchBean.getDocType())){
                	myOperationDataService.getPacuStartOperDate(searchBean, result); 
                }else{
                	myOperationDataService.getStartOperDate(searchBean, result,flagInsert);
                }
			} catch (Exception e) {
				return JsonType.jsonType(result);
			}
            
            
            AnaesRecordFormBean anaesRecordFormBean = new AnaesRecordFormBean();
            BeanUtils.copyProperties(anaesRecord, anaesRecordFormBean);
            anaesRecordFormBean.setOptBodys(StringUtils.getListByString(anaesRecordFormBean.getOptBody()));
            
            // 实际麻醉方法
            List<EvtAnaesMethodFormBean> realAnaesList = evtRealAnaesMethodService.getSelectRealAnaesMethodList(searchBean);
            // 术后诊断
            List<DiagnosedefFormBean> optLatterDiagList = evtOptLatterDiagService.getSelectOptLatterDiagList(searchBean);
            // 实施手术
            List<OperDefFormBean> optRealOperList = evtOptRealOperService.getSelectOptRealOperList(searchBean);
            //麻醉医生列表
            searchBean.setRole(EvtParticipantService.ROLE_ANESTH);
            List<UserInfoFormBean> anesDocList = evtParticipantService.getSelectParticipantList(searchBean);
            
            //手术医生列表
            searchBean.setRole(EvtParticipantService.ROLE_OPERAT);
            List<UserInfoFormBean> operatDocList = evtParticipantService.getSelectParticipantList(searchBean);
            
            //巡回护士列表
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_TOUR);//05
            List<UserInfoFormBean> nurseList = evtParticipantService.getSelectParticipantList(searchBean);
            
            // 器械护士列表
            searchBean.setRole(EvtParticipantService.ROLE_NURSE);
            searchBean.setType(EvtParticipantService.OPER_TYPE_INSTRUMENT);//04
            List<UserInfoFormBean> instruNurseList = evtParticipantService.getSelectParticipantList(searchBean);
            
            /*// 麻醉药事件明细   麻醉前用药
            searchBean.setType("02");
            List<RegOptOperMedicaleventFormBean> anaesMedEvtList = evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);
            
            // 治疗药事件明细   用药 
            searchBean.setType("01");
            List<RegOptOperMedicaleventFormBean> treatMedEvtList = evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);*/
            
            // 镇痛药事件明细   用药 
            searchBean.setType("03");
            List<RegOptOperMedicaleventFormBean> analgesicMedEvtList = evtMedicaleventService.searchMedicaleventGroupByCodeList(searchBean);
            
            // 入药量事件
            List<RegOptOperIoeventFormBean> inIoeventList = evtInEventService.searchIoeventGroupByDefIdList(searchBean);
            
            // 出药量事件
            List<RegOptOperEgressFormBean> egressList = evtEgressService.searchEgressGroupByDefIdList(searchBean);
            
            //手术体位变更
            List<OperBodyFormBean> operBodyList = evtOperBodyEventService.queryOperBodyEventList(searchBean);
            
                       
            // 心电图数据
            List<EvtElectDiogData> diogDataList = evtElectDiogDataService.searchElectDiogDataList(searchBean);
            
            //安全核查单
            DocAnaesBeforeSafeCheck anaesBeforeSafeCheck = docAnaesBeforeSafeCheckService.searchAnaBeCheckByRegOptId(opt.getRegOptId());
            DocExitOperSafeCheck exitOperSafeCheck = docExitOperSafeCheckService.searchExitOperCheckByRegOptId(opt.getRegOptId());
            DocOperBeforeSafeCheck operBeforeSafeCheck = docOperBeforeSafeCheckService.searchOperBeCheckByRegOptId(opt.getRegOptId());
            
            //O2L/MIN等数据
            //List<EvtOtherData> dataList = evtOtherDataService.selectOtherDataByObserveName(anaesRecord.getOther(), anaesRecord.getAnaRecordId());
            
            // 去除O2
            List<BasMonitorConfig> monitorConfigList = basMonitorConfigService.selectMustShowListWithoutO2(regOptId);
            
            result.put("resultCode", "1");
            result.put("resultMessage", "查询麻醉记录单成功!!!");
            result.put("regOpt", opt);
            result.put("anaesBeforeSafeCheck", null != anaesBeforeSafeCheck ? anaesBeforeSafeCheck.getProcessState() : null);
            result.put("exitOperSafeCheck", null != exitOperSafeCheck ? exitOperSafeCheck.getProcessState() : null);
            result.put("operBeforeSafeCheck", null != operBeforeSafeCheck ? operBeforeSafeCheck.getProcessState() : null);
            result.put("anaesRecord", anaesRecordFormBean);
//            result.put("anaesMedEvtList", anaesMedEvtList);
//            result.put("treatMedEvtList", treatMedEvtList);
            result.put("analgesicMedEvtList", analgesicMedEvtList);
            result.put("realAnaesList", realAnaesList);
            result.put("optLatterDiagList", optLatterDiagList);
            result.put("optRealOperList", optRealOperList);
            result.put("inIoeventList", inIoeventList);
            result.put("outIoeventList", egressList);
            result.put("anesDocList", anesDocList);
            result.put("operatDocList", operatDocList);
            result.put("nurseList", nurseList);
            result.put("instruNurseList", instruNurseList);
            result.put("operBodyList", operBodyList);
            //result.put("otherData", dataList);
            result.put("diogDataList", diogDataList);
            result.put("monitorConfigList", monitorConfigList);
            logger.info("startOper---result-------"+JsonType.jsonType(result));
            logger.info("------------------end startOper------------------------");
            return JsonType.jsonType(result);
        } catch (Exception e) {
            //if(logger.isErrorEnabled()){
            logger.error("startOper====="+Exceptions.getStackTraceAsString(e));
            //}
            result.put("resultCode", "10000000");
            result.put("resultMessage", "系统错误，请与系统管理员联系!");
        }
        logger.info("------------------end startOper------------------------");
        return JsonType.jsonType(result);
    }

    /**
	 * 修改入室时间 时间往前修改，时间往后修改 往后修改，时间高于手术开始命令时间，不做数据处理操作
	 * 往前修改，时间低于手术开始命令时间，先删除原有的不对的数据，然后再添加以频率（第一个点的频率）算好的对应的点 入室事件
	 * 需要记录到s_anaesevent,并将时间存入d_anaesrecord 麻醉记录单中
	 * 
	 * @return
	 */
	@RequestMapping("/updateEnterRoomTimeBase")
	@ResponseBody
	@ApiOperation(value = "修改入PACU室时间", httpMethod = "POST", notes = "修改入室时间")
	public String updateEnterRoomTimeBase(@ApiParam(name = "params", value = "参数") @RequestBody EnterRoomFormBean formBean) {
		// 1、从b_reg_opt表中获取发起手术命令时间
		// 2、比对传入的入室时间和发起手术命令时间的大小 分支：2.1：入室时间大于手术时间，则不修改，删除无用数据，记录事件，并修改入室时间；
		// 2.2：入室时间小于手术时间，删除无用数据，根据当前手术时间往前推freq的值，存入b_monitor_display表中，记录事件，并修改入室时间；
		// 3、根据手术命令时间，查询当前手术的点和freq
		logger.info("----------------start updateEnterRoomTimeBase------------------------");
		ResponseValue res = new ResponseValue();
		String regOptId = formBean.getRegOptId();
		Date inTime = formBean.getInTime();
		String docId = formBean.getDocId();
		if (StringUtils.isNotBlank(regOptId) && inTime != null && StringUtils.isNotBlank(docId)) {

			BasRegOpt regOpt = basRegOptService.searchRegOptById(regOptId);
			if (null == regOpt) {
				logger.info("updatePacuEnterRoomTime----regOpt手术为null！--------------");
				res.setResultCode("10000000");
				res.setResultMessage("查询数据有误，请与系统管理员联系!");
			} else {
				if(Objects.equal(2, formBean.getDocType())){
					DocAnaesPacuRec anaesPacuRec = docAnaesPacuRecService.getAnaesPacuRecByRegOptId(regOptId);
					Date operTime = anaesPacuRec.getOperTime();
					if (null == operTime) {
						logger.info("updatePacuEnterRoomTime----operTime手术时间为null！--------------");
						res.setResultCode("10000000");
						res.setResultMessage("查询数据有误，请与系统管理员联系!");
					} else {
						long inTimeLong = inTime.getTime();
						long operTimeLong = operTime.getTime();
						
						formBean.setOperTime(operTime);
						if (inTimeLong > operTimeLong) { // 入室时间大于手术命令开始时间
							// 1、根据手术命令时间，删除没有用的数据
							// 2、记录事件，并修改入室时间
							basMonitorDisplayService.updatePacuEnterRoomTimegt(formBean, res);
						} else if (inTimeLong < operTimeLong) {// 入室时间小于手术命令开始时间
							// 1、根据手术命令时间，删除没有用的数据
							// 2、根据当前手术时间往前推freq的值，存入b_monitor_display表中
							// 3、记录事件，并修改入室时间
							basMonitorDisplayService.updPacuEnterRoomTimelt(formBean, res);
						} else { // = 不处理

						}
					}
					docAnaesPacuRecService.updatePacuRecEnterRoomTime(inTime, anaesPacuRec.getId()); 
				}else{
					Date operTime = regOpt.getOperTime();
					if (null == operTime) {
						logger.info("updateEnterRoomTime----operTime手术时间为null！--------------");
						res.setResultCode("10000000");
						res.setResultMessage("查询数据有误，请与系统管理员联系!");
					} else {
						long inTimeLong = inTime.getTime();
						long operTimeLong = operTime.getTime();
						
						formBean.setOperTime(operTime);
						if (inTimeLong > operTimeLong) { // 入室时间大于手术命令开始时间
							// 1、根据手术命令时间，删除没有用的数据
							// 2、记录事件，并修改入室时间
							basMonitorDisplayService.updateEnterRoomTimegt(formBean, res);
						} else if (inTimeLong < operTimeLong) {// 入室时间小于手术命令开始时间
							// 1、根据手术命令时间，删除没有用的数据
							// 2、根据当前手术时间往前推freq的值，存入b_monitor_display表中
							// 3、记录事件，并修改入室时间
							basMonitorDisplayService.updEnterRoomTimelt(formBean, res);
						} else { // = 不处理

						}
					}
					docAnaesRecordService.updateAnaesInRoomTime(SysUtil.getTimeFormat(inTime), regOptId); 
				}
			}
			// 根据docId查询对应的事件id
			SearchFormBean searchBean = new SearchFormBean();
			searchBean.setDocId(docId);
			List<EvtAnaesEvent> resultList = evtAnaesEventService.searchAnaeseventList(searchBean);
			res.put("anaeseventList", resultList);
		} else {
			res.setResultCode("70000000");
			res.setResultMessage(Global.getRetMsg(res.getResultCode()));
		}

		logger.info("----------------end updateEnterRoomTimeBase------------------------");
		return res.getJsonStr();
	}

	/**
     * 修改出室时间
     */
    @RequestMapping("/updateOuterTimeBase")
    @ResponseBody
    public String updateOuterTimeBase(@RequestBody EndOperationFormBean formBean){
        logger.info("----------------start updateOuterTimeBase------------------------");
        ResponseValue res = new ResponseValue();
        EvtAnaesEvent anaesevent = formBean.getAnaesevent();
        if (anaesevent != null)
        {
        	if(Objects.equal(2, formBean.getDocType())){
        		basMonitorDisplayService.updatePacuEndTime(formBean, res);
			}else{
				basMonitorDisplayService.updateEndTime(formBean,res);
			}
        	
            SearchFormBean searchBean = new SearchFormBean();
            searchBean.setDocId(anaesevent.getDocId());
            List<EvtAnaesEvent> resultList = new ArrayList<EvtAnaesEvent>();
            resultList = evtAnaesEventService.searchAnaeseventList(searchBean);
            res.put("result", true);
            res.put("anaeseventList", resultList);
        }
        else
        {
            res.setResultCode("70000000");
            res.setResultMessage(Global.getRetMsg(res.getResultCode()));
        }
        logger.info("------------------end updateOuterTimeBase------------------------");
        return res.getJsonStr();
    }
	
	/**
	 * 1、第一次进入手术室的时候，存入手术命令开始时间，存入入室事件，存入开始手术时间 2、存入第一个数据点 3、返回麻醉时间list
	 */
	@RequestMapping("/firstSpotBase")
	@ResponseBody
	@ApiOperation(value = "首次进入手术", httpMethod = "POST", notes = "首次进入手术")
	public String firstSpotBase(@RequestBody FirstSpotFormBean formBean) {
		logger.info("----------------start firstSpot------------------------");
		ResponseValue res = new ResponseValue(); 
		if (null != formBean) {
			
			if(Objects.equal(2, formBean.getDocType())){
				basMonitorDisplayService.firstSpotPacu(formBean);
			}else{
				basMonitorDisplayService.firstSpot(formBean);
			}
			// 根据docId查询对应的事件id
			SearchFormBean searchBean = new SearchFormBean();
			searchBean.setDocId(formBean.getDocId());
			List<EvtAnaesEvent> resultList = new ArrayList<EvtAnaesEvent>();
			resultList = evtAnaesEventService.searchAnaeseventList(searchBean);
			res.put("anaeseventList", resultList);
		} else {
			res.setResultCode("70000000");
			res.setResultMessage(Global.getRetMsg(res.getResultCode()));
		}
		logger.info("----------------end firstSpot------------------------");
		return res.getJsonStr();
	}
	
	
	/**
     * 正常结束手术，发送采集数据模块，并发送采集服务(黔南州中医院定制)
     * 
     * @return
     */
    @RequestMapping("/endOperationBase")
    @ResponseBody
    @ApiOperation(value = "正常结束手术", httpMethod = "POST", notes = "正常结束手术")
    public String endOperationBase(@ApiParam(name = "params", value = "参数") @RequestBody EndOperationFormBean formBean) {
        logger.info("----------------start endOperation------------------------");
        ResponseValue res = new ResponseValue();
        EvtAnaesEvent anaesevent = formBean.getAnaesevent();
        logger.info("endOperation----" + anaesevent);
        String regOptId = formBean.getRegOptId();
        
        List<EvtAnaesEvent> resultList = new ArrayList<EvtAnaesEvent>();
        SearchFormBean searchBean = new SearchFormBean();
        searchBean.setDocId(anaesevent.getDocId());
        
        if (anaesevent != null) {
        	if(Objects.equal(2, formBean.getDocType())){
        		 basMonitorDisplayService.upDatePacuEndTime(formBean, res);
        		 if (res.getResultCode().equals("1")) { // 只有成功了，才执行正常结束手术命令
                    resultList = evtAnaesEventService.searchAnaeseventList(searchBean);
                    DocAnaesPacuRec anaesPacuRec = docAnaesPacuRecService.getAnaesPacuRecByRegOptId(regOptId);
                    if(null != anaesPacuRec){
	                    CmdMsg msg = new CmdMsg();
	         			msg.setMsgType(com.digihealth.anesthesia.pacu.core.MyConstants.STATUS_END);
	         			msg.setBedId(anaesPacuRec.getBedId());
	         			msg.setRegOptId(regOptId);
	         			MsgProcess.process(msg);
                    }
                 }
        	}else{
        		 basMonitorDisplayService.updateEndTime(formBean,res);
        		 if (res.getResultCode().equals("1")) { // 只有成功了，才执行正常结束手术命令
                     resultList = evtAnaesEventService.searchAnaeseventList(searchBean);
                     CmdMsg msg = new CmdMsg();
                     msg.setMsgType(MyConstants.OPERATION_STATUS_END);
                     msg.setRegOptId(regOptId);
                     res = MessageProcess.process(msg);
                     res.put("resultList", resultList);
                     
                     if (EvtAnaesEventService.OUT_ROOM.equals(formBean.getAnaesevent().getCode()))
                     {
                         String isConnectionFlag = Global.getConfig("isConnectionHis").trim();
                         if(StringUtils.isEmpty(isConnectionFlag) || "true".equals(isConnectionFlag)){
                             logger.info("===============================发送手术麻醉记录到his===========================================");
                             //HisInterfaceServiceSYZXYY hisInterfaceService = SpringContextHolder.getBean(HisInterfaceServiceSYZXYY.class);
                             //hisInterfaceService.sendAnaesRecordToHis(regOptId);
                         }
                     }
                 }
        	}
        	 res.put("resultList", resultList);
        } else {
            res.setResultCode("70000000");
            res.setResultMessage(Global.getRetMsg(res.getResultCode()));
        }
        logger.info("------------------end endOperation------------------------");
        return res.getJsonStr();
    }
	
    
}
