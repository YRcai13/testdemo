package com.cyr.feignservice;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caiyu
 * @version 1.0
 * @description hello测试接口
 * @date 2024/5/16 19:30
 */
@RestController
@RequestMapping("/")
public class Hello {

	@GetMapping("/hello1")
	@PreAuthorize("hasAuthority('system:test:list')")
	public String hello1() {
		return "hello world";
	}
	
	@GetMapping("/hello")
	@PreAuthorize("hasAuthority('system:user:list')")
	public String hello() {
		return "hello world";
	}

}
