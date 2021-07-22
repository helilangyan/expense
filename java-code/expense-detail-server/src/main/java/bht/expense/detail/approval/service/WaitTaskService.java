package bht.expense.detail.approval.service;

import bht.expense.detail.apply.caller.BpmUseCaller;
import bht.expense.detail.approval.dto.TaskListDto;
import bht.expense.detail.common.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/6/16 16:40
 */

@Service
public class WaitTaskService {

    @Autowired
    BpmUseCaller bpmUseCaller;

    public TaskListDto myTasks(String key , String userId, int page, int limit)
    {
       return  bpmUseCaller.myTasks(key, userId, page, limit);
    }
}
