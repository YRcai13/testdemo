package com.cyr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/16 19:51
 */

@SpringBootApplication
@MapperScan("com.cyr.auth.mapper")
@EnableDiscoveryClient
public class AuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
