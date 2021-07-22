package bht.expense.enterprise.enterprise.service;

import bht.expense.enterprise.common.TimeUtil;
import bht.expense.enterprise.department.dto.DepartmentUserDto;
import bht.expense.enterprise.enterprise.dao.EnterpriseMapper;
import bht.expense.enterprise.enterprise.dao.EnterpriseUserMapper;
import bht.expense.enterprise.enterprise.entity.EnterpriseEntity;
import bht.expense.enterprise.enterprise.entity.EnterpriseUserEntity;
import bht.expense.enterprise.util.RandomCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/9 15:55
 */

@Service
public class EnterpriseService {

    @Autowired
    EnterpriseMapper enterpriseMapper;

    @Autowired
    EnterpriseUserMapper enterpriseUserMapper;

    @Transactional
    public void del(Long id)
    {
        enterpriseMapper.del(id);
    }

    @Transactional
    public void dels(Long[] id)
    {
        enterpriseMapper.dels(id);
    }

    public PageInfo findByAll(Map<String, String> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<EnterpriseEntity> list = enterpriseMapper.findByAll(map);
        PageInfo<EnterpriseEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public EnterpriseEntity insert(EnterpriseEntity enterpriseEntity)
    {
        if(enterpriseEntity.getId()==null || enterpriseEntity.getId()==0)
        {
            enterpriseEntity.setCreateTime(TimeUtil.getUTC());
            enterpriseEntity.setStatus("0");
            enterpriseEntity.setInvitationCode(RandomCode.randomCode(10) );
            enterpriseMapper.insert(enterpriseEntity);
        }
        else {
            enterpriseMapper.updateById(enterpriseEntity);
        }
        return enterpriseEntity;
    }

    public EnterpriseEntity findById(Long id)
    {
        return enterpriseMapper.selectById(id);
    }

    public Long checkTaxCode(String taxCode , Long id)
    {
        return enterpriseMapper.checkTaxCode(taxCode , id);
    }

    public List<EnterpriseEntity> findByUserId(Long userId)
    {
        return enterpriseMapper.findByUserId(userId);
    }

    @Transactional
    public void approve(String status , Long id)
    {
        if(status.equals("1"))
        {
            EnterpriseEntity enterpriseEntity = findById(id);

            EnterpriseUserEntity enterpriseUserEntity = new EnterpriseUserEntity();
            enterpriseUserEntity.setUserId(enterpriseEntity.getUserId());
            enterpriseUserEntity.setEnterpriseId(id);
            enterpriseUserEntity.setIsDefault(0);
            enterpriseUserMapper.insert(enterpriseUserEntity);
        }
        enterpriseMapper.approve(status,id);
    }

    public Long findByInvitationCode(String invitationCode)
    {
        return enterpriseMapper.findByInvitationCode(invitationCode);
    }


}
