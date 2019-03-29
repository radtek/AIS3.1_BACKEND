/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.digihealth.anesthesia.common.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.dao.BasAnaesDoctorAmountDao;
import com.digihealth.anesthesia.basedata.dao.BasAnaesEventDao;
import com.digihealth.anesthesia.basedata.dao.BasAnaesKndgbaseDao;
import com.digihealth.anesthesia.basedata.dao.BasAnaesMedicineInRecordDao;
import com.digihealth.anesthesia.basedata.dao.BasAnaesMedicineLoseRecordDao;
import com.digihealth.anesthesia.basedata.dao.BasAnaesMedicineOutRecordDao;
import com.digihealth.anesthesia.basedata.dao.BasAnaesMedicineRetreatRecordDao;
import com.digihealth.anesthesia.basedata.dao.BasAnaesMedicineStorageDao;
import com.digihealth.anesthesia.basedata.dao.BasAnaesMedicineStorageHisDao;
import com.digihealth.anesthesia.basedata.dao.BasAnaesMethodDao;
import com.digihealth.anesthesia.basedata.dao.BasAnnouncementDao;
import com.digihealth.anesthesia.basedata.dao.BasBloodDefinationDao;
import com.digihealth.anesthesia.basedata.dao.BasBusEntityDao;
import com.digihealth.anesthesia.basedata.dao.BasChargeItemDao;
import com.digihealth.anesthesia.basedata.dao.BasChargeItemPackagesRelDao;
import com.digihealth.anesthesia.basedata.dao.BasChargePackagesDao;
import com.digihealth.anesthesia.basedata.dao.BasCheckItemDao;
import com.digihealth.anesthesia.basedata.dao.BasCollectConfigDao;
import com.digihealth.anesthesia.basedata.dao.BasCollectPacuDataDao;
import com.digihealth.anesthesia.basedata.dao.BasCollectPacuDataHisDao;
import com.digihealth.anesthesia.basedata.dao.BasColumnStyleDao;
import com.digihealth.anesthesia.basedata.dao.BasConsultationDao;
import com.digihealth.anesthesia.basedata.dao.BasConsumablesInRecordDao;
import com.digihealth.anesthesia.basedata.dao.BasConsumablesLoseRecordDao;
import com.digihealth.anesthesia.basedata.dao.BasConsumablesOutRecordDao;
import com.digihealth.anesthesia.basedata.dao.BasConsumablesRetreatRecordDao;
import com.digihealth.anesthesia.basedata.dao.BasConsumablesStorageDao;
import com.digihealth.anesthesia.basedata.dao.BasConsumablesStorageHisDao;
import com.digihealth.anesthesia.basedata.dao.BasCustomConfigureDao;
import com.digihealth.anesthesia.basedata.dao.BasDefaultOperatorDao;
import com.digihealth.anesthesia.basedata.dao.BasDeptDao;
import com.digihealth.anesthesia.basedata.dao.BasDeviceConfigDao;
import com.digihealth.anesthesia.basedata.dao.BasDeviceMonitorConfigDao;
import com.digihealth.anesthesia.basedata.dao.BasDeviceSpecificationDao;
import com.digihealth.anesthesia.basedata.dao.BasDiagnosedefDao;
import com.digihealth.anesthesia.basedata.dao.BasDispatchDao;
import com.digihealth.anesthesia.basedata.dao.BasDocumentDao;
import com.digihealth.anesthesia.basedata.dao.BasHealthNurseDao;
import com.digihealth.anesthesia.basedata.dao.BasIconSvgDao;
import com.digihealth.anesthesia.basedata.dao.BasInOutInfoDao;
import com.digihealth.anesthesia.basedata.dao.BasInspectItemDao;
import com.digihealth.anesthesia.basedata.dao.BasInspectRecordDao;
import com.digihealth.anesthesia.basedata.dao.BasInstrSuitRelDao;
import com.digihealth.anesthesia.basedata.dao.BasInstrsuitDao;
import com.digihealth.anesthesia.basedata.dao.BasInstrumentDao;
import com.digihealth.anesthesia.basedata.dao.BasInventoryDao;
import com.digihealth.anesthesia.basedata.dao.BasInventoryMonthDao;
import com.digihealth.anesthesia.basedata.dao.BasIoDefinationDao;
import com.digihealth.anesthesia.basedata.dao.BasMedicalTakeReasonDao;
import com.digihealth.anesthesia.basedata.dao.BasMedicalTakeWayDao;
import com.digihealth.anesthesia.basedata.dao.BasMedicineDao;
import com.digihealth.anesthesia.basedata.dao.BasMonitorConfigFreqDao;
import com.digihealth.anesthesia.basedata.dao.BasOperateLogDao;
import com.digihealth.anesthesia.basedata.dao.BasOperationPeopleDao;
import com.digihealth.anesthesia.basedata.dao.BasOperdefDao;
import com.digihealth.anesthesia.basedata.dao.BasOperroomDao;
import com.digihealth.anesthesia.basedata.dao.BasOtherEventDao;
import com.digihealth.anesthesia.basedata.dao.BasPacuBedEventConfigDao;
import com.digihealth.anesthesia.basedata.dao.BasPacuDeviceConfigDao;
import com.digihealth.anesthesia.basedata.dao.BasPacuDeviceMonitorConfigDao;
import com.digihealth.anesthesia.basedata.dao.BasPacuDeviceSpecificationDao;
import com.digihealth.anesthesia.basedata.dao.BasPacuMonitorConfigDao;
import com.digihealth.anesthesia.basedata.dao.BasPriceDao;
import com.digihealth.anesthesia.basedata.dao.BasPropertiesDao;
import com.digihealth.anesthesia.basedata.dao.BasRegOptDao;
import com.digihealth.anesthesia.basedata.dao.BasRegionBedDao;
import com.digihealth.anesthesia.basedata.dao.BasRegionDao;
import com.digihealth.anesthesia.basedata.dao.BasRequiredItemDao;
import com.digihealth.anesthesia.basedata.dao.BasRoutingAccessControlDao;
import com.digihealth.anesthesia.basedata.dao.BasRowStyleDao;
import com.digihealth.anesthesia.basedata.dao.BasSelfPayInstrumentDao;
import com.digihealth.anesthesia.basedata.dao.BasTitleStyleDao;
import com.digihealth.anesthesia.basedata.dao.ControllerDao;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.utils.CustomConfigureUtil;
import com.digihealth.anesthesia.common.config.Global;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.doc.dao.DocAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocAccedeInformedDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesBeforeSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesMedicineAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesMethodChangeRecordDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesPacuObserveRecDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesPacuRecDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesPlanDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesPostopDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesPreDiscussRecordDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesQualityControlDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesRecordDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAllergicReactionDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAppendixCanalDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAppendixGenDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryAppendixVisitDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryDao;
import com.digihealth.anesthesia.doc.dao.DocAnaesSummaryVenipunctureDao;
import com.digihealth.anesthesia.doc.dao.DocAnalgesicInformedConsentDao;
import com.digihealth.anesthesia.doc.dao.DocAnalgesicMedicalInfoDao;
import com.digihealth.anesthesia.doc.dao.DocAnalgesicPostflupDao;
import com.digihealth.anesthesia.doc.dao.DocAnalgesicRecipeDao;
import com.digihealth.anesthesia.doc.dao.DocAnalgesicRecordDao;
import com.digihealth.anesthesia.doc.dao.DocAnalgesicVisitInfoDao;
import com.digihealth.anesthesia.doc.dao.DocBadEventDao;
import com.digihealth.anesthesia.doc.dao.DocBloodTransItemDao;
import com.digihealth.anesthesia.doc.dao.DocBloodTransRecordDao;
import com.digihealth.anesthesia.doc.dao.DocDifficultCaseDiscussDao;
import com.digihealth.anesthesia.doc.dao.DocDocordRecordDao;
import com.digihealth.anesthesia.doc.dao.DocEventBillingDao;
import com.digihealth.anesthesia.doc.dao.DocExitOperSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocGeneralAnaesDao;
import com.digihealth.anesthesia.doc.dao.DocInstrubillItemDao;
import com.digihealth.anesthesia.doc.dao.DocInsuredChargeInformDao;
import com.digihealth.anesthesia.doc.dao.DocInsuredChargeItemDao;
import com.digihealth.anesthesia.doc.dao.DocInsuredItemDao;
import com.digihealth.anesthesia.doc.dao.DocInsuredPatAgreeDao;
import com.digihealth.anesthesia.doc.dao.DocLaborAnalgesiaAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocNerveBlockDao;
import com.digihealth.anesthesia.doc.dao.DocNurseInterviewRecordDao;
import com.digihealth.anesthesia.doc.dao.DocOperBeforeSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocOptCareRecordDao;
import com.digihealth.anesthesia.doc.dao.DocOptNurseDao;
import com.digihealth.anesthesia.doc.dao.DocOptNurseRecordDao;
import com.digihealth.anesthesia.doc.dao.DocOptRiskEvaluationDao;
import com.digihealth.anesthesia.doc.dao.DocPackagesItemDao;
import com.digihealth.anesthesia.doc.dao.DocPatCheckItemDao;
import com.digihealth.anesthesia.doc.dao.DocPatCheckRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectItemDao;
import com.digihealth.anesthesia.doc.dao.DocPatInspectRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPatOutRangeAgreeDao;
import com.digihealth.anesthesia.doc.dao.DocPatOutRangeItemDao;
import com.digihealth.anesthesia.doc.dao.DocPatPrevisitRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPatRescurRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPatShuttleTransferContentDao;
import com.digihealth.anesthesia.doc.dao.DocPatShuttleTransferDao;
import com.digihealth.anesthesia.doc.dao.DocPlacentaHandleAgreeDao;
import com.digihealth.anesthesia.doc.dao.DocPostFollowAnalgesicDao;
import com.digihealth.anesthesia.doc.dao.DocPostFollowGeneralDao;
import com.digihealth.anesthesia.doc.dao.DocPostFollowRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPostFollowSpinalDao;
import com.digihealth.anesthesia.doc.dao.DocPostFollowYxrmDao;
import com.digihealth.anesthesia.doc.dao.DocPostOperRegardDao;
import com.digihealth.anesthesia.doc.dao.DocPreAnaesTalkRecordDao;
import com.digihealth.anesthesia.doc.dao.DocPreOperVisitDao;
import com.digihealth.anesthesia.doc.dao.DocPrePostVisitDao;
import com.digihealth.anesthesia.doc.dao.DocPrePostVisitItemDao;
import com.digihealth.anesthesia.doc.dao.DocPrePublicityDao;
import com.digihealth.anesthesia.doc.dao.DocPreVisitDao;
import com.digihealth.anesthesia.doc.dao.DocPrevisitAccessexamDao;
import com.digihealth.anesthesia.doc.dao.DocPrevisitAnaesplanDao;
import com.digihealth.anesthesia.doc.dao.DocPrevisitPhyexamDao;
import com.digihealth.anesthesia.doc.dao.DocRiskEvaluatPreventCareDao;
import com.digihealth.anesthesia.doc.dao.DocSafeCheckDao;
import com.digihealth.anesthesia.doc.dao.DocSelfPayAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocSelfPayAccedeLlzyyDao;
import com.digihealth.anesthesia.doc.dao.DocSelfPayInstrumentAccedeDao;
import com.digihealth.anesthesia.doc.dao.DocSelfPayInstrumentItemDao;
import com.digihealth.anesthesia.doc.dao.DocSpinalCanalPunctureDao;
import com.digihealth.anesthesia.doc.dao.DocThemeDao;
import com.digihealth.anesthesia.doc.dao.DocTitleDao;
import com.digihealth.anesthesia.doc.dao.DocTransferConnectRecordDao;
import com.digihealth.anesthesia.doc.dao.DocTransferConnectTypeDao;
import com.digihealth.anesthesia.doc.dao.DocVeinAccedeDao;
import com.digihealth.anesthesia.doc.po.DocAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocAnaesMedicineAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesPlan;
import com.digihealth.anesthesia.doc.po.DocAnaesPostop;
import com.digihealth.anesthesia.doc.po.DocAnaesPreDiscussRecord;
import com.digihealth.anesthesia.doc.po.DocAnaesQualityControl;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocAnaesSummary;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAllergicReaction;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixCanal;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixGen;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixVisit;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryVenipuncture;
import com.digihealth.anesthesia.doc.po.DocAnalgesicInformedConsent;
import com.digihealth.anesthesia.doc.po.DocAnalgesicRecord;
import com.digihealth.anesthesia.doc.po.DocBloodTransRecord;
import com.digihealth.anesthesia.doc.po.DocDifficultCaseDiscuss;
import com.digihealth.anesthesia.doc.po.DocExitOperSafeCheck;
import com.digihealth.anesthesia.doc.po.DocGeneralAnaes;
import com.digihealth.anesthesia.doc.po.DocInsuredPatAgree;
import com.digihealth.anesthesia.doc.po.DocLaborAnalgesiaAccede;
import com.digihealth.anesthesia.doc.po.DocNerveBlock;
import com.digihealth.anesthesia.doc.po.DocNurseInterviewRecord;
import com.digihealth.anesthesia.doc.po.DocOperBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.doc.po.DocOptNurse;
import com.digihealth.anesthesia.doc.po.DocOptRiskEvaluation;
import com.digihealth.anesthesia.doc.po.DocPatOutRangeAgree;
import com.digihealth.anesthesia.doc.po.DocPatPrevisitRecord;
import com.digihealth.anesthesia.doc.po.DocPatRescurRecord;
import com.digihealth.anesthesia.doc.po.DocPatShuttleTransfer;
import com.digihealth.anesthesia.doc.po.DocPlacentaHandleAgree;
import com.digihealth.anesthesia.doc.po.DocPostFollowRecord;
import com.digihealth.anesthesia.doc.po.DocPostOperRegard;
import com.digihealth.anesthesia.doc.po.DocPreOperVisit;
import com.digihealth.anesthesia.doc.po.DocPrePostVisit;
import com.digihealth.anesthesia.doc.po.DocPrePublicity;
import com.digihealth.anesthesia.doc.po.DocPreVisit;
import com.digihealth.anesthesia.doc.po.DocRiskEvaluatPreventCare;
import com.digihealth.anesthesia.doc.po.DocSafeCheck;
import com.digihealth.anesthesia.doc.po.DocSelfPayAccedeLlzyy;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentAccede;
import com.digihealth.anesthesia.doc.po.DocSpinalCanalPuncture;
import com.digihealth.anesthesia.doc.po.DocTransferConnectRecord;
import com.digihealth.anesthesia.doc.po.DocVeinAccede;
import com.digihealth.anesthesia.evt.dao.EvtAnaesEventDao;
import com.digihealth.anesthesia.evt.dao.EvtAnaesModifyRecordDao;
import com.digihealth.anesthesia.evt.dao.EvtCheckEventDao;
import com.digihealth.anesthesia.evt.dao.EvtCheckEventItemRelationDao;
import com.digihealth.anesthesia.evt.dao.EvtCtlBreathDao;
import com.digihealth.anesthesia.evt.dao.EvtEgressDao;
import com.digihealth.anesthesia.evt.dao.EvtElectDiogDataDao;
import com.digihealth.anesthesia.evt.dao.EvtInEventDao;
import com.digihealth.anesthesia.evt.dao.EvtMaterialDao;
import com.digihealth.anesthesia.evt.dao.EvtMedicalEventDao;
import com.digihealth.anesthesia.evt.dao.EvtMedicalEventDetailDao;
import com.digihealth.anesthesia.evt.dao.EvtOperBodyEventDao;
import com.digihealth.anesthesia.evt.dao.EvtOptLatterDiagDao;
import com.digihealth.anesthesia.evt.dao.EvtOptRealOperDao;
import com.digihealth.anesthesia.evt.dao.EvtOtherEventDao;
import com.digihealth.anesthesia.evt.dao.EvtParticipantDao;
import com.digihealth.anesthesia.evt.dao.EvtRealAnaesMethodDao;
import com.digihealth.anesthesia.evt.dao.EvtRescueeventDao;
import com.digihealth.anesthesia.evt.dao.EvtShiftChangeDao;
import com.digihealth.anesthesia.evt.dao.EvtSpecialMaterialEventDao;
import com.digihealth.anesthesia.operProceed.dao.BasAnaesMonitorConfigDao;
import com.digihealth.anesthesia.operProceed.dao.BasMonitorConfigDao;
import com.digihealth.anesthesia.operProceed.dao.BasMonitorConfigDefaultDao;
import com.digihealth.anesthesia.operProceed.dao.BasMonitorDisplayChangeHisDao;
import com.digihealth.anesthesia.operProceed.dao.BasMonitorDisplayDao;
import com.digihealth.anesthesia.operProceed.dao.BasMonitorFreqChangeDao;
import com.digihealth.anesthesia.operProceed.dao.BasMonitorPupilDao;
import com.digihealth.anesthesia.operProceed.dao.BasObserveDataDao;
import com.digihealth.anesthesia.operProceed.dao.BasObserveDataHisDao;
import com.digihealth.anesthesia.operProceed.dao.BasPacuRecMonitorConfigDao;
import com.digihealth.anesthesia.operProceed.dao.ObserveDataChangeHisDao;
import com.digihealth.anesthesia.research.dao.StatisticsDao;
import com.digihealth.anesthesia.sysMng.dao.BasDictGroupDao;
import com.digihealth.anesthesia.sysMng.dao.BasDictItemDao;
import com.digihealth.anesthesia.sysMng.dao.BasMenuDao;
import com.digihealth.anesthesia.sysMng.dao.BasRoleDao;
import com.digihealth.anesthesia.sysMng.dao.BasRoleMenuDao;
import com.digihealth.anesthesia.sysMng.dao.BasTaskScheduleDao;
import com.digihealth.anesthesia.sysMng.dao.BasUserDao;
import com.digihealth.anesthesia.sysMng.dao.BasUserRoleDao;
import com.digihealth.anesthesia.sysMng.dao.BasViewDao;
import com.digihealth.anesthesia.tmp.dao.TmpAnaesDoctempDao;
import com.digihealth.anesthesia.tmp.dao.TmpAnalgesicDao;
import com.digihealth.anesthesia.tmp.dao.TmpChargePayTempDao;
import com.digihealth.anesthesia.tmp.dao.TmpIoEventDao;
import com.digihealth.anesthesia.tmp.dao.TmpMedPayTempDao;
import com.digihealth.anesthesia.tmp.dao.TmpMedicineDao;
import com.digihealth.anesthesia.tmp.dao.TmpMedicineEventDao;
import com.digihealth.anesthesia.tmp.dao.TmpOthereventDao;
import com.digihealth.anesthesia.tmp.dao.TmpPacuLiquidTempDao;
import com.digihealth.anesthesia.tmp.dao.TmpPriChargeMedTempDao;
import com.digihealth.anesthesia.tmp.dao.TmpSciResearchFieldDao;
import com.digihealth.anesthesia.tmp.dao.TmpSciTempDao;
import com.digihealth.anesthesia.tmp.dao.TmpToxicOutstockTempDao;

