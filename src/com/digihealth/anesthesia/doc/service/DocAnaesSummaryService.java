/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author liukui       
 * @created 2015-10-10 下午5:32:33    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.service.BasOperateLogService;
import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.formbean.AnaesSummaryItemFormbean;
import com.digihealth.anesthesia.doc.po.DocAnaesSummary;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAllergicReaction;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixCanal;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixGen;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixVisit;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryVenipuncture;
import com.digihealth.anesthesia.doc.po.DocGeneralAnaes;
import com.digihealth.anesthesia.doc.po.DocNerveBlock;
import com.digihealth.anesthesia.doc.po.DocSpinalCanalPuncture;
import com.digihealth.anesthesia.evt.formbean.SearchFormBean;
import com.digihealth.anesthesia.sysMng.po.BasUser;

/**
 * Title: AnaesSummaryService.java Description: 麻醉总结查询
 * 
 * @author liukui
 * @created 2015-10-10 下午5:32:33
 */
@Service
public class DocAnaesSummaryService extends BaseService {
	/**
	 * 
	 * @discription 根据手术ID获取麻醉总结单
	 * @author liukui
	 * @created 2015-10-10 下午5:13:48
	 * @param SearchFormBean
	 * @return
	 */
	public List<DocAnaesSummary> searchAnaesSummaryList(SearchFormBean searchBean) {
		return docAnaesSummaryDao.searchAnaesSummaryList(searchBean);
	}
	
	public AnaesSummaryItemFormbean getAnaesSummaryDetail(String regOptId) {
		AnaesSummaryItemFormbean anaesSummaryItemFormbean = new AnaesSummaryItemFormbean();

		DocAnaesSummary anaesSummary = docAnaesSummaryDao.getAnaesSummaryByRegOptId(regOptId);
		
		anaesSummary.setAnaesDocList(StringUtils.getListByString(anaesSummary.getAnesthetistId()));
		

		if (null != anaesSummary) {
			anaesSummaryItemFormbean.setAnaesSummary(anaesSummary);
			// 椎管内穿刺
			anaesSummaryItemFormbean.setAnaesSummaryAppendixCanal(docAnaesSummaryAppendixCanalDao.getPoByAnaSumId(anaesSummary.getAnaSumId()));
			// 全麻
			anaesSummaryItemFormbean.setAnaesSummaryAppendixGen(docAnaesSummaryAppendixGenDao.getPoByAnaSumId(anaesSummary.getAnaSumId()));
			// 术后访视
			anaesSummaryItemFormbean.setAnaesSummaryAppendixVisit(docAnaesSummaryAppendixVisitDao.getPoByAnaSumId(anaesSummary.getAnaSumId()));
			anaesSummaryItemFormbean.setAnaesSummaryVenipuncture(docAnaesSummaryVenipunctureDao.getPoByAnaSumId(anaesSummary.getAnaSumId()));
			anaesSummaryItemFormbean.setAnaesSummaryAllergicReaction(docAnaesSummaryAllergicReactionDao.getPoByAnaSumId(anaesSummary.getAnaSumId()));
		}
		return anaesSummaryItemFormbean;
	}
	
