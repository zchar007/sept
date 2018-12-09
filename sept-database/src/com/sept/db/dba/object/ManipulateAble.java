package com.sept.db.dba.object;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.db.dba.Sql;

public class ManipulateAble extends QueryAble {
	public ManipulateAble() {
		this.sql = new Sql(true, true, false);// 允许查询和表数据操作
	}

	/**
	 * 插入一行
	 * 
	 * @param tableName
	 * @param pdo
	 */
	protected int insert(String tableName, DataObject pdo) {
		
		return 0;
	}

	protected int insert(String tableName, DataStore pds) {
		return 0;

	}

	protected int update(String tableName, DataObject pdo) {
		return 0;

	}

	protected int update(String tableName, DataStore pds) {
		return 0;

	}

	protected int delete(String tableName, DataObject pdo) {
		return 0;

	}

	protected int delete(String tableName, DataStore pds) {
		return 0;

	}
}
