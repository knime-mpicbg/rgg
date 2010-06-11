package at.ac.arcs.rgg.element.maimporter.ui.arrayheaderselection;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;


public class MyDefaultTableModel extends AbstractTableModel implements MyTableModel{
    protected ArrayList<String> colNames; // defines the number of cols
    protected ArrayList<ArrayList> data; // arraylist of arraylists
    protected int selectedRow = -1;
    protected HashMap<ArrayList,Boolean> selectionMap;
    public MyDefaultTableModel() {
        colNames = new ArrayList();
        data = new ArrayList<ArrayList>();
        selectionMap = new HashMap<ArrayList, Boolean>();
    }
    public MyDefaultTableModel(ArrayList<String> colNames, ArrayList<ArrayList> data) {
        this.colNames = colNames;
        this.data = data;
        selectionMap = new HashMap<ArrayList, Boolean>();
        for(ArrayList row:this.data){
            selectionMap.put(row, false);
        }
    }
/*
Basic getXXX methods required by an class implementing TableModel
 */
// Returns the name of each col, numbered 0..columns-1
    public String getColumnName(int col) {
        return colNames.get(col);
    }
// Returns the number of columns
    public int getColumnCount() {
        return(colNames.size());
    }
// Returns the number of rows
    public int getRowCount() {
        return(data.size());
    }
// Returns the data for each cell, identified by its
// row, col index.
    public Object getValueAt(int row, int col) {
        ArrayList rowList =  data.get(row);
        String result = null;
        if (col<rowList.size()) {
            result = rowList.get(col).toString();
        }
// _apparently_ it's ok to return null for a "blank" cell
        return(result);
    }
// Returns true if a cell should be editable in the table
    public boolean isCellEditable(int row, int col) {
        return true;
    }
// Changes the value of a cell
    
    public void setValueAt(Object value, int row, int col) {
        ArrayList rowList = data.get(row);
// make this row long enough
        if (col>=rowList.size()) {
            while (col>=rowList.size()) rowList.add(null);
        }
// install the data
        rowList.set(col, value);
// notify model listeners of cell change
        fireTableCellUpdated(row, col);
    }
/*
Convenience methods of BasicTable
 */
// Adds the given column to the right hand side of the model
    public void addColumn(String name) {
        colNames.add(name);
        fireTableStructureChanged();
/*
At present, TableModelListener does not have a more specific
notification for changing the number of columns.
 */
    }
// Adds the given row, returns the new row index
    public int addRow(ArrayList row) {
        data.add(row);
        selectionMap.put(row, false);
        fireTableRowsInserted(data.size()-1, data.size()-1);
        return(data.size() -1);
    }
// Adds an empty row, returns the new row index
    public int addRow() {
// Create a new row with nothing in it
        ArrayList row = new ArrayList();
        return(addRow(row));
    }
// Deletes the given row
    public void deleteRow(int row) {
        if (row == -1) return;
        selectionMap.remove(data.get(row));
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }
    public ArrayList<ArrayList> getDataList(){
        return data;
    }

    public void setSelection(int rowindex, boolean isSelected) {
        if(rowindex < 0 || rowindex >= data.size()) return;
        selectionMap.put(data.get(rowindex), isSelected);
    }

    public boolean getSelection(int rowindex) {
        if(rowindex < 0 || rowindex >= data.size()) return false;
        return selectionMap.get(data.get(rowindex));
    }

    public int getSelectedRow() {
        return selectedRow;
    }

    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
        fireTableCellUpdated(selectedRow, 0);
    }
    
}