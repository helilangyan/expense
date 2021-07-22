package bht.expense.enterprise.enterprise.service;

import bht.expense.enterprise.enterprise.dao.EnterpriseUserApplyMapper;
import bht.expense.enterprise.enterprise.dao.EnterpriseUserMapper;
import bht.expense.enterprise.enterprise.dto.ApproveDto;
import bht.expense.enterprise.enterprise.dto.EnterpriseUserApplyDto;
import bht.expense.enterprise.enterprise.entity.EnterpriseUserApplyEntity;
import bht.expense.enterprise.enterprise.entity.EnterpriseUserEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/4/12 10:33
 */
@Service
public class EnterpriseUserApplyService {

    @Autowired
    EnterpriseUserApplyMapper enterpriseUserApplyMapper;

    @Autowired
    EnterpriseUserMapper enterpriseUserMapper;

    public PageInfo findByEnterpriseId(Long enterpriseId, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<EnterpriseUserApplyDto> list = enterpriseUserApplyMapper.findByEnterpriseId(enterpriseId);
        PageInfo<EnterpriseUserApplyDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<EnterpriseUserApplyDto> findByUserId(Long userId)
    {
        return enterpriseUserApplyMapper.findByUserId(userId);
    }

    @Transactional
    public void approve(ApproveDto approveDto)
    {
        if(approveDto.getStatus().equals("1"))
        {
            EnterpriseUserEntity enterpriseUserEntity = new EnterpriseUserEntity();
            enterpriseUserEntity.setEnterpriseId(approveDto.getEnterpriseId());
            enterpriseUserEntity.setUserId(approveDto.getUserId());
            enterpriseUserEntity.setIsDefault(0);
            enterpriseUserEntity.setDepartmentId(approveDto.getDepartmentId());
            enterpriseUserEntity.setPositionId(approveDto.getPositionId());
            enterpriseUserEntity.setRoleId(approveDto.getRoleId());
            enterpriseUserEntity.setEmployeeCode(approveDto.getEmployeeCode());
            enterpriseUserEntity.setSuperior(approveDto.getSuperior());
            enterpriseUserMapper.insert(enterpriseUserEntity);
        }

        enterpriseUserApplyMapper.approve(approveDto.getStatus(),approveDto.getMessage(),approveDto.getApplyId());
    }

    @Transactional
    public void insert(EnterpriseUserApplyEntity enterpriseUserApplyEntity)
    {
        enterpriseUserApplyMapper.insert(enterpriseUserApplyEntity);
    }
}
