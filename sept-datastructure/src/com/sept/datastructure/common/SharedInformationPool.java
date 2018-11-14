package com.sept.datastructure.common;

public interface SharedInformationPool {

	/**
	 * ���һ����Ϣ��Ϣ
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void put(String key, Object value) throws Exception;

	/**
	 * ���һ����Ϣ��Ϣ��ԭ����Ϣ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void put4Add(String key, Object value) throws Exception;

	/**
	 * �첽���
	 * 
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void asynchPut(String key, Object value) throws Exception;

	/**
	 * �첽���һ����Ϣ��Ϣ��ԭ����Ϣ��
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void asynchPut4Add(String key, Object value) throws Exception;

	/**
	 * ��ȡ����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public Object get(String key) throws Exception;

	/**
	 * �ݻٳ���
	 */
	public void killMine() throws Exception;

}
