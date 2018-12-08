package com.sept.global;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;

public class DeployTools {
	/**
	 * 方法简介.
	 * <p>
	 * 获取配置文件，文件位置自定义，传统形式的一种类型取最先得到的并返回
	 * </p>
	 * 
	 * @param 关键字
	 *            说明
	 * @return 关键字 说明
	 * @throws 异常说明
	 *             发生条件
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	public static DataObject getDeploy(String urls, String deployType)
			throws AppException {
		DataObject pdo = new DataObject();
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(urls);
			Element root = document.getRootElement();
			pdo = getDeploy(root, deployType);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new AppException(e);
		}
		return pdo;

	}

	/**
	 * 方法简介.
	 * <p>
	 * 获取配置文件，文件位置自定义，把获取到的类型相同的全部返回
	 * </p>
	 * 
	 * @param 关键字
	 *            说明
	 * @return 关键字 说明
	 * @throws 异常说明
	 *             发生条件
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	public static DataStore getDSDeploy(String urls, String deployType)
			throws AppException {
		DataStore vds = new DataStore();
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(urls);
			Element root = document.getRootElement();
			vds = getDSDeploy(root, deployType);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new AppException(e);
		}
		return vds;
	}

	/**
	 * 方法简介.
	 * <p>
	 * 获取配置文件，文件位置自定义，把获取到的类型相同的全部返回,每个相同配置的名字（name）作为hashmap的key
	 * </p>
	 * 
	 * @param 关键字
	 *            说明
	 * @return 关键字 说明
	 * @throws 异常说明
	 *             发生条件
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	public static HashMap<String, DataObject> getHMDeploy(String urls,
			String deployType) throws AppException {
		HashMap<String, DataObject> hm = new HashMap<String, DataObject>();
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(urls);
			Element root = document.getRootElement();
			hm = getHMDeploy(root, deployType);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new AppException(e);
		}
		return hm;
	}

	/**
	 * 方法简介.
	 * <p>
	 * 获取所有配置
	 * </p>
	 * 
	 * @param 关键字
	 *            说明
	 * @return 关键字 说明
	 * @throws 异常说明
	 *             发生条件
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	public static HashMap<String, DataObject> getHMDeploy(String urls)
			throws AppException {
		HashMap<String, DataObject> hm = new HashMap<String, DataObject>();
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			document = reader.read(urls);
			Element root = document.getRootElement();
			hm = getAllDeploy(root);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new AppException(e);
		}
		return hm;
	}

	/**
	 * 方法简介.
	 * <p>
	 * 获取默认配置
	 * </p>
	 * 
	 * @param 关键字
	 *            说明
	 * @return 关键字 说明
	 * @throws 异常说明
	 *             发生条件
	 * @author 张超
	 * @date 创建时间 2017-5-27
	 * @since V1.0
	 */
	public static DataObject getDefaultDeploy(String deployType)
			throws AppException {
		DataObject pdo = new DataObject();
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			if (GlobalNames.APP_TYPE == GlobalVars.APP_TYPE_WEB) {
				URL url = DeployTools.class
						.getResource(GlobalNames.DEFAULT_DEPLOY_NAME);
				document = reader.read(url);
			} else if (GlobalNames.APP_TYPE == GlobalVars.APP_TYPE_DESK) {
				document = reader.read(GlobalNames.DEFAULT_DEPLOY_NAME);
			} else {

			}
			Element root = document.getRootElement();

			pdo = getDeploy(root, deployType);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new AppException(e);
		}
		return pdo;

	}

	public static DataStore getDefaultDSDeploy(String deployType)
			throws AppException {
		DataStore vds = new DataStore();
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			if (GlobalNames.APP_TYPE == GlobalVars.APP_TYPE_WEB) {
				URL url = DeployTools.class
						.getResource(GlobalNames.DEFAULT_DEPLOY_NAME);
				document = reader.read(url);
			} else if (GlobalNames.APP_TYPE == GlobalVars.APP_TYPE_DESK) {
				document = reader.read(GlobalNames.DEFAULT_DEPLOY_NAME);
			} else {

			}
			Element root = document.getRootElement();
			vds = getDSDeploy(root, deployType);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new AppException(e);
		}
		return vds;
	}

	public static HashMap<String, DataObject> getDefaultHMDeploy(
			String deployType) throws AppException {
		HashMap<String, DataObject> hm = new HashMap<String, DataObject>();
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			if (GlobalNames.APP_TYPE == GlobalVars.APP_TYPE_WEB) {
				URL url = DeployTools.class
						.getResource(GlobalNames.DEFAULT_DEPLOY_NAME);
				document = reader.read(url);
			} else if (GlobalNames.APP_TYPE == GlobalVars.APP_TYPE_DESK) {
				document = reader.read(GlobalNames.DEFAULT_DEPLOY_NAME);
			} else {

			}
			Element root = document.getRootElement();
			hm = getHMDeploy(root, deployType);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new AppException(e);
		}
		return hm;
	}

	public static HashMap<String, DataObject> getAllDeploy()
			throws AppException {
		HashMap<String, DataObject> hm = new HashMap<String, DataObject>();
		Document document = null;
		SAXReader reader = new SAXReader();
		try {
			if (GlobalNames.APP_TYPE == GlobalVars.APP_TYPE_WEB) {
				URL url = DeployTools.class
						.getResource(GlobalNames.DEFAULT_DEPLOY_NAME);
				document = reader.read(url);
			} else if (GlobalNames.APP_TYPE == GlobalVars.APP_TYPE_DESK) {
				document = reader.read(GlobalNames.DEFAULT_DEPLOY_NAME);
			} else {

			}
			Element root = document.getRootElement();
			hm = getAllDeploy(root);

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new AppException(e);
		}
		return hm;
	}

	/**
	 * 按节点，配置名称获取配置，获取到第一个即停止并返回
	 * 
	 * @param root
	 * @param deployType
	 * @return
	 */
	private static DataObject getDeploy(Element root, String deployType) {
		DataObject pdo = new DataObject();
		try {
			for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
				Element element = (Element) it.next();
				String nowDeployType = element.attributeValue("id");
				if (nowDeployType.toLowerCase()
						.equals(deployType.toLowerCase())) {
					pdo.clear();
					for (Iterator<?> it2 = element.elementIterator(); it2
							.hasNext();) {
						Element element2 = (Element) it2.next();
						String itemName = element2.attributeValue("name");
						String itemValue = element2.attributeValue("value");
						if (!itemValue.equalsIgnoreCase("")) {
							pdo.put(itemName.toUpperCase(), itemValue);
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (pdo.size() == 0) {
			System.err.println("错误:配置文件" + GlobalNames.DEFAULT_DEPLOY_NAME
					+ "中不含[" + deployType + "]的配置信息!!");
		}
		return pdo;
	}

	/**
	 * 按节点，配置名称获取配置，获取所有与当前录入名相同的
	 * 
	 * @param root
	 * @param deployType
	 * @return
	 */
	private static DataStore getDSDeploy(Element root, String deployType) {
		DataStore vds = new DataStore();
		try {
			for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
				Element element = (Element) it.next();
				String nowDeployType = element.attributeValue("id");
				if (nowDeployType.toLowerCase()
						.equals(deployType.toLowerCase())) {
					DataObject pdo = new DataObject();
					pdo.clear();
					for (Iterator<?> it2 = element.elementIterator(); it2
							.hasNext();) {
						Element element2 = (Element) it2.next();
						String itemName = element2.attributeValue("name");
						String itemValue = element2.attributeValue("value");
						if (!itemValue.equalsIgnoreCase("")) {
							pdo.put(itemName.toUpperCase(), itemValue);
						}
					}
					vds.add(pdo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (vds.rowCount() == 0) {
			System.err.println("错误:配置文件" + GlobalNames.DEFAULT_DEPLOY_NAME
					+ "中不含[" + deployType + "]的配置信息!!");
		}
		return vds;
	}

	private static HashMap<String, DataObject> getHMDeploy(Element root,
			String deployType) {
		HashMap<String, DataObject> hm = new HashMap<String, DataObject>();
		try {
			for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
				Element element = (Element) it.next();
				String nowDeployType = element.attributeValue("id");
				String nowDeployName = element.attributeValue("name");
				if (nowDeployType.toLowerCase()
						.equals(deployType.toLowerCase())) {
					DataObject pdo = new DataObject();
					pdo.clear();
					for (Iterator<?> it2 = element.elementIterator(); it2
							.hasNext();) {
						Element element2 = (Element) it2.next();
						String itemName = element2.attributeValue("name");
						String itemValue = element2.attributeValue("value");
						if (!itemValue.equalsIgnoreCase("")) {
							pdo.put(itemName.toUpperCase(), itemValue);
						}
					}
					hm.put(nowDeployName, pdo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (hm.size() == 0) {
			System.err.println("错误:配置文件" + GlobalNames.DEFAULT_DEPLOY_NAME
					+ "中不含[" + deployType + "]的配置信息!!");
		}
		return hm;
	}

	private static HashMap<String, DataObject> getAllDeploy(Element root) {
		HashMap<String, DataObject> hm = new HashMap<String, DataObject>();
		try {
			for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
				Element element = (Element) it.next();
				String nowDeployType = element.attributeValue("type");
				DataObject pdo = new DataObject();
				pdo.clear();
				for (Iterator<?> it2 = element.elementIterator(); it2.hasNext();) {
					Element element2 = (Element) it2.next();
					String itemName = element2.attributeValue("name");
					String itemValue = element2.attributeValue("value");
					if (!itemValue.equalsIgnoreCase("")) {
						pdo.put(itemName.toUpperCase(), itemValue);
					}

					hm.put(nowDeployType, pdo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (hm.size() == 0) {
			System.err.println("错误:获取配置文件" + GlobalNames.DEFAULT_DEPLOY_NAME
					+ "出错!!");
		}
		return hm;
	}

	// ///////////////////////////////////////////////////////////////////////////////
	// //////////////////以下是写配置文件的方法//////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////
	// 增加
	public static void newDeploy() {

	}

	// 追加内容
	public static void appendDeploy() {

	}

	// 修改内容
	public static void modifyDeploy() {

	}

	public static void main(String[] args) throws AppException {
		System.out.println(getDefaultHMDeploy("test2"));

	}

}
