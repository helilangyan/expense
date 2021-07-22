package bht.expense.bpm.use.dao;

import bht.expense.bpm.use.dto.UserHistoricTaskDto;
import bht.expense.bpm.use.dto.UserWaitHandTaskDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @AUTHOR: GC
 * @DATE: 2020-10-22
 * @VERSION: 1.0
 * @DESCRIPTION: 用户查询流程任务mapper
 */
public interface UserQueryTaskMapper extends BaseMapper {
    List<UserWaitHandTaskDto> selectUserQueryTasks(
        @Param("userId") String userId, @Param("startUsername") String startUsername, @Param("type") String type,
        @Param("status") String status
    );

    List<UserWaitHandTaskDto> myTaskDone(
            @Param("userId") String userId, @Param("startUsername") String startUsername, @Param("type") String type,
            @Param("status") String status
    );

    /**
     * @Description: 流程历史
     * @param businessId 业务id
     * @Return java.util.List<com.bht.hrms.sys.workflow.flow.dto.UserHistoricTaskDto>
     * @Author:GC
     * @Date: 2020/10/28 10:31
     */
    List<UserHistoricTaskDto> selectHistoricTasks(@Param("businessId") String businessId);
}
