package com.sept.frametools;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataStore;
import com.sept.support.util.file.FileSecurityByLine;

public class TagUtils {
	public static void main(String[] args) throws Exception {
		String java = getTag("Grid",getInfo("./Grid"));
		System.out.println(java);
	}

	private static String getTag(String tagName, DataStore ds) throws AppException {

		String initStr = "", setGetStr = "", functionStr = "", setFunctionStr = "";

		for (int i = 0; i < ds.size(); i++) {
			String name = ds.getString(i, "name");
			String type = ds.getString(i, "type");
			String deciption = ds.getString(i, "deciption");
			if ("string".equals(type)) {
				initStr += "//" + deciption + "\n" + "private String " + name + " = null;\n";
				setGetStr += "public void set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "(String "
						+ name + ") {\nthis." + name + " = " + name + ";\n}\npublic String get"
						+ name.substring(0, 1).toUpperCase() + name.substring(1) + "() {\nreturn this." + name
						+ ";\n}\n";
			} else if ("boolean".equals(type)) {
				initStr += "//" + deciption + "\n" + "private boolean " + name + ";\n";
				setGetStr += "public void set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "(boolean "
						+ name + ") {\nthis." + name + " = " + name + ";\n}\npublic boolean get"
						+ name.substring(0, 1).toUpperCase() + name.substring(1) + "() {\nreturn this." + name
						+ ";\n}\n";

			} else if ("array".equals(type)) {
				initStr += "//" + deciption + "\n" + "private String " + name + " = null;\n";
				setGetStr += "public void set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "(String "
						+ name + ") {\nthis." + name + " = " + name + ";\n}\npublic String get"
						+ name.substring(0, 1).toUpperCase() + name.substring(1) + "() {\nreturn this." + name
						+ ";\n}\n";
			} else if ("function".equals(type)) {
				String inpara = ds.getString(i, "inpara");

				initStr += "//" + deciption + "\n" + "private String " + name + " = null;\n";
				setGetStr += "public void set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "(String "
						+ name + ") {\nthis." + name + " = " + name + ";\n}\npublic String get"
						+ name.substring(0, 1).toUpperCase() + name.substring(1) + "() {\nreturn this." + name
						+ ";\n}\n";
				functionStr += "this.setFunctionPara(\"" + name + "\");\n";
				setFunctionStr += "if (null != this.get" + name.substring(0, 1).toUpperCase() + name.substring(1)
						+ "() && !this.get" + name.substring(0, 1).toUpperCase() + name.substring(1)
						+ "().trim().isEmpty()) {\nthis.set" + name.substring(0, 1).toUpperCase() + name.substring(1)
						+ "(\"function(){\" + this.get" + name.substring(0, 1).toUpperCase() + name.substring(1)
						+ "() + \"(" + inpara + ")}\");}\n";
			} else if ("object".equals(type)) {
				initStr += "//" + deciption + "\n" + "private String " + name + " = null;\n";
				setGetStr += "public void set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "(String "
						+ name + ") {\nthis." + name + " = " + name + ";\n}\npublic String get"
						+ name.substring(0, 1).toUpperCase() + name.substring(1) + "() {\nreturn this." + name
						+ ";\n}\n";
			} else {
				initStr += "//" + deciption + "\n" + "private String " + name + " = null;\n";
				setGetStr += "public void set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "(String "
						+ name + ") {\nthis." + name + " = " + name + ";\n}\npublic String get"
						+ name.substring(0, 1).toUpperCase() + name.substring(1) + "() {\nreturn this." + name
						+ ";\n}\n";
			}
		}

		StringBuffer sqlBF = new StringBuffer();
		sqlBF.setLength(0);
		sqlBF.append("package com.sept.framework.taglib.sept." + tagName.toLowerCase() + ";\n");
		sqlBF.append("import javax.servlet.jsp.JspException;\n");
		sqlBF.append("import com.sept.framework.taglib.sept.AbstractTag;\n");
		sqlBF.append("public class " + tagName + "Tag extends AbstractTag{\n");
		sqlBF.append("  private static final long serialVersionUID = 1L;\n");

		sqlBF.append(initStr);
		sqlBF.append("  @Override\n");
		sqlBF.append("  public void init() throws JspException {\n");
		sqlBF.append("    this.impl = new " + tagName + "Impl();\n");
		sqlBF.append(functionStr);
		sqlBF.append("  }\n");
		sqlBF.append("  @Override\n");
		sqlBF.append("  public String checkBeforeSetParams() throws JspException {\n");
		sqlBF.append("    if (null == this.getParent()) {\n");
		sqlBF.append("    this.jspException(\"" + tagName + "标签必须含有父标签！\");\n");
		sqlBF.append("    }\n");
		sqlBF.append(setFunctionStr);
		sqlBF.append("    return null;\n");
		sqlBF.append("  }\n");
		sqlBF.append("");
		sqlBF.append("  @Override\n");
		sqlBF.append("  public String checkAfterSetParams() throws JspException {\n");
		sqlBF.append("    return null;\n");
		sqlBF.append("  }\n");
		sqlBF.append(setGetStr);
		sqlBF.append("}");

		return sqlBF.toString();
	}

	private static DataStore getInfo(String url) throws Exception {
		FileSecurityByLine fsb = new FileSecurityByLine(url);
		ArrayList<String> al = fsb.getArrayList();

		DataStore ds = new DataStore();
		for (int i = 0; i < al.size(); i++) {
			ds.addRow();
			ds.put(ds.rowCount() - 1, "name", al.get(i).trim());
			i++;
			ds.put(ds.rowCount() - 1, "type", al.get(i).trim());
			if ("function".equals(al.get(i).trim())) {
				i++;
				ds.put(ds.rowCount() - 1, "inpara", al.get(i).trim());
			}
			i++;
			ds.put(ds.rowCount() - 1, "deciption", al.get(i).trim());
		}
		return ds;
	}

}