/**
 * 
 * Title: BaseService.java Description: Service基类
 * 
 * @author chengwang
 * @created 2015-10-8 下午1:57:36
 */
@Transactional
public abstract class BaseService {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	protected BasAnnouncementDao basAnnouncementDao;
	@Autowired
	protected BasPriceDao basPriceDao;
	@Autowired
	protected BasMedicineDao basMedicineDao;
	@Autowired
	protected DocPreVisitDao docPreVisitDao;
	@Autowired
	protected DocAccedeDao docAccedeDao;
	@Autowired
	protected BasAnaesMethodDao basAnaesMethodDao;
	@Autowired
	protected StatisticsDao statisticsDao;
	@Autowired
	protected ControllerDao controllerDao;
	@Autowired
	protected DocAnaesPostopDao docAnaesPostopDao;
	@Autowired
	protected DocOptNurseDao docOptNurseDao;
	@Autowired
	protected DocAnaesPlanDao docAnaesPlanDao;
	@Autowired
	protected BasIoDefinationDao basIoDefinationDao;
	@Autowired
	protected BasInstrumentDao basInstrumentDao;
	@Autowired
	protected BasInstrSuitRelDao basInstrSuitRelDao;
	@Autowired
	protected DocAnaesRecordDao docAnaesRecordDao;
	
