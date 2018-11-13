package com.sept.jui.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TextUtil {

	/**
	 * 将文本添加到粘贴板
	 * 
	 * @param message
	 */
	public static final void sendStringToClipboard(String message) {
		StringSelection stringSelection = new StringSelection(message);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	/**
	 * 获取粘贴板中的文本
	 * 
	 * @return
	 */
	public static final String getStringFromClipboard() {
		String message = "";
		try {
			Object obj = getObjectFromCipboard();
			message = obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	/**
	 * 获取粘贴板中的Object
	 * 
	 * @return
	 */
	public static final Object getObjectFromCipboard() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable content = clipboard.getContents(Toolkit.class);
		Object obj = null;
		try {
			obj = content.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public static void main(String[] args) {
		sendStringToClipboard("你好2");
		System.out.println(getStringFromClipboard());
	}
}
