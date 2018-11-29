package com.sept.jui.input;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.sept.exception.AppException;
import com.sept.jui.input.combox.color.SColorPickerCombobox;
import com.sept.jui.input.date.SDateField;
import com.sept.jui.input.radio.SRadio;

public class SInput extends JComponent {
	private static final long serialVersionUID = 1L;
	public static final int INPUT_TEXT_FILED = 0;
	public static final int INPUT_TEXT_AREA = 1;
	public static final int INPUT_DROPDOWN = 2;
	public static final int INPUT_RADIO = 3;
	public static final int INPUT_CHECKBOX = 4;
	public static final int INPUT_COLOR = 5;
	public static final int INPUT_DATE = 6;

	private static final int DEFAULT_WIDTH = 200;
	private static final int DEFAULT_HEIGHT = 25;
	private String title;
	private int input_type;
	private JSplitPane splitPane;
	private JComponent rightComponent;
	private String additionInfo;

	public SInput(String title, int input_type) {
		this.title = title;
		this.input_type = input_type;
		setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		initialize();
	}

	public SInput(String title, int input_type, String additionInfo) {
		this.title = title;
		this.input_type = input_type;
		this.setAddition(additionInfo);
		setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		initialize();
	}

	private void initialize() {
		removeAll();
		revalidate();

		setLayout(new BorderLayout(0, 0));

		this.splitPane = new JSplitPane();
		this.splitPane.setDividerSize(0);
		this.splitPane.setBorder(null);
		this.setOpaque(false);
		this.splitPane.setOpaque(false);
		add(this.splitPane, "Center");
		this.splitPane.setDividerLocation(50);

		JLabel jl_title = new JLabel(this.title);
		this.splitPane.setLeftComponent(jl_title);
		jl_title.setBorder(null);
		jl_title.setHorizontalAlignment(4);
		switch (this.input_type) {
		case INPUT_TEXT_FILED:
			this.rightComponent = new JTextField();
			break;
		case INPUT_TEXT_AREA:
			setSize(new Dimension(getWidth(), getHeight() * 4));
			JScrollPane scrollPane = new JScrollPane();
			this.splitPane.setRightComponent(scrollPane);

			this.rightComponent = new JTextPane();
			this.rightComponent.setPreferredSize(new Dimension(getWidth() * 2, getHeight() * 2));

			scrollPane.setViewportView(this.rightComponent);
			scrollPane.repaint();
			scrollPane.revalidate();
			break;
		case INPUT_DROPDOWN:
			this.rightComponent = new JComboBox<Object>();
			break;
		case INPUT_RADIO:
			this.rightComponent = new SRadio(additionInfo);
			break;
		case INPUT_CHECKBOX:
			break;
		case INPUT_COLOR:
			this.rightComponent = new SColorPickerCombobox();

			break;
		case INPUT_DATE:
			this.rightComponent = new SDateField(this.additionInfo);

			break;
		}
		this.splitPane.setRightComponent(this.rightComponent);
	}

	public Object get() throws AppException {
		if (this.input_type == 0) {
			if ((this.rightComponent == null) && ((this.rightComponent instanceof JTextField))) {
				throw new AppException("程序出现异常，rightComponent为null或不为JTextField,请联系开发者!");
			}
			return ((JTextField) this.rightComponent).getText();
		}
		if (this.input_type == 1) {
			if ((this.rightComponent == null) && ((this.rightComponent instanceof JTextPane))) {
				throw new AppException("程序出现异常，rightComponent为null或不为JTextPane,请联系开发者!");
			}
			return ((JTextPane) this.rightComponent).getText();
		}

		return ((SInputCell) rightComponent).getValue();
	}

	public JComponent getInputComponent() {
		return this.rightComponent;
	}

	public void paint(Graphics g) {
		super.paint(g);
		this.splitPane.setDividerLocation(50);
	}

	public String getAddition() {
		return additionInfo;
	}

	public void setAddition(String additionInfo) {
		this.additionInfo = additionInfo;
	}
}
