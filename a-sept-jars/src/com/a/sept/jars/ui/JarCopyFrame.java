package com.a.sept.jars.ui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileFilter;

import com.a.sept.jars.logic.CopyJarUtil;

import javax.swing.JButton;

public class JarCopyFrame {

	private JFrame frame;
	private JTextField tf_classpath_input;
	private JPanel panel_1;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private static JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JarCopyFrame window = new JarCopyFrame();
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
	public JarCopyFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 220);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(2, 1, 0, 0));

		tf_classpath_input = new JTextField();
		panel.add(tf_classpath_input);
		tf_classpath_input.setColumns(10);
		tf_classpath_input.setEditable(false);
		tf_classpath_input.setText("please input <.classpath> file path");
		tf_classpath_input.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File classFile = selectFile();
				if (null == classFile) {
					return;
				}
				tf_classpath_input.setText(classFile.getAbsolutePath());

			}
		});

		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 2, 0, 0));

		btnNewButton = new JButton("reset");
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf_classpath_input.setText("please input <.classpath> file path");

			}
		});
		btnNewButton_1 = new JButton("start");
		panel_1.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNewButton.setEnabled(false);
				try {
					boolean success = CopyJarUtil.copyJars(tf_classpath_input.getText());
					if (success) {
						JOptionPane.showMessageDialog(null, "复制成功", "成功", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "复制失败", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				} finally {
					btnNewButton.setEnabled(true);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
	}

	private File selectFile() {
		JFileChooser jf = new JFileChooser(getWellPath());
		jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileFilter ff = new FileFilter() {
			@Override
			public String getDescription() {
				return "*.classpath";
			}

			@Override
			public boolean accept(File f) {
				String name = f.getName();
				return f.isDirectory() || name.toLowerCase().equalsIgnoreCase(".classpath");
			}
		};
		jf.setFileFilter(ff);
		jf.addChoosableFileFilter(ff);
		jf.showDialog(null, null);

		File f = jf.getSelectedFile();
		if (f != null) {
			return f;
		}
		return null;
	}

	private File getWellPath() {
		File directory = new File("");
		directory = new File(directory.getAbsolutePath());
		return directory.getParentFile();
	}

	public static void appendMessage(String message) {
		textPane.setText(textPane.getText() + "\n" + message);
	}
}
