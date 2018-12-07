package com.sept.jui.desktop.framework.temp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;

import com.zchar.appFramework.AppController;
import com.zchar.appFramework.uiCell.AppModel;

/**
 * A JTabbedPane which has a close ('X') icon on each tab. To add a tab, use the
 * method addTab(String, Component) To have an extra icon on each tab (e.g. like
 * in JBuilder, showing the file type) use the method addTab(String, Component,
 * Icon). Only clicking the 'X' closes the tab.
 */
public class AppTab extends JTabbedPane implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double scaleRatio = 0.3;
	private HashMap<String, Component> maps = new HashMap<String, Component>();
	
	private HashMap<String, AppPanel> hmS = new HashMap<>();//appid   
	private HashMap<AppPanel, String> hmP = new HashMap<>();
	
	private HashMap<String, AppModel> hmApp = new HashMap<>();

	public AppTab() {
		super();
		addMouseListener(this);
	}

	public void addTab(AppModel am) {
		AppController.setTitle(am.getFatherModel().getRootName() + "--" + am.getRootName());
		if (hmS.containsKey(am.getAppID())) {
			this.setSelectedComponent(am.getAppPanel());
		} else {
			hmS.put(am.getAppID(), am.getAppPanel());
			hmP.put(am.getAppPanel(), am.getAppID());
			hmApp.put(am.getAppID(), am);
			this.addTab(am.getRootName(), am.getAppPanel());
			this.setSelectedComponent(am.getAppPanel());

		}

	}

	public void addTab(String title, Component component) {
		this.addTab(title, component, null);

	}

	public void addTab(String title, Component component, Icon extraIcon) {
		super.addTab(title, new CloseTabIcon(extraIcon), component);

	}

	public void insertTab(String title, Icon icon, Component component, String tip, int index) {
		tip = "tab" + component.hashCode();
		maps.put(tip, component);
		super.insertTab(title, icon, component, tip, index);
	}

	public void removeTabAt(int index) {

		Component component = getComponentAt(index);
		hmS.remove(hmP.remove(component));
		maps.remove("tab" + component.hashCode());
		super.removeTabAt(index);
		//�ر�֮�������һ���������һ��
		AppModel AM = hmApp.get(hmP.get(this.getComponent(index)));
		AppController.setTitle(AM.getFatherModel().getRootName() + "--" + AM.getRootName());
		AppController.openApp(AM.getAppID());
		
		
	}

	public JToolTip createToolTip() {
		ImageToolTip tooltip = new ImageToolTip();
		tooltip.setComponent(this);
		return tooltip;
	}

	public JToolTip isHaveTab() {
		ImageToolTip tooltip = new ImageToolTip();
		tooltip.setComponent(this);
		return tooltip;
	}

	class ImageToolTip extends JToolTip {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Dimension getPreferredSize() {
			String tip = getTipText();
			Component component = maps.get(tip);
			if (component != null) {
				return new Dimension((int) (getScaleRatio() * component.getWidth()),
						(int) (getScaleRatio() * component.getHeight()));
			} else {
				return super.getPreferredSize();
			}
		}

		public void paintComponent(Graphics g) {
			String tip = getTipText();
			Component component = maps.get(tip);
			if (component instanceof JComponent) {
				JComponent jcomponent = (JComponent) component;
				Graphics2D g2d = (Graphics2D) g;
				AffineTransform at = g2d.getTransform();
				g2d.transform(AffineTransform.getScaleInstance(getScaleRatio(), getScaleRatio()));
				ArrayList<JComponent> dbcomponents = new ArrayList<JComponent>();
				updateDoubleBuffered(jcomponent, dbcomponents);
				jcomponent.paint(g);
				resetDoubleBuffered(dbcomponents);
				g2d.setTransform(at);
			}
		}

		private void updateDoubleBuffered(JComponent component, ArrayList<JComponent> dbcomponents) {
			if (component.isDoubleBuffered()) {
				dbcomponents.add(component);
				component.setDoubleBuffered(false);
			}
			for (int i = 0; i < component.getComponentCount(); i++) {
				Component c = component.getComponent(i);
				if (c instanceof JComponent) {
					updateDoubleBuffered((JComponent) c, dbcomponents);
				}
			}
		}

		private void resetDoubleBuffered(ArrayList<JComponent> dbcomponents) {
			for (JComponent component : dbcomponents) {
				component.setDoubleBuffered(true);
			}
		}
	}

	public double getScaleRatio() {
		return scaleRatio;
	}

	public void setScaleRatio(double scaleRatio) {
		this.scaleRatio = scaleRatio;
	}

	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			showPopupMenu(e);
		} else {
			int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
			//System.out.println(tabNumber);
			if (tabNumber < 0) {
				return;
			}
			// AppModel AM = hmS.get(this.getTitleAt(tabNumber));
			AppModel AM = hmApp.get(hmP.get(this.getComponent(tabNumber)));
			AppController.setTitle(AM.getFatherModel().getRootName() + "--" + AM.getRootName());
			AppController.openApp(AM.getAppID());
			Rectangle rect = ((CloseTabIcon) getIconAt(tabNumber)).getBounds();
			if (rect.contains(e.getX(), e.getY())) {
				// the tab is being closed
				this.removeTabAt(tabNumber);
			}
		}
	}

	private void showPopupMenu(final MouseEvent event) {

		// �����ǰ�¼����Ҽ��˵��йأ������Ҽ������򵯳��˵�
		// if (event.isPopupTrigger()) {
		final int index = ((AppTab) event.getComponent()).getUI().tabForCoordinate(this, event.getX(),
				event.getY());
		final int count = ((AppTab) event.getComponent()).getTabCount();

		JPopupMenu pop = new JPopupMenu();

		JMenuItem closeAll = new JMenuItem("�ر����б�ǩ");
		closeAll.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				for (int j = (count - 1); j > -1; j--) {
					((AppTab) event.getComponent()).removeTabAt(j);
				}
			}
		});
		pop.add(closeAll);
		if (index != -1) {
			JMenuItem closeCurrent = new JMenuItem("�رյ�ǰ");
			closeCurrent.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					((AppTab) event.getComponent()).removeTabAt(index);
				}
			});
			pop.add(closeCurrent);

			JMenuItem closeLeft = new JMenuItem("�ر�����ǩ");
			closeLeft.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					for (int j = (index - 1); j >= 0; j--) {
						((AppTab) event.getComponent()).removeTabAt(j);
					}
				}
			});
			pop.add(closeLeft);

			JMenuItem closeRight = new JMenuItem("�ر��Ҳ��ǩ");
			closeRight.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					for (int j = (count - 1); j > index; j--) {
						((AppTab) event.getComponent()).removeTabAt(j);
					}
				}
			});
			pop.add(closeRight);
		}

		pop.show(event.getComponent(), event.getX(), event.getY());
		// }
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}

