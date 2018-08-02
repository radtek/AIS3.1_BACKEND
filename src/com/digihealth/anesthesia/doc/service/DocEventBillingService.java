/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:32:33    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.ChargeStateOption;
import com.digihealth.anesthesia.basedata.formbean.BaseInfoQuery;
import com.digihealth.anesthesia.basedata.formbean.SystemSearchFormBean;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.utils.Arith;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.doc.formbean.BatchOperCostFormBean;
import com.digihealth.anesthesia.doc.formbean.EventBillingFormBean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocEventBilling;
import com.digihealth.anesthesia.doc.po.DocPackagesItem;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.interfacedata.formbean.syzxyy.CostRow;

/**
 * Title: EventBillingService.java Description: 描述
 * 
 * @author liukui
 * @created 2015-10-10 下午5:32:33
 */
@Service
public class DocEventBillingService extends BaseService {
	
	/**
	 * 统计病人药品数据及总价
	 * @param systemSearchFormBean
	 * @return
	 */
	public List<EventBillingFormBean> searchBillGroupByMedicode(SystemSearchFormBean systemSearchFormBean){	
		return docEventBillingDao.searchBillGroupByMedicode(this.getFilterStr(systemSearchFormBean,"code"),systemSearchFormBean, getBeid());
	}
	
	/**
	 * 查询药品结账单明细
	 * @param baseInfo
	 * @return
	 */
	public List<DocEventBilling> searchEventBillingList(SystemSearchFormBean systemSearchFormBean){
		return docEventBillingDao.searchEventBillingList(this.getFilterStr(systemSearchFormBean,"code"),systemSearchFormBean, getBeid());
	}
	
	/**
	 * 查询药品结账单明细总数
	 * @param baseInfo
	 * @return
	 */
	public int searchEventBillingListTotal(SystemSearchFormBean systemSearchFormBean){
		return docEventBillingDao.searchEventBillingListTotal(this.getFilterStr(systemSearchFormBean,"code"), systemSearchFormBean, getBeid());
	}
	
	public String getFilterStr(SystemSearchFormBean systemSearchFormBean,String sort){
		
		if(StringUtils.isEmpty(systemSearchFormBean.getSort())){
			systemSearchFormBean.setSort(sort);
		}
		if(StringUtils.isEmpty(systemSearchFormBean.getOrderBy())){
			systemSearchFormBean.setOrderBy("ASC");
		}
		
		String filter = "";
		List<Filter> filters = systemSearchFormBean.getFilters();
		if(filters!=null&&filters.size()>0){
			for(int i = 0;i<filters.size();i++){
				if(!StringUtils.isEmpty(filters.get(i).getValue().toString())){
				    if ("state".equals(filters.get(i).getField()))
				    {
				        filter = filter + " AND state = '"+filters.get(i).getValue()+"' ";
				        continue;
				    }
				    filter = filter + " AND "+filters.get(i).getField() +" like '%"+filters.get(i).getValue()+"%' ";
				}
			}
		}
		return filter;
	}
	
	/**
	 * 将药品事件表、入量事件表记录同步至billing表中
	 * @param regOptId
	 * @param userType
	 */
	@Transactional
	public void synMedicTakeInfoList(BaseInfoQuery baseQuery,ResponseValue result){
		
		String costType = baseQuery.getCostType();
		String regOptId = baseQuery.getRegOptId();
		String userType = baseQuery.getUserType();
		
		//如果已经同步过术中药品数据并且传递给了HIS的，直接提示已同步HIS
		Integer cnt = docEventBillingDao.checkEventBillingSyn(regOptId, costType); 
		if(null != cnt && cnt>0){
			result.setResultCode("10000000");
			result.setResultMessage("术中用药数据已同步至HIS系统，无法再同步!");
			return;
		}
		
		//为了确保不重复同步数据，先删除原来同步的事件表数据,这里要区分麻醉医生、护士
		BaseInfoQuery baseInfo = new BaseInfoQuery();
		baseInfo.setRegOptId(regOptId);
		//baseInfo.setUserType(userType);
		docEventBillingDao.deleteBillingByRegOptId(baseInfo);
		
		List<DocEventBilling> list = new ArrayList<DocEventBilling>();
		
		DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
		baseInfo.setDocId(anaesRecord.getAnaRecordId());
		
		List<DocEventBilling> medicList = docEventBillingDao.queryMedicaleventInBill(baseInfo,getBeid());
		for (DocEventBilling eventBilling : medicList) {
			eventBilling.setEbId(GenerateSequenceUtil.generateSequenceNo());
			eventBilling.setRegOptId(regOptId);
			//eventBilling.setUserType(userType);
			Float newDosageTotalAmount = eventBilling.getDosageTotalAmount();
			Float newPackageTotalAmount = new Float(Math.ceil(newDosageTotalAmount.floatValue()/eventBilling.getPackageDosageAmount()));
			
			//如果关联不到价格表，则将总价字段设置为0
			if(null!=eventBilling.getPriceMinPackage() && eventBilling.getPriceMinPackage()>0){
				eventBilling.setShouldCost(Arith.multiply(eventBilling.getPriceMinPackage(),newPackageTotalAmount));
				eventBilling.setRealCost(Arith.multiply(eventBilling.getShouldCost(),eventBilling.getDiscount()));
			}else{
				eventBilling.setShouldCost(0.0f);
				eventBilling.setRealCost(0.0f);
			}
			eventBilling.setPackageTotalAmount(newPackageTotalAmount);
			eventBilling.setCostType(costType);
			eventBilling.setFirmId(eventBilling.getFirmId());
			eventBilling.setState(ChargeStateOption.NO_FINISH);
			
			//如果术中用药时未选择加药人，则取head里面的username
			if(StringUtils.isBlank(eventBilling.getCreateUser())){
				String createUser = request.getHeader("username");
				eventBilling.setCreateUser(createUser);
			}
			docEventBillingDao.insert(eventBilling);
			list.add(eventBilling);
		}

		//source为2表示是手工添加的
		List<DocEventBilling> sourceList = docEventBillingDao.selectEventBillingBySource(regOptId,costType,"2",getBeid());
		list.addAll(sourceList);
		
		if(medicList.size()<1){
			result.setResultMessage("无可同步的药品信息!");
			result.put("eventBillingList", list);
		}else{
			result.setResultMessage("同步术中药品信息至账单数据成功!");
			result.put("eventBillingList", list);
		}
		
	}
	
