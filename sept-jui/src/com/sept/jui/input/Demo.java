package com.sept.jui.input;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.sept.exception.AppException;
import com.sept.jui.alert.Alert;

public class Demo extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SInput textField;
	private JButton btnHuo;
	private JButton btnTanchu;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

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
		setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		this.textField = new SInput("文本输入", 0);
		this.textField.setLocation(10, 10);

		this.contentPane.add(this.textField);

		final SInput input = new SInput("颜色输入", 5);
		input.setLocation(10, 50);
		this.contentPane.add(input);

		final SInput inputDate = new SInput("日期输入", SInput.INPUT_DATE, "yyyy-MM");
		inputDate.setLocation(10, 90);
		this.contentPane.add(inputDate);

//		final SInput input_1 = new SInput("多行文本", 1);
//		input_1.setLocation(10, 130);
//		this.contentPane.add(input_1);

		final SInput inputR = new SInput("radio", SInput.INPUT_RADIO, "1:aaa,2:bbb,3:ccc");
		inputR.setBounds(10, 130, 500, 30);
		this.contentPane.add(inputR);
		
		final SInput inputF = new SInput("radio", SInput.INPUT_FILE, "D://", Alert.SelectFileType_DIRECTORIES_ONLY);
		inputF.setBounds(20, 170, 261, 25);
		contentPane.add(inputF);
		this.btnHuo = new JButton("获取");
		
		this.btnHuo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println(Demo.this.textField.get());
					System.out.println(input.get());
					System.out.println(inputDate.get());
					System.out.println(inputR.get());
					System.out.println(inputF.get());
				} catch (AppException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.btnHuo.setBounds(70, 228, 93, 23);
		this.contentPane.add(this.btnHuo);

		this.btnTanchu = new JButton("弹出");
		this.btnTanchu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		this.btnTanchu.setBounds(231, 228, 93, 23);
		this.contentPane.add(this.btnTanchu);

	}
}
