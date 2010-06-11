/*
 * RCheckBox.java
 *
 * Created on 19. Oktober 2006, 08:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.checkbox;

import javax.swing.*;

import at.ac.arcs.rgg.element.checkbox.VCheckBox;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

import java.util.Map;


/**
 * @author ilhami
 */
public class RCheckBox extends RElement {

    private String var;
    private String label;
    private String returnValueBySelected = "TRUE";
    private String returnValueByNotSelected = "FALSE";
    private VCheckBox vcheckbox;
    private VisualComponent[][] visualcomponents;


    /**
     * Creates a new instance of RCheckBox
     */
    public RCheckBox() {
    }


    public String getRCode() {
        String value;
        if (vcheckbox.isSelected() && vcheckbox.isEnabled()) {
            value = returnValueBySelected;
        } else {
            value = returnValueByNotSelected;
        }

        if (StringUtils.isBlank(var)) {
            return value;
        } else {
            return var + "<-" + value;
        }
    }


    public boolean hasVisualComponents() {
        return true;
    }


    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vcheckbox}};
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
        if (StringUtils.isBlank(label)) {
            label = var;
        }
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public VCheckBox getCheckBox() {
        return vcheckbox;
    }


    public void setCheckBox(VCheckBox vcheckbox) {
        this.vcheckbox = vcheckbox;
    }


    public JComponent[][] getSwingComponentMatrix() {
        return vcheckbox.getSwingComponents();
    }


    public String getReturnValueBySelected() {
        return returnValueBySelected;
    }


    public void setReturnValueBySelected(String returnValueBySelected) {
        this.returnValueBySelected = returnValueBySelected;
    }


    public String getReturnValueByNotSelected() {
        return returnValueByNotSelected;
    }


    public void setReturnValueByNotSelected(String returnValuebyNotSelected) {
        this.returnValueByNotSelected = returnValuebyNotSelected;
    }


    @Override
    public void persistState(Map<String, Object> persistMap) {
        persistMap.put(vcheckbox.getLabelText(), vcheckbox.isSelected());
    }


    @Override
    public void restoreState(Map<String, Object> persistMap) {
        System.err.println(vcheckbox.getLabelText());

        // necessary to allow apple-checkbox to return an actual value on macos when showing swing widget within eclipse
        if (persistMap.containsKey(vcheckbox.getLabelText())) {
//            JOptionPane.showConfirmDialog(null, "changing value of '"+vcheckbox.getLabelText()+"'  to '"+persistMap.get(vcheckbox.getLabelText())+"'here we go" + persistMap.toString());
            vcheckbox.setSelected((Boolean) persistMap.get(vcheckbox.getLabelText()));
        }
    }
}
