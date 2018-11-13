
package com.sept.jui.input.combox.date;


import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ColorPickerDemo extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4150278471112398901L;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setContentPane(new ColorPickerDemo());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}


	public ColorPickerDemo() {

		JComboBox<?> comboBox = new SDatePickerCombobox("yyyy-MM-dd hh:mm:ss");
		this.add(comboBox);
	}
}