	/**
	 * 根据id、type=M选择查询药品事件明细
	 * 根据id、type=I选择查询入量事件明细
	 * @param baseInfo
	 * @return
	 */
	public List<DocEventBilling> queryMedievnetOrIoeventListById(BaseInfoQuery baseInfo){
		if("M".equals(baseInfo.getType())){
			return docEventBillingDao.queryMedicaleventInBill(baseInfo, getBeid());
		}else{
			return docEventBillingDao.queryIoeventInBill(baseInfo, getBeid());
		}
	}
	
	/**
	 * 根据id查询单挑账单信息
	 * @param baseInfo
	 * @return
	 */
	public DocEventBilling searchEventBillingList(String ebId){
		return docEventBillingDao.searchEventBillingById(ebId);
	}
	
	/**
	 * 保存单条入账信息
	 * @param ebId
	 */	
	@Transactional(readOnly = false)
	public void saveEventBilling(List<DocEventBilling> eventBillingList,ResponseValue resp){
		
		String regOptId = "";
		String costType = "";
		
		for (DocEventBilling eventBilling : eventBillingList) {
			//使用总剂量
            eventBilling.setDosageTotalAmount(Arith.multiply(null == eventBilling.getPackageTotalAmount() ? 0
                : eventBilling.getPackageTotalAmount(), null == eventBilling.getPackageDosageAmount() ? 0
                : eventBilling.getPackageDosageAmount()));
            eventBilling.setShouldCost(Arith.multiply(null == eventBilling.getPackageTotalAmount() ? 0
                : eventBilling.getPackageTotalAmount(),
                null == eventBilling.getPriceMinPackage() ? 0 : eventBilling.getPriceMinPackage()));

			if(StringUtils.isNotBlank(eventBilling.getEbId())){
				docEventBillingDao.updateByPrimaryKey(eventBilling);
			}else{
				String ebId = GenerateSequenceUtil.generateSequenceNo();
				eventBilling.setEbId(ebId);
				eventBilling.setSource(2); //表示手工录入
				eventBilling.setCreateTime(new Date());
				eventBilling.setSeq(docEventBillingDao.getMaxSeq(eventBilling));
				docEventBillingDao.insert(eventBilling);
				resp.put("ebId", ebId);
			}
			
			regOptId = eventBilling.getRegOptId();
			costType = eventBilling.getCostType();
			
		}
		
		/**
		 * 修改收费标志
		 */
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
		if(null!=regOpt){
			Integer payState = basRegOptDao.queryPayStatusByRegOptId(costType, regOptId);
			Boolean changeFlag = false; 
			if("1".equals(costType)){//麻醉费用单收费标志
				if(!Objects.equals(payState, regOpt.getDocPayState())){
					changeFlag = true;
				}
				regOpt.setDocPayState(payState);
			}else{//手术核算单收费标志
				if(!Objects.equals(payState, regOpt.getNurPayState())){
					changeFlag = true;
				}
				regOpt.setNurPayState(payState);
			}
			if(changeFlag){
				basRegOptDao.updatePayState(regOpt);
			}
		}
		
	}
	
	
	/**
	 * 删除单条入账信息
	 * @param ebId
	 */
	@Transactional
	public void deleteBilling(String ebId){
		
		DocEventBilling evt = docEventBillingDao.searchEventBillingById(ebId);
		docEventBillingDao.deleteBilling(ebId);
		
		/**
		 * 修改收费标志
		 */
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(evt.getRegOptId());
		if(null!=regOpt){
			Integer payState = basRegOptDao.queryPayStatusByRegOptId(evt.getCostType(), evt.getRegOptId());
			Boolean changeFlag = false; 
			if("1".equals(evt.getCostType())){//麻醉费用单收费标志
				if(!Objects.equals(payState, regOpt.getDocPayState())){
					changeFlag = true;
				}
				regOpt.setDocPayState(payState);
			}else{//手术核算单收费标志
				if(!Objects.equals(payState, regOpt.getNurPayState())){
					changeFlag = true;
				}
				regOpt.setNurPayState(payState);
			}
			if(changeFlag){
				basRegOptDao.updatePayState(regOpt);
			}
		}
		
	}
	
