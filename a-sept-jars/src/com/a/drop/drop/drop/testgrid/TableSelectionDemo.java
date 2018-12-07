package com.sept.drop.testgrid;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class TableSelectionDemo extends JPanel implements ActionListener {
    private JTable table;
    private JCheckBox rowCheck;
    private JCheckBox columnCheck;
    private JCheckBox cellCheck;
    private ButtonGroup buttonGroup;
    private JTextArea output;

    public TableSelectionDemo() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        table = new JTable(new MyTableModel());
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.getColumnModel().getSelectionModel()
                .addListSelectionListener(new ColumnListener());
        add(new JScrollPane(table));
        

        add(new JLabel("Selection Mode"));
        buttonGroup = new ButtonGroup();
        //可以选中多行，使用ctrl或者shift都可以进行选择，默认情况下就是可以选择多行
        addRadio("Multiple Interval Selection").setSelected(true);
        //只能选中单行
        addRadio("Single Selection");
        //只能选中连续的多行
        addRadio("Single Interval Selection");
        
        
        add(new JLabel("Selection Options"));
        
        
        //默认情况下就是以行为单位进行选择
        rowCheck = addCheckBox("Row Selection");
        rowCheck.setSelected(true);
        
        columnCheck = addCheckBox("Column Selection");
        
        
        cellCheck = addCheckBox("Cell Selection");
        cellCheck.setEnabled(false);


        
        output = new JTextArea(5, 40);
        output.setEditable(false);
        add(new JScrollPane(output));
    }

    private JCheckBox addCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.addActionListener(this);
        add(checkBox);
        return checkBox;
    }

    private JRadioButton addRadio(String text) {
        JRadioButton b = new JRadioButton(text);
        b.addActionListener(this);
        buttonGroup.add(b);
        add(b);
        return b;
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        // Cell selection is disabled in Multiple Interval Selection
        // mode. The enabled state of cellCheck is a convenient flag
        // for this status.
        if ("Row Selection" == command) {
            table.setRowSelectionAllowed(rowCheck.isSelected());
            // In MIS mode, column selection allowed must be the
            // opposite of row selection allowed.
            if (!cellCheck.isEnabled()) {
                table.setColumnSelectionAllowed(!rowCheck.isSelected());
            }
        } else if ("Column Selection" == command) {
            table.setColumnSelectionAllowed(columnCheck.isSelected());
            // In MIS mode, row selection allowed must be the
            // opposite of column selection allowed.
            if (!cellCheck.isEnabled()) {
                table.setRowSelectionAllowed(!columnCheck.isSelected());
            }
        } else if ("Cell Selection" == command) {
            table.setCellSelectionEnabled(cellCheck.isSelected());
        } else if ("Multiple Interval Selection" == command) {
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            // If cell selection is on, turn it off.
            if (cellCheck.isSelected()) {
                cellCheck.setSelected(false);
                table.setCellSelectionEnabled(false);
            }
            // And don't let it be turned back on.
            cellCheck.setEnabled(false);
        } else if ("Single Interval Selection" == command) {
            table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            // Cell selection is ok in this mode.
            cellCheck.setEnabled(true);
        } else if ("Single Selection" == command) {
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            // Cell selection is ok in this mode.
            cellCheck.setEnabled(true);
        }

        // Update checkboxes to reflect selection mode side effects.
        rowCheck.setSelected(table.getRowSelectionAllowed());
        columnCheck.setSelected(table.getColumnSelectionAllowed());
        if (cellCheck.isEnabled()) {
            cellCheck.setSelected(table.getCellSelectionEnabled());
        }
    }

    private void outputSelection() {
        output.append(String.format("Lead: %d, %d. ", table.getSelectionModel()
                .getLeadSelectionIndex(), table.getColumnModel()
                .getSelectionModel().getLeadSelectionIndex()));
        output.append("Rows:");
        for (int c : table.getSelectedRows()) {
            output.append(String.format(" %d", c));
        }
        output.append(". Columns:");
        for (int c : table.getSelectedColumns()) {
            output.append(String.format(" %d", c));
        }
        output.append(".\n");
    }

    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("ROW SELECTION EVENT. ");
            outputSelection();
        }
    }

    private class ColumnListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            output.append("COLUMN SELECTION EVENT. ");
            outputSelection();
        }
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = { "First Name", "Last Name", "Sport",
                "# of Years", "Vegetarian" };
        private Object[][] data = {
                { "Kathy", "Smith", "Snowboarding", new Integer(5),
                        new Boolean(false) },
                { "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
                { "Sue", "Black", "Knitting", new Integer(2),
                        new Boolean(false) },
                { "Jane", "White", "Speed reading", new Integer(20),
                        new Boolean(true) },
                { "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's editable.
         */
        public boolean isCellEditable(int row, int col) {
            // Note that the data/cell address is constant,
            // no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's data can
         * change.
         */
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
            JFrame frame = new JFrame("TableSelectionDemo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            TableSelectionDemo newContentPane = new TableSelectionDemo();
            newContentPane.setOpaque(true); // content panes must be opaque
            frame.setContentPane(newContentPane);

            // Display the window.
            frame.pack();
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}