package com.sept.io.encrypt;

public class EncryptNames {
	protected static boolean MULTITHREADING = false;
	protected static int EVERY_SIZE = 1024;

	public static final void setMultithreading(boolean multithreading) {
		MULTITHREADING = multithreading;
	}

	public static final boolean isMultithreading() {
		return MULTITHREADING;
	}

	public static final int getEvery_size() {
		return EVERY_SIZE;
	}

	public static final void setEvery_size(int every_size) {
		EVERY_SIZE = every_size;
	}

}
