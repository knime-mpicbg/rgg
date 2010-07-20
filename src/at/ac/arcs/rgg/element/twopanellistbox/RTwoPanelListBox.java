/*
 * RList.java
 *
 * Created on 12. November 2006, 01:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.twopanellistbox;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author ilhami
 */
public class RTwoPanelListBox extends RElement {

    private String var;
    private String label;
    private boolean numeric = false;
    private VTwoPanelListBox vList;
    private VisualComponent[][] visualcomponents;

    private boolean keepMissingOptions = false;


    /**
     * Creates a new instance of RList
     */
    public RTwoPanelListBox() {
    }


    public String getRCode() {
        StringBuffer sbuf = new StringBuffer();

        if (StringUtils.isNotBlank(var))
            sbuf.append(var + "<-");

        List<String> selectedValues = vList.getSelectedValues();
        if (isNumeric()) {
            for (Object obj : selectedValues)
                sbuf.append(obj.toString() + ",");

        } else {
            for (Object obj : selectedValues)
                sbuf.append("\"" + obj.toString() + "\",");
        }

        // drop the last comma
        if (sbuf.length() > 0) {
            sbuf = sbuf.deleteCharAt(sbuf.length() - 1);
        }

        return sbuf.toString();
    }


    public boolean hasVisualComponents() {
        return true;
    }


    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null)
            setVisualcomponents(new VisualComponent[][]{{vList}});
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
        if (StringUtils.isBlank(getLabel()))
            setLabel(var);
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
        vList.setLabelText(label);
    }


    public VTwoPanelListBox getVComboBox() {
        return vList;
    }


    public void setVList(VTwoPanelListBox vList) {
        this.vList = vList;
    }


    void setColumnSpan(int colspan) {
        vList.setColumnSpan(colspan);
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
        return vList.getSwingComponents();
    }


    @Override
    public void persistState(Map<String, Object> persistMap) {
        persistMap.put(vList.getLabel().getText(), vList.getSelectedValues());
    }


    @Override
    public void restoreState(Map<String, Object> persistMap) {
        List<String> options = vList.getSelectedValues();


        if (persistMap.containsKey(vList.getLabelText())) {
            List<String> unpersistedSelection = (List<String>) persistMap.get(vList.getLabel().getText());

            List<String> filteredSelection = new ArrayList<String>();
            if (keepMissingOptions) {
                filteredSelection.addAll(unpersistedSelection);

            } else {
                for (String selectedItem : filteredSelection) {
                    if (options.contains(selectedItem)) {
                        filteredSelection.add(selectedItem);
                    }
                }
            }

            vList.setIncludes(filteredSelection);
        }
    }


    public void setKeepMissingOptions(boolean keepMissingOptions) {
        this.keepMissingOptions = keepMissingOptions;
    }
}