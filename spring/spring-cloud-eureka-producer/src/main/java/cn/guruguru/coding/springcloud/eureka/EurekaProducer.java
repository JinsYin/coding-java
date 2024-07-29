package cn.guruguru.coding.springcloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务提供方
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaProducer {

	public static void main(String[] args) {
		SpringApplication.run(EurekaProducer.class, args);
	}

}
