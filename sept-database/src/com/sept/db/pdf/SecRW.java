package com.sept.db.pdf;

import java.sql.Driver;

import com.sept.safety.userpwd.IUserSafety;

public abstract class SecRW implements IUserSafety, Driver {

	@Override
	public int check(String username, String password) {

		return 0;
	}

	@Override
	public int check4Sign(String username, String password) {

		return 0;
	}
}
