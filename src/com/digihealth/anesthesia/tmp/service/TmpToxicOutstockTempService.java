package com.digihealth.anesthesia.tmp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.digihealth.anesthesia.basedata.formbean.SearchDoctempFormBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.tmp.po.TmpToxicOutstockTemp;

@Service
public class TmpToxicOutstockTempService extends BaseService {

	public TmpToxicOutstockTemp getToxicOutstockById(String id) {
		return tmpToxicOutstockTempDao.selectByPrimaryKey(id);
	}
	
    /**
     * 根据条件查询模板信息
     */
	public List<TmpToxicOutstockTemp>  queryToxicStocktempByForbean(SearchDoctempFormBean searchDoctempFormBean) {
		List<TmpToxicOutstockTemp> tempList = new ArrayList<TmpToxicOutstockTemp>();
		if(StringUtils.isEmpty(searchDoctempFormBean.getSort())){
		    searchDoctempFormBean.setSort("id");
		}
		if(StringUtils.isEmpty(searchDoctempFormBean.getOrderBy())){
		    searchDoctempFormBean.setOrderBy("ASC");
		}
		if(StringUtils.isEmpty(searchDoctempFormBean.getBeid())){
           searchDoctempFormBean.setBeid(getBeid());
       }
		List<Filter> filters = searchDoctempFormBean.getFilters();
		tempList = tmpToxicOutstockTempDao.queryToxicStocktempByForbean(filters,searchDoctempFormBean);
		return tempList;
	}
	
	public int queryToxicStocktempTotalByForbean(SearchDoctempFormBean searchDoctempFormBean) {
	    if(StringUtils.isEmpty(searchDoctempFormBean.getBeid())){
           searchDoctempFormBean.setBeid(getBeid());
       }
	    List<Filter> filters = searchDoctempFormBean.getFilters();
		return tmpToxicOutstockTempDao.queryToxicStocktempTotalByForbean(filters,searchDoctempFormBean);
	}
	
	
	
	
	@Transactional
	public ResponseValue delToxicStockInfo(String tempId, String userId) {
		ResponseValue res = new ResponseValue();
		TmpToxicOutstockTemp toxicTemp = tmpToxicOutstockTempDao.selectByPrimaryKey(tempId);
		if (null != toxicTemp) {
			String createUser = toxicTemp.getCreateUser();
			if (null != createUser) {
				if (createUser.equals(userId)) {
					tmpToxicOutstockTempDao.deleteByPrimaryKey(tempId);// 删除操作
					res.setResultCode("1");
					res.setResultMessage("删除模板成功！");
				} else {
					res.setResultCode("70000000");
					res.setResultMessage("当前用户和创建用户不一致，不能删除！");
				}
			} else {
				res.setResultCode("10000000");
				res.setResultMessage("当前对象没有createUser值，系统错误！");
			}
		} else {
			res.setResultCode("10000000");
			res.setResultMessage("没有找到当前模板，系统错误！");
		}
		return res;
	}

	/**
	 * 新增or修改模板
	 * 
	 * @param researchBean
	 */
	@Transactional
	public String HandletoxicTemp(TmpToxicOutstockTemp toxicTemp) {
		String id = "";
		if(null == toxicTemp.getCreateTime()){
			toxicTemp.setCreateTime(new Date());
		}
		if(null == toxicTemp.getType()){
			toxicTemp.setType(1); // 默认使用级别为 个人
		}
		if (null == toxicTemp.getId() || StringUtils.isBlank(toxicTemp.getId())) {
			toxicTemp.setId(GenerateSequenceUtil.generateSequenceNo());// 生成id
			toxicTemp.setBeid(getBeid());
			tmpToxicOutstockTempDao.insertSelective(toxicTemp);
			id = toxicTemp.getId();
			return id;
		} else {
			toxicTemp.setCreateUser(null); //修改的时候，不需要传递createUser的值，不修改创建人id
			tmpToxicOutstockTempDao.updateByPrimaryKeySelective(toxicTemp);
			id = toxicTemp.getId();
			return id;
		}
	}


}
