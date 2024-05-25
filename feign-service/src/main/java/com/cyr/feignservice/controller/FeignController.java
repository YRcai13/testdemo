package com.cyr.feignservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
	
	@GetMapping("/hello")
	public String hello() {
		return "hello world!!!";
	}

}
