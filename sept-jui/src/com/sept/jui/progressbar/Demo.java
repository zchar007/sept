package com.sept.jui.progressbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Demo extends JFrame {
	private JPanel contentPane;
	private JTextField textField;

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

	public Demo() {
		setDefaultCloseOperation(3);
		setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(this.contentPane);

		final SProgressBarBox progressBarBox = new SProgressBarBox();
		this.contentPane.add(progressBarBox, "South");
		JPanel panel_1 = new JPanel();
		panel_1.setSize(100, 50);
		JButton btnNewButton_1 = new JButton("暂停");
		panel_1.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("查看详情");
		panel_1.add(btnNewButton_2);
		progressBarBox.setEast(panel_1);

		final SProgressBar progressBar = progressBarBox.getProgressBar();
		progressBar.setTotalValue(1000L);
		progressBar.setAutoPercentage(true);
		progressBar.setIndeterminate(false);
		progressBar.setIndeterminateColor(Color.GREEN);

		JPanel panel = new JPanel();
		this.contentPane.add(panel, "Center");
		panel.setLayout(null);

		this.textField = new JTextField();
		this.textField.setBounds(21, 47, 170, 21);
		panel.add(this.textField);
		this.textField.setColumns(10);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				progressBar.setValue(Long.parseLong(Demo.this.textField.getText()));
				progressBarBox.setNorth(progressBar.getString());
				progressBarBox.setSouth(Demo.this.textField.getText());
			}
		});
		btnNewButton.setBounds(241, 46, 93, 23);
		panel.add(btnNewButton);
	}
}
