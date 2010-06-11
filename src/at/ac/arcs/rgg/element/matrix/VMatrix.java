package at.ac.arcs.rgg.element.matrix;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;

/**
 *
 * @author ilhami
 */
public class VMatrix extends VisualComponent {

    private MatrixEditor matrixEditor;
    private RGG rggInstance;
    private JComponent[][] swingComponents;
    private Integer columncount = 0;
    private Integer rowcount = 0;

    /**
     * Creates a new instance of VTextField
     */
    public VMatrix(RGG rggInstance) {
        this.rggInstance = rggInstance;
        initializeComponents();
    }

    public void setNumeric(boolean b) {

    }

    private void initializeComponents() {
        matrixEditor = new MatrixEditor(rggInstance);
        matrixEditor.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(MatrixEditor.TABLECHANGED)) {
                    if (matrixEditor.getTableModel().getColumnCount() != columncount) {
                        setColumncount(matrixEditor.getTableModel().getColumnCount());
                    }
                    if (matrixEditor.getTableModel().getRowCount() != rowcount) {
                        setRowcount(matrixEditor.getTableModel().getRowCount());
                    }
                }
            }
        });
    }

    public boolean isVisualComponent() {
        return true;
    }

    public JComponent[][] getSwingComponents() {
        if (swingComponents == null) {
            swingComponents = new JComponent[][]{{matrixEditor}};
        }
        return swingComponents;
    }

    public Integer getColumncount() {
        return columncount;
    }

    public void setColumncount(Integer columncount) {
        Integer oldValue = this.columncount;
        this.columncount = columncount;
        changeSupport.firePropertyChange("columncount", oldValue, columncount);
    }

    public int getColumnCount() {
        if (getTableModel() == null) {
            return 0;
        }
        return getTableModel().getColumnCount();
    }

    public Integer getRowcount() {
        return rowcount;
    }

    public void setRowcount(Integer rowcount) {
        Integer oldValue = this.rowcount;
        this.rowcount = rowcount;        
        changeSupport.firePropertyChange("rowcount", oldValue,rowcount);
    }

    public String getColumnName(int columnIndex) {
        return getTableModel().getColumnName(columnIndex);
    }

    public Object[] getValuesAtColumn(int columnindex) {
        matrixEditor.stopCellEditing();
        boolean isNumber = true;
        Object[] values = new Object[getTableModel().getRowCount()];
        for (int i = 0; i < getTableModel().getRowCount(); i++) {
            if (isNumber) {
                if (StringUtils.isNumeric(getTableModel().getValueAt(i, columnindex).toString())) {
                    values[i] = Integer.parseInt(getTableModel().getValueAt(i, columnindex).toString());
                } else {
                    isNumber = false;
                    values[i] = getTableModel().getValueAt(i, columnindex).toString();
                }
            } else {
                values[i] = getTableModel().getValueAt(i, columnindex).toString();
            }
        }
        return values;
    }

    private DefaultTableModel getTableModel() {
        return matrixEditor.getTableModel();
    }

    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(matrixEditor, colspan);
        }
    }
}
