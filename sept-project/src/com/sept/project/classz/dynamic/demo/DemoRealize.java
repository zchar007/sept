package com.sept.project.classz.dynamic.demo;

public class DemoRealize implements InvokeAble {

	@Override
	public String entry(String message) {
		String[] messages = message.split(",");
		String aStr = "", bStr = "";
		for (int i = 0; i < messages.length; i++) {
			if (messages[i].startsWith("a")) {
				aStr+=messages[i];
			}
			if (messages[i].startsWith("b")) {
				bStr+=messages[i];
			}
		}

		return "A:["+aStr+"]B:["+bStr+"]";
	}
}
