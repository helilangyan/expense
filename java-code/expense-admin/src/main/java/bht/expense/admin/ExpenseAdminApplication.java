package bht.expense.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@MapperScan("bht.expense.admin")
public class ExpenseAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseAdminApplication.class, args);
	}

}
