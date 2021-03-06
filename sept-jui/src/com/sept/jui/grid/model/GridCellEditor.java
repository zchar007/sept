package com.sept.jui.grid.model;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.TreeCellEditor;

import com.sept.jui.grid.columns.BooleanColumn;
import com.sept.jui.grid.columns.ButtonColumn;
import com.sept.jui.grid.columns.ButtonsColumn;
import com.sept.jui.grid.columns.ColorColumn;
import com.sept.jui.grid.columns.DateColumn;
import com.sept.jui.grid.columns.DropdownColumn;
import com.sept.jui.grid.columns.GridLineNumberColumn;
import com.sept.jui.grid.columns.GridSelectionColumn;
import com.sept.jui.grid.columns.TextColumn;

public class GridCellEditor extends AbstractCellEditor implements TableCellEditor, TreeCellEditor {
	private static final long serialVersionUID = 1L;
//
//  Instance Variables
//

	/** The Swing component being edited. */
	protected JComponent editorComponent;
	/** save real */
	protected Object editObject;

	private ActionListener buttonListener;
	/**
	 * The delegate class which handles all methods sent from the
	 * <code>CellEditor</code>.
	 */
	protected EditorDelegate delegate;
	/**
	 * An integer specifying the number of clicks needed to start editing. Even if
	 * <code>clickCountToStart</code> is defined as zero, it will not initiate until
	 * a click occurs.
	 */
	protected int clickCountToStart = 1;

//
//  Constructors
//
	public GridCellEditor(GridColumn component) {
		editObject = component;
		if (component instanceof TextColumn || component instanceof GridLineNumberColumn
				|| component instanceof DateColumn) {
			JTextField textField = (JTextField) component.getComponent();
			this.initGridCellEditor(textField);
		} else if (component instanceof BooleanColumn || component instanceof GridSelectionColumn) {
			JCheckBox checkBox = (JCheckBox) component.getComponent();
			this.initGridCellEditor(checkBox);
		} else if (component instanceof DropdownColumn || component instanceof ColorColumn) {
			JComboBox<?> checkBox = (JComboBox<?>) component.getComponent();
			this.initGridCellEditor(checkBox);
		} else if (component instanceof ButtonColumn) {
			JButton button = (JButton) component.getComponent();
			this.initGridCellEditor(button);
		} else if (component instanceof ButtonsColumn) {
			JPanel panel = (JPanel) component.getComponent();
			this.initGridCellEditor(panel);
		}
	}

	private void initGridCellEditor(JPanel panel) {
		editorComponent = panel;
		this.clickCountToStart = -1;
		delegate = new EditorDelegate();
		// panel.addMouseListener(delegate);
	}

	private void initGridCellEditor(JButton button) {
		editorComponent = button;
		this.clickCountToStart = -1;
		delegate = new EditorDelegate();
		button.addActionListener(delegate);
	}

	/**
	 * Constructs a <code>DefaultCellEditor</code> that uses a text field.
	 *
	 * @param textField a <code>JTextField</code> object
	 */
	public void initGridCellEditor(final JTextField textField) {
		editorComponent = textField;
		if (editObject instanceof DateColumn) {
			this.clickCountToStart = 1;
		} else {
			this.clickCountToStart = 2;
		}
		delegate = new EditorDelegate() {
			private static final long serialVersionUID = 1L;

			public void setValue(Object value) {
				textField.setText((value != null) ? value.toString() : "");
			}

			public Object getCellEditorValue() {
				return textField.getText();
			}
		};
		textField.addActionListener(delegate);
	}

	/**
	 * Constructs a <code>DefaultCellEditor</code> object that uses a check box.
	 *
	 * @param checkBox a <code>JCheckBox</code> object
	 */
	public void initGridCellEditor(final JCheckBox checkBox) {
		editorComponent = checkBox;
		delegate = new EditorDelegate() {
			private static final long serialVersionUID = 1L;

			public void setValue(Object value) {
				boolean selected = false;
				if (value instanceof Boolean) {
					selected = ((Boolean) value).booleanValue();
				} else if (value instanceof String) {
					selected = value.equals("true");
				}
				checkBox.setSelected(selected);
			}

			public Object getCellEditorValue() {
				return Boolean.valueOf(checkBox.isSelected());
			}
		};
		checkBox.addActionListener(delegate);
		checkBox.setRequestFocusEnabled(false);
	}

