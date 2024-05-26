package com.cyr.feignservice.controller;

import com.cyr.feignservice.feignclient.UserClient;
import com.cyr.feignservice.feignclient.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author caiyu
 * @version 1.0
 * @description 登录接口
 * @date 2024/5/17 14:12
 */
@Slf4j
@RestController
@RequestMapping("/")
public class FeignController {
	
	@Autowired
	private UserClient userClient;
	
	@GetMapping("/feignList")
	public List<User> feignList() {
		return userClient.feignList();
	}
	
	@GetMapping("/feignGet/{id}")
	public User feignGet(@PathVariable("id") int id) {
		return userClient.feignGetUserById(id);
	}

}
