package ant.framework.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.sept.exception.AppException;
import com.sept.support.model.data.DataObject;
import com.sept.support.model.file.FileUtil;
import com.sept.support.util.file.FileSecurityByLine;

public class Test {
	public static void main(String[] args) throws AppException, IOException {
		ArrayList<String> als = new ArrayList<String>();
		File file = new File("E:\\\\SEPT_SPACES\\\\SEPT_20171213");
		File[] files = file.listFiles(); 
		for (int i = 0; i < files.length; i++) {
			als.add(files[i].getName());
			DataObject dos = FileUtil.getFilesFormFile(files[i], "java");
			ArrayList<File> filess = (ArrayList<File>) dos.get("files");
			for (int j = 0; j < filess.size(); j++) {
				//E:\SEPT_SPACES\SEPT_20171213\sept\src\ant\framework\support\Test.java
				String path = filess.get(j).getAbsolutePath();
				String name = path.split("src\\\\")[1].replaceAll("\\\\", ".");
				als.add("	"+name);
				
				
			}
			als.add("	");
		}
		FileSecurityByLine.SaveFileByLine(als, "D://test.txt", false);
	}
	
	
	private void getAllFile() {
		
	}

}
