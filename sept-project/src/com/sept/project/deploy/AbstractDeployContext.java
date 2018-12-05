package com.sept.project.deploy;

/**
 * 每种配置文件对应一个Context
 * 
 * @author zchar
 *
 */
public abstract class AbstractDeployContext {
	public AbstractDeployContext() {
		this.defaultParam();
	}

	/** 默认一些参数 **/
	protected abstract void defaultParam();

	/** 修正一些东西，在不配置的时候默认上 **/
	public abstract void init();

	/** 这里其实可以做一个公共的 销毁方法 **/
	public abstract void close();
}
