package com.cyr.feignservice.handle;

import com.alibaba.fastjson.JSON;
import com.cyr.common.BaseResponse;
import org.apache.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author caiyu
 * @version 1.0
 * @description 权限异常处理器
 * @date 2024/5/22 12:14
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
			throws IOException, ServletException {
		// 处理异常
		BaseResponse result = new BaseResponse(HttpStatus.SC_FORBIDDEN, "权限不足");
		String jsonResult = JSON.toJSONString(result);
		response.setStatus(200);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		response.getWriter().print(jsonResult);
	}
}
