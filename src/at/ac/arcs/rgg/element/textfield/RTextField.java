/*
 * VTextField.java
 *
 * Created on 09. Oktober 2006, 15:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.textfield;

import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.component.VisualComponent;

import java.util.Map;


/**
 *
 * @author ilhami
 */
public class RTextField extends RElement {

    private String label;
    private String var;
    private VTextField textfield;
    private VisualComponent[][] visualcomponents;

    /**
     * Creates a new instance of VTextField
     */
    public RTextField() {
    }

    public String getRCode() {
        StringBuilder rcode = new StringBuilder();

        if (StringUtils.isNotBlank(var)) {
            rcode.append(var);
            rcode.append("<-");
        }
        if (textfield.isNumeric()) {
            if (StringUtils.isBlank(textfield.getTextFieldValue())) {
                rcode.append("NA");
            } else {
                rcode.append(textfield.getTextFieldValue());
            }
        } else {
            if (StringUtils.isBlank(textfield.getTextFieldValue())) {
                rcode.append("NA");
            } else {
                rcode.append("\"");
                rcode.append(textfield.getTextFieldValue());
                rcode.append("\"");
            }
        }
        return rcode.toString();
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{textfield}};
        }
        return visualcomponents;
    }

    public boolean isChildAddable() {
        return false;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public VTextField getTextfield() {
        return textfield;
    }

    public void setTextfield(VTextField textfield) {
        this.textfield = textfield;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getVar() {
        return var;
    }

    public JComponent[][] getSwingComponentMatrix() {
        return textfield.getSwingComponents();
    }


    @Override
    public void persistState(Map<String, Object> persistMap) {
        persistMap.put(textfield.getLabelText(), textfield.getTextFieldValue());
    }


    @Override
    public void restoreState(Map<String, Object> persistMap) {
        if (persistMap.containsKey(textfield.getLabelText())) {
            textfield.textfield.setText(persistMap.get(textfield.getLabelText()).toString());
        }
    }
}
