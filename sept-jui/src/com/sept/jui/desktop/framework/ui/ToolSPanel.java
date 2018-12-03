package com.sept.jui.desktop.framework.ui;

import javax.swing.JPanel;

import com.sept.datastructure.DataObject;
import com.sept.exception.SeptException;
import com.sept.jui.desktop.framework.history.PageHistory;

public abstract class ToolSPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ToolSPanel() {

	}

	/**
	 * 填充数据
	 * 
	 * @param para
	 * @throws SeptException
	 */
	public abstract void coverData(PageHistory ph) throws SeptException;

	/**
	 * 获取数据
	 * 
	 * @return
	 * @throws SeptException
	 */
	public abstract PageHistory getData() throws SeptException;

}
