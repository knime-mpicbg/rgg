package at.ac.arcs.rgg.element.maimporter.ui.model;

import at.ac.arcs.rgg.element.maimporter.array.Array;
import at.ac.arcs.rgg.element.maimporter.ui.inputselection.InputInfo;
import at.ac.arcs.rgg.element.maimporter.ui.inputselection.InputList;

import javax.swing.table.AbstractTableModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ilhami
 */
public class RGListTableModel extends AbstractTableModel implements PropertyChangeListener {

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private ArrayList<Integer> columns = new ArrayList<Integer>();
    private ArrayList<Integer> annotations;
    private InputList ilist;
    private Array array;
    // contains the array header line
    private List<String> colNames = new ArrayList<String>();    // arraylist of arraylists
    private List<List<String>> data = new ArrayList<List<String>>();


    public RGListTableModel(Array array, String[] othercolumns) throws IOException {
        this.array = array;

        ilist = new InputList();
        if (array.getG() != null) {
            ilist.put(array.getG());
            array.getG().addPropertyChangeListener(this);
        }
        if (array.getGb() != null) {
            ilist.put(array.getGb());
            array.getGb().addPropertyChangeListener(this);
        }
        if (array.getR() != null) {
            ilist.put(array.getR());
            array.getR().addPropertyChangeListener(this);
        }
        if (array.getRb() != null) {
            ilist.put(array.getRb());
            array.getRb().addPropertyChangeListener(this);
        }
        ilist.put(array.getAnnotations());
        array.getAnnotations().addPropertyChangeListener(this);
        annotations = array.getAnnotations().getColumns();

        colNames.addAll(array.getHeaders());
        data.addAll(array.readAssayData(50));

        if (othercolumns != null && othercolumns.length > 0) {
            InputInfo otherinput;
            for (String othercol : othercolumns) {
                otherinput = new InputInfo(othercol, InputInfo.OptionType.ONE_TO_ONE);
                array.othercolumns.add(otherinput);
                ilist.put(otherinput);
            }
        }
    }


    public int getRowCount() {
        return data.size();
    }


    public int getColumnCount() {
        return colNames.size();
    }


    @Override
    public String getColumnName(int col) {
        return colNames.get(col);
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        List rowList = data.get(rowIndex);
        String result = null;
        if (columnIndex < rowList.size()) {
            result = rowList.get(columnIndex).toString();
        }
// _apparently_ it's ok to return null for a "blank" cell
        return (result);
    }


    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }


    public void setArray(Array array) {
        this.array = array;
    }


    public Array getArray() {
        return array;
    }


    public InputList getInputList() {
        return ilist;
    }


    public ArrayList<Integer> getOtherColumnsIndices() {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (InputInfo inf : array.othercolumns) {
            if (inf.getFirstColumn() != -1) {
                indices.add(inf.getFirstColumn());
            }
        }
        return indices;
    }


    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(Array.PROP_Annotation)) {
            changeSupport.firePropertyChange(Array.PROP_Annotation, evt.getOldValue(), evt.getNewValue());
        } else if (!evt.getPropertyName().equals(Array.PROP_G) &&
                !evt.getPropertyName().equals(Array.PROP_Gb) &&
                !evt.getPropertyName().equals(Array.PROP_R) &&
                !evt.getPropertyName().equals(Array.PROP_Rb)) {
        }
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        changeSupport.addPropertyChangeListener(listener);
    }


    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        changeSupport.removePropertyChangeListener(listener);
    }
}
