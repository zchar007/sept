package com.sept.db.pdf;

import com.sept.safety.userpwd.IUserSafety;

public abstract class SecRW implements IUserSafety {
	
	
	
	@Override
	public int check(String arg0, String arg1) {
		return 0;
	}

	@Override
	public int check4Sign(String arg0, String arg1) {
		return 0;
	}
}