	/**
	 * 新增麻醉总结
	 * @param anaesSummary
	 * @return
	 */
	@Transactional
	public void insertAnaesSummary(DocAnaesSummary anaesSummary){
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
	
	}
	/**
	 * 
	 * @discription 保存麻醉总结
	 * @author liukui
	 * @created 2015-10-20 下午1:44:18
	 * @param preVisit
	 * @return
	 */
	@Transactional
	public void saveAnaesSummaryDetail(AnaesSummaryItemFormbean anaesSummaryItemFormbean) {
		//获取麻醉总结单信息更新麻醉总结表数据
		
		DocAnaesSummary anaesSummary = anaesSummaryItemFormbean.getAnaesSummary();
		
		if(null!=anaesSummary.getAnaesDocList() && anaesSummary.getAnaesDocList().size()>0){
			anaesSummary.setAnesthetistId(StringUtils.getStringByList(anaesSummary.getAnaesDocList()));
		}
		
		//anaesSummary.setProcessState("END");
		docAnaesSummaryDao.updateByPrimaryKey(anaesSummary);
		
		/**
		 * 当麻醉记录单附页为已完成状态，病人自控镇痛输入无时，将镇痛记录单状态设置为已完成状态
		 */
//		if(anaesSummary.getProcessState().equals("END") && 1 == anaesSummary.getControAnal()){
//			DocAnalgesicRecord analg = docAnalgesicRecordDao.searchAnalgesicRecordByPatId(anaesSummary.getRegOptId());
//			analg.setProcessState("END");
//			docAnalgesicRecordDao.updateByPrimaryKeySelective(analg);
//		}
		
		//椎管内穿刺
//		anaesSummaryItemFormbean.setAnaesSummaryAppendixCanal(docAnaesSummaryAppendixCanalDao.getPoByAnaSumId(anaesSummary.getAnaSumId()));
		//全麻
//		anaesSummaryItemFormbean.setAnaesSummaryAppendixGen(docAnaesSummaryAppendixGenDao.getPoByAnaSumId(anaesSummary.getAnaSumId()));
		//术后访视
//		anaesSummaryItemFormbean.setAnaesSummaryAppendixVisit(docAnaesSummaryAppendixVisitDao.getPoByAnaSumId(anaesSummary.getAnaSumId()));

		//椎管内穿刺
		docAnaesSummaryAppendixCanalDao.updateByPrimaryKey(anaesSummaryItemFormbean.getAnaesSummaryAppendixCanal());
		//全麻
		docAnaesSummaryAppendixGenDao.updateByPrimaryKey(anaesSummaryItemFormbean.getAnaesSummaryAppendixGen());
		//术后访视
		docAnaesSummaryAppendixVisitDao.updateByPrimaryKey(anaesSummaryItemFormbean.getAnaesSummaryAppendixVisit());
		//并发症
//		allergicReactionDao.updateByPrimaryKey(anaesSummaryItemFormbean.getAnaesSummaryAllergicReaction());
		//中心静脉穿刺
		docAnaesSummaryVenipunctureDao.updateByPrimaryKey(anaesSummaryItemFormbean.getAnaesSummaryVenipuncture());
		docAnaesSummaryAllergicReactionDao.updateByPrimaryKey(anaesSummaryItemFormbean.getAnaesSummaryAllergicReaction());
		BasUser user = UserUtils.getUserCache();
		LogUtils.saveOperateLog(anaesSummary.getRegOptId(), "4",
            "2", "麻醉记录单二修改", JsonType.jsonType(anaesSummaryItemFormbean),user, getBeid());
	}
	
