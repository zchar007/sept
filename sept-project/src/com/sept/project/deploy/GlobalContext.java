package com.sept.project.deploy;

public class GlobalContext {
	private String mProjectName = "sept";

	public GlobalContext(String mProjectName) {
		this.mProjectName = mProjectName;
	}

	public String getmProjectName() {
		return mProjectName;
	}

	public void setmProjectName(String mProjectName) {
		this.mProjectName = mProjectName;
	}
}
