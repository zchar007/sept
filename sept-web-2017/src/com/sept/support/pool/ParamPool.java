package com.sept.support.pool;

import com.sept.support.model.data.DataObject;

@Deprecated
public final class ParamPool {
	private static ThreadLocal<DataObject> paramStore = new ThreadLocal<DataObject>();

	public static void addPara(String key, Object value) {
		DataObject pdo = paramStore.get();
		if (null == pdo) {
			pdo = new DataObject();
		}
		pdo.put(key, value);
		paramStore.set(pdo);
	}

	public static Object getPara(String key) {
		DataObject pdo = paramStore.get();
		if (null == pdo) {
			pdo = new DataObject();
			paramStore.set(pdo);
		}
		return paramStore.get().get(key, null);
	}

	public static DataObject getDataObject() {
		return paramStore.get();

	}

	public static void clear() {
		paramStore.remove();
	}

}
