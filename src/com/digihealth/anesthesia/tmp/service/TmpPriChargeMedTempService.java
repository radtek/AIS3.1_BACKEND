/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.digihealth.anesthesia.tmp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.MedicineFormBean;
import com.digihealth.anesthesia.basedata.formbean.SearchLiquidTempFormBean;
import com.digihealth.anesthesia.basedata.po.BasChargeItem;
import com.digihealth.anesthesia.basedata.po.BasDept;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.po.DocEventBilling;
import com.digihealth.anesthesia.doc.po.DocPackagesItem;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.sysMng.po.BasUser;
import com.digihealth.anesthesia.tmp.formbean.TmpChargeMedTempFormBean;
import com.digihealth.anesthesia.tmp.formbean.TmpChargeTempOptFormBean;
import com.digihealth.anesthesia.tmp.po.TmpChargePayTemp;
import com.digihealth.anesthesia.tmp.po.TmpMedPayTemp;
import com.digihealth.anesthesia.tmp.po.TmpPriChargeMedTemp;

/**
 * 
 * Title: MedChargeTempService.java Description: 麻醉收费单模板Service
 * 
 * @author
 */
@Service
@Transactional(readOnly = true)
public class TmpPriChargeMedTempService extends BaseService {

	public List<TmpPriChargeMedTemp> queryChargeMedTempList(
			SearchLiquidTempFormBean searchLiquidTempFormBean) {
		List<TmpPriChargeMedTemp> chargeMedTempList = new ArrayList<TmpPriChargeMedTemp>();

		BasUser user = basUserDao.getByLoginName(searchLiquidTempFormBean
				.getCreateUser(),getBeid());

		if (StringUtils.isBlank(searchLiquidTempFormBean.getRoleType())) {
			searchLiquidTempFormBean.setRoleType(user.getRoleType());
		}
		if (StringUtils.isEmpty(searchLiquidTempFormBean.getSort())) {
			searchLiquidTempFormBean.setSort("chargeMedTempId");
		}
		if (StringUtils.isEmpty(searchLiquidTempFormBean.getOrderBy())) {
			searchLiquidTempFormBean.setOrderBy("ASC");
		}
		if (StringUtils.isBlank(searchLiquidTempFormBean.getBeid())) {
            searchLiquidTempFormBean.setBeid(getBeid());
        }
		List<Filter> filters = searchLiquidTempFormBean.getFilters();
		Filter f = new Filter();
		f.setField("tempType"); 
		if ("ANAES_DOCTOR".equals(user.getUserType())) {
			f.setValue("1");
			filters.add(f);
		}
		if ("NURSE".equals(user.getUserType())) {
			f.setValue("2");
			filters.add(f);
		}
		//设置Beid
		f = new Filter();
		f.setField("beid");
		f.setValue(this.getBeid());
		filters.add(f);
		
		chargeMedTempList = tmpPriChargeMedTempDao.queryChargeMedTempList(filters,
				searchLiquidTempFormBean);

		return chargeMedTempList;
	}

	public Integer queryCountChargeMedTempList(
			SearchLiquidTempFormBean searchLiquidTempFormBean) {

		BasUser user = basUserDao.getByLoginName(searchLiquidTempFormBean
				.getCreateUser(),getBeid());

		if (StringUtils.isBlank(searchLiquidTempFormBean.getExecutiveLevel())) {
			searchLiquidTempFormBean.setExecutiveLevel(user.getExecutiveLevel());
		}
		if (StringUtils.isBlank(searchLiquidTempFormBean.getBeid())) {
            searchLiquidTempFormBean.setBeid(getBeid());;
        }

		List<Filter> filters = searchLiquidTempFormBean.getFilters();
		Filter f = new Filter();
		f.setField("tempType");
		if ("ANAES_DOCTOR".equals(user.getUserType())) {
			f.setValue("1");
			filters.add(f);
		}
		if ("NURSE".equals(user.getUserType())) {
			f.setValue("2");
			filters.add(f);
		}
		//设置Beid
		f = new Filter();
		f.setField("beid");
		f.setValue(this.getBeid());
		filters.add(f);

		return tmpPriChargeMedTempDao.queryCountChargeMedTempList(filters,
				searchLiquidTempFormBean);
	}

	public List<DocEventBilling> queryChargeMedTempById(String chargeMedTempId) {
		return tmpMedPayTempDao.queryItemListByChargeTempId(chargeMedTempId);
	}

	public List<DocPackagesItem> queryPayListByChargeTempId(String chargeMedTempId) {
		return tmpChargePayTempDao.queryItemListByChargeMedTempId(chargeMedTempId);
	}

	public TmpPriChargeMedTemp searchChargeMedTemp(String chargeMedTempId) {
		return tmpPriChargeMedTempDao.selectByPrimaryKey(chargeMedTempId);
	}

