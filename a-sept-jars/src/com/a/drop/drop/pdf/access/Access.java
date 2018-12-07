package com.sept.drop.pdf.access;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;
import com.sept.drop.pdf.AbstractDataSpace;
import com.sept.drop.pdf.IDataTable;
import com.sept.drop.pdf.IDataTableColumns;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.io.local.FileIOTool;

public class Access extends AbstractDataSpace {
	private Database accdb;

	public static final Database.FileFormat V1997 = FileFormat.V1997;
	public static final Database.FileFormat V2000 = FileFormat.V2000;
	public static final Database.FileFormat V2003 = FileFormat.V2003;
	public static final Database.FileFormat V2007 = FileFormat.V2007;
	public static final Database.FileFormat V2010 = FileFormat.V2010;

	private Access() {
		this.hmTables = new LinkedHashMap<>();
	}

	public Access(String url) throws AppException {
		this(new File(url), FileFormat.V2010);
	}

	/**
	 * 创建Access表空间对象,存在则读取，不存在则创建
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
			accdb = DatabaseBuilder.create(fileFormat, new File(url));

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
	 * 打开一个表空间
	 * 
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static final Access open(String fileUrl) throws AppException {
		return open(new File(fileUrl));
	}

	/**
	 * 打开一个表空间
	 * 
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static final Access open(File file) throws AppException {
		try {
			if (!file.isFile()) {
				throw new AppException("url不是有效的文件！");
			}
			if (!file.exists()) {
				throw new AppException("DB文件不存在！");
			}
			Access access = new Access();
			Database accdb = DatabaseBuilder.open(file);
			access.setAccdb(accdb);
			access.setUrl(file.getAbsolutePath());
			return access;
		} catch (Exception e) {
			if (!(e instanceof AppException)) {
				e.printStackTrace();
				LogHandler.fatal(e.getMessage(), e);
			} else {
				LogHandler.error(e.getMessage(), e);
				throw (AppException) e;
			}
			throw new AppException(e);
		}
	}

	/**
	 * 新建表
	 * 
	 * @param tableName
	 * @param ditColumns
	 * @return
	 * @throws AppException
	 */
	public IDataTable newTable(String tableName, IDataTableColumns ditColumns) throws AppException {
		if (hmTables.containsKey(tableName)) {
			throw new AppException("Access文件【" + url + "】已存在名为【" + tableName + "】的表！");
		}
		try {
			Table table = accdb.getTable(tableName);
			if (null != table) {
				throw new AppException("Access文件【" + url + "】已存在名为【" + tableName + "】的表！");
			}
		} catch (IOException e1) {
			throw new AppException(e1);
		}

		Table newTable;
		try {
			ArrayList<ColumnBuilder> columnBuilders = this.iDataTableColumnsToAlColumnBuilder(ditColumns);
			TableBuilder tableBuilder = new TableBuilder(tableName);
			for (int i = 0; i < columnBuilders.size(); i++) {
				tableBuilder.addColumn(columnBuilders.get(i));
			}
			newTable = tableBuilder.toTable(accdb);
			AccessTable at = new AccessTable(newTable, this.accdb);
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

	protected ArrayList<ColumnBuilder> iDataTableColumnsToAlColumnBuilder(IDataTableColumns ditColumns)
			throws AppException {
		ArrayList<ColumnBuilder> alColumnBuilders = new ArrayList<ColumnBuilder>();

		for (int i = 0; i < ditColumns.columnCount(); i++) {
			String columnName = ditColumns.getColumn(i).getName();
			int columnType = ditColumns.getColumn(i).getDataType();

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

	@Override
	public AccessTable getTable(String tableName) throws AppException {
//		HashMap<String, IDataTable> hmTables = new HashMap<>();
//		Set<String> set = accdb.getTableNames();
//		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
//			String name = (String) iterator.next();
//			AccessTable at = new AccessTable(accdb.getTable(name), accdb);
//			hmTables.put(name, at);
//		}
		try {
			Table table = accdb.getTable(tableName);
			if (null == table) {
				throw new AppException("Access文件[" + this.getUrl() + "]不存在表[" + tableName + "]");
			}
			AccessTable at = new AccessTable(table, accdb);
			this.hmTables.put(tableName, at);
		} catch (IOException e) {
			throw new AppException(e);
		}
		return (AccessTable) super.getTable(tableName);
	}

	public Database getAccdb() {
		return accdb;
	}

	private void setAccdb(Database accdb) {
		this.accdb = accdb;
	}

	@Override
	public IDataTable newPage(String tableName, IDataTableColumns ditColumns) throws AppException {
		return this.newTable(tableName, ditColumns);
	}

	@Override
	public void save() throws AppException {
		for (IDataTable at : this.hmTables.values()) {
			at.ready();
		}
	}

	@Override
	public byte[] getBytes() throws AppException {
		File file = accdb.getFile();
		byte[] accbytes;
		try {
			accbytes = FileIOTool.getBytesFromFile(file);
			return accbytes;
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

}