	/**
     * 
     * @discription 保存麻醉总结(南华局点)
     * @author liukui
     * @created 2015-10-20 下午1:44:18
     * @param preVisit
     * @return
     */
    @Transactional
    public void saveAnaesSummaryDetailForQNZZY(AnaesSummaryItemFormbean anaesSummaryItemFormbean) {
        //获取麻醉总结单信息更新麻醉总结表数据
        DocAnaesSummary anaesSummary = anaesSummaryItemFormbean.getAnaesSummary();
        String  anesthetistId = StringUtils.getStringByList(anaesSummary.getAnaesDocList());
        anaesSummary.setAnesthetistId(anesthetistId);
        docAnaesSummaryDao.updateByPrimaryKey(anaesSummary); 
        
        //椎管内穿刺
        DocSpinalCanalPuncture spinalCanalPuncture = anaesSummaryItemFormbean.getSpinalCanalPuncture();
        spinalCanalPuncture.setPuncPoint(StringUtils.getStringByList(spinalCanalPuncture.getPuncPointList()));
        spinalCanalPuncture.setPuncBodyPoint(String.valueOf(spinalCanalPuncture.getPuncBodyPointMap()));
        docSpinalCanalPunctureDao.updateByPrimaryKey(spinalCanalPuncture);
        //神经阻滞
        DocNerveBlock nerveBlock = anaesSummaryItemFormbean.getNerveBlock();
        docNerveBlockDao.updateByPrimaryKey(nerveBlock);
        //全身麻醉
        DocGeneralAnaes generalAnaes = anaesSummaryItemFormbean.getGeneralAnaes();
        docGeneralAnaesDao.updateByPrimaryKey(generalAnaes);
        
        //麻醉期间严重过敏反应
        DocAnaesSummaryAllergicReaction allergicReaction = anaesSummaryItemFormbean.getAllergicReact();
        docAnaesSummaryAllergicReactionDao.updateByPrimaryKey(allergicReaction);
        
        //中心静脉穿刺并发症
        DocAnaesSummaryVenipuncture venipuncture = anaesSummaryItemFormbean.getVenipuncture();
        docAnaesSummaryVenipunctureDao.updateByPrimaryKey(venipuncture);
        
        BasUser user = UserUtils.getUserCache();
        LogUtils.saveOperateLog(anaesSummary.getRegOptId(), BasOperateLogService.OPT_TYPE_INFO_SAVE,
            BasOperateLogService.OPT_MODULE_OPER_DOC, "麻醉记录单二修改", JsonType.jsonType(anaesSummaryItemFormbean),user,getBeid());
    }
    
    
	/**
     * 
     * @discription 保存麻醉总结(南华局点)
     * @author liukui
     * @created 2015-10-20 下午1:44:18
     * @param preVisit
     * @return
     */
    @Transactional
    public void saveAnaesSummaryDetailForSYZXYY(AnaesSummaryItemFormbean anaesSummaryItemFormbean) {
        //获取麻醉总结单信息更新麻醉总结表数据
        DocAnaesSummary anaesSummary = anaesSummaryItemFormbean.getAnaesSummary();
        String  anesthetistId = StringUtils.getStringByList(anaesSummary.getAnaesDocList());
        anaesSummary.setAnesthetistId(anesthetistId);
        docAnaesSummaryDao.updateByPrimaryKey(anaesSummary); 
        
        //椎管内穿刺
        DocSpinalCanalPuncture spinalCanalPuncture = anaesSummaryItemFormbean.getSpinalCanalPuncture();
        spinalCanalPuncture.setPuncBodyPoint(String.valueOf(spinalCanalPuncture.getPuncBodyPointMap()));
        spinalCanalPuncture.setIntubation(String.valueOf(spinalCanalPuncture.getIntubationMap()));
        docSpinalCanalPunctureDao.updateByPrimaryKey(spinalCanalPuncture);
        //神经阻滞
        DocNerveBlock nerveBlock = anaesSummaryItemFormbean.getNerveBlock();
        docNerveBlockDao.updateByPrimaryKey(nerveBlock);
        //全身麻醉
        DocGeneralAnaes generalAnaes = anaesSummaryItemFormbean.getGeneralAnaes();
        //generalAnaes.setDoubleCavityCatheter(String.valueOf(generalAnaes.getDoubleCavityCatheterMap()));
        //generalAnaes.setIntubationDepth(String.valueOf(generalAnaes.getIntubationDepthMap()));
        //generalAnaes.setLaryngealMask(String.valueOf(generalAnaes.getLaryngealMaskMap()));
        //generalAnaes.setCatheterId(String.valueOf(generalAnaes.getCatheterIdMap()));
        docGeneralAnaesDao.updateByPrimaryKey(generalAnaes);
        
        //麻醉期间严重过敏反应
        DocAnaesSummaryAllergicReaction allergicReaction = anaesSummaryItemFormbean.getAllergicReact();
        docAnaesSummaryAllergicReactionDao.updateByPrimaryKey(allergicReaction);
        
        //中心静脉穿刺并发症
        DocAnaesSummaryVenipuncture venipuncture = anaesSummaryItemFormbean.getVenipuncture();
        docAnaesSummaryVenipunctureDao.updateByPrimaryKey(venipuncture);
        
        BasUser user = UserUtils.getUserCache();
        LogUtils.saveOperateLog(anaesSummary.getRegOptId(), BasOperateLogService.OPT_TYPE_INFO_SAVE,
            BasOperateLogService.OPT_MODULE_OPER_DOC, "麻醉记录单二修改", JsonType.jsonType(anaesSummaryItemFormbean),user,getBeid());
    }
    
    
    
