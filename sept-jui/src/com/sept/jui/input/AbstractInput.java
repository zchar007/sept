package com.sept.jui.input;

import javax.swing.JComponent;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JLabel;

public abstract class AbstractInput extends JComponent {

	private JSplitPane splitPane;
	private JLabel label_name;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractInput(String name) {
		setLayout(new BorderLayout(0, 0));

		splitPane = new JSplitPane();
		add(splitPane, BorderLayout.CENTER);

		label_name = new JLabel(name);
		splitPane.setLeftComponent(label_name);

		splitPane.setDividerLocation(0.5);
	}

	abstract String getValue();

	abstract void setValue(Object obj);
}
