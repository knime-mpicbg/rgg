package at.ac.arcs.rgg.element.maimporter.ui;

import at.ac.arcs.rgg.element.maimporter.array.Array;
import at.ac.arcs.rgg.element.maimporter.ui.inputselection.AdjustmentController;
import at.ac.arcs.rgg.element.maimporter.ui.inputselection.InputSelectorTable;
import at.ac.arcs.rgg.element.maimporter.ui.model.RGListTableModel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.util.DefaultUnitConverter;
import com.jgoodies.forms.util.UnitConverter;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ilhami
 */
public class RGListSettingsPanel extends javax.swing.JPanel implements PropertyChangeListener {

    private String[] columns = new String[4];
    private String[] annotations;
    private JXTable table;
    private InputSelectorTable inputSelectorTable;
    private RGListTableModel model;
    private UnitConverter converter = DefaultUnitConverter.getInstance();
    private int height = 50;
    private boolean isHeightSet = false;


    /**
     * Creates new form RGListSettingsPanel
     */
    public RGListSettingsPanel() {
        initComponents();
    }


    public RGListSettingsPanel(int height) {
        this.height = height + converter.dialogUnitYAsPixel(35, this);
        isHeightSet = true;
        initComponents();
    }


    public void setModel(RGListTableModel model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        table.setModel(model);
        inputSelectorTable.setOptions(model.getInputList());
        propertyChange(new PropertyChangeEvent(this, Array.PROP_Annotation, null, annotations));
    }


    public String getRHeader() {
        if (model.getArray().getR().getFirstColumn() > 0 &&
                model.getArray().getR().getFirstColumn() < inputSelectorTable.getColumnCount()) {
            return inputSelectorTable.getColumnName(model.getArray().getR().getFirstColumn());
        }
        return "";
    }


    public String getRbHeader() {
        if (model.getArray().getRb().getFirstColumn() > 0 &&
                model.getArray().getRb().getFirstColumn() < inputSelectorTable.getColumnCount()) {
            return inputSelectorTable.getColumnName(model.getArray().getRb().getFirstColumn());
        }
        return "";
    }


    public String getGHeader() {
        if (model.getArray().getG().getFirstColumn() > 0 &&
                model.getArray().getG().getFirstColumn() < inputSelectorTable.getColumnCount()) {
            return inputSelectorTable.getColumnName(model.getArray().getG().getFirstColumn());
        }
        return "";
    }


    public String getGbHeader() {
        if (model.getArray().getGb().getFirstColumn() > 0 &&
                model.getArray().getGb().getFirstColumn() < inputSelectorTable.getColumnCount()) {
            return inputSelectorTable.getColumnName(model.getArray().getGb().getFirstColumn());
        }
        return "";
    }


    public List<String> getAnnotationHeaders() {
        ArrayList<String> anns = new ArrayList<String>();
        for (Integer i : model.getArray().getAnnotations().getColumns()) {
            anns.add(inputSelectorTable.getColumnName(i));
        }
        return anns;
    }


    public List<String> getOtherColumnHeaders() {
        ArrayList<String> others = new ArrayList<String>();
        for (Integer i : model.getOtherColumnsIndices()) {
            others.add(inputSelectorTable.getColumnName(i));
        }
        return others;
    }


    private void initComponents() {

        jXHeader1 = new org.jdesktop.swingx.JXHeader();
        inputSelectorScrollPane = new javax.swing.JScrollPane();
        tableScrollPane = new javax.swing.JScrollPane();

        jXHeader1.setDescription("Please define input parameters in the drop-down menus (required and optional parameters).");

        inputSelectorScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        inputSelectorScrollPane.getVerticalScrollBar().setEnabled(false);
        inputSelectorScrollPane.setBorder(null);

        table = new JXTable();
        table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
        table.setColumnControlVisible(true);

        tableScrollPane.setViewportView(table);
        tableScrollPane.setBorder(null);
        if (isHeightSet) {
            tableScrollPane.setPreferredSize(new Dimension(table.getPreferredScrollableViewportSize().width, height));
        }
        inputSelectorTable = new InputSelectorTable(table);
        table.setHorizontalScrollEnabled(true);
        inputSelectorScrollPane.setPreferredSize(
                new Dimension(inputSelectorTable.getPreferredScrollableViewportSize().width,
                        converter.dialogUnitXAsPixel(inputSelectorTable.getFont().getSize() + 4, inputSelectorTable)));
        inputSelectorScrollPane.setViewportView(inputSelectorTable);

        AdjustmentController controller = new AdjustmentController();
        controller.registerScrollPane(tableScrollPane);
        controller.registerScrollPane(inputSelectorScrollPane);

        FormLayout layout = new FormLayout("fill:min:grow",//cols
                "min,2dlu,pref,fill:pref:grow");
        setLayout(layout);
        CellConstraints cc = new CellConstraints();
        add(new javax.swing.JLabel("<html><br>Please define input parameters in the drop-down menus " +
                "(required and optional parameters).<br><br></html>"), cc.xy(1, 1));
        add(inputSelectorScrollPane, cc.xy(1, 3));
        add(tableScrollPane, cc.xy(1, 4));

//        setLayout(new BorderLayout(0,2));
//        JPanel northpanel = new JPanel(new BorderLayout());
//        northpanel.add(jXHeader1,BorderLayout.NORTH);
//        northpanel.add(inputSelectorScrollPane,BorderLayout.CENTER);        
//        add(northpanel,BorderLayout.NORTH);
//        add(tableScrollPane);
    }


    private javax.swing.JScrollPane inputSelectorScrollPane;
    private org.jdesktop.swingx.JXHeader jXHeader1;
    private javax.swing.JScrollPane tableScrollPane;


    public void propertyChange(PropertyChangeEvent evt) {
        if (Array.PROP_Annotation.equals(evt.getPropertyName())) {
            String[] old = annotations;
            annotations = new String[model.getArray().getAnnotations().getColumns().size()];
            for (int i = 0; i < model.getArray().getAnnotations().getColumns().size(); i++) {
                annotations[i] = inputSelectorTable.getColumnName(model.getArray().getAnnotations().getColumns().get(i));
            }
            firePropertyChange(Array.PROP_Annotation, old, annotations);
        } else if (Array.PROP_G.equals(evt.getPropertyName())) {
        } else if (Array.PROP_Gb.equals(evt.getPropertyName())) {
        } else if (Array.PROP_R.equals(evt.getPropertyName())) {
        } else if (Array.PROP_Rb.equals(evt.getPropertyName())) {
        }
    }


    public String[] getAnnotations() {
        return annotations;
    }


    public String[] getColumns() {
        return columns;
    }
}
