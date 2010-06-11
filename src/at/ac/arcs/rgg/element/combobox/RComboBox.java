package at.ac.arcs.rgg.element.combobox;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.element.combobox.VComboBox;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.util.Map;


/**
 * @author ilhami
 */
public class RComboBox extends RElement {

    private String var;
    private String label;
    private int selectedIndex;
    private Object selectedItem;
    private Object[] items;
    private boolean numeric = false;
    private VComboBox vComboBox;
    private VisualComponent[][] visualcomponents;


    /**
     * Creates a new instance of RComboBox
     */
    public RComboBox(VComboBox vComboBox) {
        this.setVComboBox(vComboBox);
    }


    public String getRCode() {
        StringBuffer sbuf = new StringBuffer();
        if (StringUtils.isNotBlank(var)) {
            sbuf.append(var + "<-");
        }
        if (vComboBox.getSelectedItem() != null) {
            if (isNumeric()) {
                sbuf.append(vComboBox.getSelectedItem().toString());
            } else {
                sbuf.append("\"" + vComboBox.getSelectedItem().toString() + "\"");

            }
        } else {
            sbuf.append("NA");
        }
        return sbuf.toString();
    }


    public boolean hasVisualComponents() {
        return true;
    }


    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            setVisualcomponents(new VisualComponent[][]{{vComboBox}});
        }
        return visualcomponents;
    }


    public boolean isChildAddable() {
        return false;
    }


    public String getVar() {
        return var;
    }


    public void setVar(String var) {
        this.var = var;
        if (StringUtils.isBlank(getLabel())) {
            setLabel(var);
        }
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
        vComboBox.setLabelText(label);
    }


    public VComboBox getVComboBox() {
        return vComboBox;
    }


    public void setVComboBox(VComboBox vComboBox) {
        this.vComboBox = vComboBox;
    }


    void setColumnSpan(int colspan) {
        vComboBox.setColumnSpan(colspan);
    }


    public int getSelectedIndex() {
        return vComboBox.getSelectedIndex();
    }


    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        vComboBox.setSelectedIndex(selectedIndex - 1);
    }


    public Object getSelectedItem() {
        return vComboBox.getSelectedItem();
    }


    public void setSelectedItem(Object selectedItem) {
        this.selectedItem = selectedItem;
        vComboBox.setSelectedItem(selectedItem);
    }


    public Object[] getItems() {
        return items;
    }


    public void setItems(Object[] items) {
        this.items = items;
        vComboBox.setItems(items);
    }


    public void setVisualcomponents(VisualComponent[][] visualcomponents) {
        this.visualcomponents = visualcomponents;
    }


    public boolean isNumeric() {
        return numeric;
    }


    public void setNumeric(boolean numeric) {
        this.numeric = numeric;
    }


    public JComponent[][] getSwingComponentMatrix() {
        return vComboBox.getSwingComponents();
    }


    @Override
    public void persistState(Map<String, Object> persistMap) {
        persistMap.put(vComboBox.getLabelText(), vComboBox.getSelectedItem());
    }


    @Override
    public void restoreState(Map<String, Object> persistMap) {
        if (persistMap.containsKey(vComboBox.getLabelText())) {
            vComboBox.setSelectedItem(persistMap.get(vComboBox.getLabelText()));
        }
    }
}