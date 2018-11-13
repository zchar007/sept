package com.sept.datastructure;

import com.sept.datastructure.exception.DataException;
import com.sept.exception.ApplicationException;

public class Demo {
	public static void main(String[] args) throws DataException, ApplicationException {
		DataStore ds = new DataStore();

		for (int i = 0; i < 5; i++) {
			ds.addRow();
			ds.put(ds.rowCount() - 1, "name", "ÕÅÈý" + i);
			ds.put(ds.rowCount() - 1, "age", i);
		}
		System.out.println(ds);

		System.out.println(ds.toJSON());
		System.out.println(ds.toXML());
		System.out.println(ds.get(0).toJSON());
		System.out.println(ds.get(0).toXML());

	}
}
