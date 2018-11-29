package com.sept.jui.input.combox.color;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

import com.sept.exception.AppException;
import com.sept.jui.input.SInputCell;

public class SColorPickerCombobox extends JComboBox<Object> implements SInputCell {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SELECTEDCOLOR = "selectedcolor";
	private Color defulColor = null;

	public SColorPickerCombobox() {
		this.setEditable(false);
		this.setRenderer(new ListCellRenderer<Object>() {
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				SColorTextField textField = new SColorTextField();
				textField.setColor(getSelectedColor());
				return textField;
			}
		});
		this.setPreferredSize(new Dimension(150, 22));
	}

	public void updateUI() {
		this.setUI(new MetalDateComboBoxUI());
	}

	class MetalDateComboBoxUI extends MetalComboBoxUI {
		protected ComboPopup createPopup() {
			return new ColorPopup(comboBox);
		}
	}

	class ColorPopup extends BasicComboPopup implements PropertyChangeListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private SColorPicker picker;

		public ColorPopup(JComboBox<?> box) {
			super(box);
			picker = new SColorPicker();
			picker.addPropertyChangeListener(this);
			this.setLayout(new BorderLayout());
			this.add(picker, BorderLayout.CENTER);
			this.setBorder(BorderFactory.createEmptyBorder());
		}

		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName() == SColorPicker.SELECTEDCOLORCHANGE) {
				comboBox.putClientProperty(SELECTEDCOLOR, picker.getSelectedColor());
				comboBox.setPopupVisible(false);
			}
		}
	}

	public Color getSelectedColor() {
		Object obj = getClientProperty(SELECTEDCOLOR);
		if (obj != null && obj instanceof Color) {
			return (Color) obj;
		} else {
			return defulColor;
		}
	}

	public boolean setDefulColor(Color defulColor) {
		this.defulColor = defulColor;
		return true;
	}

	public void setSelectedColor(Color selectedColor) {
		putClientProperty(SELECTEDCOLOR, selectedColor);
	}

	@Override
	public Object getSelectedItem() {
		return this.getSelectedColor();
	}

	@Override
	public void setSelectedItem(Object anObject) {
		this.setSelectedColor((Color) anObject);
	}

	@Override
	public Object getValue() throws AppException {
		return this.getSelectedItem();
	}
}
