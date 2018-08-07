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

import javax.sound.midi.Instrument;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.po.BasInstrSuitRel;
import com.digihealth.anesthesia.basedata.po.BasInstrument;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.basedata.utils.LogUtils;
import com.digihealth.anesthesia.basedata.utils.UserUtils;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.common.utils.JsonType;
import com.digihealth.anesthesia.common.utils.PingYinUtil;
import com.digihealth.anesthesia.common.utils.StringUtils;
import com.digihealth.anesthesia.doc.formbean.OptNurseInstrubillItemFormbean;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocInstrubillItem;
import com.digihealth.anesthesia.doc.po.DocOptNurse;
import com.digihealth.anesthesia.sysMng.po.BasUser;

/**
 * Title: OptNurseService.java Description: 描述
 * 
 * @author chengwang
 * @created 2015-10-10 下午5:32:33
 */
@Service
public class DocOptNurseService extends BaseService {
	@Autowired
	private DocInstrubillItemService instrubillItemService;

	/**
	 * 
	 * @discription 根据手术ID获取手术护理
	 * @author chengwang
	 * @created 2015-10-10 下午5:13:48
	 * @param regOptId
	 * @return
	 */
	public DocOptNurse searchOptNurseByRegOptId(String regOptId) {
		/*DocOptNurse opt = null;
		DocAnaesRecord anaesRecord = docAnaesRecordDao.searchAnaesRecordByRegOptId(regOptId);
		if (anaesRecord != null) {
			opt = docOptNurseDao.searchOptNurseByRegOptId(regOptId);
			if (opt != null) {
				Integer bleeding = evtEgressDao.getEgressCountValueByIoDefName("出血量", anaesRecord.getAnaRecordId())==null?0:evtEgressDao.getEgressCountValueByIoDefName("出血量", anaesRecord.getAnaRecordId());
				opt.setBleeding(bleeding); //失血量
				Integer urine = evtEgressDao.getEgressCountValueByIoDefName("尿量", anaesRecord.getAnaRecordId()) == null ?0:evtEgressDao.getEgressCountValueByIoDefName("尿量", anaesRecord.getAnaRecordId());
				opt.setUrine(urine);	//尿量
				Integer infusion = evtInEventDao.getIoeventCountValueByIoDef(anaesRecord.getAnaRecordId(),null) == null?0:evtInEventDao.getIoeventCountValueByIoDef(anaesRecord.getAnaRecordId(),null);
				opt.setInfusion(infusion);		//输液
			}
		}*/
		return docOptNurseDao.searchOptNurseByRegOptId(regOptId);
	}

	/**
	 * 
	 * @discription 通过ID查询麻醉同意书
	 * @author chengwang
	 * @created 2015-10-20 下午1:44:32
	 * @param id
	 * @return
	 */
	public DocOptNurse searchOptNurseById(String id) {
		return docOptNurseDao.searchOptNurseById(id);
	}

