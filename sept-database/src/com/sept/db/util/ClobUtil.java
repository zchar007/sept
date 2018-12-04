package com.sept.db.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.SQLException;

import com.alibaba.druid.proxy.jdbc.ClobProxy;
import com.sept.exception.AppException;

import oracle.sql.CLOB;

public class ClobUtil {
	/**
	 * 将Clob类型的对象转换成字符串 如果Clob是一个空Clob对象或是本身为空 则转换成null
	 * 
	 * @param clob
	 * @return 如果clob为 null 返回 null
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
			throw new AppException("从Clob对象中获取字符流输出对象时出现异常!");
		} catch (IOException e) {
			throw new AppException("从Clob对象中读取内容是出现 I/O 异常!");
		}
	}

	/**
	 * 将String类型的长文本内容写入到Clob类型的对象当中
	 * 
	 * @param clobObj    从数据库中获取的Clob列的对象
	 * @param clobString 要写入的长文本字符串
	 * @throws AppException
	 */
	public final static void writeString2Clob(Clob clobObj, String clobString) throws AppException {
		if (clobObj == null) {// 如果没有获取到
			throw new AppException("在向Clob类型中写入数据时，传入的Clob类型为NULL，属于非法对象!");
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
			throw new AppException("在向Clob对象中写入数据时出现I/O异常!", e);
		} catch (SQLException e) {
			throw new AppException("在获取Clob文本输出流时出现SQLException!", e);
		}
	}
}
