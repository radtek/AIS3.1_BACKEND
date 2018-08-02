/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author chengwang       
 * @created 2015-10-19 下午4:42:09    
 * tags     
 * see_to_target     
 */

package com.digihealth.anesthesia.basedata.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digihealth.anesthesia.basedata.config.OperationState;
import com.digihealth.anesthesia.basedata.po.BasDispatch;
import com.digihealth.anesthesia.basedata.po.BasRegOpt;
import com.digihealth.anesthesia.basedata.po.Controller;
import com.digihealth.anesthesia.common.entity.ResponseValue;
import com.digihealth.anesthesia.common.service.BaseService;
import com.digihealth.anesthesia.common.utils.GenerateSequenceUtil;
import com.digihealth.anesthesia.doc.po.DocAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocAnaesMedicineAccede;
import com.digihealth.anesthesia.doc.po.DocAnaesPlan;
import com.digihealth.anesthesia.doc.po.DocAnaesPostop;
import com.digihealth.anesthesia.doc.po.DocAnaesRecord;
import com.digihealth.anesthesia.doc.po.DocAnaesSummary;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAllergicReaction;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixCanal;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixGen;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryAppendixVisit;
import com.digihealth.anesthesia.doc.po.DocAnaesSummaryVenipuncture;
import com.digihealth.anesthesia.doc.po.DocAnalgesicRecord;
import com.digihealth.anesthesia.doc.po.DocBloodTransRecord;
import com.digihealth.anesthesia.doc.po.DocExitOperSafeCheck;
import com.digihealth.anesthesia.doc.po.DocGeneralAnaes;
import com.digihealth.anesthesia.doc.po.DocInsuredPatAgree;
import com.digihealth.anesthesia.doc.po.DocNerveBlock;
import com.digihealth.anesthesia.doc.po.DocNurseInterviewRecord;
import com.digihealth.anesthesia.doc.po.DocOperBeforeSafeCheck;
import com.digihealth.anesthesia.doc.po.DocOptCareRecord;
import com.digihealth.anesthesia.doc.po.DocOptNurse;
import com.digihealth.anesthesia.doc.po.DocOptRiskEvaluation;
import com.digihealth.anesthesia.doc.po.DocPatOutRangeAgree;
import com.digihealth.anesthesia.doc.po.DocPatShuttleTransfer;
import com.digihealth.anesthesia.doc.po.DocPlacentaHandleAgree;
import com.digihealth.anesthesia.doc.po.DocPostFollowRecord;
import com.digihealth.anesthesia.doc.po.DocPostOperRegard;
import com.digihealth.anesthesia.doc.po.DocPreOperVisit;
import com.digihealth.anesthesia.doc.po.DocPrePostVisit;
import com.digihealth.anesthesia.doc.po.DocPreVisit;
import com.digihealth.anesthesia.doc.po.DocRiskEvaluatPreventCare;
import com.digihealth.anesthesia.doc.po.DocSafeCheck;
import com.digihealth.anesthesia.doc.po.DocSelfPayInstrumentAccede;
import com.digihealth.anesthesia.doc.po.DocSpinalCanalPuncture;
import com.digihealth.anesthesia.doc.po.DocTransferConnectRecord;
import com.digihealth.anesthesia.doc.po.DocVeinAccede;

/**
 * Title: ControllerService.java Description: 描述
 * 
 * @author chengwang
 * @created 2015-10-19 下午4:42:09
 */
@Service
public class ControllerService extends BaseService {

    /**
     * 
     * @discription 改变状态
     * @author chengwang
     * @created 2015-10-19 下午4:44:04
     * @param id
     * @param state
     * @return
     */
    @Transactional
    public void checkOperation(String ids, String state,String previousState,ResponseValue respValue) {
        List<String> idsList = new ArrayList<String>();
        String[] idsString = ids.split(",");
        for (int i = 0; i < idsString.length; i++) {
            idsList.add(idsString[i]);
        }
        int succCnt = 0;
        if (idsList != null)
        {
            if (idsList.size() > 0)
            {
                for (int i = 0; i < idsList.size(); i++)
                {
                    String regOptId = idsList.get(i);
                    BasRegOpt regOpt = basRegOptDao.searchRegOptById(regOptId);
                    if (regOpt != null)
                    {
                        if (StringUtils.isEmpty(regOpt.getDesignedAnaesMethodName())
                            || StringUtils.isEmpty(regOpt.getDesignedAnaesMethodCode()))
                        {
                            continue;
                        }
                    }
                    Controller c = controllerDao.getControllerById(regOptId);
                    if (c != null)
                    {
                        if (c.getState().equals(OperationState.NOT_REVIEWED))
                        {
                            controllerDao.checkOperation(regOptId, state, previousState);
                            creatDocument(regOpt);
                            succCnt++;
                        }
                    }
                }
            }
            if (idsList.size() - succCnt > 0)
            {
                int failCnt = idsList.size() - succCnt;
                respValue.setResultMessage("批量审核完成!其中成功" + succCnt + "条数据," + "失败" + failCnt + "条数据");
            }
        }
    }
    
    /**
     * 
         * @discription 改变手术状态
         * @author chengwang       
         * @created 2015年10月30日 上午9:51:16
     */
    @Transactional
    public void changeControllerState(String regOptId,String state){
        Controller controller = getControllerById(regOptId);
        controllerDao.checkOperation(regOptId, state, controller.getState());
    }

    /**
     * 
     * @discription 根据手术ID获取控制表信息
     * @author chengwang
     * @created 2015-10-20 上午9:37:13
     * @param id
     * @return
     */
    public Controller getControllerById(String id) {
        return controllerDao.getControllerById(id);
    }

}