	/**
	 * Constructs a <code>DefaultCellEditor</code> object that uses a combo box.
	 *
	 * @param comboBox a <code>JComboBox</code> object
	 */
	public void initGridCellEditor(final JComboBox<?> comboBox) {
		editorComponent = comboBox;
		comboBox.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
		delegate = new EditorDelegate() {
			private static final long serialVersionUID = 1L;

			public void setValue(Object value) {
				comboBox.setSelectedItem(((GridColumn) editObject).dealValue(value));
			}

			public Object getCellEditorValue() {
				return comboBox.getSelectedItem();
			}

			public boolean shouldSelectCell(EventObject anEvent) {
				if (anEvent instanceof MouseEvent) {
					MouseEvent e = (MouseEvent) anEvent;
					return e.getID() != MouseEvent.MOUSE_DRAGGED;
				}
				return true;
			}

			public boolean stopCellEditing() {
				if (comboBox.isEditable()) {
					comboBox.actionPerformed(new ActionEvent(GridCellEditor.this, 0, ""));
				}
				return super.stopCellEditing();
			}
		};
		comboBox.addActionListener(delegate);
	}

	/**
	 * Returns a reference to the editor component.
	 *
	 * @return the editor <code>Component</code>
	 */
	public Component getComponent() {
		return editorComponent;
	}

//
//  Modifying
//

	/**
	 * Specifies the number of clicks needed to start editing.
	 *
	 * @param count an int specifying the number of clicks needed to start editing
	 * @see #getClickCountToStart
	 */
	public void setClickCountToStart(int count) {
		clickCountToStart = count;
	}

	/**
	 * Returns the number of clicks needed to start editing.
	 * 
	 * @return the number of clicks needed to start editing
	 */
	public int getClickCountToStart() {
		return clickCountToStart;
	}

//
//  Override the implementations of the superclass, forwarding all methods
//  from the CellEditor interface to our delegate.
//

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#getCellEditorValue
	 */
	public Object getCellEditorValue() {
		return delegate.getCellEditorValue();
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#isCellEditable(EventObject)
	 */
	public boolean isCellEditable(EventObject anEvent) {
		return delegate.isCellEditable(anEvent);
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#shouldSelectCell(EventObject)
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return delegate.shouldSelectCell(anEvent);
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#stopCellEditing
	 */
	public boolean stopCellEditing() {
		return delegate.stopCellEditing();
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#cancelCellEditing
	 */
	public void cancelCellEditing() {
		delegate.cancelCellEditing();
	}

//
//  Implementing the TreeCellEditor Interface
//

	/** Implements the <code>TreeCellEditor</code> interface. */
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		String stringValue = tree.convertValueToText(value, isSelected, expanded, leaf, row, false);

		delegate.setValue(stringValue);
		return editorComponent;
	}

//
//  Implementing the CellEditor Interface
//
	/** Implements the <code>TableCellEditor</code> interface. */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		delegate.setValue(value);
		if (editorComponent instanceof JCheckBox) {
			// in order to avoid a "flashing" effect when clicking a checkbox
			// in a table, it is important for the editor to have as a border
			// the same border that the renderer has, and have as the background
			// the same color as the renderer has. This is primarily only
			// needed for JCheckBox since this editor doesn't fill all the
			// visual space of the table cell, unlike a text field.
			TableCellRenderer renderer = table.getCellRenderer(row, column);
			Component c = renderer.getTableCellRendererComponent(table, value, isSelected, true, row, column);
			if (c != null) {
				editorComponent.setOpaque(true);
				editorComponent.setBackground(c.getBackground());
				if (c instanceof JComponent) {
					editorComponent.setBorder(((JComponent) c).getBorder());
				}
			} else {
				editorComponent.setOpaque(false);
			}
		} else if (editorComponent instanceof JButton) {
			ButtonColumn button = (ButtonColumn) editObject;
			if (null == buttonListener) {
				buttonListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (null != button.getAction()) {
							button.getAction().actionPerformed(e, column, table.getSelectedRow());
						}
					}
				};
				((JButton) editorComponent).addActionListener(buttonListener);
			}
		} else if (editorComponent instanceof JPanel) {
			ButtonsColumn button = (ButtonsColumn) editObject;
			if (null == buttonListener) {
				buttonListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (null != button.getAction()) {
							button.getAction().actionPerformed(e, column, table.getSelectedRow());
						}
					}
				};
				ArrayList<JButton> alButtons = button.getAlButtons();
				for (int i = 0; i < alButtons.size(); i++) {
					alButtons.get(i).addActionListener(buttonListener);
					alButtons.get(i).addActionListener(this.delegate);
				}
			}
		}
		return editorComponent;
	}