	/**
	 * 
	 * @discription 插入器械
	 * @author chengwang
	 * @created 2015-10-22 下午2:43:18
	 * @param regOptId
	 * @param optNurseId
	 * @param instrumentCode
	 * @param instrsuitCode
	 * @return
	 */
	@Transactional
	public List<DocInstrubillItem> insertInstubillItem(String regOptId,
			String optNurseId, String instrumentId, String instrsuitId, String type, String instrumentName) {
		List<DocInstrubillItem> list = new ArrayList<DocInstrubillItem>();
		BasInstrument instrument = null;
		if (StringUtils.isNotBlank(instrumentId)) {
			DocInstrubillItem instrubillItemSql = docInstrubillItemDao
					.searchInstrubillItemByCodeAndRegOptId(regOptId,
							instrumentId, type);
			if (instrubillItemSql == null) {
				instrument = basInstrumentDao.searchInstrumentByInstrumentId(instrumentId+"");
				if (instrument != null) {
					// List<InstrubillItem> instrubillItemList =
					// instrubillItemDao.searchInstrubillItemByInstrumentId(instrument.getInstrumentId());
					// if(instrubillItemList==null||instrubillItemList.size()<=0){
					DocInstrubillItem instrubillItem = instrubillItemService.insertInstrubillItemByOptNurseIdAndRegOptId(regOptId, optNurseId, instrument, null, type);
					list.add(instrubillItem);
					// }
				}
			} else {
				return list;
			}
		} else if (StringUtils.isNotBlank(instrsuitId)) {
			List<BasInstrSuitRel> instrSuitRelList = basInstrSuitRelDao
					.searchInstrumentCodeByInstrsuitCode(instrsuitId+"");
			if (instrSuitRelList.size() > 0 && instrSuitRelList != null) {
				for (int i = 0; i < instrSuitRelList.size(); i++) {

					instrument = basInstrumentDao
							.searchInstrumentByInstrumentId(instrSuitRelList
									.get(i).getInstrumentId());
					if (instrument != null) {
						DocInstrubillItem instrubillItemSql = docInstrubillItemDao
								.searchInstrubillItemByCodeAndRegOptId(regOptId, instrument.getInstrumentId(), instrSuitRelList
                                    .get(i).getType());
						if (instrubillItemSql == null) {
							
							DocInstrubillItem instrubillItem = instrubillItemService.insertInstrubillItemByOptNurseIdAndRegOptId(
											regOptId, optNurseId, instrument,
											instrSuitRelList.get(i), type);
							list.add(instrubillItem);
						}
						else
						{
						    instrubillItemSql.setOrigamount(instrubillItemSql.getOrigamount() + instrSuitRelList.get(i).getAmount());
						    docInstrubillItemDao.updateByPrimaryKey(instrubillItemSql);
						    list.add(instrubillItemSql);
						}
						// List<InstrubillItem> instrubillItemList =
						// instrubillItemDao.searchInstrubillItemByInstrumentId(instrument.getInstrumentId());
						// if(instrubillItemList==null||instrubillItemList.size()<=0){

						// }

					}

				}
			}
		}
		else
		{
		    if(StringUtils.isNotBlank(instrumentName)){
                List<BasInstrument> ls = basInstrumentDao.selectByName(instrumentName, getBeid());
                if(null ==ls || ls.size()==0){
                    instrument = new BasInstrument();
                    instrument.setInstrumentId(GenerateSequenceUtil.generateSequenceNo(GenerateSequenceUtil.getRoomId(regOptId)));
                    instrument.setName(instrumentName);
                    instrument.setPinYin(PingYinUtil.getFirstSpell(instrument.getName()));
                    instrument.setEnable(1);
                    instrument.setBeid(getBeid());
                    basInstrumentDao.insert(instrument);
                }
                else
                {
                    instrument = ls.get(0);
                }
                DocInstrubillItem instrubillItem = instrubillItemService.insertInstrubillItemByOptNurseIdAndRegOptId(regOptId, optNurseId, instrument, null, type);
                list.add(instrubillItem);
            }
		}

		return list;
	}
	
