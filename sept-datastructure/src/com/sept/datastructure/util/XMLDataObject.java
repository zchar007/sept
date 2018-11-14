package com.sept.datastructure.util;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class XMLDataObject {
	private Element element;
	private String typelist;

	public XMLDataObject(String xmlStr) throws AppException {
		try {
			this.element = new SAXReader().read(new ByteArrayInputStream(xmlStr.getBytes())).getRootElement();
		} catch (DocumentException e) {
			throw new AppException(e);
		}
		this.typelist = com.sept.util.XMLUtil.decodeXML(element.attributeValue("tl"));
	}

	public XMLDataObject(Element element) {
		this.element = element;
		this.typelist = com.sept.util.XMLUtil.decodeXML(element.attributeValue("tl"));
	}

	private DataObject parseDataObject() throws AppException {
		if (null == element) {
			return (DataObject) null;
		}
		DataObject pdo = new DataObject();
		pdo.setTypeList(typelist);

		for (Iterator<?> it = element.elementIterator(); it.hasNext();) {
			Element element1 = (Element) it.next();
			if (!element1.getName().toUpperCase().equals("P")) {
				throw new AppException("解析xml格式的DataObject时出错,<d>标签中只允许直接出现<p>标签！");
			}
			// DataObject下的元素都有key
			String key = com.sept.util.XMLUtil.decodeXML(element1.attributeValue("k"));

			if (TypeUtil.DATAOBJECT.equals(pdo.getType(key))) {
				Element elementDO = null;
				// 去除第一个do元素
				for (Iterator<?> it2 = element1.elementIterator(); it2.hasNext();) {
					elementDO = (Element) it2.next();
					break;
				}
				DataObject pdo2 = new XMLDataObject(elementDO).getDataObject();
				pdo.put(com.sept.util.XMLUtil.decodeXML(key), pdo2);
				continue;
			}
			if (TypeUtil.DATASTORE.equals(pdo.getType(key))) {
				Element elementDS = null;
				for (Iterator<?> it2 = element1.elementIterator(); it2.hasNext();) {
					elementDS = (Element) it2.next();
					break;
				}
				DataStore pds2 = new XMLDataStore(elementDS).getDataStore();
				pdo.put(com.sept.util.XMLUtil.decodeXML(key), pds2);
				continue;
			}

			String value;
			try {
				value = element1.attributeValue("v");
			} catch (Exception e) {
				throw new AppException("解析xml格式的DataObject时出错,【" + key + "】的类型是【" + pdo.getTypeList() + "】必须含有v属性！");
			}
			pdo.put(com.sept.util.XMLUtil.decodeXML(key),
					TypeUtil.getValueByType(pdo.getType(key), com.sept.util.XMLUtil.decodeXML(value)));
		}
		return pdo;
	}

	public DataObject getDataObject() throws AppException {
		return this.parseDataObject();

	}

}
