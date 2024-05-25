package com.cyr;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author caiyu
 * @version 1.0
 * @description auth启动类
 * @date 2024/5/16 19:51
 */

@SpringBootApplication
//@MapperScan("com.cyr.user.mapper")
@EnableDiscoveryClient
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}
}
