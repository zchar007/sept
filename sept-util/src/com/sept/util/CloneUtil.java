package com.sept.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class CloneUtil {
	/**
	 * ��ȿ�¡
	 * 
	 * @author �ų�
	 * @date ����ʱ�� 2017��10��11��
	 * @since V1.0
	 */
	public static <T> T deepClone(T object) throws Exception {
		if (!(object instanceof Serializable)) {
			throw new Exception("Ҫ��¡�Ķ������ʵ�����л���Serializable����");
		}
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bout);
		oos.writeObject(object);

		ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bin);
		@SuppressWarnings("unchecked")
		T objT = (T) ois.readObject();
		return objT;
	}

}
