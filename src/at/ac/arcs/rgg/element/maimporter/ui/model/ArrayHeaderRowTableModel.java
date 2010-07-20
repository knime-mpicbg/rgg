package at.ac.arcs.rgg.element.maimporter.ui.model;

import at.ac.arcs.rgg.element.maimporter.array.ArrayInfo;
import at.ac.arcs.rgg.element.maimporter.ui.arrayheaderselection.MyDefaultTableModel;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;


/**
 * @author ilhami
 */
public class ArrayHeaderRowTableModel extends MyDefaultTableModel {

    private LineNumberReader reader;
    private ArrayInfo arrayInfo;


    public ArrayHeaderRowTableModel() {
        super();
        colNames.add("Rows");
    }


    public static ArrayHeaderRowTableModel createArrayHeaderRowTableModel(ArrayInfo info) throws IOException {
        ArrayHeaderRowTableModel model = new ArrayHeaderRowTableModel();
        model.setArrayInfo(info);
        model.addRowsToModel(50);
        return model;
    }


    public void addRowsToModel(int count) throws IOException {
        if (reader == null) {
            reader = new LineNumberReader(new FileReader(arrayInfo.getArrayFile()));
        }
        String line;
        ArrayList row;
        for (int i = 0; i < count; i++) {
            line = reader.readLine();
            if (line == null) {
                break;
            } else {
                row = new ArrayList();
                row.add(line);
                data.add(row);
            }
        }
    }


    public int getSelectedRow() {
        return selectedRow;
    }


    public void setSelectedRow(int selectedRow) {
        this.selectedRow = selectedRow;
        fireArrayHeaderRowChanged(selectedRow);
    }


    public ArrayInfo getArrayInfo() {
        return arrayInfo;
    }


    public void setArrayInfo(ArrayInfo arrayInfo) {
        this.arrayInfo = arrayInfo;
    }


    public void addArrayHeaderRowChangeListener(ArrayHeaderRowChangeListener l) {
        listenerList.add(ArrayHeaderRowChangeListener.class, l);
    }


    public void removeFooListener(ArrayHeaderRowChangeListener l) {
        listenerList.remove(ArrayHeaderRowChangeListener.class, l);
    }
    // Notify all listeners that have registered interest for
    // notification on this event type.  The event instance 
    // is lazily created using the parameters passed into 
    // the fire method.


    protected void fireArrayHeaderRowChanged(int headerRow) {
        ArrayHeaderChangedEvent evt = null;
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ArrayHeaderRowChangeListener.class) {
                // Lazily create the event:
                if (evt == null) {
                    evt = new ArrayHeaderChangedEvent(this, headerRow);
                }
                ((ArrayHeaderRowChangeListener) listeners[i + 1]).stateChanged(evt);
            }
        }
    }
}
