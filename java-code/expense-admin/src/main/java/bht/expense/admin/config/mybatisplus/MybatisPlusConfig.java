package bht.expense.admin.config.mybatisplus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @AUTHOR: GC
 * @DATE: 2020-10-27
 * @VERSION: 1.0
 * @DESCRIPTION:
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public HrmsSqlInjector hrmsSqlInjector() {
        return new HrmsSqlInjector();
    }
}
