package com.sept.drop.pdf.newpbf.access;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;
import com.sept.datastructure.DataStore;
import com.sept.drop.pdf.newpbf.IDTColumns;
import com.sept.drop.pdf.newpbf.IDTSource;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.io.local.FileIOTool;

public class Access implements IDTSource<AccessTable> {
	public static final Database.FileFormat V1997 = FileFormat.V1997;
	public static final Database.FileFormat V2000 = FileFormat.V2000;
	public static final Database.FileFormat V2003 = FileFormat.V2003;
	public static final Database.FileFormat V2007 = FileFormat.V2007;
	public static final Database.FileFormat V2010 = FileFormat.V2010;

	private boolean readonly;
	private String url;
	private Database dataBase;
	private HashMap<String, AccessTable> hmTables;

	/**
	 * .创建Access表空间对象,存在则抛出错误，不存在则创建
	 * 
	 * @param url
	 * @param fileFormat
	 * @throws AppException
	 */
	public Access(String url) throws AppException {
		this(new File(url), FileFormat.V2010);
	}

	/**
	 * .创建Access表空间对象,存在则抛出错误，不存在则创建
	 * 
	 * @param url
	 * @param fileFormat
	 * @throws AppException
	 */
	public Access(File file) throws AppException {
		this(file, FileFormat.V2010);
	}

	/**
	 * .创建Access表空间对象,存在则抛出错误，不存在则创建
	 * 
	 * @param url
	 * @param fileFormat
	 * @throws AppException
	 */
	public Access(File file, Database.FileFormat fileFormat) throws AppException {
		try {
			this.url = file.getAbsolutePath();
			this.hmTables = new LinkedHashMap<>();

			if (file.exists()) {
				throw new AppException("Access文件[" + file.getAbsolutePath() + "]已存在！");
			}
			dataBase = DatabaseBuilder.create(fileFormat, new File(url));

		} catch (Exception e) {
			if (!(e instanceof AppException)) {
				LogHandler.fatal(e.getMessage(), e);
			} else {
				LogHandler.error(e.getMessage(), e);
				throw (AppException) e;
			}
			throw new AppException(e);
		}
	}

	/**
	 * .内部open使用
	 * 
	 * @param file
	 * @param readonly
	 * @throws AppException
	 */
	private Access(File file, boolean readonly) throws AppException {
		if (!file.isFile()) {
			throw new AppException("url不是有效的文件！");
		}
		if (!file.exists()) {
			throw new AppException("DB文件不存在！");
		}
		try {
			this.setReadonly(readonly);
			this.hmTables = new LinkedHashMap<>();
			this.url = file.getAbsolutePath();
			this.dataBase = DatabaseBuilder.open(file);
		} catch (IOException e) {
			throw new AppException(e);
		}

	}

	/**
	 * 打开一个表空间
	 * 
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static final Access open(String fileUrl) throws AppException {
		return open(fileUrl, true);
	}

	/**
	 * 打开一个表空间
	 * 
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static final Access open(File file) throws AppException {
		return open(file, true);
	}

	/**
	 * 打开一个表空间
	 * 
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static final Access open(String fileUrl, boolean readonly) throws AppException {
		return open(new File(fileUrl), readonly);
	}

	/**
	 * 打开一个表空间
	 * 
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static final Access open(File file, boolean readonly) throws AppException {
		Access access = new Access(file, readonly);
		return access;
	}

	@Override
	public AccessTable newTable(String tableName, IDTColumns dtColumns) throws AppException {
		if (hmTables.containsKey(tableName)) {
			throw new AppException("Access文件【" + url + "】已存在名为【" + tableName + "】的表！");
		}
		try {
			Table table = dataBase.getTable(tableName);
			if (null != table) {
				throw new AppException("Access文件【" + url + "】已存在名为【" + tableName + "】的表！");
			}
		} catch (IOException e1) {
			throw new AppException(e1);
		}

		Table newTable;
		try {
			ArrayList<ColumnBuilder> columnBuilders = this.iDataTableColumnsToAlColumnBuilder(dtColumns);
			TableBuilder tableBuilder = new TableBuilder(tableName);
			for (int i = 0; i < columnBuilders.size(); i++) {
				tableBuilder.addColumn(columnBuilders.get(i));
			}
			newTable = tableBuilder.toTable(dataBase);
			AccessTable at = new AccessTable(newTable);
			at.setColumns(dtColumns);
			this.hmTables.put(tableName, at);
			return at;
		} catch (Exception e) {
			if (!(e instanceof AppException)) {
				LogHandler.fatal(e.getMessage(), e);
			} else {
				LogHandler.error(e.getMessage(), e);
				throw (AppException) e;
			}
			throw new AppException(e);
		}
	}

	@Override
	public AccessTable getTable(String tableName) throws AppException {
		if (this.hmTables.containsKey(tableName)) {
			return this.hmTables.get(tableName);
		}
		try {
			Table table = this.dataBase.getTable(tableName);
			if (null == table) {
				throw new AppException("Access文件[" + this.url + "]不存在表[" + tableName + "]");
			}
			AccessTable at = new AccessTable(table);
			at.init();
			this.hmTables.put(tableName, at);
			return at;
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	@Override
	public AccessTable getTable(String tableName, String keyValue) throws AppException {
		AccessTable at = this.getTable(tableName);
		at.setKeyValue(keyValue);
		return at;
	}

	@Override
	public AccessTable getTable(String tableName, IDTColumns dtColumns) throws AppException {
		AccessTable at = this.getTable(tableName);
		at.setColumns(dtColumns);
		return at;
	}

	@Override
	public DataStore getDataStore(String tableName) throws AppException {
		AccessTable at = this.getTable(tableName);
		return at.select();
	}

	@Override
	public DataStore getDataStore(String tableName, IDTColumns ditColumns) throws AppException {
		AccessTable at = this.getTable(tableName, ditColumns);
		return at.select();
	}

	@Override
	public byte[] getBytes() throws AppException {
		// 触发保存
		this.save();
		File file = dataBase.getFile();
		byte[] accbytes;
		try {
			accbytes = FileIOTool.getBytesFromFile(file);
			return accbytes;
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	@Override
	public boolean save() throws AppException {
		if (readonly) {
			throw new AppException("当前access已设置为只读，无法操作保存！");
		}
		for (AccessTable at : this.hmTables.values()) {
			at.commit();
		}
		return true;
	}

	@Override
	public boolean save(String tableName) throws AppException {
		if (readonly) {
			throw new AppException("当前access已设置为只读，无法操作保存！");
		}
		if (this.hmTables.containsKey(tableName)) {
			this.hmTables.get(tableName).commit();
			return true;
		}
		return false;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	protected ArrayList<ColumnBuilder> iDataTableColumnsToAlColumnBuilder(IDTColumns ditColumns) throws AppException {
		ArrayList<ColumnBuilder> alColumnBuilders = new ArrayList<ColumnBuilder>();

		for (int i = 0; i < ditColumns.columnCount(); i++) {
			String columnName = ditColumns.getColumn(i).getHead();
			int columnType = ditColumns.getColumn(i).getType();

			ColumnBuilder columnBuilder = null;
			try {
				columnBuilder = new ColumnBuilder(columnName).setSQLType(columnType == 0 ? Types.VARCHAR : columnType);
			} catch (SQLException e) {
				LogHandler.fatal(e.getMessage(), e);
				throw new AppException(e);
			}
			alColumnBuilders.add(columnBuilder);
		}
		return alColumnBuilders;
	}

}
