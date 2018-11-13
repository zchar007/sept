package com.sept.datastructure.util;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.datastructure.exception.DataException;
import com.sept.exception.ApplicationException;

public class XMLDataStore {
	private Element element;
	private String typelist;

	public XMLDataStore(String xmlStr) throws DataException {
		try {
			this.element = new SAXReader().read(new ByteArrayInputStream(xmlStr.getBytes())).getRootElement();
		} catch (DocumentException e) {
			throw new DataException(e);
		}
		this.typelist = com.sept.util.XMLUtil.decodeXML(element.attributeValue("tl"));
	}

	public XMLDataStore(Element element) {
		this.element = element;
		this.typelist = com.sept.util.XMLUtil.decodeXML(element.attributeValue("tl"));
	}

	private DataStore parseDataStore() throws DataException, ApplicationException {
		if (null == element) {
			return (DataStore) null;
		}
		DataStore pds = new DataStore();
		String type = com.sept.util.XMLUtil.decodeXML(element.attributeValue("t"));
		if (!TypeUtil.DATASTORE.equals(type)) {
			throw new DataException("��Ҫ������xml���Ǳ�׼�� DataStore XML");
		}
		if (!element.getName().toUpperCase().equals("D")) {
			throw new DataException("����xml��ʽ��DataStoreʱ����,��ʼ������<d>��ǩΪ���ڵ�!");
		}
		// ����

		for (Iterator<?> it = element.elementIterator(); it.hasNext();) {
			Element element1 = (Element) it.next();

			if (!element1.getName().toUpperCase().equals("R")) {
				throw new DataException("����xml��ʽ��DataStoreʱ����,��ʼ������<d>��ǩΪ���ڵ�!");
			}
			element1.addAttribute("tl", this.typelist);//tl:typelist
			// DataObject�µ�Ԫ�ض���key
			DataObject pds2 = new XMLDataObject(element1).getDataObject();
			pds.addRow(pds2);
			continue;

		}
		return pds;
	}

	public DataStore getDataStore() throws DataException, ApplicationException {
		return this.parseDataStore();

	}

}