	@Autowired
	protected EvtAnaesEventDao evtAnaesEventDao;
	
	@Autowired
	protected EvtMedicalEventDao evtMedicaleventDao;

	@Autowired
	protected EvtOptRealOperDao evtOptRealOperDao;
	@Autowired
	protected EvtInEventDao evtInEventDao;
	
	@Autowired
	protected EvtCtlBreathDao evtCtlBreathDao;
	@Autowired
	protected DocSafeCheckDao docSafeCheckDao;
	@Autowired
	protected BasHealthNurseDao basHealthNurseDao;
	@Autowired
	protected BasOperationPeopleDao basOperationPeopleDao;
	@Autowired
	protected BasOperdefDao basOperdefDao;
	@Autowired
	protected DocAnaesBeforeSafeCheckDao docAnaesBeforeSafeCheckDao;
	@Autowired
	protected DocOperBeforeSafeCheckDao docOperBeforeSafeCheckDao;
	@Autowired
	protected DocExitOperSafeCheckDao docExitOperSafeCheckDao;
	@Autowired
	protected DocAnaesSummaryDao docAnaesSummaryDao;
	@Autowired
	protected DocGeneralAnaesDao docGeneralAnaesDao;
	@Autowired
	protected DocNerveBlockDao docNerveBlockDao;
	@Autowired
	protected BasRegionDao basRegionDao;
	@Autowired
	protected DocBadEventDao docBadEventDao;
	@Autowired
	protected EvtOtherEventDao evtOtherEventDao;
	@Autowired
	protected EvtOptLatterDiagDao evtOptLatterDiagDao;
	
	@Autowired
	protected EvtCheckEventItemRelationDao evtCheckEventItemRelationDao;

	@Autowired
	protected EvtCheckEventDao evtCheckEventDao;
	
	@Autowired
	protected EvtEgressDao evtEgressDao;
	@Autowired
	protected DocAccedeInformedDao docAccedeInformedDao;
	@Autowired
	protected BasCheckItemDao basCheckItemDao;
	@Autowired
	protected BasAnaesMonitorConfigDao basAnaesMonitorConfigDao;
	@Autowired
	protected BasDeviceMonitorConfigDao basDeviceMonitorConfigDao;
	@Autowired
	protected EvtOperBodyEventDao evtOperBodyEventDao;
	@Autowired
	protected BasDeviceSpecificationDao basDeviceSpecificationDao;
	@Autowired
	protected BasDeviceConfigDao basDeviceConfigDao;
	@Autowired
	protected BasMonitorConfigFreqDao basMonitorConfigFreqDao;
	@Autowired
	protected BasConsultationDao basConsultationDao;
	@Autowired
	protected BasDiagnosedefDao basDiagnosedefDao;
	@Autowired
	protected DocOptRiskEvaluationDao docOptRiskEvaluationDao;
	@Autowired
	protected BasChargeItemDao basChargeItemDao;
	@Autowired
	protected BasInOutInfoDao basInOutInfoDao;
	@Autowired
	protected BasInventoryDao basInventoryDao;
	@Autowired
	protected BasInventoryMonthDao basInventoryMonthDao;
	@Autowired
	protected DocEventBillingDao docEventBillingDao;
	@Autowired
	protected BasChargePackagesDao basChargePackagesDao;
	@Autowired
	protected BasChargeItemPackagesRelDao basChargeItemPackagesRelDao;
	@Autowired
    protected BasObserveDataHisDao basObserveDataHisDao;
	@Autowired
	protected BasObserveDataDao basObserveDataDao;
	@Autowired
	protected EvtParticipantDao evtParticipantDao;
	@Autowired
	protected BasDeptDao basDeptDao;
	@Autowired
	protected DocPackagesItemDao docPackagesItemDao;
	@Autowired
	protected ObserveDataChangeHisDao observeDataChangeDao;
	
