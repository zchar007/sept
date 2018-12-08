package com.sept.framework.taglib.sept.form;

import java.util.ArrayList;

public abstract class FormImpl {
	private int colspan;
	private ArrayList<FormCell> alCells = new ArrayList<FormCell>();

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public boolean addChild(FormCell child) {
		return this.alCells.add(child);
	}

	public void insertChild(int index, FormCell child) {
		this.alCells.add(index, child);
	}

	public boolean removeChild(FormCell child) {
		return this.alCells.remove(child);
	}

	public FormCell removeChild(int childIndex) {
		return this.alCells.remove(childIndex);
	}
}
