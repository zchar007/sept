package com.sept.io.encrypt;

import com.sept.exception.ApplicationException;

public interface MessageStore {

	/**
	 * ���һ����Ϣ��Ϣ
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void putMessage(String key, Object value) throws Exception;

	/**
	 * ��ֹ��ȡ������ӻ���ɵĲ�������<br>
	 * ��ȡ��֮���ͷ������п��ܱ������̻߳�ȡ�����������ݽ����˲�����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void putAndAddMessage(String key, Object value) throws Exception;

	/**
	 * ��ȡ����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public Object getMessage(String key) throws Exception;

	/**
	 * �ṩ��̬���첽���������ڶ�ԭ�����ݽ��мӲ���<br>
	 * ��ֹ��ȴ������������̻߳���
	 * 
	 * @author �ų�
	 * @throws ApplicationException
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void asynchPutAndAddMessage(String key, Object value) throws Exception;

	/**
	 * �ṩ��̬���첽���������ڶ����ݽ�����Ӳ���<br>
	 * ��ֹ��ȴ������������̻߳���
	 * 
	 * @author �ų�
	 * @throws ApplicationException
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void asynchPutMessage(String key, Object value) throws Exception;

	public void killMine();

}
