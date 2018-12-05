package com.sept.db;

import com.sept.project.deploy.AbstractDeployContext;

public class DBDeploy extends AbstractDeployContext {
	private boolean dbaDeployInWar;
	private boolean dbAble;
	private String dbaFileName;
	private String defaultDataSource;
	private String outWarPath;
	private int maxSelectLine;

	@Override
	protected void defaultParam() {
		this.setDbaDeployInWar(true);
		this.setDbaFileName("applicationContext-dba.xml");
	}

	@Override
	public void init() {

	}

	@Override
	public void close() {

	}

	public boolean isDbaDeployInWar() {
		return dbaDeployInWar;
	}

	public void setDbaDeployInWar(boolean dbaDeployInWar) {
		this.dbaDeployInWar = dbaDeployInWar;
	}

	public String getDbaFileName() {
		return dbaFileName;
	}

	public void setDbaFileName(String dbaFileName) {
		this.dbaFileName = dbaFileName;
	}

	public String getDefaultDataSource() {
		return defaultDataSource;
	}

	public void setDefaultDataSource(String defaultDataSource) {
		this.defaultDataSource = defaultDataSource;
	}

	public String getOutWarPath() {
		return outWarPath;
	}

	public void setOutWarPath(String outWarPath) {
		this.outWarPath = outWarPath;
	}

	public boolean isDbAble() {
		return dbAble;
	}

	public void setDbAble(boolean dbAble) {
		this.dbAble = dbAble;
	}

	public int getMaxSelectLine() {
		return maxSelectLine;
	}

	public void setMaxSelectLine(int maxSelectLine) {
		this.maxSelectLine = maxSelectLine;
	}

}
