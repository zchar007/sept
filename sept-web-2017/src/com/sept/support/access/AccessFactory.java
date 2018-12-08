package com.sept.support.access;

import com.healthmarketscience.jackcess.Database;
import com.sept.exception.AppException;
import com.sept.support.util.RandomFactory;

public class AccessFactory {

	public static Access newAccess(String url, Database.FileFormat fileFormat)
			throws AppException {
		return new Access(url, fileFormat);
	}

	public static Access openAccess(String url) throws AppException {
		return new Access().openAccess(url);
	}

	public static void main(String[] args) {
		RandomFactory.newInstance();
	}

}
