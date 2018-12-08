package com.sept.support.database;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.sept.exception.AppException;
import com.sept.exception.SeptException;
import com.sept.exception.SqlException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.thread.GlobalToolkit;
import com.sept.support.util.BlobUtil;
import com.sept.support.util.DateUtil;
import com.sept.support.util.TypeUtil;

public class Sql {

	private String dbName = null;
	private ArrayList<Object> alParas = null;
	private ArrayList<ArrayList<Object>> alBatchParas = null;
	private String nowSql = null;
	private JdbcTemplate jdbcTemplate = null;
	private boolean batchFlag = false;

	// private boolean batch = false;

	public Sql() {
		this.dbName = "dataSource";
	}

	public Sql(String dbName) {
		this.dbName = dbName;
	}

	public void setSql(String sql) throws SqlException {
		if (batchFlag == true) {
			throw new SqlException(
					"当前SQL类已经设置了Batch参数，不能重新设置SQL String，请重新实例化SQL或执行ExecuteBatch或执行resetBatch方法后，再设置SQL String。");
		}
		if (null == sql || sql.trim().isEmpty()) {
			throw new SqlException("所要执行的sql为空！");
		}
		this.nowSql = sql;
		this.alParas = new ArrayList<Object>();
	}

	public void setSql(StringBuffer sql) throws SqlException {
		if (batchFlag == true) {
			throw new SqlException(
					"当前SQL类已经设置了Batch参数，不能重新设置SQL String，请重新实例化SQL或执行ExecuteBatch或执行resetBatch方法后，再设置SQL String。");
		}
		if (null == sql || sql.toString().trim().isEmpty()) {
			throw new SqlException("所要执行的sql为空！");
		}
		this.nowSql = sql.toString();
		this.alParas = new ArrayList<Object>();

	}

	public void setPara(int index, Object para) throws SqlException {
		para = this.checkPara(para);
		if (index > (alParas.size() + 1)) {
			throw new SqlException("不允许跨越式插入");
		}
		if (null == para) {
			throw new SqlException("插入null数据时，无法预知的类型");
		}
		alParas.add(index - 1, para);
	}

	public void setInt(int index, int para) throws SqlException {
		this.setPara(index, para);
	}

	public void setDouble(int index, double para) throws SqlException {
		this.setPara(index, para);
	}

	public void setFloat(int index, float para) throws SqlException {
		this.setPara(index, para);
	}

	public void setBoolean(int index, boolean para) throws SqlException {
		this.setPara(index, para);
	}

