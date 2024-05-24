package com.cyr.feignservice.handle;

import com.alibaba.fastjson.JSON;
import com.cyr.common.BaseResponse;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author caiyu
 * @version 1.0
 * @description 认证异常处理器
 * @date 2024/5/22 12:07
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		// 处理异常
		BaseResponse result = new BaseResponse(HttpStatus.SC_UNAUTHORIZED, "用户认证失败，请查询登录");
		String jsonResult = JSON.toJSONString(result);
		response.setStatus(200);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.getWriter().print(jsonResult);
	}
}
