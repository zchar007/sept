package com.sept.jui.grid.action;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.EventListener;

/**
 * 单元格事件
 * 
 * @author zchar
 *
 */
public interface GridCellAction extends EventListener, Serializable {

	void actionPerformed(ActionEvent e, int columnIndex, int rowIndex);
}
