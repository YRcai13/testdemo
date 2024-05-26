package com.cyr.feignservice.feignclient;

import com.cyr.feignservice.feignclient.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/26 11:20
 */
@FeignClient("user-service")
public interface UserClient {
	
	@GetMapping("/api/user/hello1")
	String userHello1();
	
	@GetMapping("/api/user/get1/{id}")
	User feignGetUserById(@PathVariable("id") int id);
	
}
