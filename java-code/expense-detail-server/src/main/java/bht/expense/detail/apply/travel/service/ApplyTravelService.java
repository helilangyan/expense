package bht.expense.detail.apply.travel.service;

import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.caller.BpmUseCaller;
import bht.expense.detail.apply.copy.dao.ApplyCopyMapper;
import bht.expense.detail.apply.copy.entity.ApplyCopyEntity;
import bht.expense.detail.apply.travel.dao.ApplyTravelDetailMapper;
import bht.expense.detail.apply.travel.dao.ApplyTravelMapper;
import bht.expense.detail.apply.travel.dao.ApplyTravelUserMapper;
import bht.expense.detail.apply.travel.dto.ApplyTravelInfoDto;
import bht.expense.detail.apply.travel.dto.ApplyTravelUserDto;
import bht.expense.detail.apply.travel.entity.ApplyTravelDetailEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelEntity;
import bht.expense.detail.apply.travel.entity.ApplyTravelUserEntity;
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
 * @date 2021/4/22 13:28
 */

@Service
public class ApplyTravelService {

    @Autowired
    ApplyTravelMapper applyTravelMapper;

    @Autowired
    ApplyTravelDetailMapper applyTravelDetailMapper;

    @Autowired
    ApplyTravelUserMapper applyTravelUserMapper;

    @Autowired
    BpmUseCaller bpmUseCaller;

    @Autowired
    ApplyCopyMapper applyCopyMapper;

    @Transactional
    public void insert(ApplyTravelEntity applyTravelEntity , List<ApplyTravelDetailEntity> detailList , List<ApplyTravelUserEntity> userList)
    {
        if(applyTravelEntity.getId()==null || applyTravelEntity.getId()==0)
        {
            applyTravelEntity.setCreateTime(TimeUtil.getUTC());
            applyTravelMapper.insert(applyTravelEntity);
        }
        else {
            applyTravelMapper.updateById(applyTravelEntity);
            applyTravelDetailMapper.deleteByApplyId(applyTravelEntity.getId());
            applyTravelUserMapper.deleteByApplyId(applyTravelEntity.getId());
        }

        for(ApplyTravelDetailEntity applyTravelDetailEntity : detailList)
        {
            applyTravelDetailEntity.setApplyId(applyTravelEntity.getId());
            applyTravelDetailMapper.insert(applyTravelDetailEntity);
        }

        for(ApplyTravelUserEntity applyTravelUserEntity : userList)
        {
            applyTravelUserEntity.setApplyId(applyTravelEntity.getId());
            applyTravelUserMapper.insert(applyTravelUserEntity);
        }
    }

    @Transactional
    public void start(ApplyTravelEntity applyTravelEntity ,  List<ApplyTravelDetailEntity> detailList , List<ApplyTravelUserEntity> userList , String nextUserId , String[] copyUserIdS)
    {
        if(applyTravelEntity.getId()==null || applyTravelEntity.getId()==0)
        {
            applyTravelEntity.setCreateTime(TimeUtil.getUTC());
            applyTravelMapper.insert(applyTravelEntity);
        }
        else {
            applyTravelMapper.updateById(applyTravelEntity);
            applyTravelDetailMapper.deleteByApplyId(applyTravelEntity.getId());
            applyTravelUserMapper.deleteByApplyId(applyTravelEntity.getId());
        }

        for(ApplyTravelDetailEntity applyTravelDetailEntity : detailList)
        {
            applyTravelDetailEntity.setApplyId(applyTravelEntity.getId());
            applyTravelDetailMapper.insert(applyTravelDetailEntity);
        }

        for(ApplyTravelUserEntity applyTravelUserEntity : userList)
        {
            applyTravelUserEntity.setApplyId(applyTravelEntity.getId());
            applyTravelUserMapper.insert(applyTravelUserEntity);
        }

        ResultDto resultDto = bpmUseCaller.startBill(applyTravelEntity.getId() + "", "key_" + applyTravelEntity.getEnterpriseId() + "_2", applyTravelEntity.getApplyName(), nextUserId);

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
        applyTravelMapper.delete(id);
        applyTravelUserMapper.deleteByApplyId(id);
        applyTravelDetailMapper.deleteByApplyId(id);
    }

    public PageInfo findByUserIdAndEnterpriseId(Map<String , Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<ApplyTravelEntity> list = applyTravelMapper.findByUserIdAndEnterpriseId(map);
        PageInfo<ApplyTravelEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public ApplyTravelInfoDto findById(Long id)
    {
        ApplyTravelInfoDto applyTravelInfoDto = new ApplyTravelInfoDto();

        ApplyTravelEntity applyTravelEntity = applyTravelMapper.selectById(id);
        List<ApplyTravelDetailEntity> detailList = applyTravelDetailMapper.findByApplyId(id);
        List<ApplyTravelUserDto> userList = applyTravelUserMapper.findByApplyId(id);

        applyTravelInfoDto.setApplyTravelEntity(applyTravelEntity);
        applyTravelInfoDto.setDetailList(detailList);
        applyTravelInfoDto.setUserList(userList);

        return applyTravelInfoDto;
    }
}
