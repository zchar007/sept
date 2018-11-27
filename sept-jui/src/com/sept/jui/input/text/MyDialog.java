package com.sept.jui.input.text;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MyDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		Map<String, String> caipinMap = new HashMap();
		String yxrsKey = "diyidaocai";
		String yxrsVal = "1";
		String djjdKey = "dierdaocai";
		String djjdVal = "65";
		String fqjdKey = "disandaocai";
		String fqjdVal = "123";
		String abcdKey = "disidaocai";
		String abcdVal = "7";
		caipinMap.put(yxrsKey, yxrsVal);
		caipinMap.put(djjdKey, djjdVal);
		caipinMap.put(fqjdKey, fqjdVal);
		caipinMap.put(abcdKey, abcdVal);

		JFrame alertFrame = new JFrame();
		alertFrame.setVisible(true);
		MyDialog d = new MyDialog(alertFrame, true, caipinMap, 360, 320);
		d.setVisible(true);
	}

	JButton okBtn = new JButton("确认");
	JButton cancelBtn = new JButton("关闭");
	int x = 50;
	int y = 30;
	int width = 65;
	int height = 20;

	public MyDialog(JFrame parent, boolean modal, Map<String, String> caipinMap, int windowWidth, int windowHeight) {
		super(parent, modal);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		JButton[] addBtns = new JButton[caipinMap.keySet().size()];
		JButton[] mutBtns = new JButton[caipinMap.keySet().size()];
		int btnIndex = 0;
		setTitle("test");
		setSize(windowWidth, windowHeight);
		setLayout(null);
		setResizable(false);

		setLocation((screenWidth - getWidth()) / 2, (screenHeight - getHeight()) / 2);
		for (String strKey : caipinMap.keySet()) {
			JLabel cpNameLBL = new JLabel(strKey + ":");
			add(cpNameLBL);
			cpNameLBL.setBounds(this.x, this.y, this.width, this.height);
			cpNameLBL.setName("lbl" + strKey);

			JLabel noticeLbl = new JLabel();
			noticeLbl.setName("lbl" + strKey);
			noticeLbl.setBounds(this.x + 120, this.y, this.width, this.height);

			JTextField cpCountJTF = new JTextField((String) caipinMap.get(strKey));
			add(cpCountJTF);
			cpCountJTF.setName("jtf" + strKey);
			cpCountJTF.setBounds(this.x + 160, this.y, this.width, this.height);
			cpCountJTF.requestFocus();

			cpCountJTF.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {
					JTextField jtf = (JTextField) e.getSource();
					String selectVal = jtf.getSelectedText();
					int keyChar = e.getKeyChar();
					String val = jtf.getText();
					if ((val == null) || (val.trim().length() <= 0) || (val.replaceAll("0", "").length() <= 0)) {
						jtf.setText("1");
						e.consume();
						return;
					}
					int valLength = val.length();
					if ((selectVal != null) && (selectVal.length() == valLength) && (keyChar == 48)) {
						jtf.setText("1");
						e.consume();
						return;
					}
					if ((keyChar >= 48) && (keyChar <= 57)) {
						if ((valLength > 2) && (keyChar != 8)
								&& ((selectVal == null) || (selectVal.trim().length() <= 0))) {
							e.consume();
						}
						return;
					}
					e.consume();
				}

				public void keyReleased(KeyEvent e) {
				}

				public void keyPressed(KeyEvent e) {
				}
			});
			addBtns[btnIndex] = new JButton("+");
			addBtns[btnIndex].setName("btn" + strKey);
			addBtns[btnIndex].setBounds(this.x + 260, this.y, this.width - 40, this.height);
			addBtns[btnIndex].setBorder(new EmptyBorder(5, 5, 5, 5));
			addBtns[btnIndex].addActionListener(this);

			mutBtns[btnIndex] = new JButton("-");
			mutBtns[btnIndex].setName("btn" + strKey);
			mutBtns[btnIndex].setBounds(this.x + 80, this.y, this.width - 40, this.height);
			mutBtns[btnIndex].setBorder(new EmptyBorder(5, 5, 5, 5));
			mutBtns[btnIndex].addActionListener(this);

			add(addBtns[btnIndex]);
			add(mutBtns[btnIndex]);
			this.y += 30;
			btnIndex++;
		}
		add(this.okBtn);
		add(this.cancelBtn);
		this.okBtn.setBounds(windowWidth - 190, windowHeight - 80, 60, 25);
		this.okBtn.setName("ok");
		this.cancelBtn.setBounds(windowWidth - 120, windowHeight - 80, 60, 25);
		this.cancelBtn.setName("cancel");
		this.okBtn.addActionListener(this);
		this.cancelBtn.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Component[] cmp = getContentPane().getComponents();
		JButton btn = (JButton) e.getSource();
		String jtfKey = btn.getName().replaceAll("btn", "");
		Component localComponent1;
		Component c;
		if ((jtfKey != null) && (!jtfKey.equals("ok")) && (!jtfKey.equals("cancel"))) {
			String btnText = btn.getText();
			Component[] arrayOfComponent2;
			int i = (arrayOfComponent2 = cmp).length;
			for (int j = 0; j < i; j++) {
				c = arrayOfComponent2[j];
				if ((c instanceof JTextField)) {
					JTextField tf = (JTextField) c;
					if (c.getName().equals("jtf" + jtfKey)) {
						int val = Integer.parseInt(tf.getText());
						if (btnText.equals("+")) {
							if (val < 999) {
								val++;
							}
						} else {
							val--;
							if (val <= 0) {
								val = 1;
							}
						}
						tf.setText(String.valueOf(val));
					}
				}
			}
			return;
		}
		if (e.getSource() == this.okBtn) {
			System.out.println("OK");
			Component[] arrayOfComponent1;
			int k = (arrayOfComponent1 = cmp).length;
			for (int j = 0; j < k; j++) {
				Component c1 = arrayOfComponent1[j];
				if ((c1 instanceof JTextField)) {
					JTextField js = (JTextField) c1;
					System.out.println(js.getName().replaceAll("jtf", "") + ":" + js.getText());
				}
			}
			return;
		}
		if (e.getSource() == this.cancelBtn) {
			System.out.println("cancel");
			dispose();
			return;
		}
	}
}