	/**
     * 
     * @discription 插入器械
     * @author chengwang
     * @created 2015-10-22 下午2:43:18
     * @param regOptId
     * @param optNurseId
     * @param instrumentCode
     * @param instrsuitCode
     * @return
     */
    @Transactional
    public List<DocInstrubillItem> insertInstubillItemSYZXYY(String regOptId,
            String optNurseId, String instrumentId, String instrsuitId, String type, String instrumentName) {
        List<DocInstrubillItem> list = new ArrayList<DocInstrubillItem>();
        BasInstrument instrument = null;
        if (StringUtils.isNotBlank(instrumentId)) {
            DocInstrubillItem instrubillItemSql = docInstrubillItemDao
                    .searchInstrubillItemByCodeAndRegOptId(regOptId,
                            instrumentId, type);
            if (instrubillItemSql == null) {
                instrument = basInstrumentDao.searchInstrumentByInstrumentId(instrumentId+"");
                if (instrument != null) {
                    // List<InstrubillItem> instrubillItemList =
                    // instrubillItemDao.searchInstrubillItemByInstrumentId(instrument.getInstrumentId());
                    // if(instrubillItemList==null||instrubillItemList.size()<=0){
                    DocInstrubillItem instrubillItem = instrubillItemService.insertInstrubillItemByOptNurseIdAndRegOptIdSYZXYY(regOptId, optNurseId, instrument, null, type);
                    list.add(instrubillItem);
                    // }
                }
            } else {
                return list;
            }
        } else if (StringUtils.isNotBlank(instrsuitId)) {
            List<BasInstrSuitRel> instrSuitRelList = basInstrSuitRelDao
                    .searchInstrumentCodeByInstrsuitCode(instrsuitId+"");
            if (instrSuitRelList.size() > 0 && instrSuitRelList != null) {
                for (int i = 0; i < instrSuitRelList.size(); i++) {

                    instrument = basInstrumentDao
                            .searchInstrumentByInstrumentId(instrSuitRelList
                                    .get(i).getInstrumentId());
                    if (instrument != null) {
                        DocInstrubillItem instrubillItemSql = docInstrubillItemDao
                                .searchInstrubillItemByCodeAndRegOptId(regOptId, instrument.getInstrumentId(), instrSuitRelList
                                    .get(i).getType());
                        if (instrubillItemSql == null) {
                            
                            DocInstrubillItem instrubillItem = instrubillItemService.insertInstrubillItemByOptNurseIdAndRegOptIdSYZXYY(
                                            regOptId, optNurseId, instrument,
                                            instrSuitRelList.get(i), type);
                            list.add(instrubillItem);
                        }
                        else
                        {
                            instrubillItemSql.setOrigamount(instrubillItemSql.getOrigamount()==null?0:instrubillItemSql.getOrigamount() + instrSuitRelList.get(i).getAmount());
                            docInstrubillItemDao.updateByPrimaryKey(instrubillItemSql);
                            list.add(instrubillItemSql);
                        }
                        // List<InstrubillItem> instrubillItemList =
                        // instrubillItemDao.searchInstrubillItemByInstrumentId(instrument.getInstrumentId());
                        // if(instrubillItemList==null||instrubillItemList.size()<=0){

                        // }

                    }

                }
            }
        }
        else
        {
            if(StringUtils.isNotBlank(instrumentName)){
                List<BasInstrument> ls = basInstrumentDao.selectByName(instrumentName, getBeid());
                if(null ==ls || ls.size()==0){
                    instrument = new BasInstrument();
                    instrument.setInstrumentId(GenerateSequenceUtil.generateSequenceNo(GenerateSequenceUtil.getRoomId(regOptId)));
                    instrument.setName(instrumentName);
                    instrument.setPinYin(PingYinUtil.getFirstSpell(instrument.getName()));
                    instrument.setEnable(1);
                    instrument.setBeid(getBeid());
                    basInstrumentDao.insert(instrument);
                }
                else
                {
                    instrument = ls.get(0);
                }
                DocInstrubillItem instrubillItem = instrubillItemService.insertInstrubillItemByOptNurseIdAndRegOptIdSYZXYY(regOptId, optNurseId, instrument, null, type);
                list.add(instrubillItem);
            }
        }

        return list;
    }

