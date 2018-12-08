package com.sept.demo.ao;

import com.sept.exception.AppException;
import com.sept.exception.BusinessException;
import com.sept.framework.web.ao.BPO;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;

public class TestBPO extends BPO {

	public DataObject testRedirect(DataObject para) throws Exception {

		System.out.println(para.toJSON());
		StringBuffer sb = new StringBuffer();
		sb.append("select * from bxsys.code_config");
		this.sql.setSql(sb.toString());
		DataStore vds = this.sql.executeQuery();
		sb.setLength(0);
		sb.append("select * from bxsys.code_config where 1 = ?");
		this.sql.setSql(sb.toString());
		this.sql.setInt(1, 1);
		System.out.println(this.sql.getSqlString());
		vds = this.sql.executeQuery();
		vds.filter(" code == WORKER ", true);
		throw new BusinessException("抛出了异常");
		//throw new AppException("抛出了异常");

		//return vds.get(0);
	}

}
