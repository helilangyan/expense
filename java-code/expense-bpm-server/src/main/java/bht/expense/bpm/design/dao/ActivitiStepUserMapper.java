package bht.expense.bpm.design.dao;

import bht.expense.bpm.design.entity.ActivitiStepUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ActivitiStepUserMapper extends BaseMapper<ActivitiStepUser> {
    
    List<ActivitiStepUser> selectByStepid(@Param("stepId") String stepId);

    void logicDel(@Param("stepId") String stepId);
}