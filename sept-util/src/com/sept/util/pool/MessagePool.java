package com.sept.util.pool;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sept.exception.AppException;

/**
 * 需要实例化，一个MessagePool，相当于一个可并发的的数据存放地 <br>
 * 可用于多线程中两个线程的数据交互
 * 
 * @author 张超
 * @version 1.0 创建时间 2017-6-7
 */
public class MessagePool {
	private HashMap<String, Object> messPool = new HashMap<String, Object>();
	private ExecutorService downloadThreadPool = Executors.newFixedThreadPool(1000);

	/**
	 * 添加一个消息信息
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void putMessage(String key, Object value) {
		synchronized (messPool) {
			this.messPool.put(key, value);
		}
	}

	/**
	 * 防止先取出后添加回造成的并发问题<br>
	 * （取过之后释放了锁有可能被其他线程获取到锁而对数据进行了操作）
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
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
	 * 获取数据
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-7
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
	 * 提供静态的异步方法，用于对原有数据进行加操作<br>
	 * 防止因等待锁而导致主线程缓慢
	 * 
	 * @author 张超
	 * @throws AppException
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void asynchPutAndAddMessage(String key, Object value) throws AppException {
		if (!downloadThreadPool.isShutdown()) {
			downloadThreadPool.execute(new PutAndAddMessageThread(key, value, this));
		} else {
			throw new AppException(this.getClass().getName() + ":已关闭的线程池！");
		}
	}

	/**
	 * 提供静态的异步方法，用于对数据进行添加操作<br>
	 * 防止因等待锁而导致主线程缓慢
	 * 
	 * @author 张超
	 * @throws AppException
	 * @date 创建时间 2017-6-7
	 * @since V1.0
	 */
	public void asynchPutMessage(String key, Object value) throws AppException {
		if (!downloadThreadPool.isShutdown()) {
			downloadThreadPool.execute(new PutMessageThread(key, value, this));
		} else {
			throw new AppException(this.getClass().getName() + ":已关闭的线程池！");
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
