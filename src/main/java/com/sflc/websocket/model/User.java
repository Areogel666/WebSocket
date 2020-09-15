package com.sflc.websocket.model;

import lombok.Data;


@Data
public class User {
	//用户id
	String uid;

	//用户名
	String name;

	//用户账号
	String code;

	//用户源密码
	String pwd_src;

	//用户加密密码
	String password;

	//用户手机号码
	String mobile;

	//用户邮箱
	String email;

	//用户类型
	String person_type;

	//用户区域
	String area;

}
