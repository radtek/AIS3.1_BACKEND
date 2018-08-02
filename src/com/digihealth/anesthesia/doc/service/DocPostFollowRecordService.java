/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:32:33    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.doc.formbean.PostFollowRecordFormBean;
import com.digihealth.anesthesia.doc.po.DocPostFollowAnalgesic;
import com.digihealth.anesthesia.doc.po.DocPostFollowGeneral;
import com.digihealth.anesthesia.doc.po.DocPostFollowRecord;
import com.digihealth.anesthesia.doc.po.DocPostFollowSpinal;
import com.digihealth.anesthesia.doc.po.DocPostFollowYxrm;
import com.digihealth.anesthesia.sysMng.po.BasUser;

/**
 * Title: PostFollowRecordService.java Description: 描述
 * 
 * @author liukui
 * 
 * @created 2016-7-10 下午5:32:33
 */
@Service
public class DocPostFollowRecordService extends BaseService {
	
	/**
	 * 根据患者id获取麻醉术后随访数据
	 * @param patId
	 * @return
	 */
	public PostFollowRecordFormBean getPostFollowRecord(String regOptId){
		PostFollowRecordFormBean record = new PostFollowRecordFormBean();
		DocPostFollowRecord postFollowRecord = docPostFollowRecordDao.searchFollowRecordByRegOptId(regOptId, getBeid());
		if(postFollowRecord!=null){
			String postFollowId = postFollowRecord.getPostFollowId();
			record.setPostFollowRecord(postFollowRecord);
			List<DocPostFollowAnalgesic> postFollowAnalgesicList = docPostFollowAnalgesicDao.getInfoByPostFollowId(postFollowId, getBeid());
            record.setPostFollowAnalgesicInfo(null != postFollowAnalgesicList ? postFollowAnalgesicList : new ArrayList<DocPostFollowAnalgesic>());
			List<DocPostFollowGeneral> postFollowGeneralList = docPostFollowGeneralDao.getInfoByPostFollowId(postFollowId, getBeid());
            record.setPostFollowGeneralInfo(null != postFollowGeneralList ? postFollowGeneralList : new ArrayList<DocPostFollowGeneral>());
			List<DocPostFollowSpinal> postFollowSpinalList = docPostFollowSpinalDao.getInfoByPostFollowId(postFollowId, getBeid());
            record.setPostFollowSpinalInfo(null != postFollowSpinalList ? postFollowSpinalList : new ArrayList<DocPostFollowSpinal>());
            record.setDocPostFollowYxrm(docPostFollowYxrmDao.selectPostFollowYxrmByFollowId(postFollowId)); 
		}
		return record;
	}

	/**
	 * 
	 * @discription 麻醉麻醉术后随访
	 * @author liukui
	 * @created 2015-10-20 下午1:44:18
	 * @param AnalgesicRecord
	 * @return
	 */
	@Transactional
	public void savePostFollowRecord(PostFollowRecordFormBean record) {
		DocPostFollowRecord postFollowRecord = record.getPostFollowRecord();
		List<DocPostFollowAnalgesic> postFollowAnalgesicInfoLs = record.getPostFollowAnalgesicInfo();
		List<DocPostFollowGeneral> postFollowGeneralInfoLs = record.getPostFollowGeneralInfo();
		List<DocPostFollowSpinal> postFollowSpinalInfoLs = record.getPostFollowSpinalInfo();
		DocPostFollowYxrm docPostFollowYxrm = record.getDocPostFollowYxrm();
		
		Controller controller = controllerDao.getControllerById(postFollowRecord.getRegOptId());
		
//		postFollowRecord.setState(controller.getState());
		
		DocPostFollowRecord postFollow = docPostFollowRecordDao.searchFollowRecordByRegOptId(controller.getRegOptId(), getBeid());
		
		if(postFollow!=null){
			postFollowRecord.setPostFollowId(postFollow.getPostFollowId());
			docPostFollowRecordDao.updateByPrimaryKeySelective(postFollowRecord);
		}else{
			postFollowRecord.setPostFollowId(GenerateSequenceUtil.generateSequenceNo());
			docPostFollowRecordDao.insert(postFollowRecord);
		}
		String postFollowId = postFollowRecord.getPostFollowId();
		if(null != postFollowAnalgesicInfoLs && postFollowAnalgesicInfoLs.size()>0){
			docPostFollowAnalgesicDao.deleteByPostFollowId(postFollowId);
			for (DocPostFollowAnalgesic postFollowAnalgesic : postFollowAnalgesicInfoLs) {
				postFollowAnalgesic.setPostFollowId(postFollowId);
				postFollowAnalgesic.setAnalgesicFollowId(GenerateSequenceUtil.generateSequenceNo());
				docPostFollowAnalgesicDao.insertSelective(postFollowAnalgesic);
			}
		}
		if(null != postFollowGeneralInfoLs && postFollowGeneralInfoLs.size()>0){
			docPostFollowGeneralDao.deleteByPostFollowId(postFollowId);
			for (DocPostFollowGeneral postFollowGeneral : postFollowGeneralInfoLs) {
				postFollowGeneral.setPostFollowId(postFollowId);
				postFollowGeneral.setGeneralFolllowId(GenerateSequenceUtil.generateSequenceNo());
				docPostFollowGeneralDao.insertSelective(postFollowGeneral);
			}
		}
		
		if(null != postFollowSpinalInfoLs && postFollowSpinalInfoLs.size()>0){
			docPostFollowSpinalDao.deleteByPostFollowId(postFollowId);
			for (DocPostFollowSpinal postFollowSpinal : postFollowSpinalInfoLs) {
				postFollowSpinal.setPostFollowId(postFollowId);
				postFollowSpinal.setSpinalFollowId(GenerateSequenceUtil.generateSequenceNo());
				docPostFollowSpinalDao.insertSelective(postFollowSpinal);
			}
		}
		
		if(null != docPostFollowYxrm)
		{
			DocPostFollowYxrm old = docPostFollowYxrmDao.selectPostFollowYxrmByFollowId(postFollowId);
			if(null != old)
			{
				docPostFollowYxrm.setPostFollowYxrmId(old.getPostFollowYxrmId());
				docPostFollowYxrm.setPostFollowId(postFollowId);
				docPostFollowYxrmDao.updateByPrimaryKey(docPostFollowYxrm);
			}else
			{
				docPostFollowYxrm.setPostFollowYxrmId(GenerateSequenceUtil.generateSequenceNo());
				docPostFollowYxrm.setPostFollowId(postFollowId);
				docPostFollowYxrmDao.insertSelective(docPostFollowYxrm);
			}
		}
		
		BasUser user = UserUtils.getUserCache();
		LogUtils.saveOperateLog(postFollowRecord.getRegOptId(), "4",
	            "2", "麻醉科术后随访单", JsonType.jsonType(postFollowRecord),user, getBeid());
	}

}
