package com.sept.safety.encrypt;

public interface EncryptListener {
	/**
	 * 清除所有，回到未初始化状态
	 */
	void clear();// 清除历史数据

	/**
	 * 初始化
	 * 
	 * @param allLength
	 * @param fileCount
	 */
	void onStart(int fileCount, long allLength);

	/**
	 * 增加完成的大小（在不clear之前应该是累加的）
	 * 
	 * @param length
	 */
	void onLengthChanged(long length);

	/**
	 * 设置当前完成文件的数量（在不clear之前应该是累加的）
	 * 
	 * @param fileCount
	 */
	void onFinshFile(String fileName);

	/**
	 * 设置当前操作文件的名字及大小
	 * 
	 * @param fromFileName
	 * @param toFileName
	 * @param fileLength
	 */
	void onStartFile(String fromFileName, String toFileName, long fileLength);

	/**
	 * 信息传递
	 * 
	 * @param fromFileName
	 * @param toFileName
	 * @param fileLength
	 */
	void onMessageChanged(String message);

	/**
	 * 获取初始化状态,已初始化返回true,否则返回true
	 * 
	 * @return
	 */
	boolean hasInit();
}