	/**
     * 
     * @discription 保存麻醉总结(永兴局点)
     * @author 彭清
     * @created 2018-30-20 下午1:44:18
     * @param preVisit
     * @return
     */
    @Transactional
    public void saveAnaesSummaryDetailForYXRM(AnaesSummaryItemFormbean anaesSummaryItemFormbean) {
        //获取麻醉总结单信息更新麻醉总结表数据
        DocAnaesSummary anaesSummary = anaesSummaryItemFormbean.getAnaesSummary();
        String  anesthetistId = StringUtils.getStringByList(anaesSummary.getAnaesDocList());
        anaesSummary.setAnesthetistId(anesthetistId);
        docAnaesSummaryDao.updateByPrimaryKey(anaesSummary); 
        
        //椎管内穿刺
        DocSpinalCanalPuncture spinalCanalPuncture = anaesSummaryItemFormbean.getSpinalCanalPuncture();
        docSpinalCanalPunctureDao.updateByPrimaryKey(spinalCanalPuncture);
        //神经阻滞
        DocNerveBlock nerveBlock = anaesSummaryItemFormbean.getNerveBlock();
        docNerveBlockDao.updateByPrimaryKey(nerveBlock);
        //全身麻醉
        DocGeneralAnaes generalAnaes = anaesSummaryItemFormbean.getGeneralAnaes();
        docGeneralAnaesDao.updateByPrimaryKey(generalAnaes);
        
        //麻醉期间严重过敏反应
        //DocAnaesSummaryAllergicReaction allergicReaction = anaesSummaryItemFormbean.getAllergicReact();
        //docAnaesSummaryAllergicReactionDao.updateByPrimaryKey(allergicReaction);
        
        //中心静脉穿刺并发症
        //DocAnaesSummaryVenipuncture venipuncture = anaesSummaryItemFormbean.getVenipuncture();
        //docAnaesSummaryVenipunctureDao.updateByPrimaryKey(venipuncture);
        
        //术后随访
        DocAnaesSummaryAppendixVisit anaesSummaryAppendixVisit = anaesSummaryItemFormbean.getAnaesSummaryAppendixVisit();
        docAnaesSummaryAppendixVisitDao.updateByPrimaryKey(anaesSummaryAppendixVisit);
        
        BasUser user = UserUtils.getUserCache();
        LogUtils.saveOperateLog(anaesSummary.getRegOptId(), BasOperateLogService.OPT_TYPE_INFO_SAVE,
            BasOperateLogService.OPT_MODULE_OPER_DOC, "麻醉总结单修改", JsonType.jsonType(anaesSummaryItemFormbean),user,getBeid());
    }
	
	
	
	public void getAmountDetail(ResponseValue resp,String docId){
		resp.put("inAmountList", evtInEventDao.getIoAmountCountByDocId(docId));
		resp.put("inAmount", evtInEventDao.countAmountByDocId(docId));
		resp.put("outAmountList", evtEgressDao.getEgressAmountCountByDocId(docId));
		resp.put("outAmount", evtEgressDao.countAmountByDocId(docId));
	}
	
	public void getAmountDetail(Map<String, Object> resp,String docId){
		resp.put("inAmountList", evtInEventDao.getIoAmountCountByDocId(docId));
		resp.put("inAmount", evtInEventDao.countAmountByDocId(docId));
		resp.put("outAmountList", evtEgressDao.getEgressAmountCountByDocId(docId));
		resp.put("outAmount", evtEgressDao.countAmountByDocId(docId));
	}
	
}
