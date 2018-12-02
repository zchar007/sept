package com.sept.project.deploy;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sept.exception.AppException;

/**
 * 每种配置文件对应一个Context
 * 
 * @author zchar
 *
 */
public abstract class Context {
	private HashMap<String, String> contextMap = new HashMap<>();

	/** 获取配置信息 */
	public String get(String key) throws AppException {
		return this.contextMap.get(key);
	}

	/** 初始化，可通过重写来改变逻辑 **/
	/*****
	 * 读取配置信息
	 * 
	 *****/
	protected HashMap<String, String> readXML(String sourceName) {
		Document document = null;
		HashMap<String, String> hmParas = new HashMap<String, String>();
		try {
			SAXReader reader = new SAXReader();
			URL url = this.getClass().getResource(sourceName);
			document = reader.read(url);
		} catch (Exception e) {
			LogHandler.logException(sourceName + "不存在，无法加载", e);
		}
		try {
			Element root = document.getRootElement();
			for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
				Element element = (Element) it.next();
				String itemName = element.attributeValue("name");
				String itemValue = element.attributeValue("value");
				hmParas.put(itemName, itemValue);
			}

		} catch (AppException e) {
			LogHandler.logException(sourceName + ":" + e.getMessage(), e);
		}
		return hmParas;
	}

}
