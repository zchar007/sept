package com.sept.io.encrypt;

import java.io.File;

import com.sept.exception.AppException;

public interface I_Encrypt {
	public void doEncrypt() throws AppException;

	void doEncryptFile(File fromFile, File toFile) throws AppException;

	void doEncryptDirectory(File fromFile, File toFile) throws AppException;

	public EncryptListener getEncryptListener() throws AppException;

	public void addEncryptListener(EncryptListener encryptListener) throws AppException;

	public void addConfig(String key, String value) throws AppException;

	public void setPwd(String pwd) throws AppException;

	public String getPwd() throws AppException;
}
