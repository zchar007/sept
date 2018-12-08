package com.sept.support.interfaces.def;

import java.util.Date;

import com.sept.exception.AppException;
import com.sept.support.interfaces.user.UserInterface;
import com.sept.support.model.data.DataObject;

public class CurrentUser implements UserInterface {

	private String userName;

	@Override
	public boolean initUser(DataObject para) {
		try {
			userName = para.getString("username");
		} catch (AppException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String getUserId() {
		return null;
	}

	@Override
	public boolean checkUserPassword(String password) {
		return false;
	}

	@Override
	public boolean checkSignIP(String nowIp) {
		return false;
	}

	@Override
	public Date getSignTime() {
		return null;
	}

	@Override
	public String toString() {
		return "CurrentUser [userName=" + userName + "]";
	}
}
