package com.sept.web.taglib;


import com.sept.web.taglib.common.body.BodyTag;

public class TagTest {
	public static void main(String[] args) throws Exception {
		BodyTag bt = new BodyTag();
		bt.doStartTag();
		bt.setName("张三");
		//bt.setFit("true");
		bt.doEndTag();
		
		BodyTag bt2 = new BodyTag();
		bt2.doStartTag();
		bt2.setStyle("默认风格");
		bt2.doEndTag();
		
		System.out.println(bt.getAttributes());
		System.out.println(bt2.getAttributes());
	}
}
