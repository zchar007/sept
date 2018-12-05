package com.sept.project.context.demo;

import com.sept.safety.up.IUserSafety;

public class PBFUserSafetyDriver implements IUserSafety {

	@Override
	public int check4Sign(String un, String pwd) {
		
		return 0;
	}

	@Override
	public int check(String un, String pwd) {
		return 0;
	}

}