//
//  Protected EditorDelegate class
//

	/**
	 * The protected <code>EditorDelegate</code> class.
	 */
	protected class EditorDelegate implements ActionListener, ItemListener, MouseListener, Serializable {
		private static final long serialVersionUID = 1L;
		/** The value of this cell. */
		protected Object value;

		/**
		 * Returns the value of this cell.
		 * 
		 * @return the value of this cell
		 */
		public Object getCellEditorValue() {
			return value;
		}

		/**
		 * Sets the value of this cell.
		 * 
		 * @param value the new value of this cell
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * Returns true if <code>anEvent</code> is <b>not</b> a <code>MouseEvent</code>.
		 * Otherwise, it returns true if the necessary number of clicks have occurred,
		 * and returns false otherwise.
		 *
		 * @param anEvent the event
		 * @return true if cell is ready for editing, false otherwise
		 * @see #setClickCountToStart
		 * @see #shouldSelectCell
		 */
		public boolean isCellEditable(EventObject anEvent) {
			if (anEvent instanceof MouseEvent) {
				return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
			}
			return true;
		}

		/**
		 * Returns true to indicate that the editing cell may be selected.
		 *
		 * @param anEvent the event
		 * @return true
		 * @see #isCellEditable
		 */
		public boolean shouldSelectCell(EventObject anEvent) {
			return true;
		}

		/**
		 * Returns true to indicate that editing has begun.
		 *
		 * @param anEvent the event
		 */
		public boolean startCellEditing(EventObject anEvent) {
			return true;
		}

		/**
		 * Stops editing and returns true to indicate that editing has stopped. This
		 * method calls <code>fireEditingStopped</code>.
		 *
		 * @return true
		 */
		public boolean stopCellEditing() {
			fireEditingStopped();
			return true;
		}

		/**
		 * Cancels editing. This method calls <code>fireEditingCanceled</code>.
		 */
		public void cancelCellEditing() {
			fireEditingCanceled();
		}

		/**
		 * When an action is performed, editing is ended.
		 * 
		 * @param e the action event
		 * @see #stopCellEditing
		 */
		public void actionPerformed(ActionEvent e) {
			// System.out.println("actionPerformed");
			GridCellEditor.this.stopCellEditing();
		}

		/**
		 * When an item's state changes, editing is ended.
		 * 
		 * @param e the action event
		 * @see #stopCellEditing
		 */
		public void itemStateChanged(ItemEvent e) {
			GridCellEditor.this.stopCellEditing();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			GridCellEditor.this.stopCellEditing();
			// System.out.println("mouseClicked");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			GridCellEditor.this.stopCellEditing();
			// System.out.println("mousePressed");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			GridCellEditor.this.stopCellEditing();
			// System.out.println("mouseReleased");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			GridCellEditor.this.stopCellEditing();
			// System.out.println("mouseEntered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			GridCellEditor.this.stopCellEditing();
			// System.out.println("mouseExited");
		}
	}

}
