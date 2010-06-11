package at.ac.arcs.rgg.element.maimporter.ui.arrayheaderselection;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.plaf.UIResource;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


public class RowHeaderTable extends JTable {

    private MyJTable mainTable;
    private PropertyChangeListener tablePropertyChangeListener;
    private ArrayList<RowRadioButton> rbuttons = new ArrayList();


    public RowHeaderTable(MyJTable table) {
        super();
        setAutoCreateColumnsFromModel(false);
//        setSortable(false);
        setAutoscrolls(false);


        TableColumn nrColumn = new TableColumn();
        TableColumn selectionColumn = new TableColumn();

        addColumn(nrColumn);
        addColumn(selectionColumn);

        nrColumn.setCellRenderer(new UIResourceTableCellRenderer());
        selectionColumn.setPreferredWidth(20);
        selectionColumn.setHeaderValue("");
        selectionColumn.setModelIndex(1);

        nrColumn.setPreferredWidth(30); //String.valueOf(mainTable.getRowCount()).length() * 10

        nrColumn.setHeaderValue("Nr");
//

        installTable(table);

//        selectionColumn.setHeaderRenderer(new SelectionHeaderCellRenderer());

        selectionColumn.setCellEditor(new RadioButtonEditor(new JCheckBox()));
        selectionColumn.setCellRenderer(new RadioButtonRenderer());


        setPreferredScrollableViewportSize(getPreferredSize());
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);


    }


    private void installTable(MyJTable table) {
        this.mainTable = table;
        setSelectionForeground(mainTable.getSelectionForeground());
        setSelectionBackground(mainTable.getSelectionBackground());
        setSelectionModel(mainTable.getSelectionModel());

        table.addPropertyChangeListener(getTablePropertyChangeListener());
        updateFromModelChange(null);
        updateFromTableEnabledChanged();
    }


    /**
     * Returns the listener to table's property changes. The listener is lazily created if necessary.
     *
     * @return the <code>PropertyChangeListener</code> for use with the table, guaranteed to be not <code>null</code>.
     */
    protected PropertyChangeListener getTablePropertyChangeListener() {
        if (tablePropertyChangeListener == null) {
            tablePropertyChangeListener = createTablePropertyChangeListener();
        }
        return tablePropertyChangeListener;
    }


    /**
     * Creates the listener to table's property changes. Subclasses are free to roll their own. <p> Implementation note:
     * this listener reacts to table's <code>enabled</code> and <code>columnModel</code> properties and calls the
     * respective <code>updateFromXX</code> methodes.
     *
     * @return the <code>PropertyChangeListener</code> for use with the table.
     */
    protected PropertyChangeListener createTablePropertyChangeListener() {
        return new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ("model".equals(evt.getPropertyName())) {
                    updateFromModelChange((TableModel) evt.getOldValue());
                } else if ("enabled".equals(evt.getPropertyName())) {
                    updateFromTableEnabledChanged();
                }
            }
        };
    }


    private void updateFromModelChange(TableModel tableModel) {
        DefaultTableModel dmodel = new DefaultTableModel(0, 2) {

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return JRadioButton.class;
                    default:
                        throw new IllegalStateException("Table column index is not allowed!");
                }

            }


            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                fireTableDataChanged();
            }
        };
        ButtonGroup bg = new ButtonGroup();
        RowRadioButton b;
        for (int i = 0; i < mainTable.getModel().getRowCount(); i++) {
            b = new RowRadioButton(i);
            bg.add(b);
            dmodel.addRow(new Object[]{i + 1, b});
            rbuttons.add(b);
        }
        setModel(dmodel);
    }


    private void updateFromTableEnabledChanged() {
        setEnabled(mainTable.isEnabled());
    }


    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 1;
    }


    @Override
    public int getRowHeight(int row) {
        return mainTable.getRowHeight(row);
    }


    class myCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                super.setBackground(table.getSelectionBackground());
            } else {
                setBackground(table.getBackground());
            }
            setFont(mainTable.getTableHeader().getFont());
            setBorder(mainTable.getTableHeader().getBorder());
            return this;
        }
    }


    private static class UIResourceTableCellRenderer extends DefaultTableCellRenderer implements UIResource {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (table != null) {
                JTableHeader header = table.getTableHeader();
                if (header != null) {
                    setForeground(header.getForeground());
                    setBackground(header.getBackground());
                    setFont(header.getFont());
                }
            }

            setText((value == null) ? "" : value.toString());
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setHorizontalAlignment(JLabel.CENTER);
            return this;
        }
    }


    private class RadioButtonRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (value == null) {
                return null;
            }
            JRadioButton radioButton = (JRadioButton) value;
            return radioButton;
        }
    }


    private class RadioButtonEditor extends DefaultCellEditor implements ItemListener {

        private JRadioButton button;
        private JTable table;


        public RadioButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }


        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.table = table;
            if (value == null) {
                return null;
            }
            button = (JRadioButton) value;
            button.addItemListener(this);
            return button;
        }


        @Override
        public Object getCellEditorValue() {
            button.removeItemListener(this);
            return button;
        }


        public void itemStateChanged(ItemEvent e) {
            super.fireEditingStopped();
        }
    }


    private class RowRadioButton extends JRadioButton {

        private int rowindex;


        public RowRadioButton(int row) {
            this.rowindex = row;
            if (mainTable.getMyModel().getSelectedRow() == row) {
                setSelected(true);
            }
            addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (isSelected()) {
                        mainTable.getMyModel().setSelectedRow(rowindex);
                    }
                }
            });
        }
    }
}


