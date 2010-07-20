/*
 * RVector.java
 *
 * Created on 17.04.2007, 10:55:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.vector;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;


/**
 * @author ilhami
 */
public class RVector extends RElement {

    private String label;
    private String var;

    private VisualComponent[][] visualcomponents;

    private VVector vvector;


    public RVector(VVector vvector) {
        this.vvector = vvector;
    }


    public String getRCode() {
        StringBuilder rbuilder = new StringBuilder();
        if (var != null)
            rbuilder.append(var + "<-");
        rbuilder.append("c(");
        if (vvector.getVectortype() == VectorType.NUMERIC) {
            for (JComponent textfield : vvector.getVectorlist()) {
                rbuilder.append(((JFormattedTextField) textfield).getText() + ",");
            }
        } else if (vvector.getVectortype() == VectorType.CHARACTER) {
            for (JComponent textfield : vvector.getVectorlist()) {
                rbuilder.append("\"" + ((JFormattedTextField) textfield).getText() + "\",");
            }
        } else {
            for (JComponent component : vvector.getVectorlist()) {
                rbuilder.append(new Boolean(((JCheckBox) component).isSelected()).toString().toUpperCase() + ",");
            }
        }
        rbuilder.deleteCharAt(rbuilder.length() - 1);
        rbuilder.append(")");
        return rbuilder.toString();
    }


    public boolean hasVisualComponents() {
        return true;
    }


    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null)
            visualcomponents = new VisualComponent[][]{{vvector}};
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
        vvector.setLabelText(label);
    }


    public JComponent[][] getSwingComponentMatrix() {
        return vvector.getSwingComponents();
    }

}
