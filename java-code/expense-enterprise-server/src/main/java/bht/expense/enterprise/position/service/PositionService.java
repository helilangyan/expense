package bht.expense.enterprise.position.service;

import bht.expense.enterprise.position.dao.PositionMapper;
import bht.expense.enterprise.position.entity.PositionEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/16 14:24
 */
@Service
public class PositionService {

    @Autowired
    PositionMapper positionMapper;

    @Transactional
    public void delete(Long id)
    {
        positionMapper.delete(id);
    }

    @Transactional
    public void deletes(Long[] id)
    {
        positionMapper.deletes(id);
    }

    public PageInfo findByEnterpriseId(Map<String, Object> map, int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<PositionEntity> list = positionMapper.findByEnterpriseId(map);
        PageInfo<PositionEntity> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Transactional
    public PositionEntity insert(PositionEntity positionEntity)
    {
        if(positionEntity.getId()==null || positionEntity.getId()==0)
        {
            positionMapper.insert(positionEntity);
        }
        else {
            positionMapper.updateById(positionEntity);
        }
        return positionEntity;
    }

    public PositionEntity findById(Long id)
    {
        return positionMapper.selectById(id);
    }

}
