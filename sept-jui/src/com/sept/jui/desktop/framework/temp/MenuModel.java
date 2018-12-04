package com.zchar.appFramework.uiCell;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.zchar.appFramework.CompantCell.AppPanel;

public class MenuModel {
	private String MenuName;
	private Class<? extends AppPanel> ActionClass;
	private String ActionMethod;

	public MenuModel(String menuName, String actionMethod) {
		super();
		MenuName = menuName;
		ActionMethod = actionMethod;
	}

	public MenuModel(String menuName, Class<? extends AppPanel> actionClass, String actionMethod) {
		super();
		MenuName = menuName;
		ActionClass = actionClass;
		ActionMethod = actionMethod;
	}

	public String getMenuName() {
		return MenuName;
	}

	public void setMenuName(String menuName) {
		MenuName = menuName;
	}

	public Class<? extends AppPanel> getActionClass() {
		return ActionClass;
	}

	public void setActionClass(Class<? extends AppPanel> class1) {
		ActionClass = class1;
	}

	public String getActionMethod() {
		System.out.println(getActionClass());
		return ActionMethod;
	}

	public void setActionMethod(String actionMethod) {
		ActionMethod = actionMethod;
	}

	public void doActionMethod() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		Method m3 = ActionClass.getMethod(this.ActionMethod);
		m3.invoke(null);
	}

	@Override
	public String toString() {
		return "MenuModel [MenuName=" + MenuName + ", ActionClass=" + ActionClass + ", ActionMethod=" + ActionMethod
				+ "]";
	}

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		// testPanel tPanel = new testPanel();
		// MenuModel menuModel = new MenuModel("aa",tPanel.getClass(),
		// "doThing");
		// menuModel.doActionMethod();
	}

}