	@Autowired
	protected BasMonitorDisplayChangeHisDao basMonitorDisplayChangeHisDao;
	@Autowired
	protected DocPatOutRangeItemDao docPatOutRangeItemDao;
	@Autowired
	protected DocOptCareRecordDao docOptCareRecordDao;
	@Autowired
	protected BasOperroomDao basOperroomDao;
	@Autowired
	protected EvtMaterialDao evtMaterialDao;
	
	@Autowired
	protected DocAnalgesicRecipeDao docAnalgesicRecipeDao;
	@Autowired
	protected DocAnalgesicMedicalInfoDao docAnalgesicMedicalInfoDao;
	@Autowired
	protected DocAnalgesicVisitInfoDao docAnalgesicVisitInfoDao;
	@Autowired
	protected BasMonitorDisplayDao basMonitorDisplayDao;
	@Autowired
	protected DocInstrubillItemDao docInstrubillItemDao;
	@Autowired
	protected BasMonitorFreqChangeDao basMonitorFreqChangeDao;
	@Autowired
	protected BasMonitorConfigDao basMonitorConfigDao;
	@Autowired
	protected DocPatInspectItemDao docPatInspectItemDao;
	@Autowired
	protected DocPatInspectRecordDao  docPatInspectRecordDao;
	@Autowired
	protected BasInspectItemDao basInspectItemDao;
	@Autowired
	protected BasInspectRecordDao basInspectRecordDao;
	@Autowired
	protected DocAnaesPacuRecDao docAnaesPacuRecDao;
	@Autowired
    protected DocAnaesPacuObserveRecDao docAnaesPacuObserveRecDao;
	@Autowired
	protected DocAnalgesicRecordDao docAnalgesicRecordDao;
	@Autowired
	protected TmpAnaesDoctempDao tmpAnaesDoctempDao;
	@Autowired
	protected TmpIoEventDao tmpIoEventDao;
	@Autowired
    protected TmpMedicineEventDao tmpMedicineEventDao;
	@Autowired
    protected TmpMedicineDao tmpMedicineDao;
	@Autowired
    protected TmpSciResearchFieldDao tmpSciResearchFieldDao;
	@Autowired
    protected TmpSciTempDao tmpSciTempDao;
	@Autowired
	protected BasCollectPacuDataDao basCollectPacuDataDao;
	@Autowired
	protected BasCollectPacuDataHisDao basCollectPacuDataHisDao;
	@Autowired
	protected DocAnaesSummaryAppendixCanalDao docAnaesSummaryAppendixCanalDao;
	@Autowired
	protected DocAnaesSummaryAppendixGenDao docAnaesSummaryAppendixGenDao;
	@Autowired
	protected DocAnaesSummaryAppendixVisitDao docAnaesSummaryAppendixVisitDao;
	@Autowired
	protected DocOptNurseRecordDao docOptNurseRecordDao;
	@Autowired
	protected BasColumnStyleDao basColumnStyleDao;
	@Autowired
	protected BasRowStyleDao basRowStyleDao;
	@Autowired
	protected BasTitleStyleDao basTitleStyleDao;
	@Autowired
	protected DocPrePostVisitItemDao docPrePostVisitItemDao;
	
	@Autowired
	protected EvtMedicalEventDetailDao evtMedicalEventDetailDao;
	
	@Autowired
	protected DocInsuredChargeInformDao docInsuredChargeInformDao;
	@Autowired
	protected DocInsuredChargeItemDao docInsuredChargeItemDao;
	@Autowired
    protected BasMonitorConfigDefaultDao basMonitorConfigDefaultDao;
	
	@Autowired
	protected BasBusEntityDao basBusEntityDao;
	
	@Autowired
	protected BasUserRoleDao basUserRoleDao;

	@Autowired
	protected BasRoleMenuDao basRoleMenuDao;
	
	@Autowired
	protected BasRoleDao basRoleDao;
	
	@Autowired
	protected BasUserDao basUserDao;

	@Autowired
	protected BasMenuDao basMenuDao;
	
	@Autowired
	protected BasOperateLogDao basOperateLogDao;
	
	@Autowired
	protected BasRegOptDao basRegOptDao;

	@Autowired
	protected BasDispatchDao basDispatchDao;
	
	@Autowired
	protected BasAnaesKndgbaseDao basAnaesKndgbaseDao;
	@Autowired
    protected BasDefaultOperatorDao basDefaultOperatorDao;
	@Autowired
	protected BasRegionBedDao basRegionBedDao;
	@Autowired
	protected BasRequiredItemDao basRequiredItemDao;
	
	@Autowired
	protected BasMedicalTakeReasonDao basMedicalTakeReasonDao;
	
	@Autowired
	protected BasMedicalTakeWayDao basMedicalTakeWayDao;
	
	@Autowired
    protected BasInstrsuitDao basInstrsuitDao;
	
	@Autowired
	protected BasMonitorPupilDao basMonitorPupilDao;
	
	@Autowired
	protected BasIconSvgDao basIconSvgDao;
	@Autowired
	protected DocPatOutRangeAgreeDao docPatOutRangeAgreeDao;
	
	@Autowired
	protected DocPrePostVisitDao docPrePostVisitDao;
	
	@Autowired
	protected DocPatShuttleTransferDao docPatShuttleTransferDao;
	
	@Autowired
	protected DocAnaesSummaryAllergicReactionDao docAnaesSummaryAllergicReactionDao;
	
	@Autowired
	protected DocAnaesSummaryVenipunctureDao docAnaesSummaryVenipunctureDao;

