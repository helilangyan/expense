package bht.expense.business.bpm.use.service;

import bht.expense.business.bpm.use.caller.BpmUseCaller;
import bht.expense.business.common.ResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 姚轶文
 * @date 2021/6/10 15:04
 */
@Service
public class BpmUseService {

    @Autowired
    BpmUseCaller bpmUseCaller;


    public ResultDto getTaskAuditors(String businessKey, String key, String taskId)
    {
        return bpmUseCaller.getTaskAuditors(businessKey, key, taskId);
    }

    public ResultDto approveBill(String billId, String approve, String comment, String nextUserId,String taskId)
    {
        return bpmUseCaller.approveBill(billId, approve, comment, nextUserId, taskId);
    }

    public ResultDto getTaskHistoriesPic(String businessKey ,String userId)
    {
        return bpmUseCaller.getTaskHistoriesPic(businessKey, userId);
    }

    public ResultDto myTaskDone(String userId, String startUserName, String type, String status, int page, int limit)
    {
        return bpmUseCaller.myTaskDone(userId, startUserName, type, status, page, limit);
    }

    public ResultDto getTaskHistoriesRecord(String businessId)
    {
        return bpmUseCaller.getTaskHistoriesRecord(businessId);
    }
}
