package com.sept.framework.taglib.sept.form;

public class test {
	public static void main(String[] args) {
		Form form = new Form("你好");
		form.setColspan(6);
		FormCell fc = new FormCell("年龄", form);
		fc.setColspan(2);
	}
}
