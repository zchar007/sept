package com.sept.util.pool;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sept.exception.AppException;

/**
 * ��Ҫʵ������һ��MessagePool���൱��һ���ɲ����ĵ����ݴ�ŵ� <br>
 * �����ڶ��߳��������̵߳����ݽ���
 * 
 * @author �ų�
 * @version 1.0 ����ʱ�� 2017-6-7
 */
public class MessagePool {
	private HashMap<String, Object> messPool = new HashMap<String, Object>();
	private ExecutorService downloadThreadPool = Executors.newFixedThreadPool(1000);

	/**
	 * ���һ����Ϣ��Ϣ
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void putMessage(String key, Object value) {
		synchronized (messPool) {
			this.messPool.put(key, value);
		}
	}

	/**
	 * ��ֹ��ȡ������ӻ���ɵĲ�������<br>
	 * ��ȡ��֮���ͷ������п��ܱ������̻߳�ȡ�����������ݽ����˲�����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void putAndAddMessage(String key, Object value) {
		synchronized (messPool) {
			if (value instanceof Integer) {
				if (!this.messPool.containsKey(key)) {
					this.messPool.put(key, 0);
				}
				this.messPool.put(key, (int) this.messPool.get(key) + (int) value);
			}
			if (value instanceof Double) {
				if (!this.messPool.containsKey(key)) {
					this.messPool.put(key, 0.0d);
				}
				this.messPool.put(key, (double) this.messPool.get(key) + (double) value);
			}
			if (value instanceof Float) {
				if (!this.messPool.containsKey(key)) {
					this.messPool.put(key, 0.0f);
				}
				this.messPool.put(key, (float) this.messPool.get(key) + (float) value);
			}
			if (value instanceof Long) {
				if (!this.messPool.containsKey(key)) {
					this.messPool.put(key, 0l);
				}
				this.messPool.put(key, (long) this.messPool.get(key) + (long) value);
			}
			if (value instanceof String) {
				if (!this.messPool.containsKey(key)) {
					this.messPool.put(key, "");
				}
				this.messPool.put(key, (String) this.messPool.get(key) + (String) value);
			}

		}
	}

	/**
	 * ��ȡ����
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public Object getMessage(String key) {
		synchronized (messPool) {
			if (this.messPool.containsKey(key)) {
				return this.messPool.get(key);
			}
			return null;
		}
	}

	/**
	 * �ṩ��̬���첽���������ڶ�ԭ�����ݽ��мӲ���<br>
	 * ��ֹ��ȴ������������̻߳���
	 * 
	 * @author �ų�
	 * @throws AppException
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void asynchPutAndAddMessage(String key, Object value) throws AppException {
		if (!downloadThreadPool.isShutdown()) {
			downloadThreadPool.execute(new PutAndAddMessageThread(key, value, this));
		} else {
			throw new AppException(this.getClass().getName() + ":�ѹرյ��̳߳أ�");
		}
	}

	/**
	 * �ṩ��̬���첽���������ڶ����ݽ�����Ӳ���<br>
	 * ��ֹ��ȴ������������̻߳���
	 * 
	 * @author �ų�
	 * @throws AppException
	 * @date ����ʱ�� 2017-6-7
	 * @since V1.0
	 */
	public void asynchPutMessage(String key, Object value) throws AppException {
		if (!downloadThreadPool.isShutdown()) {
			downloadThreadPool.execute(new PutMessageThread(key, value, this));
		} else {
			throw new AppException(this.getClass().getName() + ":�ѹرյ��̳߳أ�");
		}
	}

	public void killMine() {
		if (!downloadThreadPool.isShutdown()) {
			downloadThreadPool.shutdownNow();
		}
	}

	public static void main(String[] args) {
		final MessagePool mep = new MessagePool();
		mep.putMessage("message", 0);
		for (int i = 0; i < 10; i++) {
			new Thread() {
				public void run() {
					while (true) {
						mep.putAndAddMessage("message", 1);
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
		new Thread() {
			public void run() {
				while (true) {
					int mess = (int) mep.getMessage("message");
					System.out.println(mess);
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
