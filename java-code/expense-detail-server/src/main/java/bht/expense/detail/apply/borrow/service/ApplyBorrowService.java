package bht.expense.detail.apply.borrow.service;

import bht.expense.detail.apply.borrow.dao.ApplyBorrowMapper;
import bht.expense.detail.apply.borrow.dto.ApplyBorrowInfoDto;
import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.caller.BpmUseCaller;
import bht.expense.detail.apply.copy.dao.ApplyCopyMapper;
import bht.expense.detail.apply.copy.entity.ApplyCopyEntity;
import bht.expense.detail.apply.expense.entity.ApplyExpenseEntity;
import bht.expense.detail.common.ResultDto;
import bht.expense.detail.common.TimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/22 10:21
 */

@Service
public class ApplyBorrowService {

    @Autowired
    ApplyBorrowMapper applyBorrowMapper;

    @Autowired
    BpmUseCaller bpmUseCaller;

    @Autowired
    ApplyCopyMapper applyCopyMapper;

    @Transactional
    public void delete(Long id)
    {
        applyBorrowMapper.delete(id);
    }

    public PageInfo findByUserIdAndEnterpriseId(Map<String , Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<ApplyBorrowInfoDto> list = applyBorrowMapper.findByUserIdAndEnterpriseId(map);
        PageInfo<ApplyBorrowInfoDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public ApplyBorrowInfoDto findById(Long id)
    {
        return applyBorrowMapper.findById(id);
    }

    @Transactional
    public void insert(ApplyBorrowEntity applyBorrowEntity)
    {
        if(applyBorrowEntity.getId()==null || applyBorrowEntity.getId()==0)
        {
            applyBorrowEntity.setCreateTime(TimeUtil.getUTC());
            applyBorrowMapper.insert(applyBorrowEntity);
        }
        else {
            applyBorrowMapper.updateById(applyBorrowEntity);
        }
    }

    @Transactional
    public void start(ApplyBorrowEntity applyBorrowEntity , String nextUserId , String[] copyUserIdS)
    {
        if(applyBorrowEntity.getId()==null || applyBorrowEntity.getId()==0)
        {
            applyBorrowEntity.setCreateTime(TimeUtil.getUTC());
            applyBorrowMapper.insert(applyBorrowEntity);
        }
        else {
            applyBorrowMapper.updateById(applyBorrowEntity);
        }
        ResultDto resultDto = bpmUseCaller.startBill(applyBorrowEntity.getId() + "", "key_" + applyBorrowEntity.getEnterpriseId() + "_3", applyBorrowEntity.getApplyName(), nextUserId);

        if(resultDto!=null && resultDto.getData()!=null)
        {
            Long billId = (Long)resultDto.getData();
            for(String copyUserId : copyUserIdS)
            {
                ApplyCopyEntity applyCopyEntity = new ApplyCopyEntity();
                applyCopyEntity.setBillId(billId);
                applyCopyEntity.setCopyUserId(Long.parseLong(copyUserId));
                applyCopyMapper.insert(applyCopyEntity);
            }

        }
    }
}
