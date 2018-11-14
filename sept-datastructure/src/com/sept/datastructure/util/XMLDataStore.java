package com.sept.datastructure.util;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class XMLDataStore {
	private Element element;
	private String typelist;

	public XMLDataStore(String xmlStr) throws AppException {
		try {
			this.element = new SAXReader().read(new ByteArrayInputStream(xmlStr.getBytes())).getRootElement();
		} catch (DocumentException e) {
			throw new AppException(e);
		}
		this.typelist = com.sept.util.XMLUtil.decodeXML(element.attributeValue("tl"));
	}

	public XMLDataStore(Element element) {
		this.element = element;
		this.typelist = com.sept.util.XMLUtil.decodeXML(element.attributeValue("tl"));
	}

	private DataStore parseDataStore() throws AppException {
		if (null == element) {
			return (DataStore) null;
		}
		DataStore pds = new DataStore();
		String type = com.sept.util.XMLUtil.decodeXML(element.attributeValue("t"));
		if (!TypeUtil.DATASTORE.equals(type)) {
			throw new AppException("所要解析的xml不是标准的 DataStore XML");
		}
		if (!element.getName().toUpperCase().equals("D")) {
			throw new AppException("解析xml格式的DataStore时出错,开始必须以<d>标签为根节点!");
		}
		// 类型

		for (Iterator<?> it = element.elementIterator(); it.hasNext();) {
			Element element1 = (Element) it.next();

			if (!element1.getName().toUpperCase().equals("R")) {
				throw new AppException("解析xml格式的DataStore时出错,开始必须以<d>标签为根节点!");
			}
			element1.addAttribute("tl", this.typelist);//tl:typelist
			// DataObject下的元素都有key
			DataObject pds2 = new XMLDataObject(element1).getDataObject();
			pds.addRow(pds2);
			continue;

		}
		return pds;
	}

	public DataStore getDataStore() throws AppException {
		return this.parseDataStore();

	}

}
