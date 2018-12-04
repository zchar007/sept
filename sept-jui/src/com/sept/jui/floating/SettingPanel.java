package com.sept.jui.floating;

import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JDialog;

import com.sept.jui.input.SInput;

/**
 * 其实是个JFrame
 * 
 * @author zchar
 *
 */
public class SettingPanel extends JDialog implements WindowFocusListener{

	private static final long serialVersionUID = 1L;

	public SettingPanel(Component component, String title) {
		// super(frame, title);
		this.setTitle(title);
		//this.setModal(true);
		this.setResizable(false);
		this.setUndecorated(true);
		this.addWindowFocusListener(this);
		this.setBounds(component.getLocationOnScreen().x, component.getLocationOnScreen().y, 300, 150);
		final SInput inputDate = new SInput("日期输入", SInput.INPUT_DATE, "yyyy-MM");
		inputDate.setLocation(10, 90);
		this.add(inputDate);
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		this.setVisible(false);
	}

}
