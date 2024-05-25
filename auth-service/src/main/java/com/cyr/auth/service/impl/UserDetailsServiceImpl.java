package com.cyr.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyr.auth.mapper.MenuMapper;
import com.cyr.auth.mapper.UserMapper;
import com.cyr.auth.model.po.User;
import com.cyr.auth.model.po.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author caiyu
 * @version 1.0
 * @description 将spring security默认的内存访问改为数据库访问
 * @date 2024/5/19 20:41
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MenuMapper menuMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 查询用户信息
		LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(User::getUsername, username);
		User user = userMapper.selectOne(queryWrapper);

		if (Objects.isNull(user)) {
			throw new RuntimeException("用户名或密码错误!!!");
		}

		// 查询数据库对应的权限信息
		List<String> list = menuMapper.selectPermsByUserId(user.getUserId());
		// 把数据封装成 UserDetail 返回
		return new LoginUser(user, list);
	}
}
