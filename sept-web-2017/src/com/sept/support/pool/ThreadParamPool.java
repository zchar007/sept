package com.sept.support.pool;

import java.util.HashMap;

import com.sept.support.model.data.DataObject;

/**
 * 类描述，记录同一线程下的所有共享参数
 * 
 * @author 张超
 * @version 1.0 创建时间 2017-6-13
 */
@Deprecated
public class ThreadParamPool {

	private static HashMap<String, DataObject> hmDatas = new HashMap<String, DataObject>();
	private static Object ovsKey = new Object();

	/**
	 * 添加一个操作参数
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-3
	 * @since V1.0
	 */
	public static void addPara(String key, Object value) {
		synchronized (ovsKey) {
			String hmKey = Thread.currentThread().toString();
			if (!hmDatas.containsKey(hmKey)) {
				hmDatas.put(hmKey, new DataObject());
			}
			hmDatas.get(hmKey).put(key, value);
		}
	}

	/**
	 * 添加一个操作参数
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-3
	 * @since V1.0
	 */
	public static void addPara(String hmKey, String key, Object value) {
		synchronized (ovsKey) {
			if (!hmDatas.containsKey(hmKey)) {
				hmDatas.put(hmKey, new DataObject());
			}
			hmDatas.get(hmKey).put(key, value);
		}
	}

	/**
	 * 移除一个组
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-3
	 * @since V1.0
	 */
	public static DataObject removeData() {
		synchronized (ovsKey) {
			String hmKey = Thread.currentThread().toString().toString();
			if (hmDatas.containsKey(hmKey)) {
				return hmDatas.remove(Thread.currentThread().toString());
			}
			return null;
		}
	}

	/**
	 * 移除一个组
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-3
	 * @since V1.0
	 */
	public static DataObject removeData(String hmKey) {
		synchronized (ovsKey) {
			if (hmDatas.containsKey(hmKey)) {
				return hmDatas.remove(Thread.currentThread().toString());
			}
			return null;
		}
	}

	/**
	 * 获取数据
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-3
	 * @since V1.0
	 */
	public static DataObject getData() {
		synchronized (ovsKey) {
			if (hmDatas.containsKey(Thread.currentThread().toString())) {
				return hmDatas.get(Thread.currentThread().toString());
			}
			return null;
		}
	}

	/**
	 * 获取数据
	 * 
	 * @author 张超
	 * @date 创建时间 2017-6-3
	 * @since V1.0
	 */
	public static DataObject getData(String hmKey) {
		synchronized (ovsKey) {
			if (hmDatas.containsKey(hmKey)) {
				return hmDatas.get(Thread.currentThread().toString());
			}
			return null;
		}
	}

	public static HashMap<String, DataObject> getAll() {
		synchronized (ovsKey) {
			return hmDatas;
		}

	}
	// /**
	// * 获取数据
	// *
	// * @author 张超
	// * @date 创建时间 2017-6-3
	// * @since V1.0
	// */
	// public static DataObject getPara(String hmKey) {
	// synchronized (ovsKey) {
	// if (hmDatas.containsKey(Thread.currentThread().toString())) {
	// if(hmDatas.get(Thread.currentThread().toString()).get)
	// return ;
	// }
	// return null;
	// }
	// }

}
