package bht.expense.bpm.use.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 姚轶文
 * @date 2021/6/10 16:12 流程业务接口
 */
@Service
@Slf4j
public class WorkflowBillService {

    /**
     * @Description: 保存业务单
     * @param userId 用户
     * @param title 业务单标题
     * @param files 附件id
     * @param formId 表单id
     * @param details 表单值json字符串
     * @Return void
     * @Author: GC
     * @Date: 2020/10/27 11:10
     */
//    @Transactional
//    public String addBill(String billId,String userId, String title, String files,String formId,String details,String key,String nextUserId){
//        //保存主题表单
//        if(!StringUtils.isEmpty(billId)){
//            ActivitiBill dbActivitiBill = activitiBillMapper.selectById(billId);
//            if(dbActivitiBill!=null){
//                if (!StringUtils.isEmpty(title)){
//                    dbActivitiBill.setTitle(title);
//                }
//                if (!StringUtils.isEmpty(nextUserId)){
//                    dbActivitiBill.setNextId(nextUserId);
//                }
//                if (!StringUtils.isEmpty(files)){
//                    //重新保存文件
//                    List<String> fileIds = Arrays.asList(files.split(","));
//                    fileRelationService.deleteAllFileRelation(billId,"ADD_WORKFLOW");
//                    fileIds.forEach(s -> addFileRelation(s, billId, "ADD_WORKFLOW"));
//                }
//                updateBillForm(billId, details);
//                activitiBillMapper.updateById(dbActivitiBill);
//            }
//            return billId;
//        }else{
//            FormDesign formDesign = formDesignMapper.selectById(formId);
//            if(formDesign!=null){
//                ActivitiBill activitiBill = new ActivitiBill();
//                activitiBill.setContentHtml(formDesign.getContentHtml());
//                activitiBill.setContentJson(formDesign.getContentJson());
//                activitiBill.setFormId(formId);
//                activitiBill.setCreateTime(new Date());
//                activitiBill.setCreator(userId);
//                activitiBill.setFormName(formDesign.getName());
//                activitiBill.setStatus(1);
//                activitiBill.setTitle(title);
//                activitiBill.setKey(key);
//                activitiBill.setNextId(nextUserId);
//                activitiBillMapper.insert(activitiBill);
//                //保存文件关联
//                if(!StringUtils.isEmpty(files)){
//                    List<String> fileIds = Arrays.asList(files.split(","));
//                    fileIds.forEach(s -> addFileRelation(s, activitiBill.getId(), "ADD_WORKFLOW"));
//                }
//                //保存表单值
//                if(!StringUtils.isEmpty(details)) {
//                    log.info("待解析json:{}",details);
//                    try{
//                        JSONArray array = JSONArray.parseArray(details);
//                        List<BillFormDetail> billFormDetails = array.stream().map(o -> {
//                            JSONObject dataObj = JSONObject.parseObject(o.toString());
//                            BillFormDetail billFormDetail = new BillFormDetail();
//                            billFormDetail.setColumnId(dataObj.getString("detailFieldId"));
//                            billFormDetail.setBillId(activitiBill.getId());
//                            billFormDetail.setColumnValue(dataObj.getString("defaultValue"));
//                            billFormDetail.setDetailId(dataObj.getString("detailId"));
//                            billFormDetail.setFormId(formId);
//                            return billFormDetail;
//                        }).collect(Collectors.toList());
//
//                        billFormDetailMapper.insertBatchSomeColumn(billFormDetails);
//                    }catch (Exception e){
//                        log.error("json转换报错:",e);
//                    }
//
//                }
//                return activitiBill.getId();
//            }
//        }
//
//        return null;
//    }
}
