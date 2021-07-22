package bht.expense.user.bank.dao;

import bht.expense.user.bank.entity.BankEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author 姚轶文
 * @date 2021/4/7 13:47
 */
public interface BankMapper  extends BaseMapper<BankEntity> {

    void del(Long id);

    void dels(Long[] id);

    List<BankEntity> findByAll(Map<String, String> map);
}
