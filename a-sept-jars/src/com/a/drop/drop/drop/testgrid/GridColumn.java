package com.sept.drop.testgrid;

import java.io.Serializable;

public interface GridColumn extends Serializable{

	String getName();

	String getHead();

	Object getDefault();

	boolean readonly();

	Class<?> getValueType();

	Object dealValue(Object value);

}
