package com.sept.framework.taglib.sept.form;

public class Form extends FormImpl {
	private String title;

	public Form(String title) {
		super();
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
