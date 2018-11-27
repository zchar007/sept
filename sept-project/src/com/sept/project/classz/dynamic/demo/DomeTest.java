package com.sept.project.classz.dynamic.demo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.sept.project.classz.dynamic.JavaStringCompiler;

public class DomeTest {
	private static final String SINGLE_JAVA = "package com.sept.project.classz.dynamic.demo;\r\n" + "\r\n"
			+ "public class DemoRealize implements InvokeAble {\r\n" + "\r\n" + "	@Override\r\n"
			+ "	public String entry(String message) {\r\n" + "		String[] messages = message.split(\",\");\r\n"
			+ "		String aStr = \"\", bStr = \"\";\r\n" + "		for (int i = 0; i < messages.length; i++) {\r\n"
			+ "			if (messages[i].startsWith(\"a\")) {\r\n" + "				aStr+=messages[i];\r\n"
			+ "			}\r\n" + "			if (messages[i].startsWith(\"b\")) {\r\n"
			+ "				bStr+=messages[i];\r\n" + "			}\r\n" + "		}\r\n" + "\r\n"
			+ "		return \"A:[\"+aStr+\"]B:[\"+bStr+\"]\";\r\n" + "	}\r\n" + "}\r\n";

	public static void main(String[] args)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		JavaStringCompiler compiler = new JavaStringCompiler();
		Map<String, byte[]> results = compiler.compile("DemoRealize.java", SINGLE_JAVA);

		Class<?> clazz = compiler.loadClass("com.itranswarp.compiler.demo.DemoRealize", results);
		Object obj = clazz.newInstance();
		Method entryMethod = clazz.getMethod("entry", String.class);

		String message = "bdd,aaa,baa,abb,acc,add,bbb,bcc";

		String returnMessage = (String) entryMethod.invoke(obj, message);
		System.out.println(returnMessage);
		// System.out.println(results);
	}

}
