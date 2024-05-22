package com.cyr.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyr.userservice.model.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/17 14:24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
