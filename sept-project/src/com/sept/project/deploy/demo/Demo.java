package com.sept.project.deploy.demo;

import com.sept.exception.AppException;
import com.sept.project.deploy.DeployFactory;
import com.sept.project.deploy.ProjectDeploy;

public class Demo {
	public static void main(String[] args) throws AppException {
		DeployFactory.init();
		ProjectDeploy gcc = DeployFactory.get(ProjectDeploy.class);
		System.out.println(gcc.getProjectname());
	}

}
