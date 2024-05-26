package com.cyr.feignservice.feignclient;

import com.cyr.feignservice.feignclient.po.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author caiyu
 * @version 1.0
 * @description 远程调用用户服务的接口
 * @date 2024/5/26 11:20
 */
@FeignClient("user-service")
public interface UserClient {
	
	@GetMapping("/api/user/list")
	List<User> feignList();
	
	@GetMapping("/api/user/get/{id}")
	User feignGetUserById(@PathVariable("id") int id);
	
}
