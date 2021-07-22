package bht.expense.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@MapperScan("bht.expense.business")
public class ExpenseBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseBusinessApplication.class, args);
	}

}
