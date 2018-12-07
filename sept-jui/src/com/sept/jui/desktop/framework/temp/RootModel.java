package com.sept.jui.desktop.framework.temp;

import java.util.ArrayList;

public class RootModel {
	private AppModel root;
	public ArrayList<AppModel> appModels;
	public RootModel(AppModel root, ArrayList<AppModel> appModels) {
		super();
		this.root = root;
		this.appModels = appModels;
	}
	public AppModel getRoot() {
		return root;
	}
	public void setRoot(AppModel root) {
		this.root = root;
	}
	public ArrayList<AppModel> getAppModels() {
		return appModels;
	}
	public void setAppModels(ArrayList<AppModel> appModels) {
		this.appModels = appModels;
	}
	
}
