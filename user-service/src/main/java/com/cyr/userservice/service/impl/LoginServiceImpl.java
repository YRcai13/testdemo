package com.cyr.userservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyr.common.BaseResponse;
import com.cyr.userservice.mapper.UserMapper;
import com.cyr.userservice.model.po.LoginUser;
import com.cyr.userservice.model.po.User;
import com.cyr.userservice.service.LoginService;
import com.cyr.userservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/17 14:46
 */
@Service
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {


	@Autowired
	UserMapper userMapper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public BaseResponse login(User user) {
		// AuthenticationManager authenticate进行用户认证
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//		 认证没通过
		if (Objects.isNull(authenticate)) {
			throw new RuntimeException("登录失败");
		}

//		认证通过，生成jwt
		LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
		String userId = String.valueOf(loginUser.getUser().getUserId());
		String jwt = JwtUtil.createJWT(userId);
		Map<String, String> map = new HashMap<>();
		map.put("token", jwt);
//		将信息存入redis，userid作为key
		redisTemplate.opsForValue().set("login:" + userId, loginUser, 360, TimeUnit.SECONDS);
		return new BaseResponse(200, map, "登录成功");
	}
	
	@Override
	public BaseResponse logout() {
		// 获取SecurityContextHolder中的userid
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 删除redis中userid对应的值
		LoginUser loginUser = (LoginUser) authentication.getPrincipal();
		String userid = String.valueOf(loginUser.getUser().getUserId());
		String redisKey = "login:" + userid;
		redisTemplate.delete(redisKey);
		return new BaseResponse(200, "注销成功");
	}
}
