package com.sept.support.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;
import com.sept.support.model.data.xml.DataObjectXML;
import com.sept.support.model.data.xml.DataStoreXML;
import com.sept.support.model.data.xml.XMLDataObject;
import com.sept.support.model.data.xml.XMLDataStore;
import com.sept.support.model.file.FileSecurityByLine;

public class XMLUtil {
	private final static HashMap<String, String> specialCharMap = new HashMap<String, String>();
	private final static HashMap<String, String> specialCharMapR = new HashMap<String, String>();

	static {
		specialCharMap.put("&", "&amp;");
		specialCharMap.put(">", "&gt;");
		specialCharMap.put("<", "&lt;");
		specialCharMap.put("'", "&apos;");
		specialCharMap.put("\"", "&quot;");

		specialCharMapR.put("&amp;", "&");
		specialCharMapR.put("&gt;", ">");
		specialCharMapR.put("&lt;", "<");
		specialCharMapR.put("&apos;", "'");
		specialCharMapR.put("&quot;", "\"");
	}

	/**
	 * xmlString转为DataObject或 DataStore
	 * 
	 * @param para
	 * @return
	 * @throws AppException
	 * @throws DocumentException
	 */
	public final static Object XmlToData(String para) throws AppException {
		if (null == para || para.trim().isEmpty()) {
			return null;
		}
		SAXReader reader = new SAXReader();
		InputStream is = new ByteArrayInputStream(para.getBytes());
		try {
			Document document = reader.read(is);
			Element element = document.getRootElement();
			return XmlToData(element);
		} catch (DocumentException e) {
			throw new AppException(e.getMessage());
		}
	}

	/**
	 * xmlString转为DataObject或 DataStore
	 * 
	 * @param para
	 * @return
	 * @throws AppException
	 * @throws DocumentException
	 */
	public final static Object XmlToData(Element element) throws AppException {
		String type = element.attributeValue("t");
		if (TypeUtil.DATAOBJECT.equals(type)) {
			return XmlToDataObject(element);
		} else if (TypeUtil.DATASTORE.equals(type)) {
			return XmlToDataStore(element);
		} else {
			throw new AppException("所需解析的xml必须为DataObject转成或DataStore转成");
		}
	}

	public static DataObject XmlToDataObject(String xmlStr) throws AppException {
		return new XMLDataObject(xmlStr).getDataObject();
	}

	public static DataObject XmlToDataObject(Element element)
			throws AppException {
		return new XMLDataObject(element).getDataObject();
	}

	public static DataStore XmlToDataStore(String xmlStr) throws AppException {
		return new XMLDataStore(xmlStr).getDataStore();
	}

	public static DataStore XmlToDataStore(Element element) throws AppException {
		return new XMLDataStore(element).getDataStore();
	}

	/**
	 * DataObject 转xml Element
	 * 
	 * @param pdo
	 * @return
	 * @throws AppException
	 */
	public static Element DataObjectToXml(DataObject pdo) throws AppException {

		return new DataObjectXML(pdo).getElement();
	}

	/**
	 * DataObject 转xmlString
	 * 
	 * @param pdo
	 * @return
	 * @throws AppException
	 */
	public static String DataObjectToXmlString(DataObject pdo)
			throws AppException {
		return new DataObjectXML(pdo).getElement().asXML();
	}

	/**
	 * DataStore转xml Element
	 * 
	 * @param pds
	 * @return
	 * @throws AppException
	 */
	public static Element DataStoreToXml(DataStore pds) throws AppException {
		return new DataStoreXML(pds).getElement();

	}

	/**
	 * DataStore转xml字符串
	 * 
	 * @param pds
	 * @return
	 * @throws AppException
	 */
	public static String DataStoreToXmlString(DataStore pds)
			throws AppException {
		return new DataStoreXML(pds).getElement().asXML();

	}

	/**
	 * 移除插入的特叔字符
	 * 
	 * @param value
	 * @return
	 */
	public static String encodeXML(String value) {
		if (value != null && !"".equals(value)) {
			if (specialCharMap != null && specialCharMap.size() > 0) {
				for (Entry<String, String> entry : specialCharMap.entrySet()) {
					value = value.replaceAll(entry.getKey(), entry.getValue());
				}
			}
		}
		return value;
	}

