package com.sept.framework.web.ao;

/**
 * BPO只允许调用ASO，ACO
 * 
 * @author zchar
 * 
 */
public class BPO extends AbstractSupport {

	protected ASO newASO(Class<? extends ASO> classz) {
		try {
			return classz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected ACO newACO(Class<? extends ACO> classz) {
		try {
			return classz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
