/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-10 下午5:32:33    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.doc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.doc.po.DocPrePublicity;
import com.digihealth.anesthesia.sysMng.po.BasUser;

@Service
public class DocPrePublicityService extends BaseService {

	public DocPrePublicity searchPrePublicityByRegOptId(String regOptId) {
		return docPrePublicityDao.searchPrePublicityByRegOptId(regOptId);
	}
	
	public DocPrePublicity searchPrePublicityById(String id) {
		return docPrePublicityDao.searchPrePublicityById(id);
	}
	
	@Transactional
	public ResponseValue updatePrePublicity(DocPrePublicity prePublicity) {
		ResponseValue resp = new ResponseValue();
		Controller controller = controllerDao.getControllerById(prePublicity.getRegOptId()!=null?prePublicity.getRegOptId():"");
		if(controller == null){
			resp.setResultCode("70000001");
			resp.setResultMessage("手术控制信息不存在!");
			return resp;
		}
		if(prePublicity!=null&&(!org.apache.commons.lang3.StringUtils.isEmpty(prePublicity.getId()))){
			docPrePublicityDao.updateByPrimaryKeySelective(prePublicity);
		}else{
			prePublicity.setId(GenerateSequenceUtil.generateSequenceNo());
			docPrePublicityDao.insert(prePublicity);
		}
		BasUser user = UserUtils.getUserCache();
		LogUtils.saveOperateLog(prePublicity.getRegOptId(), "4",
            "2", "麻醉手术室术前宣教修改", JsonType.jsonType(prePublicity),user, getBeid());
		resp.setResultCode("1");
		resp.setResultMessage("更新麻醉手术室术前宣教成功!");
		return resp;
	}
	
	@Transactional
	public int deleteByPrimaryKey(String id){
		return docPrePublicityDao.deleteByPrimaryKey(id);
	}

}
