package bht.expense.bpm.design.dao;

import bht.expense.bpm.design.entity.ActivitiStep;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ActivitiStepMapper extends BaseMapper<ActivitiStep> {
    List<ActivitiStep> selectAllByFlowid(@Param("flowId") String flowId);
}