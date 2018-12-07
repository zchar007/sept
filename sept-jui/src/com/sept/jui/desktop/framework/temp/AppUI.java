package com.sept.jui.desktop.framework.temp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.zchar.appFramework.AppController;
import com.zchar.appFramework.uiCell.MenuModel;


public class AppUI extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JMenu mnNewMenu;

	/**
	 * Create the panel.
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public AppUI(AppFrame frame) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		add(menuBar, BorderLayout.NORTH);
		
		 mnNewMenu = new JMenu("����");
		menuBar.add(mnNewMenu);
		
//		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
//		mnNewMenu.add(mntmNewMenuItem);
//		
//		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
//		mnNewMenu.add(mntmNewMenuItem_1);
//		
//		JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
//		mnNewMenu.add(mntmNewMenuItem_2);
		
//		JMenu mnNewMenu_1 = new JMenu("New menu");
//		menuBar.add(mnNewMenu_1);
		
//		JMenuItem mntmNewMenuItem_3 = new JMenuItem("New menu item");
//		mnNewMenu_1.add(mntmNewMenuItem_3);
//		
//		JMenuItem mntmNewMenuItem_4 = new JMenuItem("New menu item");
//		mnNewMenu_1.add(mntmNewMenuItem_4);
		new AppController(frame);
		splitPane.setLeftComponent(AppController.getAppTree());
		
		splitPane.setRightComponent(AppController.getAppTab());

	}
	
	
	public static void setMenu(ArrayList<MenuModel> menuModels) {
		
		for (int i = 0; i < menuModels.size(); i++) {
			MenuModel menuModel = menuModels.get(i);
			JMenuItem mntmNewMenuItem = new JMenuItem(menuModel.getMenuName());
			
			mntmNewMenuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
						try {
							menuModel.doActionMethod();
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| NoSuchMethodException | SecurityException | IllegalArgumentException
								| InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				
				}
			});
			mnNewMenu.add(mntmNewMenuItem);
		}

	}

	public static void clearMenu() {
		mnNewMenu.removeAll();
	}

	
//	public static void addMenuToIndex(MenuModel menuModel) {
//		JMenuItem mntmNewMenuItem = new JMenuItem(menuModel.getMenuName());
//		mntmNewMenuItem.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					menuModel.doActionMethod();
//				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//						| NoSuchMethodException | SecurityException | IllegalArgumentException
//						| InvocationTargetException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
//		mnNewMenu.add(mntmNewMenuItem);
//
//	}

}