	/**
	 * 
	 * @discription 保存手术护理
	 * @author chengwang
	 * @created 2015-10-20 下午1:44:18
	 * @param preVisit
	 * @return
	 *//*
	@Transactional
	public ResponseValue updateOptNurse(OptNurseInstrubillItemFormbean optNurseItem) {
		ResponseValue resp = new ResponseValue();
		DocOptNurse optNurse = optNurseItem.getOptNurse();
		if(optNurse.getShuHouState() == 1){
			Controller controller = controllerDao.getControllerById(optNurse.getRegOptId());
			if(null != controller){
				if(controller.getIsLocalAnaes().equals("1")){
					controller.setState(OperationState.POSTOPERATIVE);
					controllerDao.update(controller);
				}
				
			}
		}
		//optNurse.setProcessState("END");
		Controller controller = controllerDao.getControllerById(optNurse
				.getRegOptId() != null ? optNurse.getRegOptId() : "");
		
		BasRegOpt regOpt = basRegOptDao.searchRegOptById(optNurse
				.getRegOptId() != null ? optNurse.getRegOptId() : "");
		
		if (controller == null) {
			resp.setResultCode("70000001");
			resp.setResultMessage("手术控制信息不存在!");
			return resp;
		}
		DocOptNurse oldOptNurse = searchOptNurseById(optNurse.getOptNurseId() != null ? optNurse
				.getOptNurseId() : "");
		if (oldOptNurse == null) {
			resp.setResultCode("40000002");
			resp.setResultMessage("护理记录单不存在");
			return resp;
		}
        optNurse.setPreCircunurseId(StringUtils.getStringByList(optNurse.getPreCircunurseList()));
        optNurse.setMidCircunurseId(StringUtils.getStringByList(optNurse.getMidCircunurseList()));
        optNurse.setPostCircunurseId(StringUtils.getStringByList(optNurse.getPostCircunurseList()));
        optNurse.setInstrnuseId(StringUtils.getStringByList(optNurse.getInstrnuseList()));
        optNurse.setCircunurseId(StringUtils.getStringByList(optNurse.getCircunurseList()));
        optNurse.setOptBody(StringUtils.getStringByList(optNurse.getOptBodyList()));
        optNurse.setOperDoctor(StringUtils.getStringByList(optNurse.getOperDoctorList()));
        optNurse.setShiftCircunurseId(StringUtils.getStringByList(optNurse.getShiftCircunurseList()));
        optNurse.setShiftInstrnuseId(StringUtils.getStringByList(optNurse.getShiftInstrnuseList()));
        optNurse.setPreInstrnurseId(StringUtils.getStringByList(optNurse.getPreInstrnurseList()));
        optNurse.setMidInstrnurseId(StringUtils.getStringByList(optNurse.getMidInstrnurseList()));
        optNurse.setPostInstrnurseId(StringUtils.getStringByList(optNurse.getPostInstrnurseList()));
        docOptNurseDao.updateByPrimaryKey(optNurse);
        if (optNurseItem.getInstrubillItems() != null && optNurseItem.getInstrubillItems().size() > 0)
        {
            for (int i = 0; i < optNurseItem.getInstrubillItems().size(); i++) 
            {
                docInstrubillItemDao.updateInstrubillItem(optNurseItem.getInstrubillItems().get(i));
            }
        }
		BasUser user = UserUtils.getUserCache();
		LogUtils.saveOperateLog(regOpt.getRegOptId(), "4",
            "2", "手术护理单修改", JsonType.jsonType(optNurseItem),user, getBeid());
		resp.setResultCode("1");
		resp.setResultMessage("护理器械单修改成功!");
		return resp;
	}*/
	
	
	/**
     * 
     * @discription 保存手术护理
     * @author chengwang
     * @created 2015-10-20 下午1:44:18
     * @param preVisit
     * @return
     */
    @Transactional
    public ResponseValue updateOptNurse(OptNurseInstrubillItemFormbean optNurseItem) {
        ResponseValue resp = new ResponseValue();
        DocOptNurse optNurse = optNurseItem.getOptNurse();
        if(optNurse.getShuHouState() == 1){
            Controller controller = controllerDao.getControllerById(optNurse.getRegOptId());
            if(null != controller){
                if(controller.getIsLocalAnaes().equals("1")){
                    controller.setState(OperationState.POSTOPERATIVE);
                    controllerDao.update(controller);
                }
                
            }
        }
        //optNurse.setProcessState("END");
        Controller controller = controllerDao.getControllerById(optNurse
                .getRegOptId() != null ? optNurse.getRegOptId() : "");
        
        BasRegOpt regOpt = basRegOptDao.searchRegOptById(optNurse
                .getRegOptId() != null ? optNurse.getRegOptId() : "");
        
        if (controller == null) {
            resp.setResultCode("70000001");
            resp.setResultMessage("手术控制信息不存在!");
            return resp;
        }
        DocOptNurse oldOptNurse = searchOptNurseById(optNurse.getOptNurseId() != null ? optNurse
                .getOptNurseId() : "");
        if (oldOptNurse == null) {
            resp.setResultCode("40000002");
            resp.setResultMessage("护理记录单不存在");
            return resp;
        }
        optNurse.setPreCircunurseId(StringUtils.getStringByList(optNurse.getPreCircunurseList()));
        optNurse.setMidCircunurseId(StringUtils.getStringByList(optNurse.getMidCircunurseList()));
        optNurse.setPostCircunurseId(StringUtils.getStringByList(optNurse.getPostCircunurseList()));
        optNurse.setInstrnuseId(StringUtils.getStringByList(optNurse.getInstrnuseList()));
        optNurse.setCircunurseId(StringUtils.getStringByList(optNurse.getCircunurseList()));
        optNurse.setOptBody(StringUtils.getStringByList(optNurse.getOptBodyList()));
        optNurse.setOperDoctor(StringUtils.getStringByList(optNurse.getOperDoctorList()));
        optNurse.setShiftCircunurseId(StringUtils.getStringByList(optNurse.getShiftCircunurseList()));
        optNurse.setShiftInstrnuseId(StringUtils.getStringByList(optNurse.getShiftInstrnuseList()));
        docOptNurseDao.updateByPrimaryKeySelective(optNurse);
        
        if (optNurseItem.getInstrubillItems() != null && optNurseItem.getInstrubillItems().size() > 0)
        {
            for (int i = 0; i < optNurseItem.getInstrubillItems().size(); i++) 
            {
                DocInstrubillItem docInstrubillItem = optNurseItem.getInstrubillItems().get(i);
                if(null != docInstrubillItem.getInaddInt() && docInstrubillItem.getInaddInt().intValue() >0)
                {
                	docInstrubillItem.setInadd(docInstrubillItem.getInaddInt().intValue() + "");
                }
                docInstrubillItemDao.updateInstrubillItem(docInstrubillItem);
            }
        }
        BasUser user = UserUtils.getUserCache();
        LogUtils.saveOperateLog(regOpt.getRegOptId(), "4",
            "2", "手术护理单修改", JsonType.jsonType(optNurseItem),user, getBeid());
            
        resp.setResultCode("1");
        resp.setResultMessage("护理器械单修改成功!");
        return resp;
    }

