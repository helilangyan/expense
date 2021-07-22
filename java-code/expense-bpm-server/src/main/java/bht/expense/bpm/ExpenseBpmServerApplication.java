package bht.expense.bpm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@MapperScan("bht.expense.bpm")
public class ExpenseBpmServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseBpmServerApplication.class, args);
	}

}
