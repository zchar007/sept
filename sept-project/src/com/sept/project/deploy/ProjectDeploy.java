package com.sept.project.deploy;

public class ProjectDeploy extends AbstractDeployContext {
	private String projectname;

	@Override
	protected void defaultParams() {
		this.projectname = "sept.unknow";
	}

	@Override
	public void init() {
		if (!this.projectname.startsWith("sept.")) {
			this.projectname = "sept." + this.projectname;
		}
	}

	@Override
	public void close() {
		this.projectname = null;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

}
