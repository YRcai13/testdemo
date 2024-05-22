package com.cyr.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyr.common.BaseResponse;
import com.cyr.auth.model.po.User;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/17 14:45
 */
public interface LoginService extends IService<User> {
	BaseResponse login(User user);
	
	BaseResponse logout();
}
