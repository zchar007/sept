package com.sept.safety.encrypt.demo;

import com.sept.exception.AppException;
import com.sept.safety.encrypt.Encrypt;
import com.sept.safety.encrypt.EncryptListener;
import com.sept.safety.encrypt.EncryptNames;

public class Demo implements EncryptListener {
	private boolean init = false;

	public static void main(String[] args) throws AppException {
		Encrypt encrypt = new Encrypt("12345678","G:\\EncryptDemo\\B\\A", "G:\\EncryptDemo\\C");
		EncryptNames.setEvery_size(102400);
		encrypt.addEncryptListener(new Demo());
		encrypt.doEncrypt();
		
		//encrypt.doEncrypt(new File("G:\\EncryptDemo\\A\\拉卡.mp4"), new File("G:\\EncryptDemo\\B"));

	}

	@Override
	public void clear() {
		System.out.println("clear");
	}

	@Override
	public boolean hasInit() {
		return init;
	}

	@Override
	public void onStart(int fileCount, long allLength) {
		init = true;
		System.out.println("onStart--allLength["+allLength+"],fileCount["+fileCount+"]");		
	}

	@Override
	public void onLengthChanged(long length) {
		System.out.println("onLengthChanged--length["+length+"]");
		
	}

	@Override
	public void onFinshFile(String fileName) {
		System.out.println("onFinshFile--fileName["+fileName+"]");
		
	}

	@Override
	public void onStartFile(String fromFileName, String toFileName, long fileLength) {
		System.out.println("onStartFile--fromFileName["+fromFileName+"],toFileName["+toFileName+"],fileLength["+fileLength+"]");
		
	}

	@Override
	public void onMessageChanged(String message) {
		System.out.println("onMessageChanged--"+message);
	}
}
