package bht.expense.bpm.bill.service;

import bht.expense.bpm.bill.dao.ActivitiBillMapper;
import bht.expense.bpm.bill.entity.ActivitiBillEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/6/17 10:39
 */
@Service
public class ActivitiBillService {

    @Autowired
    ActivitiBillMapper activitiBillMapper;

    @Transactional
    public void insert(ActivitiBillEntity activitiBillEntity)
    {
        if(activitiBillEntity.getId()==null || activitiBillEntity.getId()==0)
        {
            activitiBillMapper.insert(activitiBillEntity);
        }
        else
        {
            activitiBillMapper.updateById(activitiBillEntity);
        }
    }

    /**
     * @Description: 更新业务单
     * @param billId 业务id
     * @param title 标题
     * @Return void
     * @Author:GC
     * @Date: 2020/10/27 13:46
     */
    @Transactional
    public void updateBill(String billId, String title, String instanceId, String status, Date endTime){
        ActivitiBillEntity activitiBill = activitiBillMapper.selectById(billId);
        if(activitiBill!=null){
            if(!StringUtils.isEmpty(title)){
                activitiBill.setTitle(title);
            }
            if(!StringUtils.isEmpty(instanceId)){
                activitiBill.setInstanceId(instanceId);
            }
            if(status!=null){
                activitiBill.setStatus(status);
            }
            if(endTime!=null){
                activitiBill.setEndTime(endTime);
            }
            activitiBillMapper.updateById(activitiBill);
        }
    }

    public ActivitiBillEntity findByBusinessIdAndInstanceId(String instanceId , Long businessId)
    {
        return activitiBillMapper.findByBusinessIdAndInstanceId(instanceId, businessId);
    }
}
