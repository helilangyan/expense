package bht.expense.bpm.bill.dao;

import bht.expense.bpm.bill.entity.ActivitiBillEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author 姚轶文
 * @date 2021/6/17 10:28
 */
public interface ActivitiBillMapper extends BaseMapper<ActivitiBillEntity> {

    ActivitiBillEntity findByBusinessIdAndInstanceId(String instanceId , Long businessId);
}
