package com.sept.db.dba.temp;

import java.util.ArrayList;

import com.sept.db.dba.DBType;
import com.sept.exception.AppException;

public class DBTable implements IDBTable {
	private String username;
	private String tableName;
	private String description;
	private String tableSpace;
	private DBType dbtype;
	private ArrayList<DBColumn> alColumns = new ArrayList<>();

	public DBTable(String username, String tableName, String tableSpace, DBType dbtype) {
		super();
		this.username = username;
		this.tableName = tableName;
		this.tableSpace = tableSpace;
		this.dbtype = dbtype;
	}

	@Override
	public void addColumn(DBColumn idbColumn) throws AppException {
		this.alColumns.add(idbColumn);
	}

	@Override
	public void setColumn(int index, DBColumn idbColumn) throws AppException {
		this.alColumns.set(index, idbColumn);
	}

	@Override
	public void setShowName(int index, String name) {
		this.alColumns.get(index).setShowName(name);
	}

	@Override
	public ArrayList<String> getHead() throws AppException {
		ArrayList<String> alArrayList = new ArrayList<>();
		for (int i = 0; i < alColumns.size(); i++) {
			alArrayList.add(alColumns.get(i).getShowName());
		}
		return alArrayList;
	}

	@Override
	public String getSql() throws AppException {
		StringBuilder sql = new StringBuilder();
		if (this.getDbtype() == DBType.ORACLE) {
			sql.setLength(0);
			sql.append("CREATE TABLE  ").append(username).append(".").append(tableName).append(" ");
			sql.append("( ");
			for (int i = 0; i < alColumns.size(); i++) {
				sql.append(alColumns.get(i).getDefineStr()).append(i < alColumns.size() - 1 ? "," : "");
			}
			sql.append(") ");
			sql.append("TABLESPACE  ").append(tableSpace).append(";");
			sql.append("COMMENT ON TABLE ").append(username).append(".").append(tableName).append(" IS ").append("'")
					.append(this.getDescription()).append("';");
			for (int i = 0; i < alColumns.size(); i++) {
				sql.append("COMMENT ON COLUMN ").append(alColumns.get(i).getName()).append(" IS ").append("'")
						.append(alColumns.get(i).getShowName()).append("';");
			}
		}
		return sql.toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableSpace() {
		return tableSpace;
	}

	public void setTableSpace(String tableSpace) {
		this.tableSpace = tableSpace;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DBType getDbtype() {
		return dbtype;
	}

	public void setDbtype(DBType dbtype) {
		this.dbtype = dbtype;
	}
}
