package com.sept.jui.scroll;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * @author zchar
 *
 */
public class SScrollPane extends JScrollPane {
	private static final long serialVersionUID = 1L;

	public static final int POSITION_LEFT = 0;

	public static final int POSITION_RIGHT = 1;
	private int component_width;
	private int component_height;
	private int between = 5;
	private ArrayList<Component> alComponent;
	private JPanel panel;

	public SScrollPane(int width, int height) {
		super();
		this.component_width = width;
		this.component_height = height;
		this.alComponent = new ArrayList<>();
		panel = new JPanel();
		super.setViewportView(panel);
	}

	@Override
	public Component add(Component comp) {
		//int nowHeight = alComponent.size() * (component_height + between);
		comp.setSize(component_width, component_height);
		this.alComponent.add(comp);
		panel.setLayout(new GridLayout(alComponent.size(), 1, 0, between));

		return this.panel.add(comp);

	}

	@Override
	public void setViewportView(Component view) {
	}

}