	@Transactional(readOnly = false)
	public void saveChargeMedTempForm(
			TmpChargeMedTempFormBean tmpChargeMedTempFormBean) {

		TmpPriChargeMedTemp tmpPriChargeMedTemp = tmpChargeMedTempFormBean.getTmpPriChargeMedTemp();

		Boolean operFlag = true; // 是否为新增模板

		if (StringUtils.isNotBlank(tmpPriChargeMedTemp.getTempName())) {
			tmpPriChargeMedTemp.setPinyin(PingYinUtil.getFirstSpell(tmpPriChargeMedTemp
					.getTempName()));
		}

		if (StringUtils.isBlank(tmpPriChargeMedTemp.getChargeMedTempId())) {
			operFlag = false;
			String chargeMedTempId = GenerateSequenceUtil.generateSequenceNo();
			tmpPriChargeMedTemp.setChargeMedTempId(chargeMedTempId);
			tmpPriChargeMedTemp.setBeid(this.getBeid());
			tmpPriChargeMedTempDao.insertSelective(tmpPriChargeMedTemp);
		}else{
			tmpPriChargeMedTempDao.updateByPrimaryKeySelective(tmpPriChargeMedTemp);
		}
		/**
		 * 用药模板\输液模板
		 * 
		 */
		List<TmpMedPayTemp> tmpMedPayTempList = tmpChargeMedTempFormBean.getTmpMedPayTempList();
		if (tmpMedPayTempList != null &&tmpMedPayTempList.size() > 0) {

			// 如果是修改模板时，需要先判断模板对应明细表里是否有数据，如果有就删除
			if (operFlag) {
				List<DocEventBilling> ls = tmpMedPayTempDao.queryItemListByChargeTempId(tmpPriChargeMedTemp.getChargeMedTempId());
				if (null != ls && ls.size() > 0) {
					tmpMedPayTempDao.deleteByChargeTempId(tmpPriChargeMedTemp.getChargeMedTempId());
				}
			}
			for (int i = 0; i < tmpMedPayTempList.size(); i++) {
				TmpMedPayTemp temp = tmpMedPayTempList.get(i);
				temp.setMedPayTempId(GenerateSequenceUtil.generateSequenceNo());
				temp.setChargeMedTempId(tmpPriChargeMedTemp.getChargeMedTempId());
				temp.setBeid(this.getBeid());
				tmpMedPayTempDao.insertSelective(temp);
			}
		}
		/**
		 * 服务项目收费模板
		 */
		List<TmpChargePayTemp> tmpChargePayTempList = tmpChargeMedTempFormBean.getTmpChargePayTempList();
		if (tmpChargePayTempList != null && tmpChargePayTempList.size() > 0) {
			// 如果是修改模板时，需要先判断模板对应明细表里是否有数据，如果有就删除
			if (operFlag) {
				List<DocPackagesItem> ls = tmpChargePayTempDao.queryItemListByChargeMedTempId(tmpPriChargeMedTemp.getChargeMedTempId());
				if (null != ls && ls.size() > 0) {
					tmpChargePayTempDao.deleteByChargeMedTempId(tmpPriChargeMedTemp.getChargeMedTempId());
				}
			}
			for (int i = 0; i < tmpChargePayTempList.size(); i++) {
				TmpChargePayTemp pay = tmpChargePayTempList.get(i);
				pay.setChargePayTempId(GenerateSequenceUtil.generateSequenceNo());
				pay.setChargeMedTempId(tmpPriChargeMedTemp.getChargeMedTempId());
				pay.setBeid(this.getBeid());
				tmpChargePayTempDao.insertSelective(pay);
			}
		}

	}

	/**
	 * 删除麻醉收费项目模板
	 */
	@Transactional(readOnly = false)
	public void deleteChargeMedTempById(TmpPriChargeMedTemp tmpPriChargeMedTemp) {
		if (tmpPriChargeMedTemp != null) {
			if (StringUtils.isNotBlank(tmpPriChargeMedTemp.getChargeMedTempId())) {
				String chargeMedTempId = tmpPriChargeMedTemp.getChargeMedTempId();
				tmpMedPayTempDao.deleteByChargeTempId(chargeMedTempId);//收费模板对应药品输液
				tmpChargePayTempDao.deleteByChargeMedTempId(chargeMedTempId);//收费模板对应收费项目
				tmpPriChargeMedTempDao.deleteByPrimaryKey(chargeMedTempId);
			}
		}
	}

	public List<TmpMedPayTemp> searchMedChargeListById(String chargeMedTempId,String chargedType) {
		return tmpMedPayTempDao.searchMedChargeListById(chargeMedTempId,chargedType);
	}

