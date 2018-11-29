package com.sept.jui.themes;

import java.awt.Component;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sept.exception.AppException;

/**
 * 缺包
 * 
 * @author zchar
 *
 */
public class Themes {
	public static final String THEME_WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";// WINDOWS
	public static final String THEME_NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";// Nimbus风格，新出来的外观，jdk6
																									// update10版本以后的才会出现
	public static final String THEME_SYSTEM = UIManager.getSystemLookAndFeelClassName();// 系统风格
	public static final String THEME_MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";// Motif风格，外观接近windows经典，但宽宽大大，而且不是黑灰主色，而是蓝黑
	public static final String THEME_JAVA = UIManager.getCrossPlatformLookAndFeelClassName();// 跨平台JAVA
	public static final String THEME_WINDOWS_2 = "javax.swing.plaf.windows.WindowsLookAndFeel";// WINDOWS
	public static final String THEME_JAVA_2 = "javax.swing.plaf.metal.MetalLookAndFeel";// JAVA
	public static final String THEME_MAC = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
	public static final String THEME_MAC_2 = "com.apple.mrj.swing.MacLookAndFeel";// MAC

	public static final void setTheme(String classz) throws AppException {
		try {
			UIManager.setLookAndFeel(classz);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			throw new AppException(e);
		}
	}

	public static final void update(Component component) {
		SwingUtilities.updateComponentTreeUI(component);// 不更新是看不到效果的

	}

}
