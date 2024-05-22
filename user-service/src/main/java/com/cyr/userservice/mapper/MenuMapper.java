package com.cyr.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyr.userservice.model.po.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/21 21:14
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
	List<String> selectPermsByUserId(Long userId);
}
