package com.sept.drop.temp2;

public enum CT {
	ORACLE_CT_CHAR("CHAR"), ORACLE_CT_VARCHAR2("VARCHAR2"), ORACLE_CT_NCHAR("NCHAR"), ORACLE_CT_NVARCHAR2("NVARCHAR2"),
	ORACLE_CT_DATE("DATE"), ORACLE_CT_LONG("LONG"), ORACLE_CT_RAW("RAW"), ORACLE_CT_LONG_RAW("LONG RAW"),
	ORACLE_CT_BLOB("CLOB"), ORACLE_CT_NCLOB("NCLOB"), ORACLE_CT_BFILE("BFILE"), ORACLE_CT_ROWID("ROWID"),
	ORACLE_CT_NROWID("NROWID"), ORACLE_CT_NUMBER("NUMBER"), ORACLE_CT_DECIMAL("DECIMAL"), ORACLE_CT_INTEGER("INTEGER"),
	ORACLE_CT_FLOAT("FLOAT"), ORACLE_CT_REAL("REAL");
	private String name;

	private CT(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}