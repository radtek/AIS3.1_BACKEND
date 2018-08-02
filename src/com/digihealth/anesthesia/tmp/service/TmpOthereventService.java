package com.digihealth.anesthesia.tmp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.formbean.SearchDoctempFormBean;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.evt.formbean.Filter;
import com.digihealth.anesthesia.tmp.po.TmpOtherevent;

@Service
public class TmpOthereventService extends BaseService {
    /**
     * 根据条件查询模板信息
     */
	public List<TmpOtherevent>  queryOthereventTempList(SearchDoctempFormBean searchDoctempFormBean) {
		List<TmpOtherevent> othereventTempList = new ArrayList<TmpOtherevent>();
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
		othereventTempList = tmpOthereventDao.queryOthereventTempList(filters,searchDoctempFormBean);
		return othereventTempList;
	}
	
	public int queryTotalCntOthereventTemp(SearchDoctempFormBean searchDoctempFormBean) {
	    if(StringUtils.isEmpty(searchDoctempFormBean.getBeid())){
            searchDoctempFormBean.setBeid(getBeid());
        }
	    List<Filter> filters = searchDoctempFormBean.getFilters();
		return tmpOthereventDao.queryTotalCntOthereventTemp(filters,searchDoctempFormBean);
	}
	
	
	@Transactional
	public ResponseValue delOthereventTmp(String tmpId, String userId) {
		ResponseValue res = new ResponseValue();
		TmpOtherevent tmpOtherevent = tmpOthereventDao.selectByPrimaryKey(tmpId);
		if (null != tmpOtherevent) {
			String createUser = tmpOtherevent.getCreateUser();
			if (null != createUser) {
				if (createUser.equals(userId)) {
					tmpOthereventDao.deleteByPrimaryKey(tmpId);// 删除操作
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
	 * @param tmpOthereventTemp
	 */
	@Transactional
	public String saveOthereventTemp(TmpOtherevent tmpOtherevent) {
		
		if (null == tmpOtherevent.getId() || StringUtils.isBlank(tmpOtherevent.getId())) {
			tmpOtherevent.setId(GenerateSequenceUtil.generateSequenceNo());// 生成id
			tmpOtherevent.setBeid(getBeid());
			tmpOthereventDao.insertSelective(tmpOtherevent);
			return tmpOtherevent.getId();
		} else {
			tmpOtherevent.setCreateUser(null); //修改的时候，不需要传递createUser的值，不修改创建人id
			tmpOthereventDao.updateByPrimaryKeySelective(tmpOtherevent);
			return tmpOtherevent.getId();
		}
	}

	public TmpOtherevent selectBySciId(String sciId) {
		return tmpOthereventDao.selectByPrimaryKey(sciId);
	}

}
