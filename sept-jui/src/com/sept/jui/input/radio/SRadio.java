package com.sept.jui.input.radio;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JRadioButton;

import com.sept.jui.input.SInputCell;

public class SRadio extends JComponent implements ItemListener, SInputCell {
	private static final long serialVersionUID = 1L;
	private ButtonGroup bGroup;
	private String arrayCode;
	private LinkedHashMap<String, String> keyValue;
	private LinkedHashMap<String, String> valueKey;
	private String select_item;

	public SRadio(String arrayCode) {
		this.setLayout(new GridLayout(0, 5, 0, 4));

		this.setArrayCode(arrayCode);
	}

	private void initRadios() {
		this.removeAll();
		this.revalidate();
		this.bGroup = new ButtonGroup();
		this.keyValue = new LinkedHashMap<>();
		this.valueKey = new LinkedHashMap<>();

		String[] str = this.arrayCode.split(",");
		for (int i = 0; i < str.length; i++) {
			String[] st2 = str[i].split(":");
			this.keyValue.put(st2[0], st2[1]);
			this.valueKey.put(st2[1], st2[0]);
		}

		for (String value : this.keyValue.values()) {
			JRadioButton jrb = new JRadioButton(value);
			this.add(jrb);
			this.bGroup.add(jrb);
			jrb.addItemListener(this);
		}

	}

	public String getArrayCode() {
		return arrayCode;
	}

	public void setArrayCode(String arrayCode) {
		this.arrayCode = arrayCode;
		this.initRadios();
	}

	@Override
	public Object getValue() {
		return this.valueKey.get(this.select_item);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object item = e.getItem();
		if (item instanceof JRadioButton) {
			if (e.getStateChange() == 1) {
				JRadioButton jrb = (JRadioButton) item;
				this.select_item = jrb.getText();
			}
		}
	}
}
