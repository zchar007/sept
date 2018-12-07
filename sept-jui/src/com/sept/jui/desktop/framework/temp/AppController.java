package com.sept.jui.desktop.framework.temp;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.zchar.appFramework.CompantCell.AppFrame;
import com.zchar.appFramework.CompantCell.AppPanel;
import com.zchar.appFramework.CompantCell.AppTab;
import com.zchar.appFramework.CompantCell.AppTree;
import com.zchar.appFramework.CompantCell.AppUI;
import com.zchar.appFramework.uiCell.AppModel;
import com.zchar.appFramework.uiCell.MenuModel;
import com.zchar.appFramework.uiCell.RootModel;
import com.zchar.framework.Utils.DataObject;
import com.zchar.framework.Utils.DataStore;
import com.zchar.framework.Utils.XMLUtils;

/**
 * ���ฺ�� 1.Э��tree��panel����ʾ���� 2.��������app�ļ��� 3.����ָ��app�ļ����µ�app
 * 
 * @author zchar
 *
 */
public class AppController {
	private static RootModel rModel;
	private static AppModel root;
	private static ArrayList<AppModel> root_folder = new ArrayList<>();
	private static HashMap<String, ArrayList<AppModel>> root_app = new HashMap<>();
	private static HashMap<String, ArrayList<MenuModel>> app_menus = new HashMap<>();
	private static HashMap<String, AppModel> apps = new HashMap<>();
	public static AppTab tabbedPane;
	public static AppTree appTree;
	private File f_root;
	private static AppFrame frame;

	public AppController(AppFrame fr) {
		frame = fr;
		// ��ʼ�����ڵ�
		f_root = new File("./img/icon.bmp");
		try {
			root = new AppModel("���ڵ�", ImageIO.read(f_root), null, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		tabbedPane = new AppTab();
		appTree = new AppTree();

	}

	public static AppTab getAppTab() {
		return AppController.tabbedPane;
	}

	public static AppTree getAppTree() {
		return AppController.appTree;
	}

	public static RootModel getRootModel() {
		for (int i = 0; i < root_folder.size(); i++) {
			root_folder.get(i).setAppModels(root_app.get(root_folder.get(i).getAppID()));
		}
		// �˷�������дһ��,�Ӽ���������ȡ����Ȼ�����
		rModel = new RootModel(root, root_folder);

		return rModel;
	}

	/**
	 * �ڴ�app��ʱ��ֱ������
	 * 
	 * @param fatherAm
	 * @param am
	 * @return
	 */
	public static boolean openApp(String appid) {

		// �鿴�Ƿ����panel
		// ���appId��ͬ,����Ϊ��ͬһ��,����������
		if (apps.containsKey(appid)) {
			AppPanel appPanel = apps.get(appid).getAppPanel();

			if (null == appPanel) {
				return false;
			}
			// System.out.println(appPanel.appid);
			tabbedPane.addTab(apps.get(appid));
			// ���Ƴ�����menu
			AppUI.clearMenu();
			// ���ò˵���ť
			if (app_menus.containsKey(appid)) {
				AppUI.setMenu(app_menus.get(appid));
			}
			return true;
		}

		return false;

	}

	public static void setRootLook(String name, Image img) {
		if (null != name && !name.trim().isEmpty()) {
			root.setRootName(name);
		}
		if (null != img) {
			root.setImageIcon(img);
		}
	}

	public static boolean addAppFolder(AppModel am) {
		if (!checkFolder(am)) {
			return false;
		}
		// ����ǲ��Ǵ��ڴ�fload
		for (int i = 0; i < root_folder.size(); i++) {
			// ���appId��ͬ,����Ϊ��ͬһ��,����������
			if (root_folder.get(i).getAppID().equals(am.getAppID())) {
				return false;
			}

		}

		root_folder.add(am);
		root_app.put(am.getAppID(), new ArrayList<AppModel>());

		return true;
	}

	private static boolean checkFolder(AppModel am) {
		if (null == am || !am.isFolder()) {
			return false;
		}

		return true;
	}

	public static boolean addAppToFolder(AppModel am, AppModel amFather) {
		if (!checkFolder(amFather)) {
			return false;
		}
		if (!checkApp(am)) {
			return false;
		}

		// ���뵽����app����
		apps.put(am.getAppID(), am);
		// ���뵽app����
		root_app.get(amFather.getAppID()).add(am);

		return true;
	}

	private static boolean checkApp(AppModel am) {
		if (null == am || !am.isApp()) {
			return false;
		}
		// ���appId��ͬ,����Ϊ��ͬһ��,����������
		if (apps.containsKey(am.getAppID())) {
			return false;
		}

		return true;
	}

	public static void setTitle(String string) {
		frame.setTitle(string);
	}

	static {
		File f = null;
		try {
			DataObject vdo = XMLUtils.getXMLToDataObject("./App.xml");
			System.out.println(vdo);
			DataObject vdo_apps = (DataObject) vdo.get("apps");
			DataObject vdo_folders = (DataObject) vdo.get("folders");
			DataObject vdo_folder;
			DataStore vds_apps;
			for (String key : vdo_folders.keySet()) {
				vdo_folder = (DataObject) vdo_folders.get(key);
				vds_apps = (DataStore) vdo_apps.get(key);
				String foldername = vdo_folder.getString("foldername");
				String foldericon = vdo_folder.getString("foldericon");
				//System.out.println(foldericon);
				f = new File(foldericon);
				AppModel folder = new AppModel(foldername, ImageIO.read(f), null, null, null);
				addAppFolder(folder);
				for (int i = 0; i < vds_apps.rowCount(); i++) {
					DataObject vdo_temp = vds_apps.get(i);
					f = new File(vdo_temp.getString("appicon"));
					AppModel appModel = new AppModel(vdo_temp.getString("appname"), ImageIO.read(f), null, folder,
							null);

					Class<?> panel = Class.forName(vdo_temp.getString("apppanel"));

					AppPanel appPanel = (AppPanel) panel.newInstance();
					appPanel.setAppid(appModel.getAppID());
					appModel.setAppPanel(appPanel);
					appPanel.setMenus();
					addAppToFolder(appModel, folder);
				}
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addMenu(String appid, MenuModel menuModel) {
		if (!app_menus.containsKey(appid)) {
			app_menus.put(appid, new ArrayList<MenuModel>());
		}
		// System.out.println(appid);
		app_menus.get(appid).add(menuModel);

	}

	public static String getXMLModel() {
		String xml = "<folders>\n" + "<folder>\n" + "<foldername>�����ļ���</foldername>\n"
				+ "<foldericon>./img/icon.bmp</foldericon>\n" + "<app>\n" + "<appname>MenuTest</appname>\n"
				+ "<appicon>./img/icon.bmp</appicon>\n" + "<apppanel>com.zchar.framework.UIModel.testPanel</apppanel>\n"
				+ "</app>\n" + "<app>\n" + "<appname>���Ƕ�������app</appname>\n" + "<appicon>./img/icon.bmp</appicon>\n"
				+ "<apppanel>com.zchar.framework.UIModel.AppPanel</apppanel>\n" + "</app>\n" + "</folder>\n"
				+ "</folders>";
		return xml;

	}
	
	public static JFrame getJFrameFromComponent(Component component) throws Exception {
		Component component_temp = component.getParent();
		while ((component_temp != null) && !(component_temp instanceof JFrame)) {
			component_temp = component_temp.getParent();
		}
		if (component_temp == null) {
			throw new Exception("������Դû���ҵ�JFrame!!");
		}
		return (JFrame) component_temp;
	}

}
