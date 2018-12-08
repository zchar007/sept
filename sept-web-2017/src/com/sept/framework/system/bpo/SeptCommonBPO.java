package com.sept.framework.system.bpo;

import com.sept.framework.web.ao.BPO;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.data.DataStore;

public class SeptCommonBPO extends BPO {

	/**
	 * 解析路径信息
	 * 
	 * @param para
	 * @return
	 * @throws Exception 
	 */
	public DataObject parsingTrace(DataObject para) throws Exception {
		DataObject rdo = new DataObject();
		//[{"cost":"2","methodName":"public org.springframework.web.servlet.ModelAndView com.sept.demo.logon.PubController.mainPage(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,com.sept.support.model.data.DataObject) throws java.lang.Exception","className":"com.sept.demo.logon.PubController","parentNodeId":"","nodeId":"e85e2add_9336_4c46_8a6f_3f1e472ca039"},{"cost":"0","methodName":"forward:\/sept\/demo\/main\/mainPage.jsp","className":"forward:\/sept\/demo\/main\/mainPage.jsp","parentNodeId":"e85e2add_9336_4c46_8a6f_3f1e472ca039","nodeId":"69b8069c_7527_4327_a416_c844b66315b5"}]

		DataStore dsTrace = para.getDataStore("_tracktext_");
		
		int index = dsTrace.find("parentNodeId isnull ");
		DataStore rds = new DataStore();
		DataObject fristNode = dsTrace.get(index);
		String fristClassName = fristNode.getString("className");
		fristClassName = fristClassName.toUpperCase();
		if(fristClassName.endsWith("BPO")){
			fristNode.put("type", "BPO");
		}else if(fristClassName.endsWith("ACO")){
			fristNode.put("type", "ACO");
		}else if(fristClassName.endsWith("APO")){
			fristNode.put("type", "APO");
		}else if(fristClassName.endsWith("ASO")){
			fristNode.put("type", "ASO");
		}else if(fristClassName.endsWith("CONTROLLER")){
			fristNode.put("type", "Controller");
		}else if(fristClassName.endsWith(".JSP")){
			fristNode.put("type", "JSP");
		}else{
			fristNode.put("type", "NULL");
		}
		fristNode.put("xh", 0);
		rds.addRow(fristNode);
		
		String nodeId = fristNode.getString("nodeId");
		
		int xh = 1, jspindex = -1;
		while((index = dsTrace.find("parentNodeId == "+nodeId)) >= 0){
			DataObject node = dsTrace.get(index);
			String className = node.getString("className");
			className = className.toUpperCase();
			if(className.endsWith("BPO")){
				node.put("type", "BPO");
			}else if(className.endsWith("ACO")){
				node.put("type", "ACO");
			}else if(className.endsWith("APO")){
				node.put("type", "APO");
			}else if(className.endsWith("ASO")){
				node.put("type", "ASO");
			}else if(className.endsWith("CONTROLLER")){
				node.put("type", "Controller");
			}else if(className.endsWith(".JSP")){
				node.put("type", "JSP");
				jspindex = rds.rowCount();
			}else{
				node.put("type", "NULL");
			}
			node.put("xh", xh++);
			rds.addRow(node);
			nodeId = node.getString("nodeId");
		}
		
		for (int i = 0; i < rds.rowCount(); i++) {
			String className = rds.getString(i,"className");
			String methodName = rds.getString(i,"methodName");
			String type = rds.getString(i,"type");
			if("jsp".equals(type.toLowerCase())){
				rds.put(i, "className",className.replaceFirst("forward:", ""));
				rds.put(i, "methodName",methodName.replaceFirst("forward:", ""));
			}else{
				System.out.println(methodName);
				methodName = methodName.split(className)[1].split("\\(")[0].substring(1);
				rds.put(i, "methodName",methodName);

			}
		}
		
		rds.sortASC("xh");
		rdo.put("_tracktext_", rds);
		rdo.put("jspindex", jspindex);
		return rdo;
	}

}