	@Autowired
	protected DocPostFollowRecordDao docPostFollowRecordDao;
	@Autowired
	protected DocPrevisitAccessexamDao docPrevisitAccessexamDao;
	@Autowired
	protected DocPrevisitAnaesplanDao docPrevisitAnaesplanDao;
	@Autowired
	protected DocPrevisitPhyexamDao docPrevisitPhyexamDao;
	@Autowired
	protected DocSelfPayAccedeDao docSelfPayAccedeDao;
	@Autowired
	protected DocSpinalCanalPunctureDao docSpinalCanalPunctureDao;
    @Autowired
    protected BasDocumentDao basDocumentDao;
    @Autowired
    protected EvtRescueeventDao evtRescueeventDao;
    @Autowired
    protected EvtRealAnaesMethodDao evtRealAnaesMethodDao;
    @Autowired
    protected EvtShiftChangeDao evtShiftChangeDao;
	@Autowired
	protected DocPatShuttleTransferContentDao docPatShuttleTransferContentDao;
    @Autowired
    protected DocPreOperVisitDao docPreOperVisitDao;
    @Autowired
    protected DocPostFollowAnalgesicDao docPostFollowAnalgesicDao;
    @Autowired
    protected DocPostFollowGeneralDao docPostFollowGeneralDao;
    @Autowired
    protected DocPostFollowSpinalDao docPostFollowSpinalDao;
    @Autowired
    protected BasPacuDeviceConfigDao basPacuDeviceConfigDao;
    @Autowired
    protected BasPacuBedEventConfigDao basPacuBedEventConfigDao;
    @Autowired
    protected BasPacuDeviceSpecificationDao basPacuDeviceSpecificationDao;
    @Autowired
    protected BasPacuMonitorConfigDao basPacuMonitorConfigDao;
    @Autowired
    protected BasAnaesMedicineInRecordDao basAnaesMedicineInRecordDao;
    @Autowired
    protected BasAnaesMedicineStorageDao basAnaesMedicineStorageDao;
    @Autowired
    protected BasAnaesMedicineOutRecordDao basAnaesMedicineOutRecordDao;
    @Autowired
    protected BasAnaesMedicineRetreatRecordDao basAnaesMedicineRetreatRecordDao;
    @Autowired
    protected BasAnaesMedicineLoseRecordDao basAnaesMedicineLoseRecordDao;
    @Autowired
    protected BasAnaesMedicineStorageHisDao basAnaesMedicineStorageHisDao;
    @Autowired
    protected DocInsuredPatAgreeDao docInsuredPatAgreeDao;
    @Autowired
    protected DocInsuredItemDao docInsuredItemDao;
    @Autowired
    protected DocTransferConnectRecordDao docTransferConnectRecordDao;
    @Autowired
    protected DocTransferConnectTypeDao docTransferConnectTypeDao;
    @Autowired
    protected DocPlacentaHandleAgreeDao docPlacentaHandleAgreeDao;
    @Autowired
    protected BasPacuDeviceMonitorConfigDao basPacuDeviceMonitorConfigDao;
    @Autowired
    protected DocNurseInterviewRecordDao docNurseInterviewRecordDao;
    @Autowired
    protected TmpPriChargeMedTempDao tmpPriChargeMedTempDao;
    @Autowired
    protected TmpChargePayTempDao tmpChargePayTempDao;
    @Autowired
    protected TmpMedPayTempDao tmpMedPayTempDao;
    @Autowired
    protected DocPostOperRegardDao docPostOperRegardDao;
    @Autowired
    protected DocAnalgesicPostflupDao docAnalgesicPostflupDao;
    @Autowired
    protected DocBloodTransItemDao docBloodTransItemDao;
    @Autowired
    protected DocBloodTransRecordDao docBloodTransRecordDao;
    @Autowired
    protected DocDocordRecordDao docDocordRecordDao;
    @Autowired
    protected DocPatCheckRecordDao docPatCheckRecordDao;
    @Autowired
    protected DocPatCheckItemDao docPatCheckItemDao;
    @Autowired
    protected TmpPacuLiquidTempDao tmpPacuLiquidTempDao;
    @Autowired
    protected BasBloodDefinationDao basBloodDefinationDao;
    @Autowired
    protected EvtElectDiogDataDao evtElectDiogDataDao;
    @Autowired
    protected BasSelfPayInstrumentDao basSelfPayInstrumentDao;
    @Autowired
    protected DocAnaesMedicineAccedeDao docAnaesMedicineAccedeDao;
    @Autowired
    protected DocSelfPayInstrumentAccedeDao docSelfPayInstrumentAccedeDao;
    @Autowired
    protected DocSelfPayInstrumentItemDao docSelfPayInstrumentItemDao;
    @Autowired
    protected DocVeinAccedeDao docVeinAccedeDao;
    @Autowired
    protected DocRiskEvaluatPreventCareDao docRiskEvaluatPreventCareDao;
    @Autowired
    protected BasPropertiesDao basPropertiesDao;
    @Autowired
    protected EvtAnaesModifyRecordDao evtAnaesModifyRecordDao;
    @Autowired
    protected BasAnaesEventDao basAnaesEventDao;
    @Autowired
    protected BasDictGroupDao basDictGroupDao;
    @Autowired
    protected BasDictItemDao basDictItemDao;
    @Autowired
    protected BasCustomConfigureDao basCustomConfigureDao;
    @Autowired
    protected BasViewDao basViewDao;
    @Autowired
    protected BasTaskScheduleDao basTaskScheduleDao;
    @Autowired
    protected DocThemeDao docThemeDao;
    @Autowired
    protected DocTitleDao docTitleDao;
    @Autowired
    protected BasOtherEventDao basOtherEventDao;
    @Autowired
    protected DocPatRescurRecordDao docPatRescurRecordDao;
    @Autowired
    protected DocDifficultCaseDiscussDao docDifficultCaseDiscussDao;
    @Autowired
    protected BasRoutingAccessControlDao basRoutingAccessControlDao;
    @Autowired
    protected TmpOthereventDao tmpOthereventDao;
    @Autowired
    protected DocAnaesMethodChangeRecordDao docAnaesMethodChangeRecordDao;
    @Autowired
    protected DocAnaesPreDiscussRecordDao docAnaesPreDiscussRecordDao;
    @Autowired
    protected BasConsumablesInRecordDao basConsumablesInRecordDao;
    @Autowired
    protected BasConsumablesStorageDao basConsumablesStorageDao;
    @Autowired
    protected BasConsumablesOutRecordDao basConsumablesOutRecordDao;
    @Autowired
    protected BasConsumablesRetreatRecordDao basConsumablesRetreatRecordDao;
    @Autowired
    protected BasConsumablesLoseRecordDao basConsumablesLoseRecordDao;
    @Autowired
    protected BasConsumablesStorageHisDao basConsumablesStorageHisDao;
    @Autowired
    protected BasPacuRecMonitorConfigDao basPacuRecMonitorConfigDao;
    @Autowired
    protected DocPostFollowYxrmDao docPostFollowYxrmDao;
    @Autowired
    protected DocAnalgesicInformedConsentDao docAnalgesicInformedConsentDao;
    @Autowired
    protected BasAnaesDoctorAmountDao basAnaesDoctorAmountDao;
    @Autowired
    protected DocAnaesQualityControlDao docAnaesQualityControlDao;
    @Autowired
    protected DocLaborAnalgesiaAccedeDao docLaborAnalgesiaAccedeDao;
    @Autowired
    protected TmpAnalgesicDao tmpAnalgesicDao;
    @Autowired
    protected EvtSpecialMaterialEventDao evtSpecialMaterialEventDao;
    @Autowired
    protected DocPreAnaesTalkRecordDao docPreAnaesTalkRecordDao;
    @Autowired
    protected DocPatPrevisitRecordDao docPatPrevisitRecordDao;
    @Autowired
    protected DocSelfPayAccedeLlzyyDao docSelfPayAccedeLlzyyDao;
    @Autowired
    protected DocPrePublicityDao docPrePublicityDao;
    @Autowired
    protected TmpToxicOutstockTempDao tmpToxicOutstockTempDao;
    @Autowired
    protected BasCollectConfigDao basCollectConfigDao;
    
