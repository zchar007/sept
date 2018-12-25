package com.sept.project.deploy.demo;

import com.sept.exception.AppException;
import com.sept.project.deploy.$;
import com.sept.project.deploy.ProjectDeploy;

public class Demo {
	public static void main(String[] args) throws AppException {
		$.init();
		ProjectDeploy gcc = $.get(ProjectDeploy.class);
		System.out.println(gcc.getProjectname());
	}

}
