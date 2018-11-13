package com.sept.jui.input.combox.date;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.Date;

import javax.swing.JTextField;

import com.sept.util.DateUtil;

public class SDateTextField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date date;
	private String dateFormat;
	private int leftGap = 2;

	/**
	 * Create the frame.
	 */
	public SDateTextField(String dateFormat) {
		this.setEditable(false);
		this.dateFormat = dateFormat;

	}

	@Override
	public Insets getInsets() {
		Insets insets = super.getInsets();
		insets.left += leftGap;
		insets.right += 2;
		return insets;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(100, 20);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
		if (null != this.date) {
			this.setText(DateUtil.formatDate(this.date, dateFormat));
		}
	}
}
