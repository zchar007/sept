package com.sept.datastructure.common;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sept.exception.AppException;

public class MessagePool implements SharedInformationPool {
	private HashMap<String, Object> messPool = new HashMap<String, Object>();
	private ExecutorService downloadThreadPool = Executors.newFixedThreadPool(1000);

	@Override
	public void put(String key, Object value) throws Exception {
		synchronized (messPool) {
			this.messPool.put(key, value);
		}
	}

	@Override
	public void put4Add(String key, Object value) throws Exception {
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

	@Override
	public void asynchPut(String key, Object value) throws Exception {
		if (!downloadThreadPool.isShutdown()) {
			downloadThreadPool.execute(new Thread() {
				@Override
				public void run() {
					try {
						MessagePool.this.put(key, value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			throw new AppException(this.getClass().getName() + ":已关闭的线程池！");
		}
	}

	@Override
	public void asynchPut4Add(String key, Object value) throws Exception {
		if (!downloadThreadPool.isShutdown()) {
			downloadThreadPool.execute(new Thread() {
				@Override
				public void run() {
					try {
						MessagePool.this.put4Add(key, value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			throw new AppException(this.getClass().getName() + ":已关闭的线程池！");
		}
	}

	@Override
	public Object get(String key) throws Exception {
		synchronized (messPool) {
			if (this.messPool.containsKey(key)) {
				return this.messPool.get(key);
			}
			return null;
		}
	}

	@Override
	public void killMine() throws Exception {
		if (!downloadThreadPool.isShutdown()) {
			downloadThreadPool.shutdownNow();
		}
	}

}
