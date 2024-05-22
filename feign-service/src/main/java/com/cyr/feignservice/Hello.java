package com.cyr.feignservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/16 19:30
 */
@RestController
@RequestMapping("/")
public class Hello {

	@GetMapping("/hello")
	public String hello() {
		return "hello world";
	}

}
