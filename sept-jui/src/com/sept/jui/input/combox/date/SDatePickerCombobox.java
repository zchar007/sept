package com.sept.jui.input.combox.date;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;


public class SDatePickerCombobox extends JComboBox<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dateFormat;
	private Date defaulDate = null;
	public static final String SELECTEDATE = "selecteddate";

	/**
	 * Create the frame.
	 */
	public SDatePickerCombobox(String dateFormat) {
		this.setEditable(false);
		this.dateFormat = dateFormat;
		this.setRenderer(new ListCellRenderer<Object>() {
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				SDateTextField textField = new SDateTextField(SDatePickerCombobox.this.dateFormat);
				textField.setDate(getSelectedDate());
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
			return new DatePopup(comboBox);
		}
	}

	class DatePopup extends BasicComboPopup implements PropertyChangeListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private SDatePicker picker;

		public DatePopup(JComboBox<?> box) {
			super(box);
			picker = new SDatePicker();
			picker.addPropertyChangeListener(this);
			this.setLayout(new BorderLayout());
			this.add(picker, BorderLayout.CENTER);
			this.setBorder(BorderFactory.createEmptyBorder());
		}

		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName() == SDatePicker.SELECTEDDATECHANGE) {
				comboBox.putClientProperty(SELECTEDATE, picker.getSelectedDate());
				comboBox.setPopupVisible(false);
			}
		}
	}

	public Date getSelectedDate() {
		Object obj = getClientProperty(SELECTEDATE);
		if (obj != null && obj instanceof Date) {
			return (Date) obj;
		} else {
			return defaulDate;
		}
	}

	public boolean setDefulDate(Date defaulDate) {
		this.defaulDate = defaulDate;
		return true;
	}

	public void setSelectedColor(Date selectedDate) {
		putClientProperty(SELECTEDATE, selectedDate);
	}
	
	@Override
	public void hidePopup() {
		setPopupVisible(true);
	}

}
