package com.sept.jui.desktop.framework.temp;

import javax.swing.JPanel;

import com.zchar.appFramework.AppController;
import com.zchar.appFramework.uiCell.MenuModel;


public class AppPanel extends JPanel{
	public String appid;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public AppPanel() {

	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void addMenu(MenuModel menuModel) {
		menuModel.setActionClass(this.getClass());
		// ����Ϳ������Լ��� AppModel������
		AppController.addMenu(this.appid, menuModel);
	}

	public void setMenus() {
		
	}
}