	public List<TmpChargePayTemp> searchChargePayListById(String chargeMedTempId,String chargedType) {
		return tmpChargePayTempDao.searchChargePayListById(chargeMedTempId,chargedType);
	}
	
	public List<BasChargeItem> queryInvalidChargeItemList(){
    	return tmpPriChargeMedTempDao.queryInvalidChargeItemList(this.getBeid());
    }
    
	public List<MedicineFormBean> queryInvalidMedOrPriceList(){
		return tmpPriChargeMedTempDao.queryInvalidMedOrPriceList(this.getBeid());
    }
	
	@Transactional(readOnly = false)
	public void batchDelChargePayInvalidData(String chargeItemId){
		tmpPriChargeMedTempDao.batchDelChargePayInvalidData(chargeItemId,this.getBeid());
	}
	
	@Transactional(readOnly = false)
	public void batchDelChargeMedInvalidData(String medicineId,String firmId){
		tmpPriChargeMedTempDao.batchDelChargeMedInvalidData(medicineId, firmId,this.getBeid());
	}
	
	
	@Transactional(readOnly = false)
	public void batchDelChargeTempDetaiInvalidData(TmpChargeTempOptFormBean record){
		if("1".equals(record.getCostType())){//删除收费模板药品详情数据
			if(null!=record.getMedicineId() && StringUtils.isNotBlank(record.getFirmId()))
				tmpPriChargeMedTempDao.batchDelChargeMedInvalidData(record.getMedicineId(), record.getFirmId(),this.getBeid());
		}else{//删除收费模板收费项目详情数据
			if(null!=record.getChargeItemId())
				tmpPriChargeMedTempDao.batchDelChargePayInvalidData(record.getChargeItemId(),this.getBeid());
		}
	}
	
	
	@Transactional(readOnly = false)
	public void batchReplaceChargeTempDetailData(TmpChargeTempOptFormBean chargeTempOptFormBean){
		
		if("1".equals(chargeTempOptFormBean.getCostType())){//替换收费模板药品详情数据
			tmpPriChargeMedTempDao.batchReplaceChargeMedData(chargeTempOptFormBean,this.getBeid());
		}else{//替换收费模板收费项目详情数据
			
			List<BasChargeItem> rsList = basChargeItemDao.selectByCode(chargeTempOptFormBean.getChargeItemCode(),this.getBeid());
			//如果提交过来的收费项目在数据库中找不到，则需要新增一条数据
			if(null == rsList || rsList.size()<1){
				 BasChargeItem chargeItem = new BasChargeItem();
                 chargeItem.setChargeItemCode(chargeTempOptFormBean.getChargeItemCode());
                 chargeItem.setChargeItemName(chargeTempOptFormBean.getChargeItemName());
                 chargeItem.setSpec(chargeTempOptFormBean.getSpec());
                 chargeItem.setPinYin(chargeTempOptFormBean.getPinyin());
                 chargeItem.setUnit(chargeTempOptFormBean.getUnit());
                 chargeItem.setBasicUnitAmount(chargeTempOptFormBean.getBasicUnitAmount());
                 chargeItem.setPrice(chargeTempOptFormBean.getPrice());
                 chargeItem.setBasicUnitPrice(chargeTempOptFormBean.getBasicUnitPrice());
                 chargeItem.setType(chargeTempOptFormBean.getType());
                 chargeItem.setEnable(new Integer(chargeTempOptFormBean.getEnable()));
                 chargeItem.setChargeType(chargeTempOptFormBean.getChargeType());
                 chargeItem.setBeid(this.getBeid());
                 basChargeItemDao.insert(chargeItem);
                 List<BasChargeItem> ls = basChargeItemDao.selectByCode(chargeTempOptFormBean.getChargeItemCode(),this.getBeid());
                 chargeTempOptFormBean.setToChargeItemId(ls.get(0).getChargeItemId());
			}else{
				chargeTempOptFormBean.setToChargeItemId(rsList.get(0).getChargeItemId());
			}
			tmpPriChargeMedTempDao.batchReplaceChargePayData(chargeTempOptFormBean,this.getBeid());
		}
	}
	
	public List<BasChargeItem> queryChargeTempPayItemByPy(String pinyin){
    	return tmpPriChargeMedTempDao.queryChargeTempPayItemByPy(pinyin,this.getBeid());
    }
    
	public List<MedicineFormBean> queryChargeTempMedicineItemByPy(String name){
		return tmpPriChargeMedTempDao.queryChargeTempMedicineItemByPy(name,this.getBeid());
    }
	
	public List<BasDept> queryRemarkByChargeTempList(String tempType){
		return tmpPriChargeMedTempDao.queryRemarkByChargeTempList(tempType,this.getBeid());
	}

}
