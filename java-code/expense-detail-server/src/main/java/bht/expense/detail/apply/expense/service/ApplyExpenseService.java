package bht.expense.detail.apply.expense.service;

import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.caller.BpmUseCaller;
import bht.expense.detail.apply.copy.dao.ApplyCopyMapper;
import bht.expense.detail.apply.copy.entity.ApplyCopyEntity;
import bht.expense.detail.apply.expense.dao.ApplyExpenseDetailMapper;
import bht.expense.detail.apply.expense.dao.ApplyExpenseMapper;
import bht.expense.detail.apply.expense.dao.ApplyExpensePrepMapper;
import bht.expense.detail.apply.expense.dto.ApplyExpenseInfoDto;
import bht.expense.detail.apply.expense.entity.ApplyExpenseDetailEntity;
import bht.expense.detail.apply.expense.entity.ApplyExpenseEntity;
import bht.expense.detail.apply.expense.entity.ApplyExpensePrepEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelDetailEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelUserEntity;
import bht.expense.detail.common.ResultDto;
import bht.expense.detail.common.TimeUtil;
import bht.expense.detail.detail.entity.ExpenseDetailEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/21 15:32
 */
@Service
public class ApplyExpenseService {

    @Autowired
    ApplyExpenseMapper applyExpenseMapper;

    @Autowired
    ApplyExpenseDetailMapper applyExpenseDetailMapper;

    @Autowired
    ApplyExpensePrepMapper applyExpensePrepMapper;

    @Autowired
    BpmUseCaller bpmUseCaller;

    @Autowired
    ApplyCopyMapper applyCopyMapper;


    @Transactional
    public void insert(ApplyExpenseEntity applyExpenseEntity , Long[] detailIds , Long[] borrowIds , Long[] travelIds)
    {
        if(applyExpenseEntity.getId()==null || applyExpenseEntity.getId()==0)
        {
            applyExpenseEntity.setCreateTime(TimeUtil.getUTC());
            applyExpenseMapper.insert(applyExpenseEntity);
        }
        else{
            applyExpenseMapper.updateById(applyExpenseEntity);
            applyExpenseDetailMapper.deleteByApplyId(applyExpenseEntity.getId());
            applyExpensePrepMapper.deleteByApplyId(applyExpenseEntity.getId());
        }

        for(Long detailId : detailIds)
        {
            ApplyExpenseDetailEntity applyExpenseDetailEntity = new ApplyExpenseDetailEntity();
            applyExpenseDetailEntity.setDetailId(detailId);
            applyExpenseDetailEntity.setApplyId(applyExpenseEntity.getId());
            applyExpenseDetailMapper.insert(applyExpenseDetailEntity);
        }

        for(Long borrowId : borrowIds)
        {
            ApplyExpensePrepEntity applyExpensePrepEntity = new ApplyExpensePrepEntity();
            applyExpensePrepEntity.setApplyId(applyExpenseEntity.getId());
            applyExpensePrepEntity.setBorrowId(borrowId);
            applyExpensePrepMapper.insert(applyExpensePrepEntity);
        }

        for(Long travelId : travelIds)
        {
            ApplyExpensePrepEntity applyExpensePrepEntity = new ApplyExpensePrepEntity();
            applyExpensePrepEntity.setApplyId(applyExpenseEntity.getId());
            applyExpensePrepEntity.setTravelId(travelId);
            applyExpensePrepMapper.insert(applyExpensePrepEntity);
        }
    }

    @Transactional
    public void start(ApplyExpenseEntity applyExpenseEntity , Long[] detailIds , Long[] borrowIds , Long[] travelIds , String nextUserId , String[] copyUserIdS)
    {
        if(applyExpenseEntity.getId()==null || applyExpenseEntity.getId()==0)
        {
            applyExpenseEntity.setCreateTime(TimeUtil.getUTC());
            applyExpenseMapper.insert(applyExpenseEntity);
        }
        else{
            applyExpenseMapper.updateById(applyExpenseEntity);
            applyExpenseDetailMapper.deleteByApplyId(applyExpenseEntity.getId());
            applyExpensePrepMapper.deleteByApplyId(applyExpenseEntity.getId());
        }

        for(Long detailId : detailIds)
        {
            ApplyExpenseDetailEntity applyExpenseDetailEntity = new ApplyExpenseDetailEntity();
            applyExpenseDetailEntity.setDetailId(detailId);
            applyExpenseDetailEntity.setApplyId(applyExpenseEntity.getId());
            applyExpenseDetailMapper.insert(applyExpenseDetailEntity);
        }

        for(Long borrowId : borrowIds)
        {
            ApplyExpensePrepEntity applyExpensePrepEntity = new ApplyExpensePrepEntity();
            applyExpensePrepEntity.setApplyId(applyExpenseEntity.getId());
            applyExpensePrepEntity.setBorrowId(borrowId);
            applyExpensePrepMapper.insert(applyExpensePrepEntity);
        }

        for(Long travelId : travelIds)
        {
            ApplyExpensePrepEntity applyExpensePrepEntity = new ApplyExpensePrepEntity();
            applyExpensePrepEntity.setApplyId(applyExpenseEntity.getId());
            applyExpensePrepEntity.setTravelId(travelId);
            applyExpensePrepMapper.insert(applyExpensePrepEntity);
        }

        ResultDto resultDto = bpmUseCaller.startBill(applyExpenseEntity.getId() + "", "key_" + applyExpenseEntity.getEnterpriseId() + "_1", applyExpenseEntity.getApplyName(), nextUserId);

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

    @Transactional
    public void delete(Long id)
    {
        applyExpenseMapper.delete(id);
        applyExpenseDetailMapper.deleteByApplyId(id);
        applyExpensePrepMapper.deleteByApplyId(id);
    }

    public PageInfo findByUserIdAndEnterpriseId(Map<String , Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<ApplyExpenseEntity> list = applyExpenseMapper.findByUserIdAndEnterpriseId(map);
        PageInfo<ApplyExpenseEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public ApplyExpenseInfoDto findById(Long id)
    {
        ApplyExpenseInfoDto applyExpenseInfoDto = new ApplyExpenseInfoDto();

        ApplyExpenseEntity applyExpenseEntity = applyExpenseMapper.selectById(id);
        List<ExpenseDetailEntity> detailList = applyExpenseDetailMapper.findByApplyId(id);
        List<ApplyBorrowEntity> borrowList = applyExpensePrepMapper.findByBorrow(id);
        List<ApplyTravelEntity> travelList = applyExpensePrepMapper.findByTravel(id);

        applyExpenseInfoDto.setApplyExpenseEntity(applyExpenseEntity);
        applyExpenseInfoDto.setDetailList(detailList);
        applyExpenseInfoDto.setBorrowList(borrowList);
        applyExpenseInfoDto.setTravelList(travelList);
        return applyExpenseInfoDto;
    }
}
