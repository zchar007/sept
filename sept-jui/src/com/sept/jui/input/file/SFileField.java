package com.sept.jui.input.file;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import com.sept.exception.AppException;
import com.sept.jui.alert.Alert;
import com.sept.jui.input.SInputCell;

public class SFileField extends JTextField implements SInputCell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SFileField(String defaultPath,int selectType) {
		this.setEditable(false);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String text = Alert.alertSelectFile(defaultPath, selectType);
				SFileField.this.setText(text);
			}
		});
	}

	@Override
	public Object getValue() throws AppException {
		return this.getText();
	}

}
