package com.sept.db.dbbf;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;
import com.sept.datastructure.DataStore;
import com.sept.debug.log4j.LogHandler;
import com.sept.exception.AppException;
import com.sept.io.local.FileIOTool;

/**
 * 直接读取和创建db文件的类
 * 
 * @author zchar
 *
 */
public class DBBFUtil {

	/**
	 * 创建新的DB file
	 * 
	 * @param file
	 * @return
	 * @throws AppException
	 */
	public static final Database newDB(File file) throws AppException {
		if (!isPBFFile(file)) {
			throw new AppException("传入文件[" + file.getAbsolutePath() + "]不是Excel文件！");
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new AppException(e);
			}
		}
		return getDB(file);
	}

	/**
	 * 创建新表
	 * 
	 * @param db
	 * @param name
	 * @param columns
	 * @return
	 * @throws AppException
	 */
	public static final Table newTable(Database db, String name, String columnBuilders) throws AppException {
		Table tb;
		try {
			tb = db.getTable(name);
		} catch (IOException e) {
			throw new AppException(e);
		}
		if (null != tb) {
			throw new AppException("Access文件【" + db.getFile().getAbsolutePath() + "】已存在名为【" + name + "】的表！");
		}

		return newTable(db, name, getColumnBuilders(columnBuilders));
	}

	/**
	 * 创建表
	 * 
	 * @param db
	 * @param name
	 * @param columnBuilders
	 * @return
	 * @throws AppException
	 */
	public static final Table newTable(Database db, String name, ArrayList<ColumnBuilder> columnBuilders)
			throws AppException {
		Table tb;
		try {
			tb = db.getTable(name);
		} catch (IOException e) {
			throw new AppException(e);
		}
		if (null != tb) {
			throw new AppException("Access文件【" + db.getFile().getAbsolutePath() + "】已存在名为【" + name + "】的表！");
		}

		Table newTable;
		try {
			TableBuilder tableBuilder = new TableBuilder(name);
			for (int i = 0; i < columnBuilders.size(); i++) {
				tableBuilder.addColumn(columnBuilders.get(i));
			}
			newTable = tableBuilder.toTable(db);
			return newTable;
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
	 * 根据字符串获取ColumnBuilders
	 * 
	 * @param columnBuilders
	 * @return
	 * @throws AppException
	 */
	public static final ArrayList<ColumnBuilder> getColumnBuilders(String columnBuilders) throws AppException {
		ArrayList<ColumnBuilder> alColumnBuilders = new ArrayList<ColumnBuilder>();

		String[] columnBuilderss = columnBuilders.split(",");
		for (int i = 0; i < columnBuilderss.length; i++) {
			String[] temps = columnBuilderss[i].split(":");
			String columnName = temps[0];
			String columnType = temps[1];
			int type = Types.VARCHAR;
			try {
				type = Integer.parseInt(columnType);
			} catch (Exception e) {
				type = Types.VARCHAR;
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

	/**
	 * 获取Database
	 * 
	 * @param file
	 * @return
	 * @throws AppException
	 */
	public static final Database getDB(File file) throws AppException {
		if (!isPBFFile(file)) {
			throw new AppException("传入文件[" + file.getAbsolutePath() + "]不是Excel文件！");
		}
		Database database;
		try {
			database = DatabaseBuilder.open(file);
		} catch (IOException e) {
			throw new AppException(e);
		}
		return database;
	}

	/**
	 * 获取表
	 * 
	 * @param db
	 * @param name
	 * @return
	 * @throws AppException
	 */
	public static final Table getTable(Database db, String name) throws AppException {
		try {
			return db.getTable(name);
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	/**
	 * 获取表数据
	 * 
	 * @param db
	 * @param name
	 * @return
	 * @throws AppException
	 */
	public static final DataStore getTableDs(Database db, String name) throws AppException {
		Table table = getTable(db, name);
		return getTableDs(table);
	}

	/**
	 * 获取表数据
	 * 
	 * @param table
	 * @return
	 * @throws AppException
	 */
	public static final DataStore getTableDs(Table table) throws AppException {
		DataStore vds = new DataStore();
		List<? extends Column> columns = table.getColumns();
		for (Row row : table) {
			vds.addRow();
			for (int i = 0; i < columns.size(); i++) {
				String key = columns.get(i).getName();
				vds.put(vds.rowCount() - 1, key, row.get(key));
			}
		}
		return vds;
	}

	/**
	 * 保存数据
	 * 
	 * @param table
	 * @param ds
	 * @throws AppException
	 */
	public static final void saveDsToTable(Table table, DataStore ds) throws AppException {
		try {
			table.addRowsFromMaps(ds);
		} catch (IOException e) {
			throw new AppException(e);
		}
	}

	/**
	 * 获取bytes
	 * 
	 * @param db
	 * @return
	 * @throws AppException
	 * @throws IOException
	 */
	public static final byte[] getBytes(Database db) throws AppException {
		File file = db.getFile();
		byte[] accbytes = FileIOTool.getBytesFromFile(file);
		return accbytes;
	}

	/**
	 * 验证文件是不是pbf文件(暂时仅支持access,其他类型有待探索)
	 * 
	 * @param file
	 * @return
	 */
	public static final boolean isPBFFile(File file) {
		return file.isFile() && (file.getName().endsWith(".mdb") || file.getName().endsWith(".accdb"));

	}

}
