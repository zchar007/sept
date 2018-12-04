package com.sept.db.dba.object;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.db.dba.Sql;

public class OperationAble {
	protected Sql sql = new Sql(true, true, false, false);// 允许查询和表数据操作

	protected void insert(String tableName, DataObject pdo) {

	}

	protected void insert(String tableName, DataStore pds) {

	}

	protected void update(String tableName, DataObject pdo) {

	}

	protected void update(String tableName, DataStore pds) {

	}

	protected void delete(String tableName, DataObject pdo) {

	}

	protected void delete(String tableName, DataStore pds) {

	}
}
