package bht.expense.file.log.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import bht.expense.file.log.entity.LogEntity;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2020/7/20 10:00
 */
public interface LogMapper extends BaseMapper<LogEntity> {

    List<LogEntity> findByAll(Map<String, String> map);
}
