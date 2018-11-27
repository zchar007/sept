package com.sept.project.classz.loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.sept.exception.AppException;

public class ClassFactory extends ClassLoader {
	private static ClassFactory classFactory = null;
	private static final HashMap<String, Class<?>> hmClassz = new HashMap<>();
	private static final HashMap<String, Object> hmObject = new HashMap<>();

	public static final Object getObject(String classPath, boolean single) throws AppException {
		try {
			if (!single) {// 非单利模式直接new
				return getClassz(classPath).newInstance();
			}

			if (!hmObject.containsKey(classPath) || null == hmObject.get(classPath)) {
				hmObject.put(classPath, getClassz(classPath).newInstance());
			}
			return hmObject.get(classPath);
		} catch (InstantiationException | IllegalAccessException | AppException e) {
			throw new AppException(e);
		}
	}

	public static final Class<?> getClassz(String classPath) throws AppException {
		if (null == classFactory) {
			classFactory = new ClassFactory();
		}
		if (!hmClassz.containsKey(classPath)) {
			Class<?> classz = classFactory.genClass(classPath);
			hmClassz.put(classPath, classz);
		}
		return hmClassz.get(classPath);

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
		Path path = null;
		try {
			path = Paths.get(new File(classPath).toURI());
			cLassBytes = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		cLass = defineClass(null, cLassBytes, 0, cLassBytes.length);

		return cLass;
	}

	private ClassFactory() {
	}
}
