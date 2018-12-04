package com.sept.safety.up;

public interface IUserSafety {
	public static final int NOUSER = -1;// 无此用户
	public static final int SUCCESS = 0;// 成功
	public static final int WRONGPWD = 1;// 密码错误
	public static final int ALREADYINSGN = 2;// 已处于登录状态
	public static final int UNKNOW = 3;// 未知错误时可以抛出

	int check4Sign(String un, String pwd);

	int check(String un, String pwd);
}
