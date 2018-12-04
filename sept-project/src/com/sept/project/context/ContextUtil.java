package com.sept.project.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.sept.project.context.demo.PBFDataSource;

public class ContextUtil {
	public static void main(String[] args) {
		ApplicationContext applicationContext = getApplicationContext("applicationContext-pbf.xml");
	PBFDataSource pbfDs = (PBFDataSource) applicationContext.getBean("dataSource");
	System.out.println(pbfDs.toString());
	}

	public static final ApplicationContext getApplicationContext(String url) {
		ApplicationContext applicationContext = null;
		try {
			applicationContext = new ClassPathXmlApplicationContext("classpath:" + url);
		} catch (Exception e) {
			e.printStackTrace();
			applicationContext = new FileSystemXmlApplicationContext(url);
		}
		return applicationContext;
	}

	public static final ApplicationContext getApplicationContext(String url, boolean configInWar) {
		ApplicationContext applicationContext = null;
		if (configInWar) {
			applicationContext = new ClassPathXmlApplicationContext("classpath:" + url);
		} else {
			applicationContext = new FileSystemXmlApplicationContext(url);
		}
		return applicationContext;
	}

}
