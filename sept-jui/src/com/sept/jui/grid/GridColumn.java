package com.sept.jui.grid;

import javax.swing.JComponent;

public interface GridColumn {
	Class<?> getComponentType();

	String getShowName();

	String getName();

	Object getDefaultValue();

	Object dealValue(Object value);

	Object dealValue4Get(Object value);

	boolean readonly();

	JComponent getComponent();
}
