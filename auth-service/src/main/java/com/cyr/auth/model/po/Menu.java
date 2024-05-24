package com.cyr.auth.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author caiyu
 * @version 1.0
 * @description 权限实体类
 * @date 2024/5/21 21:05
 */
@TableName("sys_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {

	private Long id;
	
	private String menuId;
	
	private String menuName;
	
	private String path;
	
	private String component;
	
	private String visible;
	
	private String status;
	
	private String perms;
	
	private Long createBy;
	
	private Date createTime;
	
	private Long updateBy;
	
	private Date updateTime;
	
	private String remark;
}
