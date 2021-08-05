package com.sso.login.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author sunshaocong
 */
@Data //添加getter和setter
@NoArgsConstructor //无参构造器
@AllArgsConstructor //全参构造器
@Accessors(chain = true) //链式调用
public class User {

	private Integer id;
	private String username;
	private String password;
}
