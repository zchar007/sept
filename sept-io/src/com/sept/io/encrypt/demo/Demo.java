package com.sept.io.encrypt.demo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;

import com.sept.exception.ApplicationException;
import com.sept.io.encrypt.EncryptUtil;

public class Demo {

	public static void main(String[] args) throws UnsupportedEncodingException, ApplicationException {
		 final MessagePool mess = new MessagePool();
//		final ExecutorService es = EncryptUtil.encryptFiles(new File("F:\\仓库_待整理\\[白鹿原]第58集_bd.mp4"),
//				"F:\\仓库_待整理\\[白鹿原]第58集_bd", "123", mess, null);
			final ExecutorService es = EncryptUtil.encryptFiles(new File("F:\\\\仓库_待整理\\\\[白鹿原]第58集_bd\\\\[白鹿原]第58集_bd.mp4"),
					"F:\\仓库_待整理\\[白鹿原]第58集_bd\\[白鹿原]第58集_bd", "123", mess, null);
		
		new Thread() {
			public void run() {
				while (true) {
					Object objNow = mess.getMessage(EncryptUtil.ENCRYPT_NOW_SIZE);
					Object objAll = mess.getMessage(EncryptUtil.ENCRYPT_ALL_SIZE);
					if (null == objAll || null == objNow) {
						continue;
					}
					long nowSzie = (long) objNow;
					long allsize = (long) objAll;
					System.out.println(nowSzie + "/" + allsize);
					if (nowSzie == allsize) {
						mess.killMine();
						es.shutdownNow();
						break;
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};
		}.start();
	}
}
