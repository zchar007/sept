package com.sept.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultAttribute;
import org.dom4j.util.XMLErrorHandler;
import org.xml.sax.SAXException;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;

public final class XMLUtil{
	/**
	 * 解析任意XML,主要是用于不确定的xml
	 * 
	 * @param XMLUrl
	 * @return
	 * @throws AppException
	 */
	public final static DataStore getDataStoreFromXML(String XMLUrl) throws AppException {
		DataStore para = new DataStore();

		if (null == XMLUrl || XMLUrl.trim().isEmpty()) {
			throw new AppException("xml的路径不能为空");
		}

		Document document = null;
		try {
			SAXReader reader = new SAXReader();
			URL url = new URL(XMLUrl);
			document = reader.read(url);

			// 获取根目录
			Element root = document.getRootElement();
			for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
				DataObject elementDo = new DataObject();
				Element element = (Element) it.next();
				elementDo = getDataObjectFromElement(element);
				para.addRow(elementDo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return para;

	}

	private static DataObject getDataObjectFromElement(Element element) throws AppException {

		DataObject elementDo = new DataObject();
		String elementName = element.getName();// 标签名
		elementDo.put("ename", elementName);
		// 循环获取标签Attribute
		List<?> attributes = element.attributes();
		for (int i = 0; i < attributes.size(); i++) {
			DefaultAttribute attribute = (DefaultAttribute) attributes.get(i);
			String name = attribute.getName();
			String value = attribute.getValue();
			elementDo.put(name, value);
		}
		// 获取标签体内容，如果还是标签，则递归调用自己
		String text = element.getText();
		DataStore vds = new DataStore();
		if (text.trim().isEmpty()) {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "type", "data");
			DataStore paras = new DataStore();
			for (Iterator<?> it = element.elementIterator(); it.hasNext();) {
				DataObject para = new DataObject();
				Element element2 = (Element) it.next();
				para = getDataObjectFromElement(element2);
				paras.addRow((DataObject) para.clone());

			}
			vds.put(vds.rowCount() - 1, "data", paras);

		} else {
			vds.addRow();
			vds.put(vds.rowCount() - 1, "type", "text");
			vds.put(vds.rowCount() - 1, "text", text);

		}
		elementDo.put("innerContent", vds);
		return elementDo;
	}

	/**
	 * DataStore 或 DataObject toXML生成的xml转回原DataStore, 规范 <d t="ds"> <v t="do">
	 * 顶级,放置ds或do，一般都是do，不写也默认do <v t="" > t:
	 * 
	 * @param para
	 * @return
	 * @throws AppException
	 * @throws DocumentException
	 */
	public final static Object parseData(String para) throws AppException {
		SAXReader reader = new SAXReader();
		InputStream is = new ByteArrayInputStream(para.getBytes());
		try {
			Document document = reader.read(is);
			Element element = document.getRootElement();

			String type = element.attributeValue("t");
			if (Type.DATAOBJECT.equals(type)) {
				return parseDataObject(element);
			} else if (Type.DATASTORE.equals(type)) {
				return parseDataStore(element);
			} else {
				throw new AppException("所需解析的xml必须为DataObject转成或DataStore转成");
			}

		} catch (DocumentException e) {
			throw new AppException(e.getMessage());
		}
	}

	/**
	 * 接收xml字符串，转为DataObject
	 * 
	 * @param xmlStr
	 * @return
	 * @throws AppException
	 */
	public final static DataObject parseDataObject(String xmlStr) throws AppException {
		if (null == xmlStr || xmlStr.trim().isEmpty()) {
			throw new AppException("传入xml字符串为空");
		}
		SAXReader reader = new SAXReader();
		InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
		Document document = null;
		Element element = null;
		try {
			document = reader.read(is);
			element = document.getRootElement();
		} catch (DocumentException e) {
			throw new AppException(e.getMessage());
		}
		return parseDataObject(element);

	}

	/**
	 * 接收xml字符串，转成DataStore
	 * 
	 * @param xmlStr
	 * @return
	 * @throws AppException
	 */
	public final static DataStore parseDataStore(String xmlStr) throws AppException {
		if (null == xmlStr || xmlStr.trim().isEmpty()) {
			throw new AppException("传入xml字符串为空");
		}
		SAXReader reader = new SAXReader();
		InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
		Document document = null;
		Element element = null;
		try {
			document = reader.read(is);
			element = document.getRootElement();
		} catch (DocumentException e) {
			throw new AppException(e.getMessage());
		}
		return parseDataStore(element);
	}

	/**
	 * 接收root的element,转成DataObject
	 * 
	 * @param document
	 * @return
	 * @throws AppException
	 */
	public final static DataObject parseDataObject(Element element) throws AppException {
		DataObject pdo = new DataObject();
		String type = element.attributeValue("t");
		if (!Type.DATAOBJECT.equals(type)) {
			throw new AppException("所要解析的xml不是标准的 DataObject XML");
		}
		for (Iterator<?> it = element.elementIterator(); it.hasNext();) {
			Element element1 = (Element) it.next();
			// DataObject下的元素都有key
			String type1 = element1.attributeValue("t");
			String key = element1.attributeValue("k");
			if (Type.DATAOBJECT.equals(type1)) {
				DataObject pdo2 = parseDataObject(element1);
				pdo.put(key, pdo2);
				continue;
			}
			if (Type.DATASTORE.equals(type1)) {
				DataStore pds2 = parseDataStore(element1);
				pdo.put(key, pds2);
				continue;
			}
			String value = element1.attributeValue("v");
			pdo.put(key, Type.getValueByType(type1, value));
		}
		return pdo;
	}

	/**
	 * 接收root的element,转成DataStore
	 * 
	 * @param document
	 * @return
	 * @throws AppException
	 */
	public final static DataStore parseDataStore(Element element) throws AppException {
		DataStore pds = new DataStore();
		String type = element.attributeValue("t");
		if (!Type.DATASTORE.equals(type)) {
			throw new AppException("所要解析的xml不是标准的 DataStore XML");
		}
		for (Iterator<?> it = element.elementIterator(); it.hasNext();) {
			Element element1 = (Element) it.next();
			// DataObject下的元素都有key
			String type1 = element1.attributeValue("t");
			if (Type.DATAOBJECT.equals(type1)) {
				DataObject pds2 = parseDataObject(element1);
				pds.addRow(pds2);
				continue;
			} else {
				System.err.println("不可能，这个肯定不加上！！ DataStore的元素只能是DataObject");
			}

		}
		return pds;
	}

	/**
	 * 
	 * 方法简介.
	 * <p>方法详述</p>
	 * 
	 * 先放着，验证xml格式的，以后慢慢看看
	 * @param 关键字   说明
	 * @return 关键字   说明
	 * @throws 异常说明   发生条件
	 * @author 张超
	 * @date 创建时间 2017-5-26
	 * @since V1.0
	 */
	public final static void validateXMLByXSD() throws SAXException {
		String xmlFileName = "com/coderli/schema/shurnim.xml";
		String xsdFileName = "com/coderli/schema/shurnim.xsd";
		// 建立schema工厂
		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		// 建立验证文档文件对象，利用此文件对象所封装的文件进行schema验证
		URL schemaFile = XMLUtil.class.getClassLoader()
			.getResource(xsdFileName);
		// 利用schema工厂，接收验证文档文件对象生成Schema对象
		Schema schema = schemaFactory.newSchema(schemaFile);
		// 通过Schema产生针对于此Schema的验证器，利用schenaFile进行验证
		Validator validator = schema.newValidator();
		// 创建默认的XML错误处理器
		XMLErrorHandler errorHandler = new XMLErrorHandler();
		validator.setErrorHandler(errorHandler);
		// 得到验证的数据源
		InputStream xmlStream = XMLUtil.class.getClassLoader()
			.getResourceAsStream(xmlFileName);
		Source source = new StreamSource(xmlStream);
		// 开始验证，成功输出success!!!，失败输出fail
		try {
			validator.validate(source);
			XMLWriter writer = new XMLWriter(OutputFormat.createPrettyPrint());
			// 如果错误信息不为空，说明校验失败，打印错误信息
			if (errorHandler.getErrors().hasContent()) {
				System.out.println("XML文件通过XSD文件校验失败！");
				writer.write(errorHandler.getErrors());
			} else {
				System.out.println("Good! XML文件通过XSD文件校验成功！");
			}
			;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public final static void main(String[] args) throws Exception {
//		System.out.println(getOutDSConfig("./conf/conf.xml", "change").toXML()
//			.asXML());
//
//		System.out.println(createConfigXML("", "test"));

		// DataStore ds =
		// getDataStoreFromXML(XMLUtil.class.getResource("/Configure.xml").toString());

		// System.out.println(ds.toXML().asXML());
		/*
		 * System.out.println(ds); System.out.println(ds.toJSON()); DataStore
		 * dss = JsonUtil.JSONToDataStore(ds.toJSON()); System.out.println(dss);
		 * System.out.println(dss.find("ename == sql"));
		 * System.out.println(dss.getRow(dss.find("ename == tag")));
		 */
		// getConfig("sql");
		// System.out.println(getConfig("sql"));
		/*
		 * DataObject para2 = new DataObject(); DataObject para = new
		 * DataObject(); // para.put("test", new DataStore()); para2.put("test",
		 * para); System.out.println(para2); System.out.println(para2.toJSON());
		 */

		/*
		 * DataStore vds = new DataStore(); for (int i = 0; i < 10; i++) {
		 * vds.addRow(); vds.put(vds.rowCount() - 1, "姓名", "张三" + i);
		 * vds.put(vds.rowCount() - 1, "年龄", i + 10); vds.put(vds.rowCount() -
		 * 1, "出生日期", DateUtil.formatStrToDate("1980090" + i)); DataStore vds2 =
		 * new DataStore(); for (int j = 0; j < 3; j++) { vds2.addRow();
		 * vds2.put(vds2.rowCount() - 1, "学期", 1.2); vds2.put(vds2.rowCount() -
		 * 1, "语文", 100 - j); vds2.put(vds2.rowCount() - 1, "是否及格", false); }
		 * vds.put(vds.rowCount() - 1, "成绩", vds2); DataObject vdo = new
		 * DataObject(); vdo.put("唱歌", true); vdo.put("跳舞", true); vdo.put("飞翔",
		 * true); vds.put(vds.rowCount() - 1, "技能", vdo); }
		 * System.out.println(vds.toXML().asXML()); DataStore vds2 =
		 * parseDataStore(vds.toXML().asXML()); System.out.println(vds);
		 * System.out.println(vds2); System.out.println(vds.expToString());
		 * System.out.println(vds.toJSON());
		 */

	}
}
