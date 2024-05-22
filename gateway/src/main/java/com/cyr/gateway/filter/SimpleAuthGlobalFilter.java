package com.cyr.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/17 21:35
 */
@Slf4j
@Component
@Deprecated
public class SimpleAuthGlobalFilter implements GlobalFilter, Ordered {

	@Resource
	private RedisTemplate redisTemplate;

	private static List<String> whiteList;

	// 白名单功能
	static {
		// 加载白名单
		try (
				InputStream resourceAsStream = SimpleAuthGlobalFilter.class.getResourceAsStream("/security-whitelist.properties");
		) {
			Properties properties = new Properties();
			properties.load(resourceAsStream);
			Set<String> strings = properties.stringPropertyNames();
			whiteList= new ArrayList<>(strings);
		} catch (IOException e) {
			whiteList = new ArrayList<>();
			log.error("加载/security-whitelist.properties出错:{}",e.getMessage());
			e.printStackTrace();
		}
	}

	// 检验token合法性
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		// 白名单放行
		String requestUrl = exchange.getRequest().getPath().value();
		AntPathMatcher pathMatcher = new AntPathMatcher();
		for (String url : whiteList) {
			if (pathMatcher.match(url, requestUrl)) {
				return chain.filter(exchange);
			}
		}

		// 检查 token 是否存在
		String token = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (StringUtils.isBlank(token)) {
			log.info("没有认证");
			return exchange.getResponse().setComplete();
		}
		// 判断是否有效的token
		Long expire = redisTemplate.getExpire(token);
		if (expire != null && expire == -2) {
			log.info("token已失效");
			return exchange.getResponse().setComplete();
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
