package com.sept.db.pbf.temp.access;

import java.io.File;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.sept.db.pbf.temp.IDataTable;
import com.sept.db.pbf.temp.IDataTableColumn;
import com.sept.db.pbf.temp.IDataTableColumns;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;

public class Access {
	private Database accdb;
	private HashMap<String, AccessTable> hmTables;
	private String url;

	public static final Database.FileFormat V1997 = FileFormat.V1997;
	public static final Database.FileFormat V2000 = FileFormat.V2000;
	public static final Database.FileFormat V2003 = FileFormat.V2003;
	public static final Database.FileFormat V2007 = FileFormat.V2007;
	public static final Database.FileFormat V2010 = FileFormat.V2010;

	public Access() {

	}

	public Access(String url, boolean isNew) throws AppException {
		this(new File(url), FileFormat.V2010, isNew);
	}

	/**
	 * 创建Access表空间对象,存在则读取，不存在则创建
	 * 
	 * @param url
	 * @param fileFormat
	 * @throws AppException
	 */
	public Access(File file, Database.FileFormat fileFormat, boolean isNew) throws AppException {
		try {
			this.url = file.getAbsolutePath();

			if (!file.exists()) {
				if (!isNew) {
					throw new AppException("data文件[" + this.url + "]不存在，且模式为不新建，Access对象创建失败，请检查！");
				}
				accdb = DatabaseBuilder.create(fileFormat, new File(url));
			} else {
				if (!file.isFile()) {
					throw new AppException("url不是有效的文件！");
				}
				this.open(file);
			}
			hmTables = new HashMap<String, AccessTable>();
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
	private void open(File file) throws AppException {
		try {
			if (!file.isFile()) {
				throw new AppException("url不是有效的文件！");
			}
			if (!file.exists()) {
				throw new AppException("DB文件不存在！");
			}
			accdb = DatabaseBuilder.open(new File(url));
			this.hmTables = new HashMap<>();
			Set<String> set = accdb.getTableNames();

			for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
				String name = (String) iterator.next();
				AccessTable at = new AccessTable(this.accdb.getTable(name), this.accdb);
				this.hmTables.put(name, at);
			}
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

		Table newTable;
		try {
			ArrayList<ColumnBuilder> columnBuilders = this.iDataTableColumnsToAlColumnBuilder(ditColumns);
			TableBuilder tableBuilder = new TableBuilder(tableName);
			for (int i = 0; i < columnBuilders.size(); i++) {
				tableBuilder.addColumn(columnBuilders.get(i));
			}
			newTable = tableBuilder.toTable(accdb);
			AccessTable at = new AccessTable(newTable, this.accdb);
			hmTables.put(tableName, at);
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

	private ArrayList<ColumnBuilder> iDataTableColumnsToAlColumnBuilder(IDataTableColumns ditColumns)
			throws AppException {
		ArrayList<ColumnBuilder> alColumnBuilders = new ArrayList<ColumnBuilder>();

		for (int i = 0; i < ditColumns.columnCount(); i++) {
			String columnName = ditColumns.getColumn(i).getName();
			String columnType = ditColumns.getColumn(i).getDataType();
			int type = Types.VARCHAR;
			try {
				type = Integer.parseInt(columnType);
			} catch (Exception e) {
				throw new AppException("参数类型应该是数字！");
			}
			ColumnBuilder columnBuilder = null;
			try {
				columnBuilder = new ColumnBuilder(columnName).setSQLType(type);
			} catch (SQLException e) {
				LogHandler.fatal(e.getMessage(), e);
				throw new AppException(e);
			}

			alColumnBuilders.add(columnBuilder);
		}
		return alColumnBuilders;
	}
}
