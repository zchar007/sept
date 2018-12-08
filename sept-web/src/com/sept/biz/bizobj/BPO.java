package com.sept.biz.bizobj;

import com.sept.biz.BizEntry;
import com.sept.database.db.Sql;

/**
 * 
 * @description 表单数据处理层，作用：1.查询数据。2.调用ACO检查数据。3.调用ASO记账。4.表单数据处理
 *
 * @author zchar.2018年12月1日上午12:00:43
 */
public class BPO extends BizEntry {
	public BPO() {
		/** 只允许执行查询的sql **/
		this.sql = new Sql();
	}

}