	/**
     * 
     * @discription 保存手术护理
     * @author chengwang
     * @created 2015-10-20 下午1:44:18
     * @param preVisit
     * @return
     */
    @Transactional
    public ResponseValue updateOptNurseForSYBX(OptNurseInstrubillItemFormbean optNurseItem) {
        ResponseValue resp = new ResponseValue();
        DocOptNurse optNurse = optNurseItem.getOptNurse();
        if(optNurse.getShuHouState() == 1){
            Controller controller = controllerDao.getControllerById(optNurse.getRegOptId());
            if(null != controller){
                if(controller.getIsLocalAnaes().equals("1")){
                    controller.setState(OperationState.POSTOPERATIVE);
                    controllerDao.update(controller);
                }
                
            }
        }
        //optNurse.setProcessState("END");
        Controller controller = controllerDao.getControllerById(optNurse
                .getRegOptId() != null ? optNurse.getRegOptId() : "");
        
        BasRegOpt regOpt = basRegOptDao.searchRegOptById(optNurse
                .getRegOptId() != null ? optNurse.getRegOptId() : "");
        
        if (controller == null) {
            resp.setResultCode("70000001");
            resp.setResultMessage("手术控制信息不存在!");
            return resp;
        }
        DocOptNurse oldOptNurse = searchOptNurseById(optNurse.getOptNurseId() != null ? optNurse
                .getOptNurseId() : "");
        if (oldOptNurse == null) {
            resp.setResultCode("40000002");
            resp.setResultMessage("护理记录单不存在");
            return resp;
        }
        optNurse.setPreCircunurseId(StringUtils.getStringByList(optNurse.getPreCircunurseList()));
        optNurse.setMidCircunurseId(StringUtils.getStringByList(optNurse.getMidCircunurseList()));
        optNurse.setPostCircunurseId(StringUtils.getStringByList(optNurse.getPostCircunurseList()));
        optNurse.setInstrnuseId(StringUtils.getStringByList(optNurse.getInstrnuseList()));
        optNurse.setCircunurseId(StringUtils.getStringByList(optNurse.getCircunurseList()));
        optNurse.setOptBody(StringUtils.getStringByList(optNurse.getOptBodyList()));
        optNurse.setOperDoctor(StringUtils.getStringByList(optNurse.getOperDoctorList()));
        optNurse.setShiftCircunurseId(StringUtils.getStringByList(optNurse.getShiftCircunurseList()));
        optNurse.setShiftInstrnuseId(StringUtils.getStringByList(optNurse.getShiftInstrnuseList()));
        docOptNurseDao.updateByPrimaryKeySelective(optNurse);
        
        if (optNurseItem.getInstrubillItems() != null && optNurseItem.getInstrubillItems().size() > 0)
        {
            for (int i = 0; i < optNurseItem.getInstrubillItems().size(); i++) 
            {
                DocInstrubillItem docInstrubillItem = optNurseItem.getInstrubillItems().get(i);
                if(null != docInstrubillItem.getInaddInt() && docInstrubillItem.getInaddInt().intValue() >0)
                {
                	docInstrubillItem.setInadd(docInstrubillItem.getInaddInt().intValue() + "");
                }
                docInstrubillItemDao.updateInstrubillItem(docInstrubillItem);
            }
        }
        BasUser user = UserUtils.getUserCache();
        LogUtils.saveOperateLog(regOpt.getRegOptId(), "4",
            "2", "手术护理单修改", JsonType.jsonType(optNurseItem),user, getBeid());
            
        resp.setResultCode("1");
        resp.setResultMessage("护理器械单修改成功!");
        return resp;
    }
}
