package com.sept.drop.temp;

import com.sept.drop.DBDeploy;
import com.sept.exception.AppException;
import com.sept.project.deploy.DeployFactory;
import com.sept.project.deploy.ProjectDeploy;

public class Demo {
	public static void main(String[] args) throws AppException {
		DeployFactory.init();
		ProjectDeploy gcc = DeployFactory.get(ProjectDeploy.class);
		System.out.println(gcc.getProjectname());
		DBDeploy dd = DeployFactory.get(DBDeploy.class);
		System.out.println(dd.isDbaDeployInWar());
	}

}
