package com.sept.jui.progressbar;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SProgressBarBox extends JComponent {
	private SProgressBar progressBar;
	private static final long serialVersionUID = 1L;
	private JComponent component_north;
	private JComponent component_south;
	private JComponent component_west;
	private JComponent component_east;
	private JLabel label_north;
	private JLabel label_south;
	private JLabel label_west;
	private JLabel label_east;

	public SProgressBarBox() {
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout(0, 0));

		this.component_north = new JPanel();
		add(this.component_north, "North");
		this.component_north.setLayout(new BorderLayout(0, 0));

		this.component_south = new JPanel();
		add(this.component_south, "West");
		this.component_south.setLayout(new BorderLayout(0, 0));

		this.component_west = new JPanel();
		add(this.component_west, "South");
		this.component_west.setLayout(new BorderLayout(0, 0));

		this.component_east = new JPanel();
		add(this.component_east, "East");
		this.component_east.setLayout(new BorderLayout(0, 0));

		this.progressBar = new SProgressBar();
		add(this.progressBar, "Center");
	}

	public void setNorth(String text) {
		if (this.label_north == null) {
			this.label_north = new JLabel(text);
			this.label_north.setHorizontalAlignment(2);
			this.component_north.add(this.label_north, "Center");
			revalidate();
		} else {
			this.label_north.setText(text);
		}
	}

	public void setNorth(JComponent component) {
		this.component_north.add(component, "Center");
		revalidate();
	}

	public void setSouth(String text) {
		if (this.label_south == null) {
			this.label_south = new JLabel(text);
			this.label_south.setHorizontalAlignment(4);
			this.component_south.add(this.label_south, "Center");
			revalidate();
		} else {
			this.label_south.setText(text);
		}
	}

	public void setSouth(JComponent component) {
		this.component_south.add(component, "Center");
		revalidate();
	}

	public void setWest(String text) {
		if (this.label_west == null) {
			this.label_west = new JLabel(text);
			this.label_west.setHorizontalAlignment(4);
			this.component_west.add(this.label_west, "Center");
			revalidate();
		} else {
			this.label_west.setText(text);
		}
	}

	public void setWest(JComponent component) {
		this.component_west.add(component, "Center");
		revalidate();
	}

	public void setEast(String text) {
		if (this.label_east == null) {
			this.label_east = new JLabel(text);
			this.label_east.setHorizontalAlignment(2);
			this.component_east.add(this.label_east, "Center");
			revalidate();
		} else {
			this.label_east.setText(text);
		}
	}

	public void setEast(JComponent component) {
		this.component_east.add(component, "Center");
	}

	public SProgressBar getProgressBar() {
		return this.progressBar;
	}

	public void setProgressBar(SProgressBar progressBar) {
		this.progressBar = progressBar;
	}
}
