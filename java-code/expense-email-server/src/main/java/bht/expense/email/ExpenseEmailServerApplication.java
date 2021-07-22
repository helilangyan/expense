package bht.expense.email;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@MapperScan("bht.expense.email")
public class ExpenseEmailServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseEmailServerApplication.class, args);
	}

}