	/**
	 * 移除插入的特叔字符
	 * 
	 * @param value
	 * @return
	 */
	public static String decodeXML(String value) {
		if (value != null && !"".equals(value)) {
			if (specialCharMapR != null && specialCharMapR.size() > 0) {
				for (Entry<String, String> entry : specialCharMapR.entrySet()) {
					value = value.replaceAll(entry.getKey(), entry.getValue());
				}
			}
		}
		return value;
	}

	/**
	 * 验证xml
	 * 
	 * @param xsdPath
	 * @param xmlPath
	 * @throws AppException
	 */
	public static void validate(String xsdPath, Source source)
			throws AppException {
		try {
			// 建立schema工厂
			SchemaFactory schemaFactory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
			File schemaFile = new File(xsdPath);
			// 利用schema工厂，接收验证文档文件对象生成Schema对象
			Schema schema = schemaFactory.newSchema(schemaFile);
			// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
			Validator validator = schema.newValidator();
			// 得到验证的数据源
			// Source source = new StreamSource(xmlPath);
			// 开始验证，成功输出success!!!，失败输出fail
			validator.validate(source);
		} catch (Exception e) {
			throw new AppException("验证失败", e);
		}
	}

	public static void validate(String xsdPath, String xmlString)
			throws Exception {
		InputStream input = InputStreamUtil.StringTOInputStream(xmlString);
		Source source = new StreamSource(input);
		validate("./xml/Data.xsd", source);
	}

	public static void main(String[] args) throws Exception {
		StringBuffer sqlBF = new StringBuffer();
		sqlBF.setLength(0);
		sqlBF.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sqlBF.append("<!DOCTYPE d SYSTEM \"xml/Data.dtd\">");
		sqlBF.append("<d t=\"do\" tl=\"id:n,xm:s,nl:n,gz:ds\">");
		sqlBF.append("  <p k=\"id\" v=\"3333333333333333333333333\" />");
		sqlBF.append("  <p k=\"xm\" v=\"张三\" />");
		sqlBF.append("  <p k=\"nl\" v=\"20\" />");
		sqlBF.append("  <p k=\"gz\">");
		sqlBF.append("    <d t=\"ds\" tl=\"gzmc:s,jfgz:n\">");
		sqlBF.append("      <r l=\"1\">");
		sqlBF.append("        <p k=\"gzmc\" v=\"实际工资1\" />");
		sqlBF.append("        <p k=\"jfgz\" v=\"8001.0\" />");
		sqlBF.append("      </r>");
		sqlBF.append("      <r l=\"1\">");
		sqlBF.append("        <p k=\"gzmc\" v=\"实际工资2\" />");
		sqlBF.append("        <p k=\"jfgz\" v=\"8002.0\" />");
		sqlBF.append("      </r>");
		sqlBF.append("      <r l=\"1\">");
		sqlBF.append("        <p k=\"gzmc\" v=\"实际工资3\" />");
		sqlBF.append("        <p k=\"jfgz\" v=\"8003.0\" />");
		sqlBF.append("      </r>");
		sqlBF.append("    </d>");
		sqlBF.append("  </p>");
		sqlBF.append("</d>");

		// validate("./xml/Data.xsd", sqlBF.toString());
		//
		// SAXReader reader = new SAXReader();
		// InputStream is = new
		// ByteArrayInputStream(sqlBF.toString().getBytes());
		// Document document = null;
		// Element element = null;
		// try {
		// document = reader.read(is);
		// element = document.getRootElement();
		// } catch (DocumentException e) {
		// throw new AppException(e.getMessage());
		// }
		// DataObject vdo = XmlToDataObject(element);
		// System.out.println(vdo);
		FileSecurityByLine fsb = new FileSecurityByLine("./xml/TestData.xml");
		ArrayList<String> al = fsb.getArrayList();
		String str = "";
		for (int i = 0; i < al.size(); i++) {
			str += al.get(i);
		}
		System.out.println(str);
		DataObject vdo = (DataObject) XmlToDataObject(str);
		// System.out.println(vdo.getDateToString("d0", "yyyyMMdd"));
		DataStore ds = vdo.getDataStore("ds");
//		for (int i = 0; i < ds.rowCount(); i++) {
//			System.out.println(ds.getRow(i).getString("xm") + "--"
//					+ ds.getRow(i).getDateToString("nl", "yyyyMM"));
//		}
		
		System.out.println(vdo.toJSON());

	}
}
