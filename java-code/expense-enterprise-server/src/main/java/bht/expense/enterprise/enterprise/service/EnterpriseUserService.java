package bht.expense.enterprise.enterprise.service;

import bht.expense.enterprise.department.dto.DepartmentUserDto;
import bht.expense.enterprise.enterprise.dao.EnterpriseUserMapper;
import bht.expense.enterprise.enterprise.dto.EnterpriseUserDto;
import bht.expense.enterprise.enterprise.entity.EnterpriseEntity;
import bht.expense.enterprise.enterprise.entity.EnterpriseUserEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/12 11:43
 */
@Service
public class EnterpriseUserService {

    @Autowired
    EnterpriseUserMapper enterpriseUserMapper;

    public List<EnterpriseUserDto> findByUserId(Long userId)
    {
        return enterpriseUserMapper.findByUserId(userId);
    }

    @Transactional
    public void insert(EnterpriseUserEntity enterpriseUserEntity)
    {
        enterpriseUserMapper.insert(enterpriseUserEntity);
    }

    @Transactional
    public void update(EnterpriseUserEntity enterpriseUserEntity)
    {
        enterpriseUserMapper.updateById(enterpriseUserEntity);
    }

    public PageInfo findByEnterpriseId(int page, int limit, Long enterpriseId)
    {
        PageHelper.startPage(page, limit);
        List<EnterpriseUserDto> list = enterpriseUserMapper.findByEnterpriseId(enterpriseId);
        PageInfo<EnterpriseUserDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public void setDefault(Long id , Long userId)
    {
        enterpriseUserMapper.clearDefault(userId);
        enterpriseUserMapper.setDefault(id);
    }

    public DepartmentUserDto findById(Long id)
    {
        return enterpriseUserMapper.findById(id);
    }

    @Transactional
    public void remove(Long id)
    {
        enterpriseUserMapper.deleteById(id);
    }
}
