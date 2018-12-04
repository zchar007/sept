package com.sept.jui.tab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class STabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	private HashSet<STab> hsTabs = new HashSet<>();
	private static final String iconPosition = "  ";

	public STabbedPane() {
		super();
		this.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
 				System.out.println(tabNumber);

				if (tabNumber < 0) {
					return;
				}
				Rectangle rect = ((CloseIcon) getIconAt(tabNumber)).getBounds();
				if (rect.contains(e.getX(), e.getY())) {
					STabbedPane.this.setIconAt(tabNumber, new CloseIcon(Color.RED));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				int tabNumber = STabbedPane.this.getUI().tabForCoordinate(STabbedPane.this, e.getX(), e.getY());
				System.out.println(tabNumber);

				if (tabNumber < 0) {
					return;
				}
				Rectangle rect = ((CloseIcon) getIconAt(tabNumber)).getBounds();
				if (rect.contains(e.getX(), e.getY())) {
					STabbedPane.this.setIconAt(tabNumber, new CloseIcon(Color.BLACK));
				}
			}

			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					// showPopupMenu(e);
				} else {
					int tabNumber = STabbedPane.this.getUI().tabForCoordinate(STabbedPane.this, e.getX(), e.getY());
					if (tabNumber < 0) {
						return;
					}
					Rectangle rect = ((CloseIcon) getIconAt(tabNumber)).getBounds();
					if (rect.contains(e.getX(), e.getY())) {
						// the tab is being closed
						STabbedPane.this.removeTabAt(tabNumber);
					}
				}
			}
		});
	}

	public void removeTabAt(int index) {
		Component component = this.getComponentAt(index);
		hsTabs.remove(component);
		super.removeTabAt(index);
	}

	public void addTab(STab tab) {
		if (this.hsTabs.contains(tab)) {
			this.setSelectedComponent(tab);
		} else {
			this.addTab(tab.getName() + iconPosition, new CloseIcon(), tab);
		}
	}

}
