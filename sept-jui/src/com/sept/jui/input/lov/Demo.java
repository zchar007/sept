package com.sept.jui.input.lov;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.sept.exception.AppException;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;

public class Demo extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo frame = new Demo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws AppException
	 */
	public Demo() throws AppException {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textField = new LovField();
		textField.setBounds(99, 87, 66, 21);
		panel.add(textField);
		textField.setColumns(10);
	}

}