    public String getIP(){  
        String ip=request.getHeader("x-forwarded-for");  
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){  
            ip=request.getHeader("Proxy-Client-IP");  
        }  
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){  
            ip=request.getHeader("WL-Proxy-Client-IP");  
        }  
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){  
            ip=request.getHeader("X-Real-IP");  
        }  
        if(ip==null || ip.length()==0 || "unknown".equalsIgnoreCase(ip)){  
            ip=request.getRemoteAddr();  
        }  
        return ip;  
    }  
    
    
    /**
     * 获取RoomID顺序
     * 1、根据regOptId的值获取对应手术室
     * 2、根据请求对象中的值获取
     * 3、根据配置文件的值获取
     * @param regOptId
     * @return
     */
    public String getCurRoomId(String regOptId){
    	String roomId = null;
    	if(StringUtils.isNotBlank(regOptId)){
    		if(CustomConfigureUtil.isSync()){
				roomId = basDispatchDao.getCurRoomIdByRegOptId(regOptId);
				logger.info("患者ID："+regOptId+",对应的手术室为："+roomId);
			}
    	}
    	if (StringUtils.isBlank(roomId)) {
	    	try {
				if (null != request && null != request.getHeader("roomId")) {
					roomId = request.getHeader("roomId");
				}
			} catch (IllegalStateException e) {
				logger.warn("特殊处理(取不到request的异常,不处理)：getCurRoomId--baseService");
			}
    	}
    	
    	if (StringUtils.isBlank(roomId)) {
			roomId = Global.getConfig("roomId").toString();
		}
    	
    	if (StringUtils.isBlank(roomId)) {
    		logger.error("特殊处理(找不到手术室信息)：getCurRoomId(String regOptId)：");
    	}
    	
    	return roomId;
    }
    
    
	protected static String getStackTrace(Throwable throwable) {
		return ExceptionUtils.getStackTrace(throwable);
	}
	
    
	public String getBeid() {
		String beid = null;
		try {
			if (null != request && null != request.getHeader("beid")) {
				beid = request.getHeader("beid");
			}
		} catch (IllegalStateException e) {
			//logger.error("特殊处理(取不到request的异常,不处理)：DataCollMgr--checkMonitorData--"+Exceptions.getStackTraceAsString(e));
			logger.warn("特殊处理(取不到request的异常,不处理)：getBeid--DataCollMgr--checkMonitorData");
		}
		//logger.info("beid==start=="+beid+"bool=="+StringUtils.isBlank(beid));
		if (StringUtils.isBlank(beid)) {
			beid = basBusEntityDao.getBeid();
			//logger.info("beid==isBlank=="+beid);
			if(StringUtils.isBlank(beid)){
				beid = Global.getConfig("operatorBeid").toString();
				//logger.info("beid==else=="+beid);
			}
		}
		//logger.info("beid===final==="+beid);
		return beid;
	}
	
