package bht.expense.detail.apply.copy.service;

import bht.expense.detail.apply.borrow.entity.ApplyBorrowEntity;
import bht.expense.detail.apply.copy.dao.ApplyCopyMapper;
import bht.expense.detail.apply.copy.dto.ApplyCopyDto;
import bht.expense.detail.apply.copy.entity.ApplyCopyEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 姚轶文
 * @date 2021/6/22 11:03
 */
@Service
public class ApplyCopyService {

    @Autowired
    ApplyCopyMapper applyCopyMapper;

    @Transactional
    public ApplyCopyEntity insert(ApplyCopyEntity applyCopyEntity)
    {
        applyCopyMapper.insert(applyCopyEntity);
        return applyCopyEntity;
    }

    public PageInfo toMyCopy(Long userId,int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<ApplyCopyDto> list = applyCopyMapper.toMyCopy(userId);
        PageInfo<ApplyCopyDto> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
