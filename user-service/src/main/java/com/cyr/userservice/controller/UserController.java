package com.cyr.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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
public class UserController {

	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	@GetMapping("/hello")
	public String hello() {
		return "hello world!!!";
	}
	
//	@GetMapping("/get/{id}")
//	public User getUserById(@PathVariable("id") int id) {
//		log.info("成功进去get");
//		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
//		queryWrapper.eq(User::getUserId, id);
//		User user = userMapper.selectOne(queryWrapper);
//		return user;
//	}
//
//	@GetMapping("/get1/{id}")
//	public User getUserById1(@PathVariable("id") int id) {
//		log.info("成功进去get");
//		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
//		queryWrapper.eq(User::getUserId, id);
//		User user = userMapper.selectOne(queryWrapper);
//		return user;
//	}

}
