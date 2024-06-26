package com.cyr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author caiyu
 * @version 1.0
 * @description feign启动类
 * @date 2024/5/16 19:51
 */

@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class FeignServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FeignServiceApplication.class, args);
	}
}