/**
 * The class which generates the 'X' icon for the tabs. The constructor accepts
 * an icon which is extra to the 'X' icon, so you can have tabs like in
 * JBuilder. This value is null if no extra icon is required.
 */
class CloseTabIcon implements Icon {
	private int x_pos;
	private int y_pos;
	private int width;
	private int height;
	private Icon fileIcon;

	public CloseTabIcon(Icon fileIcon) {
		this.fileIcon = fileIcon;
		width = 16;
		height = 16;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		this.x_pos = x;
		this.y_pos = y;
		Color col = g.getColor();
		g.setColor(Color.black);
		int y_p = y + 2;
		g.drawLine(x + 1, y_p, x + 12, y_p);
		g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
		g.drawLine(x, y_p + 1, x, y_p + 12);
		g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
		g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
		g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
		g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
		g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
		g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
		g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);

		g.setColor(col);
		if (fileIcon != null) {
			fileIcon.paintIcon(c, g, x + width, y_p);
		}
	}

	public int getIconWidth() {
		return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
	}

	public int getIconHeight() {
		return height;
	}

	public Rectangle getBounds() {
		return new Rectangle(x_pos, y_pos, width, height);
	}
	/*
	 * public static void main(String args[]) { try {
	 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
	 * catch (Exception e) { e.printStackTrace(); } JClosableTabbedPane pane =
	 * new JClosableTabbedPane(); ImageIcon icon = new
	 * ImageIcon("images/middle.jpg"); pane.addTab("tab1",new JButton(
	 * "first Button"),icon); pane.addTab("tab2",new JButton("sec Button"
	 * ),icon); pane.addTab("tab3",new JButton("third Button"),icon);
	 * pane.addTab("tab4",new JButton("fourth Button"),icon); JFrame frame = new
	 * JFrame("Demo"); frame.getContentPane().add(pane,BorderLayout.CENTER);
	 * frame.setSize(500,300); frame.setLocation(300,200); frame.show();
	 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); }
	 */
}