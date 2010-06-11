/*
 * RRadioButton.java
 *
 * Created on 12. November 2006, 01:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.radiobutton;

import java.util.HashMap;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RRadioButton extends RElement {

    private static final HashMap<String, ButtonGroup> buttonGroupMap = new HashMap<String, ButtonGroup>();
    private String var;
    private String buttonGroup;
    private String label;
    private boolean selected;
    private String returnValueBySelected = "TRUE";
    private String returnValueByNotSelected = "FALSE";
    
    private VRadioButton vradioButton;
    private VisualComponent[][] visualcomponents;

    /** Creates a new instance of RRadioButton */
    public RRadioButton(VRadioButton vradioButton) {
        this.vradioButton = vradioButton;
    }

    @Override
    public String getRCode() {
        StringBuilder rbuilder = new StringBuilder();
        if (StringUtils.isNotBlank(var)) {
            rbuilder.append(var + "<-");
        }
        if (vradioButton.getSelected()) {
            rbuilder.append(returnValueBySelected);
        } else {
            rbuilder.append(returnValueByNotSelected);
        }
        return rbuilder.toString();
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vradioButton}};
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
        vradioButton.setLabelText(label);
    }

    public String getButtonGroup() {
        return buttonGroup;
    }

    public void setButtonGroup(String buttonGroup) {
        this.buttonGroup = buttonGroup;
        addToButtonGroup(buttonGroup);
    }

    public VRadioButton getVRadioButton() {
        return vradioButton;
    }

    public void setVRadioButton(VRadioButton vradioButton) {
        this.vradioButton = vradioButton;
    }

    public void addToButtonGroup(String buttonGroup) {
        if (buttonGroupMap.containsKey(buttonGroup)) {
            vradioButton.addToButtonGroup(buttonGroupMap.get(buttonGroup));
        } else {
            ButtonGroup bg = new ButtonGroup();
            buttonGroupMap.put(buttonGroup, bg);
            vradioButton.addToButtonGroup(bg);
        }
    }

    public void removeFromButtonGroup(String buttonGroup) {
        vradioButton.removeFromButtonGroup(buttonGroupMap.get(buttonGroup));
    }

    void setColumnSpan(int colspan) {
        vradioButton.setColumnSpan(colspan);
    }

    public JComponent[][] getSwingComponentMatrix() {
        return vradioButton.getSwingComponents();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        vradioButton.setDefaultValue(selected);
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

    public void setReturnValueByNotSelected(String returnValueByNotSelected) {
        this.returnValueByNotSelected = returnValueByNotSelected;
    }

    public VRadioButton getVradioButton() {
        return vradioButton;
    }

    public void setVradioButton(VRadioButton vradioButton) {
        this.vradioButton = vradioButton;
    }

}
