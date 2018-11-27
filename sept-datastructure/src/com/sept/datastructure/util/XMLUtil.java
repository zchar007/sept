package com.sept.datastructure.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class XMLUtil {
	/**
	 * xmlString转为DataObject或 DataStore
	 * 
	 * @param para
	 * @return
	 * @throws AppException
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

	public static DataObject XmlToDataObject(Element element) throws AppException {
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
	public static String DataObjectToXmlString(DataObject pdo) throws AppException {
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
	public static String DataStoreToXmlString(DataStore pds) throws AppException {
		return new DataStoreXML(pds).getElement().asXML();

	}
}
