package com.cyr;

import com.cyr.userservice.mapper.MenuMapper;
import com.cyr.userservice.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/21 21:21
 */
@Component
public class MapperTest {
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Test
	public void testSelectPermsByUserId() {
		System.out.println(userMapper);
		System.out.println(menuMapper);
		List<String> list = menuMapper.selectPermsByUserId(2L);
		System.out.println(list);
	}
}
