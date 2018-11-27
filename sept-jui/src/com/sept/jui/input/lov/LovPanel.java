package com.sept.jui.input.lov;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.text.JTextComponent;

import com.sept.datastructure.DataStore;
import com.sept.exception.AppException;
import com.sept.jui.floating.FloatingPanel;
import com.sept.jui.grid.Grid;
import com.sept.jui.grid.GridColumn;

public abstract class LovPanel extends FloatingPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Grid grid;

	public LovPanel(Point p) {
		super(p);
		grid = new Grid();
		this.add(grid,BorderLayout.CENTER);
	}

	public static LovPanel showLovPanel(JTextComponent textComponent, ArrayList<GridColumn> gridColumns, DataStore ds)
			throws AppException {
		Point point = new Point();
		point.x = textComponent.getLocationOnScreen().x;
		point.y = textComponent.getLocationOnScreen().y + textComponent.getHeight();
		LovPanel datePanel = new LovPanel(point) {
			private static final long serialVersionUID = 1L;

			@Override
			protected ArrayList<GridColumn> getHead() {
				return gridColumns;
			}

			@Override
			protected DataStore getData() {
				return ds;
			}
		};
		datePanel.doGrid();
		datePanel.setVisible(true);
		return datePanel;
	}

	private void doGrid() throws AppException {
		grid.addColumns(getHead());
		this.grid.addRows(getData());
		this.grid.setShowLineNumber(true);
		this.grid.doGrid();
	}

	protected abstract ArrayList<GridColumn> getHead();

	protected abstract DataStore getData();

}