	/**
	 * 批量删除单条入账信息
	 * @param ebId
	 */
	@Transactional(readOnly = false)
	public void batchDeleteCostDetail(List<BatchOperCostFormBean> evList){
		
		
		String regOptId = "";
		String costType = "";
		
		for (BatchOperCostFormBean record : evList) {
			if("1".equals(record.getOperCostType())){
				DocEventBilling evt = docEventBillingDao.searchEventBillingById(record.getPrimaryKeyId());
				if(null!=evt){
					regOptId = evt.getRegOptId();
					costType = evt.getCostType();
				}
				docEventBillingDao.deleteBilling(record.getPrimaryKeyId());
			}else{
				DocPackagesItem evt = docPackagesItemDao.searchPackagesItemById(record.getPrimaryKeyId());
				if(null!=evt){
					regOptId = evt.getRegOptId();
					costType = evt.getCostType();
				}
				docPackagesItemDao.deleteByPrimaryKey(record.getPrimaryKeyId());
			}
			
			/**
			 * 修改收费标志
			 */
			BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
			if(null!=regOpt){
				Integer payState = basRegOptDao.queryPayStatusByRegOptId(costType, regOptId);
				Boolean changeFlag = false; 
				if("1".equals(costType)){//麻醉费用单收费标志
					if(!Objects.equals(payState, regOpt.getDocPayState())){
						changeFlag = true;
					}
					regOpt.setDocPayState(payState);
				}else{//手术核算单收费标志
					if(!Objects.equals(payState, regOpt.getNurPayState())){
						changeFlag = true;
					}
					regOpt.setNurPayState(payState);
				}
				if(changeFlag){
					basRegOptDao.updatePayState(regOpt);
				}
			}
		}
		
	}
	
	public Float queryShouldCostByRegOptId(String regOptId,String costType){
        return docEventBillingDao.queryShouldCostByRegOptId(regOptId,costType);
    }
	
	/**
     * 
     * @param regOptId
     * @param costType 费用类型 1 麻醉科收费清单  2手术核算单
     * @return
     */
    public List<CostRow> queryUnCostListByRegOptId(String regOptId,String costType){
        return docEventBillingDao.queryUnCostListByRegOptId(regOptId,costType);
    }

    
	/**
	 * 将收费明细表的state状态改成已同步
	 * @param costList
	 */
	@Transactional(readOnly = false)
	public void updateChargeState(List<CostRow> costList){
		String regOptId = "";
		String costType = "";
		if(null!=costList && costList.size()>0){
			for (CostRow operCost : costList) {
				docEventBillingDao.updateChargeState(operCost.getOperId());
			}
			
			DocEventBilling evt = docEventBillingDao.searchEventBillingById(costList.get(0).getOperId());
			if(null!=evt){
				regOptId = evt.getRegOptId();
				costType = evt.getCostType();
			}
			
			/**
			 * 修改收费标志
			 */
			BasRegOpt regOpt = basRegOptDao.searchRegOptById(evt.getRegOptId());
			if(null!=regOpt){
				Integer payState = basRegOptDao.queryPayStatusByRegOptId(evt.getCostType(), evt.getRegOptId());
				Boolean changeFlag = false; 
				if("1".equals(evt.getCostType())){//麻醉费用单收费标志
					if(!Objects.equals(payState, regOpt.getDocPayState())){
						changeFlag = true;
					}
					regOpt.setDocPayState(payState);
				}else{//手术核算单收费标志
					if(!Objects.equals(payState, regOpt.getNurPayState())){
						changeFlag = true;
					}
					regOpt.setNurPayState(payState);
				}
				if(changeFlag){
					basRegOptDao.updatePayState(regOpt);
				}
			}
		}
		
	}
	
	public static float add(float value1,float value2){  
	    BigDecimal b1 = new BigDecimal(Float.toString(value1));  
	    BigDecimal b2 = new BigDecimal(Float.toString(value2));  
	    return b1.add(b2).floatValue();  
	}
}
