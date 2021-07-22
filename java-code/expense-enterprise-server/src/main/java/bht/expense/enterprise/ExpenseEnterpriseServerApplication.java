package bht.expense.enterprise;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@MapperScan("bht.expense.enterprise")
public class ExpenseEnterpriseServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseEnterpriseServerApplication.class, args);
	}

}
