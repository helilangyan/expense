package bht.expense.business.enterprise.caller.bank;

import bht.expense.business.common.ResultDto;
import bht.expense.business.enterprise.bank.dto.EnterpriseBankEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/7 15:37
 */
@Slf4j
public class EnterpriseBankCallerError implements EnterpriseBankCaller {

    @Override
    public ResultDto list(int page, int limit, Long enterpriseId) {
        log.error("invoke expense-user-server: /bank/list error");
        return null;
    }


    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-user-server: /bank/findById error");
        return null;
    }

    @Override
    public ResultDto insert(EnterpriseBankEntityDto bankEntityDto) {
        log.error("invoke expense-user-server: /bank/insert error");
        return null;
    }


    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-user-server: /bank/delete error");
        return null;
    }

}
