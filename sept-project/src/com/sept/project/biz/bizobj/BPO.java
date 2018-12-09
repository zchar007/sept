package com.sept.project.biz.bizobj;

import com.sept.project.biz.BizQueryEntry;

/**
 * 
 * @description 表单数据处理层，作用：1.查询数据。2.调用ACO检查数据。3.调用ASO记账。4.表单数据处理
 *
 * @author zchar.2018年12月1日上午12:00:43
 */
public class BPO extends BizQueryEntry {

	protected BPO newBPO(Class<? extends BPO> classz) {
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
}
