package com.sept.jui.input.date;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class DemoFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					DemoFrame frame = new DemoFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DemoFrame() {
		setDefaultCloseOperation(3);
		setBounds(100, 100, 969, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		this.textField = new SDateField();
		this.textField.setBounds(37, 39, 120, 21);
		this.contentPane.add(this.textField);
//		this.textField.setColumns(10);
//		this.textField.setEditable(false);
//
//		this.textField.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				if (e.getButton() == 1) {
//					DatePanel.showDatePanel(DemoFrame.this.contentPane, DemoFrame.this.textField,
//							"yyyy-MM-dd HH:mm:ss");
//				}
//			}
//		});
	}
}
