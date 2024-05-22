package com.cyr.auth.config;

import com.cyr.auth.model.po.LoginUser;
import com.cyr.auth.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/20 14:01
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	private RedisTemplate redisTemplate;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 获取token
		String token = request.getHeader("token");
		if (StringUtils.isEmpty(token)) {
			filterChain.doFilter(request, response);
			return;
		}
		// 解析token获取userid
		String userid;
		try {
			Claims claims = JwtUtil.parseJWT(token);
			userid = claims.getSubject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("token非法");
		}
		// 从redis读取用户信息
		String redisKey = "login:" + userid;
		LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(redisKey);
		if (Objects.isNull(loginUser)) {
			throw new RuntimeException("用户未登录");
		}
		// 存入SecurityContextHolder
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}
}
