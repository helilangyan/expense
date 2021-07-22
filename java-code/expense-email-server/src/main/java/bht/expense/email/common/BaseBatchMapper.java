package bht.expense.email.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;


/**
 * @AUTHOR: GC
 * @DATE: 2020-10-27
 * @VERSION: 1.0
 * @DESCRIPTION: 让 mybatis-plus 支持批量插入,使用时继承该类
 */
public interface BaseBatchMapper<T> extends BaseMapper<T> {
    /**
     * @Description: 批量插入(mysql)
     * @param entityList
     * @Return java.lang.Integer
     * @Author:GC
     * @Date: 2020/10/27 10:23
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);
}
