package com.sept.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CloneUtil {
	/**
	 * 深度克隆
	 * 
	 * @author 张超
	 * @date 创建时间 2017年10月11日
	 * @since V1.0
	 */
	public static <T> T deepClone(T object) throws Exception {
		if (!(object instanceof Serializable)) {
			throw new Exception("要克隆的对象必须实现序列化【Serializable】！");
		}
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bout);
		oos.writeObject(object);
		oos.flush();
		ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bin);
		@SuppressWarnings("unchecked")
		T objT = (T) ois.readObject();
		ois.close();
		bin.close();
		oos.close();
		bout.close();
		return objT;
	}

}
