package com.sept.jui.scroll;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

public class demo {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					demo window = new demo();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public demo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SScrollPane scrollPane = new SScrollPane(100, 100);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		JPanel panel1 = new JPanel();
		panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel1.setBackground(Color.RED);
		scrollPane.add(panel1);
		JPanel panel2 = new JPanel();
		panel2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel2.setBackground(Color.RED);
		scrollPane.add(panel2);
		JPanel panel3 = new JPanel();
		panel3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel3.setBackground(Color.RED);
		scrollPane.add(panel3);
		for (int i = 0; i < 10; i++) {
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			panel.setBackground(Color.RED);
			scrollPane.add(panel);
		}
	}

}