//	public static void main(String[] args) {
//		String beid = null;
//		boolean bool = StringUtils.isBlank(beid);
//		System.out.println(bool);
//	}
	/**
	 * 设置查询参数的beid
	 * @param systemSearchFormBean
	 * @return
	 */
	public SystemSearchFormBean setBeid(SystemSearchFormBean searchFormBean) {
		if (StringUtils.isBlank(searchFormBean.getBeid())) {
			searchFormBean.setBeid(getBeid());
		}
		return searchFormBean;
	}
	
	public String getUserName(){
		return request.getHeader("username");
	}
	
	/**
	 * 获取module
	 * @return
	 */
	public String getModule(){
		String module = request.getHeader("module");//从header中获取module
		if(StringUtils.isBlank(module)){
			module = Global.getConfig("defModule").toString(); //取配置文件中的module
		}
		return module;
	}
	
	/** 
     * 创建文书
     * <功能详细描述>
     * @param regOpt
     * @see [类、类#方法、类#成员]
     */
    public void creatDocument(BasRegOpt regOpt)
    {
        List<String> tables = basDocumentDao.searchAllTables(getBeid());
        String regOptId = regOpt.getRegOptId();
        
        if (tables.contains("doc_pre_visit"))
        {
            DocPreVisit preVisit = new DocPreVisit();
            preVisit.setPreVisitId(GenerateSequenceUtil.generateSequenceNo());
            preVisit.setRegOptId(regOptId);
            preVisit.setProcessState("NO_END");
            docPreVisitDao.insert(preVisit);
        }
        
        if (tables.contains("doc_pre_oper_visit"))
        {
            // 创建术前访视单
            DocPreOperVisit docPreOperVisit = new DocPreOperVisit();
            docPreOperVisit.setId(GenerateSequenceUtil.generateSequenceNo());
            docPreOperVisit.setRegOptId(regOptId);
            docPreOperVisit.setProcessState("NO_END");
            docPreOperVisitDao.insert(docPreOperVisit);
        }
        
        if (tables.contains("doc_labor_analgesia_accede"))
        {
            // 创建分娩镇痛同意书
            DocLaborAnalgesiaAccede laborAccede = new DocLaborAnalgesiaAccede();
            laborAccede.setLaborId(GenerateSequenceUtil.generateSequenceNo());
            laborAccede.setRegOptId(regOptId);
            laborAccede.setProcessState("NO_END");
            docLaborAnalgesiaAccedeDao.insert(laborAccede);
        }
        
        if (tables.contains("doc_accede"))
        {
            // 创建麻醉同意书
            DocAccede accede = new DocAccede();
            accede.setAccedeId(GenerateSequenceUtil.generateSequenceNo());
            accede.setRegOptId(regOptId);
            accede.setFlag("1");
            accede.setProcessState("NO_END");
            docAccedeDao.insert(accede);
        }
        
        if (tables.contains("doc_anaes_plan"))
        {
            //麻醉计划单
            DocAnaesPlan anaesPlan = new DocAnaesPlan();
            anaesPlan.setRegOptId(regOptId);
            anaesPlan.setProcessState("NO_END");
            anaesPlan.setId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesPlanDao.insert(anaesPlan);
        }
        
        if (tables.contains("doc_pat_out_range_agree"))
        {
            //医疗保险病人超范围用药同意书
            DocPatOutRangeAgree patOutRangeAgree = new DocPatOutRangeAgree();
            patOutRangeAgree.setRegOptId(regOptId);
            patOutRangeAgree.setProcessState("NO_END");
            patOutRangeAgree.setId(GenerateSequenceUtil.generateSequenceNo());
            docPatOutRangeAgreeDao.insert(patOutRangeAgree);
        }
        
        if (tables.contains("doc_pre_post_visit"))
        {
            //手术病人术前术后访问记录单
            DocPrePostVisit prePostVisit = new DocPrePostVisit();
            prePostVisit.setRegOptId(regOptId);
            prePostVisit.setProcessState("NO_END");
            prePostVisit.setId(GenerateSequenceUtil.generateSequenceNo());
            docPrePostVisitDao.insert(prePostVisit);
        }
        
        if (tables.contains("doc_pat_shuttle_transfer"))
        {
            //手术患者接送交接单
            DocPatShuttleTransfer patShuttleTransfer = new DocPatShuttleTransfer();
            patShuttleTransfer.setRegOptId(regOptId);
            patShuttleTransfer.setProcessState("NO_END");
            patShuttleTransfer.setId(GenerateSequenceUtil.generateSequenceNo());
            docPatShuttleTransferDao.insert(patShuttleTransfer);
        }
        
        if (tables.contains("doc_opt_risk_evaluation"))
        {
            //创建手术风险评估单 
            DocOptRiskEvaluation optRiskEvaluatio = new DocOptRiskEvaluation();
            optRiskEvaluatio.setRegOptId(regOptId);
            optRiskEvaluatio.setOptRiskEvaluationId(GenerateSequenceUtil.generateSequenceNo());
            optRiskEvaluatio.setProcessState("NO_END");
            optRiskEvaluatio.setFlag("1");
            docOptRiskEvaluationDao.insert(optRiskEvaluatio);
        }
        
        if (tables.contains("doc_anaes_record"))
        {
            //创建麻醉记录单
            DocAnaesRecord anaesRecord = new DocAnaesRecord();
            anaesRecord.setAnaRecordId(GenerateSequenceUtil.generateSequenceNo());
            anaesRecord.setOther("O2L/min");
            anaesRecord.setProcessState("NO_END");
            anaesRecord.setRegOptId(regOptId);
            docAnaesRecordDao.insert(anaesRecord);
        }
        
        if (tables.contains("doc_anaes_summary"))
        {
            //麻醉总结单
            DocAnaesSummary anaesSummary = new DocAnaesSummary();
            anaesSummary.setRegOptId(regOptId);
            anaesSummary.setProcessState("NO_END");
            anaesSummary.setAnaSumId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesSummaryDao.insert(anaesSummary);
            //椎管内麻醉
            DocAnaesSummaryAppendixCanal anaesSummaryAppendixCanal = new DocAnaesSummaryAppendixCanal();
            anaesSummaryAppendixCanal.setAnaSumId(anaesSummary.getAnaSumId());
            anaesSummaryAppendixCanal.setAnaSumAppCanId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesSummaryAppendixCanalDao.insert(anaesSummaryAppendixCanal);
            //全麻
            DocAnaesSummaryAppendixGen anaesSummaryAppendixGen = new DocAnaesSummaryAppendixGen();
            anaesSummaryAppendixGen.setAnaSumId(anaesSummary.getAnaSumId());
            anaesSummaryAppendixGen.setAnaSumAppGenId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesSummaryAppendixGenDao.insert(anaesSummaryAppendixGen);
            //术后访视
            DocAnaesSummaryAppendixVisit anaesSummaryAppendixVisit = new DocAnaesSummaryAppendixVisit();
            anaesSummaryAppendixVisit.setAnaSumId(anaesSummary.getAnaSumId());
            anaesSummaryAppendixVisit.setAnesSumVisId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesSummaryAppendixVisitDao.insert(anaesSummaryAppendixVisit);
            //椎管内穿刺
            DocSpinalCanalPuncture spinalCanalPuncture = new DocSpinalCanalPuncture();
            spinalCanalPuncture.setAnaSumId(anaesSummary.getAnaSumId());
            spinalCanalPuncture.setSpinalCanalId(GenerateSequenceUtil.generateSequenceNo());
            docSpinalCanalPunctureDao.insert(spinalCanalPuncture);
            //神经阻滞
            DocNerveBlock nerveBlock = new DocNerveBlock();
            nerveBlock.setAnaSumId(anaesSummary.getAnaSumId());
            nerveBlock.setNerveBlockId(GenerateSequenceUtil.generateSequenceNo());
            docNerveBlockDao.insert(nerveBlock);
            //全身麻醉
            DocGeneralAnaes generalAnaes = new DocGeneralAnaes();
            generalAnaes.setAnaSumId(anaesSummary.getAnaSumId());
            generalAnaes.setGeneralAnaesId(GenerateSequenceUtil.generateSequenceNo());
            docGeneralAnaesDao.insert(generalAnaes);
            //并发症
            DocAnaesSummaryAllergicReaction anaesSummaryAllergicReaction = new DocAnaesSummaryAllergicReaction();
            anaesSummaryAllergicReaction.setAnaSumId(anaesSummary.getAnaSumId());
            anaesSummaryAllergicReaction.setAnaSumAllReaId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesSummaryAllergicReactionDao.insert(anaesSummaryAllergicReaction);
            //中心静脉穿刺
            DocAnaesSummaryVenipuncture  anaesSummaryVenipuncture = new DocAnaesSummaryVenipuncture();
            anaesSummaryVenipuncture.setAnaSumId(anaesSummary.getAnaSumId());
            anaesSummaryVenipuncture.setAnesSumVenId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesSummaryVenipunctureDao.insert(anaesSummaryVenipuncture);
        }
        
        if (tables.contains("doc_opt_care_record"))
        {
            //创建手术护理记录文书
            DocOptCareRecord optCareRecord = new DocOptCareRecord();
            optCareRecord.setRegOptId(regOptId);
            optCareRecord.setId(GenerateSequenceUtil.generateSequenceNo());
            optCareRecord.setProcessState("NO_END");
            docOptCareRecordDao.insert(optCareRecord);
        }
        
        if (tables.contains("doc_opt_nurse"))
        {
            //创建手术清点记录
            DocOptNurse optNurse = new DocOptNurse();
            optNurse.setRegOptId(regOptId);
            optNurse.setOptNurseId(GenerateSequenceUtil.generateSequenceNo());
            optNurse.setProcessState("NO_END");
            docOptNurseDao.insert(optNurse);
        }
        
        if (tables.contains("doc_safe_check"))
        {
            //创建手术核查单
            DocSafeCheck safeCheck = new DocSafeCheck();
            safeCheck.setRegOptId(regOptId);
            safeCheck.setProcessState("NO_END");
            safeCheck.setSafCheckId(GenerateSequenceUtil.generateSequenceNo());
            docSafeCheckDao.insert(safeCheck);
            DocAnaesBeforeSafeCheck anaesBeforeSafeCheck = new DocAnaesBeforeSafeCheck();
            anaesBeforeSafeCheck.setRegOptId(regOptId);
            anaesBeforeSafeCheck.setAnesBeforeId(GenerateSequenceUtil.generateSequenceNo());
            anaesBeforeSafeCheck.setProcessState("NO_END");
            docAnaesBeforeSafeCheckDao.insert(anaesBeforeSafeCheck);
            DocOperBeforeSafeCheck operBeforeSafeCheck = new DocOperBeforeSafeCheck();
            operBeforeSafeCheck.setRegOptId(regOptId);
            operBeforeSafeCheck.setOperBeforeId(GenerateSequenceUtil.generateSequenceNo());
            operBeforeSafeCheck.setProcessState("NO_END");
            docOperBeforeSafeCheckDao.insert(operBeforeSafeCheck);
            DocExitOperSafeCheck exitOperSafeCheck = new DocExitOperSafeCheck();
            exitOperSafeCheck.setRegOptId(regOptId);
            exitOperSafeCheck.setProcessState("NO_END");
            exitOperSafeCheck.setExitOperId(GenerateSequenceUtil.generateSequenceNo());
            docExitOperSafeCheckDao.insert(exitOperSafeCheck);
        }
        
        if (tables.contains("doc_post_follow_record"))
        {
            //术后随访记录单
            DocPostFollowRecord postFollowRecord = new DocPostFollowRecord();
            postFollowRecord.setRegOptId(regOptId);
            postFollowRecord.setProcessState("NO_END");
            postFollowRecord.setPostFollowId(GenerateSequenceUtil.generateSequenceNo());
            docPostFollowRecordDao.insert(postFollowRecord);
        }
        
        if (tables.contains("doc_insured_pat_agree"))
        {
            //参保患者特殊用药、卫材知情单
            DocInsuredPatAgree insuredPatAgree = new DocInsuredPatAgree();
            insuredPatAgree.setRegOptId(regOptId);
            insuredPatAgree.setProcessState("NO_END");
            insuredPatAgree.setId(GenerateSequenceUtil.generateSequenceNo());
            docInsuredPatAgreeDao.insert(insuredPatAgree);
        }
        
        if (tables.contains("doc_transfer_connect_record"))
        {
            // 手术病人转运交接记录单
            DocTransferConnectRecord transferConnectRecord = new DocTransferConnectRecord();
            transferConnectRecord.setRegOptId(regOptId);
            transferConnectRecord.setProcessState("NO_END");
            transferConnectRecord.setId(GenerateSequenceUtil.generateSequenceNo());
            docTransferConnectRecordDao.insert(transferConnectRecord);
        }
        
        if (tables.contains("doc_placenta_handle_agree"))
        {
            // 胎盘处置知情同意书
            DocPlacentaHandleAgree placentaHandleAgree = new DocPlacentaHandleAgree();
            placentaHandleAgree.setRegOptId(regOptId);
            placentaHandleAgree.setProcessState("NO_END");
            placentaHandleAgree.setId(GenerateSequenceUtil.generateSequenceNo());
            docPlacentaHandleAgreeDao.insert(placentaHandleAgree);
        }
        
        if (tables.contains("doc_nurse_interview_record"))
        {
            //手术室护理工作访视记录
            DocNurseInterviewRecord nurseInterviewRecord = new DocNurseInterviewRecord();
            nurseInterviewRecord.setRegOptId(regOptId);
            nurseInterviewRecord.setProcessState("NO_END");
            nurseInterviewRecord.setId(GenerateSequenceUtil.generateSequenceNo());
            docNurseInterviewRecordDao.insert(nurseInterviewRecord);
        }
        
        if (tables.contains("doc_post_oper_regard"))
        {
            // 术后回视
            DocPostOperRegard docPostOperRegard = new DocPostOperRegard();
            docPostOperRegard.setRegOptId(regOptId);
            docPostOperRegard.setProcessState("NO_END");
            docPostOperRegard.setId(GenerateSequenceUtil.generateSequenceNo());
            docPostOperRegardDao.insert(docPostOperRegard);
        }
        
        if (tables.contains("doc_anaes_postop"))
        {
            //麻醉后访视记录单
            DocAnaesPostop docAnaesPostop = new DocAnaesPostop();
            docAnaesPostop.setRegOptId(regOptId);
            docAnaesPostop.setProcessState("NO_END");
            docAnaesPostop.setAnaPostopId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesPostopDao.insert(docAnaesPostop);
        }
        
        if (tables.contains("doc_analgesic_record"))
        {
            //自控记录单
            DocAnalgesicRecord analgesic = new DocAnalgesicRecord();
            analgesic.setRegOptId(regOptId);
            analgesic.setProcessState("NO_END");
            analgesic.setState(OperationState.SURGERY);
            analgesic.setId(GenerateSequenceUtil.generateSequenceNo());
            docAnalgesicRecordDao.insert(analgesic);
        }
        
        if (tables.contains("doc_anaes_medicine_accede"))
        {
            //手术麻醉使用药品知情同意书
            DocAnaesMedicineAccede anaesMedicineAccede = new DocAnaesMedicineAccede();
            anaesMedicineAccede.setRegOptId(regOptId);
            anaesMedicineAccede.setProcessState("NO_END");
            anaesMedicineAccede.setId(GenerateSequenceUtil.generateSequenceNo());
            docAnaesMedicineAccedeDao.insert(anaesMedicineAccede);
        }
        
        if (tables.contains("doc_self_pay_instrument_accede"))
        {
            //手术麻醉使用自费及高价耗材知情同意书
            DocSelfPayInstrumentAccede selfPayInstrumentAccede = new DocSelfPayInstrumentAccede();
            selfPayInstrumentAccede.setRegOptId(regOptId);
            selfPayInstrumentAccede.setProcessState("NO_END");
            selfPayInstrumentAccede.setId(GenerateSequenceUtil.generateSequenceNo());
            docSelfPayInstrumentAccedeDao.insert(selfPayInstrumentAccede);
        }
        
        if (tables.contains("doc_vein_accede"))
        {
            //静脉麻醉知情同意书
            DocVeinAccede docVeinAccede = new DocVeinAccede();
            docVeinAccede.setRegOptId(regOptId);
            docVeinAccede.setProcessState("NO_END");
            docVeinAccede.setId(GenerateSequenceUtil.generateSequenceNo());
            docVeinAccedeDao.insert(docVeinAccede);
        }
        
        if (tables.contains("doc_risk_evaluat_prevent_care"))
        {
            //手术病人术前风险评估及预防护理记录单
            DocRiskEvaluatPreventCare docRiskEvaluatPreventCare = new DocRiskEvaluatPreventCare();
            docRiskEvaluatPreventCare.setRegOptId(regOptId);
            docRiskEvaluatPreventCare.setProcessState("NO_END");
            docRiskEvaluatPreventCare.setId(GenerateSequenceUtil.generateSequenceNo());
            docRiskEvaluatPreventCareDao.insert(docRiskEvaluatPreventCare);
        }
        
        if (tables.contains("doc_blood_trans_record"))
        {
            //临床输血记录单
            DocBloodTransRecord docBloodTransRecord = new DocBloodTransRecord();
            docBloodTransRecord.setRegOptId(regOptId);
            docBloodTransRecord.setProcessState("NO_END");
            docBloodTransRecord.setBloodTransId(GenerateSequenceUtil.generateSequenceNo());
            docBloodTransRecordDao.insert(docBloodTransRecord);
        }
        
        if (tables.contains("doc_difficult_case_discuss"))
        {
            //疑难病人讨论记录
            DocDifficultCaseDiscuss difficultCaseDiscuss = new DocDifficultCaseDiscuss();
            difficultCaseDiscuss.setRegOptId(regOptId);
            difficultCaseDiscuss.setProcessState("NO_END");
            difficultCaseDiscuss.setId(GenerateSequenceUtil.generateSequenceNo());
            docDifficultCaseDiscussDao.insert(difficultCaseDiscuss);
        }
        
        if (tables.contains("doc_pat_rescue_record"))
        {
            //危重病人抢救记录
            DocPatRescurRecord patRescurRecord = new DocPatRescurRecord();
            patRescurRecord.setRegOptId(regOptId);
            patRescurRecord.setProcessState("NO_END");
            patRescurRecord.setId(GenerateSequenceUtil.generateSequenceNo());
            docPatRescurRecordDao.insert(patRescurRecord);
        }
        
        if (tables.contains("doc_anaes_pre_discuss_record"))
        {
            //危重病人抢救记录
        	DocAnaesPreDiscussRecord docAnaesPreDiscussRecord = new DocAnaesPreDiscussRecord();
        	docAnaesPreDiscussRecord.setRegOptId(regOptId);
        	docAnaesPreDiscussRecord.setProcessState("NO_END");
        	docAnaesPreDiscussRecord.setPreDiscussId(GenerateSequenceUtil.generateSequenceNo());
        	docAnaesPreDiscussRecordDao.insert(docAnaesPreDiscussRecord);
        }
        
        if (tables.contains("doc_analgesic_informed_consent"))
        {
            //术后（术前）镇痛知情同意书
        	DocAnalgesicInformedConsent docAnalgesicInformedConsent = new DocAnalgesicInformedConsent();
        	docAnalgesicInformedConsent.setRegOptId(regOptId);
        	docAnalgesicInformedConsent.setProcessState("NO_END");
        	docAnalgesicInformedConsent.setAnalgesicId(GenerateSequenceUtil.generateSequenceNo());
        	docAnalgesicInformedConsentDao.insert(docAnalgesicInformedConsent);
        }
        //麻醉科自费耗材知情同意书
        if (tables.contains("doc_self_pay_accede_llzyy"))
        {
        	DocSelfPayAccedeLlzyy selfPayAccedeLlzyy = new DocSelfPayAccedeLlzyy();
        	selfPayAccedeLlzyy.setId(GenerateSequenceUtil.generateSequenceNo());
        	selfPayAccedeLlzyy.setRegOptId(regOptId);
        	selfPayAccedeLlzyy.setProcessState("NO_END");
        	docSelfPayAccedeLlzyyDao.insert(selfPayAccedeLlzyy);
        }
        //麻醉手术室术前宣教
        if (tables.contains("doc_pre_publicity"))
        {
        	DocPrePublicity docPrePublicity = new DocPrePublicity();
        	docPrePublicity.setId(GenerateSequenceUtil.generateSequenceNo());
        	docPrePublicity.setRegOptId(regOptId);
        	docPrePublicity.setProcessState("NO_END");
        	docPrePublicityDao.insert(docPrePublicity);
        }
        if (tables.contains("doc_pat_previsit_record"))
        {
            //患者随访管理
        	DocPatPrevisitRecord docPatPrevisitRecord = new DocPatPrevisitRecord();
        	docPatPrevisitRecord.setRegOptId(regOptId);
        	docPatPrevisitRecord.setProcessState("NO_END");
        	docPatPrevisitRecord.setPatVisitId(GenerateSequenceUtil.generateSequenceNo());
        	docPatPrevisitRecordDao.insert(docPatPrevisitRecord);
        }
        
        
        DocAnaesQualityControl docAnaesQualityControl = new DocAnaesQualityControl();
        docAnaesQualityControl.setRegOptId(regOptId);
        docAnaesQualityControl.setId(GenerateSequenceUtil.generateSequenceNo());
        docAnaesQualityControlDao.insert(docAnaesQualityControl);
        
        //在审核的时候  生成排程信息记录 
        int dispatchCount = basDispatchDao.searchDistchByRegOptId(regOptId);
        if(dispatchCount<1){
            BasDispatch dispatch = new BasDispatch();
            dispatch.setRegOptId(regOptId);
            dispatch.setBeid(getBeid());
            basDispatchDao.insert(dispatch);
        }
    }
    
	public String getControlCenterIp() {
		return request.getLocalAddr();
	}
	
	public int getControlCenterPort() {
		return request.getLocalPort();
	}
}
