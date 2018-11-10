package com.sept.database.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Date;

import com.alibaba.druid.proxy.jdbc.ClobProxy;
import com.sept.framework.exception.AppException;
import com.sept.framework.util.data.DataStore;

import oracle.sql.CLOB;

public class DBUtil {
	public static long dbTime = 0; // ���ݿ�ʱ��
	public static long serverStartTime = 0; // ��ȡ���ݿ�ʱ��Ŀ�ʼʱ��
	/**
	 * ˵������ȡ���ݿ�ʱ��
	 * 
	 * @throws SqlException
	 */
	public final static Date getDBDate() throws AppException, SqlException {
		String s = null;
		Sql sql = new Sql();
		if (DatabaseSessionUtil.getDBType() == DatabaseSessionUtil.DBTYPE_ORACLE) {
			sql.setSql("select to_char(sysdate,'yyyy-MM-dd') dbdate from dual");
		} else if (DatabaseSessionUtil.getDBType() == DatabaseSessionUtil.DBTYPE_POSTGRESQL) {
			sql.setSql("select to_char(CURRENT_DATE,'yyyy-MM-dd') dbdate from dual");
		} else {
			throw new AppException("��ܲ�ǩ����ʶ�����ݿ����͡�"
					+ DatabaseSessionUtil.getDBType() + "��");
		}

		DataStore vds = sql.executeQuery();
		if (vds.rowCount() > 0) {
			s = vds.getString(0, "dbdate");
		}

		return new Date(formatStrToDate(s).getTime());
	}

	/**
	 * ��ȡ����ʱ�䣺��ʽΪ��yyyy-MM-dd hh:mm:ss
	 * 
	 * @throws SqlException
	 */
	public final static Date getDBTime() throws AppException, SqlException {
		synchronized (refreshDBTime) {
			long currentTime = System.currentTimeMillis();
			if(!"true".equals(GlobalNames.getDeploy("sql", "SPRING-DBABLE"))){
				return new Date();
			}

			if (dbTime == 0
					|| (dbTime != 0 && currentTime - serverStartTime > 60000)) {
				String s = null;
				Sql sql = new Sql();
				if (DatabaseSessionUtil.getDBType() == DatabaseSessionUtil.DBTYPE_ORACLE) {
					sql.setSql("select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') dbdate from dual");
				} else if (DatabaseSessionUtil.getDBType() == DatabaseSessionUtil.DBTYPE_POSTGRESQL) {
					sql.setSql("select to_char(CURRENT_TIMESTAMP,'yyyy-mm-dd hh24:mi:ss') dbdate from dual");
				} else {
					throw new AppException("��ܲ�ǩ����ʶ�����ݿ����͡�"
							+ DatabaseSessionUtil.getDBType() + "��");
				}

				DataStore vds = sql.executeQuery();
				if (vds.rowCount() > 0) {
					s = vds.getString(0, "dbdate");
				}

				dbTime = formatStrToDate(s).getTime();
				serverStartTime = System.currentTimeMillis();
			} else {
				dbTime += currentTime - serverStartTime;
				serverStartTime = currentTime;
			}
			return new Date(dbTime);
		}
	}
	
	/**
	 * ��Clob���͵Ķ���ת�����ַ��� ���Clob��һ����Clob������Ǳ���Ϊ�� ��ת����null
	 * 
	 * @param clob
	 * @return ���clobΪ null ���� null
	 * @throws AppException
	 * @since 2.0
	 * @author wf 2009-12-2 13:31:05
	 */
	public final static String clob2string(Clob clob) throws AppException {
		if (clob == null) {
			return null;
		}
		Reader reader;
		try {
			if (clob instanceof ClobProxy) {
				ClobProxy clobProxy = (ClobProxy) clob;
				reader = clobProxy.getRawClob().getCharacterStream();
			} else if (clob instanceof javax.sql.rowset.serial.SerialClob) {
				javax.sql.rowset.serial.SerialClob serialClob = (javax.sql.rowset.serial.SerialClob) clob;
				reader = serialClob.getCharacterStream();
			} else {
				reader = clob.getCharacterStream();
			}
			if (reader != null) {
				BufferedReader br = new BufferedReader(reader);
				String lineString = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (lineString != null) {
					if (sb.length() == 0) {
						sb.append(lineString);
					} else {
						sb.append("\n" + lineString);
					}
					lineString = br.readLine();
				}
				return sb.toString();
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new AppException("��Clob�����л�ȡ�ַ����������ʱ�����쳣!");
		} catch (IOException e) {
			throw new AppException("��Clob�����ж�ȡ�����ǳ��� I/O �쳣!");
		}
	}

	/**
	 * ��String���͵ĳ��ı�����д�뵽Clob���͵Ķ�����
	 * 
	 * @param clobObj
	 *            �����ݿ��л�ȡ��Clob�еĶ���
	 * @param clobString
	 *            Ҫд��ĳ��ı��ַ���
	 * @throws AppException
	 */
	public final static void writeString2Clob(Clob clobObj, String clobString)
			throws AppException {
		if (clobObj == null) {// ���û�л�ȡ��
			throw new AppException("����Clob������д������ʱ�������Clob����ΪNULL�����ڷǷ�����!");
		}
		try {
			Writer out = null;
			if (clobObj instanceof ClobProxy) {
				ClobProxy clobProxy = (ClobProxy) clobObj;
				out = clobProxy.getRawClob().setCharacterStream(0L);
			} else if (clobObj instanceof javax.sql.rowset.serial.SerialClob) {
				javax.sql.rowset.serial.SerialClob serialClob = (javax.sql.rowset.serial.SerialClob) clobObj;
				out = serialClob.setCharacterStream(0L);
			} else {
				out = ((CLOB) clobObj).setCharacterStream(0L);
			}

			out.write(clobString);
			out.close();
		} catch (IOException e) {
			throw new AppException("����Clob������д������ʱ����I/O�쳣!", e);
		} catch (SQLException e) {
			throw new AppException("�ڻ�ȡClob�ı������ʱ����SQLException!", e);
		}
	}

}
