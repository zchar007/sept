package com.sept.datastructure.util;

import java.util.Date;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;

import com.sept.datastructure.DataObject;
import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;

public class DataObjectXML {
	private final static String ROOT_TYPE = "d";
	private final static String OBJECT_TYPE = "do";

	private DataObject pdo;
	private Element root;
	private String typeList;

	public DataObjectXML(DataObject pdo) {
		this.pdo = pdo;
		this.root = DocumentHelper.createElement(ROOT_TYPE);
		this.root.addAttribute("t", OBJECT_TYPE);
		this.typeList = "";
	}

	public void addPara(String key, Object value) throws AppException {
		Element valueElement = this.root.addElement("p");
		String valueType = this.pdo.getType(key);
		if (valueType.equals(TypeUtil.CLOB) || valueType.equals(TypeUtil.BLOB)) {
			return;
		}
		if (valueType.equals(TypeUtil.DATAOBJECT)) {
			DataObject rdo = this.pdo.getDataObject(key);
			DataObjectXML doXml = new DataObjectXML(rdo);
			Element element = doXml.getElement();
			valueElement.addAttribute("k", com.sept.util.XMLUtil.encodeXML(key));
			valueElement.add(element);

		} else if (valueType.equals(TypeUtil.DATASTORE)) {
			DataStore rdo = this.pdo.getDataStore(key);
			DataStoreXML dsXml = new DataStoreXML(rdo);
			Element element = dsXml.getElement();
			valueElement.addAttribute("k", com.sept.util.XMLUtil.encodeXML(key));
			valueElement.add(element);
		} else {
			valueElement.addAttribute("k", com.sept.util.XMLUtil.encodeXML(key));
			valueElement.addAttribute("v", com.sept.util.XMLUtil.encodeXML(null == pdo
					.get(key) ? "" : pdo.getString(key)));// 获取string

		}
		this.typeList += key + ":" + valueType + ",";
		if(this.typeList.length() > 0) {
			this.typeList = this.typeList.substring(0, this.typeList.length()-1);
		}

	}

	public Element getElement() throws AppException {
		for (String key : pdo.keySet()) {
			this.addPara(key, pdo.get(key));
		}
		return this.root.addAttribute("tl", com.sept.util.XMLUtil.encodeXML(this.typeList));
	}

	public static void main(String[] args) throws AppException, JSONException {
		DataObject pdo = new DataObject();
		for (int i = 0; i < 10; i++) {
			pdo.put("i" + i, i);
		}
		for (int i = 0; i < 10; i++) {
			pdo.put("s" + i, "s<>" + i);
		}
		for (int i = 0; i < 10; i++) {
			pdo.put("d<>" + i, new Date());
		}
		DataStore ds = new DataStore();
		for (int i = 0; i < 100; i++) {
			ds.addRow();
			ds.put(ds.rowCount() - 1, "xm", "张三<=/:>" + i);
			ds.put(ds.rowCount() - 1, "nl", 10 + i);
		}
		pdo.put("ds", ds);
		System.out.println(new DataObjectXML(pdo).getElement().asXML());
		System.out.println(new DataStoreXML(ds).getElement().asXML());
	}

}
