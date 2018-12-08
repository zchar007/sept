package com.sept.framework.taglib.sept.form;

public class FormCell extends FormImpl {
	private String labelValue;
	private FormImpl father;

	public FormCell(String labelValue, FormImpl father) {
		this.labelValue = labelValue;
		this.father = father;
	}

	@Override
	public String toString() {
		return "【" + labelValue + "(" + this.getColspan() + ")】";
	}
}
