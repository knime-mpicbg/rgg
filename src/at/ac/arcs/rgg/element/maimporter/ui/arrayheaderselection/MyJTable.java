/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.ui.arrayheaderselection;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author ahmet
 */
public class MyJTable extends JTable {

    public MyJTable() {
        setDefaultRenderer(Object.class, new TextEditorRenderer());
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setShowGrid(false);
        addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("model")) {
                    resizeColumn();
                }
            }
        });
        getPreferredScrollableViewportSize().height=200;
    }

    public MyTableModel getMyModel() {
        return (MyTableModel) super.getModel();
    }

    @Override
    protected TableModel createDefaultDataModel() {
        return new MyDefaultTableModel();
    }

    @Override
    public void setModel(TableModel dataModel) {
        if (MyTableModel.class.isAssignableFrom(dataModel.getClass())) {
            super.setModel(dataModel);
        } else {
            throw new UnsupportedOperationException("TableModel is not derived from MyTableModel");
        }
    }

    public void setMyModel(MyTableModel dataModel) {
        super.setModel(dataModel);
    }

    public void resizeColumn() {
        JTableHeader header = getTableHeader();
        TableColumn column = getColumnModel().getColumn(0);
        int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
        int width = (int) getTableHeader().getDefaultRenderer().getTableCellRendererComponent(this, column.getIdentifier(), false, false, -1, col).getPreferredSize().getWidth();
        for (int row = 0; row < getRowCount(); row++) {
            int preferedWidth = (int) getCellRenderer(row, col).getTableCellRendererComponent(this,
                    getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
            width = Math.max(width, preferedWidth);
        }
        header.setResizingColumn(column); // this line is very important

        column.setWidth(width + getIntercellSpacing().width);
    }

    private class TextEditorRenderer implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            JTextField tf = new JTextField(value.toString());
            tf.setBorder(new EmptyBorder(0, 0, 0, 0));
            return tf;
        }
    }
}
