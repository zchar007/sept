package com.sept.safety.userpwd;

public interface IUserSafety {
	public static final int NOUSER = -1;// 无此用户
	public static final int SUCCESS = 0;// 成功
	public static final int WRONGPWD = 1;// 密码错误
	public static final int ALREADYINSGN = 2;// 已处于登录状态
	public static final int TIME_OUT = 3;// 超时
	public static final int UNKNOW = 4;// 未知错误时可以抛出

	/** 有可能返回所有状态 **/
	int check4Sign(String username, String pwd);

	/** 不会返回已处于登录状态 **/
	int check(String username, String pwd);
}
