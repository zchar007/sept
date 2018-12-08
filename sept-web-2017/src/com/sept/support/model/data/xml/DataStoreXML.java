package com.sept.support.model.data.xml;

import java.util.Iterator;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataStore;
import com.sept.support.util.XMLUtil;

public class DataStoreXML {
	private final static String ROOT_TYPE = "d";
	private final static String OBJECT_TYPE = "ds";

	private DataStore pds;
	private Element root;
	private String typeList;

	public DataStoreXML(DataStore pds) {
		this.pds = pds;
		this.root = DocumentHelper.createElement(ROOT_TYPE);
		this.root.addAttribute("t", OBJECT_TYPE);
	}

	public void addParas() throws AppException {
		for (int i = 0; i < this.pds.rowCount(); i++) {
			Element valueElement = this.root.addElement("r");
			valueElement.addAttribute("l", i + "");
			DataObjectXML doXml = new DataObjectXML(this.pds.getRow(i));
			Element element = doXml.getElement();
			for (Iterator<?> it = element.elementIterator(); it.hasNext();) {
				Element element1 = (Element) it.next();
				valueElement.add(element1.detach());
			}
		}
	}

	public Element getElement() throws AppException {
		this.addParas();
		this.typeList = this.pds.getTypeList();
		return this.root.addAttribute("tl",
				XMLUtil.encodeXML(this.typeList));
	}

}
