package com.sept.jui.desktop.framework.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sept.jui.desktop.framework.history.Historys;

import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JDesktopPane;

public class AppSFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/** 菜单条 **/
	private JMenuBar jmb_main;
	/** 主菜单 **/
	private JMenu jm_main;

	/** 历史菜单 **/
	private JMenu jm_history;
	private JMenuItem jmi_readhis;
	private JMenu jm_file_his;
	private JMenu jm_his;
	private JMenuItem jmi_his_set;

	/** app工具菜单 **/
	private JMenu jm_app;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppSFrame frame = new AppSFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AppSFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.initMenuBar();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);

		JDesktopPane desktopPane = new JDesktopPane();
		scrollPane.setViewportView(desktopPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setLeftComponent(scrollPane_1);

		JTree tree = new JTree();
		scrollPane_1.setViewportView(tree);
	}

	private void initMenuBar() {
		jmb_main = new JMenuBar();
		this.setJMenuBar(jmb_main);
		/** 主菜单 **/
		jm_main = new JMenu("系统");
		jmb_main.add(jm_main);

		/** 历史菜单 **/
		jm_history = new JMenu("历史");
		jmb_main.add(jm_history);

		jmi_readhis = new JMenuItem("读取历史文件(*.sdata)");
		jm_history.add(jmi_readhis);

		jm_file_his = new JMenu("文件加载历史");
		jm_history.add(jm_file_his);

		jm_his = new JMenu("历史");
		jm_history.add(jm_his);

		jmi_his_set = new JMenuItem("历史数据管理");
		jm_history.add(jmi_his_set);

		/** app工具菜单 **/
		jm_app = new JMenu("工具");
		jmb_main.add(jm_app);

	}

	/**
	 * 添加menu
	 * 
	 * @param menu
	 */
	public void addMenu(JMenu menu) {
		if (null != menu) {
			jmb_main.add(menu);
		}
	}

	/**
	 * 设置某个app特有的方法
	 * 
	 * @param menu
	 */
	public void setAppTools(JMenu menu) {
		jmb_main.remove(jm_app);
		jm_app = menu;
		jmb_main.add(menu);
		jmb_main.validate();
	}

	public void addFileHistory(Historys his) {
		
	}

}