	public void setString(int index, String para) throws SqlException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.VARCHAR));
		} else {
			this.setPara(index, para);
		}
	}

	public void setDate(int index, Date para) throws SqlException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.DATE));
		} else {
			this.setPara(index, para);
		}
	}

	public void setDateTime(int index, Date para) throws SqlException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.TIMESTAMP));
		} else {
			this.setPara(index, new Timestamp(para.getTime()));
		}
	}

	public void setDate(int index, java.sql.Date para) throws SqlException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.DATE));
		} else {
			this.setPara(index, para);
		}
	}

	public void setDateTime(int index, java.sql.Date para) throws SqlException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.TIMESTAMP));
		} else {
			this.setPara(index, new Timestamp(para.getTime()));
		}
	}

	public void setTimestamp(int index, Timestamp para) throws SqlException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.TIMESTAMP));
		} else {
			this.setPara(index, para);
		}
	}

	public void setBlob(int index, Blob para) throws SeptException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.BINARY));
		} else {
			// 只有在blob流没关闭且blob不是很大的情况下才允许使用
			this.setPara(index, new BlobValue(BlobUtil.getBytes(para)));
		}
	}

	public void setBlob(int index, byte[] para) throws SeptException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.BINARY));
		} else {
			// 只有在blob流没关闭且blob不是很大的情况下才允许使用
			this.setPara(index, new BlobValue(para));
		}
	}

	public void setBlob(int index, String para) throws SeptException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.BINARY));
		} else {
			// 只有在blob流没关闭且blob不是很大的情况下才允许使用
			this.setPara(index, new BlobValue(para));
		}
	}

	public void setClob(int index, String para) throws SeptException {
		if (null == para) {
			this.setPara(index, new NullValue(java.sql.Types.LONGVARCHAR));
		} else {
			this.setPara(index, new ClobValue(para));
		}
	}

	/**
	 * 将单个的所有存放到批量的al中
	 */
	public void addBatch() {
		if (null == this.alBatchParas) {
			this.alBatchParas = new ArrayList<ArrayList<Object>>();
		}
		@SuppressWarnings("unchecked")
		ArrayList<Object> alTemp = (ArrayList<Object>) this.alParas.clone();
		this.alBatchParas.add(alTemp);
		this.alParas = new ArrayList<Object>();
		batchFlag = true;
	}

	public void resetBatch() {
		this.alBatchParas = null;
		batchFlag = false;
	}

	public int executeBatch() throws SqlException {
		int vi = 0;
		try {
			for (int i = 0; i < this.alBatchParas.size(); i++) {
				vi += this.executeUpdate(this.nowSql, this.alBatchParas.get(i));
			}
		} catch (Exception e) {
			throw new SqlException(e);
		} finally {
			this.batchFlag = false;
		}
		return vi;

	}

	public int executeUpdate() throws SqlException {
		if (batchFlag == true) {
			throw new SqlException(
					"当前SQL类已经设置了Batch参数，不能重新设置SQL String，请重新实例化SQL或执行ExecuteBatch或执行resetBatch方法后，再设置SQL String。");
		}
		return this.executeUpdate(this.nowSql, this.alParas);

	}

	private int executeUpdate(String sql, ArrayList<Object> alTempPara)
			throws SqlException {
		int vi = 0;
		try {
			this.jdbcTemplate = DatabaseSessionUtil
					.getCurrentSession(this.dbName);
			Transaction trans = TransactionManager.getTransaction(this.dbName);

			if (!trans.isUnderTransaction()) {
				throw new SqlException("当前数据库操作[" + this.nowSql + "]未正常开启事物！");
			}
			final ArrayList<Object> exePara = alTempPara;
			PreparedStatementSetter pss = new PreparedStatementSetter() {
				public void setValues(PreparedStatement pstmt)
						throws SQLException {
					ParameterMetaData pmd = pstmt.getParameterMetaData();
					int paraCount = pmd.getParameterCount();
					Object[] paras = exePara.toArray();
					try {
						Sql.this.setParas(pstmt, paras, paraCount);
					} catch (SqlException e) {
						throw new SQLException(e);
					}
				}
			};
			vi = this.jdbcTemplate.update(sql, pss);
		} catch (Exception e) {
			throw new SqlException(e);
		} finally {
			try {
				GlobalToolkit.getCurrentThread().addSql(sql);
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
		return vi;
	}

	public DataStore executeQuery() throws SqlException {
		if (batchFlag == true) {
			throw new SqlException(
					"当前SQL类已经设置了Batch参数，不能重新设置SQL String，请重新实例化SQL或执行ExecuteBatch或执行resetBatch方法后，再设置SQL String。");
		}
		DataStore ds = this.executeQuery(this.nowSql, this.alParas);
		ds = null == ds?new DataStore():ds;
		return ds;

	}

	/**
	 * 执行查询sql
	 * 
	 * @param sql
	 * @param alTempPara
	 * @return
	 * @throws SqlException
	 */
	private DataStore executeQuery(String sql, ArrayList<Object> alTempPara)
			throws SqlException {
		Transaction tm;
		try {
			tm = TransactionManager.getTransaction(this.dbName);

			if (!tm.isUnderTransaction()) {
				throw new SqlException("还未开启事物！");
			}

			this.jdbcTemplate = DatabaseSessionUtil
					.getCurrentSession(this.dbName);
			final ArrayList<Object> exePara = alTempPara;

			PreparedStatementSetter pss = new PreparedStatementSetter() {
				public void setValues(PreparedStatement pstmt)
						throws SQLException {
					ParameterMetaData pmd = pstmt.getParameterMetaData();
					int requiredParaCount = pmd.getParameterCount();
					Object[] paras = exePara.toArray();
					try {
						Sql.this.setParas(pstmt, paras, requiredParaCount);
					} catch (Exception e) {
						throw new SQLException(e);
					}
				}
			};
			ResultSetExtractor<DataStore> resultSetExtractor = new ResultSetExtractor<DataStore>() {
				public DataStore extractData(ResultSet rs) throws SQLException {
					DataStore ds = new DataStore();
					try {
						ResultSetMetaData meta = rs.getMetaData();
						int columnCount = meta.getColumnCount();
						int[] types = new int[columnCount];
						String[] columnName = new String[columnCount];
						for (int i = 1; i <= columnCount; i++) {
							types[i - 1] = meta.getColumnType(i);
							columnName[i - 1] = meta.getColumnName(i)
									.toLowerCase();
						}
						while (rs.next()) {
							DataObject doTemp = new DataObject();
							for (int i = 1; i <= columnCount; i++) {
								if (types[i - 1] == Types.VARCHAR) {
									if (null == rs.getString(i)) {
										doTemp.put(columnName[i - 1], null);
										doTemp.setType(columnName[i - 1],
												TypeUtil.STRING);

									} else {
										doTemp.put(columnName[i - 1],
												rs.getString(i));
									}
								} else if (types[i - 1] == Types.NUMERIC
										|| types[i - 1] == Types.DECIMAL) {
									doTemp.put(columnName[i - 1],
											rs.getBigDecimal(i));

								} else if (types[i - 1] == Types.FLOAT
										|| types[i - 1] == Types.DOUBLE) {
									doTemp.put(columnName[i - 1],
											rs.getDouble(i));

								} else if (types[i - 1] == Types.REAL) {
									doTemp.put(columnName[i - 1],
											rs.getFloat(i));

								} else if (types[i - 1] == Types.BIGINT) {
									doTemp.put(columnName[i - 1], rs.getLong(i));

								} else if (types[i - 1] == Types.INTEGER) {
									doTemp.put(columnName[i - 1], rs.getInt(i));

								} else if (types[i - 1] == Types.BIT) {
									doTemp.put(columnName[i - 1],
											rs.getBoolean(i));

								} else if (types[i - 1] == Types.DATE) {
									if (null == rs.getDate(i)) {
										doTemp.put(columnName[i - 1], null);
										doTemp.setType(columnName[i - 1],
												TypeUtil.DATE);
									} else {
										doTemp.put(columnName[i - 1], new Date(
												rs.getTimestamp(i).getTime()));
									}
								} else if (types[i - 1] == Types.BLOB) {
									doTemp.put(columnName[i - 1], rs.getBlob(i));
								} else if (types[i - 1] == Types.BOOLEAN) {
									doTemp.put(columnName[i - 1],
											rs.getBoolean(i));
								} else if (types[i - 1] == Types.CLOB) {
									doTemp.put(columnName[i - 1], rs.getClob(i));
								} else {
									doTemp.put(columnName[i - 1],
											rs.getObject(i));
									doTemp.setType(columnName[i - 1],
											TypeUtil.OBJECT);
								}
							}
							ds.addRow(doTemp);
						}
					} catch (Exception e) {
						throw new SQLException(e);
					}
					return ds;
				}
			};
			this.jdbcTemplate = DatabaseSessionUtil
					.getCurrentSession(this.dbName);
			this.jdbcTemplate.setFetchSize(1000);
			DataStore ds = (DataStore) this.jdbcTemplate.query(sql, pss,
					resultSetExtractor);
			return ds;
		} catch (Exception e) {
			throw new SqlException(e);
		} finally {
			try {
				GlobalToolkit.getCurrentThread().addSql(sql);
			} catch (AppException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 设置参数
	 * 
	 * @param pstmt
	 * @param para
	 * @param requiredParaCount
	 * @throws SqlException
	 */
	private void setParas(PreparedStatement pstmt, Object[] para,
			int requiredParaCount) throws SqlException {
		if (para == null) {
			if (requiredParaCount > 0) {
				throw new SqlException("设置参数个数[0]少于sql要求的参数个数["
						+ requiredParaCount + "]");
			}
			return;
		}
		if (para.length < requiredParaCount) {
			throw new SqlException("设置参数个数[" + para.length + "]少于sql要求的参数个数["
					+ requiredParaCount + "]");
		}
		if (para.length > requiredParaCount) {
			throw new SqlException("设置参数个数[" + para.length + "]多于sql要求的参数个数["
					+ requiredParaCount + "]");
		}
		try {
			for (int i = 0; i < para.length; i++) {
				Object o = para[i];
				if ((o instanceof Integer)) {
					pstmt.setInt(i + 1, ((Integer) o).intValue());
				} else if ((o instanceof Double)) {
					pstmt.setDouble(i + 1, ((Double) o).doubleValue());
				} else if ((o instanceof Boolean)) {
					pstmt.setBoolean(i + 1, ((Boolean) o).booleanValue());
				} else if ((o instanceof String)) {
					pstmt.setString(i + 1, (String) o);
				} else if ((o instanceof java.sql.Date)) {
					pstmt.setDate(i + 1, (java.sql.Date) o);
				} else if ((o instanceof Timestamp)) {
					pstmt.setTimestamp(i + 1, (Timestamp) o);
				} else if ((o instanceof java.util.Date)) {
					pstmt.setDate(i + 1,
							new java.sql.Date(((java.util.Date) o).getTime()));
				} else if ((o instanceof Blob)) {
					pstmt.setBlob(i + 1, (Blob) o);
				} else if ((o instanceof BlobValue)) {
					BlobValue blobValue = (BlobValue) o;
					pstmt.setBinaryStream(i + 1, new ByteArrayInputStream(
							blobValue.getValue()), blobValue.getLength());
				} else if ((o instanceof ClobValue)) {
					ClobValue clobValue = (ClobValue) o;
					StringReader r = new StringReader(clobValue.getValue());
					pstmt.setCharacterStream(i + 1, r, clobValue.getLength());
				} else if ((o instanceof StringBuffer)) {
					StringBuffer longVarCharValue = (StringBuffer) o;
					StringReader reader = new StringReader(
							longVarCharValue.toString());
					pstmt.setCharacterStream(i + 1, reader, longVarCharValue
							.toString().length());
				} else if ((o instanceof NullValue)) {
					pstmt.setNull(i + 1, ((NullValue) o).getType());
				} else if (o == null) {
					throw new SqlException("第" + (i + 1) + "个参数未定义");
				} else {
					throw new SqlException("第" + (i + 1) + "个参数类型不合法");
				}
			}
		} catch (SQLException e) {
			throw new SqlException(e);
		}
	}

	class NullValue {
		private int type;

		public NullValue(int type) {
			this.type = type;
		}

		public int getType() {
			return this.type;
		}
	}

	class BlobValue implements Serializable {
		private static final long serialVersionUID = 4987534051624333379L;
		private byte[] value;

		public BlobValue(byte[] value) {
			if (value == null) {
				value = new byte[0];
			}
			this.value = value;
		}

		public BlobValue(String value) {
			if (value == null) {
				value = "";
			}
			try {
				this.value = value.getBytes("UTF-8");
			} catch (Exception e) {
				this.value = value.getBytes();
			}
		}

		public byte[] getValue() {
			return this.value;
		}

		public int getLength() {
			return this.value.length;
		}
	}

	class ClobValue implements Serializable {
		private static final long serialVersionUID = -2900880620133106928L;
		private String value;

		public ClobValue(String value) {
			this.value = (value == null ? "" : value);
		}

		public String getValue() {
			return this.value;
		}

		public int getLength() {
			return this.value.length();
		}
	}

	/**
	 * 检查参数
	 * 
	 * @param para
	 * @return
	 * @throws SqlException
	 */
	private Object checkPara(Object para) throws SqlException {
		if (null == para) {
			return null;
		}
		if (para instanceof String || para instanceof java.sql.Date
				|| para instanceof Double || para instanceof Float
				|| para instanceof Number || para instanceof Integer
				|| para instanceof Timestamp || para instanceof Boolean
				|| para instanceof Blob || para instanceof Clob) {
			return para;
		}
		if (para instanceof java.util.Date) {
			return new java.sql.Date(((java.util.Date) para).getTime());
		}
		if (para instanceof StringBuffer) {
			return para.toString();
		}
		if (para instanceof BigDecimal) {
			return ((BigDecimal) para).toString();
		}
		throw new SqlException("不支持的类型--" + para.getClass());
	}

	/**
	 * 获取组装完成的sqlString
	 * 
	 * @throws SqlException
	 */
	public String getSqlString() throws AppException, SqlException {
		Object[] args = new Object[alParas.size()];

		for (int i = 0; i < alParas.size(); i++) {
			Object o = this.alParas.get(i);
			if (o instanceof java.lang.Integer) {
				args[i] = ((Integer) o).toString();
			} else if (o instanceof java.lang.Double) {
				args[i] = ((Double) o).toString();
			} else if (o instanceof java.lang.Boolean) {
				args[i] = ((Boolean) o).toString();
			} else if (o instanceof java.lang.String) {
				args[i] = "'" + ((String) o).replaceAll("'", "''") + "'";
			} else if (o instanceof java.sql.Timestamp) {
				args[i] = "to_date('"
						+ DateUtil.formatDate((Date) o, "yyyyMMddHHmmss")
						+ "','yyyymmddhh24miss')";
			} else if (o instanceof java.util.Date) {
				args[i] = "to_date('"
						+ DateUtil.formatDate((Date) o, "yyyyMMdd")
						+ "','yyyymmdd')";
			} else if (o instanceof java.sql.Blob) {
				throw new SqlException("第" + (i + 1) + "个参数类型是Blob，不能转成String");
			} else if (o instanceof BlobValue) {
				throw new SqlException("第" + (i + 1)
						+ "个参数类型是BlobValue，不能转成String");
			} else if (o instanceof StringReader) {// 增加对longVarChar类型数据的处理
				throw new SqlException("第" + (i + 1)
						+ "个参数类型是LongVarChar，不能转成String");
			} else if (o instanceof NullValue) {
				args[i] = "null";
			} else if (o == null) {
				throw new SqlException("第" + (i + 1) + "个参数未定义");
			} else {
				throw new SqlException("第" + (i + 1) + "个参数类型不合法");
			}
		}

		// | var | str |
		// select 'aa?aa'||?||'bb?bb' a FROM scott.emp
		// ^ ^
		// | |
		// | |
		// | |
		// beginQuoteMarkPos
		// |
		// endQuoteMarkPos
		//
		// 以偶数个单引号为分割点进行分割：
		//
		// 1) - "select 'aa?aa'"
		// 2) - "||?||'bb?bb'"
		// 3) - " a FROM scott.emp"
		//
		// 每一个分割片段中，单引号之间的字符全部忽略，
		// 单引号之外的"?"替换为对应的变量
		//
		String rawSqlStr = nowSql;
		StringBuffer resultSqlStr = new StringBuffer();

		int quoteMarkScanPos = 0; // 单引号扫描位置
		int beginQuoteMarkPos = -1;
		int endQuoteMarkPos = -1;
		int varReplaceNum = 0;
		while (rawSqlStr.indexOf("'", quoteMarkScanPos) != -1) {
			beginQuoteMarkPos = rawSqlStr.indexOf("'", quoteMarkScanPos);
			endQuoteMarkPos = rawSqlStr.indexOf("'", beginQuoteMarkPos + 1);

			String varQuestionMarksStr = rawSqlStr.substring(quoteMarkScanPos,
					beginQuoteMarkPos);
			String strQuestionMarksStr = rawSqlStr.substring(beginQuoteMarkPos,
					endQuoteMarkPos + 1);
			int questionMarkScanPos = 0;
			int questionMarkPos = -1;

			while (varQuestionMarksStr.indexOf("?", questionMarkScanPos) != -1) {
				questionMarkPos = varQuestionMarksStr.indexOf("?",
						questionMarkScanPos);

				resultSqlStr.append(varQuestionMarksStr.substring(
						questionMarkScanPos, questionMarkPos)
						+ (String) args[varReplaceNum++]);

				questionMarkScanPos = questionMarkPos + 1;
			}

			resultSqlStr.append(varQuestionMarksStr
					.substring(questionMarkScanPos) + strQuestionMarksStr);

			quoteMarkScanPos = endQuoteMarkPos + 1;
		}

		// quoteMarkScanPos位置后面的字符串已经不包含【'】符号，单纯解析【?】就可以
		String varQuestionMarksStr = rawSqlStr.substring(quoteMarkScanPos);
		int questionMarkScanPos = 0;
		int questionMarkPos = -1;
		while (varQuestionMarksStr.indexOf("?", questionMarkScanPos) != -1) {
			questionMarkPos = varQuestionMarksStr.indexOf("?",
					questionMarkScanPos);

			resultSqlStr.append(varQuestionMarksStr.substring(
					questionMarkScanPos, questionMarkPos)
					+ (String) args[varReplaceNum++]);

			questionMarkScanPos = questionMarkPos + 1;
		}
		resultSqlStr.append(varQuestionMarksStr.substring(questionMarkScanPos));

		return resultSqlStr.toString();
	}

	public String getSql() {
		return this.nowSql;

	}
}
