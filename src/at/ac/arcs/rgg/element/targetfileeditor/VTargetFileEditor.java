package at.ac.arcs.rgg.element.targetfileeditor;

import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;


/**
 * @author ilhami
 */
class VTargetFileEditor extends VisualComponent {

    private TargetFileEditor targetFileEditor;
    private RGG rggInstance;
    private JComponent[][] swingComponents;


    /**
     * Creates a new instance of VTextField
     */
    public VTargetFileEditor(RGG rggInstance) {
        this.rggInstance = rggInstance;
        initializeComponents();
    }


    private void initializeComponents() {
        targetFileEditor = new TargetFileEditor(rggInstance);
    }


    public boolean isVisualComponent() {
        return true;
    }


    public JComponent[][] getSwingComponents() {
        if (swingComponents == null) {
            swingComponents = new JComponent[][]{{targetFileEditor}};
        }
        return swingComponents;
    }


    public int getColumnCount() {
        if (getTableModel() == null)
            return 0;
        return getTableModel().getColumnCount();
    }


    public String getColumnName(int columnIndex) {
        return getTableModel().getColumnName(columnIndex);
    }


    public Object[] getValuesAtColumn(int columnindex) {
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
        return targetFileEditor.getTableModel();
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(targetFileEditor, colspan);
        }
    }
}
