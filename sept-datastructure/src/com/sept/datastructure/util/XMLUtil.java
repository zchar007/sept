package com.sept.datastructure.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.datastructure.exception.DataException;
import com.sept.exception.ApplicationException;

public class XMLUtil {
	/**
	 * xmlStringתΪDataObject�� DataStore
	 * 
	 * @param para
	 * @return
	 * @throws DataException
	 * @throws ApplicationException 
	 * @throws DocumentException
	 */
	public final static Object XmlToData(String para) throws DataException, ApplicationException {
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
			throw new DataException(e.getMessage());
		}
	}

	/**
	 * xmlStringתΪDataObject�� DataStore
	 * 
	 * @param para
	 * @return
	 * @throws DataException
	 * @throws ApplicationException 
	 * @throws DocumentException
	 */
	public final static Object XmlToData(Element element) throws DataException, ApplicationException {
		String type = element.attributeValue("t");
		if (TypeUtil.DATAOBJECT.equals(type)) {
			return XmlToDataObject(element);
		} else if (TypeUtil.DATASTORE.equals(type)) {
			return XmlToDataStore(element);
		} else {
			throw new DataException("���������xml����ΪDataObjectת�ɻ�DataStoreת��");
		}
	}

	public static DataObject XmlToDataObject(String xmlStr) throws DataException, ApplicationException {
		return new XMLDataObject(xmlStr).getDataObject();
	}

	public static DataObject XmlToDataObject(Element element) throws DataException, ApplicationException {
		return new XMLDataObject(element).getDataObject();
	}

	public static DataStore XmlToDataStore(String xmlStr) throws DataException, ApplicationException {
		return new XMLDataStore(xmlStr).getDataStore();
	}

	public static DataStore XmlToDataStore(Element element) throws DataException, ApplicationException {
		return new XMLDataStore(element).getDataStore();
	}

	/**
	 * DataObject תxml Element
	 * 
	 * @param pdo
	 * @return
	 * @throws DataException
	 */
	public static Element DataObjectToXml(DataObject pdo) throws DataException {

		return new DataObjectXML(pdo).getElement();
	}

	/**
	 * DataObject תxmlString
	 * 
	 * @param pdo
	 * @return
	 * @throws DataException
	 */
	public static String DataObjectToXmlString(DataObject pdo) throws DataException {
		return new DataObjectXML(pdo).getElement().asXML();
	}

	/**
	 * DataStoreתxml Element
	 * 
	 * @param pds
	 * @return
	 * @throws DataException
	 */
	public static Element DataStoreToXml(DataStore pds) throws DataException {
		return new DataStoreXML(pds).getElement();

	}

	/**
	 * DataStoreתxml�ַ���
	 * 
	 * @param pds
	 * @return
	 * @throws DataException
	 */
	public static String DataStoreToXmlString(DataStore pds) throws DataException {
		return new DataStoreXML(pds).getElement().asXML();

	}
}
