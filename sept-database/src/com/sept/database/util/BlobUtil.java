package com.sept.database.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

import com.sept.exception.AppException;
import com.sept.util.SecUtil;

import oracle.sql.BLOB;

/**
 * blob用法 StringBuffer sqlBF = new StringBuffer(); sqlBF.setLength(0);
 * sqlBF.append(" insert into si3u.temp_store_detl "); sqlBF.append( " (zcid,
 * sjlb, sjymc, nr) "); sqlBF.append(" values "); sqlBF.append( " (?, ?, ?,
 * empty_blob()) ");
 * 
 * sql.setSql(sqlBF.toString()); sql.setString(1, pZcid); sql.setString(2,
 * pSjlb); sql.setString(3, pSjymc); sql.executeUpdate(); String string =
 * "aaaaaaaa\n"; sqlBF.setLength(0); sqlBF.append(" select nid, nr ");
 * sqlBF.append(" from sapp.stringformat_nr "); sqlBF.append( " where nid = ?
 * "); sqlBF.append(" for update ");
 * 
 * sql.setSql(sqlBF.toString()); sql.setString(1, "201701240101"); DataStore vds
 * = sql.executeQuery();
 * 
 * BLOB nr = (BLOB) vds.getObject(0, "nr"); BlobUtil.writeBytesToBlob(nr,
 * string.getBytes("GBK")); this.sql.close();
 * 
 * @author zchar
 *
 */
public final class BlobUtil {
	/**
	 * 写入blob流
	 * 
	 * @param blob
	 * @param bytes
	 * @throws SQLException
	 * @throws AppException
	 * @throws DiyException
	 */
	public final static void writeBytesToBlob(Blob blob, byte[] bytes) throws SQLException, AppException {
		OutputStream os = null;
		if (blob == null) {
			throw new AppException("Blob为null!");
		}
		if (bytes == null) {
			throw new AppException("BLOB所获取的bytes为null!");
		}
		try {
			os = blob.setBinaryStream(1L);
			os.write(bytes);
		} catch (SQLException e) {
			SQLException sqle = new SQLException("错误:写入blob时,sql异常");
			sqle.initCause(e);
			throw sqle;
		} catch (IOException e) {
			AppException appe = new AppException("错误:写入blob时,IO流异常");
			appe.initCause(e);
			throw appe;
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
					os = null;
				}
			} catch (IOException e) {
				AppException appe = new AppException("错误:写入blob时,关闭OutputStream异常");
				appe.initCause(e);
				throw appe;
			}
		}
	}

	/**
	 * 读取blob流
	 * 
	 * @param blob
	 * @return
	 * @throws SQLException
	 * @throws DiyException
	 */
	public final static byte[] getBytes(Blob blob) throws AppException, SQLException {
		InputStream is = null;
		if (blob == null) {
			throw new AppException("Blob为null!");
		}

		try {
			if (blob.length() == 0L) {
				return new byte[0];
			}
			is = blob.getBinaryStream();
			return IOUtils.toByteArray(is);
		} catch (SQLException e) {
			SQLException sqle = new SQLException("错误:读取blob时,sql异常");
			sqle.initCause(e);
			throw sqle;
		} catch (IOException e) {
			AppException appe = new AppException("错误:读取blob时,IO流异常");
			appe.initCause(e);
			throw appe;
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				AppException appe = new AppException("错误:读取blob时,关闭InputStream异常");
				appe.initCause(e);
				throw appe;
			}
		}

	}

	/**
	 * 说明：将服务器端的文档写进数据库中的blob中。
	 * 
	 * @author:ZC Oct 20, 2008
	 * @param blob
	 * @param attachfile
	 * @throws AppException
	 */

	public static void saveFileToBlob(File file, BLOB blob) throws AppException {
		if (file != null) {
			OutputStream outputstream = null;
			FileInputStream inputstream = null;
			try {
				outputstream = blob.setBinaryStream(0l);
				inputstream = new FileInputStream(file);
				byte[] buffer = new byte[blob.getBufferSize()];
				int len = 0;
				while ((len = inputstream.read(buffer)) != -1) {
					outputstream.write(buffer, 0, len);
				}
				outputstream.flush();
			} catch (Exception e) {
				throw new AppException("文件写入异常，可能是文件损坏或不存在!错误信息为：" + e.getMessage());
			} finally {
				try {
					if (outputstream != null) {
						outputstream.close();
					}
					if (inputstream != null) {
						inputstream.close();
					}
				} catch (Exception e) {
					throw new AppException("文件写入异常，可能是文件损坏或不存在!错误信息为：" + e.getMessage());
				}
			}
		}
	}

	public static String getImageStrByBlob(Blob image) throws SQLException, AppException {
		int zbnrLen = (new BigDecimal(image.length())).intValue();
		byte[] filebyte = image.getBytes(1, zbnrLen);
		// 对字节数组Base64编码
		return SecUtil.base64Encode(filebyte); // 返回Base64编码过的字节数组字符串
	}
}
