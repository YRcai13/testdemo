package com.cyr.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyr.common.BaseResponse;
import com.cyr.auth.mapper.UserMapper;
import com.cyr.auth.model.po.User;
import com.cyr.auth.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/17 14:12
 */
@Slf4j
@RestController
@RequestMapping("/")
public class LoginController {

	@Resource
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private LoginService loginService;

	@Autowired
	private UserMapper userMapper;

	@PostMapping("/login")
	public BaseResponse login(@RequestBody User user) {
		log.info("成功进去login");
		return loginService.login(user);
	}

	@GetMapping("/get/{id}")
	@PreAuthorize("hasAuthority('system:user:list')")
	public User getUserById(@PathVariable("id") int id) {
		log.info("成功进去get");
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(User::getUserId, id);
		User user = userMapper.selectOne(queryWrapper);
		return user;
	}
	
	@GetMapping("/get1/{id}")
	@PreAuthorize("hasAuthority('system:test:list')")
	public User getUserById1(@PathVariable("id") int id) {
		log.info("成功进去get");
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
		queryWrapper.eq(User::getUserId, id);
		User user = userMapper.selectOne(queryWrapper);
		return user;
	}
	
	@PostMapping("/mylogout")
	public BaseResponse logout() {
		log.info("成功进去logout");
		return loginService.logout();
	}

}
