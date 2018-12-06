package com.sept.db;

public enum DBType {
	UNKNOW(-1), ORACLE(0), MYSQL(1), SQLSERVER(2), POSTGRESQL(3), ACCESS_FILE(3), XML_FILE(3), JSON_FILE(3);
	private int type;

	private DBType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
