package com.sept.support.classz;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sept.exception.AppException;

public class ClassLoader extends java.lang.ClassLoader{
	private static ClassLoader classLoader = null;

	private ClassLoader() {}

	public static Class<?> getClassz(String classPath) throws AppException {
		if (null == classLoader) {
			classLoader = new ClassLoader();
		}
		return classLoader.genClass(classPath);
	}

	private Class<?> genClass(String classPath) throws AppException {

		try {
			return this.findClass(classPath);
		} catch (ClassNotFoundException e) {
			throw new AppException(e);
		}

	}

	@Override
	protected Class<?> findClass(String classPath) throws ClassNotFoundException {
		byte[] cLassBytes = null;
		Class<?> cLass = null;
		try {
			Path path = Paths.get(new URI("file:///" + classPath));
			cLassBytes = Files.readAllBytes(path);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		cLass = defineClass(null, cLassBytes, 0, cLassBytes.length);

		return cLass;
	}

	public static void main(String[] args) throws Exception {
//		ClassStepModel classStepModel = (ClassStepModel) ClassLoader.getClassz("D://SqlColumnsFormatModel.class").newInstance();
//		System.out.println(classStepModel.doStep());
	}
}
