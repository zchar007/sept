package com.zchar.appFramework.uiCell;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;

import com.zchar.appFramework.CompantCell.AppPanel;

public class AppModel {
	private String rootName;// ����
	private Image imageIcon;// ͼ��
	private AppPanel appPanel;
	private AppModel fatherModel;
	private ArrayList<AppModel> appModels;// ������Լ���app,�򲻴��ڴ���
	private ArrayList<MenuModel> menuModels;
	private String appID;
	private static int index = 0;

	public AppModel(String rootName, Image imageIcon, AppPanel appPanel, AppModel fatherModel,
			ArrayList<AppModel> appModels) {
		super();
		this.rootName = rootName;
		this.imageIcon = imageIcon;
		this.appPanel = appPanel;
		this.fatherModel = fatherModel;
		this.appModels = appModels;
		this.appID = getAppId();
	}

	private String getAppId() {
		Date date = new Date();
		return date.toString() + (index++);
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public Image getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(Image imageIcon) {
		this.imageIcon = imageIcon;
	}

	public AppPanel getAppPanel() {
		return appPanel;
	}

	public void setAppPanel(AppPanel appPanel) {
		this.appPanel = appPanel;
	}

	public AppModel getFatherModel() {
		return fatherModel;
	}

	public void setFatherModel(AppModel fatherModel) {
		this.fatherModel = fatherModel;
	}

	public ArrayList<AppModel> getAppModels() {
		return appModels;
	}

	public void setAppModels(ArrayList<AppModel> appModels) {
		this.appModels = appModels;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public ArrayList<MenuModel> getMenuModels() {
		return menuModels;
	}

	public void addMenuModel(MenuModel menu) {
		if (this.menuModels == null) {
			menuModels = new ArrayList<>();
		}
		menuModels.add(menu);
	
	}

	public void setMenuModels(ArrayList<MenuModel> menuModels) {
		this.menuModels = menuModels;
	}

	// @Override
	// public String toString() {
	// return "AppModel [rootName=" + rootName + ", imageIcon=" + imageIcon + ",
	// appPanel=" + appPanel
	// + ", fatherModel=" + fatherModel + ", appModels=" + appModels + "]";
	// }
	/**
	 * ����Ƿ�����ļ��б�׼ 1.rootName��Ϊ�� 2.
	 * 
	 * @return
	 */
	public boolean isFolder() {
		if (this.rootName.trim().isEmpty() || null == this.rootName) {
			return false;
		}
		if (this.appID.trim().isEmpty() || null == this.appID) {
			return false;
		}
		return true;
	}

	/**
	 * ����Ƿ���������׼ 1.������Ϊ�ļ��� 2.AppPanel��Ϊ��
	 * 
	 * @return
	 */
	public boolean isApp() {
		if (!isFolder()) {
			return false;
		}
		if (this.appPanel == null) {
			return false;
		}
		return true;
	}

}
