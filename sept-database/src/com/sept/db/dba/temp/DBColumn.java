package com.sept.db.dba.temp;

public class DBColumn {

	private String name;
	private CT type;
	private int length = -1;
	private int length_start = -1;
	private int length_end = -1;
	private boolean pk;
	private boolean fk;
	private boolean notnull;
	private String fk_for_user;
	private String fk_for_table;
	private String fk_for_column;
	private String showName;

	public DBColumn(String name, CT type) {
		super();
		this.name = name;
		this.type = type;
	}

	public DBColumn(String name, CT type, int length) {
		super();
		this.name = name;
		this.type = type;
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CT getType() {
		return type;
	}

	public void setType(CT type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getLength_start() {
		return length_start;
	}

	public void setLength_start(int length_start) {
		this.length_start = length_start;
	}

	public int getLength_end() {
		return length_end;
	}

	public void setLength_end(int length_end) {
		this.length_end = length_end;
	}

	public boolean isPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public boolean isFk() {
		return fk;
	}

	public void setFk(boolean fk) {
		this.fk = fk;
	}

	public String getFk_for_user() {
		return fk_for_user;
	}

	public void setFk_for_user(String fk_for_user) {
		this.fk_for_user = fk_for_user;
	}

	public String getFk_for_table() {
		return fk_for_table;
	}

	public void setFk_for_table(String fk_for_table) {
		this.fk_for_table = fk_for_table;
	}

	public String getFk_for_column() {
		return fk_for_column;
	}

	public void setFk_for_column(String fk_for_column) {
		this.fk_for_column = fk_for_column;
	}

	public void setFK(String fk_for_user, String fk_for_table, String fk_for_column) {
		this.setFk(true);
		this.setFk_for_user(fk_for_user);
		this.setFk_for_table(fk_for_table);
		this.setFk_for_column(fk_for_column);
	}

	public String getDefineStr() {
		String lengthStr = "";
		// TODO 这里先整上一般能用到的，其他暂时不弄
		if (this.type == CT.ORACLE_CT_INTEGER) {
			length_start = length_start <= 0 ? (length < 0 ? 0 : length) : length_start;
			length_end = length_end > length_start ? (length_start - 1) : length_end;
			length_end = length_end < 0 ? 0 : length_end;
			lengthStr = "(" + length_start + "," + length_end + ")";
		} else if (this.type == CT.ORACLE_CT_VARCHAR2) {
			length = length < 0 ? 0 : length;
			lengthStr = "(" + length + ")";
		}
		return this.name + " " + this.type + lengthStr + " "
				+ (this.isPk() ? "primary key"
						: (this.isFk() ? ("references " + fk_for_user + "." + fk_for_table + "(" + fk_for_column + ")")
								: (this.isNotnull() ? "NOT NULL" : "")));
	}

	public String getFKStr() {
		return null;
	}

	public boolean isNotnull() {
		return notnull;
	}

	public void setNotnull(boolean notnull) {
		this.notnull = notnull;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}
}
