package com.sept.jui.input.date;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sept.exception.AppException;
import com.sept.util.DateUtil;

public class SDateField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String format;

	/**
	 * Create the panel.
	 */
	public SDateField(String format) {
		this.format = format;
		this.setEditable(false);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {
					DatePanel.showDatePanel(SDateField.this, SDateField.this.format);
				}
			}
		});
	}

	public SDateField() {
		this("yyyy-MM-dd HH:mm:ss");
	}

	public Date getDate() throws AppException {
		return DateUtil.formatStrToDate(this.getText());
	}
}
