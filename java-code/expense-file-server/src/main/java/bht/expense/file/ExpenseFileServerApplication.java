package bht.expense.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("bht.expense.file")
public class ExpenseFileServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseFileServerApplication.class, args);
	}

}
