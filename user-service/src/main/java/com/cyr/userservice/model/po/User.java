package com.cyr.userservice.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author caiyu
 * @version 1.0
 * @description TODO
 * @date 2024/5/17 14:14
 */
@TableName("sys_user")
@Data
public class User implements Serializable {

	private Long userId;

	private String username;

	private String password;
	
	private Character sex;
	
	private Character status;

	private String createBy;

	private Date createTime;

	private String updateBy;

	private Date updateTime;

	private String remark;
}
