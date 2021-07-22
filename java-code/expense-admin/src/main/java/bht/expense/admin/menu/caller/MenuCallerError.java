package bht.expense.admin.menu.caller;

import bht.expense.admin.common.ResultDto;
import bht.expense.admin.menu.dto.MenuEntityDto;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 姚轶文
 * @date 2021/4/14 11:11
 */
@Slf4j
public class MenuCallerError implements MenuCaller{
    @Override
    public ResultDto list(int page, int limit) {
        log.error("invoke expense-enterprise-server: /menu/list error");
        return null;
    }

    @Override
    public ResultDto findById(Long id) {
        log.error("invoke expense-enterprise-server: /menu/findById error");
        return null;
    }

    @Override
    public ResultDto insert(MenuEntityDto menuEntityDto) {
        log.error("invoke expense-enterprise-server: /menu/insert error");
        return null;
    }

    @Override
    public ResultDto delete(Long id) {
        log.error("invoke expense-enterprise-server: /menu/delete error");
        return null;
    }

    @Override
    public ResultDto deletes(Long[] id) {
        log.error("invoke expense-enterprise-server: /menu/deletes error");
        return null;
    }
}
