/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.ui.model;

import java.util.ArrayList;

import at.ac.arcs.rgg.element.maimporter.array.TargetFile;

import javax.swing.table.AbstractTableModel;


/**
 * @author ilhami
 */
public class TargetFileTableModel extends AbstractTableModel {

    private TargetFile targetFile;


    public TargetFileTableModel(TargetFile targetFile) {
        this.targetFile = targetFile;
    }


    public void addColumn(Object columnName) {
        targetFile.getHeader().add(columnName.toString());
        for (ArrayList<String> line : targetFile.getTargetFileData()) {
            line.add(null);
        }
        fireTableStructureChanged();
    }


    public void removeColumnAndColumnData(int selectedColumn) {
        targetFile.getHeader().remove(selectedColumn);
        for (int i = 0; i < getRowCount(); i++) {
            targetFile.getTargetFileData().get(i).remove(selectedColumn);
        }
        fireTableStructureChanged();
    }


    public int getRowCount() {
        return targetFile.getTargetFileData().size();
    }


    public int getColumnCount() {
        return targetFile.getHeader().size();
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        return targetFile.getTargetFileData().get(rowIndex).get(columnIndex);
    }


    @Override
    public Class<String> getColumnClass(int columnIndex) {
        return String.class;
    }


    @Override
    public String getColumnName(int column) {
        return targetFile.getHeader().get(column);
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (getColumnName(columnIndex).equalsIgnoreCase("filename")) {
            return false;
        }
        return true;
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        targetFile.getTargetFileData().get(rowIndex).remove(columnIndex);
        targetFile.getTargetFileData().get(rowIndex).add(columnIndex, aValue.toString());
        fireTableCellUpdated(rowIndex, columnIndex);
    }


    public void removeRow(int rowIndex) {
        targetFile.getTargetFileData().remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
