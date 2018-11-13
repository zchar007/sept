package com.sept.database.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

import com.sept.exception.AppException;
import com.sept.exception.SeptException;
import com.sept.exception.SqlException;

/**
 * blob�÷� StringBuffer sqlBF = new StringBuffer(); sqlBF.setLength(0);
 * sqlBF.append(" insert into si3u.temp_store_detl "); sqlBF.append(
 * " (zcid, sjlb, sjymc, nr) "); sqlBF.append(" values "); sqlBF.append(
 * " (?, ?, ?, empty_blob()) ");
 * 
 * sql.setSql(sqlBF.toString()); sql.setString(1, pZcid); sql.setString(2,
 * pSjlb); sql.setString(3, pSjymc); sql.executeUpdate(); String string =
 * "aaaaaaaa\n"; sqlBF.setLength(0); sqlBF.append(" select nid, nr ");
 * sqlBF.append("   from sapp.stringformat_nr "); sqlBF.append(
 * "  where nid = ?	"); sqlBF.append("    for update ");
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
	 * д��blob��
	 * 
	 * @param blob
	 * @param bytes
	 * @throws DiyException
	 */
	public final static void writeBytesToBlob(Blob blob, byte[] bytes)
			throws SeptException {
		OutputStream os = null;
		if (blob == null) {
			throw new AppException("BlobΪnull!");
		}
		if (bytes == null) {
			throw new AppException("BLOB����ȡ��bytesΪnull!");
		}
		try {

			os = blob.setBinaryStream(1L);
			os.write(bytes);
		} catch (SQLException e) {
			SqlException sqle = new SqlException("����:д��blobʱ,sql�쳣");
			sqle.initCause(e);
			throw sqle;
		} catch (IOException e) {
			AppException appe = new AppException("����:д��blobʱ,IO���쳣");
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
				AppException appe = new AppException(
						"����:д��blobʱ,�ر�OutputStream�쳣");
				appe.initCause(e);
				throw appe;
			}
		}
	}

	/**
	 * ��ȡblob��
	 * 
	 * @param blob
	 * @return
	 * @throws DiyException
	 */
	public final static byte[] getBytes(Blob blob) throws SeptException {
		InputStream is = null;
		if (blob == null) {
			throw new AppException("BlobΪnull!");
		}

		try {
			if (blob.length() == 0L) {
				return new byte[0];
			}
			is = blob.getBinaryStream();
			return IOUtils.toByteArray(is);
		} catch (SQLException e) {
			SqlException sqle = new SqlException("����:��ȡblobʱ,sql�쳣");
			sqle.initCause(e);
			throw sqle;
		} catch (IOException e) {
			AppException appe = new AppException("����:��ȡblobʱ,IO���쳣");
			appe.initCause(e);
			throw appe;
		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				AppException appe = new AppException(
						"����:��ȡblobʱ,�ر�InputStream�쳣");
				appe.initCause(e);
				throw appe;
			}
		}

	}
}
