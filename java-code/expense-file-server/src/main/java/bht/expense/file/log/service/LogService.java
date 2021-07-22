package bht.expense.file.log.service;

import bht.expense.file.log.dao.LogMapper;
import bht.expense.file.log.entity.LogEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/20 11:02
 */
@Service
public class LogService {

    @Autowired
    LogMapper logMapper;

    @Transactional
    public LogEntity insert(LogEntity logEntity)
    {
        logMapper.insert(logEntity);
        return logEntity;
    }

    public LogEntity findById(String id)
    {
        return logMapper.selectById(id);
    }

    public PageInfo findByAll(Map<String,String> map , int page, int limit)
    {
        PageHelper.startPage(page, limit);
        List<LogEntity> list = logMapper.findByAll(map);
        PageInfo<LogEntity> pageInfo = new PageInfo<LogEntity>(list);
        return pageInfo;
    }
}
